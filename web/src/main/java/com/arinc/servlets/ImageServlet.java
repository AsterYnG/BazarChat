package com.arinc.servlets;

import com.arinc.service.ImageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    private final ImageService imageService = ImageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var requestUri = req.getRequestURI();
        var imagePath = requestUri.replace("/images", "");

        imageService.get(imagePath)
                .ifPresentOrElse(image -> {
                    resp.setContentType("application/octet-stream");
                    try {
                        writeImage(image, resp);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, () -> resp.setStatus(404));
    }

    private void writeImage(InputStream image, HttpServletResponse resp) throws IOException {
        try (image; var outputStream = resp.getOutputStream()) {
            int currentByte;
            while ((currentByte = image.read()) != -1) {
                outputStream.write(currentByte);
            }
        }
    }
}
