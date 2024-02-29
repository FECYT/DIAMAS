package es.soltel.herramientaevaluador.common.config.seguridad;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SeguridadPropertiesConfig seguridadPropertiesConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("EntraConfigure");
		http.cors().and().csrf().disable()
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// handle an authorized attempts
				.exceptionHandling()
				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
				// Añadimos el filtro para validar el token en cada petición
				.addFilterAfter(new JwtTokenAuthorizationFilter(seguridadPropertiesConfig),
						UsernamePasswordAuthenticationFilter.class)
				// Ahora se configuran las peticiones que se van a autotorizar
				.authorizeRequests()
				// Se permite la conexión a los patrones de la whitelist
				.antMatchers(seguridadPropertiesConfig.getWhitelist() != null ? seguridadPropertiesConfig.getWhitelist()
						.toArray(new String[seguridadPropertiesConfig.getWhitelist().size()]) : null)
				.permitAll().anyRequest().authenticated();
		// Para el resto de llamadas a componentes, al menos debe estar
		// autenticado, ya se comprobará el rol en cada método concreto
	}
}
