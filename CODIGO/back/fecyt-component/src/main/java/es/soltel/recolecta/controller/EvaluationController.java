package es.soltel.recolecta.controller;

import java.io.IOException;
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

import es.soltel.recolecta.service.EvaluationService;
import es.soltel.recolecta.service.MailSending;
import es.soltel.recolecta.service.QuestionnaireService;
import es.soltel.recolecta.vo.EvaluationVO;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/evaluation")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private MailSending mailSending;
    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping("/{id}")
    public EvaluationVO getById(@PathVariable Long id) throws Exception {
        return evaluationService.findById(id);
    }
    
    @GetMapping("/questionnaireType/{id}")
    public List<EvaluationVO> getByQuestionnaireType(@PathVariable Long id) {
        return evaluationService.findByQuestionnaireType(id);
    }

    @GetMapping
    public List<EvaluationVO> getAll() {
        return evaluationService.findAll();
    }

    @PostMapping
    public EvaluationVO create(@RequestBody EvaluationVO evaluationVO) {
        return evaluationService.create(evaluationVO);
    }

    @PostMapping("deleteEvaluationAndQuestionnaire")
    public Boolean deleteEvaluationAndQuestionnaire(@RequestBody EvaluationVO evaluationVO) {
        return evaluationService.deleteEvaluationAndQuestionnaire(evaluationVO);
    }

    @PutMapping("/{id}")
    public EvaluationVO update(@PathVariable Long id, @RequestBody EvaluationVO evaluationVO) {
        return evaluationService.update(id, evaluationVO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        evaluationService.delete(id);
    }

    @GetMapping("/findAllWithoutCloseDateForRepository/{repoId}")
    public List<EvaluationVO> findAllWithoutCloseDateForRepository(@PathVariable Long repoId) {
        return evaluationService.findAllWithoutCloseDateForRepository(repoId);
    }

    @GetMapping("/findAllWithCloseDateForRepository/{repoId}")
    public List<EvaluationVO> findAllWithCloseDateForRepository(@PathVariable Long repoId) {
        return evaluationService.findAllWithCloseDateForRepository(repoId);
    }

    @GetMapping("/findAllWithCloseDateForRepositoryDNET/{dnetRepoId}")
    public List<EvaluationVO> findAllWithCloseDateForRepositoryDnet(@PathVariable String dnetRepoId) {
        return evaluationService.findAllWithCloseDateForRepositoryDnet(dnetRepoId);
    }

}
