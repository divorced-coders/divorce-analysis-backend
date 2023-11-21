package com.nighthawk.mvc.fibonacci;

import java.util.HashMap;
import java.util.Map;

public class FibonacciSequenceCalculator {
    
    // Stores state throughout all calls
    private static Map<Integer, Integer> memo = new HashMap<>();

    // Ideally O(n) Time & O(n) Space
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = fibonacci(n - 1) + fibonacci(n - 2);
        memo.put(n, result);

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
