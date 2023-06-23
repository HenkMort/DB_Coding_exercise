package com.fdmgroup.DB_Coding_Exercise.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import comparators.TradeDateComparator;
import comparators.TradePriceComparator;

public class MarketInfo {
	
	private List<Trade> marketTrades;
	private List<DailyMarketInfo> dailies;

	public MarketInfo(List<Trade> marketTrades) {
		super();
		this.marketTrades = marketTrades;
	}
	
	public MarketInfo() {
		super();
	}



	public SortedSet<String> getAllTradingDays(){
		SortedSet<String> tradingDates = new TreeSet<String>(); 
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		for (Trade t:marketTrades) {
			tradingDates.add(df.format(t.getTime_of_trade()));
		}
		
		return tradingDates;
	}
	
	public SortedSet<String> getAllTickers() {
		SortedSet<String> tickers = new TreeSet<String>();
		for (Trade t: marketTrades) {
			tickers.add(t.getTicker());
		}
		return tickers;
	}
	
	public void createDailyMarketInfo(String day) {
	
	List<Trade> dailyTradesList;
	List<Trade> filteredTradesList;
	for (String ticker:getAllTickers()) {
		filteredTradesList = marketTrades.stream()
				.filter(trade ->trade.getTicker().equals(ticker))
				.collect(Collectors.toList());
		
	}
	/*
	 * To-Do:
	 * earliest Trade->Price in a Day
	 * latest Trade-> Price in a Day
	 * lowest
	 * highest
	 * traded Volume
	 * index 
	 * */}

	public Object getOpeningPrice(List<Trade> tradeList) {
		if(tradeList.isEmpty()) {return "N/A";}
		int firstTradeIndex = tradeList.indexOf(Collections.min(tradeList, new TradeDateComparator()));
		return tradeList.get(firstTradeIndex).getPrice();
	}
	
	public Object getClosePrice(List<Trade> tradeList) {
		if(tradeList.isEmpty()) {return "N/A";}
		int lastTradeIndex = tradeList.indexOf(Collections.max(tradeList, new TradeDateComparator()));
		return tradeList.get(lastTradeIndex).getPrice();
	}

	public Object getHighestPrice(List<Trade> tradeList) {
		if(tradeList.isEmpty()) {return "N/A";}
		int highestPriceTradeIndex = tradeList.indexOf(Collections.max(tradeList, new TradePriceComparator()));
		return tradeList.get(highestPriceTradeIndex).getPrice();
	}
	
	public Object getLowestPrice(List<Trade> tradeList) {
		if(tradeList.isEmpty()) {return "N/A";}
		int lowestPriceTradeIndex = tradeList.indexOf(Collections.min(tradeList, new TradePriceComparator()));
		return tradeList.get(lowestPriceTradeIndex).getPrice();
	}

	public Double getDailyVolume(List<Trade> tradeList) {
		Double sum = tradeList.stream()
				.map(trade->trade.getPrice()*trade.getVolume())
				.reduce(0.0, (a,b) -> a+b);
		return sum;
	}
	
	
	
}
