package es.soltel.recolecta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.CatQuestionEntity;
import es.soltel.recolecta.repository.CatQuestionRepository;
import es.soltel.recolecta.service.CatQuestionService;
import es.soltel.recolecta.service.QuestionnaireTypeService;
import es.soltel.recolecta.converters.CatQuestionConverter;
import es.soltel.recolecta.vo.CatQuestionVO;
import es.soltel.recolecta.vo.EvaluationPeriodVO;
import es.soltel.recolecta.vo.QuestionnaireTypeVO;

@Service
public class CatQuestionServiceImpl implements CatQuestionService {

    @Autowired
    private CatQuestionRepository repository;

    @Autowired
    private CatQuestionConverter converter;
    
    @Autowired
    private QuestionnaireTypeService questionnaireTypeService;

    @Override
    public List<CatQuestionVO> getAll() {
        return repository.findAll().stream().map(CatQuestionConverter::entityToVo).collect(Collectors.toList());
    }

    @Override
    public CatQuestionVO getById(Long id) {
        return converter.entityToVo(repository.findByIdAndNDeleteStateNot(id, 2));
    }
    
    @Override
    public List<CatQuestionVO> findByQuestionnaireType(Long id) {
    	List<CatQuestionEntity> entities = repository.findByQuestionnaireType(id);
    	return entities.stream()
                .filter(entity -> entity.getNDeleteState() != 2)
                .map(CatQuestionConverter::entityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public CatQuestionVO create(CatQuestionVO catQuestions) {
    	QuestionnaireTypeVO questionnaireTypeTarget = questionnaireTypeService.getById(catQuestions.getQuestionnaireType().getId());

    	catQuestions.setQuestionnaireType(questionnaireTypeTarget);
    	
        CatQuestionEntity entity = converter.voToEntity(catQuestions);
        if (entity == null) {
            throw new Error("No existe una categoría relacionada con el id");
        }
        entity = repository.save(entity);
        return converter.entityToVo(entity);
    }

    @Override
    public CatQuestionVO update(CatQuestionVO catQuestions) {
        CatQuestionEntity entity = converter.voToEntity(catQuestions);
        entity = repository.save(entity);
        return converter.entityToVo(entity);
    }

    @Override
    public void delete(Long id) {
        CatQuestionEntity entity = repository.findByIdAndNDeleteStateNot(id, 2);
        if (entity == null) {
            throw new Error("No existe una categoría relacionada con el id");
        }
        entity.setNDeleteState(2);
        repository.save(entity);
    }
}
