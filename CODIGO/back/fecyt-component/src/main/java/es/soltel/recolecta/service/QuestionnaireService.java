package es.soltel.recolecta.service;

import java.util.Date;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.vo.QuestionnaireVO;

public interface QuestionnaireService {

    List<QuestionnaireVO> findAllActive();

    QuestionnaireVO findActiveById(Long id);

    List<QuestionnaireVO> findByUserId(Long id);

    QuestionnaireVO findByEvaluationId(Long id);

    QuestionnaireVO create(QuestionnaireVO questionnaire);

    QuestionnaireVO update(QuestionnaireVO questionnaire);

    void createAction(Long id, QuestionnaireEntity questionnaire, Optional<Long> actionAuthor);

    void delete(Long id);

    void closeEvaluationPDF(Long id, Long actionAuthor, byte[] zip);

    void closeEvaluation(Long id, Long actionAuthor);
    byte[] exportData(Long idType, Date startDate, Date endDate, String language, String format);
    
    byte[] exportDataFilter(Long idType, Long year, String nombre, String language, String format);

    byte[] exportCertificate(Long id);
    List<QuestionnaireVO> getCertificatesByDate(Long idType, Date date);

    List<QuestionnaireVO> findByUserAndPeriodId(Long idType, Long userId,Long periodId);

    List<QuestionnaireVO> findByPeriodId(Long periodId);
}
