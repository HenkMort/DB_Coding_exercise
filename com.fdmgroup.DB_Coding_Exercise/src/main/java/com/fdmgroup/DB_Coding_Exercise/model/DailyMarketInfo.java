package com.fdmgroup.DB_Coding_Exercise.model;

public class DailyMarketInfo {
	
	private final String ticker;
	private final String date;
	
	private Double openPrice;
	private Double closingPrice;
	private Double lowestPrice;
	private Double highestPrice;
	private Double tradedVolume;
	
	public DailyMarketInfo(String ticker, String date, Double openPrice, Double closingPrice, Double lowestPrice,
			Double highestPrice, Double tradedVolume) {
		super();
		this.ticker = ticker;
		this.date = date;
		this.openPrice = openPrice;
		this.closingPrice = closingPrice;
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

	public Double getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(Double closingPrice) {
		this.closingPrice = closingPrice;
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
	
	
	
}
