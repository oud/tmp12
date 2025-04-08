import { IPmEntreprise } from 'app/entities/pm-entreprise/pm-entreprise.model';
import { IMiseEnGestion } from 'app/entities/mise-en-gestion/mise-en-gestion.model';

export interface IDemandeXRM {
  id: number;
  pmEntreprise?: Pick<IPmEntreprise, 'id'> | null;
  miseEnGestion?: Pick<IMiseEnGestion, 'id'> | null;
}

export type NewDemandeXRM = Omit<IDemandeXRM, 'id'> & { id: null };
