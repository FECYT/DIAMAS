package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.QuestionnaireToCloseVO;

public interface QuestionnaireToCloseService {

    List<QuestionnaireToCloseVO> getAll();

    QuestionnaireToCloseVO getById(Long id);

    QuestionnaireToCloseVO create(QuestionnaireToCloseVO vo);

    QuestionnaireToCloseVO update(QuestionnaireToCloseVO vo);

    void delete(Long id);

    QuestionnaireToCloseVO getByQuestionnaireId(Long id);

}
