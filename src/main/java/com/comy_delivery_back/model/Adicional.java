package com.comy_delivery_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Adicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdicional;

    @Column(nullable = false)
    private String nmAdicional;

    @Column(length = 100)
    private String dsAdicional;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal vlPrecoAdicional;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(columnDefinition = "boolean default true")
    private boolean isDisponivel;

}
