package es.soltel.recolecta.controller;

import java.util.List;
import java.util.Set;

import org.opendope.questions.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.soltel.recolecta.entity.QuestionEntity;
import es.soltel.recolecta.service.QuestionService;
import es.soltel.recolecta.vo.QuestionByYearAndFileDTO;
import es.soltel.recolecta.vo.QuestionVO;
import es.soltel.recolecta.vo.QuestionnaireVO;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionEntity> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public QuestionEntity getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping
    @ApiOperation(value = "Crea una pregunta, el typeQuestion debe estar escrito en mayúsculas y debe ser OBLIGATORIA o RECOMENDADA", response = QuestionEntity.class)
    public QuestionVO createQuestion(@RequestBody QuestionVO question) {
        return questionService.createQuestion(question);
    }

    @PutMapping
    public QuestionVO updateQuestion(@RequestBody QuestionVO question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    @GetMapping("/evaluationPeriod/{id}")
    public List<QuestionVO> getQuestionsByEvaluationPeriodId(@PathVariable Long id) {
        return questionService.getQuestionsByEvaluationPeriodId(id);
    }

    @PostMapping("/questionnaires")
    public Set<QuestionVO> getQuestionsByQuestionnaires(@RequestBody List<QuestionnaireVO> questionnaires) {
        return questionService.getQuestionsByEvaluations(questionnaires);
    }

    @GetMapping("/hasFilePeriodByYear/{idType}/{year}")
    public List<QuestionByYearAndFileDTO> getQuestionsWithFileByYear(@PathVariable Long idType, @PathVariable Long year) {
        return questionService.getQuestionsWithFileByYear(idType, year);
    }

    @PostMapping("/insertQuestionSet")
    public List<QuestionVO> insertQuestionSet(@RequestBody List<QuestionVO> questions) {
        return questionService.insertQuestionSet(questions);
    }

}
