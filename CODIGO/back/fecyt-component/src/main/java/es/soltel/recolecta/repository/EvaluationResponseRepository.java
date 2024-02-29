package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.EvaluationResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationResponseRepository extends JpaRepository<EvaluationResponseEntity, Long> {
    // No need to add basic CRUD methods, JpaRepository already includes them!
}
