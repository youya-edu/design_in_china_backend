package org.dic.demo.composition.repository;

import java.util.List;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.servicestub.CompositionServiceStub;
import org.dic.demo.user.model.User;
import org.dic.demo.user.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

@Repository
public class CompositionRepository {

  public Composition getCompositionById(long compositionId) {
    return CompositionServiceStub.getCompositionById(compositionId);
  }

  public List<Composition> getAllCompositions() {
    return CompositionServiceStub.getAllCompositions();
  }

  public Composition createComposition(long userId, Composition composition) {
    User user = UserServiceStub.getUserById(userId);
    if (user == null) {
      return null;
    }
    Composition newComposition = CompositionServiceStub.createComposition(composition);
    user.getCompositions().add(newComposition);
    newComposition.setAuthor(user);
    return newComposition;
  }

  public Composition updateComposition(long userId, Composition composition) {
    Composition oldComposition = CompositionServiceStub.getCompositionById(composition.getId());
    if (oldComposition.getAuthor().getId() != userId) {
      throw new RuntimeException("Permission denied.");
    }
    composition.setAuthor(oldComposition.getAuthor());
    return CompositionServiceStub.updateComposition(composition);
  }

  public void deleteComposition(long userId, long compositionId) {
    Composition composition = CompositionServiceStub.getCompositionById(compositionId);
    if (composition.getAuthor().getId() != userId) {
      throw new RuntimeException("Permission denied.");
    }
    CompositionServiceStub.deleteComposition(compositionId);
  }
}
