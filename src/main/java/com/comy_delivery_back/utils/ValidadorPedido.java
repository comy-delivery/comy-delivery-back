package com.comy_delivery_back.utils;

import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.exception.RegraDeNegocioException;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.model.Pedido;
import com.comy_delivery_back.model.Produto;
import com.comy_delivery_back.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPedido {

    public void validarRestauranteAberto(Restaurante restaurante) {
        if (!restaurante.isAberto()) {
            throw new RegraDeNegocioException("Restaurante fechado");
        }
    }

    public void validarProdutoDisponivel(Produto produto) {
        if (!produto.isAtivo()) {
            throw new RegraDeNegocioException("Produto não disponível");
        }
    }

    public void validarEnderecoCliente(Endereco endereco, Long clienteId) {
        if (endereco.getCliente() == null ||
                !endereco.getCliente().getId().equals(clienteId)) {
            throw new RegraDeNegocioException("Endereço não pertence ao cliente");
        }
    }

    public void validarPedidoPodeSerModificado(Pedido pedido) {
        if (!StatusPedido.PENDENTE.equals(pedido.getStatus()) &&
                !StatusPedido.CONFIRMADO.equals(pedido.getStatus())) {
            throw new RegraDeNegocioException("Pedido não pode ser modificado");
        }
    }

}
