package es.soltel.recolecta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.EvaluationActionHistoryEntity;
import es.soltel.recolecta.repository.EvaluationActionHistoryRepository;
import es.soltel.recolecta.service.EvaluationActionHistoryService;
import es.soltel.recolecta.converters.EvaluationActionHistoryConverter;
import es.soltel.recolecta.vo.EvaluationActionHistoryVO;

@Service
public class EvaluationActionHistoryServiceImpl implements EvaluationActionHistoryService {

    @Autowired
    private EvaluationActionHistoryRepository repository;

    @Autowired
    private EvaluationActionHistoryConverter converter;

    @Override
    public EvaluationActionHistoryVO findById(Long id) {
        return converter.toVO(repository.findById(id).orElse(null));
    }

    @Override
    public List<EvaluationActionHistoryVO> findAll() {
        return converter.toVOList(repository.findAll());
    }

    @Override
    public void save(EvaluationActionHistoryVO vo) {
        repository.save(converter.toEntity(vo));
    }

    @Override
    public void delete(Long id) {
        EvaluationActionHistoryEntity entity = repository.findById(id).orElse(null);
        if(entity != null) {
            entity.setNDeleteState(2);
            repository.save(entity);
        }
    }

    @Override
    public List<EvaluationActionHistoryVO> findByEvaluationId(Long id) {
        return converter.toVOList(repository.findByEvaluationId_Id(id));
    }
}
