package test.kafkaMQ.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RangeTimeUtil{
	public static void main(String[] args) {
		String date = null;
		date = randomDate("2012-12-11 09:09:32", "2013-12-22 08:08:09");
		System.out.println(date);
	}
	
	public static String randomDate(String beginDate,String  endDate ) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date start = null;
		try {
			start = format.parse(beginDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date end = null;
		try {
			end = format.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}  

		if (start.getTime() >= end.getTime()) {  
			return null;  
		}  

		long date = random(start.getTime(), end.getTime());  
		return format.format(new Date(date));  
	}  

	private static long random(long begin, long end) {  
		long rtn = begin + (long)(Math.random() * (end - begin));  

		if(rtn == begin || rtn == end) {  
			return random(begin,end);
		}  

		return rtn;  
	}
}