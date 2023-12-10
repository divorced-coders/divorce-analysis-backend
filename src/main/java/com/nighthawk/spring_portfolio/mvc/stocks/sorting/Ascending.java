package com.nighthawk.spring_portfolio.mvc.stocks.sorting;

import java.util.List;

import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;

public class Ascending extends MonthlyStocksSorter {

  @Override
  public SortedData bubbleSort(List<MonthlyStocks> monthlyStocksList) {
    int n = monthlyStocksList.size();
    boolean swapped;

    super.sw.start();
    do {
        swapped = false;
        for (int i = 1; i < n; i++) {
            if (super.compareStrings(monthlyStocksList.get(i - 1).getHigh(), monthlyStocksList.get(i).getHigh()) > 0) {
                // Swap elements
                MonthlyStocks temp = monthlyStocksList.get(i - 1);
                monthlyStocksList.set(i - 1, monthlyStocksList.get(i));
                monthlyStocksList.set(i, temp);
                swapped = true;
            }
        }
        n--;
    } while (swapped);
    super.sw.stop();

    return new SortedData(super.comparisons, super.sw.getTotalTimeMillis(), monthlyStocksList);
  }

}
