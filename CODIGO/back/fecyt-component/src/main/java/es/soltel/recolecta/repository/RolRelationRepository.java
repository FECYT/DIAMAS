package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.RolRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRelationRepository extends JpaRepository<RolRelationEntity, Long> {

    List<RolRelationEntity> findByUserId(Long userId);

    List<RolRelationEntity> findAllByRol_IdIn(List<Long> roleIds);


}
