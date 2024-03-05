package es.soltel.recolecta.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import es.soltel.recolecta.vo.EvaluationVO;

public interface EvaluationService {

    EvaluationVO findById(Long id) throws Exception;
    
    List<EvaluationVO> findByQuestionnaireType(Long id);

    List<EvaluationVO> findAll();

    EvaluationVO create(EvaluationVO evaluationVO);

    EvaluationVO update(Long id, EvaluationVO evaluationVO);

    void delete(Long id);

    List<EvaluationVO> findEvaluationsBetweenDates(Long idType, LocalDateTime fechaInicio, LocalDateTime fechaFinal);

    List<EvaluationVO> findAllWithoutCloseDateForRepository(Long repoId);


    List<EvaluationVO> findAllWithCloseDateForRepository(Long repoId);

    Boolean deleteEvaluationAndQuestionnaire(EvaluationVO evaluationVO);

    List<EvaluationVO> findActiveEvaluationsOfUser(Long id);

    // List<EvaluationVO> findAllEvaluationsWithUsers();

    List<EvaluationVO> findEvaluationsActiveWithDataPassed();

    EvaluationVO findByQuestionnaireId(Long idType, Long id);

    List<EvaluationVO> findByRepositoryId(Long idType, Long id);

    List<EvaluationVO> findByRepositoryIdBetweenDates(Long idType, Long id,LocalDateTime fechaInicio, LocalDateTime fechaFinal );


}
