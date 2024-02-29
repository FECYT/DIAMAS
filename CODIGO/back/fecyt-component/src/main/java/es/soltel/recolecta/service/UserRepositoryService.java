package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.UserRepositoryVO;

public interface UserRepositoryService {

    List<UserRepositoryVO> findAll();

    UserRepositoryVO findById(Long id);

    UserRepositoryVO create(UserRepositoryVO vo);

    UserRepositoryVO update(UserRepositoryVO vo);

    void delete(Long id);

    UserRepositoryVO findByUserId(Long id);

    Boolean doesUserOwnRepository(Long idUser, Long idRepository);

    UserRepositoryVO findByRepositoryId(Long repositoryId);
}
