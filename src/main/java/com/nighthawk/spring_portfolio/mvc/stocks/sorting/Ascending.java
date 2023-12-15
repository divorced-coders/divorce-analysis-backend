package com.nighthawk.spring_portfolio.mvc.stocks.sorting;

import java.util.List;

import org.springframework.util.StopWatch;

import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;

public class Ascending extends MonthlyStocksSorter {

  StopWatch sw = new StopWatch();
  int comparisons;

  @Override
  public SortedData bubbleSort(List<MonthlyStocks> monthlyStocksList) {
    int n = monthlyStocksList.size();
    boolean swapped;

    sw.start();
    do {
        swapped = false;
        for (int i = 1; i < n; i++) {
            comparisons++;
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
    sw.stop();

    return new SortedData(comparisons, sw.getTotalTimeMillis(), monthlyStocksList);
  }

}
