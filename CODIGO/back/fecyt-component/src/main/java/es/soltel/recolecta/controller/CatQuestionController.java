package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.CatQuestionService;
import es.soltel.recolecta.vo.CatQuestionVO;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/catQuestions")
public class CatQuestionController {

    @Autowired
    private CatQuestionService service;

    @GetMapping
    public List<CatQuestionVO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CatQuestionVO getById(@PathVariable Long id) {
        return service.getById(id);
    }
    
    @GetMapping("/questionnaireType/{id}")
    public List<CatQuestionVO> getByQuestionnaireType(@PathVariable Long id) {
        return service.findByQuestionnaireType(id);
    }

    @PostMapping
    public CatQuestionVO create(@RequestBody CatQuestionVO catQuestions) {
        return service.create(catQuestions);
    }

    @PutMapping
    public CatQuestionVO update(@RequestBody CatQuestionVO catQuestions) {
        return service.update(catQuestions);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
