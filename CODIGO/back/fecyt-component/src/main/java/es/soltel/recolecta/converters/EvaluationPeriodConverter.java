package es.soltel.recolecta.converters;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.EvaluationPeriodEntity;
import es.soltel.recolecta.vo.EvaluationPeriodVO;

@Component
public class EvaluationPeriodConverter {

    public static EvaluationPeriodVO fromEntity(EvaluationPeriodEntity entity) {
        EvaluationPeriodVO vo = new EvaluationPeriodVO();
        vo.setId(entity.getId());
        vo.setStartDate(entity.getStartDate());
        vo.setFinishDate(entity.getFinishDate());
        vo.setStatus(entity.getStatus());
        vo.setDescription(entity.getDescription());
        vo.setDeleteState(entity.getDeleteState());
        if (entity.getQuestionnaireType() != null) vo.setQuestionnaireType(QuestionnaireTypeConverter.entityToVo(entity.getQuestionnaireType()));
        return vo;
    }

    public static EvaluationPeriodEntity toEntity(EvaluationPeriodVO vo) {
        EvaluationPeriodEntity entity = new EvaluationPeriodEntity();
        entity.setId(vo.getId());
        entity.setStartDate(vo.getStartDate());
        entity.setFinishDate(vo.getFinishDate());
        entity.setStatus(vo.getStatus());
        entity.setDescription(vo.getDescription());
        entity.setDeleteState(vo.getDeleteState());
        if (vo.getQuestionnaireType() != null) entity.setQuestionnaireType(QuestionnaireTypeConverter.voToEntity(vo.getQuestionnaireType()));
        return entity;
    }
}

