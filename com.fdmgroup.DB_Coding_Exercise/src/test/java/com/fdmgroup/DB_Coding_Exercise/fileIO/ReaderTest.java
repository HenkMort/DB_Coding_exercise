package com.fdmgroup.DB_Coding_Exercise.fileIO;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fdmgroup.DB_Coding_Exercise.model.MarketInfo;
import com.fdmgroup.DB_Coding_Exercise.model.Trade;

import fileIO.CSV_Reader;

public class ReaderTest {
	
	@Test
	public void test_Reader_FileRead_readsFileWithCorrectPathPassed() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		BufferedReader result= reader.readFile();
		assertTrue(result!=null);
	}
	
	@Test
	public void test_Reader_FileRead_readsFileWithInCorrectPathPassed() {
		CSV_Reader reader = new CSV_Reader(".\\fake.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		BufferedReader result= reader.readFile();
		assertTrue(result==null);
	}
	
	@Test
	public void test_Reader_readOutTradeFromLine_radsOutLineAndCreatesTradeClassObject() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		String line = "02.06.2023 16:42;MEGA;3179,8;327";
		Trade result= reader.readOutTrade(line);
		assertEquals(result.getTicker(), "MEGA");
	}
	
	@Test
	public void test_Reader_readAllTrades_returnsListWithAllTradesFromFile() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		List<Trade> resultList = reader.readListOfTrades();
		assertEquals(resultList.size(), 173);
	}
	@Test
	public void test_MarketInfo_getAllTradingDays_returnsListWithAllDaysOnWhichTradesWhereMade() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		List<Trade> tradeList = reader.readListOfTrades();
		MarketInfo info= new MarketInfo(tradeList);
		assertEquals(2, info.getAllTradingDays().size());
	}
	
	@Test
	public void test_getOpenPrice_returnsPriceOfEarliestTradeFromListOfTradesPassedIn() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		List<Trade> tradeList = reader.readListOfTrades();
		MarketInfo info= new MarketInfo();
		assertEquals(3997.9,info.getOpeningPrice(tradeList));
	}
	
	@Test
	public void test_getOpenPrice_returnsNAIfEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		MarketInfo info= new MarketInfo();
		assertEquals("N/A",info.getOpeningPrice(tradeList));
	}
	
	@Test
	public void test_getClosePrice_returnsPriceOfLastTradeFromListOfTradesPassedIn() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		List<Trade> tradeList = reader.readListOfTrades();
		MarketInfo info= new MarketInfo();
		assertEquals(999.88,info.getClosePrice(tradeList));
	}
	
	@Test
	public void test_getClosePrice_returnsNAIfEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		MarketInfo info= new MarketInfo();
		assertEquals("N/A",info.getClosePrice(tradeList));
	}
	
	@Test
	public void test_getHighestPrice_returnsHighestPriceOfAnyTradesFromListOfTradesPassedIn() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		List<Trade> tradeList = reader.readListOfTrades();
		MarketInfo info= new MarketInfo();
		assertEquals(5128.35,info.getHighestPrice(tradeList));
	}
	
	@Test
	public void test_getHighestPrice_returnsNAIfEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		MarketInfo info= new MarketInfo();
		assertEquals("N/A",info.getLowestPrice(tradeList));
	}
	
	@Test
	public void test_getLowestPrice_returnsLowestPriceOfAnyTradesFromListOfTradesPassedIn() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		List<Trade> tradeList = reader.readListOfTrades();
		MarketInfo info= new MarketInfo();
		assertEquals(999.85,info.getLowestPrice(tradeList));
	}
	
	@Test
	public void test_getLowestPrice_returnsNAIfEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		MarketInfo info= new MarketInfo();
		assertEquals("N/A",info.getLowestPrice(tradeList));
	}
	
	@Test
	public void test_getDailyTradedVolume_returnsSumOfVolumesOfAllTradesInListPassedIn() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		List<Trade> tradeList = reader.readListOfTrades();
		MarketInfo info= new MarketInfo();
		assertEquals(3.8485750820000005E8, info.getDailyVolume(tradeList));
	}
	
	public void test_createDailyMarketInfo_returnsCorrectMarketInfoObject_whenListOfTradesPassedIn() {
		
	}
	
}
