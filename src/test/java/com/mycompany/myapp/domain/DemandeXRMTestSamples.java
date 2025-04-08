package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DemandeXRMTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DemandeXRM getDemandeXRMSample1() {
        return new DemandeXRM().id(1L);
    }

    public static DemandeXRM getDemandeXRMSample2() {
        return new DemandeXRM().id(2L);
    }

    public static DemandeXRM getDemandeXRMRandomSampleGenerator() {
        return new DemandeXRM().id(longCount.incrementAndGet());
    }
}
