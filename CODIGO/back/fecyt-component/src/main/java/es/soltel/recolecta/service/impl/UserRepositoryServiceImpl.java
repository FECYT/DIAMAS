package es.soltel.recolecta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.entity.UserRepositoryEntity;
import es.soltel.recolecta.repository.UserRepositoryRepository;
import es.soltel.recolecta.service.UserRepositoryService;
import es.soltel.recolecta.converters.UserRepositoryConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRepositoryServiceImpl implements UserRepositoryService {

    @Autowired
    private UserRepositoryRepository userRepositoryRepository;

    @Autowired
    private UserRepositoryConverter converter;

    @Override
    public List<es.soltel.recolecta.vo.UserRepositoryVO> findAll() {
        return userRepositoryRepository.findAll().stream()
                .filter(entity -> entity.getNDeleteState() != 2)
                .map(UserRepositoryConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public es.soltel.recolecta.vo.UserRepositoryVO findById(Long id) {
        return converter.toVO(userRepositoryRepository.findById(id).orElse(null));
    }

    @Override
    public es.soltel.recolecta.vo.UserRepositoryVO create(es.soltel.recolecta.vo.UserRepositoryVO vo) {
        return converter.toVO(userRepositoryRepository.save(converter.toEntity(vo)));
    }

    @Override
    public es.soltel.recolecta.vo.UserRepositoryVO update(es.soltel.recolecta.vo.UserRepositoryVO vo) {
        return converter.toVO(userRepositoryRepository.save(converter.toEntity(vo)));
    }

    @Override
    public void delete(Long id) {
        UserRepositoryEntity entity = userRepositoryRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setNDeleteState(2);
            userRepositoryRepository.save(entity);
        }
    }

    @Override
    public es.soltel.recolecta.vo.UserRepositoryVO findByUserId(Long id) {
        return UserRepositoryConverter.toVO(userRepositoryRepository.findByUserId(id));
    }

    @Override
    public Boolean doesUserOwnRepository(Long idUser, Long idRepository) {

        // Realiza la consulta para buscar una entidad UserRepositoryEntity con los IDs
        // especificados
        UserRepositoryEntity userRepositoryEntity = userRepositoryRepository.findByUser_IdAndRepository_Id(idUser,
                idRepository);

        // Verifica si se encontró una entidad
        return userRepositoryEntity != null;

    }

    @Override
    public es.soltel.recolecta.vo.UserRepositoryVO findByRepositoryId(Long repositoryId) {
        return UserRepositoryConverter.toVO(userRepositoryRepository.findByRepository_Id(repositoryId));
    }
}
