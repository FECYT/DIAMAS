package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.RolEntity;
import es.soltel.recolecta.entity.RolRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {

    List<RolEntity> findAllByIdIn(List<Long> ids);

}
