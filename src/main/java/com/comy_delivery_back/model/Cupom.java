package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.TipoCupom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCupom;

    @Column(unique = true, nullable = false)
    private String codigoCupom;

    @Column(length = 100)
    private String dsCupom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCupom tipoCupom;

    @Column(precision = 10, scale = 2)
    private BigDecimal vlDesconto;

    private BigDecimal percentualDesconto;

    @Column(precision = 10, scale = 2)
    private BigDecimal vlMinimoPedido;

    @Column(nullable = false)
    private LocalDateTime dtValidade;

    @Column(nullable = false)
    private Integer qtdUsoMaximo;

    @Column(nullable = false)
    private Integer qtdUsado = 0;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivo;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

}
