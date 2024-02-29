package es.soltel.recolecta.service;

import es.soltel.recolecta.vo.ConfigVO;
import es.soltel.recolecta.vo.UserRepositoryVO;

import java.util.List;

public interface ConfigService {

    ConfigVO getConfig();

    void setAutomaticRegister(Boolean status);

    Boolean getAutomaticRegister();

}
