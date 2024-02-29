package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.service.CatQuestionService;
import es.soltel.recolecta.service.QuestionnaireService;
import es.soltel.recolecta.utils.Utils;
import es.soltel.recolecta.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.QuestionnaireAnswerEntity;
import es.soltel.recolecta.repository.QuestionRepository;
import es.soltel.recolecta.repository.QuestionnaireAnswerRepository;
import es.soltel.recolecta.repository.QuestionnaireQuestionRepository;
import es.soltel.recolecta.service.QuestionnaireAnswerService;
import es.soltel.recolecta.converters.QuestionConverter;
import es.soltel.recolecta.converters.QuestionnaireAnswerConverter;
import es.soltel.recolecta.converters.QuestionnaireQuestionConverter;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionnaireAnswerServiceImpl implements QuestionnaireAnswerService {

    @Autowired
    private QuestionnaireAnswerRepository repository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EvaluationServiceImpl evaluationService;

    @Autowired
    private QuestionnaireQuestionServiceImpl questionnaireQuestionService;

    @Autowired
    private QuestionnaireAnswerConverter converter;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private CatQuestionService catQuestionService;

    @Override
    public List<QuestionnaireAnswerVO> findAll() {
        return repository.findAll()
                .stream()
                .map(QuestionnaireAnswerConverter::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionnaireAnswerVO findById(Long id) {
        return converter.convertToVO(repository.findById(id).orElse(null));
    }

    @Override
    public QuestionnaireAnswerVO create(QuestionnaireAnswerVO vo) {
        QuestionnaireAnswerEntity a = converter.convertToEntity(vo);
        QuestionnaireAnswerEntity b = repository.save(a);
        QuestionnaireAnswerVO c = converter.convertToVO(b);

        return c;
    }

    @Override
    public QuestionnaireAnswerVO update(QuestionnaireAnswerVO vo) {

        vo.setAnswerDateTime(LocalDateTime.now(ZoneId.of("Europe/Madrid")));
        QuestionnaireAnswerVO createdAnswer = converter.convertToVO(repository.save(converter.convertToEntity(vo)));

        //Calculo el valor de la nota del cuestionario
        QuestionnaireQuestionVO questionnaire = updateScore(vo);
        
        // Fija la respuesta del updateScore dentro del objeto que devuelve
        createdAnswer.setQuestionnaireQuestion(questionnaire);

        return createdAnswer;
    }

    @Override
    public void delete(Long id) {
        QuestionnaireAnswerEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            entity.setNDeleteState(2);
            repository.save(entity);
        }
    }

    @Override
    public QuestionnaireAnswerVO createQuestionProgress(QuestionnaireAnswerVO questionnaireAnswer) {
        
        QuestionnaireQuestionVO questionnaireReference = QuestionnaireQuestionConverter.toVO(questionnaireQuestionRepository.findByQuestionnaire_IdAndQuestion_Id(questionnaireAnswer.getQuestionnaireQuestion().getQuestionnaire().getId(), questionnaireAnswer.getQuestionnaireQuestion().getQuestion().getId()));
        
        QuestionnaireAnswerVO questionnaireAnswerVO = new QuestionnaireAnswerVO();
        questionnaireAnswerVO.setQuestionnaireQuestion(questionnaireReference);
        questionnaireAnswerVO.setObservaciones(questionnaireAnswer.getObservaciones());
        questionnaireAnswerVO.setAnswerText(questionnaireAnswer.getAnswerText());
        questionnaireAnswerVO.setFile(questionnaireAnswer.getFile());
        questionnaireAnswerVO.setAnswerDateTime(LocalDateTime.now(ZoneId.of("Europe/Madrid")));
        questionnaireAnswerVO.setNDeleteState(1);
        questionnaireAnswerVO.setAnswer(questionnaireAnswer.getAnswer());

        QuestionnaireAnswerVO resultingVO = create(questionnaireAnswerVO);

        //Calculo el valor de la nota del cuestionario
        updateScore(resultingVO);

        return resultingVO;

    }

    public QuestionnaireQuestionVO updateScore(QuestionnaireAnswerVO questionnaireAnswer){

        List<QuestionnaireAnswerVO> allAnswers = QuestionnaireAnswerConverter.ToVO(repository.findByEvaluationId(questionnaireAnswer.getQuestionnaireQuestion().getQuestionnaire().getEvaluation().getId()));
        List<QuestionVO> allQuestions = QuestionConverter.ToVO(questionRepository.findByPeriodId(questionnaireAnswer.getQuestionnaireQuestion().getQuestionnaire().getPeriod().getId()));

        List<QuestionVO> obligatorias = allQuestions.stream()
                .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                .collect(Collectors.toList());

        List<QuestionVO> recomendadas = allQuestions.stream()
                .filter(question -> "basic".equals(question.getType().toLowerCase()))
                .collect(Collectors.toList());

        //Map de id de categorias
        Map<String, Double> weightsMapObligatorias = new HashMap<>();
        Map<String, Double> weightsMapRecomendadas = new HashMap<>();
        Map<String, Double> scoreObligatorias = new HashMap<>();
        Map<String, Double> scoreRecomendadas = new HashMap<>();

        //Primero determino el peso por categoria y tipo
        for (QuestionVO question : allQuestions) {
            if (question.getType().equalsIgnoreCase("advanced")) weightsMapObligatorias.merge(question.getCatQuestion().getId().toString(), question.getWeight().doubleValue(), Double::sum);
            else if (question.getType().equalsIgnoreCase("basic")) weightsMapRecomendadas.merge(question.getCatQuestion().getId().toString(), question.getWeight().doubleValue(), Double::sum);
        }

        // Ordenar la lista según getNegativeExtraPoint()
        allAnswers.sort(Comparator.comparingInt(answer -> answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint() : 0));

        for (QuestionnaireAnswerVO answer : allAnswers) {

            if (answer.getAnswer() != null){

                QuestionVO questionTarget = allQuestions.stream()
                        .filter(question -> question.getId() == answer.getQuestionnaireQuestion().getQuestion().getId())
                        .findFirst()
                        .orElse(null);

                if (answer != null && answer.getNegativeExtraPoint() != null && answer.getNegativeExtraPoint() == -1) {
                    if ("advanced".equalsIgnoreCase(questionTarget.getType())) {

                        weightsMapObligatorias.compute(questionTarget.getCatQuestion().getId().toString(),
                                (key, existingWeight) -> existingWeight - questionTarget.getWeight());

                    } else if ("basic".equalsIgnoreCase(questionTarget.getType())) {
                        weightsMapRecomendadas.compute(questionTarget.getCatQuestion().getId().toString(),
                                (key, existingValue) -> existingValue - questionTarget.getWeight());
                 }

                    continue;
                }

                if (questionTarget.getType().toLowerCase().equals("advanced")) {

                    if (answer.getAnswer()){
                        scoreObligatorias.merge(questionTarget.getCatQuestion().getId().toString(), (questionTarget.getWeight() / weightsMapObligatorias.get(questionTarget.getCatQuestion().getId().toString())) * 100, Double::sum);
                    } else{
                        if (answer.getNegativeExtraPoint()==null) answer.setNegativeExtraPoint(0);
                        scoreObligatorias.merge(questionTarget.getCatQuestion().getId().toString(), (questionTarget.getWeight() * ((double) answer.getNegativeExtraPoint() /100) / weightsMapObligatorias.get(questionTarget.getCatQuestion().getId().toString())) * 100, Double::sum);
                    }

                }
                else if (questionTarget.getType().toLowerCase() == "basic") {

                    if (answer.getAnswer()){
                        scoreRecomendadas.merge(questionTarget.getCatQuestion().getId().toString(), (questionTarget.getWeight() / weightsMapRecomendadas.get(questionTarget.getCatQuestion().getId().toString())) * 100, Double::sum);
                    } else{
                        if (answer.getNegativeExtraPoint()==null) answer.setNegativeExtraPoint(0);
                        scoreRecomendadas.merge(questionTarget.getCatQuestion().getId().toString(), (questionTarget.getWeight() * ((double) answer.getNegativeExtraPoint() /100) / weightsMapObligatorias.get(questionTarget.getCatQuestion().getId().toString()) ) * 100, Double::sum);
                    }
                }

            }

        }

        Map<String, Double> totalScores = new HashMap<>();

        List<CatQuestionVO> totalCat = catQuestionService.findByQuestionnaireType(questionnaireAnswer.getQuestionnaireQuestion().getQuestionnaire().getEvaluation().getQuestionnaireType().getId());

        for (CatQuestionVO catQuestion : totalCat) {

            Double valueMandatory = scoreObligatorias.get(catQuestion.getId().toString()) != null ? scoreObligatorias.get(catQuestion.getId().toString()) : 0.0;
            Double valueRecommended = scoreRecomendadas.get(catQuestion.getId().toString()) != null ? scoreRecomendadas.get(catQuestion.getId().toString()) : 0.0;

            Double valueMandatoryFormatted = valueMandatory * (weightsMapObligatorias.get(catQuestion.getId().toString()) / (weightsMapRecomendadas.get(catQuestion.getId().toString()) + weightsMapObligatorias.get(catQuestion.getId().toString())) );
            Double valueRecommendedFormatted = valueRecommended * (weightsMapRecomendadas.get(catQuestion.getId().toString()) / (weightsMapRecomendadas.get(catQuestion.getId().toString()) + weightsMapObligatorias.get(catQuestion.getId().toString())) );

            totalScores.merge(catQuestion.getId().toString(),(((valueMandatoryFormatted + valueRecommendedFormatted)/totalCat.size())),Double::sum);

        }

        QuestionnaireQuestionVO questionnaireQuestion = questionnaireAnswer.getQuestionnaireQuestion();
        questionnaireQuestion.getQuestionnaire().getEvaluation().setEvaluationGrade((double) Math.round(totalScores.values().stream().mapToDouble(Double::doubleValue).sum()));

        questionnaireQuestionService.update(questionnaireQuestion);
        return questionnaireQuestion;
    }


    @Override
    public List<QuestionnaireAnswerVO> findByPeriodId(Long id) {
        return QuestionnaireAnswerConverter.ToVO(repository.findByPeriodId(id));
    }

    @Override
    public List<QuestionnaireAnswerVO> updateList(List<QuestionnaireAnswerVO> answers) {
        
        List<QuestionnaireAnswerVO> updatedVOs = new ArrayList<>();

        for (QuestionnaireAnswerVO answer : answers) {
            QuestionnaireAnswerEntity entity = converter.convertToEntity(answer);
            QuestionnaireAnswerEntity savedEntity = repository.save(entity);
            updatedVOs.add(converter.convertToVO(savedEntity));
        }

        return updatedVOs;
    }

    @Override
    public List<QuestionnaireAnswerVO> findByEvaluationId(Long id) {
        return QuestionnaireAnswerConverter.ToVO(repository.findByEvaluationId(id));
    }

    @Override
    public QuestionnaireAnswerVO interactedQuestionnaireAnswer(QuestionnaireAnswerVO questionnaireAnswer, Long questionnaireId) {

        QuestionnaireVO questionnaireVO = questionnaireService.findActiveById(questionnaireId);
        if (!Objects.equals(questionnaireVO.getEvaluation().getEvaluationState(), "Enviado")) {
            questionnaireVO.getEvaluation().setEvaluationState("Enviado");
            questionnaireService.update(questionnaireVO);
        }

        Long questionnaireQuestionId = repository.findIdByQuestionnaireIdAndQuestionId(questionnaireAnswer.getQuestionnaireQuestion().getQuestionnaire().getId(), questionnaireAnswer.getQuestionnaireQuestion().getQuestion().getId());
        questionnaireAnswer.getQuestionnaireQuestion().setId(questionnaireQuestionId);
        QuestionnaireAnswerEntity registry = repository.findByQuestionnaireQuestionId(questionnaireAnswer.getQuestionnaireQuestion().getId());

        try {
            if (registry == null) {
                return createQuestionProgress(questionnaireAnswer);
            } else {
                questionnaireAnswer.setId(registry.getId());
                return update(questionnaireAnswer);
            }
        } catch (DataIntegrityViolationException e) {
            registry = repository.findByQuestionnaireQuestionId(questionnaireAnswer.getQuestionnaireQuestion().getId());
            questionnaireAnswer.setId(registry.getId());
            return update(questionnaireAnswer);
        }
    }

    @Override
    public Map<Long, List<QuestionnaireAnswerVO>> getAnswersByQuestionnaires(List<QuestionnaireVO> questionnaires) {

        Map<Long, List<QuestionnaireAnswerVO>> resultMap = new HashMap<>();

        for (QuestionnaireVO questionnaire : questionnaires) {
            // Obtén las respuestas para cada cuestionario (esto debe ser adaptado según tu lógica)
            List<QuestionnaireAnswerVO> answersForQuestionnaire = QuestionnaireAnswerConverter.ToVO(repository.getAnswersForQuestionnaire(questionnaire.getId()));

            // Asocia el ID del cuestionario con la lista de respuestas en el mapa
            resultMap.put(questionnaire.getId(), answersForQuestionnaire);
        }

        return resultMap;
    }

}
