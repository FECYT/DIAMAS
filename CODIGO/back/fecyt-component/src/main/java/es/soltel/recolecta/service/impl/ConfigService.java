package es.soltel.recolecta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

@Service
public class ConfigService {

    @Autowired
    private JsonObject config;

    public int getIntervaloCuestionarioEnSegundos() {
        return config.get("intervaloCuestionarioEnSegundos").getAsInt();
    }

}