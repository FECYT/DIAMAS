package es.soltel.recolecta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.AssignedEvaluatorsEntity;
import es.soltel.recolecta.entity.EvaluationEntity;
import es.soltel.recolecta.repository.AssignedEvaluatorsRepository;
import es.soltel.recolecta.repository.EvaluationRepository;
import es.soltel.recolecta.service.AssignedEvaluatorsService;
import es.soltel.recolecta.service.MailSending;
import es.soltel.recolecta.service.UserService;
import es.soltel.recolecta.converters.AssignedEvaluatorsConverter;
import es.soltel.recolecta.converters.UserConverter;
import es.soltel.recolecta.vo.AssignedEvaluatorsVO;
import es.soltel.recolecta.vo.UserVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@Service
public class AssignedEvaluatorsServiceImpl implements AssignedEvaluatorsService {

    @Autowired
    private AssignedEvaluatorsRepository repository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSending mailService;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public List<AssignedEvaluatorsVO> findAll() {
        return repository.findAll().stream()
                .map(AssignedEvaluatorsConverter::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public AssignedEvaluatorsVO findById(Long id) {
        return repository.findById(id)
                .map(AssignedEvaluatorsConverter::entityToVO)
                .orElse(null);
    }

    @Override
    public AssignedEvaluatorsVO create(AssignedEvaluatorsVO vo) {
        AssignedEvaluatorsEntity entity = repository.save(AssignedEvaluatorsConverter.voToEntity(vo));
        return AssignedEvaluatorsConverter.entityToVO(entity);
    }

    @Override
    public AssignedEvaluatorsVO update(AssignedEvaluatorsVO vo) {
        return vo;
        // Implementación de actualización
    }

    @Override
    public void delete(Long id) {
        // Implementación de borrado lógico
    }

    @Override
    public List<AssignedEvaluatorsVO> findByEvaluationId(Long id) {
        return AssignedEvaluatorsConverter.entitiesToVos(repository.findByEvaluationId(id));
    }

    @Override
    public List<AssignedEvaluatorsVO> findActiveEvaluations() {

        return AssignedEvaluatorsConverter.entitiesToVos(repository.findActiveEvaluations());
    }

    @Override
    public List<AssignedEvaluatorsVO> assignEvaluatorsToEvaluation(Long evaluationId, List<UserVO> evaluators) {
        /*
        // Verificar si la evaluación con evaluationId existe en la base de datos
        EvaluationEntity evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new EntityNotFoundException("Evaluación con ID " + evaluationId + " no encontrada"));

        // Crear una lista para almacenar las entidades asignadas
        List<AssignedEvaluatorsEntity> assignedEvaluatorsEntities = new ArrayList<>();

        // Iterar a través de la lista de evaluadores y asignarlos a
        // AssignedEvaluatorsEntity
        for (UserVO evaluator : evaluators) {

            UserVO evaluatorVO = userService.findByIdDnet(evaluator.getDnetId());

            if (evaluatorVO == null) {

                UserVO newUser = new UserVO();
                newUser.setDnetId(evaluator.getDnetId());
                newUser.setNombre(LoginServiceImpl.emailAFormatoNombre(evaluator.getNombre()));
                newUser.setNDeleteState(1);
                newUser.setEmail(evaluator.getEmail());

                evaluatorVO = userService.create(newUser);
            }

            AssignedEvaluatorsEntity assignedEvaluator = new AssignedEvaluatorsEntity();
            assignedEvaluator.setEvaluation(evaluation);
            assignedEvaluator.setUser(UserConverter.toEntity(evaluatorVO));
            assignedEvaluator.setDnetId(evaluatorVO.getDnetId());
            assignedEvaluator.setNDeleteState(0);
            if (assignedEvaluator.getEvaluation().getEvaluationState().equals("Pendiente")) {
                assignedEvaluator.getEvaluation().setEvaluationState("En proceso");
            }

            // Guardar la asignación en la base de datos
            AssignedEvaluatorsEntity savedEntity = repository.save(assignedEvaluator);

            // Mando el correo
            executorService.submit(() -> {
                try {
                    mailService.sendMailToAssignedEvaluator(evaluationId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            assignedEvaluatorsEntities.add(savedEntity);
        }

        // Convertir la entidad guardada a VO y devolverla

         */
        //return AssignedEvaluatorsConverter.entitiesToVos(assignedEvaluatorsEntities);
        return null;
    }

    @Override
    public List<AssignedEvaluatorsVO> updateAssignedEvaluators(Long evaluationId, List<UserVO> evaluators) {
        /*


        // Verificar si la evaluación con evaluationId existe en la base de datos
        EvaluationEntity evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new EntityNotFoundException("Evaluación con ID " + evaluationId + " no encontrada"));

        // Obtener las asignaciones existentes para esta evaluación
        List<AssignedEvaluatorsEntity> existingAssignments = repository.findByEvaluationId(evaluationId);

        // Actualizar las asignaciones existentes con los nuevos evaluadores o crear
        // nuevas asignaciones si es necesario
        for (int i = 0; i < evaluators.size(); i++) {

            UserVO evaluatorVO = userService.findByIdDnet(evaluators.get(i).getDnetId());

            if (evaluatorVO == null) {

                UserVO newUser = new UserVO();
                newUser.setDnetId(evaluators.get(i).getDnetId());
                newUser.setNombre(LoginServiceImpl.emailAFormatoNombre(evaluators.get(i).getNombre()));
                newUser.setNDeleteState(1);
                newUser.setEmail(evaluators.get(i).getEmail());

                evaluatorVO = userService.create(newUser);
            }

            AssignedEvaluatorsEntity existingAssignment = i < existingAssignments.size() ? existingAssignments.get(i)
                    : null;

            if (existingAssignment != null) {
                // Si la asignación ya existe, actualizarla con el nuevo evaluador
                existingAssignment.setUser(UserConverter.toEntity(evaluatorVO));
                existingAssignment.setDnetId(evaluatorVO.getDnetId());
                existingAssignment.setNDeleteState(0);
            } else {
                // Si la asignación no existe, crear una nueva y asignarle el evaluador
                AssignedEvaluatorsEntity newAssignment = new AssignedEvaluatorsEntity();
                newAssignment.setEvaluation(evaluation);
                newAssignment.setUser(UserConverter.toEntity(evaluatorVO));
                newAssignment.setDnetId(evaluatorVO.getDnetId());
                newAssignment.setNDeleteState(0);
                existingAssignments.add(newAssignment);
            }
        }

        // Eliminar las asignaciones adicionales si se redujo el número de evaluadores
        if (evaluators.size() < existingAssignments.size()) {
            for (int i = evaluators.size(); i < existingAssignments.size(); i++) {
                repository.delete(existingAssignments.get(i));
            }
            existingAssignments.subList(evaluators.size(), existingAssignments.size()).clear();
        }

        // Guardar las asignaciones actualizadas o nuevas en la base de datos
        repository.saveAll(existingAssignments);

        // Mando el correo
        executorService.submit(() -> {
            try {
                mailService.sendMailToAssignedEvaluator(evaluationId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Convertir las asignaciones actualizadas a VO y devolverlas
        return AssignedEvaluatorsConverter.entitiesToVos(existingAssignments);

         */
        return null;
    }
}
