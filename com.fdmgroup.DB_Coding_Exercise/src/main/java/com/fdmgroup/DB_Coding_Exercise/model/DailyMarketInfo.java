package com.fdmgroup.DB_Coding_Exercise.model;

/**
 * Stores the market info for each day and each ticker
 * 
 * Includes the customToString, which is used to transfer the Market Info
 * into a readable format
 * 
 * @author Morten Henken
 */

public class DailyMarketInfo {
	
	private final String ticker;
	private final String date;

	private Double openPrice;
	private Double closePrice;
	private Double lowestPrice;
	private Double highestPrice;
	private Double tradedVolume;

	public DailyMarketInfo(String ticker, String date, Double openPrice, Double closePrice, Double lowestPrice,
			Double highestPrice, Double tradedVolume) {
		super();
		this.ticker = ticker;
		this.date = date;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.lowestPrice = lowestPrice;
		this.highestPrice = highestPrice;
		this.tradedVolume = tradedVolume;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closingPrice) {
		this.closePrice = closingPrice;
	}

	public Double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(Double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public Double getTradedVolume() {
		return tradedVolume;
	}

	public void setTradedVolume(Double tradedVolume) {
		this.tradedVolume = tradedVolume;
	}

	public String getTicker() {
		return ticker;
	}

	public String getDate() {
		return date;
	}

	public String customToString() {
		if(openPrice>0) {
		
		return "DailyMarketInfo \n" + "Ticker: " + ticker + "\n"
				+ "Date: " + date + "\n"
				+ "___________________________\n"
				+ "openPrice: " + openPrice + "\n"
				+ "closePrice: " + closePrice + "\n"
				+ "lowestPrice: " + lowestPrice + "\n"
				+ "highestPrice: " + highestPrice + "\n"
				+ "tradedVolume: " + tradedVolume + "\n";
		} else {
			return "DailyMarketInfo \n" + "Ticker: " + ticker + "\n"
					+ "Date: " + date + "\n"
					+ "___________________________\n"
					+ "openPrice: N/A\n"
					+ "closePrice: N/A\n"
					+ "lowestPrice: N/A\n"
					+ "highestPrice: N/A\n"
					+ "tradedVolume: 0\n";
		}
	}

}
