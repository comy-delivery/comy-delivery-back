package com.comy_delivery_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entregador{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntregador;

    @OneToOne
    @MapsId
    private Usuario usuario;
}
