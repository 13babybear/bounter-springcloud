package cn.bounter.common.util;

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
	
	public static void main(String[] args) {
		System.out.println(getExpiresIn(120));
	}
}
