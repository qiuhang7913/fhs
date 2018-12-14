package com.self.framework.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	public static void main(String a[]) {
		System.out.println(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L_WEEKDAY));
	}
}
