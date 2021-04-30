package org.dic.demo.composition.resource;

import java.util.List;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.service.CompositionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = {"/compositions"},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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
  public ResponseEntity<List<Composition>> getAllCompositions() {
    return ResponseEntity.ok(compositionService.getAllCompositions());
  }
}
