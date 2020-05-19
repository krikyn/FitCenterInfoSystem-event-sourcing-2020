package com.kirill.vakhrushev.fitcenter.config;

import com.kirill.vakhrushev.fitcenter.core.ReportService;
import com.kirill.vakhrushev.fitcenter.model.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class Config {

    @Autowired
    private ReportService reportService;

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public EventHandler eventHandler() {
        final EventHandler handler = new EventHandler();
        handler.addListener(reportService);
        return handler;
    }
}
