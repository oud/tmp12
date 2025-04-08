import dayjs from 'dayjs/esm';
import { IPmEntreprise } from 'app/entities/pm-entreprise/pm-entreprise.model';
import { IPmEtablissement } from 'app/entities/pm-etablissement/pm-etablissement.model';

export interface ITelephone {
  id: number;
  codePaysISO?: string | null;
  codeTypeTelephone?: string | null;
  dateDerniereModification?: dayjs.Dayjs | null;
  codeIndicatifPays?: string | null;
  numeroTelephone?: string | null;
  codeStatut?: string | null;
  dateStatut?: string | null;
  codeUsageTelephone?: string | null;
  pmEntreprise?: Pick<IPmEntreprise, 'id'> | null;
  pmEtablissement?: Pick<IPmEtablissement, 'id'> | null;
}

export type NewTelephone = Omit<ITelephone, 'id'> & { id: null };
