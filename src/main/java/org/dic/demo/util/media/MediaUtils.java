package org.dic.demo.util.media;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class MediaUtils {

  public static final String RESOURCES_DIR = getResourcesDirPath();
  public static final String PUBLIC_DIR = Paths.get(RESOURCES_DIR, "public").toString();
  public static final String DEFAULT_DIR_NAME = "default";

  private MediaUtils() {}

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
    // If it is default file, do not delete.
    if (fileName.contains(DEFAULT_DIR_NAME)) {
      return;
    }
    Path filePath = Paths.get(PUBLIC_DIR, fileName);
    Files.deleteIfExists(filePath);
  }

  public static String getDefaultFile(MediaType mediaType) {
    System.out.println(RESOURCES_DIR);
    File defaultDir = Paths.get(PUBLIC_DIR, mediaType.getDir(), DEFAULT_DIR_NAME).toFile();
    File[] defaultFiles = defaultDir.listFiles();
    if (defaultFiles == null || defaultFiles.length == 0) {
      return null;
    }
    return cleanPublicDir(
        defaultFiles[new Random().nextInt(defaultFiles.length)].getAbsolutePath());
  }

  private static String cleanPublicDir(String fileName) {
    return fileName.replace(PUBLIC_DIR, "");
  }

  private static String getResourcesDirPath() {
    URL url = MediaUtils.class.getClassLoader().getResource("application.yml");
    File file = null;
    try {
      assert url != null;
      file = Paths.get(url.toURI()).toFile();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    assert file != null;
    return FilenameUtils.getFullPath(file.getAbsolutePath());
  }
}
