package es.soltel.recolecta.service.impl;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.converters.MailProperties;
import org.springframework.util.FileCopyUtils;

@Service
public class MailService {

    /**
     * Propiedades del servidor de correo.
     */
    @Autowired
    private MailProperties mailProperties;

    /**
     * Envía un correo electrónico.
     * 
     * @param to
     *                    Dirección de correo electrónico del destinatario.
     * @param subject
     *                    Asunto del correo electrónico.
     * @param rutaHTML
     *                    Texto del mensaje del correo electrónico.
     * @throws MessagingException
     *                            Si hay un error al enviar el correo electrónico.
     */


    @NoLogging
    public void sendMail(String from, String to, String subject, String rutaHTML, String link, String code) throws MessagingException, IOException {

        Properties properties = new Properties();
        properties.put("mail.smtp.from", mailProperties.getFrom());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", mailProperties.getHost());
        properties.put("mail.smtp.port", mailProperties.getPort());
        properties.put("mail.smtp.charset", "UTF-8");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProperties.getUserSMTP(), mailProperties.getPasswordSMTP());
            }
        });

        MimeMessage message = new MimeMessage(session);
        MimeMultipart multipart = new MimeMultipart("related");

        try {
            // Parte del texto del correo
            MimeBodyPart textPart = new MimeBodyPart();
            String htmlFilePath = rutaHTML;
            String emailContent = readHtmlFile(htmlFilePath);

            if (emailContent.contains("{LOGO_PATH}")) emailContent = emailContent.replace("{LOGO_PATH}", "cid:logo");
            if (emailContent.contains("{LOGOS_PATH}")) emailContent = emailContent.replace("{LOGOS_PATH}", "cid:logos");
            if (emailContent.contains("{CODE}")) emailContent = emailContent.replace("{CODE}", code);
            if (emailContent.contains("{LINK}")) emailContent = emailContent.replaceAll("\\{LINK}", link);

            textPart.setText(emailContent, "utf-8", "html");

            // Agregar la parte de texto al multipart
            multipart.addBodyPart(textPart);

            // Adjunta la imagen DiamasLOGO.png al mensaje
            MimeBodyPart firstImagePart = new MimeBodyPart();
            Resource firstResource = new ClassPathResource("images/DiamasLOGO.png");
            InputStream firstImageStream = firstResource.getInputStream();
            byte[] firstImageBytes = FileCopyUtils.copyToByteArray(firstImageStream);

            firstImagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(firstImageBytes, "image/png")));
            firstImagePart.setContentID("<logo>");
            firstImagePart.setDisposition(MimeBodyPart.INLINE);

            // Agregar la parte de la primera imagen al multipart
            multipart.addBodyPart(firstImagePart);

            // Adjunta la imagen logos.png al mensaje
            MimeBodyPart secondImagePart = new MimeBodyPart();
            Resource secondResource = new ClassPathResource("images/logos.png");
            InputStream secondImageStream = secondResource.getInputStream();
            byte[] secondImageBytes = FileCopyUtils.copyToByteArray(secondImageStream);

            secondImagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(secondImageBytes, "image/png")));
            secondImagePart.setContentID("<logos>");
            secondImagePart.setDisposition(MimeBodyPart.INLINE);

            // Agregar la parte de la segunda imagen al multipart
            multipart.addBodyPart(secondImagePart);

            // Establece el contenido del mensaje como el multipart
            message.setContent(multipart);

            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Enviar el mensaje
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void sendMailZip(String from, String to, String subject, String rutaHTML, String link, String code, byte[] zip, String zipName) throws MessagingException, IOException {

        Properties properties = new Properties();
        properties.put("mail.smtp.from", mailProperties.getFrom());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", mailProperties.getHost());
        properties.put("mail.smtp.port", mailProperties.getPort());
        properties.put("mail.smtp.charset", "UTF-8");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProperties.getUserSMTP(), mailProperties.getPasswordSMTP());
            }
        });

        MimeMessage message = new MimeMessage(session);
        MimeMultipart multipart = new MimeMultipart("related");

        try {
            // Parte del texto del correo
            MimeBodyPart textPart = new MimeBodyPart();
            String htmlFilePath = rutaHTML;
            String emailContent = readHtmlFile(htmlFilePath);

            if (emailContent.contains("{LOGO_PATH}")) emailContent = emailContent.replace("{LOGO_PATH}", "cid:logo");
            if (emailContent.contains("{LOGOS_PATH}")) emailContent = emailContent.replace("{LOGOS_PATH}", "cid:logos");
            if (emailContent.contains("{CODE}")) emailContent = emailContent.replace("{CODE}", code);
            if (emailContent.contains("{LINK}")) emailContent = emailContent.replace("{LINK}", link);

            textPart.setText(emailContent, "utf-8", "html");

            // Agregar la parte de texto al multipart
            multipart.addBodyPart(textPart);

            // Adjunta la imagen DiamasLOGO.png al mensaje
            MimeBodyPart firstImagePart = new MimeBodyPart();
            Resource firstResource = new ClassPathResource("images/DiamasLOGO.png");
            InputStream firstImageStream = firstResource.getInputStream();
            byte[] firstImageBytes = FileCopyUtils.copyToByteArray(firstImageStream);

            firstImagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(firstImageBytes, "image/png")));
            firstImagePart.setContentID("<logo>");
            firstImagePart.setDisposition(MimeBodyPart.INLINE);

            // Agregar la parte de la primera imagen al multipart
            multipart.addBodyPart(firstImagePart);

            // Adjunta la imagen logos.png al mensaje
            MimeBodyPart secondImagePart = new MimeBodyPart();
            Resource secondResource = new ClassPathResource("images/logos.png");
            InputStream secondImageStream = secondResource.getInputStream();
            byte[] secondImageBytes = FileCopyUtils.copyToByteArray(secondImageStream);

            secondImagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(secondImageBytes, "image/png")));
            secondImagePart.setContentID("<logos>");
            secondImagePart.setDisposition(MimeBodyPart.INLINE);

            // Agregar la parte de la segunda imagen al multipart
            multipart.addBodyPart(secondImagePart);

            // Agregar zip
            if (zip != null) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setDataHandler(new DataHandler(new ByteArrayDataSource(zip, "application/zip")));
                attachmentPart.setFileName(zipName);
                multipart.addBodyPart(attachmentPart);
            }

            // Establece el contenido del mensaje como el multipart
            message.setContent(multipart);

            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Enviar el mensaje
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String readHtmlFile(String filePath) {
        try {
            // Create an instance of ClassPathResource
            ClassPathResource classPathResource = new ClassPathResource(filePath);

            // Check if the resource exists
            if (!classPathResource.exists()) {
                throw new IOException("Resource does not exist: " + filePath);
            }

            // Read the content of the file using InputStream
            try (InputStream inputStream = classPathResource.getInputStream()) {
                // Convert the InputStream to a String using FileCopyUtils
                byte[] fileBytes = FileCopyUtils.copyToByteArray(inputStream);
                return new String(fileBytes, StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            // Handle errors, for example, log the error
            System.out.println("ARCHIVO NO ENCONTRADO");
            e.printStackTrace();
            return "";
        }
    }




}
