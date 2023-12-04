package com.nighthawk.spring_portfolio.mvc.fibonacci;

public class FibonacciViaRecursion extends Fibo {

  @Override
  public int fibonacci(int n) {
    if (n <= 1) {
      return n;
    } else {
      return fibonacci(n - 1) + fibonacci(n - 2);
    }
  }

  public static void main(String[] args) {
    Fibo fibo = new FibonacciViaMemoization();
    int n = 4;
    int result = fibo.fibonacci(n);
    System.out.println(result);
  }

}
