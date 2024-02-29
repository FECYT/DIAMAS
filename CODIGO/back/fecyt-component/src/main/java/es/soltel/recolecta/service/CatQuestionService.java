package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.CatQuestionVO;

public interface CatQuestionService {

    List<CatQuestionVO> getAll();

    CatQuestionVO getById(Long id);
    
    List<CatQuestionVO> findByQuestionnaireType(Long id);

    CatQuestionVO create(CatQuestionVO catQuestions);

    CatQuestionVO update(CatQuestionVO catQuestions);

    void delete(Long id);
}
