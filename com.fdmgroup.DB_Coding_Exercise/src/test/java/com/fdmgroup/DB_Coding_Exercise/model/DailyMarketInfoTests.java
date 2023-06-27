package com.fdmgroup.DB_Coding_Exercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DailyMarketInfoTests {
	
	@Test
	public void test_customToString_printsExpectedString() {

		DailyMarketInfo testInfo = new DailyMarketInfo("Ticker", "Day", 1.0, 2.0, 3.0, 4.0, 5.0);

		testInfo.customToString();
		String expectedResult = "DailyMarketInfo \n"
				+ "Ticker: Ticker\n"
				+ "Date: Day\n"
				+ "___________________________\n"
				+ "openPrice: 1.0\n"
				+ "closePrice: 2.0\n"
				+ "lowestPrice: 3.0\n"
				+ "highestPrice: 4.0\n"
				+ "tradedVolume: 5.0\n";
		
		assertEquals(expectedResult, testInfo.customToString());
	}
	
}
