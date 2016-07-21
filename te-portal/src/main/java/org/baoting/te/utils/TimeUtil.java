package org.baoting.te.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * ClassName: TimeUtil
 * 
 * @description
 * @author yin_changbao
 * @Date Jul 30, 2015
 * 
 */
public final class TimeUtil {

	private static final Log logger = LogFactory.getLog(TimeUtil.class);
	public static final String defaultFormat = "yyyyMMddHHmmssSSS";
	public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

	public static final String YYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYY_MM = "yyyy-MM";

	public static final String format = "MM/dd HH:mm:ss";

	public static final String usformat = "MMM dd, yyyy HH:mm:ss";

	private TimeUtil() {

	}

	/**
	 * 
	 * @Description: local time to UTC time
	 * @param:localTime
	 * @return:
	 */
	public static Date getUTCTime(Date localTime) {

		Calendar calendar = Calendar.getInstance();
		if (localTime != null)
			calendar.setTime(localTime);
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return calendar.getTime();
	}

	/**
	 * 
	 * @Description: UTC time to local time
	 * @param:
	 * @return:
	 */
	public static Date getLocalTime(Date utcTime) {

		Calendar calendar = Calendar.getInstance();
		if (utcTime != null)
			calendar.setTime(utcTime);
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		calendar.add(java.util.Calendar.MILLISECOND, +(zoneOffset + dstOffset));
		return calendar.getTime();
	}

	/**
	 * 
	 * @Description: get current time with given time format
	 * @param:
	 * @return:
	 */
	public static String getCurrentTime(String format) {
		Date date = new Date();
		return date2String(date, format);
	}

	public static boolean compareCurrrentTime(Timestamp startTime, Timestamp endTime) {
		if (startTime == null) {
			startTime = new Timestamp(0);
		}

		Timestamp now = new Timestamp(System.currentTimeMillis());

		final int nanos = 1000;
		if (endTime == null) {
			endTime = new Timestamp(System.currentTimeMillis() + nanos);
		}

		if (now.after(startTime) && now.before(endTime)) {
			return true;
		} else {
			return false;
		}
	}

	public static Date defaultString2Date(String strDate) {
		try {
			return string2Date(strDate, defaultFormat);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static Date string2Date(String strDate, String format) throws ParseException {
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.parse(strDate);
	}

	public static String defaultDate2String(Date date) {
		return date2String(date, defaultFormat);
	}

	public static String date2String(Date date, String format) {
		if (date == null) {
			date = new java.util.Date();
		}

		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.format(date);
	}

	public static Timestamp date2Timestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static int getYearOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int[] divideTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -13);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		return new int[] { hour, minute, second };
	}

	public static int[] divideDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH + 1);
		int day = cal.get(Calendar.DATE);

		return new int[] { year, month, day };
	}

	public static long getNDayBefore(long time, int i) {
		return time - i * 3600 * 24 * 1000;
	}

	/**
	 * 获取给定时间月的始末时间
	 * 
	 * @param date
	 * @return long[0] starttimg, long[1] endtime
	 */
	public static long[] getLongRangeOfMonth(Date date) {
		long[] re = new long[2];
		try {
			date = date != null ? date : new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);

			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			calStart.set(Calendar.DATE, 1);
			re[0] = df2.parse(df.format(calStart.getTime()) + "  00:00:00").getTime();

			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			calEnd.set(Calendar.DATE, 1);
			calEnd.add(Calendar.MONTH, 1);
			calEnd.add(Calendar.DATE, -1);
			re[1] = df2.parse(df.format(calEnd.getTime()) + "  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;

	}

	/**
	 * 获取给定时间月的始末时间
	 * 
	 * @param date
	 * @return long[0] starttimg, long[1] endtime
	 */
	public static long[] getLongRangeOfThisWeek(Date date) {
		long[] re = new long[2];
		try {
			date = date != null ? date : new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);

			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			calStart.set(Calendar.DAY_OF_WEEK, 1);
			re[0] = df2.parse(df.format(calStart.getTime()) + "  00:00:00").getTime();

			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			calEnd.set(Calendar.DAY_OF_WEEK, 7);
			re[1] = df2.parse(df.format(calEnd.getTime()) + "  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;

	}

	/**
	 * 获取给定时间月的始末时间
	 * 
	 * @param date
	 * @return long[0] starttimg, long[1] endtime
	 */
	public static long[] getLongRangeOfLastWeek(Date date) {
		long[] re = new long[2];
		try {
			date = date != null ? date : new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);

			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			calStart.add(Calendar.WEEK_OF_YEAR, -1);
			calStart.set(Calendar.DAY_OF_WEEK, 1);
			re[0] = df2.parse(df.format(calStart.getTime()) + "  00:00:00").getTime();

			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			calEnd.add(Calendar.WEEK_OF_YEAR, -1);
			calEnd.set(Calendar.DAY_OF_WEEK, 7);
			re[1] = df2.parse(df.format(calEnd.getTime()) + "  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;

	}

	public static long[] getLongRangeOfDay(Date date) {
		long[] re = new long[2];
		try {
			date = date != null ? date : new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);

			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			re[0] = df2.parse(df.format(calStart.getTime()) + "  00:00:00").getTime();

			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			re[1] = df2.parse(df.format(calEnd.getTime()) + "  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public static Date getNMonthAround(Date date, int n) {
		date = date != null ? date : new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, n);
		return c.getTime();
	}

	public static int countMonths(long start, long end) throws ParseException {

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTimeInMillis(start);
		c2.setTimeInMillis(end);

		int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

		if (year < 0) {
			year = -year;
			return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}
		return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
	}

	/**
	 * return before Year-Month
	 * 
	 * @param month
	 * @return
	 */
	public static String getBeforeMonth(int month) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, month);

		Date theDate = calendar.getTime();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);

		String dayOfMonth = df.format(gcLast.getTime());
		// StringBuffer str = new
		// StringBuffer().append(day_first_prevM).append(" 00:00:00");
		// day_first_prevM = str.toString();
		return dayOfMonth;
	}

}
