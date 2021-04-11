package org.dic.demo.repository;

import org.dic.demo.model.Composition;
import org.dic.demo.model.User;
import org.dic.demo.repository.servicestub.CompositionServiceStub;
import org.dic.demo.repository.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        if (user == null) return null;
        Composition newComposition = CompositionServiceStub.createComposition(composition);
        user.getCompositions().add(newComposition.getId());
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
