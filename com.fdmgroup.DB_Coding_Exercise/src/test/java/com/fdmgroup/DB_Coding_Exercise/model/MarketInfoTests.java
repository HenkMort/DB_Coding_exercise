package com.fdmgroup.DB_Coding_Exercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fileIO.CSV_Reader;

@ExtendWith(MockitoExtension.class)
public class MarketInfoTests {

	@Mock
	DailyMarketInfo mockInfo1;
	@Mock
	DailyMarketInfo mockInfo2;
	@Mock
	DailyMarketInfo mockInfo3;
	
	CSV_Reader reader;
	DateTimeFormatter format;
	
	@BeforeEach
	public void setUp() {
		format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",format);
	}

	@Test
	public void test_MarketInfo_getAllTradingDays_returnsListWithAllDaysOnWhichTradesWhereMade() {
		List<Trade> tradeList = reader.readListOfTrades();
		HashMap<String, Double> indexCalculation = new HashMap<String, Double>();
		MarketInfo info = new MarketInfo(tradeList, indexCalculation);
		assertEquals(2, info.getAllTradingDays().size());
	}

	@Test
	public void test_getAllTickers_returnsSortedSetOfTickers() {
		List<Trade> tradeList = reader.readListOfTrades();
		MarketInfo info = new MarketInfo(tradeList, null);
		SortedSet<String> result = info.getAllTickers();
		assertEquals("ABC", result.first());
		assertEquals("TRX", result.last());
		assertEquals(4, result.size());
	}

	@Test
	public void test_createDailyMarketInfo_returnsCorrectMarketInfoObject_whenListOfTradesPassedIn() {
		List<Trade> tradeList = reader.readListOfTrades();
		HashMap<String, Double> indexCalculation = new HashMap<String, Double>();
		indexCalculation.put("ABC", 0.1);
		indexCalculation.put("NGL", 0.4);
		indexCalculation.put("MEGA", 0.3);
		indexCalculation.put("TRX", 0.2);
		MarketInfo info = new MarketInfo(tradeList, indexCalculation);
		String day = "01.06.2023";

		info.createDailyMarketInfo(day);
		DailyMarketInfo indexResult = info.getDailies().get(4);

		assertEquals(5, info.getDailies().size());
		assertEquals(0.1 * 999.86 + 0.3 * 2997.95 + 0.4 * 5000.83 + 0.2 * 3997.9, indexResult.getOpenPrice());
		assertEquals(0.1 * 999.87 + 0.3 * 3011.26 + 0.4 * 5117.97 + 0.2 * 3884.68, indexResult.getClosePrice());
		assertEquals(0.1 * 999.86 + 0.3 * 2997.95 + 0.4 * 4992.34 + 0.2 * 3843.33, indexResult.getLowestPrice());
		assertEquals(0.1 * 999.87 + 0.3 * 3014.99 + 0.4 * 5128.35 + 0.2 * 4014.95, indexResult.getHighestPrice());
		assertEquals(1259831.56 + 2.453383862E7 + 2.092948459899999E8 + 1.1362003219000001E8,
				indexResult.getTradedVolume());
	}

	@Test
	public void test_createCompleteMarketInfo_returnsDailyMarketInfoForAllIndicesAndDays() {
		List<Trade> tradeList = reader.readListOfTrades();
		HashMap<String, Double> indexCalculation = new HashMap<String, Double>();
		indexCalculation.put("ABC", 0.1);
		indexCalculation.put("NGL", 0.4);
		indexCalculation.put("MEGA", 0.3);
		indexCalculation.put("TRX", 0.2);
		MarketInfo info = new MarketInfo(tradeList, indexCalculation);
		info.createCompleteMarketInfo();
		List<DailyMarketInfo> result = info.getDailies();

		assertEquals(10, result.size());

	}

	@Test
	public void test_printDailyMarketInfo_returnsAllDailyMarketInfo() {

		MarketInfo info = new MarketInfo();
		String day = "01.06.2023";
		String day2 = "02.06.2023";
		List<DailyMarketInfo> testDailies = new ArrayList<>();
		testDailies.add(mockInfo1);
		testDailies.add(mockInfo2);

		when(mockInfo1.getDate()).thenReturn(day);
		when(mockInfo2.getDate()).thenReturn(day2);

		when(mockInfo1.customToString()).thenReturn("");

		info.setDailies(testDailies);
		info.printDailyMarketInfo(day);

		verify(mockInfo1).customToString();
		verify(mockInfo2, times(0)).customToString();
	}
	
	@Test
	public void test_printSpecificMarketInfo_returnsCorrectDailyMarketInfo() {

		MarketInfo info = new MarketInfo();
		String day = "01.06.2023";
		String day2 = "02.06.2023";
		String ticker ="ABC";
		String ticker2 ="NGL";
		
		List<DailyMarketInfo> testDailies = new ArrayList<>();
		testDailies.add(mockInfo1);
		testDailies.add(mockInfo2);
		testDailies.add(mockInfo3);
		
		when(mockInfo1.getTicker()).thenReturn(ticker);
		when(mockInfo2.getTicker()).thenReturn(ticker);
		when(mockInfo3.getTicker()).thenReturn(ticker2);
		
		when(mockInfo1.getDate()).thenReturn(day);
		when(mockInfo2.getDate()).thenReturn(day2);

		when(mockInfo1.customToString()).thenReturn("");

		info.setDailies(testDailies);
		info.printSpecificMarketInfo(ticker, day);

		verify(mockInfo1).customToString();
		verify(mockInfo2, times(0)).customToString();
	}

	@Test
	public void test_printTickerMarketInfo_returnsCorrectDailyMarketInfo() {

		MarketInfo info = new MarketInfo();
		String ticker = "ABC";
		String ticker2 = "NGL";
		List<DailyMarketInfo> testDailies = new ArrayList<>();
		testDailies.add(mockInfo1);
		testDailies.add(mockInfo2);

		when(mockInfo1.getTicker()).thenReturn(ticker);
		when(mockInfo2.getTicker()).thenReturn(ticker2);

		when(mockInfo1.customToString()).thenReturn("");

		info.setDailies(testDailies);
		info.printTickerMarketInfo(ticker);

		verify(mockInfo1).customToString();
		verify(mockInfo2, times(0)).customToString();
	}

	@Test
	public void test_getAllMarketInfo_returnsAllDailyMarketInfo() {

		InOrder inOrder = inOrder(mockInfo1, mockInfo2);

		MarketInfo info = new MarketInfo();

		List<DailyMarketInfo> testDailies = new ArrayList<>();
		testDailies.add(mockInfo1);
		testDailies.add(mockInfo2);

		when(mockInfo1.customToString()).thenReturn("");
		when(mockInfo2.customToString()).thenReturn("");

		info.setDailies(testDailies);
		info.printAllMarketInfo();

		inOrder.verify(mockInfo1).customToString();
		inOrder.verify(mockInfo2).customToString();
	}

}
