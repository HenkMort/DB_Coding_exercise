package fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.fdmgroup.DB_Coding_Exercise.model.DailyMarketInfo;


/**
 * Writes daily market info to file. 
 * 
 * Replaces "." with "," in all Doubles.
 * 
 * @author Morten
 *
 */

public class CSV_Writer {
	
	private final String filePath;
	private final List<DailyMarketInfo> info;
	
	public CSV_Writer(String filePath, List<DailyMarketInfo> info) {
		super();
		this.filePath = filePath;
		this.info = info;
	}




	public void writeInfoToCSVFile() {
		try {
			File file = new File(filePath);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			String header =
					"Ticker;"+
					"Date;"+
					"Open Price;"+
					"Close Price;"+
					"Lowest Price;"+
					"Highest Price;"+
					"Traded Volume;";
			bufferedWriter.write(header);
			for (DailyMarketInfo daily: info) {
				String writeInfo = 
						daily.getTicker()+";"+
						daily.getDate()+";"+
						daily.getOpenPrice().toString().replace(".", ",")+";"+
						daily.getClosePrice().toString().replace(".", ",")+";"+
						daily.getLowestPrice().toString().replace(".", ",")+";"+
						daily.getHighestPrice().toString().replace(".", ",")+";"+
						daily.getTradedVolume().toString().replace(".", ",")+";";
				bufferedWriter.newLine();
				bufferedWriter.write(writeInfo);
			}
			bufferedWriter.close();
		}
			catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	
	
	
	

}
