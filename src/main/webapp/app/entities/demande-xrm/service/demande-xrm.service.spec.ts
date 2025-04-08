import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDemandeXRM } from '../demande-xrm.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../demande-xrm.test-samples';

import { DemandeXRMService } from './demande-xrm.service';

const requireRestSample: IDemandeXRM = {
  ...sampleWithRequiredData,
};

describe('DemandeXRM Service', () => {
  let service: DemandeXRMService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemandeXRM | IDemandeXRM[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeXRMService);
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

    it('should create a DemandeXRM', () => {
      const demandeXRM = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demandeXRM).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemandeXRM', () => {
      const demandeXRM = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demandeXRM).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemandeXRM', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemandeXRM', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DemandeXRM', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemandeXRMToCollectionIfMissing', () => {
      it('should add a DemandeXRM to an empty array', () => {
        const demandeXRM: IDemandeXRM = sampleWithRequiredData;
        expectedResult = service.addDemandeXRMToCollectionIfMissing([], demandeXRM);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeXRM);
      });

      it('should not add a DemandeXRM to an array that contains it', () => {
        const demandeXRM: IDemandeXRM = sampleWithRequiredData;
        const demandeXRMCollection: IDemandeXRM[] = [
          {
            ...demandeXRM,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemandeXRMToCollectionIfMissing(demandeXRMCollection, demandeXRM);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemandeXRM to an array that doesn't contain it", () => {
        const demandeXRM: IDemandeXRM = sampleWithRequiredData;
        const demandeXRMCollection: IDemandeXRM[] = [sampleWithPartialData];
        expectedResult = service.addDemandeXRMToCollectionIfMissing(demandeXRMCollection, demandeXRM);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeXRM);
      });

      it('should add only unique DemandeXRM to an array', () => {
        const demandeXRMArray: IDemandeXRM[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demandeXRMCollection: IDemandeXRM[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeXRMToCollectionIfMissing(demandeXRMCollection, ...demandeXRMArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeXRM: IDemandeXRM = sampleWithRequiredData;
        const demandeXRM2: IDemandeXRM = sampleWithPartialData;
        expectedResult = service.addDemandeXRMToCollectionIfMissing([], demandeXRM, demandeXRM2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeXRM);
        expect(expectedResult).toContain(demandeXRM2);
      });

      it('should accept null and undefined values', () => {
        const demandeXRM: IDemandeXRM = sampleWithRequiredData;
        expectedResult = service.addDemandeXRMToCollectionIfMissing([], null, demandeXRM, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeXRM);
      });

      it('should return initial array if no DemandeXRM is added', () => {
        const demandeXRMCollection: IDemandeXRM[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeXRMToCollectionIfMissing(demandeXRMCollection, undefined, null);
        expect(expectedResult).toEqual(demandeXRMCollection);
      });
    });

    describe('compareDemandeXRM', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemandeXRM(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 16042 };
        const entity2 = null;

        const compareResult1 = service.compareDemandeXRM(entity1, entity2);
        const compareResult2 = service.compareDemandeXRM(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 16042 };
        const entity2 = { id: 11627 };

        const compareResult1 = service.compareDemandeXRM(entity1, entity2);
        const compareResult2 = service.compareDemandeXRM(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 16042 };
        const entity2 = { id: 16042 };

        const compareResult1 = service.compareDemandeXRM(entity1, entity2);
        const compareResult2 = service.compareDemandeXRM(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
