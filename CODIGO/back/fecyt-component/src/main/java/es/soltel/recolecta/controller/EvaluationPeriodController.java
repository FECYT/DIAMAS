package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.EvaluationPeriodService;
import es.soltel.recolecta.vo.EvaluationPeriodVO;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/evaluationPeriod")
public class EvaluationPeriodController {
    @Autowired
    private EvaluationPeriodService evaluationService;

    @GetMapping("/{id}")
    public EvaluationPeriodVO getById(@PathVariable Long id) {
        return evaluationService.findById(id);
    }
    
    @GetMapping("/questionnaireType/{id}")
    public List<EvaluationPeriodVO> getByQuestionnaireType(@PathVariable Long id) {
        return evaluationService.findByQuestionnaireType(id);
    }

    @GetMapping
    public List<EvaluationPeriodVO> getAll() {
        return evaluationService.findAll();
    }

    @PostMapping
    public EvaluationPeriodVO create(@RequestBody EvaluationPeriodVO evaluationPeriodVO) {
        return evaluationService.create(evaluationPeriodVO);
    }

    @PutMapping("/{id}")
    public EvaluationPeriodVO update(@PathVariable Long id, @RequestBody EvaluationPeriodVO evaluationPeriodVO) {
        return evaluationService.update(evaluationPeriodVO);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return evaluationService.delete(id);
    }
}
