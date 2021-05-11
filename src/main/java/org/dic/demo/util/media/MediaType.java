package org.dic.demo.util.media;

public enum MediaType {
  AVATAR("/img/avatar");

  private final String dir;

  MediaType(String dir) {
    this.dir = dir;
  }

  public String getDir() {
    return dir;
  }
}
