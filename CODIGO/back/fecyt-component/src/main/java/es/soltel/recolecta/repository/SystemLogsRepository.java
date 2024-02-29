package es.soltel.recolecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.SystemLogsEntity;

@Repository
public interface SystemLogsRepository extends JpaRepository<SystemLogsEntity, Long> {
}
