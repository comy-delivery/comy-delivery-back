-- ==================================================================================
-- SCRIPT DE POPULAÇÃO CORRIGIDO E VALIDADO
-- ==================================================================================

-- ==================================================================================
-- 1. USUÁRIOS (Tabela Pai - Herança JOINED) - SEM ALTERAÇÕES
-- ==================================================================================

-- ADMIN (ID 1)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (1, 'admin_master', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ADMIN',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- RESTAURANTES (IDs 2 ao 21)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (2, 'ponto_perfeito', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (3, 'burgerverso', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (4, 'vapt_vupt', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (5, 'stop_gourmet', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (6, 'doce_brisa', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (7, 'geleia_real', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (8, 'fada_acucar', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (9, 'conf_afeto', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (10, 'tribo_acai', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (11, 'super_fruta', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (12, 'massa_nostra', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (13, 'forno_magico', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (14, 'hashi_ouro', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (15, 'sakura_exp', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (16, 'dragao_wok', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (17, 'o_mandarim', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (18, 'terra_sabor', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (19, 'verde_vibe', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (20, 'temp_brasil', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE', TRUE),
       (21, 'sabor_casa', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'RESTAURANTE',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- CLIENTES (IDs 22 ao 26)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (22, 'cliente_ana', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'CLIENTE', TRUE),
       (23, 'cliente_bruno', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'CLIENTE', TRUE),
       (24, 'cliente_carla', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'CLIENTE', TRUE),
       (25, 'cliente_daniel', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'CLIENTE', TRUE),
       (26, 'cliente_elena', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'CLIENTE',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- ENTREGADORES (IDs 27 ao 34)
INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo)
VALUES (27, 'driver_felipe', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR', TRUE),
       (28, 'driver_gabriela', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR', TRUE),
       (29, 'driver_hugo', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR', TRUE),
       (30, 'driver_ines', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR', TRUE),
       (31, 'driver_joao', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR', TRUE),
       (32, 'driver_keila', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR', TRUE),
       (33, 'driver_lucas', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR', TRUE),
       (34, 'driver_mariana', '$2a$10$CgsJdAealQODbmdKpEQIWevMNDmnzik7MOw1KAnCJD27z3wAhJglW', 'ENTREGADOR',
        TRUE) ON CONFLICT (id) DO NOTHING;

-- ==================================================================================
-- 2. DETALHES DOS USUÁRIOS - SEM ALTERAÇÕES
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
-- 3. ENDEREÇOS (RESTAURANTES E CLIENTES) - Região de Tubarão/SC - SEM ALTERAÇÕES
-- ==================================================================================

INSERT INTO public.endereco (id_endereco, logradouro, numero, bairro, cidade, cep, estado, tipo_endereco, latitude, longitude,
                             restaurante_id, cliente_id, is_padrao)
VALUES
-- Restaurantes (IDs 2-21 -> Endereços 102-121)
(102, 'Av. Marcolino Martins Cabral', '100', 'Centro', 'Tubarão', '88701000', 'SC', 'MATRIZ', -28.4812, -49.0064, 2, NULL, TRUE),
(103, 'Rua Lauro Müller', '250', 'Centro', 'Tubarão', '88701100', 'SC', 'MATRIZ', -28.4820, -49.0070, 3, NULL, TRUE),
(104, 'Av. Patrício Lima', '500', 'Humaitá', 'Tubarão', '88704400', 'SC', 'MATRIZ', -28.4750, -49.0150, 4, NULL, TRUE),
(105, 'Rua dos Ferroviários', '80', 'Oficinas', 'Tubarão', '88702200', 'SC', 'MATRIZ', -28.4900, -49.0100, 5, NULL, TRUE),
(106, 'Rua Altamiro Guimarães', '300', 'Centro', 'Tubarão', '88701050', 'SC', 'MATRIZ', -28.4830, -49.0050, 6, NULL, TRUE),
(107, 'Av. José Acácio Moreira', '1200', 'Dehon', 'Tubarão', '88704000', 'SC', 'MATRIZ', -28.4880, -48.9990, 7, NULL, TRUE),
(108, 'Rua Padre Geraldo Spettmann', '400', 'Humaitá', 'Tubarão', '88704300', 'SC', 'MATRIZ', -28.4720, -49.0120, 8, NULL, TRUE),
(109, 'Av. Rodovalho', '55', 'Centro', 'Tubarão', '88701150', 'SC', 'MATRIZ', -28.4805, -49.0080, 9, NULL, TRUE),
(110, 'Rua São José', '900', 'Centro', 'Tubarão', '88701200', 'SC', 'MATRIZ', -28.4840, -49.0040, 10, NULL, TRUE),
(111, 'Av. Pedro Zapelini', '1500', 'Santo Antônio', 'Tubarão', '88701300', 'SC', 'MATRIZ', -28.4950, -49.0200, 11, NULL, TRUE),
(112, 'Rua Princesa Isabel', '200', 'Oficinas', 'Tubarão', '88702100', 'SC', 'MATRIZ', -28.4890, -49.0110, 12, NULL, TRUE),
(113, 'Av. Exp. José Pedro Coelho', '2200', 'Revoredo', 'Tubarão', '88704700', 'SC', 'MATRIZ', -28.4650, -49.0180, 13, NULL, TRUE),
(114, 'Rua Marechal Deodoro', '150', 'Centro', 'Tubarão', '88701010', 'SC', 'MATRIZ', -28.4790, -49.0090, 14, NULL, TRUE),
(115, 'Av. Getúlio Vargas', '800', 'Centro', 'Capivari de Baixo', '88745000', 'SC', 'MATRIZ', -28.4450, -48.9580, 15, NULL, TRUE),
(116, 'Rua Tubalcain Faraco', '60', 'Centro', 'Tubarão', '88701120', 'SC', 'MATRIZ', -28.4815, -49.0075, 16, NULL, TRUE),
(117, 'Av. Marcolino', '2020', 'Vila Moema', 'Tubarão', '88705000', 'SC', 'MATRIZ', -28.4860, -49.0020, 17, NULL, TRUE),
(118, 'Rua Wenceslau Braz', '330', 'Vila Moema', 'Tubarão', '88705100', 'SC', 'MATRIZ', -28.4870, -49.0010, 18, NULL, TRUE),
(119, 'Rua Roberto Zumblick', '700', 'Humaitá', 'Tubarão', '88704200', 'SC', 'MATRIZ', -28.4740, -49.0140, 19, NULL, TRUE),
(120, 'Rua Vidal Ramos', '110', 'Centro', 'Tubarão', '88701020', 'SC', 'MATRIZ', -28.4800, -49.0060, 20, NULL, TRUE),
(121, 'Av. Colombo Salles', '50', 'Centro', 'Laguna', '88790000', 'SC', 'MATRIZ', -28.4810, -48.7800, 21, NULL, TRUE),
-- Clientes (IDs 22-26 -> Endereços 122-126)
(122, 'Rua Silvio Burigo', '1000', 'Monte Castelo', 'Tubarão', '88702500', 'SC', 'CASA', -28.5000, -49.0300, NULL, 22, TRUE),
(123, 'Rua Januário Alves Garcia', '250', 'Centro', 'Tubarão', '88701030', 'SC', 'TRABALHO', -28.4825, -49.0055, NULL, 23, TRUE),
(124, 'Rua Recife', '400', 'Recife', 'Tubarão', '88701500', 'SC', 'CASA', -28.4850, -49.0150, NULL, 24, TRUE),
(125, 'Av. Nações Unidas', '80', 'Santo André', 'Capivari de Baixo', '88745000', 'SC', 'CASA', -28.4500, -48.9600, NULL, 25, TRUE),
(126, 'Av. Calistrato Müller Salles', '500', 'Portinho', 'Laguna', '88790000', 'SC', 'CASA', -28.4850, -48.7900, NULL, 26, TRUE)
    ON CONFLICT (id_endereco) DO NOTHING;

-- ==================================================================================
-- 4. PRODUTOS (10 por restaurante, 2 em promoção) - SEM ALTERAÇÕES
-- ==================================================================================

-- LANCHES (Restaurantes 2, 3)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Hamburguer Artesanal de Carne', 'Pão, carne, queijo, alface e tomate e bacon', 35.00, 'Lanches', 20, FALSE, NULL),
          (2, 'Hamburguer Artesanal de Frango', 'Pão, frango, onion rings, queijo e salada', 25.00, 'Lanches', 20, TRUE, 15.00),
          (3, 'Hamburguer Artesanal Vegetariano', 'Pão, carne de soja, picles, vegan cheese e salada', 35.00, 'Lanches', 25, FALSE, NULL),
          (4, 'Hamburguer Bacon Cheddar', 'Triplo burguer, com salada, cebola caramelizada e  muito cheddar', 55.00, 'Acompanhamento', 35, FALSE, NULL),
          (5, 'Batata Rustica', '300 g de batata rustica temperada com ervas', 22.00, 'Lanches', 15, TRUE, 19.00),
          (6, 'Refrigerante Lata', '350ml gelado', 6.00, 'Bebidas', 0, FALSE, NULL)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (2), (3)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- FastFood (Restaurantes 4, 5)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Hamburguer de Carne', 'Pão, carne, queijo, alface e tomete e bacon', 35.00, 'Hmburguer', 5, FALSE, NULL),
          (2, 'Hamburguer de Frango', 'Pão, frango, onion rings, queijo e salada', 25.00, 'Hamburguer', 5, TRUE, 15.00),
          (3, 'Combo de 2 burguer, 2 batatas e Refrigerante', 'Combo para uma grande fome', 80.00, 'Combo', 10, FALSE, NULL),
          (4, 'Combo burguer, batata e refrigerante', 'Combo ', 55.00, 'Combo', 10, FALSE, NULL),
          (5, 'Batata frit M', '350 g de batata frita', 12.00, 'Acompanhamentos', 10, TRUE, 9.00),
          (6, 'Refrigerante de Cola', '350ml gelado', 6.00, 'Bebidas', 0, FALSE, NULL),
          (7, 'Refrigerante de Laranja', '350ml gelado', 6.00, 'Bebidas', 0, FALSE, NULL),
          (8, 'Refrigerante de Limão', '350ml gelado', 6.00, 'Bebidas', 0, FALSE, NULL)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (4), (5)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- DOCES (Restaurantes 8, 9)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Bolo de Cenoura', 'Bolo de cenoura com cobertura de chocolate', 15.00, 'Bolos', 0, TRUE, 12.00),
          (2, 'Bolo de chocolate', 'Bolo de chocolate belga servido com sorvete de baunilha e cobertura', 18.00, 'Bolos', 0, FALSE, NULL),
          (3, 'Bolo de coco', 'Bolo de coco macio e com recheio de brigadeiro branco', 10.00, 'Bolos', 0, FALSE, NULL),
          (4, 'Bolo Red Velvet', 'Bolo red velvet, com cobertura de chantilly e frutas vermelhas', 8.00, 'Bolos', 0, TRUE, 6.00)

     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (8), (9)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;


--SORVETE (Restaurantes 6, 7)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Milkshake de Morango', 'Bolo de cenoura com cobertura de chocolate', 15.00, 'Milkshake', 0, TRUE, 12.00),
          (2, 'Milkshake de Morango', 'Bolo de chocolate belga servido com sorvete de baunilha e cobertura', 18.00, 'Milkshake', 0, FALSE, NULL),
          (3, 'Sorvete de baunilha', 'Duas bolas de sorvete com cobertura', 6.00, 'Casquinha', 0, FALSE, NULL),
          (4, 'Sorvete de chocolate', 'Duas bolas de sorvete com cobertura', 6.00, 'Casquinha', 0, TRUE, 5.00),
          (5, 'Sorvete de morango', 'Duas bolas de sorvete com cobertura', 6.00, 'Casquinha', 0, TRUE, 4.00)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (6), (7)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

--AÇAI (Restaurantes 10, 11)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Açaí 300ml', 'Copo de açai para personalização de 300ml', 15.00, 'Açaí', 0, TRUE, 12.00),
          (2, 'Açaí 500ml', 'Copo de açai para personalização de 500ml', 18.00, 'Açaí', 0, FALSE, NULL),
          (3, 'Açaí 1 litro', 'Copo de açai para personalização de 1Litro', 22.00, 'Açaí', 0, FALSE, NULL)

     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES  (10), (11)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- PIZZA (Restaurantes 12, 13)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Pizza de Bacon e Milho', 'Pizza de 12 fatias', 40.00, 'Pizza Salgada', 30, TRUE, 35.00),
          (2, 'Pizza Calabreza', 'Pizza de calabresa manjericão e mussarela', 35.00, 'Pizza Salgada', 35, FALSE, NULL),
          (3, 'Pizza Dois Amores', 'Pizza de chocolate preto com morango', 45.00, 'Pizza Doce', 30, FALSE, NULL),
          (4, 'Pizza de Frango', 'Pizza de frango com catupiry', 58.00, 'Pizza Salgada', 35, TRUE, 50.00),
          (5, 'Pizza de Quatro Queijos', 'Mussarela, gorgonzola, parmesão, provolone ', 38.00, 'Pizza Salgada', 30, FALSE, NULL)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (12), (13)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- JAPONESA (Restaurantes 14, 15)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Sushi Combo 1', '15 peças variadas', 45.00, 'Sushi', 20, TRUE, 39.90),
          (2, 'Temaki Salmão', 'Completo com cream cheese', 22.00, 'Temaki', 10, FALSE, NULL),
          (3, 'Yakisoba Misto', 'Carne e Frango', 35.00, 'Pratos Quentes', 25, FALSE, NULL),
          (4, 'Hot Roll', '8 unidades', 20.00, 'Sushi', 15, TRUE, 15.00),
          (5, 'Sashimi Salmão', '10 fatias', 30.00, 'Sashimi', 15, FALSE, NULL),
          (6, 'Harumaki', '2 rolinhos primavera', 12.00, 'Entrada', 10, FALSE, NULL),
          (7, 'Sunomono', 'Salada de pepino', 10.00, 'Entrada', 5, FALSE, NULL),
          (8, 'Guioza', '6 unidades vapor', 18.00, 'Entrada', 15, FALSE, NULL),
          (9, 'Refrigerante Lata', 'Lata', 6.00, 'Bebidas', 0, FALSE, NULL),
          (10, 'Água', '500ml', 4.00, 'Bebidas', 0, FALSE, NULL)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (14), (15)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- Vegetariana (Restaurantes 18, 19)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Buddha Bowl', 'Grão de bico, brocolis, abobora e arroz', 28.00, 'Pratos', 15, TRUE, 22.00),
          (2, 'Feijoada Vegetariana', 'Feijoada preparada sem carne, servida com couve', 20.00, 'Pratos', 10, FALSE, NULL),
          (3, 'Hamburguer Vegetariano', 'Hamburguer de soja, alface, tomate, cebola', 25.00, 'Prato', 0, FALSE, NULL),
          (4, 'Moqueca de Palmito', 'Uma delicia baiana', 32.00, 'Pratos', 5, TRUE, 9.00),
          (5, 'Salada de Grãos', 'Pepino, alface, pimentão', 20.00, 'Salada', 15, FALSE, NULL),
          (6, 'Strogonoff de Cogumelo', 'Strogonoff vegetariano deito de cogumelo', 35.00, 'Refeição', 10, FALSE, NULL)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (18), (19)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- Chinesa (Restaurantes 16, 17)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Frango xadrez', 'Bowl de frango xadrez', 18.00, 'Pratos', 15, TRUE, 22.00),
          (2, 'Guioza Chinesa', 'Guioza de carne', 20.00, 'Pratos', 10, FALSE, NULL),
          (3, 'Kung Pao Chicken', 'Bowl de kung pao chicken com arroz', 25.00, 'Prato', 0, FALSE, NULL),
          (4, 'Mapo Tofu', 'Tofu grelhado', 12.00, 'Pratos', 5, TRUE, 9.00),
          (5, 'Rolinho primavera', 'Rolinho de queijo, carne e vegetariano (6 un)', 20.00, 'Acompanhamento', 15, FALSE, NULL),
          (6, 'Yakisoba', 'Carne, Frango ou Vegetariano', 35.00, 'Pratos', 10, FALSE, NULL)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (16), (17)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- BRASILEIRA (Restaurantes 20, 21)
INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao,
                            vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto)
SELECT
    (r.id * 100) + p.idx,
    p.nm_produto,
    p.ds_produto,
    p.vl_preco,
    p.categoria_produto,
    p.tempo_preparacao,
    p.is_promocao,
    p.vl_preco_promocional,
    TRUE,
    r.id,
    NOW()
FROM (VALUES
          (1, 'Ala Minuta', 'Arroz, Feijão, Bife, Ovo e Batata Frita', 35.00, 'Refeição', 0, TRUE, 29.90),
          (2, 'Feijoada', 'Acompanha arroz, couve, laranja', 25.00, 'Refeição', 15, FALSE, NULL),
          (3, 'Galeto Assado', 'Frango assado, acompanha brocolis e arroz', 30.00, 'Refeição', 20, FALSE, NULL),
          (4, 'Macarrão a Bolonhesa', 'Macarrão, carne moída, parmesão ralado', 28.00, 'Massas', 15, TRUE, 24.00),
          (5, 'Parmegiana Carne', 'Acompanha arroz e batata frita', 32.00, 'Refeição', 25, FALSE, NULL),
          (6, 'Strogonoff Carne', 'Com arroz e batata palha', 30.00, 'Refeição', 15, FALSE, NULL)
     ) AS p(idx, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional)
         CROSS JOIN (VALUES (20), (21)) AS r(id)
    ON CONFLICT (id_produto) DO NOTHING;

-- ==================================================================================
-- 5. ADICIONAIS - SEM ALTERAÇÕES
-- ==================================================================================

-- 5.1 Adicionais para Lanches, Burgers e Pizzas Salgadas
INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 1, 'Extra Bacon', 'Fatias crocantes', 5.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Lanches', 'Hamburguer', 'Hmburguer', 'Pizza Salgada', 'Combo', 'Acompanhamento', 'Acompanhamentos')
    ON CONFLICT (id_adicional) DO NOTHING;

INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 2, 'Queijo Extra', 'Mussarela derretida', 4.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Lanches', 'Hamburguer', 'Hmburguer', 'Pizza Salgada', 'Combo', 'Acompanhamento', 'Acompanhamentos')
    ON CONFLICT (id_adicional) DO NOTHING;

-- 5.2 Adicionais para Doces, Açaí, Sorvetes e Pizza Doce
INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 1, 'Leite Condensado', 'Extra', 3.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('DOCE', 'Bolos', 'Açaí', 'Sobremesa', 'Pizza Doce', 'Milkshake', 'Casquinha')
    ON CONFLICT (id_adicional) DO NOTHING;

INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 2, 'Granola Crocante', 'Porção extra', 2.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Açaí', 'Milkshake', 'Casquinha')
    ON CONFLICT (id_adicional) DO NOTHING;

-- 5.3 Adicionais para Comida Japonesa/Asiática
INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 1, 'Cream Cheese', 'Philadelphia Original', 6.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Sushi', 'Temaki', 'Temakis', 'Sashimi', 'Poke', 'Entrada')
    ON CONFLICT (id_adicional) DO NOTHING;

INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 2, 'Cebolinha', 'Extra', 1.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Sushi', 'Temaki', 'Temakis', 'Pratos Quentes', 'Lamens', 'Poke')
    ON CONFLICT (id_adicional) DO NOTHING;

-- 5.4 Adicionais para Refeições e Pratos (Brasileira/Saudável)
INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 1, 'Ovo Frito', 'Gema mole', 3.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Refeição', 'Pratos', 'Prato', 'Massas', 'Brasileira')
    ON CONFLICT (id_adicional) DO NOTHING;

INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 2, 'Batata Frita Extra', 'Pequena porção', 8.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Refeição', 'Pratos', 'Prato')
    ON CONFLICT (id_adicional) DO NOTHING;

-- 5.5 Adicionais para Bebidas
INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
SELECT (id_produto * 10) + 1, 'Gelo e Limão', 'No copo', 0.00, id_produto, TRUE
FROM public.produto
WHERE categoria_produto IN ('Bebidas')
    ON CONFLICT (id_adicional) DO NOTHING;

-- ==================================================================================
-- 6. CUPONS - SEM ALTERAÇÕES
-- ==================================================================================

INSERT INTO public.cupom (id_cupom, codigo_cupom, ds_cupom, tipo_cupom, vl_desconto, percentual_desconto, vl_minimo_pedido, dt_validade, qtd_uso_maximo, qtd_usado, is_ativo, restaurante_id, data_criacao)
SELECT (id * 10) + 1, CONCAT('BEMVINDO_', id), 'Desconto de boas vindas', 'VALOR_FIXO', 10.00, NULL, 30.00, '2025-12-31T23:59:59', 100, 0, TRUE, id, NOW()
FROM public.restaurante WHERE id BETWEEN 2 AND 21 ON CONFLICT (id_cupom) DO NOTHING;

INSERT INTO public.cupom (id_cupom, codigo_cupom, ds_cupom, tipo_cupom, vl_desconto, percentual_desconto, vl_minimo_pedido, dt_validade, qtd_uso_maximo, qtd_usado, is_ativo, restaurante_id, data_criacao)
SELECT (id * 10) + 2, CONCAT('VIP_', id), 'Desconto para clientes VIP', 'PERCENTUAL', NULL, 15.00, 50.00, '2025-12-31T23:59:59', 50, 0, TRUE, id, NOW()
FROM public.restaurante WHERE id BETWEEN 2 AND 21 ON CONFLICT (id_cupom) DO NOTHING;

-- ==================================================================================
-- 7. DIAS DE FUNCIONAMENTO - SEM ALTERAÇÕES
-- ==================================================================================

INSERT INTO public.restaurante_dias_funcionamento (restaurante_id, dias_funcionamento)
SELECT id, dia
FROM public.restaurante r
         CROSS JOIN (VALUES ('TERCA'), ('QUARTA'), ('QUINTA'), ('SEXTA'), ('SABADO'), ('DOMINGO')) AS d(dia)
WHERE id BETWEEN 2 AND 21 ON CONFLICT DO NOTHING;

-- ==================================================================================
-- 8. PEDIDOS - CORRIGIDO (USO DE IDs EXPLÍCITOS DE ENDEREÇO)
-- ==================================================================================

INSERT INTO public.pedido (id_pedido, cliente_id, restaurante_id, endereco_origem_id, endereco_entrega_id, cupom_id, dt_criacao, dt_atualizacao, vl_subtotal, vl_entrega, vl_desconto, vl_total, status, forma_pagamento, tempo_estimado_entrega, ds_observacoes, is_aceito, dt_aceitacao, motivo_recusa) VALUES
-- Pedidos ENTREGUES (IDs 1-12)
-- Pedido 1: Restaurante 2 (Endereço 102), Cliente 22 (Endereço 122)
(1, 22, 2, 102, 122, NULL, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', 50.00, 5.00, 0.00, 55.00, 'ENTREGUE', 'CREDITO', 40, 'Sem cebola', TRUE, NOW() - INTERVAL '5 days', NULL),
-- Pedido 2: Restaurante 3 (Endereço 103), Cliente 23 (Endereço 123)
(2, 23, 3, 103, 123, NULL, NOW() - INTERVAL '4 days', NOW() - INTERVAL '4 days', 60.00, 7.00, 0.00, 67.00, 'ENTREGUE', 'PIX', 35, NULL, TRUE, NOW() - INTERVAL '4 days', NULL),
-- Pedido 3: Restaurante 4 (Endereço 104), Cliente 24 (Endereço 124)
(3, 24, 4, 104, 124, NULL, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 45.00, 5.00, 0.00, 50.00, 'ENTREGUE', 'DEBITO', 30, 'Capricha no molho', TRUE, NOW() - INTERVAL '3 days', NULL),
-- Pedido 4: Restaurante 5 (Endereço 105), Cliente 25 (Endereço 125)
(4, 25, 5, 105, 125, (SELECT id_cupom FROM cupom WHERE restaurante_id=5 LIMIT 1), NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 80.00, 8.00, 10.00, 78.00, 'ENTREGUE', 'CREDITO', 50, NULL, TRUE, NOW() - INTERVAL '3 days', NULL),
-- Pedido 5: Restaurante 6 (Endereço 106), Cliente 26 (Endereço 126)
    (5, 26, 6, 106, 126, NULL, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 35.00, 5.00, 0.00, 40.00, 'ENTREGUE', 'DINHEIRO', 25, 'Troco para 50', TRUE, NOW() - INTERVAL '2 days', NULL),
-- Pedido 6: Restaurante 7 (Endereço 107), Cliente 22 (Endereço 122)
    (6, 22, 7, 107, 122, NULL, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 40.00, 6.00, 0.00, 46.00, 'ENTREGUE', 'PIX', 30, NULL, TRUE, NOW() - INTERVAL '2 days', NULL),
-- Pedido 7: Restaurante 8 (Endereço 108), Cliente 23 (Endereço 123)
    (7, 23, 8, 108, 123, NULL, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 55.00, 5.00, 0.00, 60.00, 'ENTREGUE', 'CREDITO', 45, NULL, TRUE, NOW() - INTERVAL '1 day', NULL),
-- Pedido 8: Restaurante 9 (Endereço 109), Cliente 24 (Endereço 124)
    (8, 24, 9, 109, 124, NULL, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 30.00, 5.00, 0.00, 35.00, 'ENTREGUE', 'DEBITO', 20, 'Não tocar campainha', TRUE, NOW() - INTERVAL '1 day', NULL),
-- Pedido 9: Restaurante 10 (Endereço 110), Cliente 25 (Endereço 125)
    (9, 25, 10, 110, 125, (SELECT id_cupom FROM cupom WHERE restaurante_id=10 LIMIT 1), NOW() - INTERVAL '5 hours', NOW() - INTERVAL '4 hours', 45.00, 6.00, 10.00, 41.00, 'ENTREGUE', 'PIX', 35, NULL, TRUE, NOW() - INTERVAL '5 hours', NULL),
-- Pedido 10: Restaurante 11 (Endereço 111), Cliente 26 (Endereço 126)
    (10, 26, 11, 111, 126, NULL, NOW() - INTERVAL '4 hours', NOW() - INTERVAL '3 hours', 25.00, 5.00, 0.00, 30.00, 'ENTREGUE', 'DINHEIRO', 25, NULL, TRUE, NOW() - INTERVAL '4 hours', NULL),
-- Pedido 11: Restaurante 12 (Endereço 112), Cliente 22 (Endereço 122)
    (11, 22, 12, 112, 122, NULL, NOW() - INTERVAL '3 hours', NOW() - INTERVAL '2 hours', 90.00, 10.00, 0.00, 100.00, 'ENTREGUE', 'CREDITO', 50, 'Bem passado', TRUE, NOW() - INTERVAL '3 hours', NULL),
-- Pedido 12: Restaurante 13 (Endereço 113), Cliente 23 (Endereço 123)
    (12, 23, 13, 113, 123, NULL, NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', 65.00, 7.00, 0.00, 72.00, 'ENTREGUE', 'PIX', 40, NULL, TRUE, NOW() - INTERVAL '2 hours', NULL),

-- Pedidos EM ROTA (IDs 13-16)
-- Pedido 13: Restaurante 14 (Endereço 114), Cliente 24 (Endereço 124)
    (13, 24, 14, 114, 124, NULL, NOW() - INTERVAL '40 minutes', NOW(), 70.00, 8.00, 0.00, 78.00, 'SAIU_PARA_ENTREGA', 'CREDITO', 45, NULL, TRUE, NOW() - INTERVAL '35 minutes', NULL),
-- Pedido 14: Restaurante 15 (Endereço 115), Cliente 25 (Endereço 125)
    (14, 25, 15, 115, 125, NULL, NOW() - INTERVAL '30 minutes', NOW(), 55.00, 6.00, 0.00, 61.00, 'SAIU_PARA_ENTREGA', 'PIX', 35, NULL, TRUE, NOW() - INTERVAL '25 minutes', NULL),
-- Pedido 15: Restaurante 16 (Endereço 116), Cliente 26 (Endereço 126)
    (15, 26, 16, 116, 126, NULL, NOW() - INTERVAL '25 minutes', NOW(), 40.00, 5.00, 0.00, 45.00, 'SAIU_PARA_ENTREGA', 'DEBITO', 30, NULL, TRUE, NOW() - INTERVAL '20 minutes', NULL),
-- Pedido 16: Restaurante 17 (Endereço 117), Cliente 22 (Endereço 122)
    (16, 22, 17, 117, 122, NULL, NOW() - INTERVAL '20 minutes', NOW(), 85.00, 9.00, 0.00, 94.00, 'SAIU_PARA_ENTREGA', 'CREDITO', 50, NULL, TRUE, NOW() - INTERVAL '15 minutes', NULL),

-- Pedidos EM PREPARO / CONFIRMADO / PENDENTE (IDs 17-19)
-- Pedido 17: Restaurante 18 (Endereço 118), Cliente 23 (Endereço 123)
    (17, 23, 18, 118, 123, NULL, NOW() - INTERVAL '15 minutes', NOW(), 35.00, 5.00, 0.00, 40.00, 'EM_PREPARO', 'PIX', 25, NULL, TRUE, NOW() - INTERVAL '10 minutes', NULL),
-- Pedido 18: Restaurante 19 (Endereço 119), Cliente 24 (Endereço 124)
    (18, 24, 19, 119, 124, NULL, NOW() - INTERVAL '10 minutes', NOW(), 45.00, 5.00, 0.00, 50.00, 'CONFIRMADO', 'CREDITO', 30, 'Retirar cebola', TRUE, NOW() - INTERVAL '5 minutes', NULL),
-- Pedido 19: Restaurante 20 (Endereço 120), Cliente 25 (Endereço 125)
    (19, 25, 20, 120, 125, NULL, NOW() - INTERVAL '5 minutes', NOW(), 60.00, 7.00, 0.00, 67.00, 'PENDENTE', 'DEBITO', 40, NULL, FALSE, NULL, NULL),

-- Pedido CANCELADO (ID 20)
-- Pedido 20: Restaurante 21 (Endereço 121), Cliente 26 (Endereço 126)
    (20, 26, 21, 121, 126, NULL, NOW() - INTERVAL '1 day', NOW() - INTERVAL '23 hours', 30.00, 5.00, 0.00, 35.00, 'CANCELADO', 'DINHEIRO', 25, NULL, FALSE, NULL, 'Restaurante sem entregadores')
ON CONFLICT (id_pedido) DO NOTHING;

-- ==================================================================================
-- 9. ITENS DO PEDIDO - CORRIGIDO
-- ==================================================================================

INSERT INTO public.item_pedido (id_item_pedido, pedido_id, produto_id, qt_quantidade, vl_preco_unitario, vl_subtotal, ds_observacao)
SELECT
    p.id_pedido, -- ID do item = ID do pedido para facilitar
    p.id_pedido,
    (SELECT pr.id_produto FROM produto pr WHERE pr.restaurante_id = p.restaurante_id ORDER BY RANDOM() LIMIT 1),
    2,
    0, -- Valor inicial 0, atualizado abaixo
    0,
    'Padrão'
FROM public.pedido p
WHERE p.id_pedido BETWEEN 1 AND 20
ON CONFLICT (id_item_pedido) DO NOTHING;

-- Atualizar preços corretos nos itens
UPDATE item_pedido ip
SET
    vl_preco_unitario = pr.vl_preco,
    vl_subtotal = pr.vl_preco * ip.qt_quantidade
    FROM produto pr
WHERE ip.produto_id = pr.id_produto;

-- Vinculando adicionais
INSERT INTO public.item_pedido_adicional (item_pedido_id, adicional_id)
SELECT
    ip.id_item_pedido,
    (SELECT a.id_adicional FROM adicional a WHERE a.produto_id = ip.produto_id LIMIT 1)
FROM item_pedido ip
WHERE EXISTS (SELECT 1 FROM adicional a WHERE a.produto_id = ip.produto_id)
  AND ip.id_item_pedido <= 10
ON CONFLICT DO NOTHING;

-- ==================================================================================
-- 10. ENTREGAS - CORRIGIDO (IDs de Endereço Explícitos)
-- ==================================================================================

INSERT INTO public.entrega (id_entrega, pedido_id, entregador_id, status_entrega, endereco_origem_id, endereco_destino_id, tempo_estimado_minutos, vl_entrega, data_hora_inicio, data_hora_conclusao, avaliacao_cliente) VALUES
-- Entregas CONCLUIDAS (IDs 1-12)
(1, 1, 27, 'CONCLUIDA', 102, 122, 40, 5.00, NOW()-INTERVAL '5 days', NOW()-INTERVAL '5 days'+INTERVAL '40 min', 5.0),
(2, 2, 28, 'CONCLUIDA', 103, 123, 35, 7.00, NOW()-INTERVAL '4 days', NOW()-INTERVAL '4 days'+INTERVAL '35 min', 4.0),
(3, 3, 29, 'CONCLUIDA', 104, 124, 30, 5.00, NOW()-INTERVAL '3 days', NOW()-INTERVAL '3 days'+INTERVAL '30 min', 5.0),
(4, 4, 30, 'CONCLUIDA', 105, 125, 50, 8.00, NOW()-INTERVAL '3 days', NOW()-INTERVAL '3 days'+INTERVAL '50 min', 3.0),
(5, 5, 31, 'CONCLUIDA', 106, 126, 25, 5.00, NOW()-INTERVAL '2 days', NOW()-INTERVAL '2 days'+INTERVAL '25 min', 5.0),
(6, 6, 32, 'CONCLUIDA', 107, 122, 30, 6.00, NOW()-INTERVAL '2 days', NOW()-INTERVAL '2 days'+INTERVAL '30 min', 4.5),
(7, 7, 33, 'CONCLUIDA', 108, 123, 45, 5.00, NOW()-INTERVAL '1 day', NOW()-INTERVAL '1 day'+INTERVAL '45 min', 5.0),
(8, 8, 34, 'CONCLUIDA', 109, 124, 20, 5.00, NOW()-INTERVAL '1 day', NOW()-INTERVAL '1 day'+INTERVAL '20 min', 4.0),
(9, 9, 27, 'CONCLUIDA', 110, 125, 35, 6.00, NOW()-INTERVAL '5 hours', NOW()-INTERVAL '4 hours', 5.0),
(10, 10, 28, 'CONCLUIDA', 111, 126, 25, 5.00, NOW()-INTERVAL '4 hours', NOW()-INTERVAL '3 hours', 4.8),
(11, 11, 29, 'CONCLUIDA', 112, 122, 50, 10.00, NOW()-INTERVAL '3 hours', NOW()-INTERVAL '2 hours', 2.0),
(12, 12, 30, 'CONCLUIDA', 113, 123, 40, 7.00, NOW()-INTERVAL '2 hours', NOW()-INTERVAL '1 hour', 5.0),

-- Entregas EM_ROTA (IDs 13-16)
(13, 13, 31, 'EM_ROTA', 114, 124, 45, 8.00, NOW()-INTERVAL '10 min', NULL, NULL),
(14, 14, 32, 'EM_ROTA', 115, 125, 35, 6.00, NOW()-INTERVAL '5 min', NULL, NULL),
(15, 15, 33, 'EM_ROTA', 116, 126, 30, 5.00, NOW()-INTERVAL '15 min', NULL, NULL),
(16, 16, 34, 'EM_ROTA', 117, 122, 50, 9.00, NOW()-INTERVAL '20 min', NULL, NULL),

-- Entrega PENDENTE (ID 17 - Vinculada ao Pedido 19)
(17, 19, NULL, 'PENDENTE', 120, 125, 25, 5.00, NULL, NULL, NULL)
    ON CONFLICT (id_entrega) DO NOTHING;

-- ==================================================================================
-- 11. AVALIAÇÕES - SEM ALTERAÇÕES
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
-- 12. ATUALIZAÇÃO DAS MÉDIAS
-- ==================================================================================

UPDATE public.restaurante r
SET avaliacao_media_restaurante = (
    SELECT COALESCE(AVG(a.nu_nota), 0.0)
    FROM public.avaliacao a
    WHERE a.restaurante_id = r.id
)
WHERE r.id IN (SELECT DISTINCT restaurante_id FROM public.avaliacao);

UPDATE public.entregador e
SET avaliacao_media_entregador = (
    SELECT COALESCE(AVG(a.avaliacao_entrega), 0.0)
    FROM public.avaliacao a
    WHERE a.entregador_id = e.id
)
WHERE e.id IN (SELECT DISTINCT entregador_id FROM public.avaliacao);

-- ==================================================================================
-- 13. ATUALIZAÇÃO DE SEQUÊNCIAS (ESSENCIAL PARA NÃO DAR ERRO AO CRIAR NOVO REGISTRO)
-- ==================================================================================

SELECT setval('usuario_id_seq', (SELECT MAX(id) FROM usuario));
SELECT setval('pedido_id_pedido_seq', (SELECT MAX(id_pedido) FROM pedido));
SELECT setval('item_pedido_id_item_pedido_seq', (SELECT MAX(id_item_pedido) FROM item_pedido));
SELECT setval('entrega_id_entrega_seq', (SELECT MAX(id_entrega) FROM entrega));
SELECT setval('avaliacao_id_avaliacao_seq', (SELECT MAX(id_avaliacao) FROM avaliacao));
SELECT setval('endereco_id_endereco_seq', (SELECT MAX(id_endereco) FROM endereco));
SELECT setval('produto_id_produto_seq', (SELECT MAX(id_produto) FROM produto));
SELECT setval('adicional_id_adicional_seq', (SELECT MAX(id_adicional) FROM adicional));
SELECT setval('cupom_id_cupom_seq', (SELECT MAX(id_cupom) FROM cupom));