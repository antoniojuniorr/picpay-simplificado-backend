/* Limpar carteiras */
DELETE FROM TRANSACAO;

DELETE FROM CARTEIRA;

/* Inserir carteiras */
INSERT INTO
    CARTEIRA (
    ID, NOME_COMPLETO, CPF, EMAIL, "SENHA", "TIPO", SALDO, "VERSAO"
)
VALUES (
   1, 'Joao - User', 12345678900, 'joao@test.com', '123456', 1, 1000.00, 1
);

INSERT INTO
    CARTEIRA (
    ID, NOME_COMPLETO, CPF, EMAIL, "SENHA", "TIPO", SALDO, "VERSAO"
)
VALUES (
   2, 'Maria - Lojista', 12345678901, 'maria@test.com', '123456', 2, 1000.00, 1
);