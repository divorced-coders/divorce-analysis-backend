package com.nighthawk.spring_portfolio.mvc.fibonacci;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@Entity
public class FibonacciRetracementLevel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private double value;

  public void initFibonacciRetracementLevels() {

  }
  public static void main(String[] args) {
    System.out.println(FibonacciSequenceCalculator.fibonacci(8));
  }
}
