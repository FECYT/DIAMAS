package es.soltel.recolecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.soltel.recolecta.entity.EvaluationActionHistoryEntity;

import java.util.List;
import java.util.Optional;

public interface EvaluationActionHistoryRepository extends JpaRepository<EvaluationActionHistoryEntity, Long> {
    
    @Override
    Optional<EvaluationActionHistoryEntity> findById(Long id);

    List<EvaluationActionHistoryEntity> findByEvaluationId_Id(Long evaluationId);

}
