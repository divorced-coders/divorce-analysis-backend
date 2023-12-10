package com.nighthawk.spring_portfolio.mvc.stocks.sorting;

import java.util.List;
import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;

public class Descending extends MonthlyStocksSorter {

  @Override
  public SortedData selectionSort(List<MonthlyStocks> monthlyStocksList) {
    int n = monthlyStocksList.size();

    super.sw.start();
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
    super.sw.stop();

    return new SortedData(super.comparisons, super.sw.getTotalTimeMillis(), monthlyStocksList);
  }

}
