package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.converters.EvaluationResponseConverter;
import es.soltel.recolecta.entity.EvaluationResponseEntity;
import es.soltel.recolecta.repository.EvaluationResponseRepository;
import es.soltel.recolecta.service.EvaluationResponseService;
import es.soltel.recolecta.vo.EvaluationResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationResponseServiceImpl implements EvaluationResponseService {

    @Autowired
    private EvaluationResponseRepository repository;

    @Override
    public List<EvaluationResponseVO> findAll() {
        List<EvaluationResponseEntity> entities = repository.findAll();
        return entities.stream()
                       .map(EvaluationResponseConverter::toVO)
                       .collect(Collectors.toList());
    }

    @Override
    public EvaluationResponseVO findById(Long id) {
        Optional<EvaluationResponseEntity> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            return EvaluationResponseConverter.toVO(entityOptional.get());
        } else {
            return null; // Considerar lanzar una excepción si no se encuentra el objeto
        }
    }

    @Override
    public void create(EvaluationResponseVO evaluationResponse) {
        EvaluationResponseEntity entity = EvaluationResponseConverter.toEntity(evaluationResponse);
        repository.save(entity);
    }

    @Override
    public void update(EvaluationResponseVO evaluationResponse) {
        if (evaluationResponse.getId() == null) {
            // Considerar lanzar una excepción si no se proporciona el ID para la actualización
            return;
        }
        EvaluationResponseEntity entity = EvaluationResponseConverter.toEntity(evaluationResponse);
        repository.save(entity); // JPA actualizará el registro si ya tiene un ID existente
    }

    @Override
    public void delete(Long id) {
        Optional<EvaluationResponseEntity> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            EvaluationResponseEntity entity = entityOptional.get();
            entity.setNDeleteState(2); // Borrado lógico
            repository.save(entity);
        } else {
            // Considerar lanzar una excepción si no se encuentra el objeto a eliminar
        }
    }
}

