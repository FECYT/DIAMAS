package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.QuestionnaireAnswerEntity;
import es.soltel.recolecta.vo.QuestionnaireAnswerVO;

@Component
public class QuestionnaireAnswerConverter {

    public static QuestionnaireAnswerVO convertToVO(QuestionnaireAnswerEntity entity) {
        if (entity == null) return null;
        QuestionnaireAnswerVO vo = new QuestionnaireAnswerVO();

        vo.setId(entity.getId());
        vo.setQuestionnaireQuestion(QuestionnaireQuestionConverter.toVO(entity.getQuestionnaireQuestion()));
        vo.setObservaciones(entity.getObservaciones());
        vo.setAnswerText(entity.getAnswerText());
        vo.setFile(FileConverter.toVO(entity.getFile()));
        vo.setAnswerDateTime(entity.getAnswerDateTime());
        vo.setNDeleteState(entity.getNDeleteState());
        vo.setAnswer(entity.getAnswer());
        vo.setNegativeExtraPoint(entity.getNegativeExtraPoint());

        return vo;
    }

    public static QuestionnaireAnswerEntity convertToEntity(QuestionnaireAnswerVO vo) {

        if (vo == null) return null;
        QuestionnaireAnswerEntity entity = new QuestionnaireAnswerEntity();

        entity.setId(vo.getId());
        entity.setQuestionnaireQuestion(QuestionnaireQuestionConverter.toEntity(vo.getQuestionnaireQuestion()));
        entity.setObservaciones(vo.getObservaciones());
        entity.setAnswerText(vo.getAnswerText());
        entity.setFile(FileConverter.toEntity(vo.getFile()));
        entity.setAnswerDateTime(vo.getAnswerDateTime());
        entity.setNDeleteState(vo.getNDeleteState());
        entity.setAnswer(vo.getAnswer());
        entity.setNegativeExtraPoint(vo.getNegativeExtraPoint());

        return entity;
    }

    public static List<QuestionnaireAnswerVO> ToVO(List<QuestionnaireAnswerEntity> entities) {
        return entities.stream().map(QuestionnaireAnswerConverter::convertToVO).collect(Collectors.toList());
    }

    public static List<QuestionnaireAnswerEntity> vosToEntities(List<QuestionnaireAnswerVO> vos) {
        return vos.stream().map(QuestionnaireAnswerConverter::convertToEntity).collect(Collectors.toList());
    }
}
