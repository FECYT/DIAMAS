package es.soltel.recolecta;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    public JsonObject jsonObject() {
        // Cargar el archivo JSON de recursos
        InputStream inputStream = getClass().getResourceAsStream("/config.json");
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        // Utilizar Gson para parsear el archivo JSON a JsonObject
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);

        return jsonObject;
    }

}