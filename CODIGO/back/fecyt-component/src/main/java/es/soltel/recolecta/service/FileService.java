package es.soltel.recolecta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import es.soltel.recolecta.entity.FileEntity;
import es.soltel.recolecta.vo.FileVO;
import es.soltel.recolecta.vo.QuestionnaireQuestionVO;

public interface FileService {

    List<FileVO> findAll();

    FileVO findById(Long id);

    FileVO create(FileVO fileVO);

    FileVO update(FileVO fileVO);

    void delete(Long id);

    FileVO create(MultipartFile fichero, Long questionnaireId, Long questionId);

    FileVO storeFile(MultipartFile file, Long questionnaireId, Long questionId);

    void deleteHard(Long id);

}
