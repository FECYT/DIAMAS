package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.converters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.soltel.recolecta.repository.ActionRepository;
import es.soltel.recolecta.repository.EvaluationPeriodRepository;
import es.soltel.recolecta.entity.EvaluationEntity;
import es.soltel.recolecta.entity.QuestionEntity;
import es.soltel.recolecta.entity.QuestionnaireEntity;
import es.soltel.recolecta.entity.RepositoryEntity;
import es.soltel.recolecta.repository.EvaluationRepository;
import es.soltel.recolecta.repository.QuestionRepository;
import es.soltel.recolecta.repository.QuestionnaireRepository;
import es.soltel.recolecta.repository.RepositoryRepository;
import es.soltel.recolecta.service.MailSending;
import es.soltel.recolecta.service.QuestionnaireQuestionService;
import es.soltel.recolecta.service.QuestionnaireService;
import es.soltel.recolecta.service.QuestionnaireToCloseService;
import es.soltel.recolecta.service.UserRepositoryService;
import es.soltel.recolecta.service.UserService;
import es.soltel.recolecta.vo.ActionVO;
import es.soltel.recolecta.vo.EvaluationActionHistoryVO;
import es.soltel.recolecta.vo.QuestionnaireQuestionVO;
import es.soltel.recolecta.vo.QuestionnaireToCloseVO;
import es.soltel.recolecta.vo.QuestionnaireVO;
import es.soltel.recolecta.vo.StatsDividedVO;
import es.soltel.recolecta.vo.UserVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import com.opencsv.CSVWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.io.IOUtils;

