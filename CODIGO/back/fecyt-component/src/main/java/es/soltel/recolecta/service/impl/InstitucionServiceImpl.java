package es.soltel.recolecta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.netflix.discovery.converters.Auto;
import es.soltel.recolecta.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.converters.InstitucionConverter;
import es.soltel.recolecta.entity.InstitucionEntity;
import es.soltel.recolecta.repository.InstitucionRepository;
import es.soltel.recolecta.service.InstitucionService;
import es.soltel.recolecta.vo.InstitucionVO;

@Service
public class InstitucionServiceImpl implements InstitucionService {

    @Autowired
    private InstitucionRepository repository;

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Override
    public List<InstitucionVO> getAll() {
        return repository.findAllExcludeDeleted().stream().map(InstitucionConverter::convertEntityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public InstitucionVO getById(Long id) {
        return InstitucionConverter.convertEntityToVO(repository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public InstitucionVO create(InstitucionVO vo) {
        return InstitucionConverter.convertEntityToVO(repository.save(InstitucionConverter.convertVOToEntity(vo)));
    }

    @Override
    @Transactional
    public InstitucionVO update(InstitucionVO vo) {
        return InstitucionConverter.convertEntityToVO(repository.save(InstitucionConverter.convertVOToEntity(vo)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        InstitucionEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            entity.setnDeleteState(2);
            repository.save(entity);
        }
    }

    @Override
    public InstitucionVO findByInstitucionName(String nombre) {
        return InstitucionConverter.convertEntityToVO(repository.findByInstitucionName(nombre));
    }

    @Override
    public InstitucionVO findByUserId(Long id) {
        return userRepositoryService.findByUserId(id).getRepository().getInstitucion();
    }

}

