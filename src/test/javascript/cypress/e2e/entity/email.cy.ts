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

describe('Email e2e test', () => {
  const emailPageUrl = '/email';
  const emailPageUrlPattern = new RegExp('/email(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const emailSample = {
    adresseEmail: 'amidst',
    codeStatut: 'lest which informal',
    dateStatut: '2025-04-07',
    codeUsageEmail: 'extroverted amid',
  };

  let email;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/emails+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/emails').as('postEntityRequest');
    cy.intercept('DELETE', '/api/emails/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (email) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/emails/${email.id}`,
      }).then(() => {
        email = undefined;
      });
    }
  });

  it('Emails menu should load Emails page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('email');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Email').should('exist');
    cy.url().should('match', emailPageUrlPattern);
  });

  describe('Email page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(emailPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Email page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/email/new$'));
        cy.getEntityCreateUpdateHeading('Email');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', emailPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/emails',
          body: emailSample,
        }).then(({ body }) => {
          email = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/emails+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [email],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(emailPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Email page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('email');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', emailPageUrlPattern);
      });

      it('edit button click should load edit Email page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Email');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', emailPageUrlPattern);
      });

      it('edit button click should load edit Email page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Email');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', emailPageUrlPattern);
      });

      it('last delete button click should delete instance of Email', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('email').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', emailPageUrlPattern);

        email = undefined;
      });
    });
  });

  describe('new Email page', () => {
    beforeEach(() => {
      cy.visit(`${emailPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Email');
    });

    it('should create an instance of Email', () => {
      cy.get(`[data-cy="adresseEmail"]`).type('hm now um');
      cy.get(`[data-cy="adresseEmail"]`).should('have.value', 'hm now um');

      cy.get(`[data-cy="codeStatut"]`).type('like');
      cy.get(`[data-cy="codeStatut"]`).should('have.value', 'like');

      cy.get(`[data-cy="dateStatut"]`).type('2025-04-08');
      cy.get(`[data-cy="dateStatut"]`).blur();
      cy.get(`[data-cy="dateStatut"]`).should('have.value', '2025-04-08');

      cy.get(`[data-cy="codeUsageEmail"]`).type('emphasise alongside');
      cy.get(`[data-cy="codeUsageEmail"]`).should('have.value', 'emphasise alongside');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        email = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', emailPageUrlPattern);
    });
  });
});
