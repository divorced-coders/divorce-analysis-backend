package com.nighthawk.spring_portfolio.mvc;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.spring_portfolio.mvc.stocks.DailyStocks;
import com.nighthawk.spring_portfolio.mvc.stocks.DailyStocksJpaRepository;
import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;
import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocksJPARepository;
import com.nighthawk.spring_portfolio.mvc.fibonacci.Fibo;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FiboRepository;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FiboRetracementLevel;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FibonacciViaMemoization;

@Component
@Configuration // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {
    @Autowired
    DailyStocksJpaRepository dailyRepo;
    @Autowired
    MonthlyStocksJPARepository monthlyRepo;
    @Autowired
    FiboRepository fiboRepo;

    @Bean
    CommandLineRunner run() { // The run() method will be executed after the application starts
        return args -> {

            ArrayList<MonthlyStocks> monthlyStocks = new ArrayList<MonthlyStocks>();
            ArrayList<DailyStocks> dailyStocks = new ArrayList<DailyStocks>();

            // monthly request
            HttpRequest m_request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_MONTHLY&symbol=MSFT&outputsize=compact&datatype=json"))
                    .header("X-RapidAPI-Key", "a96f7bb54emshee5a698b2344228p12bd6cjsnbb7e0177bdb6")
                    .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> m_repsonse = HttpClient.newHttpClient().send(m_request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(m_repsonse.body());
            String m_data = m_repsonse.body();

            ObjectMapper m_objectMapper = new ObjectMapper();
            Map<String, Object> jsonData = m_objectMapper.readValue(m_data, new TypeReference<Map<String, Object>>() {
            });
            Map<String, Object> m_metaData = (Map<String, Object>) jsonData.get("Meta Data");
            String m_symbol = m_metaData != null ? (String) m_metaData.get("2. Symbol") : "N/A";
            Map<String, Map<String, String>> m_timeSeries = (Map<String, Map<String, String>>) jsonData
                    .get("Monthly Time Series");
            SimpleDateFormat m_dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (m_timeSeries != null) {
                // Iterate over each date in the time series
                for (Map.Entry<String, Map<String, String>> entry : m_timeSeries.entrySet()) {
                    String m_dateString = entry.getKey();
                    Map<String, String> m_monthData = entry.getValue();
                    Date m_date = m_dateFormat.parse(m_dateString);

                    System.out.println("Date: " + m_dateString);
                    String high = m_monthData.get("2. high");
                    List<MonthlyStocks> monthFound = monthlyRepo.findBySymbolAndDate(m_symbol, m_date);

                    if (monthFound.size() == 0) {
                        MonthlyStocks monthly = new MonthlyStocks(high, m_symbol, m_date);
                        monthlyStocks.add(monthly);
                    } else {
                        System.out.println("No time series data found");
                    }

                }
            } else {
                System.out.println("No time series data found");
            }
            
            // daily stocks
            HttpRequest d_request = HttpRequest.newBuilder()
                .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=compact&datatype=json"))
                .header("X-RapidAPI-Key", "a96f7bb54emshee5a698b2344228p12bd6cjsnbb7e0177bdb6")
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
            HttpResponse<String> d_response = HttpClient.newHttpClient().send(d_request, HttpResponse.BodyHandlers.ofString());
            System.out.println(d_response.body());
            String d_data = d_response.body();
            
            ObjectMapper d_objectMapper = new ObjectMapper();
            Map<String, Object> d_jsonData = d_objectMapper.readValue(d_data, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> d_metaData = (Map<String, Object>) d_jsonData.get("Meta Data");
            String symbol = d_metaData != null ? (String) d_metaData.get("2. Symbol") : "N/A";
            Map<String, Map<String, String>> timeSeries = (Map<String, Map<String, String>>) d_jsonData.get("Time Series (Daily)");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (timeSeries != null) {
                        // Iterate over each date in the time series
                        for (Map.Entry<String, Map<String, String>> entry : timeSeries.entrySet()) {
                            String dateString = entry.getKey();
                            Map<String, String> dayData = entry.getValue();
                            Date date = dateFormat.parse(dateString);
                            
                            // Now you can access the date and dayData information
                            System.out.println("Date: " + dateString);
                            System.out.println("High: " + dayData.get("2. high"));
                            String high = dayData.get("2. high");
                            List<DailyStocks> dayfound = dailyRepo.findBySymbolAndDate(symbol, date);

                            if(dayfound.size() == 0){
                                DailyStocks daily = new DailyStocks(symbol, high, date);
                                

                                for (MonthlyStocks month : monthlyStocks) {

                                    if (date.getMonth() == monthly.getDate().getMonth()) {
                                        
                                    }

                                }
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
