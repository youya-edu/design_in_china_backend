package org.dic.demo.user.resource;

import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.service.CompositionService;
import org.dic.demo.util.HttpUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(
        value = {"/users/{userId}/compositions"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
)
public class UserCompositionResource {

    private final CompositionService compositionService;

    public UserCompositionResource(CompositionService compositionService) {
        this.compositionService = compositionService;
    }

    @GetMapping("/{compositionId}")
    public ResponseEntity<Composition> getComposition(@PathVariable("compositionId") long compositionId) {
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
            HttpServletRequest req
    ) {
        return ResponseEntity.created(HttpUtils.uriWithPath(req, String.valueOf(composition.getId())))
                .body(compositionService.createComposition(userId, composition));
    }

    @PutMapping
    public ResponseEntity<Composition> updateComposition(@PathVariable("userId") long userId, @RequestBody Composition composition) {
        return ResponseEntity.ok(compositionService.updateComposition(userId, composition));
    }

    @DeleteMapping("/{compositionId}")
    public ResponseEntity<Void> deleteComposition(@PathVariable("userId") long userId, @PathVariable("compositionId") long compositionId) {
        compositionService.deleteComposition(userId, compositionId);
        return ResponseEntity.noContent().build();
    }
}
