package es.soltel.recolecta.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.QuestionnaireQuestionEntity;
import es.soltel.recolecta.repository.QuestionnaireQuestionRepository;
import es.soltel.recolecta.service.QuestionnaireQuestionService;
import es.soltel.recolecta.converters.QuestionnaireQuestionConverter;
import es.soltel.recolecta.vo.QuestionnaireQuestionVO;

@Service
public class QuestionnaireQuestionServiceImpl implements QuestionnaireQuestionService {

    @Autowired
    private QuestionnaireQuestionRepository repository;


    @Autowired
    private QuestionnaireQuestionConverter converter;

    @Override
    public QuestionnaireQuestionVO create(QuestionnaireQuestionVO vo) {
        QuestionnaireQuestionEntity entity = converter.toEntity(vo);
        entity = repository.save(entity);
        return converter.toVO(entity);
    }

    @Override
    public QuestionnaireQuestionVO update(QuestionnaireQuestionVO vo) {
        QuestionnaireQuestionEntity entity = converter.toEntity(vo);
        entity.getQuestionnaire().setnDeleteState(0);
        QuestionnaireQuestionEntity updatedEntity = repository.save(entity);
        return converter.toVO(updatedEntity);
    }


    @Override
    public void delete(Long id) {
        QuestionnaireQuestionEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        entity.setNDeleteState(2); // Estado de registro para eliminado lógico
        repository.save(entity);
    }

    @Override
    public List<QuestionnaireQuestionVO> findAll() {
        return repository.findAll().stream().map(QuestionnaireQuestionConverter::toVO).collect(Collectors.toList());
    }

    @Override
    public QuestionnaireQuestionVO findById(Long id) {
        QuestionnaireQuestionEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        return converter.toVO(entity);
    }

    @Override
    public List<QuestionnaireQuestionVO> findAllByQuestionnaireId(Long id) {
        return QuestionnaireQuestionConverter.ToVO(repository.findByQuestionnaire_Id(id));
    }

}
