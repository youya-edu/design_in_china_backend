package org.dic.demo.repository.servicestub;

import org.dic.demo.model.Composition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CompositionServiceStub {

    private static final AtomicLong idCounter = new AtomicLong(-1);
    private static final Map<Long, Composition> compositions = new ConcurrentHashMap<>();

    public Composition getCompositionById(long compositionId) {
        return compositions.get(compositionId);
    }

    public List<Composition> getAllCompositions() {
        return new ArrayList<>(compositions.values());
    }

    public Composition createComposition(Composition composition) {
        composition.setId(idCounter.incrementAndGet());
        if (composition.getName() == null) {
            composition.setName(randomCompositionName());
        }
        compositions.put(composition.getId(), composition);
        return composition;
    }

    public Composition updateComposition(Composition composition) {
        compositions.put(composition.getId(), composition);
        return composition;
    }

    public void deleteComposition(long compositionId) {
        compositions.remove(compositionId);
    }

    private String randomCompositionName() {
        String dictionary = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder dummyName = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            dummyName.append(dictionary.charAt(random.nextInt(dictionary.length())));
        }
        return dummyName.toString();
    }
}
