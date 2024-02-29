package es.soltel.recolecta.converters;


import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.QuestionnaireQuestionEntity;
import es.soltel.recolecta.vo.QuestionnaireQuestionVO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionnaireQuestionConverter {

    // Convertir de entidad a VO
    public static QuestionnaireQuestionVO toVO(QuestionnaireQuestionEntity entity) {
        if (entity == null) return null;

        QuestionnaireQuestionVO vo = new QuestionnaireQuestionVO();
        vo.setId(entity.getId());
        
        vo.setQuestionnaire(QuestionnaireConverter.entityToVO(entity.getQuestionnaire()));
        vo.setQuestion(QuestionConverter.convertToVO(entity.getQuestion()));
        
        vo.setNDeleteState(entity.getNDeleteState());
        
        return vo;
    }

    // Convertir de VO a entidad
    public static QuestionnaireQuestionEntity toEntity(QuestionnaireQuestionVO vo) {
        if (vo == null) return null;

        QuestionnaireQuestionEntity entity = new QuestionnaireQuestionEntity();
        entity.setId(vo.getId());

        entity.setQuestionnaire(QuestionnaireConverter.voToEntity(vo.getQuestionnaire()));
        entity.setQuestion(QuestionConverter.convertToEntity(vo.getQuestion()));
        
        entity.setNDeleteState(vo.getNDeleteState());
        
        return entity;
    }

    public static List<QuestionnaireQuestionVO> ToVO(List<QuestionnaireQuestionEntity> entities) {
        return entities.stream().map(QuestionnaireQuestionConverter::toVO).collect(Collectors.toList());
    }

    public static List<QuestionnaireQuestionEntity> vosToEntities(List<QuestionnaireQuestionVO> vos) {
        return vos.stream().map(QuestionnaireQuestionConverter::toEntity).collect(Collectors.toList());
    }
}
