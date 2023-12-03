package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.nighthawk.spring_portfolio.mvc.stocks.DailyStocksJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;


import java.net.http.HttpRequest;


@Component
@Configuration // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {  
    @Autowired DailyStocksJpaRepository dailyrepo;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=compact&datatype=json"))
            .header("X-RapidAPI-Key", "a96f7bb54emshee5a698b2344228p12bd6cjsnbb7e0177bdb6")
            .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        };

    }
}

