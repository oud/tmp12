import dayjs from 'dayjs/esm';

export interface IMiseEnGestion {
  id: number;
  typeMEG?: string | null;
  codeOffre?: string | null;
  dateEffet?: dayjs.Dayjs | null;
}

export type NewMiseEnGestion = Omit<IMiseEnGestion, 'id'> & { id: null };
