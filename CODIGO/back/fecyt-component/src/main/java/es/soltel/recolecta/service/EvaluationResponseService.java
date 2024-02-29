package es.soltel.recolecta.service;

import es.soltel.recolecta.vo.EvaluationResponseVO;

import java.util.List;

public interface EvaluationResponseService {
    List<EvaluationResponseVO> findAll();
    EvaluationResponseVO findById(Long id);
    void create(EvaluationResponseVO evaluationResponse);
    void update(EvaluationResponseVO evaluationResponse);
    void delete(Long id);
}
