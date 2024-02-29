package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.QuestionnaireAnswerService;
import es.soltel.recolecta.vo.QuestionnaireAnswerVO;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/api/questionnaireAnswer")
public class QuestionnaireAnswerController {

    @Autowired
    private QuestionnaireAnswerService service;

    @GetMapping
    public List<QuestionnaireAnswerVO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public QuestionnaireAnswerVO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public QuestionnaireAnswerVO create(@RequestBody QuestionnaireAnswerVO vo) {
        return service.create(vo);
    }

    @PutMapping
    public QuestionnaireAnswerVO update(@RequestBody QuestionnaireAnswerVO vo) {
        return service.update(vo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/question")
    public QuestionnaireAnswerVO createQuestionProgress(@RequestBody QuestionnaireAnswerVO vo) {
        return service.createQuestionProgress(vo);
    }

    @PostMapping("/questionnaireAnswer/{questionnaireId}")
    public QuestionnaireAnswerVO interactedQuestionnaireAnswer(@RequestBody QuestionnaireAnswerVO vo, @PathVariable Long questionnaireId) {
        return service.interactedQuestionnaireAnswer(vo,questionnaireId);
    }

    @GetMapping("/periodId/{id}")
    public List<QuestionnaireAnswerVO> findByPeriodId(@PathVariable Long id) {
        return service.findByPeriodId(id);
    }

    @GetMapping("evaluationId/{id}")
    public List<QuestionnaireAnswerVO> findByEvaluationId(@PathVariable Long id) {
        return service.findByEvaluationId(id);
    }

    @PutMapping("/updateList")
    public List<QuestionnaireAnswerVO> updateList(@RequestBody List<QuestionnaireAnswerVO> vo) {
        return service.updateList(vo);
    }
}
