package org.rrd4j.jl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Pattern;

import org.rrd4j.core.timespec.TimeSpec;
/**
 * DateCalc is responsible for all actions which work with date
 * it converts the date in different formats and determines the min and max of date, it also calculate timestamps for one day, week, month, year
 * @author j.launer
 *
 */

public class DateCalc {

	JmSave jm = new JmSave();
	Long[] longTimeStore=new Long[1];

	Vector<String> vectorStoreStart = new Vector<String>();
	Vector<Long> longVectorStoreStart = new Vector<Long>();

	/**
	 * convertToDate parses a long (linuxTimestamp) to a String (Date)
	 * @param long timeStamp
	 * @return String formatedDate
	 */
	protected String convertToDate(long timeStamp){
		Date unixTime = new Date(timeStamp*1000); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
		String formattedDate = sdf.format(unixTime);
		return formattedDate;
	}

	/**
	 * it convert a given timestamp into a date. manage the use of different time periods
	 * @param timeStamp
	 * @return String 
	 */
	protected String calculateDates(long timeStamp){
		Date unixTime = new Date(timeStamp*1000); // *1000 is to convert milliseconds to seconds
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
		String formattedDate = sdf.format(unixTime);
		return formattedDate;
	}

	/**
	 * calcOneDay() determines the span of time for one day with aid of endtime
	 * @param long timeStamp
	 * @return long startTime
	 */
	protected long calcOneDay(long timeStamp){
		long startMilliTimeStamp=timeStamp-86400;
		return startMilliTimeStamp;
	}

	/**
	 * calcOneWeek() determines the span of time for one week
	 * @param timeStamp
	 * @return long starTime
	 */
	protected long calcOneWeek(long timeStamp){
		long startMilliTimeStamp=timeStamp-604800;
		return startMilliTimeStamp;
	}
	/**
	 * calcOneMonth() determines the span of time for one Month
	 * @param timeStamp
	 * @return long startTime
	 */
	protected long calcOneMonth(long timeStamp){
		long startMilliTimeStamp=timeStamp-2592000;
		return startMilliTimeStamp;
	}
	/**
	 * calcOneYear() determines the span of time for one year
	 * @param timeStamp
	 * @return long startTime
	 */
	protected long calcOneYear(long timeStamp){
		long startMilliTimeStamp=timeStamp-31104000;
		return startMilliTimeStamp;
	}

	/**
	 * convertToTimestamp parses a String(in Date-Format) to a long (unixTimestamp)
	 * @param String formatedDate
	 * @return long timeStamp
	 * @throws ParseException
	 */
	protected long convertToTimestamp(String formatedDate) throws ParseException{
		DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm"); // the format of your date;
		//IMPORTANT: Set Timezone in convertToDate and in convertToTimestamp
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
		Date date=dateFormat.parse(formatedDate);
		long timeStamp=date.getTime();
		timeStamp=timeStamp/1000;
//		 because divide 1000;
		return timeStamp;
	}
	/**
	 * it fill different timestamps into a vector
	 * @param long timeStamp
	 */
	protected void fillVector(long timeStamp){
		// To fill vectorStartTime
//				String stringTimeStamp = "";
//				stringTimeStamp = String.valueOf(timeStamp);
//		vectorStoreStart.add(stringTimeStamp);
				longVectorStoreStart.add(timeStamp);

	}

//	protected void printStoreStartTime(){
//		longTimeStore = new Long[vectorStoreStart.size()];
//		//Variable for count
//		int i=0;
//	    for (Enumeration<String> element=vectorStoreStart.elements(); element.hasMoreElements(); ) {
//	    	String stringTimeStore=(String)element.nextElement();
//	    	longTimeStore[i]=Long.parseLong(stringTimeStore);
//	    	i++;
//	    	System.out.println(longTimeStore);
//	    		System.out.println(stringTimeStore);
//	    		System.out.println(vectorStoreStart.size());
//	    		System.out.println(i);
//	      }
//	    }

	/**
	 * print minimum value of the vector
	 * @param Vector <long> time
	 * @return long
	 */
	protected long findMinDate(Vector<Long> time)
	{
		Object obj = Collections.min(time);
		return (long) obj;
//		long ob= Long.parseLong((String) obj);
//		return (long) obj;
//		return ob;
	}

	/**
	 * print minimum value of the vector
	 * @param Vector <long> time
	 * @return
	 */
	protected String findMinDateString(Vector<Long> time)
	{
		Object obj = Collections.min(time);
		return (String) obj;
	}

	/**
	 * print maximum value of the vector
	 * @param time
	 * @return
	 */
	protected long findMaxDate(Vector<Long> time)
		{
			Object obj = Collections.max(time);
		    System.out.println(obj);
		    return (long) obj;
//			long ob= Long.parseLong((String) obj);
//			return (long) obj;
//			return ob;

		}

	/**
	 * check if string is a long-Value with length 9
	 * @param word
	 */
	public void checkRegToLong(String word){
		boolean bool=Pattern.matches( "\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d", word );
		if(bool==true)
		{
			System.out.println("Die eingegeben Zeichen sind vom Typ long");
		}
		else
			{
			System.out.println("Kein Long");
			}
//		return bool;
	}

//	public static void main(String[] args) throws Exception{
//		DateCalc date = new DateCalc();
//		long timestampLong=1396345500;
//	    Vector<Long> d = new Vector<Long>();
//	    d.add(111111111L);
//	    d.add(100000001L);
//	    d.add(100000000L);
//	    d.add(126499999L);
//		date.findMinDate(d);
//		System.out.println("Timestamp:               "+timestampLong);
//		System.out.println("Original Timestamp:      " + date.convertToDate(timestampLong));
//		long dayTimestampLong=date.calcOneDay(timestampLong);
//		System.out.println("One Day of Timestamp   : " + date.convertToDate(dayTimestampLong));
//		System.out.println("dayTimestampLong:        "+dayTimestampLong);
//		long weekTimestampLong=date.calcOneWeek(timestampLong);
//		System.out.println("weekTimestampLong:       "+weekTimestampLong);
//		System.out.println("Week Day of Timestamp  : " + date.convertToDate(weekTimestampLong));
//		long monthTimestampLong=date.calcOneMonth(timestampLong);
//		System.out.println("monthTimestampLong:      "+monthTimestampLong);
//		System.out.println("One Month of Timestamp : " + date.convertToDate(monthTimestampLong));
//		long yearTimestampLong=date.calcOneYear(timestampLong);
//		System.out.println("newTimestampLong:        "+yearTimestampLong);
//		System.out.println("One year of Timestamp  : " + date.convertToDate(yearTimestampLong));
//////		System.out.println("checkForLong:");
//////		date.checkRegToLong("1396345500");
////		System.out.println("Timestamp:         1388250900");
////		System.out.println("Timestamp to Date: " + date.convertToDate(date.calcOneDay(1396345500)));
////		System.out.println("Date to Timestamp: " + date.convertToTimestamp("28-12-2013 19:15"));
////		System.out.println("Timestamp:         1388247300");
////		System.out.println("Timestamp to Date: " + date.convertToDate(1388247300));
////		System.out.println("Date to Timestamp: " + date.convertToTimestamp("28-12-2013 18:15"));
////		System.out.println("checkForLong:");
////		date.checkRegToLong("1388247300");
////		d.printStoreStartTime();
////		System.out.println(d.convertDate(1396346400));
//
//	}

}
