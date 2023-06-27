package comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DayStringComparator implements Comparator<String> {
	
	/**
	 * Compares dates of the SimpleDateFormat "dd.MM.yyyy",  
	 */

	@Override
	public int compare(String day1, String day2) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		try {
			return format.parse(day1).compareTo(format.parse(day2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day1.compareTo(day2);
	}

}
