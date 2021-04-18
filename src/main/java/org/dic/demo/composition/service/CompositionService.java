package org.dic.demo.composition.service;

import java.util.List;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.repository.CompositionRepository;
import org.springframework.stereotype.Service;

@Service
public class CompositionService {

  private final CompositionRepository compositionRepository;

  public CompositionService(CompositionRepository compositionRepository) {
    this.compositionRepository = compositionRepository;
  }

  public Composition getCompositionById(long compositionId) {
    return compositionRepository.getCompositionById(compositionId);
  }

  public List<Composition> getAllCompositions() {
    return compositionRepository.getAllCompositions();
  }

  public Composition createComposition(long userId, Composition composition) {
    return compositionRepository.createComposition(userId, composition);
  }

  public Composition updateComposition(long userId, Composition composition) {
    return compositionRepository.updateComposition(userId, composition);
  }

  public void deleteComposition(long userId, long compositionId) {
    compositionRepository.deleteComposition(userId, compositionId);
  }
}
