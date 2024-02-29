package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.soltel.recolecta.entity.RepositoryEntity;

public interface RepositoryRepository extends JpaRepository<RepositoryEntity, Long> {


    @Query("SELECT re FROM RepositoryEntity re WHERE re.nDeleteState != 2")
    List<RepositoryEntity> findAllExcludeDeleted();

    @Query("SELECT re FROM RepositoryEntity re " +
            "JOIN UserRepositoryEntity ure ON re.id = ure.repository.id " +
            "JOIN UserEntity ue ON ue.id = ure.user.id " +
            "WHERE ue.id = :userId AND re.nDeleteState != 2")
    List<RepositoryEntity> findRepositoriesByUserId(@Param("userId") Long userId);

}