package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import es.soltel.recolecta.entity.InitialQuestionEntity;

@Repository
public interface InitialQuestionRepository extends JpaRepository<InitialQuestionEntity, Long> {
	@Query("SELECT iq FROM InitialQuestionEntity iq WHERE iq.catQuestion.questionnaireType.id = :idType")
    List<InitialQuestionEntity> findAllWithSameType(@Param("idType") Long idType);
}
