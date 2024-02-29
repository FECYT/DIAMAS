package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import es.soltel.recolecta.entity.EvaluationResponseEntity;
import es.soltel.recolecta.vo.EvaluationResponseVO;

public class EvaluationResponseConverter {

    public static EvaluationResponseVO toVO(EvaluationResponseEntity entity) {
        if (entity == null) {
            return null;
        }

        EvaluationResponseVO vo = new EvaluationResponseVO();
        vo.setId(entity.getId());
        vo.setEvaluatorCommentary(entity.getEvaluatorCommentary());
        vo.setLastEdited(entity.getLastEdited());
        vo.setNDeleteState(entity.getNDeleteState());
        vo.setQuestionnaireQuestion(QuestionnaireQuestionConverter.toVO(entity.getQuestionnaireQuestion()));
        
        // Aquí, es posible que también necesites convertir la relación "questionnaireQuestion" 
        // a un formato adecuado para el VO, pero por simplicidad, lo he omitido.

        return vo;
    }

    public static EvaluationResponseEntity toEntity(EvaluationResponseVO vo) {
        if (vo == null) {
            return null;
        }

        EvaluationResponseEntity entity = new EvaluationResponseEntity();
        entity.setId(vo.getId());
        entity.setEvaluatorCommentary(vo.getEvaluatorCommentary());
        entity.setLastEdited(vo.getLastEdited());
        entity.setNDeleteState(vo.getNDeleteState());
        entity.setQuestionnaireQuestion(QuestionnaireQuestionConverter.toEntity(vo.getQuestionnaireQuestion()));

        // Al igual que con la conversión a VO, aquí también es posible que necesites manejar 
        // la conversión del campo "questionnaireQuestion", pero lo he omitido para simplificar.

        return entity;
    }

    public static List<EvaluationResponseVO> ToVO(List<EvaluationResponseEntity> entities) {
        return entities.stream().map(EvaluationResponseConverter::toVO).collect(Collectors.toList());
    }

    public static List<EvaluationResponseEntity> vosToEntities(List<EvaluationResponseVO> vos) {
        return vos.stream().map(EvaluationResponseConverter::toEntity).collect(Collectors.toList());
    }
}
