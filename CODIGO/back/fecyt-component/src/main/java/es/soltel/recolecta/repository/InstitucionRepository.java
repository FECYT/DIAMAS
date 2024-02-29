package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.soltel.recolecta.entity.InstitucionEntity;

public interface InstitucionRepository extends JpaRepository<InstitucionEntity, Long> {


    @Query("SELECT ie FROM InstitucionEntity ie WHERE ie.nDeleteState != 2")
    List<InstitucionEntity> findAllExcludeDeleted();

    @Query("SELECT ie FROM InstitucionEntity ie " +
            "WHERE LOWER(ie.nombre) = LOWER(:nombre)")
    InstitucionEntity findByInstitucionName(@Param("nombre") String nombre);

}