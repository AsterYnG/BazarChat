package com.arinc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@PreAuthorize("permitAll()")
@Service
@RequiredArgsConstructor
public class ImageService {
    private final String BASE_PATH = "images/";


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

    public Optional<InputStream> get(String imagePath) throws IOException {
        var fullPath = Path.of(BASE_PATH, imagePath);

        return Files.exists(fullPath)
                ? Optional.of(Files.newInputStream(fullPath))
                : Optional.empty();
    }
}
