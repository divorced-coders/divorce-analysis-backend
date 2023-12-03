package com.nighthawk.spring_portfolio.mvc.stocks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface DailyStocksJpaRepository extends JpaRepository<DailyStocks, Long> {
    // JPA has many built in methods, these few have been prototyped for this application
    void save(String DailyStocks);
    List<DailyStocks> findAllByOrderBySymbolAsc();
    List<DailyStocks> findBySymbolIgnoreCase(String DailyStocks);
}