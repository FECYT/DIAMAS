package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.EvaluationPeriodEntity;

@Repository
public interface EvaluationPeriodRepository extends JpaRepository<EvaluationPeriodEntity, Long> {

    EvaluationPeriodEntity findByIdAndDeleteStateNot(Long id, Integer deleteState);

    @Query("SELECT ep FROM EvaluationPeriodEntity ep WHERE ep.questionnaireType.id = :idParam")
    List<EvaluationPeriodEntity> findByQuestionnaireType(@Param("idParam") Long id);
    
    @Query("SELECT ep FROM EvaluationPeriodEntity ep WHERE ep.deleteState = 1 AND ep.questionnaireType.id = :idType AND ep.id <> :idParam ORDER BY ep.id DESC")
    List<EvaluationPeriodEntity> findLatestDeletedEvaluationPeriod(@Param("idParam") Long id ,@Param("idType") Long idType);

}

