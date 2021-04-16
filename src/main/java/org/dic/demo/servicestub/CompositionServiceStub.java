package org.dic.demo.servicestub;

import org.dic.demo.exception.CompositionNotFoundException;
import org.dic.demo.model.Composition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CompositionServiceStub {

    private static final AtomicLong idCounter = new AtomicLong(-1);
    private static final Map<Long, Composition> compositions = new ConcurrentHashMap<>();

    public static Composition getCompositionById(long compositionId) {
        checkCompositionExistence(compositionId);
        return compositions.get(compositionId);
    }

    public static List<Composition> getAllCompositions() {
        return new ArrayList<>(compositions.values());
    }

    public static Composition createComposition(Composition composition) {
        composition.setId(idCounter.incrementAndGet());
        if (composition.getName() == null) {
            composition.setName(randomCompositionName());
        }
        compositions.put(composition.getId(), composition);
        return composition;
    }

    public static Composition updateComposition(Composition composition) {
        checkCompositionExistence(composition.getId());
        compositions.put(composition.getId(), composition);
        return composition;
    }

    public static void deleteComposition(long compositionId) {
        checkCompositionExistence(compositionId);
        compositions.remove(compositionId);
    }

    private static void checkCompositionExistence(long compositionId) {
        if (!compositions.containsKey(compositionId)) {
            throw new CompositionNotFoundException("No such composition: " + compositionId);
        }
    }

    private static String randomCompositionName() {
        String dictionary = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder dummyName = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            dummyName.append(dictionary.charAt(random.nextInt(dictionary.length())));
        }
        return dummyName.toString();
    }
}
