package com.nighthawk.mvc.stocks;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.mongodb.lang.NonNull;
import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.*;
import lombok.*;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
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
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.symbol = symbol;
    }
}