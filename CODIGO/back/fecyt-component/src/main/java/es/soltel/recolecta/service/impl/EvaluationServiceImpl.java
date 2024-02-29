package es.soltel.recolecta.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.netflix.discovery.converters.Auto;
import es.soltel.recolecta.converters.QuestionnaireConverter;
import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.service.*;
import es.soltel.recolecta.service.RepositoryService;
import es.soltel.recolecta.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.EvaluationEntity;
import es.soltel.recolecta.repository.EvaluationRepository;
import es.soltel.recolecta.converters.EvaluationConverter;
import es.soltel.recolecta.vo.EvaluationVO;
import es.soltel.recolecta.vo.QuestionnaireTypeVO;
import es.soltel.recolecta.vo.RepositoryVO;
import es.soltel.recolecta.vo.UserRepositoryVO;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationConverter evaluationConverter;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private DnetService dnetService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepositoryService userRepositoryService;
    
    @Autowired
    private QuestionnaireTypeService questionnaireTypeService;

    @Override
    public EvaluationVO findById(Long id) {
        EvaluationVO evaluationVO = evaluationConverter.entityToVO(evaluationRepository.findById(id).orElse(null));
        return evaluationVO;
    }
    
    @Override
    public List<EvaluationVO> findByQuestionnaireType(Long id) {
    	List<EvaluationEntity> entities = evaluationRepository.findByQuestionnaireType(id);
    	return entities.stream()
                .filter(entity -> entity.getnDeleteState() != 2)
                .map(EvaluationConverter::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluationVO> findAll() {
        List<EvaluationVO> allEvaluations = evaluationConverter.ToVO(evaluationRepository.findAll());

        // Filtrar la lista para incluir solo aquellos cuyo nDeleteState no sea igual a 2
        List<EvaluationVO> filteredEvaluations = allEvaluations.stream()
                .filter(evaluation -> !Objects.equals(evaluation.getnDeleteState(), 2))
                .collect(Collectors.toList());

        return filteredEvaluations;
    }

    @Override
    public EvaluationVO create(EvaluationVO evaluationVO) {

        // UserVO userTarget = userService.findByRepositoryId(evaluationVO.getUserRepository().getRepository().getId());
    	UserRepositoryVO userRepositoryTarget = userRepositoryService.findByRepositoryId(evaluationVO.getUserRepository().getRepository().getId());

    	/*
        RepositoryVO repoTarget = repositoryService.findByUserId(userTarget.getId());
        evaluationVO.getUserRepository().setRepository(repoTarget);
        evaluationVO.getUserRepository().setUser(userTarget);
        */
    	evaluationVO.setUserRepository(userRepositoryTarget);
    	
    	QuestionnaireTypeVO questionnaireTypeTarget = questionnaireTypeService.getById(evaluationVO.getQuestionnaireType().getId());

    	evaluationVO.setQuestionnaireType(questionnaireTypeTarget);

        //Compruebo si hay una evaluación anterior en ese repositorio para preestablecer respuestas
        /*
         * List<EvaluationResponseVO> latestEvaluation =
         * EvaluationResponseConverter.ToVO(evaluationRepository.findLatestResponsesByRepository(createdEvaluation.
         * getRepository().getId()));
         *
         * if (latestEvaluation.size() != 0){ //entonces se copia las answers y se pegan en current createdEvaluation
         * answer id latestEvaluation.forEach(it->{
         *
         * }); }
         */

        return EvaluationConverter
                .entityToVO(evaluationRepository.save(EvaluationConverter.ToEntity(evaluationVO)));
    }


    @Override
    public EvaluationVO update(Long id, EvaluationVO evaluationVO) {
        return EvaluationConverter.entityToVO(evaluationRepository.save(EvaluationConverter.ToEntity(evaluationVO)));
    }

    @Override
    public void delete(Long id) {
        EvaluationEntity evaluationEntity = evaluationRepository.findById(id).orElse(null);
        evaluationEntity.setnDeleteState(2);
        evaluationRepository.save(evaluationEntity);
    }

    @Override
    public List<EvaluationVO> findEvaluationsBetweenDates(Long idType, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

        return EvaluationConverter.ToVO(evaluationRepository.findByCloseDateBetween(idType, fechaInicio,fechaFinal));
    }

    @Override
    public List<EvaluationVO> findAllWithoutCloseDateForRepository(Long repoId) {
        return EvaluationConverter.ToVO(evaluationRepository.findByRepositoryIdAndCloseDateIsNull(repoId));
    }

    @Override
    public List<EvaluationVO> findAllWithCloseDateForRepository(Long repoId) {
        return EvaluationConverter.ToVO(evaluationRepository.findByRepositoryIdAndCloseDateIsNotNull(repoId));
    }

    @Override
    public Boolean deleteEvaluationAndQuestionnaire(EvaluationVO evaluationVO) {
        QuestionnaireEntity questionnaireEntity = evaluationRepository.findQuestionnaireByEvaluationId(evaluationVO.getId());

        questionnaireEntity.setnDeleteState(2);
        questionnaireEntity.getEvaluation().setnDeleteState(2);

        questionnaireService.update(QuestionnaireConverter.entityToVO(questionnaireEntity));
        return true;
    }

    /*
    @Override
    public List<EvaluationVO> findActiveEvaluationsOfUser(Long id) {
        return EvaluationConverter.ToVO(evaluationRepository.findActiveEvaluationsOfUser(id));
    }

    @Override
    public List<EvaluationVO> findAllEvaluationsWithUsers() {
        return EvaluationConverter.ToVO(evaluationRepository.findAllEvaluationsWithUsers());
    }
    */

    @Override
    public List<EvaluationVO> findAllWithCloseDateForRepositoryDnet(String dnetRepoId) {
        //return EvaluationConverter.ToVO(evaluationRepository.findByRepository_DnetId(dnetRepoId));
        return null;
    }

    @Override
    public List<EvaluationVO> findEvaluationsActiveWithDataPassed() {
        return EvaluationConverter.ToVO(evaluationRepository.findActiveEvaluationsDatePassed(LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid"))));
    }

    @Override
    public EvaluationVO findByQuestionnaireId(Long idType, Long id) {
        return EvaluationConverter.entityToVO(evaluationRepository.findByQuestionnaireId(idType, id));
    }

    @Override
    public List<EvaluationVO> findByRepositoryId(Long idType, Long id){
        return EvaluationConverter.ToVO(evaluationRepository.findByRepositoryId(idType, id));
    }

    @Override
    public List<EvaluationVO> findByRepositoryIdBetweenDates(Long idType, Long id, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return EvaluationConverter.ToVO(evaluationRepository.findByRepositoryIdBetweenDates(idType, id,fechaInicio,fechaFinal));
    }

	@Override
	public List<EvaluationVO> findActiveEvaluationsOfUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



}
