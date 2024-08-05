-- RODE O SCRIPT NO SEU BANCO DE DADOS

-- Inserindo dados na tabela USUARIOS
INSERT INTO USUARIOS (id, username, password, role)
VALUES (1, 'ana@email.com.br', '$2a$10$AtWo422MdyRQ1RgPzmJNnuDB7xN0GW38sXT4rnBFBqGnMyVmVEf4O', 'ROLE_ADMIN');
INSERT INTO USUARIOS (id, username, password, role)
VALUES (2, 'bia@email.com.br', '$2a$10$AtWo422MdyRQ1RgPzmJNnuDB7xN0GW38sXT4rnBFBqGnMyVmVEf4O', 'ROLE_CLIENTE');
INSERT INTO USUARIOS (id, username, password, role)
VALUES (3, 'bob@email.com.br', '$2a$10$AtWo422MdyRQ1RgPzmJNnuDB7xN0GW38sXT4rnBFBqGnMyVmVEf4O', 'ROLE_CLIENTE');

-- Inserindo dados na tabela CLIENTES
INSERT INTO CLIENTES (id, nome, cpf, id_usuario)
VALUES (21, 'Beatriz Rodrigues', '09191773016', 1);
INSERT INTO CLIENTES (id, nome, cpf, id_usuario)
VALUES (22, 'Rodrigo Silva', '98401203015', 2);

-- Inserindo dados na tabela VAGAS
INSERT INTO VAGAS (id, codigo, status)
VALUES (1, 'A-01', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status)
VALUES (2, 'A-02', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status)
VALUES (3, 'A-03', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status)
VALUES (4, 'A-04', 'LIVRE');
INSERT INTO VAGAS (id, codigo, status)
VALUES (5, 'A-05', 'LIVRE');

-- Inserindo dados na tabela CLIENTES_TEM_VAGAS
INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
VALUES ('20240313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2024-03-13 10:15:00', 22, 1);
INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
VALUES ('20240314-101400', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2024-03-14 10:15:00', 21, 2);
INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
VALUES ('20240315-101500', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2024-03-14 10:15:00', 22, 3);
