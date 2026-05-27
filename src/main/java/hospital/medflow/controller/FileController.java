package hospital.medflow.controller;

import hospital.medflow.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static hospital.medflow.utils.ApiPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/profile")
public class FileController {

    private final FileService service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) {
        return service.upload(file);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<FileSystemResource> download(@PathVariable String id) {
        return service.download(id);
    }

}
