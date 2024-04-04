package com.arinc.util;

import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Comparator;

@Component
public class ImageUtils {
    private final String BASE_PATH = "images/";

    public String loadImage(String picUrl)
    {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

// Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }

            String fileName = parseFileName(picUrl);
            var fullPath = Path.of( BASE_PATH , fileName);


            URLConnection openConnection = new URI(picUrl).toURL().openConnection();
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            try (var stream = openConnection.getInputStream()){
                Files.write(fullPath, stream.readAllBytes(), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
                return fullPath.toString();
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String parseFileName(String picUrl) {
        String[] split = picUrl.split("/");
        //TODO: Реализовать поиск названия
        return split[split.length -1] + ".png";
    }
}
