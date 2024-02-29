package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.EvaluationActionHistoryVO;

public interface EvaluationActionHistoryService {
    EvaluationActionHistoryVO findById(Long id);
    List<EvaluationActionHistoryVO> findAll();
    List<EvaluationActionHistoryVO> findByEvaluationId(Long id);
    void save(EvaluationActionHistoryVO vo);
    void delete(Long id);
}
