package es.soltel.recolecta.repository;

import java.util.Date;
import java.util.Optional;

import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.vo.EvaluationVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.EvaluationEntity;
import es.soltel.recolecta.entity.EvaluationResponseEntity;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
    Optional<EvaluationEntity> findById(Long id);
    
    @Query("SELECT e FROM EvaluationEntity e WHERE e.questionnaireType.id = :idParam")
    List<EvaluationEntity> findByQuestionnaireType(@Param("idParam") Long id);

    @Query("SELECT e FROM EvaluationEntity e WHERE e.userRepository.repository.id = :repoId AND e.closeDate IS NULL")
    List<EvaluationEntity> findByRepositoryIdAndCloseDateIsNull(Long repoId);

    @Query("SELECT e FROM EvaluationEntity e WHERE e.userRepository.repository.id = :repoId AND e.closeDate IS NOT NULL")
    List<EvaluationEntity> findByRepositoryIdAndCloseDateIsNotNull(Long repoId);

    @Query("SELECT e FROM EvaluationEntity e WHERE e.evaluationState = 'Abierto' AND e.closeDate < :currentDate AND e.nDeleteState != 2")
    List<EvaluationEntity> findActiveEvaluationsDatePassed(@Param("currentDate") LocalDateTime currentDate);


    @Query("SELECT e FROM EvaluationEntity e " +
            "JOIN e.questionnaires q " +
            "WHERE q.id = :questionnaireId AND e.nDeleteState != 2 AND e.questionnaireType.id = :idType")
    EvaluationEntity findByQuestionnaireId(@Param("idType") Long idType, @Param("questionnaireId") Long questionnaireId);

    @Query("SELECT e FROM EvaluationEntity e " +
            "WHERE e.userRepository.repository.id = :repositoryId AND e.nDeleteState != 2 AND e.questionnaireType.id = :idType")
    List<EvaluationEntity> findByRepositoryId(@Param("idType") Long idType, @Param("repositoryId") Long repositoryId);

    @Query("SELECT e FROM EvaluationEntity e WHERE e.closeDate BETWEEN :startDate AND :endDate AND e.nDeleteState != 2 AND e.questionnaireType.id = :idType")
    List<EvaluationEntity> findByCloseDateBetween(@Param("idType") Long idType, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e FROM EvaluationEntity e " +
            "WHERE e.userRepository.repository.id = :repositoryId " +
            "AND e.closeDate BETWEEN :startDate AND :endDate AND e.nDeleteState != 2 AND e.questionnaireType.id = :idType")
    List<EvaluationEntity> findByRepositoryIdBetweenDates(
            @Param("idType") Long idType,
            @Param("repositoryId") Long repositoryId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT q FROM QuestionnaireEntity q WHERE q.evaluation.id = :evaluationId")
    QuestionnaireEntity findQuestionnaireByEvaluationId(Long evaluationId);

}
