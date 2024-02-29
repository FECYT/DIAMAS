package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.QuestionnaireQuestionService;
import es.soltel.recolecta.vo.QuestionnaireQuestionVO;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/api/questionnaire-questions")
public class QuestionnaireQuestionController {

    @Autowired
    private QuestionnaireQuestionService service;

    @PostMapping("/create")
    public ResponseEntity<QuestionnaireQuestionVO> create(@RequestBody QuestionnaireQuestionVO vo) {
        return new ResponseEntity<>(service.create(vo), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<QuestionnaireQuestionVO> update(@RequestBody QuestionnaireQuestionVO vo) {
        return new ResponseEntity<>(service.update(vo), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<QuestionnaireQuestionVO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<QuestionnaireQuestionVO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }
}
