package es.soltel.recolecta.converters;

import es.soltel.recolecta.entity.RolRelationEntity;
import es.soltel.recolecta.vo.RolRelationVO;
import es.soltel.recolecta.vo.RolVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RolRelationConverter {

    public static RolRelationVO convertToVo(RolRelationEntity rolRelationEntity) {
        RolRelationVO rolRelationVO = new RolRelationVO();
        rolRelationVO.setId(rolRelationEntity.getId());
        rolRelationVO.setUser(UserConverter.toVO(rolRelationEntity.getUser()));
        rolRelationVO.setRol(RolConverter.convertToVo(rolRelationEntity.getRol()));
        return rolRelationVO;
    }

    public static RolRelationEntity convertToEntity(RolRelationVO rolRelationVO) {
        RolRelationEntity rolRelationEntity = new RolRelationEntity();
        rolRelationEntity.setId(rolRelationVO.getId());
        rolRelationEntity.setUser(UserConverter.toEntity(rolRelationVO.getUser()));
        rolRelationEntity.setRol(RolConverter.convertToEntity(rolRelationVO.getRol()));

        return rolRelationEntity;
    }

    public static List<RolRelationVO> ToVO(List<RolRelationEntity> entities) {
        return entities.stream().map(RolRelationConverter::convertToVo).collect(Collectors.toList());
    }

    public static List<RolRelationEntity> ToEntity(List<RolRelationVO> vos) {
        return vos.stream().map(RolRelationConverter::convertToEntity).collect(Collectors.toList());
    }
}
