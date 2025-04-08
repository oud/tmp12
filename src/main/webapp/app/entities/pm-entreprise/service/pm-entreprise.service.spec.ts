import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPmEntreprise } from '../pm-entreprise.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../pm-entreprise.test-samples';

import { PmEntrepriseService, RestPmEntreprise } from './pm-entreprise.service';

const requireRestSample: RestPmEntreprise = {
  ...sampleWithRequiredData,
  dateCreationJuridique: sampleWithRequiredData.dateCreationJuridique?.format(DATE_FORMAT),
  dateEtat: sampleWithRequiredData.dateEtat?.format(DATE_FORMAT),
  dateFermetureJuridique: sampleWithRequiredData.dateFermetureJuridique?.format(DATE_FORMAT),
  dateCessationActivite: sampleWithRequiredData.dateCessationActivite?.format(DATE_FORMAT),
  dateEffectifOfficiel: sampleWithRequiredData.dateEffectifOfficiel?.format(DATE_FORMAT),
};

describe('PmEntreprise Service', () => {
  let service: PmEntrepriseService;
  let httpMock: HttpTestingController;
  let expectedResult: IPmEntreprise | IPmEntreprise[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PmEntrepriseService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PmEntreprise', () => {
      const pmEntreprise = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pmEntreprise).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PmEntreprise', () => {
      const pmEntreprise = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pmEntreprise).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PmEntreprise', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PmEntreprise', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PmEntreprise', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPmEntrepriseToCollectionIfMissing', () => {
      it('should add a PmEntreprise to an empty array', () => {
        const pmEntreprise: IPmEntreprise = sampleWithRequiredData;
        expectedResult = service.addPmEntrepriseToCollectionIfMissing([], pmEntreprise);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pmEntreprise);
      });

      it('should not add a PmEntreprise to an array that contains it', () => {
        const pmEntreprise: IPmEntreprise = sampleWithRequiredData;
        const pmEntrepriseCollection: IPmEntreprise[] = [
          {
            ...pmEntreprise,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPmEntrepriseToCollectionIfMissing(pmEntrepriseCollection, pmEntreprise);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PmEntreprise to an array that doesn't contain it", () => {
        const pmEntreprise: IPmEntreprise = sampleWithRequiredData;
        const pmEntrepriseCollection: IPmEntreprise[] = [sampleWithPartialData];
        expectedResult = service.addPmEntrepriseToCollectionIfMissing(pmEntrepriseCollection, pmEntreprise);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pmEntreprise);
      });

      it('should add only unique PmEntreprise to an array', () => {
        const pmEntrepriseArray: IPmEntreprise[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pmEntrepriseCollection: IPmEntreprise[] = [sampleWithRequiredData];
        expectedResult = service.addPmEntrepriseToCollectionIfMissing(pmEntrepriseCollection, ...pmEntrepriseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pmEntreprise: IPmEntreprise = sampleWithRequiredData;
        const pmEntreprise2: IPmEntreprise = sampleWithPartialData;
        expectedResult = service.addPmEntrepriseToCollectionIfMissing([], pmEntreprise, pmEntreprise2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pmEntreprise);
        expect(expectedResult).toContain(pmEntreprise2);
      });

      it('should accept null and undefined values', () => {
        const pmEntreprise: IPmEntreprise = sampleWithRequiredData;
        expectedResult = service.addPmEntrepriseToCollectionIfMissing([], null, pmEntreprise, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pmEntreprise);
      });

      it('should return initial array if no PmEntreprise is added', () => {
        const pmEntrepriseCollection: IPmEntreprise[] = [sampleWithRequiredData];
        expectedResult = service.addPmEntrepriseToCollectionIfMissing(pmEntrepriseCollection, undefined, null);
        expect(expectedResult).toEqual(pmEntrepriseCollection);
      });
    });

    describe('comparePmEntreprise', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePmEntreprise(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 16964 };
        const entity2 = null;

        const compareResult1 = service.comparePmEntreprise(entity1, entity2);
        const compareResult2 = service.comparePmEntreprise(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 16964 };
        const entity2 = { id: 18362 };

        const compareResult1 = service.comparePmEntreprise(entity1, entity2);
        const compareResult2 = service.comparePmEntreprise(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 16964 };
        const entity2 = { id: 16964 };

        const compareResult1 = service.comparePmEntreprise(entity1, entity2);
        const compareResult2 = service.comparePmEntreprise(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
