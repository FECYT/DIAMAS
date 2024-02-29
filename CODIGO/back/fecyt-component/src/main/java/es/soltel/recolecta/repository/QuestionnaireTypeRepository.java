package es.soltel.recolecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.CatQuestionEntity;
import es.soltel.recolecta.entity.QuestionnaireTypeEntity;

@Repository
public interface QuestionnaireTypeRepository extends JpaRepository<QuestionnaireTypeEntity, Long> {
	QuestionnaireTypeEntity findByIdAndNDeleteStateNot(Long id, Integer nDeleteState);

}
