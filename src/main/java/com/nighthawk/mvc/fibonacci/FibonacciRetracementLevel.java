package com.nighthawk.mvc.fibonacci;

import javax.persistence.*;

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
