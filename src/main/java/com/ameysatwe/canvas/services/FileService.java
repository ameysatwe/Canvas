package com.ameysatwe.canvas.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    // Ensure that the directory exists
    public FileService() {
        File dir = new File("./uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Method to store the uploaded file
    public String storeFile(MultipartFile file) throws IOException {
        // Generate a unique file name to avoid conflicts
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.contains(".") ?
                originalFileName.substring(originalFileName.lastIndexOf("."))
                : ".txt"; // Default to .txt if no extension is found

        String fileName = UUID.randomUUID().toString() + fileExtension;

        // Define the destination path for storing the file
        Path targetLocation = Paths.get(uploadDir).resolve(fileName);

        // Copy the file to the target location
        Files.copy(file.getInputStream(), targetLocation);

        // Return the file URL or path (can be used in the database)
        return targetLocation.toString();
    }

    // Method to get the file URL (for retrieving the file if needed)
    public Path loadFile(String fileName) {
        return Paths.get(uploadDir).resolve(fileName);
    }
}
