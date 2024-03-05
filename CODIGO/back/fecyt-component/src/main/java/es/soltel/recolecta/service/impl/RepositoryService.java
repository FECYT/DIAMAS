package es.soltel.recolecta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.RepositoryEntity;
import es.soltel.recolecta.repository.RepositoryRepository;
import es.soltel.recolecta.converters.RepositoryConverter;
import es.soltel.recolecta.vo.RepositoryVO;

@Service
public class RepositoryService implements es.soltel.recolecta.service.RepositoryService {

    @Autowired
    private RepositoryRepository repository;


    @Override
    public List<RepositoryVO> getAll() {
        return repository.findAllExcludeDeleted().stream().map(RepositoryConverter::convertEntityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public RepositoryVO getById(Long id) {
        return RepositoryConverter.convertEntityToVO(repository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public RepositoryVO create(RepositoryVO vo) {
        return RepositoryConverter.convertEntityToVO(repository.save(RepositoryConverter.convertVOToEntity(vo)));
    }

    @Override
    @Transactional
    public RepositoryVO update(RepositoryVO vo) {
        return RepositoryConverter.convertEntityToVO(repository.save(RepositoryConverter.convertVOToEntity(vo)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        RepositoryEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            entity.setnDeleteState(2);
            repository.save(entity);
        }
    }

    @Override
    public RepositoryVO findByUserId(Long idUser) {
        return RepositoryConverter.convertEntityToVO(repository.findRepositoriesByUserId(idUser).get(0));
    }

}

