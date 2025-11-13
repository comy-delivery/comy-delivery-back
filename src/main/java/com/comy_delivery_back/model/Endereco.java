package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.TipoEndereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEndereco;

    @Column(nullable=false)
    private String logradouro;

    @Column(nullable=false)
    private String numero;

    @Column
    private String complemento;

    @Column
    private String bairro;

    @Column(nullable=false)
    private String cidade;

    @Column(nullable=false)
    private String cep;

    @Column
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoEndereco tipoEndereco;

    @Column(length = 500)
    private String pontoDeReferecia;

    @Column(columnDefinition = "boolean default true")
    private boolean isPadrao;

    private Double latitude;

    private Double longitude;

//    @ManyToOne
//    @JoinColumn(name = "cliente_id")
//    private Cliente cliente;
//
//    @ManyToOne
//    @JoinColumn(name = "restaurante_id")
//    private Restaurante restaurante;
}
