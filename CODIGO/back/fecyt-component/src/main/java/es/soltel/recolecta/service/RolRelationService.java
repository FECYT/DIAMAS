package es.soltel.recolecta.service;

import es.soltel.recolecta.entity.RolRelationEntity;
import es.soltel.recolecta.vo.RolRelationVO;

import java.util.List;

public interface RolRelationService {

    void setUserRoles(Long userId, List<Long> roles);

    List<RolRelationVO> getUserRoles(Long userId);


}
