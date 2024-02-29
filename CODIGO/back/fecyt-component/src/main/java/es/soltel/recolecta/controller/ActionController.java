package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import es.soltel.recolecta.vo.ActionVO;
import es.soltel.recolecta.vo.QuestionnaireVO;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.soltel.recolecta.service.ActionService;
import es.soltel.recolecta.service.MailSending;
import es.soltel.recolecta.service.QuestionnaireService;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/actions")
public class ActionController {

    @Autowired
    private ActionService service;

    @GetMapping
    public List<ActionVO> getAll() {
        return service.getAllActions();
    }

    @GetMapping("/{id}")
    public ActionVO getById(@PathVariable Long id) {
        return service.getActionById(id);
    }

    @GetMapping("/deletedActions")
    public List<ActionVO> getAllDeletedActions() {
        return service.getAllDeletedActions();
    }

    @PostMapping
    public ActionVO create(@RequestBody ActionVO action) {
        return service.createAction(action);
    }

    @PutMapping
    public ActionVO update(@RequestBody ActionVO action) {
        return service.updateAction(action);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteAction(id);
    }

}
