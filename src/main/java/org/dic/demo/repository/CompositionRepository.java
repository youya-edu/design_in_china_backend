package org.dic.demo.repository;

import org.dic.demo.model.Composition;
import org.dic.demo.model.User;
import org.dic.demo.repository.servicestub.CompositionServiceStub;
import org.dic.demo.repository.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompositionRepository {

    private final CompositionServiceStub compositionServiceStub;
    private final UserServiceStub userServiceStub;

    public CompositionRepository(CompositionServiceStub compositionServiceStub, UserServiceStub userServiceStub) {
        this.compositionServiceStub = compositionServiceStub;
        this.userServiceStub = userServiceStub;
    }

    public Composition getCompositionById(long compositionId) {
        return compositionServiceStub.getCompositionById(compositionId);
    }

    public List<Composition> getAllCompositions() {
        return compositionServiceStub.getAllCompositions();
    }

    public Composition createComposition(long userId, Composition composition) {
        User user = userServiceStub.getUserById(userId);
        if (user == null) return null;
        Composition newComposition = compositionServiceStub.createComposition(composition);
        user.getCompositions().add(newComposition.getId());
        newComposition.setAuthor(user);
        return newComposition;
    }

    public Composition updateComposition(long userId, Composition composition) {
        Composition oldComposition = compositionServiceStub.getCompositionById(composition.getId());
        if (oldComposition.getAuthor().getId() != userId) {
            throw new RuntimeException("Permission denied.");
        }
        composition.setAuthor(oldComposition.getAuthor());
        return compositionServiceStub.updateComposition(composition);
    }

    public void deleteComposition(long userId, long compositionId) {
        Composition composition = compositionServiceStub.getCompositionById(compositionId);
        if (composition.getAuthor().getId() != userId) {
            throw new RuntimeException("Permission denied.");
        }
        compositionServiceStub.deleteComposition(compositionId);
    }
}
