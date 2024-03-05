package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.entity.CatQuestionEntity;
import es.soltel.recolecta.repository.CatQuestionRepository;
import es.soltel.recolecta.service.*;
import es.soltel.recolecta.utils.Utils;
import es.soltel.recolecta.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionnaireAnswerService questionnaireAnswerService;

    @Autowired
    private QuestionnaireQuestionService questionnaireQuestionService;
    
    @Autowired
    private CatQuestionRepository catQuestionRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public StatsVO getPuntuacionMediaGeneral(Long idType, String fechaInicio, String fechaFin) {

        List<EvaluationVO> evaluations = evaluationService.findEvaluationsBetweenDates(idType, LocalDateTime.parse(fechaInicio, formatter),LocalDateTime.parse(fechaFin, formatter));

        double sumaEvaluationGrade = evaluations.stream()
                .mapToDouble(EvaluationVO::getEvaluationGrade)
                .sum();

        double mediaEvaluationGrade = sumaEvaluationGrade / evaluations.size();

        StatsVO data = new StatsVO();
        data.setPuntuacion(mediaEvaluationGrade);
        data.setCategoria("Calificación general");

        return data;
    }
    @Override
    public StatsVO getStatByCategoria(Long idType, String categoria, String fechaInicio, String fechaFin) {

        if (categoria.toLowerCase() == "logs" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        //Obtenemos las evaluaciones
        List<EvaluationVO> evaluations = evaluationService.findEvaluationsBetweenDates(idType, LocalDateTime.parse(fechaInicio, formatter), LocalDateTime.parse(fechaFin, formatter));

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();

        for (EvaluationVO evaluation : evaluations) {
            cuestionarios.add(questionnaireService.findByEvaluationId(evaluation.getId()));
        }

        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            Double totalScoreObligatorias = 0.0;
            Double totalWeightObligatorias = 0.0;

            Double totalScoreRecomendadas = 0.0;
            Double totalWeightRecomendadas = 0.0;

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null  && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (questionTarget.getType().equalsIgnoreCase("advanced") && answer.getAnswer()) totalScoreObligatorias += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("advanced")) totalScoreObligatorias += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScoreRecomendadas += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("basic")) totalScoreRecomendadas += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;

                }

            }
            Double valueMandatoryFormatted = totalScoreObligatorias * (totalWeightObligatorias / (totalWeightRecomendadas + totalWeightObligatorias ));
            Double valueRecommendedFormatted = totalScoreRecomendadas * (totalWeightRecomendadas / (totalWeightRecomendadas + totalWeightObligatorias ));

            resultadosNotas.add(valueMandatoryFormatted + valueRecommendedFormatted);

            // resultadosNotas.add(totalScoreObligatorias + totalScoreRecomendadas);
        }

        StatsVO stats = new StatsVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));

        return stats;
    }

    @Override
    public StatsVO getPuntuacionMediaGeneralByRepositoryId(Long idType, Long id, String fechaInicio, String fechaFin) {

        List<EvaluationVO> evaluations = evaluationService.findByRepositoryIdBetweenDates(idType, id,LocalDateTime.parse(fechaInicio, formatter),LocalDateTime.parse(fechaFin, formatter));

        double sumaEvaluationGrade = evaluations.stream()
                .mapToDouble(EvaluationVO::getEvaluationGrade)
                .sum();

        double mediaEvaluationGrade = sumaEvaluationGrade / evaluations.size();

        StatsVO data = new StatsVO();
        data.setPuntuacion(mediaEvaluationGrade);
        data.setCategoria("Calificación general");

        return data;
    }

    @Override
    public List<StatsVO> getStatsByCategoria(Long idType, String fechaInicio, String fechaFin) {

    	List<StatsVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatByCategoria(idType, categoryType, fechaInicio, fechaFin));
        }

        return categorias;
    }

    @Override
    public StatsVO getStatByCategoriaAndRepositoryId(Long idType, String categoria, Long id, String fechaInicio, String fechaFin) {

        if (categoria.toLowerCase() == "log" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        //Obtenemos las evaluaciones
        List<EvaluationVO> evaluations = evaluationService.findByRepositoryIdBetweenDates(idType, id,LocalDateTime.parse(fechaInicio, formatter),LocalDateTime.parse(fechaFin, formatter));

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();

        for (EvaluationVO evaluation : evaluations) {
            cuestionarios.add(questionnaireService.findByEvaluationId(evaluation.getId()));
        }

        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            Double totalScoreObligatorias = 0.0;
            Double totalWeightObligatorias = 0.0;

            Double totalScoreRecomendadas = 0.0;
            Double totalWeightRecomendadas = 0.0;

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (questionTarget.getType().equalsIgnoreCase("advanced") && answer.getAnswer()) totalScoreObligatorias += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("advanced")) totalScoreObligatorias += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * ((double) answer.getNegativeExtraPoint() /100) * result;
                    else if (questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScoreRecomendadas += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("basic")) totalScoreRecomendadas += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;
                }

            }
            Double valueMandatoryFormatted = totalScoreObligatorias * (totalWeightObligatorias / (totalWeightRecomendadas + totalWeightObligatorias ));
            Double valueRecommendedFormatted = totalScoreRecomendadas * (totalWeightRecomendadas / (totalWeightRecomendadas + totalWeightObligatorias ));

            resultadosNotas.add(valueMandatoryFormatted + valueRecommendedFormatted);

            // resultadosNotas.add(totalScoreObligatorias + totalScoreRecomendadas);
        }

        StatsVO stats = new StatsVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));

        return stats;
    }
    
    @Override
    public StatsDividedVO getStatByRepositoryId(Long idType, Boolean obligatoriasToggle, String categoria, Long id) {

        if (categoria.toLowerCase() == "log" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        //Obtenemos las evaluaciones
        // List<EvaluationVO> evaluations = evaluationService.findByRepositoryIdBetweenDates(id,LocalDateTime.parse(fechaInicio, formatter),LocalDateTime.parse(fechaFin, formatter));
        List<EvaluationVO> evaluations = evaluationService.findByRepositoryId(idType, id);

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();

        for (EvaluationVO evaluation : evaluations) {
            cuestionarios.add(questionnaireService.findByEvaluationId(evaluation.getId()));
        }

        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());


            Double totalWeightObligatorias = 0.0;
            Double totalWeightRecomendadas = 0.0;
            Double totalScore = 0.0;

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (obligatoriasToggle && answer.getAnswer() && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic")) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;

                }

            }
            resultadosNotas.add(totalScore);
        }

        StatsDividedVO stats = new StatsDividedVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));
        if (obligatoriasToggle) stats.setType("Advanced");
        else stats.setType("Basic");

        return stats;
    }
    
    @Override
    public StatsDividedVO getStatByEvaluationId(Long idType, String categoria, Boolean obligatoriasToggle, Long evaluationId) throws Exception {

        if (categoria.toLowerCase() == "log" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        //Obtenemos la evaluacion
        EvaluationVO evaluation = evaluationService.findById(evaluationId);

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();


        cuestionarios.add(questionnaireService.findByEvaluationId(evaluation.getId()));


        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());


            Double totalWeightObligatorias = 0.0;
            Double totalWeightRecomendadas = 0.0;
            Double totalScore = 0.0;
            

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (obligatoriasToggle && answer.getAnswer() && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic")) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;

                }

            }
            resultadosNotas.add(totalScore);
        }

        StatsDividedVO stats = new StatsDividedVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));
        if (obligatoriasToggle) stats.setType("Advanced");
        else stats.setType("Basic");

        return stats;
    }

    @Override
    public StatsDividedVO getStatsByCategoriaDivide(Long idType, String categoria, Boolean obligatoriasToggle, String fechaInicio, String fechaFin) {

        if (categoria.toLowerCase() == "log" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        //Obtenemos las evaluaciones
        List<EvaluationVO> evaluations = evaluationService.findEvaluationsBetweenDates(idType, LocalDateTime.parse(fechaInicio, formatter), LocalDateTime.parse(fechaFin, formatter));

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();

        for (EvaluationVO evaluation : evaluations) {
            cuestionarios.add(questionnaireService.findByEvaluationId(evaluation.getId()));
        }

        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());


            Double totalWeightObligatorias = 0.0;
            Double totalWeightRecomendadas = 0.0;
            Double totalScore = 0.0;

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (obligatoriasToggle && answer.getAnswer() && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic")) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;

                }

            }
            resultadosNotas.add(totalScore);
        }

        StatsDividedVO stats = new StatsDividedVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));
        if (obligatoriasToggle) stats.setType("Advanced");
        else stats.setType("Basic");

        return stats;
    }

    @Override
    public List<StatsDividedVO> getStatsByCategoriasDivididas(Long idType, String fechaInicio, String fechaFin) {

    	List<StatsDividedVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatsByCategoriaDivide(idType, categoryType, true, fechaInicio, fechaFin));
            categorias.add(getStatsByCategoriaDivide(idType, categoryType, false, fechaInicio, fechaFin));
        }

        return categorias;
    }

    @Override
    public List<StatsDividedVO> getStatsByCategoriasDivididasByRepositoryId(Long idType, Long id, String fechaInicio, String fechaFin) {

    	List<StatsDividedVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatsByCategoriaDivideByRepositoryId(idType, categoryType, true, id, fechaInicio, fechaFin));
            categorias.add(getStatsByCategoriaDivideByRepositoryId(idType, categoryType, false, id, fechaInicio, fechaFin));
        }

        return categorias;
    }

    @Override
    public StatsDividedVO getStatsByCategoriaDivideByRepositoryId(Long idType, String categoria, Boolean obligatoriasToggle, Long repositoryId, String fechaInicio, String fechaFin) {

        if (categoria.toLowerCase() == "log" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        //Obtenemos las evaluaciones
        List<EvaluationVO> evaluations = evaluationService.findByRepositoryIdBetweenDates(idType, repositoryId,LocalDateTime.parse(fechaInicio, formatter),LocalDateTime.parse(fechaFin, formatter));

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();

        for (EvaluationVO evaluation : evaluations) {
            cuestionarios.add(questionnaireService.findByEvaluationId(evaluation.getId()));
        }

        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());


            Double totalWeightObligatorias = 0.0;
            Double totalWeightRecomendadas = 0.0;
            Double totalScore = 0.0;

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (obligatoriasToggle && answer.getAnswer() && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("advanced") && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic")) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;

                }

            }
            resultadosNotas.add(totalScore);
        }

        StatsDividedVO stats = new StatsDividedVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));
        if (obligatoriasToggle) stats.setType("Advanced");
        else stats.setType("Basic");

        return stats;
    }

    @Override
    public StatsVO getPuntuacionMediaGeneralByQuestionnaireId(Long idType, Long questionnaireId) {
        EvaluationVO evaluations = evaluationService.findByQuestionnaireId(idType, questionnaireId);

        StatsVO data = new StatsVO();
        data.setPuntuacion(evaluations.getEvaluationGrade());
        data.setCategoria("Calificación general");

        return data;
    }

    //TODO FIX
    @Override
    public StatsVO getStatByCategoriaAndQuestionnaireId(Long idType, String categoria, Long questionnaireId) {

        if (categoria.equalsIgnoreCase("log") || categoria.equalsIgnoreCase("estadisticas")) categoria = "Logs / estadisticas";

        //Obtenemos las evaluaciones
        EvaluationVO evaluation = evaluationService.findByQuestionnaireId(idType, questionnaireId);

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();

        cuestionarios.add(questionnaireService.findByEvaluationId(evaluation.getId()));

        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            Double totalScoreObligatorias = 0.0;
            Double totalWeightObligatorias = 0.0;

            Double totalScoreRecomendadas = 0.0;
            Double totalWeightRecomendadas = 0.0;

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (questionTarget.getType().equalsIgnoreCase("advanced") && answer.getAnswer()) totalScoreObligatorias += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("advanced") && questionTarget.getType().equalsIgnoreCase("advanced")) totalScoreObligatorias += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScoreRecomendadas += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("basic")) totalScoreRecomendadas += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;

                }

            }

            Double valueMandatoryFormatted = totalScoreObligatorias * (totalWeightObligatorias / (totalWeightRecomendadas + totalWeightObligatorias ));
            Double valueRecommendedFormatted = totalScoreRecomendadas * (totalWeightRecomendadas / (totalWeightRecomendadas + totalWeightObligatorias ));

            resultadosNotas.add(valueMandatoryFormatted + valueRecommendedFormatted);

        }

        StatsVO stats = new StatsVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));

        return stats;
    }

    @Override
    public List<StatsDividedVO> getStatsByCategoriasDivididasByQuestionnaireId(Long idType, Long questionnaireId) {

    	List<StatsDividedVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatsByQuestionnaireDivideByRepositoryId(idType, categoryType, true, questionnaireId));
            categorias.add(getStatsByQuestionnaireDivideByRepositoryId(idType, categoryType, false, questionnaireId));
        }

        return categorias;
    }



    @Override
    public StatsDividedVO getStatsByQuestionnaireDivideByRepositoryId(Long idType, String categoria, Boolean obligatoriasToggle, Long questionnaireId) {

        if (categoria.toLowerCase() == "log" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        //Obtenemos las evaluaciones
        EvaluationVO evaluations = evaluationService.findByQuestionnaireId(idType, questionnaireId);

        //Ahora los cuestionarios de los que provienen
        List<QuestionnaireVO> cuestionarios = new ArrayList<>();


        cuestionarios.add(questionnaireService.findByEvaluationId(evaluations.getId()));


        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());


            Double totalWeightObligatorias = 0.0;
            Double totalWeightRecomendadas = 0.0;
            Double totalScore = 0.0;
            

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (obligatoriasToggle && answer.getAnswer() && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (!obligatoriasToggle && questionTarget.getType().equalsIgnoreCase("basic")) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;

                }

            }
            resultadosNotas.add(totalScore);
        }

        StatsDividedVO stats = new StatsDividedVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));
        if (obligatoriasToggle) stats.setType("Advanced");
        else stats.setType("Basic");

        return stats;
    }

    @Override
    public List<StatsVO> getStatsByCategoriaAndQuestionnaireId(Long idType, Long questionnaireId) {

    	List<StatsVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatByCategoriaAndQuestionnaireId(idType, categoryType, questionnaireId));
        }

        return categorias;
    }

    @Override
    public List<StatsVO> getStatsByCategoriaAndRepositoryId(Long idType, Long repositoryId, String fechaInicio, String fechaFin) {

    	List<StatsVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatByCategoriaAndRepositoryId(idType, categoryType, repositoryId, fechaInicio, fechaFin));
        }

        return categorias;
    }
    
    
    

    @Override
    public List<StatsDividedVO> getStatsByRepositoryId(Long idType, Long repositoryId) {

    	List<StatsDividedVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatByRepositoryId(idType,true, categoryType, repositoryId));
            categorias.add(getStatByRepositoryId(idType,false, categoryType, repositoryId));
        }

        return categorias;

    }
    
    @Override
    public List<StatsDividedVO> getStatsByEvaluationId(Long idType, Long evaluationId) throws Exception {

    	List<StatsDividedVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatByEvaluationId(idType, categoryType, true, evaluationId));
            categorias.add(getStatByEvaluationId(idType, categoryType, false, evaluationId));
        }

        return categorias;

    }

    @Override
    public List<StatsDividedVO> getStatsByUserAndPeriodId(Long idType, Long periodId, Long userId) {

    	List<StatsDividedVO> categorias = new ArrayList<>();
        List<CatQuestionEntity> entities = catQuestionRepository.findByQuestionnaireType(idType);

        for (CatQuestionEntity entity : entities) {
            String categoryType = entity.getCategoryType();
            categorias.add(getStatByUserAndPeriod(idType, categoryType, periodId, userId));
        }

        StatsVO general = new StatsVO();
        StatsDividedVO generalFormatted = new StatsDividedVO();

        List<QuestionnaireVO> cuestionarios = questionnaireService.findByUserAndPeriodId(idType, userId, periodId);

        if (!cuestionarios.isEmpty()) {
            QuestionnaireVO ultimoCuestionario = cuestionarios.get(cuestionarios.size() - 1);
            general = getPuntuacionMediaGeneralByQuestionnaireId(idType, ultimoCuestionario.getId());

            generalFormatted.setCategoria("GENERAL");
            generalFormatted.setPuntuacion(general.getPuntuacion());

            categorias.add(generalFormatted);
        } else {
            generalFormatted.setCategoria("GENERAL");
            generalFormatted.setPuntuacion(0);

            categorias.add(generalFormatted);
        }

        return categorias;
    }

    public StatsDividedVO getStatByUserAndPeriod(Long idType, String categoria, Long periodId, Long userId) {

        if (categoria.toLowerCase() == "log" || categoria.toLowerCase() == "estadisticas") categoria = "Logs / estadisticas";

        List<QuestionnaireVO> cuestionarios = questionnaireService.findByUserAndPeriodId(idType, userId, periodId);
        if (!cuestionarios.isEmpty()) {
            QuestionnaireVO ultimoCuestionario = cuestionarios.get(cuestionarios.size() - 1);
            cuestionarios = new ArrayList<>();
            cuestionarios.add(ultimoCuestionario);
        }


        //Obtenemos las preguntas y sus respuestas
        Set<QuestionVO> questionsList = questionService.getQuestionsByEvaluations(cuestionarios);
        Map<Long,List<QuestionnaireAnswerVO>> respuestasList = questionnaireAnswerService.getAnswersByQuestionnaires(cuestionarios);

        List<Double> resultadosNotas = new ArrayList<>();

        for (QuestionnaireVO cuestionario: cuestionarios){

            List<QuestionnaireAnswerVO> respuestas = respuestasList.get(cuestionario.getId());
            List<QuestionVO> questions = questionsList.stream()
                    .filter(question -> question.getPeriodId().getId() == cuestionario.getPeriod().getId())
                    .collect(Collectors.toList());

            List<QuestionVO> obligatorias = questions.stream()
                    .filter(question -> "advanced".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());

            List<QuestionVO> recomendadas = questions.stream()
                    .filter(question -> "basic".equals(question.getType().toLowerCase()))
                    .collect(Collectors.toList());


            Double totalWeightObligatorias = 0.0;
            Double totalWeightRecomendadas = 0.0;
            Double totalScore = 0.0;

            for (QuestionVO question : questions) {
                if (question.getType().equalsIgnoreCase("advanced") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightObligatorias += question.getWeight();
                else if (question.getType().equalsIgnoreCase("basic") && Utils.parsearTildes(question.getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))) totalWeightRecomendadas += question.getWeight();
            }

            for (QuestionnaireAnswerVO answer : respuestas) {

                if (answer.getAnswer() != null && Utils.parsearTildes(answer.getQuestionnaireQuestion().getQuestion().getCatQuestion().getCategoryType()).equalsIgnoreCase(Utils.parsearTildes(categoria))){

                    QuestionVO questionTarget = questions.stream()
                            .filter(question -> Objects.equals(question.getId(), answer.getQuestionnaireQuestion().getQuestion().getId()))
                            .findFirst()
                            .orElse(null);

                    assert questionTarget != null;

                    double result = (answer.getNegativeExtraPoint() != null ? answer.getNegativeExtraPoint().doubleValue() : 0.0) / 100;

                    if (answer.getAnswer() && questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("advanced")) totalScore += (questionTarget.getWeight() / totalWeightObligatorias) * 100 * result;
                    else if (questionTarget.getType().equalsIgnoreCase("basic") && answer.getAnswer()) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100;
                    else if (questionTarget.getType().equalsIgnoreCase("basic")) totalScore += (questionTarget.getWeight() / totalWeightRecomendadas) * 100 * result;
                }

            }

            resultadosNotas.add(totalScore);
        }

        StatsDividedVO stats = new StatsDividedVO();
        stats.setCategoria(categoria);
        stats.setPuntuacion(Double.parseDouble(String.format("%.2f", resultadosNotas.stream().mapToDouble(Double::doubleValue).average().orElse(0)).replace(",", ".")));
        stats.setType("Advanced");

        return stats;
    }


}
