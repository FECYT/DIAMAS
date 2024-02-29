package es.soltel.recolecta;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import es.soltel.recolecta.service.EvaluationService;
import es.soltel.recolecta.service.MailSending;
import es.soltel.recolecta.service.QuestionnaireService;
import es.soltel.recolecta.service.QuestionnaireToCloseService;
import es.soltel.recolecta.service.impl.ConfigService;
import es.soltel.recolecta.vo.EvaluationVO;

@Component
public class ScheduledTasks {

    @Autowired
    private QuestionnaireToCloseService questionnaireToCloseService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MailSending mailService;

    @Autowired
    private EvaluationService evaluationService;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

/*     @Transactional
    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    public void closeEvaluationsAutomatically() {

        List<QuestionnaireToCloseVO> allPending = questionnaireToCloseService.getAll();
        Integer intervalo = configService.getIntervaloCuestionarioEnSegundos();

        LocalDateTime now = LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid"));

        for (QuestionnaireToCloseVO questionnaire : allPending) {

            LocalDateTime fecha = questionnaire.getFecha();

            if (fecha.plusSeconds(intervalo).isBefore(now)) {
                // Si la fecha más el intervalo es anterior a la hora actual,
                // cierra la evaluación y envía un correo electrónico

                QuestionnaireVO updatingQuestionnaire = questionnaire.getQuestionnaire();
                updatingQuestionnaire.getEvaluation().setEvaluationState("Retrasada");
                updatingQuestionnaire.getEvaluation()
                        .setCloseDate(LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid")));
                questionnaireService.update(updatingQuestionnaire);
                questionnaireToCloseService.delete(questionnaire.getId());

                questionnaireService.createAction(6L, QuestionnaireConverter.voToEntity(updatingQuestionnaire), null);

                // Envía un correo electrónico de notificación de cierre
                executorService
                        .submit(() -> {
                            try {
                                mailService.sendMailQuestionnaireAutomaticallyClosed(updatingQuestionnaire);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        });
            }
        }
    } */

    @Transactional
    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    public void closeEvaluationsFechaPasada(){

        List<EvaluationVO> evaluaciones = evaluationService.findEvaluationsActiveWithDataPassed();

        for (EvaluationVO evaluacion : evaluaciones) {
            evaluacion.setEvaluationState("Cerrado");
            evaluationService.update(evaluacion.getId(),evaluacion);
        }

    }

}
