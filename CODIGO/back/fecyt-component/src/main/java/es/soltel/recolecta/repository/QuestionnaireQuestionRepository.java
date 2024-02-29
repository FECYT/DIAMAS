package es.soltel.recolecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.QuestionnaireQuestionEntity;

import java.util.List;

@Repository
public interface QuestionnaireQuestionRepository extends JpaRepository<QuestionnaireQuestionEntity, Long> {

    QuestionnaireQuestionEntity findByQuestionnaire_IdAndQuestion_Id(Long questionnaireId, Long questionId);

    List<QuestionnaireQuestionEntity> findByQuestionnaire_Id(Long questionnaireId);


}
