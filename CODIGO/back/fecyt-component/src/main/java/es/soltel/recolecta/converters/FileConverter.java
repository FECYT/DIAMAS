package es.soltel.recolecta.converters;

import es.soltel.recolecta.entity.FileEntity;
import es.soltel.recolecta.entity.QuestionnaireQuestionEntity;
import es.soltel.recolecta.vo.FileVO;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileConverter {

    public static FileVO toVO(FileEntity fileEntity) {
        if (fileEntity == null) {
            return null;
        }

        FileVO fileVO = new FileVO();

        fileVO.setId(fileEntity.getId());
        fileVO.setNormalizedDocumentName(fileEntity.getNormalizedDocumentName());
        fileVO.setFileFormat(fileEntity.getFileFormat());
        fileVO.setFileSize(fileEntity.getFileSize());
        fileVO.setAswerDateTime(fileEntity.getAswerDateTime());
        fileVO.setFileHash(fileEntity.getFileHash());
        fileVO.setFilePath(fileEntity.getFilePath());
        fileVO.setNDeleteState(fileEntity.getNDeleteState());

        if (fileEntity.getQuestionnaireQuestion() != null) {
            fileVO.setQuestionnaireQuestion(QuestionnaireQuestionConverter.toVO(fileEntity.getQuestionnaireQuestion()));
        }

        return fileVO;
    }

    public static FileEntity toEntity(FileVO fileVO) {
        if (fileVO == null) {
            return null;
        }

        FileEntity fileEntity = new FileEntity();

        fileEntity.setId(fileVO.getId());
        fileEntity.setNormalizedDocumentName(fileVO.getNormalizedDocumentName());
        fileEntity.setFileFormat(fileVO.getFileFormat());
        fileEntity.setFileSize(fileVO.getFileSize());
        fileEntity.setAswerDateTime(fileVO.getAswerDateTime());
        fileEntity.setFileHash(fileVO.getFileHash());
        fileEntity.setFilePath(fileVO.getFilePath());
        fileEntity.setNDeleteState(fileVO.getNDeleteState());

        // Nota: Para la relación con QuestionnaireQuestionEntity, probablemente necesites 
        // más lógica aquí, por ejemplo, buscando la entidad relacionada en la base de datos 
        // a través de su repositorio. Por simplicidad, aquí simplemente establezco la relación 
        // basada en el ID.
        if (fileVO.getQuestionnaireQuestion() != null) {
            QuestionnaireQuestionEntity qqEntity = new QuestionnaireQuestionEntity();
            qqEntity.setId(fileVO.getQuestionnaireQuestion().getId());
            fileEntity.setQuestionnaireQuestion(qqEntity);
        }

        return fileEntity;
    }

    public static String calculateSHA256(MultipartFile multipartFile) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] fileBytes = multipartFile.getBytes(); // Obtiene los bytes del MultipartFile
            md.update(fileBytes);
            byte[] hashBytes = md.digest();
    
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                hexString.append(String.format("%02x", hashByte));
            }
    
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular el hash: Algoritmo SHA-256 no disponible");
        }
    }
}
