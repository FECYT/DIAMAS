package es.soltel.recolecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.entity.QuestionnaireToCloseEntity;

public interface QuestionnaireToCloseRepository extends JpaRepository<QuestionnaireToCloseEntity, Long> {
 
    QuestionnaireToCloseEntity findByQuestionnaireId(Long questionnaireId);

    @Modifying
    @Query("DELETE FROM QuestionnaireToCloseEntity qtc WHERE qtc.id = :id")
    void hardDeleteById(@Param("id") Long id);
    
}
