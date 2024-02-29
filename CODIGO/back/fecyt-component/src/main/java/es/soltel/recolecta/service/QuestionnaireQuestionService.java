package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.QuestionnaireQuestionVO;

public interface QuestionnaireQuestionService {

    QuestionnaireQuestionVO create(QuestionnaireQuestionVO vo);

    QuestionnaireQuestionVO update(QuestionnaireQuestionVO vo);

    void delete(Long id);

    List<QuestionnaireQuestionVO> findAll();

    QuestionnaireQuestionVO findById(Long id);

    List<QuestionnaireQuestionVO> findAllByQuestionnaireId(Long id);
}