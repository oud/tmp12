import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITelephone } from '../telephone.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../telephone.test-samples';

import { RestTelephone, TelephoneService } from './telephone.service';

const requireRestSample: RestTelephone = {
  ...sampleWithRequiredData,
  dateDerniereModification: sampleWithRequiredData.dateDerniereModification?.format(DATE_FORMAT),
};

describe('Telephone Service', () => {
  let service: TelephoneService;
  let httpMock: HttpTestingController;
  let expectedResult: ITelephone | ITelephone[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TelephoneService);
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

    it('should create a Telephone', () => {
      const telephone = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(telephone).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Telephone', () => {
      const telephone = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(telephone).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Telephone', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Telephone', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Telephone', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTelephoneToCollectionIfMissing', () => {
      it('should add a Telephone to an empty array', () => {
        const telephone: ITelephone = sampleWithRequiredData;
        expectedResult = service.addTelephoneToCollectionIfMissing([], telephone);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(telephone);
      });

      it('should not add a Telephone to an array that contains it', () => {
        const telephone: ITelephone = sampleWithRequiredData;
        const telephoneCollection: ITelephone[] = [
          {
            ...telephone,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTelephoneToCollectionIfMissing(telephoneCollection, telephone);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Telephone to an array that doesn't contain it", () => {
        const telephone: ITelephone = sampleWithRequiredData;
        const telephoneCollection: ITelephone[] = [sampleWithPartialData];
        expectedResult = service.addTelephoneToCollectionIfMissing(telephoneCollection, telephone);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(telephone);
      });

      it('should add only unique Telephone to an array', () => {
        const telephoneArray: ITelephone[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const telephoneCollection: ITelephone[] = [sampleWithRequiredData];
        expectedResult = service.addTelephoneToCollectionIfMissing(telephoneCollection, ...telephoneArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const telephone: ITelephone = sampleWithRequiredData;
        const telephone2: ITelephone = sampleWithPartialData;
        expectedResult = service.addTelephoneToCollectionIfMissing([], telephone, telephone2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(telephone);
        expect(expectedResult).toContain(telephone2);
      });

      it('should accept null and undefined values', () => {
        const telephone: ITelephone = sampleWithRequiredData;
        expectedResult = service.addTelephoneToCollectionIfMissing([], null, telephone, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(telephone);
      });

      it('should return initial array if no Telephone is added', () => {
        const telephoneCollection: ITelephone[] = [sampleWithRequiredData];
        expectedResult = service.addTelephoneToCollectionIfMissing(telephoneCollection, undefined, null);
        expect(expectedResult).toEqual(telephoneCollection);
      });
    });

    describe('compareTelephone', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTelephone(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 29884 };
        const entity2 = null;

        const compareResult1 = service.compareTelephone(entity1, entity2);
        const compareResult2 = service.compareTelephone(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 29884 };
        const entity2 = { id: 17258 };

        const compareResult1 = service.compareTelephone(entity1, entity2);
        const compareResult2 = service.compareTelephone(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 29884 };
        const entity2 = { id: 29884 };

        const compareResult1 = service.compareTelephone(entity1, entity2);
        const compareResult2 = service.compareTelephone(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
