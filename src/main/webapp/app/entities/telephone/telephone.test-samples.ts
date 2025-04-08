import dayjs from 'dayjs/esm';

import { ITelephone, NewTelephone } from './telephone.model';

export const sampleWithRequiredData: ITelephone = {
  id: 9770,
  codePaysISO: 'whoa huzzah mask',
  codeTypeTelephone: 'inasmuch phooey',
  dateDerniereModification: dayjs('2025-04-08'),
  codeIndicatifPays: 'despite outrun tooth',
  numeroTelephone: 'ick tributary validity',
  codeStatut: 'of mockingly scent',
  dateStatut: 'pharmacopoeia',
  codeUsageTelephone: 'midst incidentally',
};

export const sampleWithPartialData: ITelephone = {
  id: 16000,
  codePaysISO: 'back scrabble',
  codeTypeTelephone: 'relieve frozen',
  dateDerniereModification: dayjs('2025-04-08'),
  codeIndicatifPays: 'behind',
  numeroTelephone: 'handle wholly wedding',
  codeStatut: 'as before phew',
  dateStatut: 'whereas',
  codeUsageTelephone: 'yet',
};

export const sampleWithFullData: ITelephone = {
  id: 15888,
  codePaysISO: 'trusty anti',
  codeTypeTelephone: 'magnificent whose',
  dateDerniereModification: dayjs('2025-04-07'),
  codeIndicatifPays: 'pharmacopoeia bah',
  numeroTelephone: 'which sedately',
  codeStatut: 'merrily fearless',
  dateStatut: 'pfft',
  codeUsageTelephone: 'suspiciously',
};

export const sampleWithNewData: NewTelephone = {
  codePaysISO: 'willfully because',
  codeTypeTelephone: 'meal',
  dateDerniereModification: dayjs('2025-04-08'),
  codeIndicatifPays: 'times qua',
  numeroTelephone: 'furthermore',
  codeStatut: 'whether',
  dateStatut: 'between legal',
  codeUsageTelephone: 'wherever',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
