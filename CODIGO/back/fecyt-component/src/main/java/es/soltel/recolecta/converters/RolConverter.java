package es.soltel.recolecta.converters;

import es.soltel.recolecta.entity.ActionEntity;
import es.soltel.recolecta.entity.RolEntity;
import es.soltel.recolecta.entity.RolRelationEntity;
import es.soltel.recolecta.entity.UserEntity;
import es.soltel.recolecta.vo.ActionVO;
import es.soltel.recolecta.vo.RolRelationVO;
import es.soltel.recolecta.vo.RolVO;
import es.soltel.recolecta.vo.UserVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RolConverter {

    public static RolVO convertToVo(RolEntity rolEntity) {
        RolVO rolVO = new RolVO();
        rolVO.setId(rolEntity.getId());
        rolVO.setName(rolEntity.getName());
        return rolVO;
    }

    public static RolEntity convertToEntity(RolVO rolVO) {
        RolEntity rolEntity = new RolEntity();
        rolEntity.setId(rolVO.getId());
        rolEntity.setName(rolVO.getName());
        return rolEntity;
    }

    public static List<RolVO> ToVO(List<RolEntity> entities) {
        return entities.stream().map(RolConverter::convertToVo).collect(Collectors.toList());
    }
}
