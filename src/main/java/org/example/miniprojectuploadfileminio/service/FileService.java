package org.example.miniprojectuploadfileminio.service;
import org.example.miniprojectuploadfileminio.model.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
public interface FileService {
    FileMetadata uploadFile(MultipartFile file);
    InputStream getFileByFileName(String fileName);
}
