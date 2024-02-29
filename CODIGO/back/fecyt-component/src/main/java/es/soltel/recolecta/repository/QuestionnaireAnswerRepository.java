package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.EvaluationEntity;
import es.soltel.recolecta.entity.QuestionnaireAnswerEntity;
import es.soltel.recolecta.vo.QuestionVO;
import es.soltel.recolecta.vo.QuestionnaireVO;

@Repository
public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswerEntity, Long> {

    @Query("SELECT qa FROM QuestionnaireAnswerEntity qa " +
           "WHERE qa.questionnaireQuestion.question.periodId.id = :periodId")
    List<QuestionnaireAnswerEntity> findByPeriodId(@Param("periodId") Long periodId);

    @Query("SELECT qa FROM QuestionnaireAnswerEntity qa " +
       "WHERE qa.questionnaireQuestion.questionnaire.evaluation.id = :evaluationId")
    List<QuestionnaireAnswerEntity> findByEvaluationId(@Param("evaluationId") Long evaluationId);

        @Query("SELECT qa FROM QuestionnaireAnswerEntity qa " +
           "WHERE qa.questionnaireQuestion.id = :questionnaireQuestionId")
    QuestionnaireAnswerEntity findByQuestionnaireQuestionId(@Param("questionnaireQuestionId") Long questionnaireQuestionId);

    @Query("SELECT qq.id FROM QuestionnaireQuestionEntity qq " +
           "WHERE qq.questionnaire.id = :questionnaireId AND qq.question.id = :questionId")
    Long findIdByQuestionnaireIdAndQuestionId(@Param("questionnaireId") Long questionnaireId,
                                              @Param("questionId") Long questionId);

    @Query("SELECT qa FROM QuestionnaireAnswerEntity qa " +
            "JOIN QuestionnaireQuestionEntity qq ON qa.questionnaireQuestion.id = qq.id " +
            "JOIN QuestionnaireEntity q ON qq.questionnaire.id = q.id " +
            "WHERE q.id = :questionnaireId")
    List<QuestionnaireAnswerEntity> getAnswersForQuestionnaire(@Param("questionnaireId") Long questionnaireId);
                                              

}
