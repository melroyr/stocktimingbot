package com.myco.stock.trader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "alphavantage.api.key")

public class MyStockTraderApplicationConfig {

    private String apiKey;

  
}