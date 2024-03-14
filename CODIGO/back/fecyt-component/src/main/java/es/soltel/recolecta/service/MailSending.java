package es.soltel.recolecta.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import es.soltel.recolecta.utils.Base64Util;
import es.soltel.recolecta.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.repository.AssignedEvaluatorsRepository;
import es.soltel.recolecta.service.impl.MailService;
import es.soltel.recolecta.vo.QuestionnaireVO;

@Service
public class MailSending {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private AssignedEvaluatorsService assignedEvaluatorsService;

    @Autowired
    private AssignedEvaluatorsRepository assignedEvaluatorsRepository;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private UserService userService;

    @Value("${web.link}")
    private String webLink;
    @Value("${mail.from}")
    private String remitente;

    private Boolean debug = false;
    private String destinatarioDebug = "ricardo.gallego@soltel.es";


    public void sendMailPDF(String email, QuestionnaireVO questionnaire, byte[] zip){

        String subject = "DIAMAS - Self-assessment";
        String rutaHTML = "correos/en/sendSelfAssessment.html";
        String zipName = "report.zip";
        
        
        if (Objects.equals(getUserLanguage(email), "es")){
            subject = "DIAMAS - Autoevaluación";
        	rutaHTML = "correos/" + getUserLanguage(email) + "/sendSelfAssessment.html";
        	zipName = "report.zip";
        }
        
        
        try {
            if (debug) mailService.sendMailZip(remitente, destinatarioDebug, subject,rutaHTML,null,null, zip, zipName);
            else mailService.sendMailZip(remitente, email, subject,rutaHTML,null,null, zip, zipName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void sendMailUsuarioRegistradoExito(String emailRegistrado){

        String subject = "DIAMAS - Te has registrado con éxito";

        if (Objects.equals(getUserLanguage(emailRegistrado), "en")){
            subject = "DIAMAS - Registered successfully";
        }
        
        String rutaHTML = "correos/en/usuarioRegistradoExito.html";
        if (Objects.equals(getUserLanguage(emailRegistrado), "es")){
        	rutaHTML = "correos/" + getUserLanguage(emailRegistrado) + "/sendSelfAssessment.html";
        }

        try {
            if (debug) mailService.sendMail(remitente, destinatarioDebug, subject,rutaHTML,null,null);
            else mailService.sendMail(remitente, emailRegistrado, subject,rutaHTML,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMailUsuarioRegistradoExitoNecesitaConfirmacion(){

        String subject = "DIAMAS - Te has registrado con éxito, pendiente activación";
        String rutaHTML = "correos/en/usuarioRegistradoExitoNecesitaConfirmacion.html";

        List<UserVO> destinatarios = userService.findAdmins();

        try {
            if (debug) mailService.sendMail(remitente, destinatarioDebug, subject,rutaHTML,null,null);
            else {

                for (UserVO destinatario : destinatarios) {
                    mailService.sendMail(remitente, destinatario.getEmail(), subject,rutaHTML,null,null);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMailAdmin_UsuarioRegistradoNecesitaConfirmacion(String emailRegistrado){

        String subject = "DIAMAS - Un usuario registrado require activación";
        String rutaHTML = "correos/" + getUserLanguage(emailRegistrado) + "/admin_usuarioRegistradoNecesitaConfirmacion.html";

        if (Objects.equals(getUserLanguage(emailRegistrado), "en")){
            subject = "DIAMAS - Registered user needs activation";
        }

        List<UserVO> destinatarios = userService.findAdmins();
        String link = webLink + "/user-gestion?email=" + emailRegistrado;

        try {

            if (debug) mailService.sendMail(remitente, destinatarioDebug, subject, rutaHTML,link,null);
            else {

                for (UserVO destinatario : destinatarios) {
                    mailService.sendMail(remitente, destinatario.getEmail(), subject, rutaHTML,link,null);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMailUsuarioCambiarContraseña(String email, String code){


        String subject = "DIAMAS - Cambiar contraseña";
        String rutaHTML = "correos/" + getUserLanguage(email) + "/usuarioCambiarContraseña.html";

        if (Objects.equals(getUserLanguage(email), "en")){
            subject = "DIAMAS - Change password";
        }

        String emailHashed = Base64Util.convertirABase64("emairuZZ" + email);
        String link = webLink + "/recover-password?email=" + emailHashed;


        try {
            if (debug) mailService.sendMail(remitente, destinatarioDebug, subject,rutaHTML,link, code);
            else mailService.sendMail(remitente, email, subject,rutaHTML,link, code);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMailUsuarioContraseñaCambiada(){

        String subject = "DIAMAS - Un usuario registrado require activación";
        String rutaHTML = "correos/en/usuarioContraseñaCambiada.html";

        try {
            if (debug) mailService.sendMail(remitente, destinatarioDebug, subject,rutaHTML,null,null);
            else mailService.sendMail(remitente, destinatarioDebug, subject,rutaHTML,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getUserLanguage(String email){

        List<String> spanish = Arrays.asList(
                "es",
                "ar", // Argentina
                "br", // Brasil
                "cl", // Chile
                "co", // Colombia
                "cr", // Costa Rica
                "cu", // Cuba
                "do", // República Dominicana
                "ec", // Ecuador
                "gt", // Guatemala
                "hn", // Honduras
                "mx", // México
                "ni", // Nicaragua
                "pa", // Panamá
                "pe", // Perú
                "uy", // Uruguay
                "ve"  // Venezuela
                );

        UserVO user = userService.findByEmail(email);
        if (spanish.contains(user.getPais().toLowerCase())) return "es";
        else return "en";

    }

}
