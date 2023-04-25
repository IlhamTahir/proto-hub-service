package com.bilitech.api.core.service;

import com.bilitech.api.core.dto.FileUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    FileUploadDto initFileUpload() throws IOException;

    String getFileUri(String fileKey);

    void upload(MultipartFile file);
}
