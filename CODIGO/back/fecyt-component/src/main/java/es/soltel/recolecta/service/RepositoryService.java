package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.RepositoryVO;

public interface RepositoryService {

    List<RepositoryVO> getAll();

    RepositoryVO getById(Long id);

    RepositoryVO create(RepositoryVO vo);

    RepositoryVO update(RepositoryVO vo);

    void delete(Long id);


    RepositoryVO findByUserIdDnet(String idUserDnet);

    RepositoryVO findByRepositoryIdDnet(String idRepositoryDnet);

    RepositoryVO findByUserId(Long idUser);


}

