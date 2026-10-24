-- AutoRent - Script de População do Banco de Dados

-- Este script insere dados nas tabelas já criada pelo Hibernate (hibernate.hbm2ddl.auto = create).
-- Execute APÓS o deploy da aplicação (quando as tabelas e sequences já existirem no banco).

BEGIN;

SELECT setval('accessory_seq', 100, false);

INSERT INTO accessory_table (id, name, description, dailyprice) VALUES
(1, 'GPS',               'Sistema de navegação GPS integrado com mapas atualizados',   15.00),
(2, 'Cadeirinha Infantil','Cadeirinha para criança com certificação INMETRO',           25.00),
(3, 'Rack de Teto',      'Rack de teto para bagagens e equipamentos esportivos',       20.00),
(4, 'Seguro Premium',    'Cobertura total contra danos, furto e terceiros',            45.00),
(5, 'Wi-Fi Portátil',    'Roteador portátil 4G com dados ilimitados',                  18.00),
(6, 'Câmera de Ré',      'Câmera traseira com sensor de estacionamento',               12.00),
(7, 'Proteção de Vidros', 'Seguro adicional para vidros, retrovisores e faróis',       10.00),
(8, 'Assento Booster',   'Assento elevado para crianças de 4 a 10 anos',               15.00);


SELECT setval('agency_seq', 100, false);

INSERT INTO agency_table (id, name, phonenumber, country, state, city, neighborhood, street, number, complement, code) VALUES
(1, 'AutoRent Centro SP',        '(11) 3333-1001', 'Brasil', 'SP', 'São Paulo',      'Centro',          'Av. São João',           '1200', 'Loja 3',     '01035-000'),
(2, 'AutoRent Aeroporto GRU',    '(11) 2445-2002', 'Brasil', 'SP', 'Guarulhos',      'Cumbica',         'Rod. Hélio Smidt',       's/n',  'Terminal 2', '07190-100'),
(3, 'AutoRent Barra RJ',         '(21) 3344-3003', 'Brasil', 'RJ', 'Rio de Janeiro', 'Barra da Tijuca', 'Av. das Américas',       '4666', 'Bloco B',    '22640-102'),
(4, 'AutoRent Savassi BH',       '(31) 3222-4004', 'Brasil', 'MG', 'Belo Horizonte', 'Savassi',         'Rua Pernambuco',         '1000', NULL,         '30130-150'),
(5, 'AutoRent Boa Viagem Recife', '(81) 3366-5005', 'Brasil', 'PE', 'Recife',         'Boa Viagem',      'Av. Conselheiro Aguiar', '2000', 'Sala 12',    '51020-010');


SELECT setval('user_seq', 100, false);

