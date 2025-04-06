package com.practice.javagroupiiminiproject.controller;

import com.practice.javagroupiiminiproject.model.entity.FileMetadata;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import com.practice.javagroupiiminiproject.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
@RestController
@RequestMapping("api/v1/files")
public class FileController {

    private final FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<FileMetadata>> uploadFile(@RequestParam MultipartFile file){

        System.out.println("Uploading the filessss");
        FileMetadata fileMetadata = fileService.uploadFile(file);
        APIResponse<FileMetadata> apiResponse = APIResponse.<FileMetadata>builder()
                .success(true)
                .message("Upload file Successfully")
                .status(HttpStatus.CREATED)
                .payload(fileMetadata)
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/preview-file/{file-name}")
    public ResponseEntity<?> getFileByFileName(@PathVariable("file-name") String fileName) throws IOException {
        InputStream inputStream = (InputStream) fileService.getFileByFileName(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(inputStream.readAllBytes());
    }
}
