package com.fdmgroup.DB_Coding_Exercise.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import comparators.TradeDateComparator;
import comparators.TradePriceComparator;

/**
 * Creates new Instances of the DailyMarketInfo Class.
 * Determines the market Info, i.e. open Price, highest Price, etc.
 * 
 * @param String day: Trading day for which the DailyMarketInfo will be created
 * @param String Ticker: Ticker for which the DailyMarketInfo will be created
 * @param List<Trade> dailyTradeList: List of all daily trades with the
 *  		corresponding ticker. Can be null for ticker INDEX.
 * 
 * @author Morten Henken
 * 
 */

public class DailyMarketInfoController {
	
	private final String day;
	private final String ticker;
	private final List<Trade> dailyTradeList;

	public DailyMarketInfoController(String day, String ticker, List<Trade> dailyTradeList) {
		super();
		this.day = day;
		this.ticker = ticker;
		this.dailyTradeList = dailyTradeList;
	}

	public DailyMarketInfo createDailyMarketInfo() {
		Double openPrice = getOpenPrice();
		Double closePrice = getClosePrice();
		Double lowestPrice = getLowestPrice();
		Double highestPrice = getHighestPrice();
		Double dailyTradeVolume = getDailyVolume();

		return new DailyMarketInfo(ticker, day, openPrice, closePrice, lowestPrice, highestPrice, dailyTradeVolume);
	}

	public DailyMarketInfo createDailyIndexMarketInfo(List<DailyMarketInfo> dailies,
			HashMap<String, Double> indexCalculation, TreeSet<String> traidingDays) {
		
		List<DailyMarketInfo> dailyMarketInfo = dailies.stream().filter(daily -> daily.getDate().equals(day))
				.collect(Collectors.toList());

		Double openPrice = 0.0;
		Double closePrice = 0.0;
		Double lowestPrice = 0.0;
		Double highestPrice = 0.0;
		Double dailyTradeVolume = 0.0;

		for (DailyMarketInfo daily : dailyMarketInfo) {
			if (daily.getOpenPrice() > 0) {
				openPrice += Double.valueOf(daily.getOpenPrice().toString()) * indexCalculation.get(daily.getTicker());
				closePrice += Double.valueOf(daily.getClosePrice().toString())
						* indexCalculation.get(daily.getTicker());
				lowestPrice += Double.valueOf(daily.getLowestPrice().toString())
						* indexCalculation.get(daily.getTicker());
				highestPrice += Double.valueOf(daily.getHighestPrice().toString())
						* indexCalculation.get(daily.getTicker());
				dailyTradeVolume += Double.valueOf(daily.getTradedVolume().toString());
			} else {
				Double latestPrice = getLatestPrice(daily.getTicker(), dailies, traidingDays);
				openPrice += latestPrice * indexCalculation.get(daily.getTicker());
				closePrice += latestPrice * indexCalculation.get(daily.getTicker());
				lowestPrice += latestPrice * indexCalculation.get(daily.getTicker());
				highestPrice += latestPrice * indexCalculation.get(daily.getTicker());
				dailyTradeVolume += 0.0;
			}
		}

		return new DailyMarketInfo(ticker, day, openPrice, closePrice, lowestPrice, highestPrice, dailyTradeVolume);
	}

	public Double getOpenPrice() {
		if (dailyTradeList.isEmpty()) {
			return -0.1;
		}
		int firstTradeIndex = dailyTradeList.indexOf(Collections.min(dailyTradeList, new TradeDateComparator()));
		return dailyTradeList.get(firstTradeIndex).getPrice();
	}

	public Double getClosePrice() {
		if (dailyTradeList.isEmpty()) {
			return -0.1;
		}
		int lastTradeIndex = dailyTradeList.indexOf(Collections.max(dailyTradeList, new TradeDateComparator()));
		return dailyTradeList.get(lastTradeIndex).getPrice();
	}

	public Double getHighestPrice() {
		if (dailyTradeList.isEmpty()) {
			return -0.1;
		}
		int highestPriceTradeIndex = dailyTradeList
				.indexOf(Collections.max(dailyTradeList, new TradePriceComparator()));
		return dailyTradeList.get(highestPriceTradeIndex).getPrice();
	}

	public Double getLowestPrice() {
		if (dailyTradeList.isEmpty()) {
			return -0.1;
		}
		int lowestPriceTradeIndex = dailyTradeList.indexOf(Collections.min(dailyTradeList, new TradePriceComparator()));
		return dailyTradeList.get(lowestPriceTradeIndex).getPrice();
	}

	public Double getDailyVolume() {
		Double sum = dailyTradeList.stream().map(trade -> trade.getPrice() * trade.getVolume()).reduce(0.0,
				(a, b) -> a + b);
		return sum;
	}
	
	/**
	 * Finds latest available price from the last trade with with a specific ticker
	 *  
	 * @param ticker
	 * @param dailies
	 * @param tradingDays
	 * @return
	 */
	
	public Double getLatestPrice(String ticker, List<DailyMarketInfo> dailies, TreeSet<String> tradingDays) {
		TreeSet<String> previousTradingDays = (TreeSet<String>) tradingDays.subSet(tradingDays.first(), day);
		Iterator<String> iterator = previousTradingDays.descendingIterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			Optional<DailyMarketInfo> previousDayOptional = dailies.stream()
					.filter(daily -> daily.getTicker().equals(ticker) && daily.getDate().equals(next)).findFirst();
			if (previousDayOptional.isPresent() && previousDayOptional.get().getClosePrice() > 0) {
				return previousDayOptional.get().getClosePrice();
			}
		}
		return null;
	}

}
