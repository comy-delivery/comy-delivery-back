package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.StatusEntrega;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrega;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", unique = true, nullable = false)
    private Pedido pedido;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntrega statusEntrega = StatusEntrega.PENDENTE;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_origem_id", nullable = false)
    private Endereco enderecoOrigem;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_destino_id", nullable = false)
    private Endereco enderecoDestino;

    private LocalDateTime dataHoraInicio;

    private Integer tempoEstimadoMinutos;

    private LocalDateTime dataHoraConclusao;

    private Double vlEntrega;

    private Double avaliacaoCliente;
}
