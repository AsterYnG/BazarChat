package com.arinc.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
class ImageServiceTest {
    private String BASE_PATH = "C:/MySpace/LocalStorage/BazarChat/images/";
    private final Path TEST_IMAGE_PATH = Path.of("src/test/resources/test-image.png");
    private final ImageService imageService = new ImageService();

    @Test
    void upload() throws IOException {
        ReflectionTestUtils.setField(imageService, "BASE_PATH", BASE_PATH);
        try (var testImageInputStream = Files.newInputStream(TEST_IMAGE_PATH)) {
            imageService.upload("test-image.png", testImageInputStream);
            assertTrue(Files.exists(Path.of(BASE_PATH+ "test-image.png")));
            Files.deleteIfExists(Path.of(BASE_PATH+ "test-image.png"));
        }
    }
}