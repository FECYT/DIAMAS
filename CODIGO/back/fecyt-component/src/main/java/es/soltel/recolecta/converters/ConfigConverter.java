package es.soltel.recolecta.converters;

import es.soltel.recolecta.entity.ActionEntity;
import es.soltel.recolecta.entity.ConfigEntity;
import es.soltel.recolecta.vo.ActionVO;
import es.soltel.recolecta.vo.ConfigVO;
import org.springframework.stereotype.Component;

@Component
public class ConfigConverter {

    public static ConfigVO convertToVo(ConfigEntity configEntity) {
        ConfigVO configVO = new ConfigVO();

        configVO.setId(configEntity.getId());
        configVO.setAutomaticRegister(configEntity.getAutomaticRegister());

        return configVO;
    }

    public static ConfigEntity convertToEntity(ConfigVO configVO) {
        ConfigEntity configEntity = new ConfigEntity();

        configEntity.setId(configVO.getId());
        configEntity.setAutomaticRegister(configVO.getAutomaticRegister());

        return configEntity;
    }

}
