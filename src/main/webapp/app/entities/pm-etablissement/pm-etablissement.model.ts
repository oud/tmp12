import { IDemandeXRM } from 'app/entities/demande-xrm/demande-xrm.model';

export interface IPmEtablissement {
  id: number;
  idEtablissementRPG?: string | null;
  codePartenaireDistributeur?: string | null;
  numeroSiretSiege?: string | null;
  codeEtat?: string | null;
  libelleEtat?: string | null;
  codeCategoriePersonne?: string | null;
  libelleCategoriePersonne?: string | null;
  codeType?: string | null;
  dateCreationJuridique?: string | null;
  dateEtat?: string | null;
  dateFermetureJuridique?: string | null;
  codeIDCC?: string | null;
  codeTrancheEffectif?: string | null;
  libelleTrancheEffectif?: string | null;
  dateCessationActivite?: string | null;
  dateEffectifOfficiel?: string | null;
  effectifOfficiel?: string | null;
  codeMotifCessationActivite?: string | null;
  siret?: string | null;
  codeTypeEtablissement?: string | null;
  libelleEnseigne?: string | null;
  codeNIC?: string | null;
  demandeXRM?: Pick<IDemandeXRM, 'id'> | null;
}

export type NewPmEtablissement = Omit<IPmEtablissement, 'id'> & { id: null };
