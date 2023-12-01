package com.nighthawk.spring_portfolio.mvc.fibonacci;

import java.util.HashMap;
import java.util.Map;

public class FibonacciSequenceCalculator {
    
    // Stores state throughout all calls
    private static Map<Integer, Integer> memo = new HashMap<>();

    // Ideally O(n) Time & O(n) Space
    public static int fibonacci(int n) {
        // Base case: if n is 0 or 1, return n
        if (n <= 1) {
            return n;
        }
    
        // Check if the result for the current value of n is already memoized
        if (memo.containsKey(n)) {
            // Return the memoized result to avoid redundant calculations
            return memo.get(n);
        }
    
        // Recursive calculation for Fibonacci sequence
        int result = fibonacci(n - 1) + fibonacci(n - 2);
    
        // Memoize the result for the current value of n
        memo.put(n, result);
    
        // Return the calculated result
        return result;
    }
    

    // O(2^n) Time & O(n) Space
    public static int fibonacci(Integer n) {
        if (n <= 1) {
            return n;
        } else {
            System.out.println(n);
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void main(String[] args) {
        int n = 4;
        int result = fibonacci(n);
        System.out.println(result);
    }
}
