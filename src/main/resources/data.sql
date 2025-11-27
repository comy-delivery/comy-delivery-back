-- ==================================================================================
-- 1. USUÁRIOS (Tabela Pai - Herança JOINED)
-- Senha padrão: "SenhaForte123" -> $2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW
-- ==================================================================================

-- ADMIN (ID 1)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (1, 'admin_master', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ADMIN',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- RESTAURANTES (IDs 2 ao 21)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (2, 'ponto_perfeito', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (3, 'burgerverso', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (4, 'vapt_vupt', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (5, 'stop_gourmet', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (6, 'doce_brisa', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (7, 'geleia_real', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (8, 'fada_acucar', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (9, 'conf_afeto', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (10, 'tribo_acai', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (11, 'super_fruta', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (12, 'massa_nostra', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (13, 'forno_magico', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (14, 'hashi_ouro', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (15, 'sakura_exp', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (16, 'dragao_wok', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (17, 'o_mandarim', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (18, 'terra_sabor', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (19, 'verde_vibe', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (20, 'temp_brasil', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE),
       (21, 'sabor_casa', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- CLIENTES (IDs 22 ao 26)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (22, 'cliente_ana', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'CLIENTE', TRUE),
       (23, 'cliente_bruno', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'CLIENTE', TRUE),
       (24, 'cliente_carla', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'CLIENTE', TRUE),
       (25, 'cliente_daniel', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'CLIENTE', TRUE),
       (26, 'cliente_elena', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'CLIENTE',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- ENTREGADORES (IDs 27 ao 34)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (27, 'driver_felipe', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE),
       (28, 'driver_gabriela', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE),
       (29, 'driver_hugo', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE),
       (30, 'driver_ines', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE),
       (31, 'driver_joao', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE),
       (32, 'driver_keila', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE),
       (33, 'driver_lucas', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE),
       (34, 'driver_mariana', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- ==================================================================================
-- 2. DETALHES DOS USUÁRIOS
-- ==================================================================================

-- ADMIN (ID 1)
INSERT INTO public.admin (id, nm_admin, cpf_admin, email_admin)
VALUES (1, 'Administrador Master', '83596263000', 'admin@master.com') ON CONFLICT (id) DO NOTHING;

-- RESTAURANTES (IDs 2-21)
INSERT INTO public.restaurante (id, nm_restaurante, email_restaurante, cnpj, telefone_restaurante,
                                descricao_restaurante, categoria, horario_abertura, horario_fechamento,
                                tempo_media_entrega, is_aberto, is_disponivel, data_cadastro,
                                avaliacao_media_restaurante)
VALUES (2, 'O Ponto Perfeito', 'contato@pontoperfeito.com', '06056727000166', '48999991010', 'Hambúrgueres artesanais.',
        'LANCHE', '11:00:00', '23:00:00', 40, TRUE, TRUE, '2024-02-01', 4.5),
       (3, 'Burgerverso', 'alo@burgerverso.com', '28395033000106', '48988882020', 'O melhor smash burger.', 'LANCHE',
        '17:00:00', '02:00:00', 35, TRUE, TRUE, '2024-02-05', 4.8),
       (4, 'Vapt Vupt Lanches', 'pedidos@vaptvupt.com', '35927833000130', '48977773030', 'Lanches rápidos.', 'LANCHE',
        '10:00:00', '22:00:00', 20, TRUE, TRUE, '2024-02-10', 4.2),
       (5, 'O Stop Gourmet', 'sac@stopgourmet.com', '77224919000190', '48966664040', 'Sanduíches sofisticados.',
        'LANCHE', '12:00:00', '23:59:00', 50, TRUE, TRUE, '2024-02-12', 4.9),
       (6, 'Doce Brisa', 'encomendas@docebrisa.com', '32922842000137', '48955555050', 'Bolos e tortas.', 'DOCE',
        '09:00:00', '19:00:00', 30, TRUE, TRUE, '2024-03-01', 4.7),
       (7, 'Geleia Real', 'contato@geleiareal.com', '12474385000103', '48944446060', 'Doces artesanais.', 'DOCE',
        '10:00:00', '18:00:00', 25, TRUE, TRUE, '2024-03-05', 4.6),
       (8, 'A Fada do Açúcar', 'pedidos@fadaacucar.com', '86769039000177', '48933337070', 'Cupcakes e brigadeiros.',
        'DOCE', '11:00:00', '20:00:00', 35, TRUE, TRUE, '2024-03-10', 4.8),
       (9, 'Confeitaria Afeto', 'amor@confeitariaafeto.com', '15096863000103', '48922228080', 'Receitas de vó.', 'DOCE',
        '08:00:00', '18:00:00', 40, TRUE, TRUE, '2024-03-15', 5.0),
       (10, 'Tribo do Açaí', 'verao@tribodoacai.com', '94234287000199', '48911119090', 'O açaí mais puro.', 'DOCE',
        '12:00:00', '22:00:00', 15, TRUE, TRUE, '2024-03-20', 4.5),
       (11, 'Super Fruta Power', 'energia@superfruta.com', '56802322000139', '48900001212', 'Saladas de frutas.',
        'DOCE', '07:00:00', '19:00:00', 20, TRUE, TRUE, '2024-03-25', 4.4),
       (12, 'Massa Nostra', 'pizzas@massanostra.com', '72460277000178', '48912345678', 'Tradição italiana.', 'PIZZA',
        '18:00:00', '23:59:00', 50, TRUE, TRUE, '2024-04-01', 4.7),
       (13, 'O Forno Mágico', 'delivery@fornomagico.com', '27578991000120', '48987654321', 'Pizzas quadradas.', 'PIZZA',
        '18:30:00', '00:30:00', 45, TRUE, TRUE, '2024-04-05', 4.3),
       (14, 'Hashi de Ouro', 'sushi@hashideouro.com', '49233727000137', '48923456789', 'Sushi e Sashimi.', 'ASIATICA',
        '19:00:00', '23:00:00', 60, TRUE, TRUE, '2024-05-01', 4.9),
       (15, 'Sakura Express', 'pedidos@sakuraexpress.com', '16624394000152', '48934567890', 'Comida japonesa rápida.',
        'ASIATICA', '11:00:00', '15:00:00', 30, TRUE, TRUE, '2024-05-10', 4.5),
       (16, 'Dragão Wok', 'yakisoba@dragaowok.com', '51262624000192', '48945678901', 'Yakisoba e Frango Xadrez.',
        'ASIATICA', '18:00:00', '22:30:00', 40, TRUE, TRUE, '2024-05-15', 4.6),
       (17, 'O Mandarim', 'contato@omandarim.com', '18130022000180', '48956789012', 'Culinária chinesa.', 'ASIATICA',
        '11:00:00', '22:00:00', 55, TRUE, TRUE, '2024-05-20', 4.7),
       (18, 'Terra & Sabor', 'fit@terraesabor.com', '10998074000180', '48967890123', 'Marmitas fit.', 'SAUDAVEL',
        '10:00:00', '16:00:00', 30, TRUE, TRUE, '2024-06-01', 4.8),
       (19, 'Verde Vibe', 'vegan@verdevibe.com', '65236284000100', '48978901234', 'Culinária vegana.', 'SAUDAVEL',
        '11:30:00', '20:00:00', 35, TRUE, TRUE, '2024-06-05', 4.9),
       (20, 'Temperos do Meu Brasil', 'feijoada@temperosbrasil.com', '37798828000105', '48989012345',
        'A melhor feijoada.', 'BRASILEIRA', '11:00:00', '16:00:00', 45, TRUE, TRUE, '2024-07-01', 4.6),
       (21, 'Sabor de Casa', 'caseiro@sabordecasa.com', '92725737000130', '48990123456', 'Comida caseira.',
        'BRASILEIRA', '10:30:00', '15:00:00', 25, TRUE, TRUE, '2024-07-10', 4.4) ON CONFLICT (id) DO NOTHING;

-- CLIENTES (IDs 22-26)
INSERT INTO public.cliente (id, nm_cliente, email_cliente, cpf_cliente, telefone_cliente, data_cadastro_cliente)
VALUES (22, 'Ana Souza', 'ana@gmail.com', '22336002062', '48999991111', '2024-01-10'),
       (23, 'Bruno Lima', 'bruno@hotmail.com', '49435321020', '48999992222', '2024-01-12'),
       (24, 'Carla Dias', 'carla@uol.com.br', '15644213004', '48999993333', '2024-02-01'),
       (25, 'Daniel Alves', 'daniel@outlook.com', '73178507000', '48999994444', '2024-02-15'),
       (26, 'Elena Costa', 'elena@gmail.com', '02634066005', '48999995555', '2024-03-01') ON CONFLICT (id) DO NOTHING;

-- ENTREGADORES (IDs 27-34)
INSERT INTO public.entregador (id, nm_entregador, email_entregador, cpf_entregador, telefone_entregador, veiculo, placa,
                               is_disponivel, avaliacao_media_entregador)
VALUES (27, 'Felipe Motos', 'felipe@driver.com', '72437769062', '48988881111', 'MOTO', 'ABC1234', TRUE, 4.8),
       (28, 'Gabriela Bike', 'gabi@driver.com', '12040722060', '48988882222', 'BICICLETA', NULL, TRUE, 4.9),
       (29, 'Hugo Carros', 'hugo@driver.com', '67633963026', '48988883333', 'CARRO', 'XYZ9876', TRUE, 4.7),
       (30, 'Inês Motos', 'ines@driver.com', '38879962002', '48988884444', 'MOTO', 'MTO5555', TRUE, 5.0),
       (31, 'João Silva', 'joao@driver.com', '73377309030', '48988885555', 'MOTO', 'JJO1122', FALSE, 4.5),
       (32, 'Keila Costa', 'keila@driver.com', '33846658087', '48988886666', 'BICICLETA', NULL, TRUE, 4.6),
       (33, 'Lucas Pereira', 'lucas@driver.com', '97531723002', '48988887777', 'MOTO', 'LUC8899', TRUE, 4.8),
       (34, 'Mariana Luz', 'mari@driver.com', '80323242060', '48988888888', 'CARRO', 'MAR2024', TRUE,
        4.9) ON CONFLICT (id) DO NOTHING;

-- ==================================================================================
-- 3. ENDEREÇOS (RESTAURANTES E CLIENTES) - Região de Tubarão/SC
-- ==================================================================================

INSERT INTO public.endereco (logradouro, numero, bairro, cidade, cep, estado, tipo_endereco, latitude, longitude,
                             restaurante_id, cliente_id, is_padrao)
VALUES
-- Restaurantes (IDs 2-21)
('Av. Marcolino Martins Cabral', '100', 'Centro', 'Tubarão', '88701000', 'SC', 'MATRIZ', -28.4812, -49.0064, 2, NULL,
 TRUE),
('Rua Lauro Müller', '250', 'Centro', 'Tubarão', '88701100', 'SC', 'MATRIZ', -28.4820, -49.0070, 3, NULL, TRUE),
('Av. Patrício Lima', '500', 'Humaitá', 'Tubarão', '88704400', 'SC', 'MATRIZ', -28.4750, -49.0150, 4, NULL, TRUE),
('Rua dos Ferroviários', '80', 'Oficinas', 'Tubarão', '88702200', 'SC', 'MATRIZ', -28.4900, -49.0100, 5, NULL, TRUE),
('Rua Altamiro Guimarães', '300', 'Centro', 'Tubarão', '88701050', 'SC', 'MATRIZ', -28.4830, -49.0050, 6, NULL, TRUE),
('Av. José Acácio Moreira', '1200', 'Dehon', 'Tubarão', '88704000', 'SC', 'MATRIZ', -28.4880, -48.9990, 7, NULL, TRUE),
('Rua Padre Geraldo Spettmann', '400', 'Humaitá', 'Tubarão', '88704300', 'SC', 'MATRIZ', -28.4720, -49.0120, 8, NULL,
 TRUE),
('Av. Rodovalho', '55', 'Centro', 'Tubarão', '88701150', 'SC', 'MATRIZ', -28.4805, -49.0080, 9, NULL, TRUE),
('Rua São José', '900', 'Centro', 'Tubarão', '88701200', 'SC', 'MATRIZ', -28.4840, -49.0040, 10, NULL, TRUE),
('Av. Pedro Zapelini', '1500', 'Santo Antônio', 'Tubarão', '88701300', 'SC', 'MATRIZ', -28.4950, -49.0200, 11, NULL,
 TRUE),
('Rua Princesa Isabel', '200', 'Oficinas', 'Tubarão', '88702100', 'SC', 'MATRIZ', -28.4890, -49.0110, 12, NULL, TRUE),
('Av. Exp. José Pedro Coelho', '2200', 'Revoredo', 'Tubarão', '88704700', 'SC', 'MATRIZ', -28.4650, -49.0180, 13, NULL,
 TRUE),
('Rua Marechal Deodoro', '150', 'Centro', 'Tubarão', '88701010', 'SC', 'MATRIZ', -28.4790, -49.0090, 14, NULL, TRUE),
('Av. Getúlio Vargas', '800', 'Centro', 'Capivari de Baixo', '88745000', 'SC', 'MATRIZ', -28.4450, -48.9580, 15, NULL,
 TRUE),
('Rua Tubalcain Faraco', '60', 'Centro', 'Tubarão', '88701120', 'SC', 'MATRIZ', -28.4815, -49.0075, 16, NULL, TRUE),
('Av. Marcolino', '2020', 'Vila Moema', 'Tubarão', '88705000', 'SC', 'MATRIZ', -28.4860, -49.0020, 17, NULL, TRUE),
('Rua Wenceslau Braz', '330', 'Vila Moema', 'Tubarão', '88705100', 'SC', 'MATRIZ', -28.4870, -49.0010, 18, NULL, TRUE),
('Rua Roberto Zumblick', '700', 'Humaitá', 'Tubarão', '88704200', 'SC', 'MATRIZ', -28.4740, -49.0140, 19, NULL, TRUE),
('Rua Vidal Ramos', '110', 'Centro', 'Tubarão', '88701020', 'SC', 'MATRIZ', -28.4800, -49.0060, 20, NULL, TRUE),
('Av. Colombo Salles', '50', 'Centro', 'Laguna', '88790000', 'SC', 'MATRIZ', -28.4810, -48.7800, 21, NULL, TRUE),
-- Clientes (IDs 22-26)
('Rua Silvio Burigo', '1000', 'Monte Castelo', 'Tubarão', '88702500', 'SC', 'CASA', -28.5000, -49.0300, NULL, 22, TRUE),
('Rua Januário Alves Garcia', '250', 'Centro', 'Tubarão', '88701030', 'SC', 'TRABALHO', -28.4825, -49.0055, NULL, 23,
 TRUE),
('Rua Recife', '400', 'Recife', 'Tubarão', '88701500', 'SC', 'CASA', -28.4850, -49.0150, NULL, 24, TRUE),
('Av. Nações Unidas', '80', 'Santo André', 'Capivari de Baixo', '88745000', 'SC', 'CASA', -28.4500, -48.9600, NULL, 25,
 TRUE),
('Av. Calistrato Müller Salles', '500', 'Portinho', 'Laguna', '88790000', 'SC', 'CASA', -28.4850, -48.7900, NULL, 26,
 TRUE) ON CONFLICT DO NOTHING;

-- ==================================================================================
-- 4. PRODUTOS (10 por restaurante, 2 em promoção)
-- ==================================================================================
-- LANCHES (Restaurantes 2, 3, 4, 5)
INSERT INTO public.produto (nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT p.nm_produto,
       p.ds_produto,
       p.vl_preco,
       p.categoria_produto,
       p.tempo_preparacao,
       p.is_promocao,
       p.vl_preco_promocional,
       TRUE,
       r.id,
       NOW()
FROM (VALUES ('X-Bacon', 'Pão, carne, queijo e bacon', 25.00, 'Lanches', 20, FALSE, NULL),
             ('X-Salada', 'Pão, carne, queijo e salada', 20.00, 'Lanches', 20, TRUE, 15.00),
             ('X-Tudo', 'Completo com tudo dentro', 35.00, 'Lanches', 25, FALSE, NULL),
             ('Smash Simples', 'Carne prensada e queijo', 18.00, 'Lanches', 15, FALSE, NULL),
             ('Smash Duplo', 'Duas carnes prensadas', 22.00, 'Lanches', 15, TRUE, 19.00),
             ('Batata Frita P', 'Porção individual', 12.00, 'Acompanhamento', 10, FALSE, NULL),
             ('Batata Frita G', 'Porção família', 20.00, 'Acompanhamento', 15, FALSE, NULL),
             ('Refrigerante Lata', '350ml gelado', 6.00, 'Bebidas', 0, FALSE, NULL),
             ('Suco Natural', 'Laranja 500ml', 10.00, 'Bebidas', 5, FALSE, NULL),
             ('Milkshake', 'Morango 500ml', 18.00, 'Sobremesa', 10, FALSE, NULL)) AS p(nm_produto, ds_produto, vl_preco,
                                                                                       categoria_produto,
                                                                                       tempo_preparacao, is_promocao,
                                                                                       vl_preco_promocional)
         CROSS JOIN (VALUES (2), (3), (4), (5)) AS r(id)
    ON CONFLICT DO NOTHING;

-- DOCES (Restaurantes 6 a 11)
INSERT INTO public.produto (nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT p.nm_produto,
       p.ds_produto,
       p.vl_preco,
       p.categoria_produto,
       p.tempo_preparacao,
       p.is_promocao,
       p.vl_preco_promocional,
       TRUE,
       r.id,
       NOW()
FROM (VALUES ('Bolo de Pote', 'Chocolate belga', 15.00, 'Doces', 0, TRUE, 12.00),
             ('Torta de Limão', 'Fatia generosa', 12.00, 'Doces', 0, FALSE, NULL),
             ('Brigadeiro Gourmet', '4 unidades', 10.00, 'Doces', 0, FALSE, NULL),
             ('Brownie', 'Com nozes', 8.00, 'Doces', 0, TRUE, 6.00),
             ('Cheesecake', 'Frutas vermelhas', 18.00, 'Doces', 0, FALSE, NULL),
             ('Açaí 300ml', 'Puro', 15.00, 'Açaí', 5, FALSE, NULL),
             ('Açaí 500ml', 'Completo', 22.00, 'Açaí', 5, FALSE, NULL),
             ('Água s/ Gás', '500ml', 4.00, 'Bebidas', 0, FALSE, NULL),
             ('Café Expresso', 'Grão moído na hora', 5.00, 'Bebidas', 3, FALSE, NULL),
             ('Coxinha de Morango', 'Chocolate ao redor', 7.00, 'Doces', 0, FALSE, NULL)) AS p(nm_produto, ds_produto,
                                                                                               vl_preco,
                                                                                               categoria_produto,
                                                                                               tempo_preparacao,
                                                                                               is_promocao,
                                                                                               vl_preco_promocional)
         CROSS JOIN (VALUES (6), (7), (8), (9), (10), (11)) AS r(id)
    ON CONFLICT DO NOTHING;

-- PIZZA (Restaurantes 12, 13)
INSERT INTO public.produto (nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT p.nm_produto,
       p.ds_produto,
       p.vl_preco,
       p.categoria_produto,
       p.tempo_preparacao,
       p.is_promocao,
       p.vl_preco_promocional,
       TRUE,
       r.id,
       NOW()
FROM (VALUES ('Pizza Calabresa M', 'Clássica', 40.00, 'Pizza', 30, TRUE, 35.00),
             ('Pizza 4 Queijos G', 'Muito queijo', 60.00, 'Pizza', 35, FALSE, NULL),
             ('Pizza Frango Catupiry M', 'Cremosa', 45.00, 'Pizza', 30, FALSE, NULL),
             ('Pizza Portuguesa G', 'Ovos, presunto, cebola', 58.00, 'Pizza', 35, TRUE, 50.00),
             ('Pizza Marguerita M', 'Manjericão fresco', 38.00, 'Pizza', 30, FALSE, NULL),
             ('Pizza Chocolate P', 'Sobremesa', 30.00, 'Pizza Doce', 25, FALSE, NULL),
             ('Borda Recheada', 'Catupiry', 10.00, 'Adicional', 0, FALSE, NULL),
             ('Coca Cola 2L', 'Gelada', 14.00, 'Bebidas', 0, FALSE, NULL),
             ('Guaraná 2L', 'Gelado', 12.00, 'Bebidas', 0, FALSE, NULL),
             ('Cerveja Long Neck', 'Heineken', 9.00, 'Bebidas', 0, FALSE, NULL)) AS p(nm_produto, ds_produto, vl_preco,
                                                                                      categoria_produto,
                                                                                      tempo_preparacao, is_promocao,
                                                                                      vl_preco_promocional)
         CROSS JOIN (VALUES (12), (13)) AS r(id)
    ON CONFLICT DO NOTHING;

-- ASIATICA (Restaurantes 14, 15, 16, 17)
INSERT INTO public.produto (nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT p.nm_produto,
       p.ds_produto,
       p.vl_preco,
       p.categoria_produto,
       p.tempo_preparacao,
       p.is_promocao,
       p.vl_preco_promocional,
       TRUE,
       r.id,
       NOW()
FROM (VALUES ('Sushi Combo 1', '15 peças variadas', 45.00, 'Sushi', 20, TRUE, 39.90),
             ('Temaki Salmão', 'Completo com cream cheese', 22.00, 'Temaki', 10, FALSE, NULL),
             ('Yakisoba Misto', 'Carne e Frango', 35.00, 'Pratos Quentes', 25, FALSE, NULL),
             ('Hot Roll', '8 unidades', 20.00, 'Sushi', 15, TRUE, 15.00),
             ('Sashimi Salmão', '10 fatias', 30.00, 'Sashimi', 15, FALSE, NULL),
             ('Harumaki', '2 rolinhos primavera', 12.00, 'Entrada', 10, FALSE, NULL),
             ('Sunomono', 'Salada de pepino', 10.00, 'Entrada', 5, FALSE, NULL),
             ('Guioza', '6 unidades vapor', 18.00, 'Entrada', 15, FALSE, NULL),
             ('Refrigerante Lata', 'Lata', 6.00, 'Bebidas', 0, FALSE, NULL),
             ('Água', '500ml', 4.00, 'Bebidas', 0, FALSE, NULL)) AS p(nm_produto, ds_produto, vl_preco,
                                                                      categoria_produto, tempo_preparacao, is_promocao,
                                                                      vl_preco_promocional)
         CROSS JOIN (VALUES (14), (15), (16), (17)) AS r(id)
    ON CONFLICT DO NOTHING;

-- SAUDAVEL (Restaurantes 18, 19)
INSERT INTO public.produto (nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT p.nm_produto,
       p.ds_produto,
       p.vl_preco,
       p.categoria_produto,
       p.tempo_preparacao,
       p.is_promocao,
       p.vl_preco_promocional,
       TRUE,
       r.id,
       NOW()
FROM (VALUES ('Salada Caesar', 'Frango grelhado e molho', 28.00, 'Saladas', 15, TRUE, 22.00),
             ('Wrap de Atum', 'Integral', 20.00, 'Lanches', 10, FALSE, NULL),
             ('Marmita Fit', 'Frango e batata doce', 25.00, 'Refeição', 0, FALSE, NULL),
             ('Suco Detox', 'Verde', 12.00, 'Bebidas', 5, TRUE, 9.00),
             ('Poke Havaiano', 'Salmão e frutas', 40.00, 'Poke', 15, FALSE, NULL),
             ('Omelete Proteico', '3 ovos e queijo', 18.00, 'Refeição', 10, FALSE, NULL),
             ('Tapioca Frango', 'Sem glúten', 16.00, 'Lanches', 10, FALSE, NULL),
             ('Salada de Frutas', 'Mel e granola', 14.00, 'Sobremesa', 5, FALSE, NULL),
             ('Smoothie Manga', 'Com iogurte', 15.00, 'Bebidas', 5, FALSE, NULL),
             ('Água de Coco', 'Natural', 8.00, 'Bebidas', 0, FALSE, NULL)) AS p(nm_produto, ds_produto, vl_preco,
                                                                                categoria_produto, tempo_preparacao,
                                                                                is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (18), (19)) AS r(id)
    ON CONFLICT DO NOTHING;

-- BRASILEIRA (Restaurantes 20, 21)
INSERT INTO public.produto (nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT p.nm_produto,
       p.ds_produto,
       p.vl_preco,
       p.categoria_produto,
       p.tempo_preparacao,
       p.is_promocao,
       p.vl_preco_promocional,
       TRUE,
       r.id,
       NOW()
FROM (VALUES ('Feijoada Completa', 'Para 1 pessoa', 35.00, 'Refeição', 0, TRUE, 29.90),
             ('Prato Feito (PF)', 'Bife, arroz, feijão', 25.00, 'Refeição', 15, FALSE, NULL),
             ('Virado à Paulista', 'Tradicional', 30.00, 'Refeição', 20, FALSE, NULL),
             ('Bife a Cavalo', 'Com ovo frito', 28.00, 'Refeição', 15, TRUE, 24.00),
             ('Parmegiana Frango', 'Com purê', 32.00, 'Refeição', 25, FALSE, NULL),
             ('Strogonoff Carne', 'Com batata palha', 30.00, 'Refeição', 15, FALSE, NULL),
             ('Pudim de Leite', 'Fatia', 10.00, 'Sobremesa', 0, FALSE, NULL),
             ('Mousse Maracujá', 'Potinho', 8.00, 'Sobremesa', 0, FALSE, NULL),
             ('Refrigerante 600ml', 'Garrafa', 8.00, 'Bebidas', 0, FALSE, NULL),
             ('Suco de Laranja', 'Jarra 1L', 20.00, 'Bebidas', 10, FALSE, NULL)) AS p(nm_produto, ds_produto, vl_preco,
                                                                                      categoria_produto,
                                                                                      tempo_preparacao, is_promocao,
                                                                                      vl_preco_promocional)
         CROSS JOIN (VALUES (20), (21)) AS r(id)
    ON CONFLICT DO NOTHING;

-- ==================================================================================
-- 5. ADICIONAIS (2 por produto, lógica por categoria)
-- ==================================================================================

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Extra Bacon', 'Fatias crocantes', 5.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Lanches', 'Pizza')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Queijo Extra', 'Mussarela derretida', 4.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Lanches', 'Pizza')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Leite Condensado', 'Extra', 3.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Doces', 'Açaí', 'Sobremesa', 'Pizza Doce')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Granola', 'Crocante', 2.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Doces', 'Açaí', 'Sobremesa')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Cream Cheese', 'Philadelphia', 6.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Sushi', 'Temaki', 'Sashimi')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Cebolinha', 'Extra', 1.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Sushi', 'Temaki', 'Pratos Quentes')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Ovo Frito', 'Gema mole', 3.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Refeição')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Batata Frita Extra', 'Pequena porção', 8.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Refeição')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Gelo e Limão', 'No copo', 0.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Bebidas')
    ON CONFLICT DO NOTHING;

INSERT INTO public.adicional (nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT 'Copo Descartável', 'Extra', 0.10, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Bebidas')
    ON CONFLICT DO NOTHING;

-- ==================================================================================
-- 6. CUPONS (2 por Restaurante, IDs 2-21)
-- ==================================================================================

INSERT INTO public.cupom (codigo_cupom, ds_cupom, tipo_cupom, vl_desconto, percentual_desconto, vl_minimo_pedido,
                          dt_validade, qtd_uso_maximo, qtd_usado, is_ativo, restaurante_id, data_criacao)
SELECT CONCAT('BEMVINDO_', id),
       'Desconto de boas vindas',
       'VALOR_FIXO',
       10.00,
       NULL,
       30.00,
       '2025-12-31T23:59:59',
       100,
       0,
       TRUE,
       id,
       NOW()
FROM public.restaurante
WHERE id BETWEEN 2 AND 21
    ON CONFLICT (codigo_cupom) DO NOTHING;

INSERT INTO public.cupom (codigo_cupom, ds_cupom, tipo_cupom, vl_desconto, percentual_desconto, vl_minimo_pedido,
                          dt_validade, qtd_uso_maximo, qtd_usado, is_ativo, restaurante_id, data_criacao)
SELECT CONCAT('VIP_', id),
       'Desconto para clientes VIP',
       'PERCENTUAL',
       NULL,
       15.00, -- 15%
       50.00,
       '2025-12-31T23:59:59',
       50,
       0,
       TRUE,
       id,
       NOW()
FROM public.restaurante
WHERE id BETWEEN 2 AND 21
    ON CONFLICT (codigo_cupom) DO NOTHING;

-- ==================================================================================
-- 7. DIAS DE FUNCIONAMENTO
-- ==================================================================================

INSERT INTO public.restaurante_dias_funcionamento (restaurante_id, dias_funcionamento)
SELECT id, dia
FROM public.restaurante r
         CROSS JOIN (VALUES ('TERCA'), ('QUARTA'), ('QUINTA'), ('SEXTA'), ('SABADO'), ('DOMINGO')) AS d(dia)
WHERE id BETWEEN 2 AND 21 ON CONFLICT DO NOTHING;


-- ==================================================================================
-- 8. PEDIDOS (20 Pedidos variados)
-- IDs: 1 a 20
-- Distribuição:
-- - 12 ENTREGUE (Com Avaliação)
-- - 4 SAIU_PARA_ENTREGA / EM_ROTA
-- - 3 CONFIRMADO / EM_PREPARO
-- - 1 CANCELADO
-- ==================================================================================

INSERT INTO public.pedido (id_pedido, cliente_id, restaurante_id, endereco_origem_id, endereco_entrega_id, cupom_id, dt_criacao, dt_atualizacao, vl_subtotal, vl_entrega, vl_desconto, vl_total, status, forma_pagamento, tempo_estimado_entrega, ds_observacoes, is_aceito, dt_aceitacao, motivo_recusa) VALUES
-- Pedidos ENTREGUES (IDs 1-12)
(1, 22, 2, (SELECT id_endereco FROM endereco WHERE restaurante_id=2 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=22 LIMIT 1), NULL, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', 50.00, 5.00, 0.00, 55.00, 'ENTREGUE', 'CREDITO', 40, 'Sem cebola', TRUE, NOW() - INTERVAL '5 days', NULL),
    (2, 23, 3, (SELECT id_endereco FROM endereco WHERE restaurante_id=3 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=23 LIMIT 1), NULL, NOW() - INTERVAL '4 days', NOW() - INTERVAL '4 days', 60.00, 7.00, 0.00, 67.00, 'ENTREGUE', 'PIX', 35, NULL, TRUE, NOW() - INTERVAL '4 days', NULL),
    (3, 24, 4, (SELECT id_endereco FROM endereco WHERE restaurante_id=4 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=24 LIMIT 1), NULL, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 45.00, 5.00, 0.00, 50.00, 'ENTREGUE', 'DEBITO', 30, 'Capricha no molho', TRUE, NOW() - INTERVAL '3 days', NULL),
    (4, 25, 5, (SELECT id_endereco FROM endereco WHERE restaurante_id=5 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=25 LIMIT 1), (SELECT id_cupom FROM cupom WHERE restaurante_id=5 LIMIT 1), NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 80.00, 8.00, 10.00, 78.00, 'ENTREGUE', 'CREDITO', 50, NULL, TRUE, NOW() - INTERVAL '3 days', NULL),
    (5, 26, 6, (SELECT id_endereco FROM endereco WHERE restaurante_id=6 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=26 LIMIT 1), NULL, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 35.00, 5.00, 0.00, 40.00, 'ENTREGUE', 'DINHEIRO', 25, 'Troco para 50', TRUE, NOW() - INTERVAL '2 days', NULL),
    (6, 22, 7, (SELECT id_endereco FROM endereco WHERE restaurante_id=7 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=22 LIMIT 1), NULL, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 40.00, 6.00, 0.00, 46.00, 'ENTREGUE', 'PIX', 30, NULL, TRUE, NOW() - INTERVAL '2 days', NULL),
    (7, 23, 8, (SELECT id_endereco FROM endereco WHERE restaurante_id=8 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=23 LIMIT 1), NULL, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 55.00, 5.00, 0.00, 60.00, 'ENTREGUE', 'CREDITO', 45, NULL, TRUE, NOW() - INTERVAL '1 day', NULL),
    (8, 24, 9, (SELECT id_endereco FROM endereco WHERE restaurante_id=9 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=24 LIMIT 1), NULL, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 30.00, 5.00, 0.00, 35.00, 'ENTREGUE', 'DEBITO', 20, 'Não tocar campainha', TRUE, NOW() - INTERVAL '1 day', NULL),
    (9, 25, 10, (SELECT id_endereco FROM endereco WHERE restaurante_id=10 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=25 LIMIT 1), (SELECT id_cupom FROM cupom WHERE restaurante_id=10 LIMIT 1), NOW() - INTERVAL '5 hours', NOW() - INTERVAL '4 hours', 45.00, 6.00, 10.00, 41.00, 'ENTREGUE', 'PIX', 35, NULL, TRUE, NOW() - INTERVAL '5 hours', NULL),
    (10, 26, 11, (SELECT id_endereco FROM endereco WHERE restaurante_id=11 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=26 LIMIT 1), NULL, NOW() - INTERVAL '4 hours', NOW() - INTERVAL '3 hours', 25.00, 5.00, 0.00, 30.00, 'ENTREGUE', 'DINHEIRO', 25, NULL, TRUE, NOW() - INTERVAL '4 hours', NULL),
    (11, 22, 12, (SELECT id_endereco FROM endereco WHERE restaurante_id=12 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=22 LIMIT 1), NULL, NOW() - INTERVAL '3 hours', NOW() - INTERVAL '2 hours', 90.00, 10.00, 0.00, 100.00, 'ENTREGUE', 'CREDITO', 50, 'Bem passado', TRUE, NOW() - INTERVAL '3 hours', NULL),
    (12, 23, 13, (SELECT id_endereco FROM endereco WHERE restaurante_id=13 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=23 LIMIT 1), NULL, NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', 65.00, 7.00, 0.00, 72.00, 'ENTREGUE', 'PIX', 40, NULL, TRUE, NOW() - INTERVAL '2 hours', NULL),

-- Pedidos EM ROTA (IDs 13-16)
    (13, 24, 14, (SELECT id_endereco FROM endereco WHERE restaurante_id=14 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=24 LIMIT 1), NULL, NOW() - INTERVAL '40 minutes', NOW(), 70.00, 8.00, 0.00, 78.00, 'SAIU_PARA_ENTREGA', 'CREDITO', 45, NULL, TRUE, NOW() - INTERVAL '35 minutes', NULL),
    (14, 25, 15, (SELECT id_endereco FROM endereco WHERE restaurante_id=15 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=25 LIMIT 1), NULL, NOW() - INTERVAL '30 minutes', NOW(), 55.00, 6.00, 0.00, 61.00, 'SAIU_PARA_ENTREGA', 'PIX', 35, NULL, TRUE, NOW() - INTERVAL '25 minutes', NULL),
    (15, 26, 16, (SELECT id_endereco FROM endereco WHERE restaurante_id=16 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=26 LIMIT 1), NULL, NOW() - INTERVAL '25 minutes', NOW(), 40.00, 5.00, 0.00, 45.00, 'SAIU_PARA_ENTREGA', 'DEBITO', 30, NULL, TRUE, NOW() - INTERVAL '20 minutes', NULL),
    (16, 22, 17, (SELECT id_endereco FROM endereco WHERE restaurante_id=17 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=22 LIMIT 1), NULL, NOW() - INTERVAL '20 minutes', NOW(), 85.00, 9.00, 0.00, 94.00, 'SAIU_PARA_ENTREGA', 'CREDITO', 50, NULL, TRUE, NOW() - INTERVAL '15 minutes', NULL),

-- Pedidos EM PREPARO / CONFIRMADO / PENDENTE (IDs 17-19)
    (17, 23, 18, (SELECT id_endereco FROM endereco WHERE restaurante_id=18 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=23 LIMIT 1), NULL, NOW() - INTERVAL '15 minutes', NOW(), 35.00, 5.00, 0.00, 40.00, 'EM_PREPARO', 'PIX', 25, NULL, TRUE, NOW() - INTERVAL '10 minutes', NULL),
    (18, 24, 19, (SELECT id_endereco FROM endereco WHERE restaurante_id=19 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=24 LIMIT 1), NULL, NOW() - INTERVAL '10 minutes', NOW(), 45.00, 5.00, 0.00, 50.00, 'CONFIRMADO', 'CREDITO', 30, 'Retirar cebola', TRUE, NOW() - INTERVAL '5 minutes', NULL),
    (19, 25, 20, (SELECT id_endereco FROM endereco WHERE restaurante_id=20 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=25 LIMIT 1), NULL, NOW() - INTERVAL '5 minutes', NOW(), 60.00, 7.00, 0.00, 67.00, 'PENDENTE', 'DEBITO', 40, NULL, FALSE, NULL, NULL),

-- Pedido CANCELADO (ID 20)
    (20, 26, 21, (SELECT id_endereco FROM endereco WHERE restaurante_id=21 LIMIT 1), (SELECT id_endereco FROM endereco WHERE cliente_id=26 LIMIT 1), NULL, NOW() - INTERVAL '1 day', NOW() - INTERVAL '23 hours', 30.00, 5.00, 0.00, 35.00, 'CANCELADO', 'DINHEIRO', 25, NULL, FALSE, NULL, 'Restaurante sem entregadores')
ON CONFLICT (id_pedido) DO NOTHING;

-- ==================================================================================
-- 9. ITENS DO PEDIDO
-- ==================================================================================

-- Inserção dos itens (2 unidades do primeiro produto encontrado do restaurante)
INSERT INTO public.item_pedido (pedido_id, produto_id, qt_quantidade, vl_preco_unitario, vl_subtotal, ds_observacao)
SELECT
    p.id_pedido,
    (SELECT pr.id_produto FROM produto pr WHERE pr.restaurante_id = p.restaurante_id LIMIT 1),
    2,
    (SELECT pr.vl_preco FROM produto pr WHERE pr.restaurante_id = p.restaurante_id LIMIT 1),
    (SELECT pr.vl_preco FROM produto pr WHERE pr.restaurante_id = p.restaurante_id LIMIT 1) * 2,
    'Padrão'
FROM public.pedido p WHERE p.id_pedido BETWEEN 1 AND 20
ON CONFLICT DO NOTHING;

-- Vinculando adicionais a alguns itens (Pedidos 1, 3, 11, 13)
INSERT INTO public.item_pedido_adicional (item_pedido_id, adicional_id)
SELECT
    ip.id_item_pedido,
    (SELECT a.id_adicional FROM adicional a WHERE a.produto_id = ip.produto_id LIMIT 1)
FROM item_pedido ip
WHERE ip.pedido_id IN (1, 3, 11, 13)
ON CONFLICT DO NOTHING;

-- ==================================================================================
-- 10. ENTREGAS
-- IDs iniciam em 1
-- Entregadores: 27 a 34
-- ==================================================================================

INSERT INTO public.entrega (id_entrega, pedido_id, entregador_id, status_entrega, endereco_origem_id, endereco_destino_id, tempo_estimado_minutos, vl_entrega, data_hora_inicio, data_hora_conclusao, avaliacao_cliente) VALUES
-- Concluídas (IDs 1-12)
(1, 1, 27, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=1), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=1), 40, 5.00, NOW()-INTERVAL '5 days', NOW()-INTERVAL '5 days'+INTERVAL '40 min', 5.0),
(2, 2, 28, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=2), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=2), 35, 7.00, NOW()-INTERVAL '4 days', NOW()-INTERVAL '4 days'+INTERVAL '35 min', 4.0),
(3, 3, 29, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=3), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=3), 30, 5.00, NOW()-INTERVAL '3 days', NOW()-INTERVAL '3 days'+INTERVAL '30 min', 5.0),
(4, 4, 30, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=4), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=4), 50, 8.00, NOW()-INTERVAL '3 days', NOW()-INTERVAL '3 days'+INTERVAL '50 min', 3.0),
(5, 5, 31, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=5), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=5), 25, 5.00, NOW()-INTERVAL '2 days', NOW()-INTERVAL '2 days'+INTERVAL '25 min', 5.0),
(6, 6, 32, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=6), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=6), 30, 6.00, NOW()-INTERVAL '2 days', NOW()-INTERVAL '2 days'+INTERVAL '30 min', 4.5),
(7, 7, 33, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=7), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=7), 45, 5.00, NOW()-INTERVAL '1 day', NOW()-INTERVAL '1 day'+INTERVAL '45 min', 5.0),
(8, 8, 34, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=8), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=8), 20, 5.00, NOW()-INTERVAL '1 day', NOW()-INTERVAL '1 day'+INTERVAL '20 min', 4.0),
(9, 9, 27, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=9), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=9), 35, 6.00, NOW()-INTERVAL '5 hours', NOW()-INTERVAL '4 hours', 5.0),
(10, 10, 28, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=10), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=10), 25, 5.00, NOW()-INTERVAL '4 hours', NOW()-INTERVAL '3 hours', 4.8),
(11, 11, 29, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=11), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=11), 50, 10.00, NOW()-INTERVAL '3 hours', NOW()-INTERVAL '2 hours', 2.0),
(12, 12, 30, 'CONCLUIDA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=12), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=12), 40, 7.00, NOW()-INTERVAL '2 hours', NOW()-INTERVAL '1 hour', 5.0),

-- Em Rota (IDs 13-16)
(13, 13, 31, 'EM_ROTA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=13), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=13), 45, 8.00, NOW()-INTERVAL '10 min', NULL, NULL),
(14, 14, 32, 'EM_ROTA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=14), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=14), 35, 6.00, NOW()-INTERVAL '5 min', NULL, NULL),
(15, 15, 33, 'EM_ROTA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=15), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=15), 30, 5.00, NOW()-INTERVAL '15 min', NULL, NULL),
(16, 16, 34, 'EM_ROTA', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=16), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=16), 50, 9.00, NOW()-INTERVAL '20 min', NULL, NULL),

-- Pendente (ID 17, sem entregador) - Apenas criado a tabela, sem ID definido pois ainda não foi "aceito" pelo entregador no fluxo normal, mas aqui inserimos para teste de listagem
(17, 19, NULL, 'PENDENTE', (SELECT endereco_origem_id FROM pedido WHERE id_pedido=19), (SELECT endereco_entrega_id FROM pedido WHERE id_pedido=19), 25, 5.00, NULL, NULL, NULL)
    ON CONFLICT (id_entrega) DO NOTHING;

-- ==================================================================================
-- 11. AVALIAÇÕES (Apenas para entregas CONCLUIDAS)
-- IDs iniciam em 1
-- ==================================================================================

INSERT INTO public.avaliacao (id_avaliacao, restaurante_id, cliente_id, pedido_id, entregador_id, nu_nota, ds_comentario, dt_avaliacao, avaliacao_entrega) VALUES
                                                                                                                                                               (1, 2, 22, 1, 27, 5, 'Lanche top!', NOW(), 5),
                                                                                                                                                               (2, 3, 23, 2, 28, 4, 'Demorou um pouco, mas estava bom.', NOW(), 4),
                                                                                                                                                               (3, 4, 24, 3, 29, 5, 'Muito rápido.', NOW(), 5),
                                                                                                                                                               (4, 5, 25, 4, 30, 5, 'Sabor incrível.', NOW(), 3),
                                                                                                                                                               (5, 6, 26, 5, 31, 5, 'Doce maravilhoso.', NOW(), 5),
                                                                                                                                                               (6, 7, 22, 6, 32, 4, 'Bom.', NOW(), 5),
                                                                                                                                                               (7, 8, 23, 7, 33, 5, 'Amei o cupcake.', NOW(), 5),
                                                                                                                                                               (8, 9, 24, 8, 34, 3, 'Achei meio seco.', NOW(), 4),
                                                                                                                                                               (9, 10, 25, 9, 27, 5, 'Açaí caprichado.', NOW(), 5),
                                                                                                                                                               (10, 11, 26, 10, 28, 4, 'Frutas frescas.', NOW(), 5),
                                                                                                                                                               (11, 12, 22, 11, 29, 2, 'Pizza chegou fria e revirada.', NOW(), 2),
                                                                                                                                                               (12, 13, 23, 12, 30, 5, 'Pizza quadrada é vida.', NOW(), 5)
    ON CONFLICT (id_avaliacao) DO NOTHING;

-- ==================================================================================
-- 12. ATUALIZAÇÃO DAS MÉDIAS (Restaurantes e Entregadores)
-- ==================================================================================

-- Atualizar média dos Restaurantes
UPDATE public.restaurante r
SET avaliacao_media_restaurante = (
    SELECT COALESCE(AVG(a.nu_nota), 0.0)
    FROM public.avaliacao a
    WHERE a.restaurante_id = r.id
)
WHERE r.id IN (SELECT DISTINCT restaurante_id FROM public.avaliacao);

-- Atualizar média dos Entregadores
UPDATE public.entregador e
SET avaliacao_media_entregador = (
    SELECT COALESCE(AVG(a.avaliacao_entrega), 0.0)
    FROM public.avaliacao a
    WHERE a.entregador_id = e.id
)
WHERE e.id IN (SELECT DISTINCT entregador_id FROM public.avaliacao);

-- ==================================================================================
-- 13. ATUALIZAÇÃO DE SEQUÊNCIAS (Para evitar erros futuros)
-- ==================================================================================

SELECT setval('pedido_id_pedido_seq', (SELECT MAX(id_pedido) FROM pedido));
SELECT setval('item_pedido_id_item_pedido_seq', (SELECT MAX(id_item_pedido) FROM item_pedido));
SELECT setval('entrega_id_entrega_seq', (SELECT MAX(id_entrega) FROM entrega));
SELECT setval('avaliacao_id_avaliacao_seq', (SELECT MAX(id_avaliacao) FROM avaliacao));

SELECT setval('usuario_id_seq', (SELECT MAX(id) FROM usuario));
SELECT setval('endereco_id_endereco_seq', (SELECT MAX(id_endereco) FROM endereco));
SELECT setval('produto_id_produto_seq', (SELECT MAX(id_produto) FROM produto));
SELECT setval('adicional_id_adicional_seq', (SELECT MAX(id_adicional) FROM adicional));
SELECT setval('cupom_id_cupom_seq', (SELECT MAX(id_cupom) FROM cupom));