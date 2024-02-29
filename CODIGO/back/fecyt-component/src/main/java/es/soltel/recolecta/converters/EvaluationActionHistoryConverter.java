package es.soltel.recolecta.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.EvaluationActionHistoryEntity;
import es.soltel.recolecta.vo.EvaluationActionHistoryVO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EvaluationActionHistoryConverter {

    @Autowired
    private EvaluationConverter evaluationConverter; // Asumiendo que existe

    @Autowired
    private ActionConverter actionConverter; // Asumiendo que existe

    public static EvaluationActionHistoryEntity toEntity(EvaluationActionHistoryVO vo) {
        if (vo == null)
            return null;

        EvaluationActionHistoryEntity entity = new EvaluationActionHistoryEntity();

        entity.setId(vo.getId());

        if (vo.getEvaluationId() != null)
            entity.setEvaluationId(EvaluationConverter.ToEntity(vo.getEvaluationId()));
        if (vo.getActionId() != null)
            entity.setActionId(ActionConverter.convertToEntity(vo.getActionId()));
        if (vo.getUserId() != null)
            entity.setUser(UserConverter.toEntity(vo.getUserId()));
        entity.setLastEdited(vo.getLastEdited());
        entity.setNDeleteState(vo.getNDeleteState());

        return entity;
    }

    public static EvaluationActionHistoryVO toVO(EvaluationActionHistoryEntity entity) {
        if (entity == null)
            return null;

        EvaluationActionHistoryVO vo = new EvaluationActionHistoryVO();
        vo.setId(entity.getId());
        vo.setActionId(ActionConverter.convertToVo(entity.getActionId()));
        vo.setLastEdited(entity.getLastEdited());
        vo.setNDeleteState(entity.getNDeleteState());
        if (entity.getUser() != null)
            vo.setUserId(UserConverter.toVO(entity.getUser()));
        if (entity.getEvaluationId() != null)
            vo.setEvaluationId(EvaluationConverter.entityToVO(entity.getEvaluationId()));
        if (entity.getActionId() != null)
            vo.setActionId(ActionConverter.convertToVo(entity.getActionId()));

        return vo;
    }

    public static List<EvaluationActionHistoryVO> toVOList(List<EvaluationActionHistoryEntity> entities) {
        return entities.stream().map(EvaluationActionHistoryConverter::toVO).collect(Collectors.toList());
    }
}
