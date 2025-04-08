package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Email getEmailSample1() {
        return new Email().id(1L).adresseEmail("adresseEmail1").codeStatut("codeStatut1").codeUsageEmail("codeUsageEmail1");
    }

    public static Email getEmailSample2() {
        return new Email().id(2L).adresseEmail("adresseEmail2").codeStatut("codeStatut2").codeUsageEmail("codeUsageEmail2");
    }

    public static Email getEmailRandomSampleGenerator() {
        return new Email()
            .id(longCount.incrementAndGet())
            .adresseEmail(UUID.randomUUID().toString())
            .codeStatut(UUID.randomUUID().toString())
            .codeUsageEmail(UUID.randomUUID().toString());
    }
}
