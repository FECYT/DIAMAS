package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.QuestionnaireTypeService;
import es.soltel.recolecta.vo.QuestionnaireTypeVO;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/api/questionnaireType")
public class QuestionnaireTypeController {

    @Autowired
    private QuestionnaireTypeService service;

    @GetMapping
    public List<QuestionnaireTypeVO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public QuestionnaireTypeVO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public QuestionnaireTypeVO create(@RequestBody QuestionnaireTypeVO questionnaireType) {
        return service.create(questionnaireType);
    }

    @PutMapping
    public QuestionnaireTypeVO update(@RequestBody QuestionnaireTypeVO questionnaireType) {
        return service.update(questionnaireType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
