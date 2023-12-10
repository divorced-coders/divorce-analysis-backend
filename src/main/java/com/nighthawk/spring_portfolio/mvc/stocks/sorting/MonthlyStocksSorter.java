package com.nighthawk.spring_portfolio.mvc.stocks.sorting;

import java.util.List;

import org.springframework.util.StopWatch;

import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;

public class MonthlyStocksSorter {

    StopWatch sw = new StopWatch();
    int comparisons;

    public SortedData bubbleSort(List<MonthlyStocks> monthlyStocksList) {
        return null;
    }

    public SortedData selectionSort(List<MonthlyStocks> monthlyStocksList) {
        return null;
    }

    public int compareStrings(String str1, String str2) {
        this.comparisons++;
        try {
            double value1 = Double.parseDouble(str1);
            double value2 = Double.parseDouble(str2);
            return Double.compare(value1, value2);
        } catch (NumberFormatException e) {
            // Handle parsing errors or decide how to compare non-numeric strings
            return str1.compareTo(str2);
        }
    }
}
