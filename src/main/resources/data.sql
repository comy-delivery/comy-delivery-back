-- ==================================================================================
-- 1. USUARIOS (Tabela Pai - Herança JOINED)
-- Senha padrão para todos: "SenhaForte123" -> $2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW
-- ==================================================================================

INSERT INTO public.usuario (id, username, password, role_usuario, is_ativo, recuperar, token_recuperacao_senha, expiracao_token)
VALUES
    (1, 'admin_master', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ADMIN', TRUE, FALSE, NULL, NULL),
    (2, 'pizzaria_top', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'RESTAURANTE', TRUE, FALSE, NULL, NULL),
    (3, 'cliente_joao', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'CLIENTE', TRUE, FALSE, NULL, NULL),
    (4, 'motoboy_carlos', '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW', 'ENTREGADOR', TRUE, FALSE, NULL, NULL)
    ON CONFLICT (id) DO NOTHING;

SELECT setval('usuario_id_seq', (SELECT MAX(id) FROM usuario));

-- ==================================================================================
-- 2. DETALHES DOS USUÁRIOS (Tabelas Filhas)
-- ==================================================================================

-- ADMIN
INSERT INTO public.admin (id, nm_admin, cpf_admin, email_admin)
VALUES (1, 'Administrador Geral', '11122233344', 'admin@comy.com')
    ON CONFLICT (id) DO NOTHING;

-- RESTAURANTE
INSERT INTO public.restaurante (id, nm_restaurante, email_restaurante, cnpj, telefone_restaurante, descricao_restaurante, categoria, horario_abertura, horario_fechamento, tempo_media_entrega, is_aberto, is_disponivel, data_cadastro, avaliacao_media_restaurante, imagem_logo, imagem_banner)
VALUES (2, 'Pizzaria Top', 'contato@pizzariatop.com', '12345678000199', '11999990000', 'A melhor pizza artesanal da região.', 'PIZZA', '18:00:00', '23:59:00', 45, TRUE, TRUE, '2024-01-01', 5.0, NULL, NULL)
    ON CONFLICT (id) DO NOTHING;

-- Dias de funcionamento do Restaurante (ElementCollection)
INSERT INTO public.restaurante_dias_funcionamento (restaurante_id, dias_funcionamento) VALUES
                                                                                           (2, 'SEXTA'), (2, 'SABADO'), (2, 'DOMINGO')
    ON CONFLICT DO NOTHING;

-- CLIENTE
INSERT INTO public.cliente (id, nm_cliente, email_cliente, cpf_cliente, telefone_cliente, data_cadastro_cliente)
VALUES (3, 'João da Silva', 'joao@email.com', '99988877766', '11988887777', '2024-01-02')
    ON CONFLICT (id) DO NOTHING;

-- ENTREGADOR
INSERT INTO public.entregador (id, nm_entregador, email_entregador, cpf_entregador, telefone_entregador, veiculo, placa, is_disponivel, data_cadastro_entregador, avaliacao_media_entregador)
VALUES (4, 'Carlos Motoboy', 'carlos@moto.com', '55544433322', '11977776666', 'MOTO', 'ABC1234', TRUE, '2024-01-03', 4.8)
    ON CONFLICT (id) DO NOTHING;

-- ==================================================================================
-- 3. ENDEREÇOS
-- ==================================================================================

INSERT INTO public.endereco (id_endereco, logradouro, numero, bairro, cidade, cep, estado, tipo_endereco, latitude, longitude, ponto_de_referencia, is_padrao, cliente_id, restaurante_id)
VALUES
    (1, 'Rua das Pizzas', '100', 'Centro', 'São Paulo', '01001000', 'SP', 'MATRIZ', -23.550520, -46.633308, 'Próximo à praça', TRUE, NULL, 2),
    (2, 'Av. do Cliente', '500', 'Jardins', 'São Paulo', '01401000', 'SP', 'CASA', -23.561684, -46.655981, 'Portão azul', TRUE, 3, NULL)
    ON CONFLICT (id_endereco) DO NOTHING;

SELECT setval('endereco_id_endereco_seq', (SELECT MAX(id_endereco) FROM endereco));

-- ==================================================================================
-- 4. PRODUTOS E ADICIONAIS
-- ==================================================================================

INSERT INTO public.produto (id_produto, nm_produto, ds_produto, vl_preco, categoria_produto, tempo_preparacao, is_promocao, vl_preco_promocional, is_ativo, restaurante_id, data_cadastro_produto, imagem_produto)
VALUES
    (1, 'Pizza Calabresa', 'Molho de tomate, mussarela e calabresa fatiada', 40.00, 'Pizzas', 30, FALSE, NULL, TRUE, 2, '2024-01-05T10:00:00', NULL),
    (2, 'Pizza Quatro Queijos', 'Mussarela, provolone, parmesão e gorgonzola', 50.00, 'Pizzas', 35, TRUE, 45.00, TRUE, 2, '2024-01-05T10:00:00', NULL),
    (3, 'Coca Cola 2L', 'Refrigerante gelado', 12.00, 'Bebidas', 0, FALSE, NULL, TRUE, 2, '2024-01-05T10:00:00', NULL)
    ON CONFLICT (id_produto) DO NOTHING;

SELECT setval('produto_id_produto_seq', (SELECT MAX(id_produto) FROM produto));

INSERT INTO public.adicional (id_adicional, nm_adicional, ds_adicional, vl_preco_adicional, produto_id, is_disponivel)
VALUES
    (1, 'Borda Recheada', 'Borda de Catupiry Original', 5.00, 1, TRUE),
    (2, 'Bacon Extra', 'Cubos de bacon crocante', 4.00, 1, TRUE)
    ON CONFLICT (id_adicional) DO NOTHING;

SELECT setval('adicional_id_adicional_seq', (SELECT MAX(id_adicional) FROM adicional));

-- ==================================================================================
-- 5. CUPOM
-- ==================================================================================

INSERT INTO public.cupom (id_cupom, codigo_cupom, ds_cupom, tipo_cupom, vl_desconto, percentual_desconto, vl_minimo_pedido, dt_validade, qtd_uso_maximo, qtd_usado, is_ativo, restaurante_id, data_criacao)
VALUES (1, 'BEMVINDO10', 'Desconto de 10 reais na primeira compra', 'VALOR_FIXO', 10.00, NULL, 30.00, '2030-12-31T23:59:59', 100, 0, TRUE, 2, '2024-01-01T00:00:00')
    ON CONFLICT (id_cupom) DO NOTHING;

SELECT setval('cupom_id_cupom_seq', (SELECT MAX(id_cupom) FROM cupom));

-- ==================================================================================
-- 6. PEDIDO (Exemplo de Pedido já finalizado)
-- ==================================================================================

INSERT INTO public.pedido (id_pedido, cliente_id, restaurante_id, endereco_origem_id, endereco_entrega_id, cupom_id, dt_criacao, dt_atualizacao, vl_subtotal, vl_entrega, vl_desconto, vl_total, status, forma_pagamento, tempo_estimado_entrega, ds_observacoes, is_aceito, dt_aceitacao, motivo_recusa)
VALUES (1, 3, 2, 1, 2, 1, '2024-10-10T19:00:00', '2024-10-10T19:05:00', 45.00, 5.00, 10.00, 40.00, 'ENTREGUE', 'CREDITO', 45, 'Sem cebola, por favor.', TRUE, '2024-10-10T19:01:00', NULL)
    ON CONFLICT (id_pedido) DO NOTHING;

SELECT setval('pedido_id_pedido_seq', (SELECT MAX(id_pedido) FROM pedido));

-- ITENS DO PEDIDO
INSERT INTO public.item_pedido (id_item_pedido, pedido_id, produto_id, qt_quantidade, vl_preco_unitario, vl_subtotal, ds_observacao)
VALUES (1, 1, 1, 1, 40.00, 45.00, 'Bem assada')
    ON CONFLICT (id_item_pedido) DO NOTHING;

SELECT setval('item_pedido_id_item_pedido_seq', (SELECT MAX(id_item_pedido) FROM item_pedido));

-- Item Adicional (Tabela de junção)
INSERT INTO public.item_pedido_adicional (item_pedido_id, adicional_id) VALUES (1, 1) ON CONFLICT DO NOTHING;

-- ==================================================================================
-- 7. ENTREGA
-- ==================================================================================

INSERT INTO public.entrega (id_entrega, pedido_id, entregador_id, status_entrega, endereco_origem_id, endereco_destino_id, tempo_estimado_minutos, vl_entrega, data_hora_inicio, data_hora_conclusao, avaliacao_cliente)
VALUES (1, 1, 4, 'CONCLUIDA', 1, 2, 45, 5.00, '2024-10-10T19:10:00', '2024-10-10T19:50:00', 5.0)
    ON CONFLICT (id_entrega) DO NOTHING;

SELECT setval('entrega_id_entrega_seq', (SELECT MAX(id_entrega) FROM entrega));

-- ==================================================================================
-- 8. AVALIAÇÃO
-- ==================================================================================

INSERT INTO public.avaliacao (id_avaliacao, restaurante_id, cliente_id, pedido_id, entregador_id, nu_nota, ds_comentario, dt_avaliacao, avaliacao_entrega)
VALUES (1, 2, 3, 1, 4, 5, 'Pizza chegou quentinha e muito rápida!', '2024-10-10T20:00:00', 5)
    ON CONFLICT (id_avaliacao) DO NOTHING;

SELECT setval('avaliacao_id_avaliacao_seq', (SELECT MAX(id_avaliacao) FROM avaliacao));