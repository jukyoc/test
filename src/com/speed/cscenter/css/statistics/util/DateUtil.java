package com.speed.cscenter.css.statistics.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateUtil {
	public static String secondFormat(Calendar c, SimpleDateFormat df, Integer seconds) {
		if (c == null) {
			c = Calendar.getInstance();
		}
		if (df == null) {
			df = new SimpleDateFormat("HH:mm:ss");
		}
		if (seconds == null) {
			seconds = 0;
		}
		c.clear();
		c.set(Calendar.SECOND, seconds);
		return df.format(c.getTime());
	}
	public static String dateFormat(Date date, SimpleDateFormat df) {
		return dateFormat(date, df, false);
	}
	public static String dateFormat(Date date, SimpleDateFormat df, boolean createNull) {
		if (date == null) {
			if (createNull) {
				date = new Date();
			}
		}
		if (date == null) {
			return "";
		}
		return df.format(date);
	}
}
