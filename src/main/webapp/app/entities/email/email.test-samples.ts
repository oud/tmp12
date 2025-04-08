import dayjs from 'dayjs/esm';

import { IEmail, NewEmail } from './email.model';

export const sampleWithRequiredData: IEmail = {
  id: 6371,
  adresseEmail: 'arcade ah arraign',
  codeStatut: 'gaseous duh',
  dateStatut: dayjs('2025-04-07'),
  codeUsageEmail: 'properly muffled bidet',
};

export const sampleWithPartialData: IEmail = {
  id: 12163,
  adresseEmail: 'obedient',
  codeStatut: 'ick safeguard factorize',
  dateStatut: dayjs('2025-04-07'),
  codeUsageEmail: 'entwine',
};

export const sampleWithFullData: IEmail = {
  id: 3643,
  adresseEmail: 'acclaimed coagulate',
  codeStatut: 'cinch',
  dateStatut: dayjs('2025-04-07'),
  codeUsageEmail: 'geez attribute',
};

export const sampleWithNewData: NewEmail = {
  adresseEmail: 'lucky amidst',
  codeStatut: 'needily',
  dateStatut: dayjs('2025-04-07'),
  codeUsageEmail: 'pull',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
