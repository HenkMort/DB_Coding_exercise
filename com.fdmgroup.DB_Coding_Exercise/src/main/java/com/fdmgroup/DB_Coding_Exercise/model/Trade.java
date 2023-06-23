package com.fdmgroup.DB_Coding_Exercise.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Trade{
	private static AtomicInteger uniqueId = new AtomicInteger();
	private Integer id;
	private Date time_of_trade;
	
	private String ticker;
	
	private Double price;
	
	private int volume;

	public Date getTime_of_trade() {
		return time_of_trade;
	}

	public void setTime_of_trade(Date time_of_trade) {
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

	public Trade(Date time_of_trade, String ticker, Double price, int volume) {
		super();
		id = uniqueId.incrementAndGet();
		this.time_of_trade = time_of_trade;
		this.ticker = ticker;
		this.price = price;
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "Trade [time_of_trade=" + time_of_trade + ", ticker=" + ticker + ", price=" + price + ", volume=" + volume
				+ "]";
	}

}
