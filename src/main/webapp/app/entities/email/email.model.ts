import dayjs from 'dayjs/esm';
import { IPmEntreprise } from 'app/entities/pm-entreprise/pm-entreprise.model';
import { IPmEtablissement } from 'app/entities/pm-etablissement/pm-etablissement.model';

export interface IEmail {
  id: number;
  adresseEmail?: string | null;
  codeStatut?: string | null;
  dateStatut?: dayjs.Dayjs | null;
  codeUsageEmail?: string | null;
  pmEntreprise?: Pick<IPmEntreprise, 'id'> | null;
  pmEtablissement?: Pick<IPmEtablissement, 'id'> | null;
}

export type NewEmail = Omit<IEmail, 'id'> & { id: null };
