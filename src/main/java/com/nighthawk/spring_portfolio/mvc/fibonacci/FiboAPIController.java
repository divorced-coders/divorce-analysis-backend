package com.nighthawk.spring_portfolio.mvc.fibonacci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/fibonacci")  // all requests in file begin with this URI
public class FiboAPIController {

    @Autowired
    private FiboRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<FiboRetracementLevel>> getRetracements() {
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }
}
