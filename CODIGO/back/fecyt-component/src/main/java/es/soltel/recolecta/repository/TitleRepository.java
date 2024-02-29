package es.soltel.recolecta.repository;

import es.soltel.recolecta.entity.TitleEntity;
import es.soltel.recolecta.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<TitleEntity, Long> {

}
