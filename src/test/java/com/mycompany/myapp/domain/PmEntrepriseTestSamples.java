package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PmEntrepriseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PmEntreprise getPmEntrepriseSample1() {
        return new PmEntreprise()
            .id(1L)
            .idEntrepriseRPG("idEntrepriseRPG1")
            .codePartenaireDistributeur("codePartenaireDistributeur1")
            .numeroSiretSiege("numeroSiretSiege1")
            .codeEtat("codeEtat1")
            .libelleEtat("libelleEtat1")
            .codeCategoriePersonne("codeCategoriePersonne1")
            .libelleCategoriePersonne("libelleCategoriePersonne1")
            .codeType("codeType1")
            .codeTrancheEffectif("codeTrancheEffectif1")
            .libelleTrancheEffectif("libelleTrancheEffectif1")
            .effectifOfficiel("effectifOfficiel1")
            .codeMotifCessationActivite("codeMotifCessationActivite1")
            .siren("siren1")
            .codeFormeJuridique("codeFormeJuridique1")
            .raisonSociale("raisonSociale1")
            .codeCategorieJuridique("codeCategorieJuridique1")
            .codeIDCC("codeIDCC1")
            .codeAPE("codeAPE1");
    }

    public static PmEntreprise getPmEntrepriseSample2() {
        return new PmEntreprise()
            .id(2L)
            .idEntrepriseRPG("idEntrepriseRPG2")
            .codePartenaireDistributeur("codePartenaireDistributeur2")
            .numeroSiretSiege("numeroSiretSiege2")
            .codeEtat("codeEtat2")
            .libelleEtat("libelleEtat2")
            .codeCategoriePersonne("codeCategoriePersonne2")
            .libelleCategoriePersonne("libelleCategoriePersonne2")
            .codeType("codeType2")
            .codeTrancheEffectif("codeTrancheEffectif2")
            .libelleTrancheEffectif("libelleTrancheEffectif2")
            .effectifOfficiel("effectifOfficiel2")
            .codeMotifCessationActivite("codeMotifCessationActivite2")
            .siren("siren2")
            .codeFormeJuridique("codeFormeJuridique2")
            .raisonSociale("raisonSociale2")
            .codeCategorieJuridique("codeCategorieJuridique2")
            .codeIDCC("codeIDCC2")
            .codeAPE("codeAPE2");
    }

    public static PmEntreprise getPmEntrepriseRandomSampleGenerator() {
        return new PmEntreprise()
            .id(longCount.incrementAndGet())
            .idEntrepriseRPG(UUID.randomUUID().toString())
            .codePartenaireDistributeur(UUID.randomUUID().toString())
            .numeroSiretSiege(UUID.randomUUID().toString())
            .codeEtat(UUID.randomUUID().toString())
            .libelleEtat(UUID.randomUUID().toString())
            .codeCategoriePersonne(UUID.randomUUID().toString())
            .libelleCategoriePersonne(UUID.randomUUID().toString())
            .codeType(UUID.randomUUID().toString())
            .codeTrancheEffectif(UUID.randomUUID().toString())
            .libelleTrancheEffectif(UUID.randomUUID().toString())
            .effectifOfficiel(UUID.randomUUID().toString())
            .codeMotifCessationActivite(UUID.randomUUID().toString())
            .siren(UUID.randomUUID().toString())
            .codeFormeJuridique(UUID.randomUUID().toString())
            .raisonSociale(UUID.randomUUID().toString())
            .codeCategorieJuridique(UUID.randomUUID().toString())
            .codeIDCC(UUID.randomUUID().toString())
            .codeAPE(UUID.randomUUID().toString());
    }
}
