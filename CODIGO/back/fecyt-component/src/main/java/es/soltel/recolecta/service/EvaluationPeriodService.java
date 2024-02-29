package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.EvaluationPeriodVO;

public interface EvaluationPeriodService {

    EvaluationPeriodVO create(EvaluationPeriodVO evaluationPeriod);

    EvaluationPeriodVO findById(Long id);
    
    List<EvaluationPeriodVO> findByQuestionnaireType(Long id);

    List<EvaluationPeriodVO> findAll();

    EvaluationPeriodVO update(EvaluationPeriodVO evaluationPeriod);

    Boolean delete(Long id);
}
