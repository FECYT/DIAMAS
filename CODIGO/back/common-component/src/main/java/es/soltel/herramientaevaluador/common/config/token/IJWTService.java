package es.soltel.herramientaevaluador.common.config.token;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;

import es.soltel.herramientaevaluador.common.config.seguridad.JwtConfig;
import io.jsonwebtoken.Claims;

public interface IJWTService {

	JwtConfig getJwtConfig();

	Claims getClaims(String token) throws Exception;

	String getUserId(String token) throws Exception;

	Collection<? extends GrantedAuthority> getRoles(String token) throws Exception;

	String resolve(String token) throws Exception;

	String getHeader(HttpServletRequest request) throws Exception;

	String getToken(HttpServletRequest request) throws Exception;

}
