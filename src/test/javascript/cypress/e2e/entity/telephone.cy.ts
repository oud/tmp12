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

describe('Telephone e2e test', () => {
  const telephonePageUrl = '/telephone';
  const telephonePageUrlPattern = new RegExp('/telephone(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const telephoneSample = {
    codePaysISO: 'unripe',
    codeTypeTelephone: 'whoever scratchy phooey',
    dateDerniereModification: '2025-04-08',
    codeIndicatifPays: 'briskly',
    numeroTelephone: 'yowza festival during',
    codeStatut: 'including',
    dateStatut: 'times brandish',
    codeUsageTelephone: 'subexpression versus wherever',
  };

  let telephone;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/telephones+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/telephones').as('postEntityRequest');
    cy.intercept('DELETE', '/api/telephones/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (telephone) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/telephones/${telephone.id}`,
      }).then(() => {
        telephone = undefined;
      });
    }
  });

  it('Telephones menu should load Telephones page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('telephone');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Telephone').should('exist');
    cy.url().should('match', telephonePageUrlPattern);
  });

  describe('Telephone page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(telephonePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Telephone page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/telephone/new$'));
        cy.getEntityCreateUpdateHeading('Telephone');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', telephonePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/telephones',
          body: telephoneSample,
        }).then(({ body }) => {
          telephone = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/telephones+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [telephone],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(telephonePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Telephone page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('telephone');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', telephonePageUrlPattern);
      });

      it('edit button click should load edit Telephone page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Telephone');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', telephonePageUrlPattern);
      });

      it('edit button click should load edit Telephone page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Telephone');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', telephonePageUrlPattern);
      });

      it('last delete button click should delete instance of Telephone', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('telephone').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', telephonePageUrlPattern);

        telephone = undefined;
      });
    });
  });

  describe('new Telephone page', () => {
    beforeEach(() => {
      cy.visit(`${telephonePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Telephone');
    });

    it('should create an instance of Telephone', () => {
      cy.get(`[data-cy="codePaysISO"]`).type('saw pace apud');
      cy.get(`[data-cy="codePaysISO"]`).should('have.value', 'saw pace apud');

      cy.get(`[data-cy="codeTypeTelephone"]`).type('ah boldly');
      cy.get(`[data-cy="codeTypeTelephone"]`).should('have.value', 'ah boldly');

      cy.get(`[data-cy="dateDerniereModification"]`).type('2025-04-07');
      cy.get(`[data-cy="dateDerniereModification"]`).blur();
      cy.get(`[data-cy="dateDerniereModification"]`).should('have.value', '2025-04-07');

      cy.get(`[data-cy="codeIndicatifPays"]`).type('concentration till preside');
      cy.get(`[data-cy="codeIndicatifPays"]`).should('have.value', 'concentration till preside');

      cy.get(`[data-cy="numeroTelephone"]`).type('through daily hospitable');
      cy.get(`[data-cy="numeroTelephone"]`).should('have.value', 'through daily hospitable');

      cy.get(`[data-cy="codeStatut"]`).type('although save think');
      cy.get(`[data-cy="codeStatut"]`).should('have.value', 'although save think');

      cy.get(`[data-cy="dateStatut"]`).type('judgementally eggplant yippee');
      cy.get(`[data-cy="dateStatut"]`).should('have.value', 'judgementally eggplant yippee');

      cy.get(`[data-cy="codeUsageTelephone"]`).type('carefully whether er');
      cy.get(`[data-cy="codeUsageTelephone"]`).should('have.value', 'carefully whether er');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        telephone = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', telephonePageUrlPattern);
    });
  });
});
