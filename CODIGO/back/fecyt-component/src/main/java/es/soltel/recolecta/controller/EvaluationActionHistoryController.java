package es.soltel.recolecta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soltel.recolecta.service.EvaluationActionHistoryService;
import es.soltel.recolecta.vo.EvaluationActionHistoryVO;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/evaluation-action-histories")
public class EvaluationActionHistoryController {

    @Autowired
    private EvaluationActionHistoryService service;

    @GetMapping("/{id}")
    public EvaluationActionHistoryVO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<EvaluationActionHistoryVO> findAll() {
        return service.findAll();
    }

    @GetMapping("/evaluation/{evaluationId}")
    public List<EvaluationActionHistoryVO> findByEvaluationId(@PathVariable Long evaluationId) {
        return service.findByEvaluationId(evaluationId);
    }

    @PostMapping
    public void create(@RequestBody EvaluationActionHistoryVO vo) {
        service.save(vo);
    }

    @PutMapping
    public void update(@RequestBody EvaluationActionHistoryVO vo) {
        service.save(vo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
