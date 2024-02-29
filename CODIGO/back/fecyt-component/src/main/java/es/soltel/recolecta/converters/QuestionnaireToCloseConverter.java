package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import es.soltel.recolecta.entity.QuestionnaireToCloseEntity;
import es.soltel.recolecta.vo.QuestionnaireToCloseVO;

public class QuestionnaireToCloseConverter {
    
        // Convertir de entidad a VO
    public static QuestionnaireToCloseVO toVO(QuestionnaireToCloseEntity entity) {
        if (entity == null) return null;

        QuestionnaireToCloseVO vo = new QuestionnaireToCloseVO();
        vo.setId(entity.getId());
        vo.setFecha(entity.getFecha());
        vo.setQuestionnaireId(QuestionnaireConverter.entityToVO(entity.getQuestionnaire()));
        
        return vo;
    }

    // Convertir de VO a entidad
    public static QuestionnaireToCloseEntity toEntity(QuestionnaireToCloseVO vo) {
        if (vo == null) return null;

        QuestionnaireToCloseEntity entity = new QuestionnaireToCloseEntity();
        entity.setId(vo.getId());
        entity.setFecha(vo.getFecha());
        entity.setQuestionnaire(QuestionnaireConverter.voToEntity(vo.getQuestionnaire()));
    
        return entity;
    }

    public static List<QuestionnaireToCloseVO> ToVO(List<QuestionnaireToCloseEntity> entities) {
        return entities.stream().map(QuestionnaireToCloseConverter::toVO).collect(Collectors.toList());
    }

    public static List<QuestionnaireToCloseEntity> vosToEntities(List<QuestionnaireToCloseVO> vos) {
        return vos.stream().map(QuestionnaireToCloseConverter::toEntity).collect(Collectors.toList());
    }

}
