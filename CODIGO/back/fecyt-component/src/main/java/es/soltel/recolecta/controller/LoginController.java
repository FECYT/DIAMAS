package es.soltel.recolecta.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.soltel.herramientaevaluador.common.model.RegisterBean;
import es.soltel.recolecta.vo.LoginResponse;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soltel.herramientaevaluador.common.base.exception.RestException;
import es.soltel.herramientaevaluador.common.base.exception.RestUnauthorizedException;
import es.soltel.herramientaevaluador.common.model.LoginBean;
import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    public static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Permite inicia sesón mediante el certificado.
     * 
     * @return
     * @throws RestException
     */
    @NoLogging
    @PostMapping("/iniciarSesion")
    public LoginResponse iniciarSesion(@RequestBody LoginBean loginBean, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {

            return loginService.inicioSesion(loginBean, response);
    }

    @NoLogging
    @PostMapping("/registrar")
    public Integer registrar(@RequestBody RegisterBean registerBean){
        return loginService.registrar(registerBean);
    }

    @ExceptionHandler(RestUnauthorizedException.class)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

}
