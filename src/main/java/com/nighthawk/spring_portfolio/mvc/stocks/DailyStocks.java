package com.nighthawk.spring_portfolio.mvc.stocks;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;


import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.mongodb.lang.NonNull;
import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyStocks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String open;

    @NonNull
    private String close;

    @NonNull
    private String high;

    @NonNull
    private String low;

    @NonNull
    private String volume;

    @NonNull
    private String symbol;

    public DailyStocks(String open, String close, String high, String low, String volume, String symbol){
        // Initialize instance variables with provided values
        this.open = open;       // Opening stock price for the day
        this.close = close;     // Closing stock price for the day
        this.high = high;       // Highest stock price during the day
        this.low = low;         // Lowest stock price during the day
        this.volume = volume;   // Trading volume for the day
        this.symbol = symbol;   // Stock symbol or ticker
    }
    
}
