package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.soltel.recolecta.entity.AssignedEvaluatorsEntity;
import es.soltel.recolecta.entity.EvaluationEntity;

public interface AssignedEvaluatorsRepository extends JpaRepository<AssignedEvaluatorsEntity, Long> {
	List<AssignedEvaluatorsEntity> findByEvaluationId(Long evaluationId);

    @Query("SELECT ae FROM AssignedEvaluatorsEntity ae LEFT JOIN FETCH ae.evaluation LEFT JOIN FETCH ae.user")
    List<AssignedEvaluatorsEntity> findActiveEvaluations();
}
