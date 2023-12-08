package com.nighthawk.spring_portfolio.mvc.stocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/monthly-stocks")  // all requests in file begin with this URI
public class MonthlyStocksApiController {

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private MonthlyStocksJpaRepository repository;

    @GetMapping("/chronological")
    public ResponseEntity<List<MonthlyStocks>> getMonthlyStocks() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/high-to-low")
    public ResponseEntity<List<MonthlyStocks>> getHighToLowMonthlyStocks() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        List<MonthlyStocks> sortedMonthlyStocks = MonthlyStocksSorter.selectionSortByHighDescending(repository.findAll());

        return new ResponseEntity<>( sortedMonthlyStocks, HttpStatus.OK);
    }

    @GetMapping("/low-to-high")
    public ResponseEntity<List<MonthlyStocks>> getLowToHighMonthlyStocks() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        List<MonthlyStocks> sortedMonthlyStocks = MonthlyStocksSorter.bubbleSortByHighAscending(repository.findAll());

        return new ResponseEntity<>( sortedMonthlyStocks, HttpStatus.OK);
    }
}
