package es.soltel.recolecta.service.impl;

import com.google.gson.JsonObject;
import es.soltel.recolecta.converters.ConfigConverter;
import es.soltel.recolecta.entity.ConfigEntity;
import es.soltel.recolecta.repository.ConfigRepository;
import es.soltel.recolecta.service.ConfigService;
import es.soltel.recolecta.vo.ConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepository;

    @Override
    public ConfigVO getConfig() {
        return ConfigConverter.convertToVo(configRepository.findById(1L).orElse(null));
    }

    @Override
    public void setAutomaticRegister(Boolean status) {
        ConfigEntity config = configRepository.findById(1L).orElse(null);
        config.setAutomaticRegister(status);
        configRepository.save(config);
    }

    @Override
    public Boolean getAutomaticRegister() {
        return configRepository.findById(1L).orElse(null).getAutomaticRegister();
    }


}