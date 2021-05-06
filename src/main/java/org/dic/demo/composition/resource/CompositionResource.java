package org.dic.demo.composition.resource;

import java.util.List;
import java.util.stream.Collectors;
import org.dic.demo.composition.model.Composition;
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
  public ResponseEntity<Composition> getComposition(
      @PathVariable("compositionId") long compositionId) {
    return ResponseEntity.ok(compositionService.getCompositionById(compositionId));
  }

  @GetMapping
  public ResponseEntity<List<ViewComposition>> getAllCompositions() {
    return ResponseEntity.ok(
        compositionService.getAllCompositions().stream()
            .map(ViewComposition::fromDomainObject)
            .collect(Collectors.toList()));
  }
}
