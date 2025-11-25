package com.comy_delivery_back.enums;

import java.time.DayOfWeek;

public enum DiasSemana {
    SEGUNDA(DayOfWeek.MONDAY),
    TERCA(DayOfWeek.TUESDAY),
    QUARTA(DayOfWeek.WEDNESDAY),
    QUINTA(DayOfWeek.THURSDAY),
    SEXTA(DayOfWeek.FRIDAY),
    SABADO(DayOfWeek.SATURDAY),
    DOMINGO(DayOfWeek.SUNDAY);

    private final DayOfWeek dayOfWeek;

    DiasSemana(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public static DiasSemana fromDayOfWeek(DayOfWeek dayOfWeek) {
        for (DiasSemana dia : DiasSemana.values()) {
            if (dia.dayOfWeek == dayOfWeek) {
                return dia;
            }
        }
        throw new IllegalArgumentException("Dia da semana não encontrado: " + dayOfWeek);
    }

}
