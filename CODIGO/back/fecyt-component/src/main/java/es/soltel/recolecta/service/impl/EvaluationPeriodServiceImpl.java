package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.service.QuestionnaireService;
import es.soltel.recolecta.service.QuestionnaireTypeService;
import es.soltel.recolecta.service.UserRepositoryService;
import es.soltel.recolecta.vo.QuestionnaireVO;
import es.soltel.recolecta.vo.UserRepositoryVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.EvaluationPeriodEntity;
import es.soltel.recolecta.repository.EvaluationPeriodRepository;
import es.soltel.recolecta.service.EvaluationPeriodService;
import es.soltel.recolecta.service.InitialQuestionService;
import es.soltel.recolecta.service.QuestionService;
import es.soltel.recolecta.converters.EvaluationPeriodConverter;
import es.soltel.recolecta.converters.QuestionConverter;
import es.soltel.recolecta.vo.EvaluationPeriodVO;
import es.soltel.recolecta.vo.QuestionVO;
import es.soltel.recolecta.vo.QuestionnaireTypeVO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationPeriodServiceImpl implements EvaluationPeriodService {

    @Autowired
    private EvaluationPeriodRepository repository;

    @Autowired
    private EvaluationPeriodConverter converter;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EvaluationPeriodService evaluationService;

    @Autowired
    private InitialQuestionService initialQuestionService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private QuestionnaireTypeService questionnaireTypeService;

    @Override
    public EvaluationPeriodVO create(EvaluationPeriodVO evaluationPeriod) {

		QuestionnaireTypeVO questionnaireTypeTarget = questionnaireTypeService.getById(evaluationPeriod.getQuestionnaireType().getId());

		evaluationPeriod.setQuestionnaireType(questionnaireTypeTarget);


        EvaluationPeriodEntity entity = converter.toEntity(evaluationPeriod);
        entity = repository.save(entity);
        EvaluationPeriodVO createdEvaluation = converter.fromEntity(entity);

        //Inicializo las preguntas relativas a este periodo
        try{
            //EvaluationPeriodVO priorPeriod = evaluationService.findById(createdEvaluation.getId() - 1);
        	List<EvaluationPeriodEntity> priorPeriodList = repository.findLatestDeletedEvaluationPeriod(entity.getId(), createdEvaluation.getQuestionnaireType().getId());

            if (priorPeriodList != null && !priorPeriodList.isEmpty()) {
                // Si hay resultados en la lista, procesa normalmente
                EvaluationPeriodVO priorPeriod = EvaluationPeriodConverter.fromEntity(priorPeriodList.get(0));
                List<QuestionVO> priorQuestions = questionService.getQuestionsByEvaluationPeriodId(priorPeriod.getId());
                questionService.insertQuestionSet(QuestionConverter.declarePeriodIdInList(priorQuestions, createdEvaluation));
            } else {
                // Si la lista está vacía, ejecuta el bloque catch
                throw new NullPointerException();
            }

        }
        catch(NullPointerException e){
            List<QuestionVO> initialQuestionsConverted = QuestionConverter.initialQuestionsToQuestions(initialQuestionService.findAllWithSameType(createdEvaluation.getQuestionnaireType().getId()),createdEvaluation);

            //List<QuestionVO> initialQuestionsConverted = QuestionConverter.initialQuestionsToQuestions(initialQuestionService.findAll(),createdEvaluation);
            questionService.insertQuestionSet(QuestionConverter.declarePeriodIdInList(initialQuestionsConverted, createdEvaluation));
        }

        return createdEvaluation;
    }

    @Override
    public EvaluationPeriodVO findById(Long id) {
        EvaluationPeriodEntity entity = repository.findByIdAndDeleteStateNot(id, 2);
        return converter.fromEntity(entity);
    }

    @Override
    public List<EvaluationPeriodVO> findByQuestionnaireType(Long id) {
    	List<EvaluationPeriodEntity> entities = repository.findByQuestionnaireType(id);
    	return entities.stream()
                .filter(entity -> entity.getDeleteState() != 2)
                .map(EvaluationPeriodConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluationPeriodVO> findAll() {
        List<EvaluationPeriodEntity> entities = repository.findAll();
        return entities.stream()
                .filter(entity -> entity.getDeleteState() != 2)
                .map(EvaluationPeriodConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EvaluationPeriodVO update(EvaluationPeriodVO evaluationPeriod) {
        EvaluationPeriodEntity entity = converter.toEntity(evaluationPeriod);
        entity = repository.save(entity);
        return converter.fromEntity(entity);
    }

    @Override
    public Boolean delete(Long id) {

        EvaluationPeriodEntity entity = repository.findById(id).orElse(null);

        List<QuestionnaireVO> questionnaires = questionnaireService.findByPeriodId(entity.getId());

        if (questionnaires.isEmpty()){
            if (entity != null) {
                entity.setDeleteState(2);
                repository.save(entity);
            }
            return true;
        } else return false;

    }
}
