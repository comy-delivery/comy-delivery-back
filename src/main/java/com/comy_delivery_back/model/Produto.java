package com.comy_delivery_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @Column(nullable = false)
    private String nmProduto;

    @Column(length = 500)
    private String dsProduto;

    @Column(precision = 10, scale = 2, nullable = false)
    private Double vlPreco;

    @Lob
    private byte[] imagemProduto;

//    @ManyToOne
//    @JoinColumn(name = "restaurante_id", nullable = false)
//    private Restaurante restaurante;

    @Column(nullable = false, length = 100)
    private String categoriaProduto;

    @Column
    private Integer tempoPreparacao;

    @Column(nullable = false)
    private Boolean isPromocao = false;

    @Column(precision = 10, scale = 2)
    private Double vlPrecoPromocional;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivo;

    @Column(nullable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

}
