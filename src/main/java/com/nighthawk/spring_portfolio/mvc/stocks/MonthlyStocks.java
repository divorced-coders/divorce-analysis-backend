package com.nighthawk.spring_portfolio.mvc.stocks;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;


import com.mongodb.lang.NonNull;
import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;

import static jakarta.persistence.FetchType.EAGER;

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

public class MonthlyStocks {
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

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    @ManyToMany(fetch = EAGER)
    private Collection<DailyStocks> daily = new ArrayList<>();

    public MonthlyStocks(String open, String close, String high, String low, String volume, String symbol, Date date){
        // Initialize instance variables with provided values
        this.open = open;       // Opening stock price for the month
        this.close = close;     // Closing stock price for the month
        this.high = high;       // Highest stock price during the month
        this.low = low;         // Lowest stock price during the month
        this.volume = volume;   // Trading volume for the month
        this.symbol = symbol;   // Stock symbol or ticker
        this.date = date;       // Date corresponding to the monthly stock data
    }
    
}
