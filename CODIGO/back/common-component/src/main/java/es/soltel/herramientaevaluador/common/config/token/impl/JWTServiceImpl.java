package es.soltel.herramientaevaluador.common.config.token.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.soltel.herramientaevaluador.common.config.seguridad.JwtConfig;
import es.soltel.herramientaevaluador.common.config.seguridad.SeguridadPropertiesConfig;
import es.soltel.herramientaevaluador.common.config.token.IJWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;

@Component
public class JWTServiceImpl implements IJWTService {

	private static final String START_WITH_BEARER = "Bearer ";

	@Autowired
	private SeguridadPropertiesConfig seguridadPropertiesConfig;

	@Override
	public JwtConfig getJwtConfig() {
		return this.seguridadPropertiesConfig.getJwt();
	}

	@Override
	public Claims getClaims(String token) throws Exception {
		try {
			Claims claims = null;

			if (token.startsWith(START_WITH_BEARER)) {
				token = token.substring(START_WITH_BEARER.length());
			}

			try {
				claims = Jwts.parser().setSigningKey(this.seguridadPropertiesConfig.getJwt().getSecret().getBytes())
						.parseClaimsJws(token).getBody();
			} catch (final ExpiredJwtException e) {
				// Si el token ha expirado, salta ExpiredJwtException y hay que
				// rearmar las
				// claims manualmente aprovechando el token
				final String[] splitString = token.split("\\.");
				final String base64EncodedBody = splitString[1];
				final String body = new String(Base64.getDecoder().decode(base64EncodedBody));
				@SuppressWarnings("unchecked")
				final HashMap<String, Object> mapaClaims = new ObjectMapper().readValue(body, HashMap.class);
				claims = new DefaultClaims(mapaClaims);
			}
			return claims;
		} catch (final Exception e) {
			throw e;
		}
	}

	@Override
	public String getUserId(String token) throws Exception {
		return this.getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws Exception {
		try {
			final Object roles = this.getClaims(token).get("authorities");

			Collection<? extends GrantedAuthority> authorities;
			authorities = Arrays
					.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
							.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

			return authorities;
		} catch (final IOException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public String resolve(String token) throws Exception {
		if (token != null && token.startsWith(this.getJwtConfig().getPrefix())) {
			return token.replace(this.getJwtConfig().getPrefix(), "");
		}

		return null;
	}

	@Override
	public String getHeader(HttpServletRequest request) throws Exception {
		return request.getHeader(this.getJwtConfig().getHeader());
	}

	@Override
	public String getToken(HttpServletRequest request) throws Exception {
		return this.resolve(this.getHeader(request));
	}

}
