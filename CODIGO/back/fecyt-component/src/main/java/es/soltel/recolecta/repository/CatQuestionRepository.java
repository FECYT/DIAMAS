package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.CatQuestionEntity;

@Repository
public interface CatQuestionRepository extends JpaRepository<CatQuestionEntity, Long> {
    CatQuestionEntity findByIdAndNDeleteStateNot(Long id, Integer nDeleteState);

    @Query("SELECT cq FROM CatQuestionEntity cq WHERE cq.questionnaireType.id = :idParam")
    List<CatQuestionEntity> findByQuestionnaireType(@Param("idParam") Long id);
}
