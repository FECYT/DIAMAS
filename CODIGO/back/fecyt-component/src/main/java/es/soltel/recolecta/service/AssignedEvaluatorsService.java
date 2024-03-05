package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.AssignedEvaluatorsVO;
import es.soltel.recolecta.vo.UserVO;

public interface AssignedEvaluatorsService {
    List<AssignedEvaluatorsVO> findAll();
    List<AssignedEvaluatorsVO> findActiveEvaluations();
    AssignedEvaluatorsVO findById(Long id);
    List<AssignedEvaluatorsVO> findByEvaluationId(Long id);
    AssignedEvaluatorsVO create(AssignedEvaluatorsVO vo);
}
