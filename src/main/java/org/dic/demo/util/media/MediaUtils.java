package org.dic.demo.util.media;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class MediaUtils {

  public static final String CLASS_PATH = MediaUtils.class.getResource("/").getPath();
  public static final String PUBLIC_DIR = CLASS_PATH + "public";

  public static String uploadFile(MediaType mediaType, MultipartFile multipartFile)
      throws IOException {
    Path uploadDir = Paths.get(PUBLIC_DIR, mediaType.getDir());

    if (Files.notExists(uploadDir)) {
      Files.createDirectories(uploadDir);
    }

    String ext = FilenameUtils.getExtension(multipartFile.getResource().getFilename());
    String fileName =
        UUID.randomUUID().toString().replace("-", "").concat(".").concat(ext == null ? "" : ext);
    Path filePath = uploadDir.resolve(fileName);

    try (InputStream inputStream = multipartFile.getInputStream()) {
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new IOException(String.format("Could not save file: %s", filePath.toString()), e);
    }

    return cleanPublicDir(filePath.toString());
  }

  public static void deleteFile(String fileName) throws IOException {
    Path filePath = Paths.get(PUBLIC_DIR, fileName);
    Files.deleteIfExists(filePath);
  }

  private static String cleanPublicDir(String fileName) {
    return fileName.replace(PUBLIC_DIR, "");
  }
}
