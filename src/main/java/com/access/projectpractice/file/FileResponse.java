package com.access.projectpractice.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileUrl;
    private String fileType;
    private Long size;
}
