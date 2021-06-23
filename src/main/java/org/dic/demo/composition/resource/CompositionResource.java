package org.dic.demo.composition.resource;

import java.util.stream.Collectors;
import org.dic.demo.common.PaginationParam;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.CompositionCollection;
import org.dic.demo.composition.service.CompositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compositions")
public class CompositionResource {

  private final CompositionService compositionService;

  public CompositionResource(CompositionService compositionService) {
    this.compositionService = compositionService;
  }

  @GetMapping("/{compositionId}")
  public ResponseEntity<ViewComposition> getComposition(
      @PathVariable("compositionId") long compositionId) {
    return ResponseEntity.ok(compositionService.getCompositionById(compositionId).toViewObject());
  }

  @GetMapping
  public ResponseEntity<ViewCompositionCollection> getAllCompositions(
      PaginationParam paginationParam) {
    CompositionCollection compositionCollection =
        compositionService.getAllCompositions(paginationParam);
    return ResponseEntity.ok(
        new ViewCompositionCollection(
            compositionCollection.getCompositions().stream()
                .map(Composition::toViewObject)
                .collect(Collectors.toList()),
            compositionCollection.getTotalSize()));
  }
}
