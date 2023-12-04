package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.fibonacci.Fibo;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FiboRepository;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FiboRetracementLevel;
import com.nighthawk.spring_portfolio.mvc.fibonacci.FibonacciViaMemoization;

import java.util.List;

@Component
@Configuration // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {  
    @Autowired FiboRepository jokesRepo;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {

            // Joke database is populated with starting jokes

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

