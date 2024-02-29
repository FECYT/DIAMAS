package es.soltel.recolecta.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import es.soltel.herramientaevaluador.common.model.RegisterBean;
import es.soltel.recolecta.service.*;
import es.soltel.recolecta.utils.PasswordSecurity;
import es.soltel.recolecta.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.herramientaevaluador.common.base.exception.RestException;
import es.soltel.herramientaevaluador.common.base.exception.RestUnauthorizedException;
import es.soltel.herramientaevaluador.common.config.seguridad.JwtConfig;
import es.soltel.herramientaevaluador.common.config.seguridad.SeguridadPropertiesConfig;
import es.soltel.herramientaevaluador.common.config.token.ITokenService;
import es.soltel.herramientaevaluador.common.model.LoginBean;
import es.soltel.recolecta.anottation.NoLogging;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private RequestHttpServiceImpl requestService;

    @Autowired
    private XMLResponseService xmlResponseService;

    @Autowired
    private DnetService dnetService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private InstitucionServiceImpl institucionService;

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private SeguridadPropertiesConfig seguridadPropertiesConfig;

    @Autowired
    private RolRelationService rolRelationService;

    @Autowired
    private MailSending mailSending;

    private ExecutorService executorService = Executors.newFixedThreadPool(2); // Puedes ajustar el número de hilos

    /**
     * @param loginBean
     *                  contiene información codificada en base 64 para loguearse
     */
    @NoLogging
    @Override
    public LoginResponse inicioSesion(LoginBean loginBean, HttpServletResponse response) throws Exception {

        try {

            String email = new String(Base64.getDecoder().decode(loginBean.getEmail()), StandardCharsets.UTF_8);
            String password = new String(Base64.getDecoder().decode(loginBean.getPassword()), StandardCharsets.UTF_8);
            String hashedPassword = PasswordSecurity.encrypt(password);

            UserVO userTarget = null;

            try{
                userTarget = userService.findByEmail(email);
            } catch (Exception e) {
            }

            LoginResponse loginResponse = new LoginResponse();
            Integer responseCode = 0;


            if (userTarget == null) {
                responseCode = 1;
            }
            if (responseCode == 0 && !hashedPassword.equals(userTarget.getPassword())) {
                responseCode = 2;
            }

            if (responseCode == 0 && userTarget.getActive().equals(false)) {
                responseCode = 3;
            }


            loginResponse.setResponseCode(responseCode);

            if (responseCode != 0){
                userTarget = new UserVO();
            }

            List<RolRelationVO> roles = rolRelationService.getUserRoles(userTarget.getId());

            List<String> rolNames = roles.stream()
                    .map(rolRelationVO -> rolRelationVO.getRol().getName())
                    .collect(Collectors.toList());

            String token = tokenService.createToken(userTarget.getId(),email,rolNames,userTarget.getNombre(),userTarget.getApellidos(),userTarget.getAfiliacion_institucional(),responseCode);
            JwtConfig jwtConfig = seguridadPropertiesConfig.getJwt();
            response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

            loginResponse.setResponseCode(responseCode);

            if (responseCode != 0) {
                return loginResponse;
            }

            loginResponse.setUserVO(userTarget);

            return loginResponse;
        } catch (RestUnauthorizedException eu) {
            throw eu;
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @Override
    public Integer registrar(RegisterBean registerBean) {

        String email = new String(Base64.getDecoder().decode(registerBean.getEmail()), StandardCharsets.UTF_8);
        String password = new String(Base64.getDecoder().decode(registerBean.getPassword()), StandardCharsets.UTF_8);
        String hashedPassword = PasswordSecurity.encrypt(password);

        registerBean.setPassword(hashedPassword);
        registerBean.setEmail(email);

        UserVO userTarget = null;

        try{
            userTarget = userService.findByEmail(email);
        } catch (Exception e) {}

        if (userTarget != null){
            //EMAIL YA EXISTE
            return 2;
        }

        userService.registerUser(registerBean);
        UserVO registeredUser = userService.findByEmail(registerBean.getEmail());

        Integer returnCode = 0;

        if (registeredUser.getActive() == true)
            returnCode = 0;
        else returnCode = 1;

        //Le establezco el rol predeterminado
        rolRelationService.setUserRoles(registeredUser.getId(), Arrays.asList(2L));

        //Le creo un repositorio

        RepositoryVO newRepo = new RepositoryVO();
        InstitucionVO institucionBuscada = institucionService.findByInstitucionName(registerBean.getIpsp());
        if(institucionBuscada == null) {
            InstitucionVO nuevaInstitucion = new InstitucionVO();
            nuevaInstitucion.setNombre(registerBean.getIpsp());
            nuevaInstitucion.setAcronimo(registerBean.getAcronimo());
            nuevaInstitucion.setUrl(registerBean.getUrl());
            institucionBuscada = institucionService.create(nuevaInstitucion);
            newRepo.setInstitucion(institucionBuscada);
        } else {
            newRepo.setInstitucion(institucionBuscada);
        }
        newRepo.setnDeleteState(1);

        RepositoryVO newRepoCreated = repositoryService.create(newRepo);

        UserRepositoryVO newUserRepo = new UserRepositoryVO();
        newUserRepo.setUser(registeredUser);
        newUserRepo.setNDeleteState(1);
        newUserRepo.setRepository(newRepoCreated);

        userRepositoryService.create(newUserRepo);

        //Enviar correos
        switch (returnCode){

            case 0:
                executorService.submit(() -> mailSending.sendMailUsuarioRegistradoExito(registeredUser.getEmail()));
                break;

            case 1:
                executorService.submit(() -> mailSending.sendMailAdmin_UsuarioRegistradoNecesitaConfirmacion(registeredUser.getEmail()));
                executorService.submit(() -> mailSending.sendMailUsuarioRegistradoExitoNecesitaConfirmacion());
                break;

            case 2:
                break;

        }

        return returnCode;
    }


    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular el hash de la contraseña", e);
        }
    }

    @Override
    public String inicioSesionEvaluador() {
        try {
            return tokenService.createTokenEvaluadorNoData();

        } catch (RestUnauthorizedException eu) {
            throw eu;
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    public static String emailAFormatoNombre(String email) {
        String[] partes = email.split("@")[0].split("\\.");
        StringBuilder nombreCompleto = new StringBuilder();
        for (String parte : partes) {
            if (nombreCompleto.length() > 0) {
                nombreCompleto.append(" ");
            }
            nombreCompleto.append(capitalize(parte));
        }
        return nombreCompleto.toString();
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // Método para reemplazar ":" por "."
    public String findAndReplace(String input) {
        return input.replace(":", ".");
    }

    private static String getMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convertir el hash de bytes a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular el hash MD5", e);
        }
    }

}