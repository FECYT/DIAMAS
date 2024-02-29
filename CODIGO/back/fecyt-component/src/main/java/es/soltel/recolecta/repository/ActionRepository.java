package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
    List<ActionEntity> findBynDeleteState(Integer nDeleteState);
}
