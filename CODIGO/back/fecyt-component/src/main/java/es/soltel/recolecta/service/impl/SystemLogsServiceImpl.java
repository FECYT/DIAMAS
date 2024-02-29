package es.soltel.recolecta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.entity.SystemLogsEntity;
import es.soltel.recolecta.repository.SystemLogsRepository;
import es.soltel.recolecta.repository.UserRepository;
import es.soltel.recolecta.service.SystemLogsService;
import es.soltel.recolecta.converters.SystemLogsConverter;
import es.soltel.recolecta.vo.SystemLogsVO;

@Service
@Transactional
public class SystemLogsServiceImpl implements SystemLogsService {

    private final SystemLogsRepository systemLogsRepository;
    private final SystemLogsConverter systemLogsConverter;
    //Repositories
    @Autowired
    UserRepository userRepository;

    public SystemLogsServiceImpl(SystemLogsRepository systemLogsRepository, SystemLogsConverter systemLogsConverter) {
        this.systemLogsRepository = systemLogsRepository;
        this.systemLogsConverter = systemLogsConverter;
    }

    @NoLogging
    @Override
    public SystemLogsVO create(SystemLogsVO systemLogsVO) {
        SystemLogsEntity entity = systemLogsConverter.toEntity(systemLogsVO);
        if (systemLogsVO.getUserId() != null) {

        }
        entity.setNDeleteState(1);
        entity = systemLogsRepository.save(entity);
        return systemLogsConverter.toVO(entity);
    }

    @Override
    public SystemLogsVO getById(Long id) {
        SystemLogsEntity entity = systemLogsRepository.findById(id).orElseThrow(null);
        return systemLogsConverter.toVO(entity);
    }

    @Override
    public SystemLogsVO update(Long id, SystemLogsVO systemLogsVO) {
        SystemLogsEntity entity = systemLogsRepository.findById(id).orElseThrow(null);
        systemLogsConverter.updateEntityFromVO(systemLogsVO, entity);
        entity = systemLogsRepository.save(entity);
        return systemLogsConverter.toVO(entity);
    }

    @Override
    public void delete(Long id) {
        SystemLogsEntity entity = systemLogsRepository.findById(id).orElseThrow(null);
        entity.setNDeleteState(2);
        systemLogsRepository.save(entity);
    }

    @Override
    public List<SystemLogsVO> getAll() {
        List<SystemLogsEntity> entities = systemLogsRepository.findAll();
        return systemLogsConverter.toVOList(entities);
    }
}

