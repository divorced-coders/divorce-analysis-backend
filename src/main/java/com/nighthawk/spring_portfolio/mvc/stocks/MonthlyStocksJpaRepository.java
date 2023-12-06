package com.nighthawk.spring_portfolio.mvc.stocks;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface MonthlyStocksJpaRepository extends JpaRepository<MonthlyStocks, Long> {
    // JPA has many built in methods, these few have been prototyped for this application
    List<MonthlyStocks> findAllByOrderBySymbolAsc();
    List<MonthlyStocks> findBySymbolIgnoreCase(String MonthlyStocks);
    List<MonthlyStocks> findBySymbolAndDate(String symbol, Date date);

}
