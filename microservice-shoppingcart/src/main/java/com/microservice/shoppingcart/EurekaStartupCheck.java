package com.microservice.shoppingcart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class EurekaStartupCheck implements SmartLifecycle {

    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaUrl;

    private boolean running = false;

    @Override
    public void start() {
        try {
            URL url = new URL(eurekaUrl.replace("/eureka/", "/actuator/health")); // endpoint de salud
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.connect();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Eureka no está disponible. Finalizando app.");
            }
            running = true;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar con Eureka: " + e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
