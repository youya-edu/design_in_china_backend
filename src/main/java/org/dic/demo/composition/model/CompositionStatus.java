package org.dic.demo.composition.model;

public enum CompositionStatus {
  DRAFT,
  PUBLIC,
  DISCARD;

  public static CompositionStatus from(String name) {
    for (CompositionStatus status : CompositionStatus.values()) {
      if (status.name().equals(name)) {
        return status;
      }
    }
    return null;
  }
}
