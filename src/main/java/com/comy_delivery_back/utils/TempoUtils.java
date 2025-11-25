package com.comy_delivery_back.utils;

public class TempoUtils {
    // Estimativa: 2 minutos por km (velocidade média de 30km/h)
    private static final int MINUTOS_POR_KM = 2;
    // Tempo fixo de coleta/entrega (ex: estacionar, subir elevador)
    private static final int TEMPO_ADICIONAL_FIXO = 5;

    public static Integer calcularTempoEntrega(Integer tempoMedioRestaurante, double distanciaKm) {
        if (tempoMedioRestaurante == null) {
            tempoMedioRestaurante = 30; // Valor default caso não tenha
        }

        int tempoDeslocamento = (int) Math.ceil(distanciaKm * MINUTOS_POR_KM);

        return tempoMedioRestaurante + tempoDeslocamento + TEMPO_ADICIONAL_FIXO;
    }
}
