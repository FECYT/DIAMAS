package es.soltel.recolecta.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileStorageServiceImpl {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final Path fileStorageLocation;


    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }
        catch (Exception ex) {
            throw new Error("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }

}
