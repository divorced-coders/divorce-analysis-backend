package com.nighthawk.spring_portfolio.mvc;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;
import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocksJpaRepository;
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
    MonthlyStocksJpaRepository monthlyRepo;
    @Autowired
    FiboRepository fiboRepo;

    @Bean
    CommandLineRunner run() { // The run() method will be executed after the application starts
        return args -> {

            // if (!monthlyRepo.findAll().equals(null)) {
            //   return;   
            // }

            ArrayList<MonthlyStocks> monthlyStocks = new ArrayList<MonthlyStocks>();

            // monthly request
            HttpRequest m_request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_MONTHLY&symbol=MSFT&outputsize=12&datatype=json"))
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

                    System.out.println("Monthly Date: " + m_dateString);
                    String m_high = m_monthData.get("2. high");
                    List<MonthlyStocks> monthFound = monthlyRepo.findBySymbolAndDate(m_symbol, m_date);

                    Calendar monthlyTime = Calendar.getInstance();
                    monthlyTime.setTime(m_date);

                    if (monthFound.size() == 0) {

                        if (monthlyTime.get(Calendar.YEAR) == 2023) {
                            MonthlyStocks monthly = new MonthlyStocks(m_symbol, m_high, m_date);
                            monthlyStocks.add(monthly);
                            monthlyRepo.save(monthly);
                        } else {
                            System.out.println("Filtered Year");
                        }

                    } else {
                        System.out.println("Item exists in DB");
                    }
                }
            } else {
                System.out.println("No MONTHLY time series data found");
            }

            // if (!dailyRepo.findAll().equals(null)) {
            //     return;   
            //   }

            // daily stocks
            HttpRequest d_request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=full&datatype=json"))
                    .header("X-RapidAPI-Key", "a96f7bb54emshee5a698b2344228p12bd6cjsnbb7e0177bdb6")
                    .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> d_response = HttpClient.newHttpClient().send(d_request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(d_response.body());
            String d_data = d_response.body();

            ObjectMapper d_objectMapper = new ObjectMapper();
            Map<String, Object> d_jsonData = d_objectMapper.readValue(d_data, new TypeReference<Map<String, Object>>() {
            });
            Map<String, Object> d_metaData = (Map<String, Object>) d_jsonData.get("Meta Data");
            String symbol = d_metaData != null ? (String) d_metaData.get("2. Symbol") : "N/A";
            Map<String, Map<String, String>> timeSeries = (Map<String, Map<String, String>>) d_jsonData
                    .get("Time Series (Daily)");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (timeSeries != null) {

                int daysToIterate = 365;
                int count = 0;

                // Iterate over each date in the time series
                for (Map.Entry<String, Map<String, String>> entry : timeSeries.entrySet()) {
                    if (count++ >= daysToIterate) {
                        break;
                    }
                    
                    String dateString = entry.getKey();
                    Map<String, String> dayData = entry.getValue();
                    Date date = dateFormat.parse(dateString);

                    // Now you can access the date and dayData information
                    System.out.println("Daily Date: " + dateString);
                    System.out.println("Daily High: " + dayData.get("2. high"));
                    String high = dayData.get("2. high");
                    List<DailyStocks> dayfound = dailyRepo.findBySymbolAndDate(symbol, date);

                    if (dayfound.size() == 0) {

                        Calendar dailyCalendar = Calendar.getInstance();
                        dailyCalendar.setTime(date);

                        if (dailyCalendar.get(Calendar.YEAR) == 2023) {
                            DailyStocks daily = new DailyStocks(symbol, high, date);

                            for (MonthlyStocks month : monthlyStocks) {

                                Calendar monthlyCalendar = Calendar.getInstance();
                                monthlyCalendar.setTime(month.getDate());

                                if (dailyCalendar.get(Calendar.MONTH) == monthlyCalendar.get(Calendar.MONTH)) {
                                    // Associate daily stock data with t his monthly data

                                    month.addDailyStock(daily);
                                    daily.setMonthlyStock(month);
                                    break; // Break the loop since we found the corresponding monthly data
                                }
                            }

                            dailyRepo.save(daily);
                        } else {
                            System.out.println("Filtered Year");
                        }
                    } else {
                        System.out.println("Item exists in DB");
                    }
                }
            } else {
                System.out.println("No DAILY time series data found");
            }

            // if (!fiboRepo.findAll().equals(null)) {
            //     return;   
            // }

            Fibo fibo = new FibonacciViaMemoization();
            double[] retracementLevels = {0.236, 0.382, 0.5, 0.618, 0.786};
            for (int i = 0; i < retracementLevels.length; i++) {
                FiboRetracementLevel level = new FiboRetracementLevel();
                if (i >= 2) {
                    double numerator = fibo.fibonacci(i - 2);
                    double denominator = fibo.fibonacci(i - 1);
                    // level.setValue(numerator / denominator);
                } else {
                    // Handle the case where i < 2
                    // level.setValue(0); // You can set the value to another default value if needed
                }
                level.setValue(retracementLevels[i]);
                fiboRepo.save(level);
            }
        };

    }
}
