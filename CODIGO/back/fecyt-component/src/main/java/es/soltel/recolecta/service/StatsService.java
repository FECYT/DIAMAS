package es.soltel.recolecta.service;

import es.soltel.recolecta.vo.StatsDividedVO;
import es.soltel.recolecta.vo.StatsVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StatsService {

    StatsVO getPuntuacionMediaGeneral(Long idType, String fechaInicio, String fechaFin);
    StatsVO getStatByCategoria(Long idType, String categoria, String fechaInicio, String fechaFin);

    StatsVO getPuntuacionMediaGeneralByRepositoryId(Long idType, Long id, String fechaInicio, String fechaFin);

    List<StatsVO> getStatsByCategoria(Long idType, String fechaInicio, String fechaFin);

    StatsVO getStatByCategoriaAndRepositoryId(Long idType, String categoria, Long id, String fechaInicio, String fechaFin);
    
    StatsDividedVO getStatByRepositoryId(Long idType, Boolean obligatoriasToggle, String categoria, Long id);

    StatsDividedVO getStatsByCategoriaDivide(Long idType, String categoria, Boolean obligatoriasToggle , String fechaInicio, String fechaFin);

    List<StatsDividedVO> getStatsByCategoriasDivididas(Long idType, String fechaInicio, @PathVariable String fechaFin);

    List<StatsDividedVO> getStatsByCategoriasDivididasByRepositoryId(Long idType, Long id, String fechaInicio, String fechaFin);

    StatsDividedVO getStatsByCategoriaDivideByRepositoryId(Long idType, String categoria, Boolean obligatoriasToggle, Long repositoryId, String fechaInicio, String fechaFin);

    StatsVO getPuntuacionMediaGeneralByQuestionnaireId(Long idType, Long questionnaireId);

    StatsVO getStatByCategoriaAndQuestionnaireId(Long idType, String categoria, Long questionnaireId);

    List<StatsDividedVO> getStatsByCategoriasDivididasByQuestionnaireId(Long idType, Long questionnaireId);

    List<StatsDividedVO> getStatsByUserAndPeriodId(Long idType, Long periodId, Long userId);

    StatsDividedVO getStatsByQuestionnaireDivideByRepositoryId(Long idType, String categoria, Boolean obligatoriasToggle, Long questionnaireId);

    List<StatsVO> getStatsByCategoriaAndQuestionnaireId(Long idType, Long questionnaireId);

    List<StatsVO> getStatsByCategoriaAndRepositoryId(Long idType, Long repositoryId, String fechaInicio, String fechaFin );
    
    List<StatsDividedVO> getStatsByRepositoryId(Long idType, Long repositoryId);
    
    List<StatsDividedVO> getStatsByEvaluationId(Long idType, Long evaluationId) throws Exception;
	
    StatsDividedVO getStatByEvaluationId(Long idType, String categoria, Boolean obligatoriasToggle, Long evaluationId) throws Exception;
    
}