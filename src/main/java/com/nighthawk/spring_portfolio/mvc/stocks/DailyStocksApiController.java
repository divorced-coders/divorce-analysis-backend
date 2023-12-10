package com.nighthawk.spring_portfolio.mvc.stocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://0.0.0.0:4200"})
@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/DailyStocks")  // all requests in file begin with this URI
public class DailyStocksApiController {

    @Autowired
    private DailyStocksJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<DailyStocks>> getDailyStocks() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    // New method to retrieve DailyStocks based on monthly ID
    @GetMapping("/{monthlyId}")
    public ResponseEntity<List<DailyStocks>> getDailyStocksByMonthlyId(@PathVariable Long monthlyId) {
        // Retrieve DailyStocks based on the monthly ID
        List<DailyStocks> dailyStocks = repository.findByMonthlyStockId(monthlyId);

        // Check if there are any matching DailyStocks
        if (dailyStocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dailyStocks, HttpStatus.OK);
        }
    }

}
