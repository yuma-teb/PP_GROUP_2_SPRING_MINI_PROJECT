package com.practice.javagroupiiminiproject.service.impl;
import io.minio.*;
import lombok.SneakyThrows;
import com.practice.javagroupiiminiproject.model.entity.FileMetadata;
import com.practice.javagroupiiminiproject.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.InputStream;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    //Get Bucket Name
    @Value("${minio.bucket.name}")
    private String bucketName;
    private final MinioClient minioClient;
    public FileServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }
    @SneakyThrows
    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        boolean bucketExits = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!bucketExits) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID()+ "." +StringUtils.getFilenameExtension(fileName);
        //provide image into file at bucket in MinIo
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .contentType(file.getContentType())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build()
        );
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/preview-file" +fileName)
                .toUriString();
        return FileMetadata.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .build();
    }
    @SneakyThrows
    @Override
    public InputStream getFileByFileName(String fileName) {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }
}
