package es.soltel.recolecta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import es.soltel.recolecta.service.impl.FileServiceImpl;
import es.soltel.recolecta.vo.FileVO;
import io.swagger.annotations.ApiOperation;
import es.soltel.recolecta.entity.FileEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.UrlResource;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileServiceImpl fileService;

    @GetMapping("/{id}")
    public FileVO getById(@PathVariable Long id) {
        return fileService.findById(id);
    }

    @GetMapping
    public List<FileVO> getAll() {
        return fileService.findAll();
    }

    @PostMapping("/create/{questionnaireId}/{questionId}")
    public FileVO create(@RequestPart("fichero") MultipartFile fichero, @PathVariable Long questionnaireId,
            @PathVariable Long questionId) {
        return fileService.create(fichero, questionnaireId, questionId);
    }

    @PutMapping
    public FileVO update(@RequestBody FileVO file) {
        return fileService.update(file);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fileService.delete(id);
    }

    @ApiOperation(value = "Elimina un documento por completo, tanto de la base de datos como del sistema de ficheros para siempre, esta acción es irreversible", response = Long.class)
    @DeleteMapping("deleteHard/{id}")
    public void deleteHard(@PathVariable Long id) {
        fileService.deleteHard(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        return fileService.downloadFileById(id);
    }

    @PostMapping("/downloadZip")
    public ResponseEntity<Resource> downloadFilesByQuestionIds(@RequestBody Long[] questionIds) {
        return fileService.getFilesForQuestions(questionIds);
    }


}
