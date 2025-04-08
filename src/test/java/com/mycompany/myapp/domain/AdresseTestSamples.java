package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdresseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adresse getAdresseSample1() {
        return new Adresse()
            .id(1L)
            .codePaysISO("codePaysISO1")
            .codePostal("codePostal1")
            .libelleCommune("libelleCommune1")
            .ligne1("ligne11")
            .ligne2("ligne21")
            .ligne3("ligne31")
            .ligne4("ligne41")
            .ligne5("ligne51")
            .ligne6("ligne61")
            .ligne7("ligne71")
            .nombreCourriersPND("nombreCourriersPND1")
            .codeUsageAdresse("codeUsageAdresse1");
    }

    public static Adresse getAdresseSample2() {
        return new Adresse()
            .id(2L)
            .codePaysISO("codePaysISO2")
            .codePostal("codePostal2")
            .libelleCommune("libelleCommune2")
            .ligne1("ligne12")
            .ligne2("ligne22")
            .ligne3("ligne32")
            .ligne4("ligne42")
            .ligne5("ligne52")
            .ligne6("ligne62")
            .ligne7("ligne72")
            .nombreCourriersPND("nombreCourriersPND2")
            .codeUsageAdresse("codeUsageAdresse2");
    }

    public static Adresse getAdresseRandomSampleGenerator() {
        return new Adresse()
            .id(longCount.incrementAndGet())
            .codePaysISO(UUID.randomUUID().toString())
            .codePostal(UUID.randomUUID().toString())
            .libelleCommune(UUID.randomUUID().toString())
            .ligne1(UUID.randomUUID().toString())
            .ligne2(UUID.randomUUID().toString())
            .ligne3(UUID.randomUUID().toString())
            .ligne4(UUID.randomUUID().toString())
            .ligne5(UUID.randomUUID().toString())
            .ligne6(UUID.randomUUID().toString())
            .ligne7(UUID.randomUUID().toString())
            .nombreCourriersPND(UUID.randomUUID().toString())
            .codeUsageAdresse(UUID.randomUUID().toString());
    }
}
