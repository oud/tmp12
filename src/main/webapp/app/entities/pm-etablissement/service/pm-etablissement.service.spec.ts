import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPmEtablissement } from '../pm-etablissement.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../pm-etablissement.test-samples';

import { PmEtablissementService } from './pm-etablissement.service';

const requireRestSample: IPmEtablissement = {
  ...sampleWithRequiredData,
};

describe('PmEtablissement Service', () => {
  let service: PmEtablissementService;
  let httpMock: HttpTestingController;
  let expectedResult: IPmEtablissement | IPmEtablissement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PmEtablissementService);
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

    it('should create a PmEtablissement', () => {
      const pmEtablissement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pmEtablissement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PmEtablissement', () => {
      const pmEtablissement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pmEtablissement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PmEtablissement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PmEtablissement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PmEtablissement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPmEtablissementToCollectionIfMissing', () => {
      it('should add a PmEtablissement to an empty array', () => {
        const pmEtablissement: IPmEtablissement = sampleWithRequiredData;
        expectedResult = service.addPmEtablissementToCollectionIfMissing([], pmEtablissement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pmEtablissement);
      });

      it('should not add a PmEtablissement to an array that contains it', () => {
        const pmEtablissement: IPmEtablissement = sampleWithRequiredData;
        const pmEtablissementCollection: IPmEtablissement[] = [
          {
            ...pmEtablissement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPmEtablissementToCollectionIfMissing(pmEtablissementCollection, pmEtablissement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PmEtablissement to an array that doesn't contain it", () => {
        const pmEtablissement: IPmEtablissement = sampleWithRequiredData;
        const pmEtablissementCollection: IPmEtablissement[] = [sampleWithPartialData];
        expectedResult = service.addPmEtablissementToCollectionIfMissing(pmEtablissementCollection, pmEtablissement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pmEtablissement);
      });

      it('should add only unique PmEtablissement to an array', () => {
        const pmEtablissementArray: IPmEtablissement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pmEtablissementCollection: IPmEtablissement[] = [sampleWithRequiredData];
        expectedResult = service.addPmEtablissementToCollectionIfMissing(pmEtablissementCollection, ...pmEtablissementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pmEtablissement: IPmEtablissement = sampleWithRequiredData;
        const pmEtablissement2: IPmEtablissement = sampleWithPartialData;
        expectedResult = service.addPmEtablissementToCollectionIfMissing([], pmEtablissement, pmEtablissement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pmEtablissement);
        expect(expectedResult).toContain(pmEtablissement2);
      });

      it('should accept null and undefined values', () => {
        const pmEtablissement: IPmEtablissement = sampleWithRequiredData;
        expectedResult = service.addPmEtablissementToCollectionIfMissing([], null, pmEtablissement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pmEtablissement);
      });

      it('should return initial array if no PmEtablissement is added', () => {
        const pmEtablissementCollection: IPmEtablissement[] = [sampleWithRequiredData];
        expectedResult = service.addPmEtablissementToCollectionIfMissing(pmEtablissementCollection, undefined, null);
        expect(expectedResult).toEqual(pmEtablissementCollection);
      });
    });

    describe('comparePmEtablissement', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePmEtablissement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 16761 };
        const entity2 = null;

        const compareResult1 = service.comparePmEtablissement(entity1, entity2);
        const compareResult2 = service.comparePmEtablissement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 16761 };
        const entity2 = { id: 14323 };

        const compareResult1 = service.comparePmEtablissement(entity1, entity2);
        const compareResult2 = service.comparePmEtablissement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 16761 };
        const entity2 = { id: 16761 };

        const compareResult1 = service.comparePmEtablissement(entity1, entity2);
        const compareResult2 = service.comparePmEtablissement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
