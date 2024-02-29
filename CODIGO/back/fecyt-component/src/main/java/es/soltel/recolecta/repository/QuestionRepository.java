package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.QuestionEntity;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT q FROM QuestionEntity q WHERE q.periodId.id = :periodId AND q.periodId.questionnaireType.id = q.catQuestion.questionnaireType.id")
    List<QuestionEntity> findByPeriodId(@Param("periodId") Long periodId);

    @Query(value ="SELECT q.id, e.description, e.finish_date, " +
            "t.eng, cq.category_type, " +
            "q.is_writable_by_evaluator, q.type_question, q.weight, " +
            "i.nombre as institucion_nombre, u.nombre as user_nombre, u.apellidos as user_apellidos " +
            "FROM evaluation_period e " +
            "JOIN question q ON q.period_id = e.id " +
            "JOIN cat_questions cq ON q.cat_question_id = cq.id " +
            "JOIN questionnaire_question_entity qqe ON qqe.question_id = q.id " +
            "JOIN questionnaire_answer_entity qae ON qae.questionnaire_question_id = qqe.id " +
            "JOIN questionnaire_entity qe ON qqe.questionnaire_id = qe.id " +
            "JOIN evaluation ev ON qe.evaluation_id = ev.id " +
            "JOIN repository repo ON ev.repository_id = repo.id " +
            "JOIN institucion i ON repo.institucion_id = i.id " +
            "JOIN user_repository ur ON ev.repository_id = ur.repository_id " +
            "JOIN users u ON ur.user_id = u.id " +
		    "JOIN titles t ON q.title_id = t.id " +
            "WHERE YEAR(e.start_date) <= :year " +
            "AND YEAR(e.finish_date) >= :year " +
            "AND qae.file_id IS NOT NULL AND ev.n_delete_state != 2 AND ev.evaluation_state = 'Cerrado' AND ev.questionnaire_type_id = :idType",
            nativeQuery = true)
     List<Object[]> findByYear(@Param("idType") Long idType, @Param("year") Long year);
}

