package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TelephoneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Telephone getTelephoneSample1() {
        return new Telephone()
            .id(1L)
            .codePaysISO("codePaysISO1")
            .codeTypeTelephone("codeTypeTelephone1")
            .codeIndicatifPays("codeIndicatifPays1")
            .numeroTelephone("numeroTelephone1")
            .codeStatut("codeStatut1")
            .dateStatut("dateStatut1")
            .codeUsageTelephone("codeUsageTelephone1");
    }

    public static Telephone getTelephoneSample2() {
        return new Telephone()
            .id(2L)
            .codePaysISO("codePaysISO2")
            .codeTypeTelephone("codeTypeTelephone2")
            .codeIndicatifPays("codeIndicatifPays2")
            .numeroTelephone("numeroTelephone2")
            .codeStatut("codeStatut2")
            .dateStatut("dateStatut2")
            .codeUsageTelephone("codeUsageTelephone2");
    }

    public static Telephone getTelephoneRandomSampleGenerator() {
        return new Telephone()
            .id(longCount.incrementAndGet())
            .codePaysISO(UUID.randomUUID().toString())
            .codeTypeTelephone(UUID.randomUUID().toString())
            .codeIndicatifPays(UUID.randomUUID().toString())
            .numeroTelephone(UUID.randomUUID().toString())
            .codeStatut(UUID.randomUUID().toString())
            .dateStatut(UUID.randomUUID().toString())
            .codeUsageTelephone(UUID.randomUUID().toString());
    }
}
