package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.soltel.recolecta.service.AssignedEvaluatorsService;
import es.soltel.recolecta.vo.AssignedEvaluatorsVO;
import es.soltel.recolecta.vo.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/assignedEvaluators")
public class AssignedEvaluatorsController {

    @Autowired
    private AssignedEvaluatorsService service;

    @GetMapping("/findAll")
    public List<AssignedEvaluatorsVO> findAll() {
        return service.findAll();
    }

    @PostMapping
    public AssignedEvaluatorsVO create(@RequestBody AssignedEvaluatorsVO evaluators) {
        return service.create(evaluators);
    }

    @GetMapping("/assigned-evaluator/evaluation/{evaluationId}")
    public List<AssignedEvaluatorsVO> getAssignedEvaluators(@RequestParam("evaluationId") Long evaluationId) {
        return service.findByEvaluationId(evaluationId);
    }

    @GetMapping("/getActiveEvaluations/")
    public List<AssignedEvaluatorsVO> getActiveEvaluations() {
        List<AssignedEvaluatorsVO> assignedEvaluators = service.findActiveEvaluations();
        Map<Long, AssignedEvaluatorsVO> groupedEvaluators = new HashMap<>();

        for (AssignedEvaluatorsVO evaluator : assignedEvaluators) {
            Long evaluationId = evaluator.getEvaluation().getId();

            if (!groupedEvaluators.containsKey(evaluationId)) {
                AssignedEvaluatorsVO groupedEvaluator = new AssignedEvaluatorsVO();
                groupedEvaluator.setEvaluation(evaluator.getEvaluation());
                groupedEvaluator.setNDeleteState(evaluator.getNDeleteState());
                groupedEvaluator.setUsers(new ArrayList<>());
                groupedEvaluators.put(evaluationId, groupedEvaluator);
            }

            AssignedEvaluatorsVO groupedEvaluator = groupedEvaluators.get(evaluationId);
            List<UserVO> users = groupedEvaluator.getUsers();

            // Verificar si evaluator.getUsers() es null y manejarlo adecuadamente
            if (evaluator.getUsers() == null) {
                // Si evaluator.getUsers() es null, crear una lista vacía y asignarla a users
                users = new ArrayList<>();
            } else {
                // Si evaluator.getUsers() no es null, agregar todos los usuarios a la lista
                users.addAll(evaluator.getUsers());
            }

            // Establecer la lista de usuarios en el evaluador agrupado
            groupedEvaluator.setUsers(users);
        }

        return new ArrayList<>(groupedEvaluators.values());
    }

}
