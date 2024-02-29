package es.soltel.recolecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.soltel.recolecta.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByIdAndNDeleteStateNot(String id, Integer deleteState);

/*    @Query("SELECT u FROM UserEntity u WHERE u.id NOT IN (SELECT ur.user.id FROM RepositoryEntity r JOIN r.userRepositoryEntities ur WHERE r.id = :repositoryId)")
    List<UserEntity> findUsersNotInRepository(@Param("repositoryId") Long repositoryId);*/

    UserEntity findByEmail(String email);

    @Query("SELECT rr.user FROM RolRelationEntity rr JOIN rr.rol r WHERE r.name = :roleName")
    List<UserEntity> findUsersByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN u.userRepositoryEntities ur " +
            "WHERE ur.repository.id = :repoId")
    UserEntity findUserByRepoId(@Param("repoId") Long repoId);
}
