package com.access.projectpractice.firebase;

import com.access.projectpractice.file.FileResponse;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
//import com.google.cloud.storage.BlobInfo;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.cloud.StorageClient;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseStorageService {
    private final FirebaseApp firebaseApp;

    private final String BUCKET_NAME = "practice-project-8bb4c.appspot.com";

    public Map<String, String> uploadFile(MultipartFile file) throws IOException {

        Storage storage = StorageClient.getInstance().bucket("practice-project-8bb4c.appspot.com").getStorage();

        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String mimeType = file.getContentType();
        long fileSize = file.getSize();

        BlobId blobId = BlobId.of(BUCKET_NAME, filename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
        Blob blob = storage.create(blobInfo, file.getBytes());

        Map<String, String> result = new HashMap<>();
        result.put("filename", filename);
        result.put("downloadUrl", blob.getMediaLink());
        result.put("fetchUrl", "https://storage.googleapis.com/practice-project-8bb4c.appspot.com/" + filename);
        result.put("type", mimeType);
        result.put("size", String.valueOf(fileSize));

        return result;
    }

    public String getFileUrl(String fileName) {
        Storage storage = StorageClient.getInstance().bucket("practice-project-8bb4c.appspot.com").getStorage();
        BlobId blobId = BlobId.of("practice-project-8bb4c.appspot.com", "file/" + fileName);

        Blob blob = storage.get(blobId);
        if (blob == null) {
            throw new RuntimeException("File not found!");
        }

        return blob.getMediaLink();
    }
}
