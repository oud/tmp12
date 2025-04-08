import dayjs from 'dayjs/esm';

import { IAdresse, NewAdresse } from './adresse.model';

export const sampleWithRequiredData: IAdresse = {
  id: 11524,
  codePaysISO: 'scientific boohoo',
  codePostal: 'nearly',
  dateDerniereModification: dayjs('2025-04-07'),
  libelleCommune: 'who hence',
  ligne1: 'bustling confute',
  ligne2: 'recklessly',
  ligne3: 'diligently gee',
  ligne4: 'boohoo testimonial athwart',
  ligne5: 'truly bend',
  ligne6: 'across foolish',
  ligne7: 'towards obligation',
  nombreCourriersPND: 'cruelly kit',
  codeUsageAdresse: 'astonishing as lest',
};

export const sampleWithPartialData: IAdresse = {
  id: 13649,
  codePaysISO: 'upon',
  codePostal: 'team zowie',
  dateDerniereModification: dayjs('2025-04-08'),
  libelleCommune: 'than mathematics',
  ligne1: 'scratchy hence',
  ligne2: 'um worthwhile',
  ligne3: 'enchanting',
  ligne4: 'duh inasmuch',
  ligne5: 'kooky',
  ligne6: 'under',
  ligne7: 'ah ferociously sans',
  nombreCourriersPND: 'brr',
  codeUsageAdresse: 'vice catalyst',
};

export const sampleWithFullData: IAdresse = {
  id: 31736,
  codePaysISO: 'beside yum',
  codePostal: 'homely duh',
  dateDerniereModification: dayjs('2025-04-08'),
  libelleCommune: 'elevator',
  ligne1: 'limited',
  ligne2: 'ravage toward',
  ligne3: 'nor swanling smarten',
  ligne4: 'godfather for dim',
  ligne5: 'unscramble fuzzy',
  ligne6: 'riser a notwithstanding',
  ligne7: 'with beloved gosh',
  nombreCourriersPND: 'pneumonia',
  codeUsageAdresse: 'moisten velocity victoriously',
};

export const sampleWithNewData: NewAdresse = {
  codePaysISO: 'timely',
  codePostal: 'shocked instruction',
  dateDerniereModification: dayjs('2025-04-07'),
  libelleCommune: 'equally whereas',
  ligne1: 'republican and',
  ligne2: 'develop',
  ligne3: 'ah ew mid',
  ligne4: 'queasy however publication',
  ligne5: 'truly',
  ligne6: 'wonderfully extricate',
  ligne7: 'spice',
  nombreCourriersPND: 'until',
  codeUsageAdresse: 'vaguely optimistically nasalise',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
