package com.nighthawk.spring_portfolio.mvc.stocks.sorting;

import java.util.List;

import com.nighthawk.spring_portfolio.mvc.stocks.MonthlyStocks;

import lombok.Data;
import lombok.Getter;

@Data
public class SortedData {
  
  @Getter
  private int comparisons;
  @Getter
  private long timeTaken;
  @Getter
  private List<MonthlyStocks> monthlyStocks;

  public SortedData(int comparisons, long timeTaken, List<MonthlyStocks> monthlyStocks) {

    this.comparisons = comparisons;
    this.timeTaken = timeTaken;
    this.monthlyStocks = monthlyStocks;

  }

}
