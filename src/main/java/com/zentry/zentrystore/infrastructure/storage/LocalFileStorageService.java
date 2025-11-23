package com.zentry.zentrystore.infrastructure.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileStorageService.class);

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:8087}")
    private String baseUrl;

    @Override
    public String storeFile(MultipartFile file, String folder) {
        try {
            // Validar archivo
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Cannot store empty file");
            }

            // Crear directorio si no existe
            Path uploadPath = Paths.get(uploadDir, folder);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : "";
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // Guardar archivo
            Path destinationFile = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Retornar URL
            String fileUrl = String.format("%s/uploads/%s/%s", baseUrl, folder, fileName);
            logger.info("File stored successfully: {}", fileUrl);

            return fileUrl;

        } catch (IOException e) {
            logger.error("Error storing file: {}", e.getMessage());
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            // Extraer nombre de archivo de la URL
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            String folder = fileUrl.substring(
                    fileUrl.indexOf("/uploads/") + 9,
                    fileUrl.lastIndexOf("/")
            );

            Path filePath = Paths.get(uploadDir, folder, fileName);
            Files.deleteIfExists(filePath);

            logger.info("File deleted successfully: {}", fileUrl);

        } catch (IOException e) {
            logger.error("Error deleting file {}: {}", fileUrl, e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return String.format("%s/uploads/%s", baseUrl, fileName);
    }
}