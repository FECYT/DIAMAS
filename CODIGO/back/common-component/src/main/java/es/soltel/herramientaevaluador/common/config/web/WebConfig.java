package es.soltel.herramientaevaluador.common.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://evaluador.recolecta.fecyt.es/","http://localhost:4200", "https://evaluacion.recolecta.fecyt.es","http://localhost:8001","http://192.168.74.107:8380","http://192.168.74.107:8001","http://192.168.74.107:8002","https://w2dwnzdn-4200.uks1.devtunnels.ms")
                .allowedMethods("*")
                .exposedHeaders("Authorization");
    }
	
	 @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addRedirectViewController("/", "/api/swagger-ui.html");
	    }

}