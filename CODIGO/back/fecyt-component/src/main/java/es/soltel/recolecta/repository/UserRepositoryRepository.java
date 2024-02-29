package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.entity.UserRepositoryEntity;

@Repository
public interface UserRepositoryRepository extends JpaRepository<UserRepositoryEntity, Long> {

    // Puedes añadir consultas personalizadas aquí si lo necesitas.
    // Por ejemplo, una consulta que recupere todas las entradas no eliminadas:
    // List<UserRepositoryEntity> findByNDeleteStateNot(Integer state);
    UserRepositoryEntity findByUserId(Long id);

    UserRepositoryEntity findByUser_IdAndRepository_Id(Long userId, Long repositoryId);

    UserRepositoryEntity findByRepository_Id(Long repositoryId);

}
