package es.soltel.recolecta.service;

import java.util.List;
import java.util.Set;

import es.soltel.recolecta.entity.QuestionEntity;
import es.soltel.recolecta.vo.QuestionByYearAndFileDTO;
import es.soltel.recolecta.vo.QuestionVO;
import es.soltel.recolecta.vo.QuestionnaireVO;

public interface QuestionService {
    List<QuestionEntity> getAllQuestions();
    QuestionEntity getQuestionById(Long id);
    QuestionVO createQuestion(QuestionVO question);
    QuestionVO updateQuestion(QuestionVO question);
    void deleteQuestion(Long id);
    List<QuestionVO> getQuestionsByEvaluationPeriodId(Long id);
    List<QuestionByYearAndFileDTO> getQuestionsWithFileByYear(Long idType, Long year);
    List<QuestionVO> insertQuestionSet(List<QuestionVO> questions);
    Set<QuestionVO> getQuestionsByEvaluations(List<QuestionnaireVO> questionnaires);


    
}
