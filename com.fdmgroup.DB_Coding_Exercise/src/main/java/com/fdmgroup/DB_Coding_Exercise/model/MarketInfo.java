package com.fdmgroup.DB_Coding_Exercise.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import comparators.DayStringComparator;
import fileIO.CSV_Writer;

public class MarketInfo {
	
	/**
	 * Stores all trades and market information.
	 * 
	 * 
	 * 
	 */

	private List<Trade> marketTrades;
	private List<DailyMarketInfo> dailies = new ArrayList<>();
	private HashMap<String, Double> indexCalculation;

	public MarketInfo(List<Trade> marketTrades, HashMap<String, Double> indexCalculation) {
		super();
		this.marketTrades = marketTrades;
		this.indexCalculation = indexCalculation;
	}

	public MarketInfo() {
		super();
	}

	public List<Trade> getMarketTrades() {
		return marketTrades;
	}

	public void setMarketTrades(List<Trade> marketTrades) {
		this.marketTrades = marketTrades;
	}

	public List<DailyMarketInfo> getDailies() {
		return dailies;
	}

	public void setDailies(List<DailyMarketInfo> dailies) {
		this.dailies = dailies;
	}

	public HashMap<String, Double> getIndexCalculation() {
		return indexCalculation;
	}

	public void setIndexCalculation(HashMap<String, Double> indexCalculation) {
		this.indexCalculation = indexCalculation;
	}

	/**
	 * Searches the trades for days, on which trades occurred
	 * 
	 * @return
	 */
	
	public TreeSet<String> getAllTradingDays() {
		TreeSet<String> tradingDates = new TreeSet<String>(new DayStringComparator());
		for (Trade trade : marketTrades) {
			tradingDates.add(trade.getDateOfTrade());
		}
		return tradingDates;
	}

	/**
	 * Searches the trades for tickers, that were traded
	 * 
	 * @return
	 */
	
	public SortedSet<String> getAllTickers() {
		SortedSet<String> tickers = new TreeSet<String>();
		for (Trade t : marketTrades) {
			tickers.add(t.getTicker());
		}
		return tickers;
	}

	public void createDailyMarketInfo(String day) {

		List<Trade> dailyTradesList = marketTrades.stream()
				.filter(trade -> trade.getDateOfTrade().equals(day)).collect(Collectors.toList());
		List<Trade> filteredTradesList;
		for (String ticker : getAllTickers()) {

			filteredTradesList = dailyTradesList.stream().filter(trade -> trade.getTicker().equals(ticker))
					.collect(Collectors.toList());
			DailyMarketInfoController controller = new DailyMarketInfoController(day, ticker, filteredTradesList);
			dailies.add(controller.createDailyMarketInfo());

		}

		DailyMarketInfoController controller = new DailyMarketInfoController(day, "INDEX", null);
		dailies.add(controller.createDailyIndexMarketInfo(dailies, indexCalculation, getAllTradingDays()));
	}

	public void createCompleteMarketInfo() {
		for (String day : getAllTradingDays()) {
			createDailyMarketInfo(day);
		}
	}

	public void printDailyMarketInfo(String day) {
		List<DailyMarketInfo> dailyMarketInfo = dailies.stream().filter(daily -> daily.getDate().equals(day))
				.collect(Collectors.toList());

		dailyMarketInfo.forEach(daily -> System.out.println(daily.customToString()));
	}

	public void printTickerMarketInfo(String ticker) {
		List<DailyMarketInfo> tickerMarketInfo = dailies.stream().filter(daily -> daily.getTicker().equals(ticker))
				.collect(Collectors.toList());

		tickerMarketInfo.forEach(daily -> System.out.println(daily.customToString()));
	}

	public void printSpecificMarketInfo(String ticker, String day) {
		List<DailyMarketInfo> tickerMarketInfo = dailies.stream().filter(daily -> daily.getTicker().equals(ticker))
				.collect(Collectors.toList());
		List<DailyMarketInfo> dailyMarketInfo = tickerMarketInfo.stream().filter(daily -> daily.getDate().equals(day))
				.collect(Collectors.toList());

		dailyMarketInfo.forEach(daily -> System.out.println(daily.customToString()));
	}
	
	public void printAllMarketInfo() {
		dailies.forEach(daily -> System.out.println(daily.customToString()));
	}
	
	public void writeToFile(String filePath) {
		CSV_Writer writer = new CSV_Writer(filePath, dailies);
		writer.writeInfoToCSVFile();
	}

}
