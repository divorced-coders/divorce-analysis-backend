package com.nighthawk.spring_portfolio.mvc;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.spring_portfolio.mvc.stocks.DailyStocks;
import com.nighthawk.spring_portfolio.mvc.stocks.DailyStocksJpaRepository;
import com.nighthawk.spring_portfolio.mvc.fibonacci.Fibo;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FiboRepository;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FiboRetracementLevel;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FibonacciViaMemoization;


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
        String data = response.body();
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonData = objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> metaData = (Map<String, Object>) jsonData.get("Meta Data");
        String symbol = metaData != null ? (String) metaData.get("2. Symbol") : "N/A";
        Map<String, Map<String, String>> timeSeries = (Map<String, Map<String, String>>) jsonData.get("Time Series (Daily)");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (timeSeries != null) {
                    // Iterate over each date in the time series
                    for (Map.Entry<String, Map<String, String>> entry : timeSeries.entrySet()) {
                        String dateString = entry.getKey();
                        Map<String, String> dayData = entry.getValue();
                        Date date = dateFormat.parse(dateString);
                        
                        // Now you can access the date and dayData information
                        System.out.println("Date: " + dateString);
                        System.out.println("Open: " + dayData.get("1. open"));
                        String open = dayData.get("1. open");
                        System.out.println("Close: " + dayData.get("4. close"));
                        String close = dayData.get("4. close");
                        System.out.println("High: " + dayData.get("2. high"));
                        String high = dayData.get("2. high");
                        System.out.println("Low: " + dayData.get("3. low"));
                        String low = dayData.get("3. low");
                        System.out.println("Volume: " + dayData.get("5. volume"));
                        String volume = dayData.get("5. volume");
                        System.out.println("---------------------");
                        List<DailyStocks> dayfound = dailyrepo.findBySymbolAndDate(symbol, date);

                        if(dayfound.size() == 0){
                            DailyStocks daily = new DailyStocks(open, close, high, low, volume, symbol, date);
                            dailyrepo.save(daily);
                        }
                        else {
                            System.out.println("found");
                        }
                    }
                } else {
                    System.out.println("No time series data found");
                }
        };

    }

    public static FiboRetracementLevel[] initRetracements(int n) {
        FiboRetracementLevel[] retracements = new FiboRetracementLevel[n + 1];
        Fibo fibo = new FibonacciViaMemoization();
        for (int i = 0; i <= n; i++) {
            FiboRetracementLevel level = new FiboRetracementLevel();
            if (i >= 2) {
                double numerator = fibo.fibonacci(i - 2);
                double denominator = fibo.fibonacci(i - 1);
                level.setValue(numerator / denominator);
            } else {
                // Handle the case where i < 2
                level.setValue(0); // You can set the value to another default value if needed
            }
            retracements[i] = level;
        }
        return retracements;
    }

}

