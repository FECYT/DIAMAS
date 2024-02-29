package es.soltel.recolecta.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.soltel.recolecta.entity.FileEntity;
import es.soltel.recolecta.repository.FileRepository;
import es.soltel.recolecta.repository.QuestionnaireQuestionRepository;
import es.soltel.recolecta.service.FileService;
import es.soltel.recolecta.converters.FileConverter;
import es.soltel.recolecta.converters.QuestionnaireQuestionConverter;
import es.soltel.recolecta.vo.FileVO;
import es.soltel.recolecta.vo.QuestionnaireQuestionVO;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileConverter fileConverter;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private final FileStorageServiceImpl fileStorageServiceImpl;


    public FileServiceImpl(FileStorageServiceImpl fileStorageServiceImpl) {
        this.fileStorageServiceImpl = fileStorageServiceImpl;
    }

    @Override
    public List<FileVO> findAll() {
        return fileRepository.findAll().stream().map(FileConverter::toVO).collect(Collectors.toList());
    }

    @Override
    public FileVO findById(Long id) {
        return fileConverter.toVO(fileRepository.findById(id).orElse(null));
    }

    @Override
    public FileVO create(MultipartFile fichero, Long questionnaireId, Long questionId) {
        try {
            return this.storeFile(fichero, questionnaireId, questionId);
        }
        catch (Error error) {
            throw new Error("Error al crear el fichero " + fichero.getName());
        }
    }

    @Override
    public FileVO create(FileVO fileVO) {
        fileVO.setFilePath(this.fileStorageServiceImpl.getFileStorageLocation().toString());
        return fileConverter.toVO(fileRepository.save(fileConverter.toEntity(fileVO)));
    }

    @Override
    public FileVO update(FileVO fileVO) {
        return fileConverter.toVO(fileRepository.save(fileConverter.toEntity(fileVO)));
    }

    @Override
    public void delete(Long id) {
        FileEntity fileEntity = fileRepository.findById(id).orElse(null);
        if (fileEntity != null) {
            fileEntity.setNDeleteState(2);
            // deleteFileFromSystem(fileEntity.getFilePath());
            fileRepository.save(fileEntity);
        }
    }

    @Override
    public void deleteHard(Long id) {
        FileEntity fileEntity = fileRepository.findById(id).orElse(null);
        if (fileEntity != null) {
            deleteFileFromSystem(fileEntity.getFilePath());
            fileRepository.delete(fileEntity);
        }
    }

    @Override
    public FileVO storeFile(MultipartFile file, Long questionnaireId, Long questionId) {
        String originalFileName = file.getOriginalFilename();

        try {
            if (originalFileName.contains("..")) {
                throw new Error("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            QuestionnaireQuestionVO questionnaireQuestion = QuestionnaireQuestionConverter.toVO(
                    questionnaireQuestionRepository.findByQuestionnaire_IdAndQuestion_Id(questionnaireId, questionId));

            String descripcionPeriodo = questionnaireQuestion.getQuestionnaire().getPeriod().getDescription();
            String periodId = questionnaireQuestion.getQuestionnaire().getPeriod().getId().toString();
            String stringIdEvaluacion = questionnaireQuestion.getQuestionnaire().getEvaluation().getId().toString();
            String stringIdPregunta = questionnaireQuestion.getQuestion().getId().toString();
            String nombreRepositorio = questionnaireQuestion.getQuestionnaire().getEvaluation().getUserRepository().getRepository().getInstitucion().getNombre();

            // Mantener la extensión original del archivo
            String extension = FilenameUtils.getExtension(originalFileName);

            FileEntity fileEntity = new FileEntity();
            fileEntity.setNormalizedDocumentName(originalFileName); // Por ahora guardamos el nombre original
            fileEntity.setFileFormat(extension);
            fileEntity.setFileSize(file.getSize());
            fileEntity.setFilePath(""); // Lo dejamos vacío por ahora
            fileEntity.setAswerDateTime(LocalDateTime.now(ZoneId.of("Europe/Madrid")));
            fileEntity.setFileHash(FileConverter.calculateSHA256(file));
            fileEntity.setNDeleteState(1);
            fileEntity.setQuestionnaireQuestion(QuestionnaireQuestionConverter.toEntity(questionnaireQuestion));

            // Guardamos el archivo en la base de datos y obtenemos el ID
            FileEntity savedFileEntity = fileRepository.save(fileEntity);

            // Ahora que tenemos el ID, generamos el nombre final
            String concatenatedString = String.join("_", descripcionPeriodo,
                    stringIdEvaluacion, stringIdPregunta, nombreRepositorio, Long.toString(savedFileEntity.getId()));
            String newFileName = concatenatedString + "." + extension;

            Path targetLocation = this.fileStorageServiceImpl.getFileStorageLocation().resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Ahora actualizamos la entidad con el path correcto
            savedFileEntity.setNormalizedDocumentName(newFileName);
            savedFileEntity.setFilePath(targetLocation.toString());
            fileRepository.save(savedFileEntity); // Actualizamos el registro en la base de datos

            return FileConverter.toVO(savedFileEntity);

        }
        catch (IOException ex) {
            throw new Error("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }


    public void deleteFileFromSystem(String filePath) {
        try {
            Path fileToDeletePath = Paths.get(filePath);
            Files.delete(fileToDeletePath);
        }
        catch (IOException ex) {
            throw new RuntimeException("Error al eliminar el archivo: " + filePath, ex);
        }
    }

    public ResponseEntity<Resource> downloadFileById(Long id) {
        FileEntity file = fileRepository.findById(id).orElse(null);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(file.getFilePath());
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
        }
        catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }

        String contentType = "application/octet-stream";
        try {
            contentType = Files.probeContentType(filePath);
        }
        catch (IOException e) {
            // Logger puede ser usado aquí para registrar el error
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getNormalizedDocumentName() + "\"")
                .body(resource);
    }

    public ResponseEntity<Resource> getFilesForQuestions(Long[] questionIds) {
        // Utiliza el repositorio para buscar los archivos asociados a las preguntas
        try {
            List<FileEntity> fileEntities = fileRepository.findFilesByQuestionIds(questionIds);
            if (fileEntities.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Comprimir los archivos en un ZIP
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
                for (FileEntity fileEntity : fileEntities) {
                    String fileName = fileEntity.getNormalizedDocumentName();
                    Path filePath = Paths.get(fileEntity.getFilePath());
                    Resource resource = new UrlResource(filePath.toUri());

                    if (resource.exists()) {
                        byte[] fileData = Files.readAllBytes(filePath);

                        ZipEntry zipEntry = new ZipEntry(fileName);
                        zipOutputStream.putNextEntry(zipEntry);
                        zipOutputStream.write(fileData);
                        zipOutputStream.closeEntry();
                    }
                }
            }

            // Configurar la respuesta para la descarga del ZIP
            ByteArrayResource zipResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"archivos.zip\"");

            return ResponseEntity.ok().headers(headers).contentLength(byteArrayOutputStream.size())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(zipResource);
        }
        catch (IOException e) {
            // Manejo de errores, registra el error o devuelve una respuesta de error apropiada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
