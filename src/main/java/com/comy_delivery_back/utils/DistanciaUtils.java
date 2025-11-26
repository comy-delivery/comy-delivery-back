package com.comy_delivery_back.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistanciaUtils {


    private static final double RAIO_TERRA_KM = 6371.0;


    public static double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {

        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            throw new IllegalArgumentException("Coordenadas não podem ser nulas");
        }

        if (lat1.equals(lat2) && lon1.equals(lon2)) {
            log.warn("As coordenadas de origem e destino são idênticas. Distância 0.");
            return 0.0;
        }

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distanciaKm = RAIO_TERRA_KM * c;
        double distanciaArredondada = Math.round(distanciaKm * 1000.0) / 1000.0;

        if (distanciaArredondada == 0.0 && !lat1.equals(lat2)) {
            return 0.01;
        }

        return distanciaArredondada;
    }

}
