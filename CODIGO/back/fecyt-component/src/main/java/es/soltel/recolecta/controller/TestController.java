package es.soltel.recolecta.controller;

import es.soltel.recolecta.service.MailSending;
import es.soltel.recolecta.service.QuestionnaireService;
import es.soltel.recolecta.vo.QuestionnaireVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private MailSending mailSending;

    @GetMapping
    public void testMails() throws Exception {

        QuestionnaireVO questionnaire = questionnaireService.findByEvaluationId(1L);

        mailSending.sendMailUsuarioRegistradoExito("antonio.gutierrez@soltel.es");
        mailSending.sendMailUsuarioCambiarContraseña("antonio.gutierrez@soltel.es","asdasd");
        mailSending.sendMailPDF("antonio.gutierrez@soltel.es",questionnaire,null);

    }


}
