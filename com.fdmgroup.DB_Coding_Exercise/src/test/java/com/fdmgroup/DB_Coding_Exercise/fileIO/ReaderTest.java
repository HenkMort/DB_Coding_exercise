package com.fdmgroup.DB_Coding_Exercise.fileIO;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fdmgroup.DB_Coding_Exercise.model.Trade;

import fileIO.CSV_Reader;

public class ReaderTest {

	@Test
	public void test_Reader_FileRead_readsFileWithCorrectPathPassed() {
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv",null);
		BufferedReader result = reader.readFile();
		assertTrue(result != null);
	}

	@Test
	public void test_Reader_FileRead_doesNotReadsFileWithInCorrectPathPassed() {
		CSV_Reader reader = new CSV_Reader(".\\fake.csv",null);
		BufferedReader result = reader.readFile();
		assertTrue(result == null);
	}

	@Test
	public void test_Reader_readOutTradeFromLine_readsOutLineAndCreatesTradeClassObject() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv", format);
		String line = "02.06.2023 16:42;MEGA;3179,8;327";
		Trade result = reader.readOutTrade(line);
		assertEquals(result.getTicker(), "MEGA");
	}

	@Test
	public void test_Reader_readAllTrades_returnsListWithAllTradesFromFile() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		CSV_Reader reader = new CSV_Reader(".\\src\\test\\resources\\test.csv", format);
		List<Trade> resultList = reader.readListOfTrades();
		assertEquals(resultList.size(), 173);
	}

}
