package es.soltel.recolecta.controller;

import es.soltel.recolecta.service.StatsService;
import es.soltel.recolecta.utils.Utils;
import es.soltel.recolecta.vo.StatsDividedVO;
import es.soltel.recolecta.vo.StatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService service;

    //EJEMPLO DATE A RECIBIR 2025-11-29T15:30:00

    @GetMapping("/getGeneralStats/{idType}/{fechaInicio}/{fechaFin}")
    public StatsVO getGeneralStats(@PathVariable Long idType, @PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getPuntuacionMediaGeneral(idType,fechaInicio,fechaFin);
    }

    @GetMapping("/getGeneralStats/repository/{idType}/{id}/{fechaInicio}/{fechaFin}")
    public StatsVO getGeneralStatsByRepositoryId(@PathVariable Long idType, @PathVariable Long id,@PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getPuntuacionMediaGeneralByRepositoryId(idType,id,fechaInicio,fechaFin);
    }

    @GetMapping("/getGeneralStats/questionnaire/{idType}/{id}")
    public StatsVO getGeneralStatsByQuestionnaireId(@PathVariable Long idType, @PathVariable Long id) {
        return service.getPuntuacionMediaGeneralByQuestionnaireId(idType,id);
    }

    /*
    @GetMapping("/getStatByCategoria/{categoria}/{fechaInicio}/{fechaFin}")
    public StatsVO getStatByCategoria(@PathVariable String categoria,@PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getStatByCategoria(Utils.parsearTildes(categoria),fechaInicio,fechaFin);
    }
    */

    @GetMapping("/getStatsByCategoria/{idType}/{fechaInicio}/{fechaFin}")
    public List<StatsVO> getStatsByCategoria(@PathVariable Long idType, @PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getStatsByCategoria(idType, fechaInicio,fechaFin);
    }

    /*
    @GetMapping("/getStatByCategoria/categoria/{categoria}/repositoryId/{id}/{fechaInicio}/{fechaFin}")
    public StatsVO getStatByCategoriaAndRepositoryId(@PathVariable String categoria,@PathVariable Long id,@PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getStatByCategoriaAndRepositoryId(Utils.parsearTildes(categoria),id,fechaInicio,fechaFin);
    }
    */

    @GetMapping("/getStatsByCategoria/repositoryId/{idType}/{id}/{fechaInicio}/{fechaFin}")
    public List<StatsVO> getStatsByCategoriaAndRepositoryId(@PathVariable Long idType,@PathVariable Long id,@PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getStatsByCategoriaAndRepositoryId(idType,id,fechaInicio,fechaFin);
    }

    /*
    @GetMapping("/getStatByCategoria/categoria/{categoria}/questionnaireId/{id}")
    public StatsVO getStatByCategoriaAndQuestionnaireId(@PathVariable String categoria,@PathVariable Long id) {
        return service.getStatByCategoriaAndQuestionnaireId(Utils.parsearTildes(categoria),id);
    }*/
    

    @GetMapping("/getStatsByCategoria/questionnaireId/{idType}/{id}")
    public List<StatsVO> getStatsByCategoriaAndQuestionnaireId(@PathVariable Long idType, @PathVariable Long id) {
        return service.getStatsByCategoriaAndQuestionnaireId(idType, id);
    }


    @GetMapping("/getStatsCategoriasDivided/{idType}/{fechaInicio}/{fechaFin}")
    public List<StatsDividedVO> getStatsCategoriasDivided(@PathVariable Long idType, @PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getStatsByCategoriasDivididas(idType,fechaInicio,fechaFin);
    }

    @GetMapping("/getStatByCategoria/repositoryId/{idType}/{id}/{fechaInicio}/{fechaFin}")
    public List<StatsDividedVO> getStatsCategoriasByRepositoryId(@PathVariable Long idType, @PathVariable Long id,@PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return service.getStatsByCategoriasDivididasByRepositoryId(idType,id,fechaInicio,fechaFin);
    }

    @GetMapping("/getStatsCategoriasByQuestionnaire/questionnaireId/{idType}/{questionnaireId}")
    public List<StatsDividedVO> getStatsCategoriasByQuestionnaireId(@PathVariable Long idType, @PathVariable Long questionnaireId) {
        return service.getStatsByCategoriasDivididasByQuestionnaireId(idType, questionnaireId);
    }

    @GetMapping("/getStatsByUserAndPeriodId/periodId/{idType}/{periodId}/userId/{userId}")
    public List<StatsDividedVO> getStatsByUserAndPeriodId(@PathVariable Long idType,@PathVariable Long periodId,@PathVariable Long userId){
        return service.getStatsByUserAndPeriodId(idType,periodId,userId);
    }

    @GetMapping("/getStatsByRepositoryId/questionnaireId/{idType}/{id}")
    public List<StatsDividedVO> getStatsByRepositoryId(@PathVariable Long idType, @PathVariable Long id) {
        return service.getStatsByRepositoryId(idType, id);
    }

    @GetMapping("/getStatsByEvaluationId/evaluationId/{idType}/{id}")
    public List<StatsDividedVO> getStatsByEvaluationId(@PathVariable Long idType, @PathVariable Long id) throws Exception {
        return service.getStatsByEvaluationId(idType, id);
    }
}
