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
    public List<AssignedEvaluatorsVO> findByEvaluationId(Long id) {
        return AssignedEvaluatorsConverter.entitiesToVos(repository.findByEvaluationId(id));
    }

    @Override
    public List<AssignedEvaluatorsVO> findActiveEvaluations() {

        return AssignedEvaluatorsConverter.entitiesToVos(repository.findActiveEvaluations());
    }

}
