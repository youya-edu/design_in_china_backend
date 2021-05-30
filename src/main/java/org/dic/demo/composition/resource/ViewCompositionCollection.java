package org.dic.demo.composition.resource;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ViewCompositionCollection {
  private final List<ViewComposition> compositions;
  private final long totalSize;
}