@Service
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireRepository repository;

    @Autowired
    private QuestionnaireConverter converter;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private QuestionnaireQuestionService questionnaireQuestionService;

    @Autowired
    private EvaluationActionHistoryServiceImpl evaluationActionHistoryServiceImpl;

    @Autowired
    private EvaluationPeriodRepository periodRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private MailSending mailService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private QuestionnaireToCloseService questionnaireToCloseService;
    
    @Autowired
    private RepositoryRepository repositoryRepository;
    
    @Autowired
    private StatsServiceImpl statsServiceImpl;

    private ExecutorService executorService = Executors.newFixedThreadPool(2); // Puedes ajustar el número de hilos
                                                                               // según tus necesidades

    @Override
    public List<QuestionnaireVO> findAllActive() {
        return repository.findAllActive().stream().map(QuestionnaireConverter::entityToVO).collect(Collectors.toList());
    }

    @Override
    public QuestionnaireVO create(QuestionnaireVO questionnaireVO) {

        QuestionnaireEntity entity = QuestionnaireConverter.voToEntity(questionnaireVO);
        QuestionnaireVO createdQuestionnaire = converter.entityToVO(repository.save(entity));

        // Ahora consigo las preguntas relacionadas a este cuestionario y creo las
        // relaciones entre el cuestionario y sus preguntas en la tabla intermedia

        List<QuestionEntity> questions = questionRepository.findByPeriodId(createdQuestionnaire.getPeriod().getId());

        for (QuestionEntity question : questions) {

            QuestionnaireQuestionVO newRelation = new QuestionnaireQuestionVO();
            newRelation.setQuestionnaire(createdQuestionnaire);
            newRelation.setNDeleteState(1);
            newRelation.setQuestion(QuestionConverter.convertToVO(question));

            questionnaireQuestionService.create(newRelation);
        }

        // Creo la entrada en el historial
        ActionVO action = ActionConverter.convertToVo(actionRepository.findById(1L).orElse(null));

        Long idRepo = createdQuestionnaire.getEvaluation().getUserRepository().getRepository().getId();
        UserVO user = userRepositoryService.findById(idRepo).getUser();

        EvaluationActionHistoryVO newEntry = new EvaluationActionHistoryVO();
        newEntry.setLastEdited(java.time.LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid")));
        newEntry.setNDeleteState(1);
        newEntry.setActionId(action);
        newEntry.setEvaluationId(createdQuestionnaire.getEvaluation());
        newEntry.setUserId(user);
        evaluationActionHistoryServiceImpl.save(newEntry);

        return createdQuestionnaire;
    }

    @Override
    public QuestionnaireVO findActiveById(Long id) {
        return converter.entityToVO(repository.findActiveById(id));
    }

    @Override
    public List<QuestionnaireVO> findByUserId(Long id) {
        return QuestionnaireConverter.ToVO(repository.findByUserId(id));
    }

    @Override
    public QuestionnaireVO findByEvaluationId(Long id) {
        return converter.entityToVO(repository.findByEvaluationId(id));
    }

    @Override
    public QuestionnaireVO update(QuestionnaireVO questionnaireVO) {
        QuestionnaireEntity entity = QuestionnaireConverter.voToEntity(questionnaireVO);
        repository.save(entity);
        return converter.entityToVO(entity);
    }

    @Override
    public void delete(Long id) {
        QuestionnaireEntity entity = repository.findById(id).orElseThrow(null);
        entity.setnDeleteState(2); // Esto simula un "borrado lógico"
        repository.save(entity);
    }

    private QuestionnaireEntity updateEntityFromVo(QuestionnaireEntity entity, QuestionnaireVO vo) {
        entity.setEvaluation(evaluationRepository.findById(vo.getId()).orElse(null));
        entity.setState(vo.getState());
        entity.setCreationDate(vo.getCreationDate());
        entity.setPeriod(periodRepository.findById(vo.getId()).orElse(null));
        entity.setnDeleteState(vo.getnDeleteState());
        return entity;
    }


    @Override
    public void createAction(Long id, QuestionnaireEntity questionnaire, Optional<Long> actionAuthor) {
        ActionVO action = ActionConverter.convertToVo(actionRepository.findById(id).orElse(null));

        Long idRepo = questionnaire.getEvaluation().getUserRepository().getRepository().getId();
        UserVO user;
        if (actionAuthor != null) {
            Long authorId = actionAuthor.get();
            user = userService.findById(authorId);
        } else {
            user = userRepositoryService.findById(idRepo).getUser();
        }

        EvaluationActionHistoryVO newEntry = new EvaluationActionHistoryVO();
        newEntry.setLastEdited(java.time.LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid")));
        newEntry.setNDeleteState(1);
        newEntry.setActionId(action);
        newEntry.setEvaluationId(EvaluationConverter.entityToVO(questionnaire.getEvaluation()));
        newEntry.setUserId(user);
        evaluationActionHistoryServiceImpl.save(newEntry);
    }

    @Override
    public void closeEvaluation(Long id, Long actionAuthor) {

        QuestionnaireVO questionnaire = QuestionnaireConverter.entityToVO(repository.findByEvaluationId(id));
        questionnaire.getEvaluation().setEvaluationState("Cerrado");
        questionnaire.getEvaluation().setCloseDate(LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid")));
        update(questionnaire);

        // Creo la entrada en el historial
        ActionVO action = ActionConverter.convertToVo(actionRepository.findById(5L).orElse(null));

        UserVO user = userService.findById(actionAuthor);

        EvaluationActionHistoryVO newEntry = new EvaluationActionHistoryVO();
        newEntry.setLastEdited(java.time.LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid")));
        newEntry.setNDeleteState(1);
        newEntry.setActionId(action);
        newEntry.setEvaluationId(questionnaire.getEvaluation());
        newEntry.setUserId(user);
        evaluationActionHistoryServiceImpl.save(newEntry);


    }
    
    @Override
    public void closeEvaluationPDF(Long id, Long actionAuthor, byte[] zip) {

        QuestionnaireVO questionnaire = QuestionnaireConverter.entityToVO(repository.findByEvaluationId(id));
        questionnaire.getEvaluation().setEvaluationState("Cerrado");
        questionnaire.getEvaluation().setCloseDate(LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid")));
        update(questionnaire);

        // Creo la entrada en el historial
        ActionVO action = ActionConverter.convertToVo(actionRepository.findById(5L).orElse(null));

        UserVO user = userService.findById(actionAuthor);

        EvaluationActionHistoryVO newEntry = new EvaluationActionHistoryVO();
        newEntry.setLastEdited(java.time.LocalDateTime.now(java.time.ZoneId.of("Europe/Madrid")));
        newEntry.setNDeleteState(1);
        newEntry.setActionId(action);
        newEntry.setEvaluationId(questionnaire.getEvaluation());
        newEntry.setUserId(user);
        evaluationActionHistoryServiceImpl.save(newEntry);

        String email = user.getEmail();
        // usar zip en el email
        executorService.submit(() -> {
            mailService.sendMailPDF(email, questionnaire, zip);
        });

    }

    public byte[] exportData(Long idType, Date startDate, Date endDate, String language, String format) {
        // Obtén los datos según las fechas y el formato
        List<Object[]> data = repository.getQuestionnaireDataInDateRange(idType, startDate, endDate, language);

        // Verifica si la lista tiene al menos un elemento
        if (data.size() < 2) {
            // Lanza una excepción o maneja el caso de lista vacía según tus necesidades
            throw new IllegalArgumentException("No hay datos válidos en el rango de fechas especificado.");
        }

        // Formatea los datos según el formato recibido (CSV o Excel)
        if ("csv".equals(format)) {
            try {
                return exportToCsv(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("excel".equals(format)) {
            try {
                return exportToExcel(data, "Data.xlsx");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Formato no válido: " + format);
        }
        return null;
    }
    
    public byte[] exportDataFilter(Long idType, Long year, String nombre, String language, String format) {
        // Obtén los datos según las fechas y el formato
        List<Object[]> data = repository.getQuestionnaireDataInDateRangeWithFilter(idType, year, nombre, language);

        // Verifica si la lista tiene al menos un elemento
        if (data.size() < 2) {
            // Lanza una excepción o maneja el caso de lista vacía según tus necesidades
            throw new IllegalArgumentException("No hay datos válidos en el rango de fechas especificado.");
        }

        // Formatea los datos según el formato recibido (CSV o Excel)
        if ("csv".equals(format)) {
            try {
                return exportToCsv(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("excel".equals(format)) {
            try {
                return exportToExcel(data, "Data.xlsx");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Formato no válido: " + format);
        }
        return null;
    }

    private byte[] exportToCsv(List<Object[]> data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(outputStreamWriter);

        for (Object[] row : data) {
            csvWriter.writeNext(convertToStringArray(row));
        }

        csvWriter.close();
        return outputStream.toByteArray();
    }

    private byte[] exportToExcel(List<Object[]> data, String fileName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data");

        int rowNum = 0;
        for (Object[] rowData : data) {
            XSSFRow row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : rowData) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    private String[] convertToStringArray(Object[] data) {
        String[] stringArray = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            stringArray[i] = String.valueOf(data[i]);
        }
        return stringArray;
    }

    public byte[] exportCertificate(Long id) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Cargar img
            String imagePath = "pdf/cabecera.png";

            try (InputStream in = getClass().getClassLoader().getResourceAsStream(imagePath)) {
                if (in != null) {
                    PDImageXObject image = PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(in), null);
                    contentStream.drawImage(image, 50, 700, 200, 85);
                } else {
                    // Handle the case where the image file is not found
                    System.out.println("Image not found: " + imagePath);
                }
            }

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 22); // Tamaño del texto

            // Texto: CERTIFICADO DE CUMPLIMIENTO
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 650); // Posición del texto
            contentStream.setNonStrokingColor(43, 43, 94); // Color azul oscuro
            contentStream.showText("CERTIFICADO DE CUMPLIMIENTO");
            contentStream.setNonStrokingColor(0, 0, 0); // Color negro
            contentStream.endText();

            // Obtener datos del repositorio desde la base de datos
            QuestionnaireEntity data = repository.getPdfData(id);
            System.out.println("Data questionnaire");
            System.out.println(data);
            System.out.println("Data repositorio padre de questionnaire");
            List<RepositoryEntity> dataRepo = repositoryRepository.findAllExcludeDeleted();
            System.out.println(dataRepo);
            // Texto: Contenido dinámico
            // contentStream.beginText();
            float maxLineWidth = 550; // Ancho máximo de la línea en puntos
            float fontSize = 10; // Tamaño inicial del texto en puntos
            float leading = 30; // Espacio vertical entre líneas en puntos

            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            // contentStream.newLineAtOffset(50, 600); // Nueva posición del texto

            // Obtener la fecha actual
            Calendar cal = Calendar.getInstance();// Definir el nuevo formato
            SimpleDateFormat sdf = new SimpleDateFormat("dd 'del' MM 'de' yyyy");
            String fechaActual = sdf.format(cal.getTime());
            String nombreRepo = data.getEvaluation().getUserRepository().getRepository().getInstitucion().getNombre(); // Reemplaza esto con el valor
                                                                                      // real del nombre del repositorio
            String porcentaje = data.getEvaluation().getEvaluationGrade().toString() + "%"; // Reemplaza esto con el
                                                                                            // valor real del porcentaje

            String texto = String.format(
                    "Por medio de la presente, DIAMAS certifica que:\n\n%s\n\nHa cumplido satisfactoriamente con un %s de los estándares y requisitos establecidos en nuestra evaluación de repositorios en la fecha %s.\n\nEste porcentaje de cumplimiento refleja el esfuerzo, compromiso y dedicación de %s hacia la excelencia y adherencia a las mejores prácticas establecidas por nuestra entidad.\n\nEsperamos que este reconocimiento sirva como un testimonio de su trabajo arduo y los aliente a continuar mejorando y elevando sus estándares.\n\nDado en Madrid el día %s.\n\nDIAMAS",
                    nombreRepo, porcentaje, fechaActual, nombreRepo, fechaActual);

            // Dividir el texto en líneas y mostrarlo en el PDF
            String[] lines = texto.split("\n");
            float yPosition = 615; // Posición vertical inicial del texto

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, fontSize); // Aplicar negrita al texto en negrita
            contentStream.newLineAtOffset(50, 615); // Posición vertical inicial del texto

            contentStream.showText(
                    "Por medio de la presente, DIAMAS certifica que:");
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
            contentStream.showText(nombreRepo); // Nombre del repositorio
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Ha cumplido satisfactoriamente con un ");
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize); // Aplicar negrita al porcentaje
            contentStream.showText(porcentaje); // Porcentaje
            contentStream.setFont(PDType1Font.HELVETICA, fontSize); // Restaurar el tipo de fuente
            contentStream.showText(" de los estándares y requisitos establecidos en nuestra evaluación de ");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("repositorios en la fecha ");
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
            contentStream.showText(fechaActual); // Fecha actual
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.newLineAtOffset(0, -leading);
            contentStream.showText("Este porcentaje de cumplimiento refleja el esfuerzo, compromiso y dedicación de ");
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
            float textWidthNombreRepo = PDType1Font.HELVETICA_BOLD.getStringWidth(
                    "Este porcentaje de cumplimiento refleja el esfuerzo, compromiso y dedicación de " + nombreRepo)
                    * fontSize / 1000;
            float textWidth = PDType1Font.HELVETICA_BOLD
                    .getStringWidth("Este porcentaje de cumplimiento refleja el esfuerzo, compromiso y dedicación de "
                            + nombreRepo + " hacia la excelencia y ")
                    * fontSize / 1000;
            // Verifica si el texto es demasiado ancho para la página
            if (textWidthNombreRepo > maxLineWidth) {
                contentStream.newLineAtOffset(0, -15); // Ajusta el valor de leading según sea necesario
                contentStream.showText(nombreRepo + " ");
            } else if (textWidth > maxLineWidth) {
                // Hace un salto de línea en nombreRepo
                contentStream.showText(nombreRepo);
                contentStream.newLineAtOffset(0, -15); // Ajusta el valor de leading según sea necesario
            } else {
                contentStream.showText(nombreRepo + " ");

            }
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.showText("hacia la excelencia y ");
            if (textWidthNombreRepo > maxLineWidth) {
                float nextLine = PDType1Font.HELVETICA_BOLD.getStringWidth(nombreRepo
                        + " hacia la excelencia y adherencia a las mejores prácticas establecidas por nuestra entidad.")
                        * fontSize / 1000;
                if (nextLine > maxLineWidth) {
                    contentStream.newLineAtOffset(0, -15);
                }
            } else if (textWidth < maxLineWidth) {
                contentStream.newLineAtOffset(0, -15);
            }
            contentStream.showText("adherencia a las mejores prácticas establecidas por nuestra entidad.");
            contentStream.newLineAtOffset(0, -leading);
            contentStream.showText(
                    "Esperamos que este reconocimiento sirva como un testimonio de su trabajo arduo y los aliente a continuar ");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("mejorando y elevando sus estándares.");
            contentStream.newLineAtOffset(0, -leading);
            contentStream.showText("Dado en ");
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
            contentStream.showText("Madrid el día ");
            contentStream.showText(fechaActual); // Fecha actual
            contentStream.newLineAtOffset(0, -leading);
            contentStream.showText("DIAMAS");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Puntuación desglosada:");
            // Finalizar y cerrar el co

            contentStream.endText();
            // Falta idType aqui
            List<StatsDividedVO> categorias = statsServiceImpl.getStatsByCategoriasDivididasByQuestionnaireId(id, id);
            
            contentStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }
    
    public List<QuestionnaireVO> getCertificatesByDate(Long idType, Date date) {
        // Utiliza el repositorio para buscar los archivos asociados a las preguntas
            return repository.findCertificatesByDate(idType, date).stream().map(QuestionnaireConverter::entityToVO).collect(Collectors.toList());

    }

    @Override
    public List<QuestionnaireVO> findByUserAndPeriodId(Long idType, Long userId, Long periodId) {
        return QuestionnaireConverter.ToVO(repository.findByUserAndPeriodId(idType, userId,periodId));
    }

    @Override
    public List<QuestionnaireVO> findByPeriodId(Long periodId) {
        return QuestionnaireConverter.ToVO(repository.findByPeriodId(periodId));
    }
}
