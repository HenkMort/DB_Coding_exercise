package comparators;

import java.util.Comparator;

import com.fdmgroup.DB_Coding_Exercise.model.Trade;

public class TradeDateComparator implements Comparator<Trade> {

	public int compare(Trade t1, Trade t2) {
		return t1.getId().compareTo(t2.getId());
	}

}
