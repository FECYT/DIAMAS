package es.soltel.herramientaevaluador.common.config.token;

import java.util.List;

import es.soltel.herramientaevaluador.common.base.exception.RestException;

public interface ITokenService {

    String createTokenDeprectated(String username, String pass) throws RestException;

    String createTokenEvaluadorNoData();

    String createToken(Long idUser, String email, List<String> roles, String nombre, String apellidos, String institucion, Integer returnCode) throws RestException;
}
