package es.soltel.recolecta.service;

import javax.servlet.http.HttpServletResponse;

import es.soltel.herramientaevaluador.common.model.LoginBean;
import es.soltel.herramientaevaluador.common.model.RegisterBean;
import es.soltel.recolecta.vo.LoginResponse;
import es.soltel.recolecta.vo.UserVO;

public interface LoginService {


    public String inicioSesionEvaluador();

    /**
     * @param loginBean
     *            contiene información codificada en base 64 para loguearse
     */
    LoginResponse inicioSesion(LoginBean loginBean, HttpServletResponse response) throws Exception;

    Integer registrar(RegisterBean registerBean);


}