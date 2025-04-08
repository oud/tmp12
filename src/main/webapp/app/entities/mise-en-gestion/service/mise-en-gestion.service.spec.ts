import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMiseEnGestion } from '../mise-en-gestion.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../mise-en-gestion.test-samples';

import { MiseEnGestionService, RestMiseEnGestion } from './mise-en-gestion.service';

const requireRestSample: RestMiseEnGestion = {
  ...sampleWithRequiredData,
  dateEffet: sampleWithRequiredData.dateEffet?.format(DATE_FORMAT),
};

describe('MiseEnGestion Service', () => {
  let service: MiseEnGestionService;
  let httpMock: HttpTestingController;
  let expectedResult: IMiseEnGestion | IMiseEnGestion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MiseEnGestionService);
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

    it('should create a MiseEnGestion', () => {
      const miseEnGestion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(miseEnGestion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MiseEnGestion', () => {
      const miseEnGestion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(miseEnGestion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MiseEnGestion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MiseEnGestion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MiseEnGestion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMiseEnGestionToCollectionIfMissing', () => {
      it('should add a MiseEnGestion to an empty array', () => {
        const miseEnGestion: IMiseEnGestion = sampleWithRequiredData;
        expectedResult = service.addMiseEnGestionToCollectionIfMissing([], miseEnGestion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(miseEnGestion);
      });

      it('should not add a MiseEnGestion to an array that contains it', () => {
        const miseEnGestion: IMiseEnGestion = sampleWithRequiredData;
        const miseEnGestionCollection: IMiseEnGestion[] = [
          {
            ...miseEnGestion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMiseEnGestionToCollectionIfMissing(miseEnGestionCollection, miseEnGestion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MiseEnGestion to an array that doesn't contain it", () => {
        const miseEnGestion: IMiseEnGestion = sampleWithRequiredData;
        const miseEnGestionCollection: IMiseEnGestion[] = [sampleWithPartialData];
        expectedResult = service.addMiseEnGestionToCollectionIfMissing(miseEnGestionCollection, miseEnGestion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(miseEnGestion);
      });

      it('should add only unique MiseEnGestion to an array', () => {
        const miseEnGestionArray: IMiseEnGestion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const miseEnGestionCollection: IMiseEnGestion[] = [sampleWithRequiredData];
        expectedResult = service.addMiseEnGestionToCollectionIfMissing(miseEnGestionCollection, ...miseEnGestionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const miseEnGestion: IMiseEnGestion = sampleWithRequiredData;
        const miseEnGestion2: IMiseEnGestion = sampleWithPartialData;
        expectedResult = service.addMiseEnGestionToCollectionIfMissing([], miseEnGestion, miseEnGestion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(miseEnGestion);
        expect(expectedResult).toContain(miseEnGestion2);
      });

      it('should accept null and undefined values', () => {
        const miseEnGestion: IMiseEnGestion = sampleWithRequiredData;
        expectedResult = service.addMiseEnGestionToCollectionIfMissing([], null, miseEnGestion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(miseEnGestion);
      });

      it('should return initial array if no MiseEnGestion is added', () => {
        const miseEnGestionCollection: IMiseEnGestion[] = [sampleWithRequiredData];
        expectedResult = service.addMiseEnGestionToCollectionIfMissing(miseEnGestionCollection, undefined, null);
        expect(expectedResult).toEqual(miseEnGestionCollection);
      });
    });

    describe('compareMiseEnGestion', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMiseEnGestion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 9935 };
        const entity2 = null;

        const compareResult1 = service.compareMiseEnGestion(entity1, entity2);
        const compareResult2 = service.compareMiseEnGestion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 9935 };
        const entity2 = { id: 12141 };

        const compareResult1 = service.compareMiseEnGestion(entity1, entity2);
        const compareResult2 = service.compareMiseEnGestion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 9935 };
        const entity2 = { id: 9935 };

        const compareResult1 = service.compareMiseEnGestion(entity1, entity2);
        const compareResult2 = service.compareMiseEnGestion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
