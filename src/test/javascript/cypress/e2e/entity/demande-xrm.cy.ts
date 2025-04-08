import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('DemandeXRM e2e test', () => {
  const demandeXRMPageUrl = '/demande-xrm';
  const demandeXRMPageUrlPattern = new RegExp('/demande-xrm(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const demandeXRMSample = {};

  let demandeXRM;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/demande-xrms+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/demande-xrms').as('postEntityRequest');
    cy.intercept('DELETE', '/api/demande-xrms/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (demandeXRM) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/demande-xrms/${demandeXRM.id}`,
      }).then(() => {
        demandeXRM = undefined;
      });
    }
  });

  it('DemandeXRMS menu should load DemandeXRMS page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('demande-xrm');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DemandeXRM').should('exist');
    cy.url().should('match', demandeXRMPageUrlPattern);
  });

  describe('DemandeXRM page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(demandeXRMPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DemandeXRM page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/demande-xrm/new$'));
        cy.getEntityCreateUpdateHeading('DemandeXRM');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeXRMPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/demande-xrms',
          body: demandeXRMSample,
        }).then(({ body }) => {
          demandeXRM = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/demande-xrms+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [demandeXRM],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(demandeXRMPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DemandeXRM page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('demandeXRM');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeXRMPageUrlPattern);
      });

      it('edit button click should load edit DemandeXRM page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DemandeXRM');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeXRMPageUrlPattern);
      });

      it('edit button click should load edit DemandeXRM page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DemandeXRM');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeXRMPageUrlPattern);
      });

      it('last delete button click should delete instance of DemandeXRM', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('demandeXRM').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeXRMPageUrlPattern);

        demandeXRM = undefined;
      });
    });
  });

  describe('new DemandeXRM page', () => {
    beforeEach(() => {
      cy.visit(`${demandeXRMPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DemandeXRM');
    });

    it('should create an instance of DemandeXRM', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        demandeXRM = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', demandeXRMPageUrlPattern);
    });
  });
});
