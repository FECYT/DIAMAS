package es.soltel.recolecta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.CatQuestionEntity;
import es.soltel.recolecta.entity.QuestionnaireTypeEntity;
import es.soltel.recolecta.repository.CatQuestionRepository;
import es.soltel.recolecta.repository.QuestionnaireTypeRepository;
import es.soltel.recolecta.service.CatQuestionService;
import es.soltel.recolecta.service.QuestionnaireTypeService;
import es.soltel.recolecta.converters.CatQuestionConverter;
import es.soltel.recolecta.converters.QuestionnaireTypeConverter;
import es.soltel.recolecta.vo.CatQuestionVO;
import es.soltel.recolecta.vo.QuestionnaireTypeVO;

@Service
public class QuestionnaireTypeServiceImpl implements QuestionnaireTypeService {

    @Autowired
    private QuestionnaireTypeRepository repository;

    @Autowired
    private QuestionnaireTypeConverter converter;

    @Override
    public List<QuestionnaireTypeVO> getAll() {
        return repository.findAll().stream().map(QuestionnaireTypeConverter::entityToVo).collect(Collectors.toList());
    }

    @Override
    public QuestionnaireTypeVO getById(Long id) {
        return converter.entityToVo(repository.findByIdAndNDeleteStateNot(id, 2));
    }

    @Override
    public QuestionnaireTypeVO create(QuestionnaireTypeVO questionnaireType) {
        QuestionnaireTypeEntity entity = converter.voToEntity(questionnaireType);
        if (entity == null) {
            throw new Error("No existe un tipo de cuestionario relacionada con el id");
        }
        entity = repository.save(entity);
        return converter.entityToVo(entity);
    }

    @Override
    public QuestionnaireTypeVO update(QuestionnaireTypeVO questionnaireType) {
    	QuestionnaireTypeEntity entity = converter.voToEntity(questionnaireType);
        entity = repository.save(entity);
        return converter.entityToVo(entity);
    }

    @Override
    public void delete(Long id) {
    	QuestionnaireTypeEntity entity = repository.findByIdAndNDeleteStateNot(id, 2);
        if (entity == null) {
            throw new Error("No existe una tipo de cuestionario relacionada con el id");
        }
        entity.setNDeleteState(2);
        repository.save(entity);
    }
}
