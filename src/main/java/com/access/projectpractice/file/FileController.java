package com.access.projectpractice.file;

import com.access.projectpractice.firebase.FirebaseStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class FileController {
    private final FirebaseStorageService firebaseStorageService;

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, String> result = firebaseStorageService.uploadFile(file);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<String> getFileUrl(@PathVariable String filename) {
        try {
            String fileUrl = firebaseStorageService.getFileUrl(filename);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Error retrieving file URL");
        }
    }
}
