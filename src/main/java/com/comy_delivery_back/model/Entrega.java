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

    //@OneToOne
    //@JoinColumn(name = "pedido_id"
    //private Pedido pedido

    @ManyToOne
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;

    @Enumerated(EnumType.STRING)
    private StatusEntrega statusEntrega;

    @ManyToOne
    @JoinColumn(name = "endereco_origem_id", nullable = false)
    private Endereco enderecoOrigem;

    @ManyToOne
    @JoinColumn(name = "endereco_destino_id", nullable = false)
    private Endereco enderecoDestino;


    private LocalDateTime dtHrInicio;

    private Integer tempoEstimado;

    private LocalDateTime dtHrConclusao;

    private Double vlEntrega;

    private Double avaliacaoCliente;
}
