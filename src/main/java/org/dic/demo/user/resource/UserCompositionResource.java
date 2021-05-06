package org.dic.demo.user.resource;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.service.CompositionService;
import org.dic.demo.util.HttpUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/compositions")
public class UserCompositionResource {

  private final CompositionService compositionService;

  public UserCompositionResource(CompositionService compositionService) {
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

  @PostMapping
  public ResponseEntity<Composition> createComposition(
      @PathVariable("userId") long userId,
      @RequestBody Composition composition,
      HttpServletRequest req) {
    return ResponseEntity.created(HttpUtils.uriWithPath(req, String.valueOf(composition.getId())))
        .body(compositionService.createComposition(userId, composition));
  }

  @PutMapping
  public ResponseEntity<Composition> updateComposition(
      @PathVariable("userId") long userId, @RequestBody Composition composition) {
    return ResponseEntity.ok(compositionService.updateComposition(userId, composition));
  }

  @DeleteMapping("/{compositionId}")
  public ResponseEntity<Void> deleteComposition(
      @PathVariable("userId") long userId, @PathVariable("compositionId") long compositionId) {
    compositionService.deleteComposition(userId, compositionId);
    return ResponseEntity.noContent().build();
  }
}
