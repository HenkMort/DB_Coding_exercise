package com.fdmgroup.DB_Coding_Exercise.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stores information about a trade. 
 * 
 * 
 * 
 * @author Morten Henken
 *
 */

public class Trade {
	private static AtomicInteger uniqueId = new AtomicInteger();
	private Integer id;
	private LocalDateTime time_of_trade;

	private String ticker;

	private Double price;

	private int volume;

	public LocalDateTime getTime_of_trade() {
		return time_of_trade;
	}

	public void setTime_of_trade(LocalDateTime time_of_trade) {
		this.time_of_trade = time_of_trade;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	/**
	 * 
	 * Transfers LocalDateTime to Date String
	 * 
	 * @return Returns Date as "dd.MM.yyyy" String 
	 */
	
	public String getDateOfTrade() {
		String date = String.format("%02d", getTime_of_trade().getDayOfMonth())
				+ "." + String.format("%02d", getTime_of_trade().getMonthValue())
				+ "." + String.format("%02d", getTime_of_trade().getYear());
		return date; 
	}

	public Trade(LocalDateTime time_of_trade, String ticker, Double price, int volume) {
		super();
		id = uniqueId.incrementAndGet();
		this.time_of_trade = time_of_trade;
		this.ticker = ticker;
		this.price = price;
		this.volume = volume;
	}

}
