package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.ActionEntity;
import es.soltel.recolecta.entity.PasswordRecoverEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PasswordRecoverRepository extends JpaRepository<PasswordRecoverEntity, Long> {

    PasswordRecoverEntity findByUserEmail(String userEmail);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM password_recover WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);

}
