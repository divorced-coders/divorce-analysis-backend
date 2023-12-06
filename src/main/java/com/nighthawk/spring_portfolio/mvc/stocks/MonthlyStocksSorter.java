package com.nighthawk.spring_portfolio.mvc.stocks;

import java.util.List;

public class MonthlyStocksSorter {
    public static List<MonthlyStocks> bubbleSortByHighAscending(List<MonthlyStocks> monthlyStocksList) {
        int n = monthlyStocksList.size();
        boolean swapped;

        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (compareStrings(monthlyStocksList.get(i - 1).getHigh(), monthlyStocksList.get(i).getHigh()) > 0) {
                    // Swap elements
                    MonthlyStocks temp = monthlyStocksList.get(i - 1);
                    monthlyStocksList.set(i - 1, monthlyStocksList.get(i));
                    monthlyStocksList.set(i, temp);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);

        return monthlyStocksList;
    }

    public static List<MonthlyStocks> selectionSortByHighDescending(List<MonthlyStocks> monthlyStocksList) {
        int n = monthlyStocksList.size();

        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (compareStrings(monthlyStocksList.get(j).getHigh(), monthlyStocksList.get(maxIndex).getHigh()) > 0) {
                    maxIndex = j;
                }
            }

            // Swap elements
            MonthlyStocks temp = monthlyStocksList.get(maxIndex);
            monthlyStocksList.set(maxIndex, monthlyStocksList.get(i));
            monthlyStocksList.set(i, temp);
        }

        return monthlyStocksList;
    }

    private static int compareStrings(String str1, String str2) {
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
