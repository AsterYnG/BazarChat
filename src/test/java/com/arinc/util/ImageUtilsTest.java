package com.arinc.util;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilsTest {
    private ImageUtils imageUtils = new ImageUtils();
    private final String BASE_PATH = "C:/MySpace/LocalStorage/BazarChat/images/";
    private final String IMAGE_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcAOQOXpz3-LzbrnJJkZnn5jejcz7HPJyAkw&s";

    @Test
    void loadImageByUrl() {
        ReflectionTestUtils.setField(imageUtils, "BASE_PATH", BASE_PATH);
        try {
            var imagePath = imageUtils.loadImageByUrl(IMAGE_URL);
            Path path = Path.of(BASE_PATH+imagePath);
            assertTrue(Files.exists(path));
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}