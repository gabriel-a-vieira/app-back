package com.softix.app_back.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint público e leve usado pelo monitor de uptime (UptimeRobot/cron-job.org)
 * para manter o serviço acordado no plano gratuito do Render. Não toca no banco
 * de propósito, para o ping ser barato e não depender do Postgres.
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
