package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import es.soltel.recolecta.entity.AssignedEvaluatorsEntity;
import es.soltel.recolecta.vo.AssignedEvaluatorsVO;

public class AssignedEvaluatorsConverter {

    public static AssignedEvaluatorsVO entityToVO(AssignedEvaluatorsEntity entity) {
        if (entity == null) return null;

        AssignedEvaluatorsVO vo = new AssignedEvaluatorsVO();
        vo.setId(entity.getId());
        vo.setDnetId(entity.getDnetId());
        vo.setNDeleteState(entity.getNDeleteState());

        if (entity.getEvaluation() != null) {
            vo.setEvaluation(EvaluationConverter.entityToVO(entity.getEvaluation()));
        }

        if (entity.getUser() != null) {
            vo.setUsers(UserConverter.toVOList(entity.getUser()));
        }

        return vo;
    }

    public static AssignedEvaluatorsEntity voToEntity(AssignedEvaluatorsVO vo) {
        if (vo == null) return null;

        AssignedEvaluatorsEntity entity = new AssignedEvaluatorsEntity();
        entity.setId(vo.getId());
        entity.setDnetId(vo.getDnetId());
        entity.setNDeleteState(vo.getNDeleteState());

        if (vo.getEvaluation() != null) {
            entity.setEvaluation(EvaluationConverter.ToEntity(vo.getEvaluation()));
        }

        if (vo.getUsers() != null) {
            entity.setUser(UserConverter.toEntityFromList(vo.getUsers()));
        }

        return entity;
    }

    public static List<AssignedEvaluatorsVO> entitiesToVos(List<AssignedEvaluatorsEntity> entities) {
        return entities.stream().map(AssignedEvaluatorsConverter::entityToVO).collect(Collectors.toList());
    }

    public static List<AssignedEvaluatorsEntity> vosToEntities(List<AssignedEvaluatorsVO> vos) {
        return vos.stream().map(AssignedEvaluatorsConverter::voToEntity).collect(Collectors.toList());
    }
}
