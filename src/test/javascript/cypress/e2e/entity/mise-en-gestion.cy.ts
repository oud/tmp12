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

describe('MiseEnGestion e2e test', () => {
  const miseEnGestionPageUrl = '/mise-en-gestion';
  const miseEnGestionPageUrlPattern = new RegExp('/mise-en-gestion(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const miseEnGestionSample = { typeMEG: 'ew unto', codeOffre: 'if', dateEffet: '2025-04-07' };

  let miseEnGestion;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/mise-en-gestions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/mise-en-gestions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/mise-en-gestions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (miseEnGestion) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/mise-en-gestions/${miseEnGestion.id}`,
      }).then(() => {
        miseEnGestion = undefined;
      });
    }
  });

  it('MiseEnGestions menu should load MiseEnGestions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('mise-en-gestion');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MiseEnGestion').should('exist');
    cy.url().should('match', miseEnGestionPageUrlPattern);
  });

  describe('MiseEnGestion page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(miseEnGestionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MiseEnGestion page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/mise-en-gestion/new$'));
        cy.getEntityCreateUpdateHeading('MiseEnGestion');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', miseEnGestionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/mise-en-gestions',
          body: miseEnGestionSample,
        }).then(({ body }) => {
          miseEnGestion = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/mise-en-gestions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [miseEnGestion],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(miseEnGestionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MiseEnGestion page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('miseEnGestion');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', miseEnGestionPageUrlPattern);
      });

      it('edit button click should load edit MiseEnGestion page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MiseEnGestion');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', miseEnGestionPageUrlPattern);
      });

      it('edit button click should load edit MiseEnGestion page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MiseEnGestion');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', miseEnGestionPageUrlPattern);
      });

      it('last delete button click should delete instance of MiseEnGestion', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('miseEnGestion').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', miseEnGestionPageUrlPattern);

        miseEnGestion = undefined;
      });
    });
  });

  describe('new MiseEnGestion page', () => {
    beforeEach(() => {
      cy.visit(`${miseEnGestionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MiseEnGestion');
    });

    it('should create an instance of MiseEnGestion', () => {
      cy.get(`[data-cy="typeMEG"]`).type('hm kooky miserly');
      cy.get(`[data-cy="typeMEG"]`).should('have.value', 'hm kooky miserly');

      cy.get(`[data-cy="codeOffre"]`).type('when');
      cy.get(`[data-cy="codeOffre"]`).should('have.value', 'when');

      cy.get(`[data-cy="dateEffet"]`).type('2025-04-07');
      cy.get(`[data-cy="dateEffet"]`).blur();
      cy.get(`[data-cy="dateEffet"]`).should('have.value', '2025-04-07');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        miseEnGestion = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', miseEnGestionPageUrlPattern);
    });
  });
});
