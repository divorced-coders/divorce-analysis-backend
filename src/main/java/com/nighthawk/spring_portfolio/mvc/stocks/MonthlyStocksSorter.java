package com.nighthawk.spring_portfolio.mvc.stocks;

import java.util.List;

public class MonthlyStocksSorter {

    public static void bubbleSort(List<MonthlyStocks> monthlyStocksList) {
        int n = monthlyStocksList.size();
        boolean swapped;
        
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                // Compare 'high' values and swap if needed
                if (compare(monthlyStocksList.get(i - 1), monthlyStocksList.get(i)) > 0) {
                    swap(monthlyStocksList, i - 1, i);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    private static int compare(MonthlyStocks a, MonthlyStocks b) {
        // You can customize the comparison logic based on your needs
        // Here, we compare 'high' values as strings for simplicity
        return a.getHigh().compareTo(b.getHigh());
    }

    private static void swap(List<MonthlyStocks> list, int i, int j) {
        MonthlyStocks temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static void main(String[] args) {
        // Example usage
        List<MonthlyStocks> monthlyStocksList = /* Initialize your list here */; // data impending from api

        System.out.println("Before sorting:");
        for (MonthlyStocks monthlyStocks : monthlyStocksList) {
            System.out.println(monthlyStocks.getHigh());
        }

        bubbleSort(monthlyStocksList);

        System.out.println("\nAfter sorting:");
        for (MonthlyStocks monthlyStocks : monthlyStocksList) {
            System.out.println(monthlyStocks.getHigh());
        }
    }
}
