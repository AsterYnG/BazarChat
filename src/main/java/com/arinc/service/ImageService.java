package com.arinc.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@PreAuthorize("permitAll()")
@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${image.path}")
    private String BASE_PATH;


    public void upload(String imagePath, InputStream content) {
        var fullPath = Path.of(BASE_PATH, imagePath);
        try (content) {
            var allBytes = content.readAllBytes();
            if (!(allBytes.length == 0)) {
                Files.write(fullPath, allBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
