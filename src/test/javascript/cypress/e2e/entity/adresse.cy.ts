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

describe('Adresse e2e test', () => {
  const adressePageUrl = '/adresse';
  const adressePageUrlPattern = new RegExp('/adresse(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adresseSample = {
    codePaysISO: 'phew jaunty',
    codePostal: 'icebreaker',
    dateDerniereModification: '2025-04-07',
    libelleCommune: 'ick cross-contamination',
    ligne1: 'whoa service',
    ligne2: 'behind the',
    ligne3: 'ack propound upwardly',
    ligne4: 'as given',
    ligne5: 'suspiciously disadvantage plus',
    ligne6: 'ew negative',
    ligne7: 'nervous',
    nombreCourriersPND: 'fiercely ah',
    codeUsageAdresse: 'and unabashedly',
  };

  let adresse;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/adresses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/adresses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/adresses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adresse) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/adresses/${adresse.id}`,
      }).then(() => {
        adresse = undefined;
      });
    }
  });

  it('Adresses menu should load Adresses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('adresse');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Adresse').should('exist');
    cy.url().should('match', adressePageUrlPattern);
  });

  describe('Adresse page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adressePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Adresse page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/adresse/new$'));
        cy.getEntityCreateUpdateHeading('Adresse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', adressePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/adresses',
          body: adresseSample,
        }).then(({ body }) => {
          adresse = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/adresses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [adresse],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(adressePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Adresse page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adresse');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', adressePageUrlPattern);
      });

      it('edit button click should load edit Adresse page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Adresse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', adressePageUrlPattern);
      });

      it('edit button click should load edit Adresse page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Adresse');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', adressePageUrlPattern);
      });

      it('last delete button click should delete instance of Adresse', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adresse').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', adressePageUrlPattern);

        adresse = undefined;
      });
    });
  });

  describe('new Adresse page', () => {
    beforeEach(() => {
      cy.visit(`${adressePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Adresse');
    });

    it('should create an instance of Adresse', () => {
      cy.get(`[data-cy="codePaysISO"]`).type('so save longingly');
      cy.get(`[data-cy="codePaysISO"]`).should('have.value', 'so save longingly');

      cy.get(`[data-cy="codePostal"]`).type('heartache yippee');
      cy.get(`[data-cy="codePostal"]`).should('have.value', 'heartache yippee');

      cy.get(`[data-cy="dateDerniereModification"]`).type('2025-04-07');
      cy.get(`[data-cy="dateDerniereModification"]`).blur();
      cy.get(`[data-cy="dateDerniereModification"]`).should('have.value', '2025-04-07');

      cy.get(`[data-cy="libelleCommune"]`).type('furthermore generously gadzooks');
      cy.get(`[data-cy="libelleCommune"]`).should('have.value', 'furthermore generously gadzooks');

      cy.get(`[data-cy="ligne1"]`).type('brr lavish break');
      cy.get(`[data-cy="ligne1"]`).should('have.value', 'brr lavish break');

      cy.get(`[data-cy="ligne2"]`).type('because who snoop');
      cy.get(`[data-cy="ligne2"]`).should('have.value', 'because who snoop');

      cy.get(`[data-cy="ligne3"]`).type('reconstitute substantial');
      cy.get(`[data-cy="ligne3"]`).should('have.value', 'reconstitute substantial');

      cy.get(`[data-cy="ligne4"]`).type('and behest');
      cy.get(`[data-cy="ligne4"]`).should('have.value', 'and behest');

      cy.get(`[data-cy="ligne5"]`).type('how');
      cy.get(`[data-cy="ligne5"]`).should('have.value', 'how');

      cy.get(`[data-cy="ligne6"]`).type('ah greatly');
      cy.get(`[data-cy="ligne6"]`).should('have.value', 'ah greatly');

      cy.get(`[data-cy="ligne7"]`).type('against attraction');
      cy.get(`[data-cy="ligne7"]`).should('have.value', 'against attraction');

      cy.get(`[data-cy="nombreCourriersPND"]`).type('highlight');
      cy.get(`[data-cy="nombreCourriersPND"]`).should('have.value', 'highlight');

      cy.get(`[data-cy="codeUsageAdresse"]`).type('once bustling of');
      cy.get(`[data-cy="codeUsageAdresse"]`).should('have.value', 'once bustling of');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        adresse = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', adressePageUrlPattern);
    });
  });
});
