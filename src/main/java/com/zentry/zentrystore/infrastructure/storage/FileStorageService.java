package com.zentry.zentrystore.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file, String folder);

    void deleteFile(String fileUrl);

    String getFileUrl(String fileName);
}