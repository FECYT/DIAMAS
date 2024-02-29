package es.soltel.recolecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

import es.soltel.recolecta.entity.QuestionnaireEntity;

@Repository
public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity, Long> {

    @Query("SELECT q FROM QuestionnaireEntity q WHERE q.nDeleteState != 2")
    List<QuestionnaireEntity> findAllActive();

    @Query("SELECT q FROM QuestionnaireEntity q WHERE q.id = :id AND q.nDeleteState != 2")
    QuestionnaireEntity findActiveById(@Param("id") Long id);

    List<QuestionnaireEntity> findByPeriodId(Long periodId);

    QuestionnaireEntity findByEvaluationId(Long id);

	@Query("SELECT q FROM QuestionnaireEntity q " +
			"JOIN q.evaluation e " +
			"JOIN e.userRepository ur " +
			"WHERE ur.user.id = :userId AND q.nDeleteState != 2")
	List<QuestionnaireEntity> findByUserId(@Param("userId") Long userId);

	@Query(value = "SELECT " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Respuesta' " +
	        "   ELSE 'Answer' " +
	        "END as answer, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Edición' " +
	        "   ELSE 'Edition' " +
	        "END as description, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Institución' " +
	        "   ELSE 'Institution' " +
	        "END as nombreInstitucion, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Nombre' " +
	        "   ELSE 'Name' " +
	        "END as nombre, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Apellido' " +
	        "   ELSE 'Surname' " +
	        "END as apellidos, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Fecha de inicio' " +
	        "   ELSE 'Start date' " +
	        "END as start_date, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Fecha de finalización' " +
	        "   ELSE 'End date' " +
	        "END as finish_date, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Comentario' " +
	        "   ELSE 'Comment' " +
	        "END as answer_text, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Título' " +
	        "   ELSE 'Title' " +
	        "END as title, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Completado' " +
	        "   ELSE 'Completed' " +
	        "END as negative_extra_point, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Peso' " +
	        "   ELSE 'Weight' " +
	        "END as weight, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Categoría' " +
	        "   ELSE 'Category' " +
	        "END as category_type, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Tipo de pregunta' " +
	        "   ELSE 'Question type' " +
	        "END as type_question " +
	        "UNION " +
	        "SELECT CASE :language " +
	        "   WHEN 'es' THEN CASE WHEN qae.answer = 1 THEN 'Sí' ELSE 'No' END " +
	        "   ELSE CASE WHEN qae.answer = 1 THEN 'Yes' ELSE 'No' END " +
	        "END as answer, " +
	        "ep.description, i.nombre, u.nombre, u.apellidos, " +
	        "DATE_FORMAT(ep.start_date, '%Y-%m-%d') as start_date, " +
	        "DATE_FORMAT(ep.finish_date, '%Y-%m-%d') as finish_date, " +
	        "qae.answer_text, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN t.esp " +
	        "   ELSE t.eng " +
	        "END as eng, " +
	        "CASE " +
	        "   WHEN qae.answer = 1 THEN '100%' " +
	        "   WHEN qae.answer = 0 AND qae.negative_extra_point IS NULL THEN '0%' " +
	        "   WHEN qae.answer = 0 AND qae.negative_extra_point IS NOT NULL THEN CONCAT(qae.negative_extra_point, '%') " +
	        "END as negative_extra_point, " +
	        "q.weight, cq.category_type, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN " +
	        "       CASE " +
	        "           WHEN q.type_question = 'ADVANCED' THEN 'Avanzado' " +
	        "           WHEN q.type_question = 'BASIC' THEN 'Básico' " +
	        "           ELSE q.type_question " +
	        "       END " +
	        "   ELSE " +
	        "       CASE " +
	        "           WHEN q.type_question = 'ADVANCED' THEN 'Advanced' " +
	        "           WHEN q.type_question = 'BASIC' THEN 'Basic' " +
	        "           ELSE q.type_question " +
	        "       END " +
	        "END as type_question " +
	        "FROM questionnaire_entity qe " +
	        "JOIN questionnaire_question_entity qq ON qe.id = qq.questionnaire_id " +
	        "JOIN questionnaire_answer_entity qae ON qq.id = qae.questionnaire_question_id " +
	        "JOIN question q ON qq.question_id = q.id " +
	        "JOIN cat_questions cq ON q.cat_question_id = cq.id " +
	        "JOIN evaluation_period ep ON qe.period_id = ep.id " +
	        "JOIN evaluation e ON qe.evaluation_id = e.id " +
	        "JOIN repository r ON e.repository_id = r.id " +
	        "JOIN user_repository ur ON r.id = ur.repository_id " +
	        "JOIN users u ON ur.user_id = u.id " +
	        "JOIN institucion i ON r.institucion_id = i.id " +
	        "JOIN titles t ON q.title_id = t.id " +
	        "WHERE YEAR(e.close_date) BETWEEN YEAR(:startDate) AND YEAR(:endDate) " +
	        "AND e.evaluation_state = 'Cerrado' AND e.n_delete_state != 2 AND e.questionnaire_type_id = :idType",
	        nativeQuery = true)
	List<Object[]> getQuestionnaireDataInDateRange(
    		@RequestParam("idType") Long idType,
	        @Param("startDate") Date startDate,
	        @Param("endDate") Date endDate,
	        @Param("language") String language);

	@Query(value = "SELECT " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Respuesta' " +
	        "   ELSE 'Answer' " +
	        "END as answer, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Edición' " +
	        "   ELSE 'Edition' " +
	        "END as description, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Institución' " +
	        "   ELSE 'Institution' " +
	        "END as nombreInstitucion, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Nombre' " +
	        "   ELSE 'Name' " +
	        "END as nombre, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Apellido' " +
	        "   ELSE 'Surname' " +
	        "END as apellidos, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Fecha de inicio' " +
	        "   ELSE 'Start date' " +
	        "END as start_date, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Fecha de finalización' " +
	        "   ELSE 'End date' " +
	        "END as finish_date, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Comentario' " +
	        "   ELSE 'Comment' " +
	        "END as answer_text, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Título' " +
	        "   ELSE 'Title' " +
	        "END as title, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Completado' " +
	        "   ELSE 'Completed' " +
	        "END as negative_extra_point, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Peso' " +
	        "   ELSE 'Weight' " +
	        "END as weight, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Categoría' " +
	        "   ELSE 'Category' " +
	        "END as category_type, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN 'Tipo de pregunta' " +
	        "   ELSE 'Question type' " +
	        "END as type_question " +
	        "UNION " +
	        "SELECT CASE :language " +
	        "   WHEN 'es' THEN CASE WHEN qae.answer = 1 THEN 'Sí' ELSE 'No' END " +
	        "   ELSE CASE WHEN qae.answer = 1 THEN 'Yes' ELSE 'No' END " +
	        "END as answer, " +
	        "ep.description, i.nombre, u.nombre, u.apellidos, " +
	        "DATE_FORMAT(ep.start_date, '%Y-%m-%d') as start_date, " +
	        "DATE_FORMAT(ep.finish_date, '%Y-%m-%d') as finish_date, " +
	        "qae.answer_text, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN t.esp " +
	        "   ELSE t.eng " +
	        "END as eng, " +
	        "CASE " +
	        "   WHEN qae.answer = 1 THEN '100%' " +
	        "   WHEN qae.answer = 0 AND qae.negative_extra_point IS NULL THEN '0%' " +
	        "   WHEN qae.answer = 0 AND qae.negative_extra_point IS NOT NULL THEN CONCAT(qae.negative_extra_point, '%') " +
	        "END as negative_extra_point, " +
	        "q.weight, cq.category_type, " +
	        "CASE :language " +
	        "   WHEN 'es' THEN " +
	        "       CASE " +
	        "           WHEN q.type_question = 'ADVANCED' THEN 'Avanzado' " +
	        "           WHEN q.type_question = 'BASIC' THEN 'Básico' " +
	        "           ELSE q.type_question " +
	        "       END " +
	        "   ELSE " +
	        "       CASE " +
	        "           WHEN q.type_question = 'ADVANCED' THEN 'Advanced' " +
	        "           WHEN q.type_question = 'BASIC' THEN 'Basic' " +
	        "           ELSE q.type_question " +
	        "       END " +
	        "END as type_question " +
	        "FROM questionnaire_entity qe " +
	        "JOIN questionnaire_question_entity qq ON qe.id = qq.questionnaire_id " +
	        "JOIN questionnaire_answer_entity qae ON qq.id = qae.questionnaire_question_id " +
	        "JOIN question q ON qq.question_id = q.id " +
	        "JOIN cat_questions cq ON q.cat_question_id = cq.id " +
	        "JOIN evaluation_period ep ON qe.period_id = ep.id " +
	        "JOIN evaluation e ON qe.evaluation_id = e.id " +
	        "JOIN repository r ON e.repository_id = r.id " +
	        "JOIN user_repository ur ON r.id = ur.repository_id " +
	        "JOIN users u ON ur.user_id = u.id " +
	        "JOIN institucion i ON r.institucion_id = i.id " +
	        "JOIN titles t ON q.title_id = t.id " +
	        "WHERE :year BETWEEN YEAR(qe.creation_date) AND YEAR(e.last_edited) " +
	        "AND e.evaluation_state = 'Cerrado' AND e.n_delete_state != 2 AND e.questionnaire_type_id = :idType " +
	        "AND (COALESCE(:nombre, '') = '' OR LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))",
	        nativeQuery = true)
	List<Object[]> getQuestionnaireDataInDateRangeWithFilter(
    		@RequestParam("idType") Long idType,
	        @Param("year") Long year,
	        @Param("nombre") String nombre,
	        @Param("language") String language);
    

	
	/*
	 * 
	 * 
	        "WHERE :year BETWEEN YEAR(qe.creation_date) AND YEAR(e.last_edited) " +
	        "AND (qe.state = 'Enviado' OR qe.state = 'Cerrado') AND qe.delete_state != 2 " +
	        "AND (COALESCE(:nombre, '') = '' OR LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))",
	        nativeQuery = true)
	List<Object[]> getQuestionnaireDataInDateRangeWithFilter(
	        @Param("year") Long year,
	        @Param("nombre") String nombre,
	        @Param("language") String language);
	 */
    
    @Query("SELECT q FROM QuestionnaireEntity q WHERE q.id = :questionnaireId AND q.nDeleteState != 2")
        QuestionnaireEntity getPdfData(Long questionnaireId);


    @Query("SELECT q FROM QuestionnaireEntity q JOIN q.evaluation e WHERE YEAR(e.closeDate) = YEAR(:date) AND e.evaluationState = 'Cerrado' AND e.nDeleteState !=2 AND e.questionnaireType.id = :idType")
    	List<QuestionnaireEntity> findCertificatesByDate(@Param("idType") Long idType, @Param("date") Date date);

/*	@Query("SELECT q FROM QuestionnaireEntity q " +
			"JOIN q.evaluation e " +
			"JOIN e.repository r " +
			"JOIN r.userRepositoryEntities ure " +
			"JOIN ure.user u " +
			"WHERE u.id = :userId AND q.nDeleteState != 2")
	List<QuestionnaireEntity> findByUserId(@Param("userId") Long userId);*/

	@Query("SELECT q FROM QuestionnaireEntity q " +
			"JOIN q.evaluation e " +
			"JOIN e.userRepository ur " +
			"JOIN ur.user u " +
			"WHERE q.period.id = :periodId AND u.id = :userId " +
			"AND e.nDeleteState <> 2 AND e.questionnaireType.id = :idType")
	List<QuestionnaireEntity> findByUserAndPeriodId(
			@Param("idType") Long idType,
			@Param("userId") Long userId,
			@Param("periodId") Long periodId
	);

}
