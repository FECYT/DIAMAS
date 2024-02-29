package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.QuestionnaireTypeVO;

public interface QuestionnaireTypeService {

    List<QuestionnaireTypeVO> getAll();

    QuestionnaireTypeVO getById(Long id);

    QuestionnaireTypeVO create(QuestionnaireTypeVO questionnaireType);

    QuestionnaireTypeVO update(QuestionnaireTypeVO questionnaireType);

    void delete(Long id);
}
