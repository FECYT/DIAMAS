package es.soltel.recolecta.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.soltel.recolecta.entity.InitialQuestionEntity;
import es.soltel.recolecta.entity.QuestionEntity;
import es.soltel.recolecta.vo.EvaluationPeriodVO;
import es.soltel.recolecta.vo.QuestionVO;

public class QuestionConverter {
    public static QuestionVO convertToVO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            return null;

        QuestionVO vo = new QuestionVO();

        vo.setId(questionEntity.getId());
        vo.setCatQuestion(CatQuestionConverter.entityToVo(questionEntity.getCatQuestion())); 
        vo.setTitle(TitleConverter.entityToVo(questionEntity.getTitle()));
        vo.setOrder(questionEntity.getOrden());
        vo.setWeight(questionEntity.getWeight());
        vo.setHelpText(questionEntity.getHelpText());
        vo.setIsWritableByEvaluator(questionEntity.getIsWritableByEvaluator());
        vo.setHasUrlText(questionEntity.getHasUrlText());
        vo.setHasFileAttach(questionEntity.getHasFileAttach());
        vo.setnDeleteState(questionEntity.getnDeleteState());
        vo.setType(questionEntity.getTypeQuestion().toString());
        vo.setPeriodId(EvaluationPeriodConverter.fromEntity(questionEntity.getPeriodId()));
        vo.setNegativeMessage(questionEntity.getNegativeMessage());
        vo.setHasNegativeExtraResponse(questionEntity.getHasNegativeExtraResponse());

        return vo;
    }

    public static QuestionEntity convertToEntity(QuestionVO vo) {
        if (vo == null)
            return null;

        QuestionEntity entity = new QuestionEntity();

        entity.setId(vo.getId());
        entity.setCatQuestion(CatQuestionConverter.voToEntity(vo.getCatQuestion())); 
        entity.setTitle(TitleConverter.voToEntity(vo.getTitle()));
        entity.setOrden(vo.getOrden());
        entity.setWeight(vo.getWeight());
        entity.setHelpText(vo.getHelpText());
        entity.setIsWritableByEvaluator(vo.getIsWritableByEvaluator());
        entity.setHasUrlText(vo.getHasUrlText());
        entity.setHasFileAttach(vo.getHasFileAttach());
        entity.setnDeleteState(vo.getnDeleteState());
        entity.setTypeQuestion(QuestionTypeConverter.fromString(vo.getType()));
        entity.setPeriodId(EvaluationPeriodConverter.toEntity(vo.getPeriodId()));
        entity.setNegativeMessage(vo.getNegativeMessage());
        entity.setHasNegativeExtraResponse(vo.getHasNegativeExtraResponse());

        return entity;
    }

    public static List<QuestionVO> ToVO(List<QuestionEntity> entities) {
        return entities.stream().map(QuestionConverter::convertToVO).collect(Collectors.toList());
    }

    public static List<QuestionEntity> vosToEntities(List<QuestionVO> vos) {
        return vos.stream().map(QuestionConverter::convertToEntity).collect(Collectors.toList());
    }

    public static List<QuestionVO> initialQuestionsToQuestions(List<InitialQuestionEntity> initialQuestions, EvaluationPeriodVO period){

        List<QuestionVO> finalList = new ArrayList<>();

        initialQuestions.forEach(initialQuestion -> {
            QuestionVO vo = new QuestionVO();
        
            vo.setId(null);
            vo.setCatQuestion(CatQuestionConverter.entityToVo(initialQuestion.getCatQuestion())); 
            vo.setTitle(TitleConverter.entityToVo(initialQuestion.getTitle()));
            vo.setOrder(initialQuestion.getOrder());
            vo.setWeight(initialQuestion.getWeight().floatValue());
            vo.setHelpText(initialQuestion.getHelpText());
            vo.setIsWritableByEvaluator(initialQuestion.getIsWritableByEvaluator());
            vo.setHasUrlText(initialQuestion.getHasUrlText());
            vo.setHasFileAttach(initialQuestion.getHasFileAttach());
            vo.setnDeleteState(initialQuestion.getNDeleteState());
            vo.setType(initialQuestion.getType());
            vo.setNegativeMessage(initialQuestion.getNegativeMessage());
            vo.setPeriodId(period);
            vo.setHasNegativeExtraResponse(initialQuestion.getHasNegativeExtraResponse());
        
            finalList.add(vo);
        });

        return finalList;

    }

    //Esta función la he hecho para replicar una lista cambiando el id del periodo solo
    public static List<QuestionVO> declarePeriodIdInList(List<QuestionVO> questionList, EvaluationPeriodVO nuevoPeriodo) {
    List<QuestionVO> nuevaQuestionList = new ArrayList<>();

    for (QuestionVO question : questionList) {
        QuestionVO nuevaQuestion = new QuestionVO();
        nuevaQuestion.setId(null);
        nuevaQuestion.setCatQuestion(question.getCatQuestion());
        nuevaQuestion.setTitle(question.getTitle());
        nuevaQuestion.setOrden(question.getOrden());
        nuevaQuestion.setWeight(question.getWeight());
        nuevaQuestion.setHelpText(question.getHelpText());
        nuevaQuestion.setIsWritableByEvaluator(question.getIsWritableByEvaluator());
        nuevaQuestion.setHasUrlText(question.getHasUrlText());
        nuevaQuestion.setHasFileAttach(question.getHasFileAttach());
        nuevaQuestion.setnDeleteState(question.getnDeleteState());
        nuevaQuestion.setType(question.getType());
        nuevaQuestion.setNegativeMessage(question.getNegativeMessage());
        nuevaQuestion.setPeriodId(nuevoPeriodo);
        nuevaQuestion.setHasNegativeExtraResponse(question.getHasNegativeExtraResponse());
        nuevaQuestion.setNegativeExtraPoint(question.getNegativeExtraPoint());

        nuevaQuestionList.add(nuevaQuestion);
    }

    return nuevaQuestionList;
}
}

