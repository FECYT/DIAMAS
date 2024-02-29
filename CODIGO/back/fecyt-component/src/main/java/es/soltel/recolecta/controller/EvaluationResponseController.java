package es.soltel.recolecta.controller;

import es.soltel.recolecta.service.EvaluationResponseService;
import es.soltel.recolecta.vo.EvaluationResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/api/evaluation-response")
public class EvaluationResponseController {

    @Autowired
    private EvaluationResponseService service;

    @GetMapping("/all")
    public List<EvaluationResponseVO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EvaluationResponseVO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/create")
    public void create(@RequestBody EvaluationResponseVO evaluationResponse) {
        service.create(evaluationResponse);
    }

    @PutMapping("/update")
    public void update(@RequestBody EvaluationResponseVO evaluationResponse) {
        service.update(evaluationResponse);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
