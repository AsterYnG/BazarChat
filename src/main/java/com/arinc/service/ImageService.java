package com.arinc.service;

import com.arinc.util.PropertiesUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageService {

    private final static ImageService INSTANCE = new ImageService();
    private final String BASE_PATH = PropertiesUtil.get("image.base.url");

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
    public static ImageService getInstance() {
        return INSTANCE;
    }
}
