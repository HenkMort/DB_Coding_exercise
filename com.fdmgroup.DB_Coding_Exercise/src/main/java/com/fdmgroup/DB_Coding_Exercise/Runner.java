package com.fdmgroup.DB_Coding_Exercise;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import com.fdmgroup.DB_Coding_Exercise.model.MarketInfo;
import com.fdmgroup.DB_Coding_Exercise.model.Trade;

import fileIO.CSV_Reader;

public class Runner {

	public static void main(String[] args) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		CSV_Reader reader = new CSV_Reader(".\\src\\main\\resources\\DB_task.csv", format);
		List<Trade> tradeList = reader.readListOfTrades();

		HashMap<String, Double> indexCalculation = new HashMap<>();
		indexCalculation.put("ABC", 0.1);
		indexCalculation.put("NGL", 0.4);
		indexCalculation.put("MEGA", 0.3);
		indexCalculation.put("TRX", 0.2);

		MarketInfo marketInfo = new MarketInfo(tradeList, indexCalculation);
		marketInfo.createCompleteMarketInfo();
		marketInfo.printAllMarketInfo();

	}

}
