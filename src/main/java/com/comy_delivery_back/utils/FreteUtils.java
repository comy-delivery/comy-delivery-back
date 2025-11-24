package com.comy_delivery_back.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class FreteUtils {


    private static final BigDecimal TAXA_BASE = new BigDecimal("5.00");
    private static final BigDecimal TAXA_POR_KM = new BigDecimal("0.10");
    private static final BigDecimal TAXA_NOTURNA = new BigDecimal("10.00");


    public static BigDecimal calcularFrete(double distanciaKm) {
        return calcularFrete(distanciaKm, LocalTime.now());
    }

    public static BigDecimal calcularFrete(double distanciaKm, LocalTime horario) {
        BigDecimal distancia = BigDecimal.valueOf(distanciaKm);

        BigDecimal frete = TAXA_BASE.add(distancia.multiply(TAXA_POR_KM));

        // Taxa noturna: 00:00 às 05:59
        if (isTaxaNoturna(horario)) {
            frete = frete.add(TAXA_NOTURNA);
        }

        return frete.setScale(2, RoundingMode.HALF_UP);
    }

    private static boolean isTaxaNoturna(LocalTime horario) {
        return (horario.equals(LocalTime.MIDNIGHT) || horario.isAfter(LocalTime.MIDNIGHT))
                && horario.isBefore(LocalTime.of(6, 0));
    }

}
