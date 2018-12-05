package cn.bounter.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author simon
 *
 */
public class DateUtil {

	/** 月 */
	public static final int MONTH = 2;
	/** 日 */
	public static final int DAY = 6;
	/** 小时 */
	public static final int HOUR = 10;
	/** 分钟 */
	public static final int MINUTE = 12;
	/** 秒 */
	public static final int SECOND = 13;


	/**
	 * 获取格式化日期字符串，如：2017-11-16 16:10:00
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
		return null;
	}


	/**
	 * 解析日期字符串
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) throws ParseException {
		if (dateStr != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		}
		return null;
	}

	/**
	 * 获取n天后的时间
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateSomeDaysLater(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, days);
		return now.getTime();
	}
	
	/**
	 * 获取指定日期后n天的时间
	 * @param days
	 * @param date
	 * @return
	 */
	public static Date getDateSomeDaysLater(int days, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/**
	 * 获取n个月后的时间
	 * 
	 * @param months
	 * @return
	 */
	public static Date getDateSomeMonthsLater(int months) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, months);
		return now.getTime();
	}

	/**
	 * 获取指定日期后n个月的时间
	 * 
	 * @param months
	 * @param date
	 * @return
	 */
	public static Date getDateSomeMonthsLater(int months, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * 计算目标日期和今天相差的天数
	 * @param targetDate
	 * @return
	 */
	public static Long dayUntil(Date targetDate) {
		ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(targetDate.toInstant(), zone);
		return LocalDate.now().until(localDateTime.toLocalDate(), ChronoUnit.DAYS);
	}

	/**
	 * 获取n分钟后的日期
	 * @param days
	 * @return
	 */
	public static Date getDateMinutesLater(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE,minutes);
		return now.getTime();
	}

	/**
	 * 获取n天后的日期
	 * @param days
	 * @return
	 */
	public static Date getDateDaysLater(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR,days);
		return now.getTime();
	}
	
	/**
	 * 根据过期天数获取过期日期
	 * @param days
	 * @return
	 */
	public static Date getExpireTimeByDay(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR,days);
		return now.getTime();
	}


	/**
	 * 获取n秒后的过期时间
	 * @param seconds
	 * @return
	 */
	public static Date getExpiresIn(int seconds) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND,seconds);
		return now.getTime();
	}


	/**
	 * 获取过期时间
	 * @param unit		时间单位（eg:DateUtil.MONTH）
	 * @param time		时间值（eg:3）
	 * @return
	 */
	public static Date getExpiresIn(int unit, int time) {
		Calendar now = Calendar.getInstance();
		now.add(unit,time);
		return now.getTime();
	}


	/**
	 * 获取n天后的日期
	 * @param unit
	 * @param time
	 * @param date		指定的日期
	 * @return
	 */
	public static Date getDateIn(int unit, int time, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(unit,time);
		return calendar.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(getExpiresIn(120));
	}
}
