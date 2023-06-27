package com.fdmgroup.DB_Coding_Exercise.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import comparators.DayStringComparator;
import fileIO.CSV_Reader;

@ExtendWith(MockitoExtension.class)
public class DailyMarketInfoControllerTests {

	@Mock
	DailyMarketInfo mockDailyMarketInfo;
	@Mock
	DailyMarketInfo mockDailyMarketInfo2;
	@Mock
	DailyMarketInfo mockDailyMarketInfo3;
	@Mock
	DailyMarketInfo mockDailyMarketInfo4;
	
	CSV_Reader reader;
	DateTimeFormatter format;
	
	@BeforeEach
	public void setUp() {
		format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",format);
	}
	
	@Test
	public void test_getOpenPrice_returnsPriceOfEarliestTradeFromListOfTradesPassedIn() {
		
		List<Trade> tradeList = reader.readListOfTrades();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertEquals(3997.9, controller.getOpenPrice());
	}

	@Test
	public void test_getOpenPrice_returnsSmallerThan0fEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertTrue(controller.getOpenPrice() == -0.1);
	}

	@Test
	public void test_getClosePrice_returnsPriceOfLastTradeFromListOfTradesPassedIn() {
		List<Trade> tradeList = reader.readListOfTrades();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertEquals(999.88, controller.getClosePrice());
	}

	@Test
	public void test_getClosePrice_returnsNAIfEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertTrue(controller.getClosePrice() == -0.1);
	}

	@Test
	public void test_getHighestPrice_returnsHighestPriceOfAnyTradesFromListOfTradesPassedIn() {
		List<Trade> tradeList = reader.readListOfTrades();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertEquals(5128.35, controller.getHighestPrice());
	}

	@Test
	public void test_getHighestPrice_returnsNAIfEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertTrue(controller.getHighestPrice() == -0.1);
	}

	@Test
	public void test_getLowestPrice_returnsLowestPriceOfAnyTradesFromListOfTradesPassedIn() {
		List<Trade> tradeList = reader.readListOfTrades();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertEquals(999.85, controller.getLowestPrice());
	}

	@Test
	public void test_getLowestPrice_returnsNAIfEmptyListPassedIn() {
		List<Trade> tradeList = new ArrayList<Trade>();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertTrue(controller.getLowestPrice() == -0.1);
	}

	@Test
	public void test_getDailyTradedVolume_returnsSumOfVolumesOfAllTradesInListPassedIn() {
		List<Trade> tradeList = reader.readListOfTrades();
		DailyMarketInfoController controller = new DailyMarketInfoController(null, null, tradeList);
		assertEquals(3.8485750820000005E8, controller.getDailyVolume());
	}

	@Test
	public void test_getLatestPriceGetsLatestClosingPriceOfPreviousTradingDay() {
		List<Trade> tradeList = reader.readListOfTrades();
		TreeSet<String> traidingDays = new TreeSet<>(new DayStringComparator());
		traidingDays.add("01.06.2023");
		traidingDays.add("02.06.2023");
		traidingDays.add("03.06.2023");

		List<DailyMarketInfo> mockDailies = new ArrayList<DailyMarketInfo>();
		mockDailies.add(mockDailyMarketInfo);
		when(mockDailyMarketInfo.getDate()).thenReturn("02.06.2023");
		when(mockDailyMarketInfo.getTicker()).thenReturn("ABC");
		when(mockDailyMarketInfo.getClosePrice()).thenReturn(999.88);

		DailyMarketInfoController controller = new DailyMarketInfoController("03.06.2023", "ABC", tradeList);
		Double result = controller.getLatestPrice("ABC", mockDailies, traidingDays);
		assertEquals(999.88, result);
	}

	@Test
	public void test_getLatestPriceGetsLatestClosingPriceOfNextToPreviousTradingDayIfPriceIsSetToUnder0() {
		List<Trade> tradeList = reader.readListOfTrades();
		TreeSet<String> traidingDays = new TreeSet<>(new DayStringComparator());
		traidingDays.add("01.06.2023");
		traidingDays.add("02.06.2023");
		traidingDays.add("03.06.2023");

		List<DailyMarketInfo> mockDailies = new ArrayList<DailyMarketInfo>();
		mockDailies.add(mockDailyMarketInfo);
		mockDailies.add(mockDailyMarketInfo2);
		when(mockDailyMarketInfo.getDate()).thenReturn("02.06.2023");
		when(mockDailyMarketInfo.getTicker()).thenReturn("ABC");
		when(mockDailyMarketInfo.getClosePrice()).thenReturn(-0.01);
		when(mockDailyMarketInfo2.getDate()).thenReturn("01.06.2023");
		when(mockDailyMarketInfo2.getTicker()).thenReturn("ABC");
		when(mockDailyMarketInfo2.getClosePrice()).thenReturn(999.87);

		DailyMarketInfoController controller = new DailyMarketInfoController("03.06.2023", "ABC", tradeList);
		Double result = controller.getLatestPrice("ABC", mockDailies, traidingDays);
		assertEquals(999.87, result);
		verify(mockDailyMarketInfo).getClosePrice();
		verify(mockDailyMarketInfo2, times(2)).getClosePrice();
	}

	@Test
	public void test_getLatestPriceReturnsNullIfNoPricesExists() {
		List<Trade> tradeList = reader.readListOfTrades();
		TreeSet<String> traidingDays = new TreeSet<>(new DayStringComparator());
		traidingDays.add("01.06.2023");
		traidingDays.add("02.06.2023");
		traidingDays.add("03.06.2023");

		List<DailyMarketInfo> mockDailies = new ArrayList<DailyMarketInfo>();

		DailyMarketInfoController controller = new DailyMarketInfoController("03.06.2023", "ABC", tradeList);
		Double result = controller.getLatestPrice("ABC", mockDailies, traidingDays);
		assertEquals(null, result);

	}

	@Test
	public void test_createDailyMarketInfo_returnsCorrectDailyMarketInfoIfCorrectTradesTickerAndDayArePassedIn() {
		List<Trade> tradeList = reader.readListOfTrades();
		String day = "01.06.2023";
		String ticker = "ABC";
		List<Trade> dailyTradesList = tradeList.stream()
				.filter(trade -> trade.getDateOfTrade().equals(day)).collect(Collectors.toList());
		List<Trade> filteredTradeList = dailyTradesList.stream().filter(trade -> trade.getTicker().equals(ticker))
				.collect(Collectors.toList());
		DailyMarketInfoController controller = new DailyMarketInfoController(day, ticker, filteredTradeList);
		DailyMarketInfo result = controller.createDailyMarketInfo();
		assertEquals(999.86, result.getOpenPrice());
		assertEquals(999.87, result.getClosePrice());
		assertEquals(999.86, result.getLowestPrice());
		assertEquals(999.87, result.getHighestPrice());
		assertEquals(1259831.56, result.getTradedVolume());
		assertEquals("01.06.2023", result.getDate());
		assertEquals("ABC", result.getTicker());
	}

	@Test
	public void test_createDailyMarketInfo_returnsNegativePricesForDailyMarketInfoIfNoTradesArePassedIn() {
		String day = "01.06.2023";
		String ticker = "ABC";
		List<Trade> tradesList = new ArrayList<>();
		DailyMarketInfoController controller = new DailyMarketInfoController(day, ticker, tradesList);
		DailyMarketInfo result = controller.createDailyMarketInfo();
		assertEquals(-0.1, result.getOpenPrice());
		assertEquals(-0.1, result.getClosePrice());
		assertEquals(-0.1, result.getLowestPrice());
		assertEquals(-0.1, result.getHighestPrice());
		assertEquals(0, result.getTradedVolume());
		assertEquals("01.06.2023", result.getDate());
		assertEquals("ABC", result.getTicker());
	}

	@Test
	public void test_createDailyIndexMarketInfo_returnsCorrectIndexInfoIfMockDailliesArePassedIn() {
		String day = "01.06.2023";
		DailyMarketInfoController controller = new DailyMarketInfoController(day, "INDEX", null);
		List<DailyMarketInfo> mockDailies = new ArrayList<DailyMarketInfo>();
		TreeSet<String> tradingDays = new TreeSet<String>(new DayStringComparator());
		tradingDays.add(day);

		mockDailies.add(mockDailyMarketInfo);
		mockDailies.add(mockDailyMarketInfo2);
		mockDailies.add(mockDailyMarketInfo3);
		mockDailies.add(mockDailyMarketInfo4);

		when(mockDailyMarketInfo.getDate()).thenReturn(day);
		when(mockDailyMarketInfo.getTicker()).thenReturn("ABC");
		when(mockDailyMarketInfo.getOpenPrice()).thenReturn(999.86);
		when(mockDailyMarketInfo.getClosePrice()).thenReturn(999.87);
		when(mockDailyMarketInfo.getLowestPrice()).thenReturn(999.86);
		when(mockDailyMarketInfo.getHighestPrice()).thenReturn(999.87);
		when(mockDailyMarketInfo.getTradedVolume()).thenReturn(1259831.56);

		when(mockDailyMarketInfo2.getDate()).thenReturn(day);
		when(mockDailyMarketInfo2.getTicker()).thenReturn("MEGA");
		when(mockDailyMarketInfo2.getOpenPrice()).thenReturn(2997.95);
		when(mockDailyMarketInfo2.getClosePrice()).thenReturn(3011.26);
		when(mockDailyMarketInfo2.getLowestPrice()).thenReturn(2997.95);
		when(mockDailyMarketInfo2.getHighestPrice()).thenReturn(3014.99);
		when(mockDailyMarketInfo2.getTradedVolume()).thenReturn(2.453383862E7);

		when(mockDailyMarketInfo3.getDate()).thenReturn(day);
		when(mockDailyMarketInfo3.getTicker()).thenReturn("NGL");
		when(mockDailyMarketInfo3.getOpenPrice()).thenReturn(5000.83);
		when(mockDailyMarketInfo3.getClosePrice()).thenReturn(5117.97);
		when(mockDailyMarketInfo3.getLowestPrice()).thenReturn(4992.34);
		when(mockDailyMarketInfo3.getHighestPrice()).thenReturn(5128.35);
		when(mockDailyMarketInfo3.getTradedVolume()).thenReturn(2.092948459899999E8);

		when(mockDailyMarketInfo4.getDate()).thenReturn(day);
		when(mockDailyMarketInfo4.getTicker()).thenReturn("TRX");
		when(mockDailyMarketInfo4.getOpenPrice()).thenReturn(3997.9);
		when(mockDailyMarketInfo4.getClosePrice()).thenReturn(3884.68);
		when(mockDailyMarketInfo4.getLowestPrice()).thenReturn(3843.33);
		when(mockDailyMarketInfo4.getHighestPrice()).thenReturn(4014.95);
		when(mockDailyMarketInfo4.getTradedVolume()).thenReturn(1.1362003219000001E8);

		HashMap<String, Double> indexCalculation = new HashMap<>();
		indexCalculation.put("ABC", 0.1);
		indexCalculation.put("NGL", 0.4);
		indexCalculation.put("MEGA", 0.3);
		indexCalculation.put("TRX", 0.2);

		DailyMarketInfo result = controller.createDailyIndexMarketInfo(mockDailies, indexCalculation, tradingDays);

		assertEquals(0.1 * 999.86 + 0.3 * 2997.95 + 0.4 * 5000.83 + 0.2 * 3997.9, result.getOpenPrice());
		assertEquals(0.1 * 999.87 + 0.3 * 3011.26 + 0.4 * 5117.97 + 0.2 * 3884.68, result.getClosePrice());
		assertEquals(0.1 * 999.86 + 0.3 * 2997.95 + 0.4 * 4992.34 + 0.2 * 3843.33, result.getLowestPrice());
		assertEquals(0.1 * 999.87 + 0.3 * 3014.99 + 0.4 * 5128.35 + 0.2 * 4014.95, result.getHighestPrice());
		assertEquals(1259831.56 + 2.453383862E7 + 2.092948459899999E8 + 1.1362003219000001E8, result.getTradedVolume());

	}

	@Test
	public void test_createDailyIndexMarketInfo_returnsCorrectIndexInfoIfMockDailliesArePassedInAndGetLastestPriceIsCalled() {
		String day = "01.06.2023";
		DailyMarketInfoController controller = new DailyMarketInfoController(day, "INDEX", null);
		DailyMarketInfoController spy = Mockito.spy(controller);

		List<DailyMarketInfo> mockDailies = new ArrayList<DailyMarketInfo>();
		TreeSet<String> tradingDays = new TreeSet<String>(new DayStringComparator());
		tradingDays.add(day);

		mockDailies.add(mockDailyMarketInfo);
		mockDailies.add(mockDailyMarketInfo2);
		mockDailies.add(mockDailyMarketInfo3);
		mockDailies.add(mockDailyMarketInfo4);

		when(mockDailyMarketInfo.getDate()).thenReturn(day);
		when(mockDailyMarketInfo.getTicker()).thenReturn("ABC");
		when(mockDailyMarketInfo.getOpenPrice()).thenReturn(-0.1);

		when(mockDailyMarketInfo2.getDate()).thenReturn(day);
		when(mockDailyMarketInfo2.getTicker()).thenReturn("MEGA");
		when(mockDailyMarketInfo2.getOpenPrice()).thenReturn(2997.95);
		when(mockDailyMarketInfo2.getClosePrice()).thenReturn(3011.26);
		when(mockDailyMarketInfo2.getLowestPrice()).thenReturn(2997.95);
		when(mockDailyMarketInfo2.getHighestPrice()).thenReturn(3014.99);
		when(mockDailyMarketInfo2.getTradedVolume()).thenReturn(2.453383862E7);

		when(mockDailyMarketInfo3.getDate()).thenReturn(day);
		when(mockDailyMarketInfo3.getTicker()).thenReturn("NGL");
		when(mockDailyMarketInfo3.getOpenPrice()).thenReturn(5000.83);
		when(mockDailyMarketInfo3.getClosePrice()).thenReturn(5117.97);
		when(mockDailyMarketInfo3.getLowestPrice()).thenReturn(4992.34);
		when(mockDailyMarketInfo3.getHighestPrice()).thenReturn(5128.35);
		when(mockDailyMarketInfo3.getTradedVolume()).thenReturn(2.092948459899999E8);

		when(mockDailyMarketInfo4.getDate()).thenReturn(day);
		when(mockDailyMarketInfo4.getTicker()).thenReturn("TRX");
		when(mockDailyMarketInfo4.getOpenPrice()).thenReturn(3997.9);
		when(mockDailyMarketInfo4.getClosePrice()).thenReturn(3884.68);
		when(mockDailyMarketInfo4.getLowestPrice()).thenReturn(3843.33);
		when(mockDailyMarketInfo4.getHighestPrice()).thenReturn(4014.95);
		when(mockDailyMarketInfo4.getTradedVolume()).thenReturn(1.1362003219000001E8);

		doReturn(1000.0).when(spy).getLatestPrice("ABC", mockDailies, tradingDays);

		HashMap<String, Double> indexCalculation = new HashMap<>();
		indexCalculation.put("ABC", 0.1);
		indexCalculation.put("NGL", 0.4);
		indexCalculation.put("MEGA", 0.3);
		indexCalculation.put("TRX", 0.2);

		DailyMarketInfo result = spy.createDailyIndexMarketInfo(mockDailies, indexCalculation, tradingDays);

		assertEquals(0.1 * 1000.0 + 0.3 * 2997.95 + 0.4 * 5000.83 + 0.2 * 3997.9, result.getOpenPrice());
		assertEquals(0.1 * 1000.0 + 0.3 * 3011.26 + 0.4 * 5117.97 + 0.2 * 3884.68, result.getClosePrice());
		assertEquals(0.1 * 1000.0 + 0.3 * 2997.95 + 0.4 * 4992.34 + 0.2 * 3843.33, result.getLowestPrice());
		assertEquals(0.1 * 1000.0 + 0.3 * 3014.99 + 0.4 * 5128.35 + 0.2 * 4014.95, result.getHighestPrice());
		assertEquals(0.0 + 2.453383862E7 + 2.092948459899999E8 + 1.1362003219000001E8, result.getTradedVolume());

	}
}
