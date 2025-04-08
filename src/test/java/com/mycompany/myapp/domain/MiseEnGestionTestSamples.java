package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MiseEnGestionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MiseEnGestion getMiseEnGestionSample1() {
        return new MiseEnGestion().id(1L).typeMEG("typeMEG1").codeOffre("codeOffre1");
    }

    public static MiseEnGestion getMiseEnGestionSample2() {
        return new MiseEnGestion().id(2L).typeMEG("typeMEG2").codeOffre("codeOffre2");
    }

    public static MiseEnGestion getMiseEnGestionRandomSampleGenerator() {
        return new MiseEnGestion()
            .id(longCount.incrementAndGet())
            .typeMEG(UUID.randomUUID().toString())
            .codeOffre(UUID.randomUUID().toString());
    }
}
