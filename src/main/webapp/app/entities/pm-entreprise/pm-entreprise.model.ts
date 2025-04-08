import dayjs from 'dayjs/esm';

export interface IPmEntreprise {
  id: number;
  idEntrepriseRPG?: string | null;
  codePartenaireDistributeur?: string | null;
  numeroSiretSiege?: string | null;
  codeEtat?: string | null;
  libelleEtat?: string | null;
  codeCategoriePersonne?: string | null;
  libelleCategoriePersonne?: string | null;
  codeType?: string | null;
  dateCreationJuridique?: dayjs.Dayjs | null;
  dateEtat?: dayjs.Dayjs | null;
  dateFermetureJuridique?: dayjs.Dayjs | null;
  codeTrancheEffectif?: string | null;
  libelleTrancheEffectif?: string | null;
  dateCessationActivite?: dayjs.Dayjs | null;
  dateEffectifOfficiel?: dayjs.Dayjs | null;
  effectifOfficiel?: string | null;
  codeMotifCessationActivite?: string | null;
  siren?: string | null;
  codeFormeJuridique?: string | null;
  raisonSociale?: string | null;
  codeCategorieJuridique?: string | null;
  codeIDCC?: string | null;
  codeAPE?: string | null;
}

export type NewPmEntreprise = Omit<IPmEntreprise, 'id'> & { id: null };
