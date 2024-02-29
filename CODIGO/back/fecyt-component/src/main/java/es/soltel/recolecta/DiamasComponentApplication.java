package es.soltel.recolecta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * Clase encargada de gestionar el arranque del microservicio para gestionar las entidades.
 */

@EnableFeignClients(basePackages = { "es.soltel.herramientaevaluador.common.config.client" })
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
@EntityScan({ "es.soltel.herramientaevaluador.common.base", "es.soltel.recolecta.entity" })
@ComponentScan({ "es.soltel.herramientaevaluador.common.config", "es.soltel.recolecta", "es.soltel.herramientaevaluador.common.base.util" })
public class DiamasComponentApplication {


	public static void main(String[] args) {
		SpringApplication.run(DiamasComponentApplication.class, args);
	}

}


	