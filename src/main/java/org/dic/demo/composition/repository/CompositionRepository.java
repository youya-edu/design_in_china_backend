package org.dic.demo.composition.repository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.dic.demo.composition.database.CompositionDao;
import org.dic.demo.composition.database.DatabaseComposition;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.servicestub.CompositionServiceStub;
import org.dic.demo.user.model.User;
import org.dic.demo.user.repository.UserRepository;
import org.dic.demo.user.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CompositionRepository {

  private final CompositionDao compositionDao;
  private final UserRepository userRepository;

  public Composition getCompositionById(long compositionId) {
    return CompositionServiceStub.getCompositionById(compositionId);
  }

  public List<Composition> getAllCompositions() {
    return compositionDao.getAllCompositions().stream()
        .map(
            databaseComposition ->
                DatabaseComposition.asDomainObject(
                    databaseComposition,
                    userRepository.getUserById(databaseComposition.getAuthorId())))
        .collect(Collectors.toList());
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
