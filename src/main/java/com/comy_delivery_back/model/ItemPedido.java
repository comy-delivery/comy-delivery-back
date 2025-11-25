package com.comy_delivery_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemPedido;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer qtQuantidade;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal vlPrecoUnitario;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal vlSubtotal;

    @Column(length = 300)
    private String dsObservacao;

    @ManyToMany
    @JoinTable(
            name = "item_pedido_adicional",
            joinColumns = @JoinColumn(name = "item_pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "adicional_id")
    )
    private List<Adicional> adicionais = new ArrayList<>();

}
