package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.ActionEntity;
import es.soltel.recolecta.entity.ConfigEntity;
import es.soltel.recolecta.vo.ConfigVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Long> {
}
