package com.nighthawk.spring_portfolio.mvc.stocks;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
import com.mongodb.lang.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;

import static jakarta.persistence.FetchType.EAGER;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*

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
    private String high;

    @NonNull
    private String symbol;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    @NonNull
    @OneToMany(fetch = EAGER)
    private Collection<DailyStocks> daily = new ArrayList<>();

    public void addDailyStock(DailyStocks dailyStock) {
        this.daily.add(dailyStock);
        dailyStock.setMonthlyStock(this);
    }

    public MonthlyStocks(String symbol, String high, Date date){
        this.symbol = symbol;
        this.high = high;
        this.date = date;
    }
    
}
