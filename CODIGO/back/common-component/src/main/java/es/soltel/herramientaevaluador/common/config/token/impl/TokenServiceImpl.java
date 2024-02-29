package es.soltel.herramientaevaluador.common.config.token.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.soltel.herramientaevaluador.common.base.exception.RestException;
import es.soltel.herramientaevaluador.common.config.seguridad.JwtConfig;
import es.soltel.herramientaevaluador.common.config.seguridad.SeguridadPropertiesConfig;
import es.soltel.herramientaevaluador.common.config.token.ITokenService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private SeguridadPropertiesConfig seguridadPropertiesConfig;

    @Override
    public String createTokenDeprectated(String username, String pass) throws RestException {
        try {
            JwtConfig jwtConfig = seguridadPropertiesConfig.getJwt();

            JwtBuilder buildToken = Jwts.builder().setSubject(String.valueOf(username))
                    // Convert to list of strings.
                    // This is important because it affects the way we get them back
                    // in the Gateway.
                    // .claim("authorities",
                    // SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    // .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000)) // in
                    // milliseconds
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes());
            // List<PermisoInner> arrayList = new ArrayList<PermisoInner>();

            //            if (!permisos.isEmpty()) {
            //
            //                for (UsuarioXPermisoVO uXp : permisos) {
            //                    PermisoInner permisoInner = new PermisoInner(uXp.getPermiso().getNombre(),
            //                            (uXp.getPropioAjeno() != null && uXp.getPropioAjeno().toString().equals("true")) ? "1"
            //                                    : "0");
            //                    arrayList.add(permisoInner);
            //
            //                }
            //
            //            }

            // buildToken.claim("permisos",
            // arrayList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            // buildToken.claim("permisos", arrayList);

            // buildToken.claim("tokenOffice365", tokenOffice365);
            buildToken.claim("username", username);
            // buildToken.claim("rol", rol);
            // buildToken.claim("iduser", iduser);
            // buildToken.claim("surname", surname);
            // buildToken.claim("name", name);
            // Se crea el token
            return buildToken.compact();
        }
        catch (Exception e) {
            throw new RestException(e.getMessage(), e);
        }
    }

    @Override
    public String createTokenEvaluadorNoData() {
        try {
            JwtConfig jwtConfig = seguridadPropertiesConfig.getJwt();

            JwtBuilder buildToken = Jwts.builder().setSubject(String.valueOf("pruebas"))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000))
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes());
            return buildToken.compact();
        }
        catch (Exception e) {
            throw new RestException(e.getMessage(), e);
        }

    }


    /**
     * Crea un token JWT para un evaluador con información específica.
     * 
     * @param email
     *            El correo electrónico del evaluador.
     *            El ID del usuario.
     * @param roles
     *            Los roles asignados al evaluador.
     * @return Una cadena que representa el token JWT generado.
     * @throws RestException
     *             si algo sale mal durante la creación del token.
     */
    @Override
    public String createToken(Long idUser, String email, List<String> roles, String nombre, String apellidos, String institucion, Integer returnCode) throws RestException {
        try {
            JwtConfig jwtConfig = seguridadPropertiesConfig.getJwt();


            JwtBuilder buildToken = Jwts.builder().setSubject(String.valueOf(email))//.claims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000))
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes());

            buildToken.claim("idUser", idUser);
            buildToken.claim("email", email);
            buildToken.claim("roles", roles);
            buildToken.claim("nombre", nombre);
            buildToken.claim("apellidos", apellidos);
            buildToken.claim("institucion", institucion);
            buildToken.claim("returnCode", returnCode);

            return buildToken.compact();
        }
        catch (Exception e) {
            throw new RestException(e.getMessage(), e);
        }
    }

}
