package es.soltel.recolecta.service;

import java.util.List;
import java.util.Map;

import es.soltel.recolecta.vo.QuestionnaireAnswerVO;
import es.soltel.recolecta.vo.QuestionnaireVO;

public interface QuestionnaireAnswerService {

    List<QuestionnaireAnswerVO> findAll();

    QuestionnaireAnswerVO findById(Long id);

    QuestionnaireAnswerVO create(QuestionnaireAnswerVO questionnaireAnswer);

    QuestionnaireAnswerVO update(QuestionnaireAnswerVO questionnaireAnswer);

    List<QuestionnaireAnswerVO> updateList(List<QuestionnaireAnswerVO> questionnaireAnswer);

    QuestionnaireAnswerVO createQuestionProgress(QuestionnaireAnswerVO questionnaireAnswer);

    List<QuestionnaireAnswerVO> findByPeriodId(Long id);

    List<QuestionnaireAnswerVO> findByEvaluationId(Long id);

    QuestionnaireAnswerVO interactedQuestionnaireAnswer(QuestionnaireAnswerVO questionnaireAnswer, Long questionnaireId);

    Map<Long, List<QuestionnaireAnswerVO>> getAnswersByQuestionnaires(List<QuestionnaireVO> questionnaires);

    void delete(Long id);
}
