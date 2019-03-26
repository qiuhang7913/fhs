package com.self.framework.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @des: 时间工具类
 * @author qiuhang
 * @version v1
 */
public class DateTool {
	public static final String FORMAT_L6 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_L5 = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_L4 = "yyyy-MM-dd HH";
	public static final String FORMAT_L3 = "yyyy-MM-dd";
	public static final String FORMAT_L_WEEKDAY = "yyyy-MM-dd E";

	/**
	 * 根据localDateTime 获取对应的字符串时间
	 * 获取失败后默认返回当前时间的字符串时间
	 * @return
	 */
	public static String getDataStrByLocalDateTime(LocalDateTime localDateTime, String dataRule){
		try {
			if (dataRule.contains("E")){
				return new SimpleDateFormat(dataRule, Locale.CHINA).format (Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant()));
			}
			return new SimpleDateFormat(dataRule).format (Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant()));
		}catch (Exception e){
			e.printStackTrace ();
			return new SimpleDateFormat (dataRule).format (Date.from(LocalDateTime.now ().atZone( ZoneId.systemDefault()).toInstant()));
		}
	}

	/**
	 * 根据localDateTime 获取对应的字符串时间
	 * 获取失败后默认返回当前时间的字符串时间
	 * @return
	 */
	public static LocalDate getLocalDateByDateStr(String dataString, String dataRule){
		try {
			if (dataRule.contains("E")){
				return LocalDate.parse(dataString, DateTimeFormatter.ofPattern(dataRule, Locale.CHINA));
			}
			return LocalDate.parse(dataString, DateTimeFormatter.ofPattern(dataRule));
		}catch (Exception e){
			e.printStackTrace ();
			return LocalDate.now();
		}
	}

	/**
	 * 获取前几日的时间数据
	 * @param howDays
	 * @return
	 */
	public static List<String> getBeforeDateStrByHowDays(Integer howDays){
		List<String> beforeDateList = new ArrayList<String>(  );
		int i = 1;
		while (i <= howDays){
			beforeDateList.add (getDataStrByLocalDateTime(LocalDateTime.now ().plusDays ( -i ), FORMAT_L3));
			i ++ ;
		}
		return beforeDateList;
	}

	/**
	 * 计算时间年份差
	 * @param localDateStart 被减数
	 * @param localDateEnd 减数
	 * @return
	 */
	public static Integer calculationYearDifference(LocalDate localDateStart, LocalDate localDateEnd){
		int years = calculationDifference(localDateStart, localDateEnd).getYears();
		return Integer.valueOf(years);
	}

	/**
	 * 计算时间月份差
	 * @param localDateStart
	 * @param localDateEnd
	 * @return
	 */
	public static Integer calculationMonthDifference(LocalDate localDateStart, LocalDate localDateEnd){
		int months = calculationDifference(localDateStart, localDateEnd).getMonths();
		return Integer.valueOf(months);
	}

	/**
	 * 计算
	 * @param localDateStart
	 * @param localDateEnd
	 * @return
	 */
	private static Period calculationDifference(LocalDate localDateStart, LocalDate localDateEnd){
		return Period.between(localDateStart, localDateEnd);
	}

	public static void main(String a[]) {
		System.out.println( DateTool.calculationYearDifference(
				DateTool.getLocalDateByDateStr("1993-06-30",DateTool.FORMAT_L3),
				LocalDate.now()
				)
		);
	}

}
