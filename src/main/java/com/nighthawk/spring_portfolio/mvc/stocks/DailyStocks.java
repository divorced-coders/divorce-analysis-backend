package com.nighthawk.spring_portfolio.mvc.stocks;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.Date;

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
    private String high;

    @Nonnull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    @NonNull
    private String symbol;

    @ManyToOne
    MonthlyStocks monthlyStock;
    

    public DailyStocks(String symbol, String high, Date date){
        this.symbol = symbol;
        this.high = high;
        this.date = date;
    }
    
}
