package com.comy_delivery_back.scheduler;

import com.comy_delivery_back.service.RestauranteService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RestauranteScheduler {

    private final RestauranteService restauranteService;

    public RestauranteScheduler(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    //executa a cada 5 min
    @Scheduled(cron = "0 0/55 * * * *")
    public void verificarHorariosRestaurantes() {
        System.out.println("SCHEDULER: Verificando e atualizando status de abertura dos restaurantes. (A cada 5 min)");

        restauranteService.atualizarStatusAberturaFechamento();
    }
}