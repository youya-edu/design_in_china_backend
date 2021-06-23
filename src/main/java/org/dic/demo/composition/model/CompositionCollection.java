package org.dic.demo.composition.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompositionCollection {
  private List<Composition> compositions = new ArrayList<>();
  private long totalSize;
}