INSERT INTO user_table (id, name, cpf, email, passwordhash, role) VALUES
(1, 'Carlos Silva',      '111.222.333-44', 'carlos.silva@email.com',   '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251', 'CUSTOMER'),
(2, 'Maria Oliveira',    '222.333.444-55', 'maria.oliveira@email.com', '7428557649a482e5ae23a41b2264c7605bc19d389558b2cf26c2640eca59c7b7', 'CUSTOMER'),
(3, 'João Santos',       '333.444.555-66', 'joao.santos@email.com',    '3a659983678ac013e50cdcd313c4b8a8d7110dc5c176ac79c5a015d752ae2068', 'CUSTOMER'),
(4, 'Ana Costa',         '444.555.666-77', 'ana.costa@email.com',       'ec7be197de9fbe3a60c8af23a9ffad7783bb684470c9d137ef4bde4e56354b70', 'CUSTOMER'),
(5, 'Admin AutoRent',    '999.888.777-66', 'admin@autorent.com',        '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'EMPLOYEE');


SELECT setval('vehicle_seq', 100, false);

INSERT INTO vehicle_table (id, licenseplate, brand, model, year, category, dailyprice, status, agency_id) VALUES
-- Agência 1
( 1, 'ABC-1A23', 'Volkswagen', 'Polo',           2024, 'HATCH',  120.00, 'AVAILABLE',   1),
( 2, 'DEF-2B34', 'Chevrolet',  'Onix',           2024, 'HATCH',  115.00, 'AVAILABLE',   1),
( 3, 'GHI-3C45', 'Toyota',     'Corolla',        2024, 'SEDAN',  180.00, 'AVAILABLE',   1),
( 4, 'JKL-4D56', 'Honda',      'Civic',          2023, 'SEDAN',  175.00, 'RENTED',      1),
-- Agência 2
( 5, 'MNO-5E67', 'Jeep',       'Compass',        2024, 'SUV',    250.00, 'AVAILABLE',   2),
( 6, 'PQR-6F78', 'Hyundai',    'Tucson',         2024, 'SUV',    240.00, 'AVAILABLE',   2),
( 7, 'STU-7G89', 'Toyota',     'Corolla Cross',  2024, 'SUV',    260.00, 'RESERVED',    2),
( 8, 'VWX-8H90', 'Fiat',       'Mobi',           2023, 'HATCH',   95.00, 'AVAILABLE',   2),
-- Agência 3
( 9, 'YZA-9I01', 'Toyota',     'Hilux',          2024, 'PICKUP', 320.00, 'AVAILABLE',   3),
(10, 'BCD-0J12', 'Chevrolet',  'S10',            2023, 'PICKUP', 300.00, 'RENTED',      3),
(11, 'EFG-1K23', 'BMW',        '320i',           2024, 'COUPE',  450.00, 'AVAILABLE',   3),
(12, 'HIJ-2L34', 'Renault',    'Kwid',           2024, 'HATCH',   90.00, 'MAINTENANCE', 3),
-- Agência 4  
(13, 'KLM-3M45', 'Volkswagen', 'T-Cross',        2024, 'SUV',    220.00, 'AVAILABLE',   4),
(14, 'NOP-4N56', 'Fiat',       'Pulse',          2024, 'SUV',    200.00, 'AVAILABLE',   4),
(15, 'QRS-5O67', 'Fiat',       'Ducato',         2023, 'VAN',    350.00, 'AVAILABLE',   4),
(16, 'TUV-6P78', 'Hyundai',    'HB20',           2024, 'HATCH',  110.00, 'AVAILABLE',   5),
(17, 'WXY-7Q89', 'Chevrolet',  'Tracker',        2024, 'SUV',    230.00, 'RESERVED',    5),
(18, 'ZAB-8R90', 'Nissan',     'Kicks',          2023, 'SUV',    210.00, 'AVAILABLE',   5);

INSERT INTO vehicle_table_accessory_table (vehicle_id, accessories_id) VALUES
(1, 1), (1, 6),
(2, 1),
(3, 1), (3, 4), (3, 5),
(4, 1), (4, 4), (4, 6),
(5, 1), (5, 3), (5, 4), (5, 5),
(6, 1), (6, 2), (6, 4),
(7, 1), (7, 3), (7, 5),
(9, 1), (9, 3), (9, 4),
(10, 1), (10, 3),
(11, 1), (11, 4), (11, 5), (11, 6),
(13, 1), (13, 2), (13, 6),
(14, 1), (14, 7),
(15, 1), (15, 5),
(16, 1),
(17, 1), (17, 2), (17, 8),
(18, 1), (18, 6);

SELECT setval('rental_seq', 100, false);

INSERT INTO rental_table (id, user_id, vehicle_id, pickupagency_id, returnagency_id, pickupdate, expectedreturndate, returndate, status, totalprice) VALUES
(1, 1,  3, 1, 1, '2026-09-10', '2026-09-15', '2026-09-15', 'COMPLETED',    900.00),
(2, 2,  4, 2, 2, '2026-10-20', '2026-10-27', NULL,         'IN_PROGRESS', 1225.00),
(3, 3, 10, 3, 3, '2026-09-15', '2026-09-22', '2026-09-22', 'COMPLETED',  2100.00),
(4, 4, 13, 4, 4, '2026-11-01', '2026-11-05', NULL,         'RESERVED',    880.00),
(5, 1, 17, 5, 5, '2026-10-05', '2026-10-08', NULL,         'CANCELLED',   690.00);

COMMIT;

-- Usuários para testes:
-- ┌──────────────────┬─────────────────┬──────────┬──────────┐
-- │ Nome             │ CPF             │ Senha    │ Role     │
-- ├──────────────────┼─────────────────┼──────────┼──────────┤
-- │ Carlos Silva     │ 111.222.333-44  │ senha123 │ CUSTOMER │
-- │ Maria Oliveira   │ 222.333.444-55  │ maria456 │ CUSTOMER │
-- │ João Santos      │ 333.444.555-66  │ joao789  │ CUSTOMER │
-- │ Ana Costa        │ 444.555.666-77  │ ana2024  │ CUSTOMER │
-- │ Admin AutoRent   │ 999.888.777-66  │ admin123 │ EMPLOYEE │
-- └──────────────────┴─────────────────┴──────────┘──────────┘