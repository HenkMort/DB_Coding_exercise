package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.DB_Coding_Exercise.model.Trade;

/**
 * 
 * Reads InputFiles and returns a list of all trades
 * 
 * @param inputFileName inputFileName stores the Path of the input File
 * 
 * @author Morten 
 * 
 */

public class CSV_Reader {
	
	private final String inputFileName;
	private final DateTimeFormatter format;

	public CSV_Reader(String inputFileName, DateTimeFormatter format) {
		super();
		this.inputFileName = inputFileName;
		this.format = format;
	}

	public List<Trade> readListOfTrades() {
		List<Trade> tradeList = new ArrayList<Trade>();
		BufferedReader in = readFile();
		try {
			String line = in.readLine();
			while (line != null) {
				tradeList.add(readOutTrade(line));
				line = in.readLine();	
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return tradeList;

	}

	public BufferedReader readFile() {
		File inputFile = new File(inputFileName);
		FileReader reader;
		try {
			reader = new FileReader(inputFile);
			BufferedReader in = new BufferedReader(reader);
			return in;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public Trade readOutTrade(String line) {
		Trade trade;
		String[] tradeInfo = line.split(";");
		LocalDateTime tradeDate;
		tradeDate = LocalDateTime.parse(tradeInfo[0], format);
		String index = tradeInfo[1];
		Double price = Double.parseDouble(tradeInfo[2].replace(",", "."));
		int volume = Integer.parseInt(tradeInfo[3]);
		trade = new Trade(tradeDate, index, price, volume);
		return trade;
	}
}
