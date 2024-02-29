package es.soltel.herramientaevaluador.common.config.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.soltel.herramientaevaluador.common.config.seguridad.SeguridadPropertiesConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	@Autowired
	private SeguridadPropertiesConfig seguridadPropertiesConfig;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Override
	public void apply(RequestTemplate requestTemplate) {
		String header = seguridadPropertiesConfig.getJwt().getHeader();
		String token = httpServletRequest.getHeader(header);

		if (token != null) {
			if (!token.startsWith(seguridadPropertiesConfig.getJwt().getPrefix())) {
				token = seguridadPropertiesConfig.getJwt().getPrefix().concat(token);
			}
			requestTemplate.header(header, token);
		}
	}
}
