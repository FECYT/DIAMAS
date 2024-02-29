package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.vo.QuestionnaireVO;

@Component
public class QuestionnaireConverter {

    public static QuestionnaireVO entityToVO(QuestionnaireEntity entity) {
        QuestionnaireVO vo = new QuestionnaireVO();
        vo.setId(entity.getId());
        if (entity.getEvaluation() != null) vo.setEvaluation(EvaluationConverter.entityToVO(entity.getEvaluation()));
        vo.setState(entity.getState());
        vo.setCreationDate(entity.getCreationDate());
        if (entity.getPeriod() != null) vo.setPeriod(EvaluationPeriodConverter.fromEntity(entity.getPeriod())); 
        vo.setnDeleteState(entity.getnDeleteState());
    
        return vo;
    }

    public static QuestionnaireEntity voToEntity(QuestionnaireVO vo) {
        QuestionnaireEntity entity = new QuestionnaireEntity();
        entity.setId(vo.getId());
        if (vo.getEvaluation() != null) entity.setEvaluation(EvaluationConverter.ToEntity(vo.getEvaluation()));
        entity.setState(vo.getState());
        entity.setCreationDate(vo.getCreationDate());
        if (vo.getPeriod() != null) entity.setPeriod(EvaluationPeriodConverter.toEntity(vo.getPeriod()));
        entity.setnDeleteState(vo.getnDeleteState());
        return entity;
    }

    public static List<QuestionnaireVO> ToVO(List<QuestionnaireEntity> entities) {
        return entities.stream().map(QuestionnaireConverter::entityToVO).collect(Collectors.toList());
    }

    public static List<QuestionnaireEntity> vosToEntities(List<QuestionnaireVO> vos) {
        return vos.stream().map(QuestionnaireConverter::voToEntity).collect(Collectors.toList());
    }
}


