package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.FormaPagamento;
import com.comy_delivery_back.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id", nullable = false)
    private Endereco enderecoEntrega;

    @ManyToOne
    @JoinColumn(name = "endereco_origem_id", nullable = false)
    private Endereco enderecoOrigem;


    @ManyToOne
    @JoinColumn(name = "cupom_id")
    private Cupom cupom;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itensPedido = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dtCriacao = LocalDateTime.now();

    private LocalDateTime dtAtualizacao;

    @Column(precision = 10, scale = 2)
    private BigDecimal vlSubtotal = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal vlEntrega = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal vlDesconto = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal vlTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @Column
    private Integer tempoEstimadoEntrega;

    @Column(length = 500)
    private String dsObservacoes;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isAceito = false;

    private LocalDateTime dtAceitacao;

    @Column(length = 300)
    private String motivoRecusa;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, optional = true)
    private Entrega entrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();


}
