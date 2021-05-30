package org.dic.demo.composition.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.dic.demo.common.PaginationParam;
import org.dic.demo.composition.database.CompositionDao;
import org.dic.demo.composition.database.DatabaseComposition;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.CompositionCollection;
import org.dic.demo.composition.servicestub.CompositionServiceStub;
import org.dic.demo.user.model.User;
import org.dic.demo.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class CompositionRepository {

  private final CompositionDao compositionDao;
  private final UserRepository userRepository;

  public Composition getCompositionById(long compositionId) {
    return convertToComposition(compositionDao.getCompositionById(compositionId));
  }

  public CompositionCollection getAllCompositions(PaginationParam paginationParam) {
    PageHelper.startPage(paginationParam.getPage(), paginationParam.getPageSize());
    List<DatabaseComposition> databaseCompositions = compositionDao.getAllCompositions();
    return new CompositionCollection(
        convertToCompositions(databaseCompositions), ((Page) databaseCompositions).getTotal());
  }

  public CompositionCollection getCompositionsByUserId(long userId) {
    PaginationParam paginationParam = new PaginationParam();
    PageHelper.startPage(paginationParam.getPage(), paginationParam.getPageSize());
    List<DatabaseComposition> databaseCompositions = compositionDao.getCompositionsByUserId(userId);
    return new CompositionCollection(
        convertToCompositions(databaseCompositions), ((Page) databaseCompositions).getTotal());
  }

  private List<Composition> convertToCompositions(List<DatabaseComposition> databaseCompositions) {
    return databaseCompositions.stream()
        .map(this::convertToComposition)
        .collect(Collectors.toList());
  }

  private Composition convertToComposition(DatabaseComposition databaseComposition) {
    Composition composition = databaseComposition.toDomainObject();
    User author = userRepository.getUserById(databaseComposition.getAuthorId());
    composition.setAuthor(author);
    return composition;
  }

  public Composition createComposition(long userId, Composition composition) {
    throw new UnsupportedOperationException("This method has not been implemented yet.");
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
