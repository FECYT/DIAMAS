package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

	@Query("SELECT f FROM FileEntity f " +
		       "WHERE f.questionnaireQuestion.question.id IN :questionIds AND f.nDeleteState != 2")
		List<FileEntity> findFilesByQuestionIds(@Param("questionIds") Long[] questionIds);

}
