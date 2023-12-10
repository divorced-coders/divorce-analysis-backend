package com.nighthawk.spring_portfolio.mvc.stocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.spring_portfolio.mvc.stocks.sorting.Ascending;
import com.nighthawk.spring_portfolio.mvc.stocks.sorting.Descending;
import com.nighthawk.spring_portfolio.mvc.stocks.sorting.MonthlyStocksSorter;
import com.nighthawk.spring_portfolio.mvc.stocks.sorting.SortedData;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/monthly-stocks") // all requests in file begin with this URI
public class MonthlyStocksApiController {

    // Autowired enables Control to connect URI request and POJO Object to easily
    // for Database CRUD operations
    @Autowired
    private MonthlyStocksJpaRepository repository;

    @GetMapping("/chronological")
    public ResponseEntity<List<MonthlyStocks>> getMonthlyStocks() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/high-to-low")
    public ResponseEntity<SortedData> getHighToLowMonthlyStocks() {
        MonthlyStocksSorter descending = new Descending();

        return new ResponseEntity<>(descending.selectionSort(repository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/low-to-high")
    public ResponseEntity<String> getLowToHighMonthlyStocks() {
        MonthlyStocksSorter ascending = new Ascending();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Convert SortedData to JSON
            String json = objectMapper.writeValueAsString(ascending.bubbleSort(repository.findAll()));
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            // Handle exception if serialization fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
