package com.nighthawk.spring_portfolio.mvc.stocks.sorting;

import java.util.List;

import org.springframework.util.StopWatch;

import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;

public class Descending extends MonthlyStocksSorter {

  StopWatch sw = new StopWatch();
  int comparisons;

  @Override
  public SortedData selectionSort(List<MonthlyStocks> monthlyStocksList) {
    int n = monthlyStocksList.size();

    sw.start();
    for (int i = 0; i < n - 1; i++) {
      int maxIndex = i;

      for (int j = i + 1; j < n; j++) {
        if (compareStrings(monthlyStocksList.get(j).getHigh(), monthlyStocksList.get(maxIndex).getHigh()) > 0) {
          maxIndex = j;
        }
        comparisons++;
      }

      // Swap elements
      MonthlyStocks temp = monthlyStocksList.get(maxIndex);
      monthlyStocksList.set(maxIndex, monthlyStocksList.get(i));
      monthlyStocksList.set(i, temp);
    }
    sw.stop();

    return new SortedData(comparisons, sw.getTotalTimeMillis(), monthlyStocksList);
  }

}
