/**
 *
 */
package com.lagou.edu.util;


import io.micrometer.core.instrument.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author  <a href="mailto:xuyy@yyt.com">Xu Yuanyuan</a>
 * @version 1.0
 * @date    2018年7月31日 下午12:17:12
 * @desc    DateUtil
 */
public class DateUtil {

	static{
		initMap();
	}

	public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat dateparttimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat cnDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM    = "yyyy-MM-dd HH:mm";
	public static final String CN_YYYY_MM_DD_HH_MM = "yyyy年MM月dd日 HH时mm分";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String HH_MM = "HH:mm";
	private static Map<String,String> weekMap;

	/**
	 * 将日期转换为时间戳
	 *
	 * @param date
	 * @return Timestamp
	 * @throws Exception
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	private static void initMap() {
		weekMap = new HashMap<String,String>();
		weekMap.put("Monday", "星期一");
		weekMap.put("Tuesday", "星期二");
		weekMap.put("Wednesday", "星期三");
		weekMap.put("Thursday", "星期四");
		weekMap.put("Friday", "星期五");
		weekMap.put("Saturday", "星期六");
		weekMap.put("Sunday", "星期日");
	}

	/**
	 * 得到当前时间戳
	 *
	 * @return Timestamp
	 */
	public Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 得到当前sql时间
	 *
	 * @return
	 */
	public java.sql.Date getNowSqlDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * 得到格式化后的日期字符串
	 *
	 * @param date
	 * @param format
	 *            eg:yyyy-MM-dd
	 * @return
	 */
	public static String getFormatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 将当前时间格式化
	 *
	 * @param format
	 * @return
	 */
	public static String getFormatNow(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 获得当前时间
	 *
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}

	/**
	 * 获得当前日期时间
	 * 日期时间格式yyyy-MM-dd HH:mm
	 * @return
	 */
	public static String currentDatePartTime() {
		return dateparttimeFormat.format(now());
	}

	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}

	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 *
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}

	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 *
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}



	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH:mm:ss
	 *
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(now());
	}

	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH:mm:ss
	 *
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH年mm月ss日
	 *
	 * @return
	 */
	public static String currentCnDate(){
		return cnDateFormat.format(now());
	}

	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH年mm月ss日
	 *
	 * @return
	 */
	public static String formatCnDate(Date date) {
		return cnDateFormat.format(date);
	}

	public static Calendar calendar() {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	/**
	 * 获得当前时间的毫秒数
	 * <p>
	 * 详见{@link System#currentTimeMillis()}
	 *
	 * @return
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}
	/**
	 * 获取当前年
	 */
	public static int year() {
		return calendar().get(Calendar.YEAR);
	}
	/**
	 *
	 * 获得当前Chinese月份
	 *
	 * @return
	 */
	public static int month() {
		return calendar().get(Calendar.MONTH) + 1;
	}

	/**
	 *
	 * 获得当前季度
	 *
	 * @return
	 */
	public static int quarter() {
		int month = month() - 1;
		return month / 3 + 1;
	}

	/**
	 * 获得月份中的第几天
	 *
	 * @return
	 */
	public static int dayOfMonth() {
		return calendar().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 今天是星期的第几天
	 *
	 * @return
	 */
	public static int dayOfWeek() {
		return calendar().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 今天是年中的第几天
	 *
	 * @return
	 */
	public static int dayOfYear() {
		return calendar().get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断原日期是否在目标日期之前
	 *
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isBefore(Date src, Date dst) {
		return src.before(dst);
	}

	/**
	 * 判断原日期是否在目标日期之后
	 *
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isAfter(Date src, Date dst) {
		return src.after(dst);
	}

	/**
	 * 判断两日期是否相同
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqual(Date date1, Date date2) {
		return date1.compareTo(date2) == 0;
	}

	/**
	 * 判断某个日期是否在某个日期范围
	 *
	 * @param beginDate
	 *            日期范围开始
	 * @param endDate
	 *            日期范围结束
	 * @param src
	 *            需要判断的日期
	 * @return
	 */
	public static boolean between(Date beginDate, Date endDate, Date src) {
		return beginDate.before(src) && endDate.after(src);
	}

	/**
	 * 获得当前月的最后一天
	 * <p>
	 * HH:mm:ss为0，毫秒为999
	 *
	 * @return
	 */
	public static Date lastDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
		cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
		return cal.getTime();
	}

	/**
	 * 通过传入的日期 格式为yyyy-MM-dd
	 * 获取该日期中文形式的星期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String dayOfWeek(String date) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		String day = "";
		switch(weekDay) {
	        case 1 :
	        	day="日";
	        break;
	        case 2 :
	        	day="一";
	        break;
	        case 3 :
	        	day="二";
	        break;
	        case 4 :
	        	day="三";
	        break;
	        case 5 :
	        	day="四";
	        break;
	        case 6 :
	        	day="五";
	        break;
	        case 7 :
	        	day="六";
	        break;
	        default:

	        break;
    }
		return day;
	}

	/**
	 * 获得当前年的第一天
	 * <p>
	 * HH:mm:ss SS为零
	 *
	 * @return
	 */
	public static Date firstDayOfYear() {
		Calendar cal = calendar();
		cal.set(Calendar.MONTH, 0); // M月置1
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		return cal.getTime();
	}

	/**
	 * 获得当前月的第一天
	 * <p>
	 * HH:mm:ss SS为零
	 *
	 * @return
	 */
	public static Date firstDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		return cal.getTime();
	}

	private static Date weekDay(int week) {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_WEEK, week);
		return cal.getTime();
	}

	/**
	 * 获得周五日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 *
	 * @return
	 */
	public static Date friday() {
		return weekDay(Calendar.FRIDAY);
	}

	/**
	 * 获得周六日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 *
	 * @return
	 */
	public static Date saturday() {
		return weekDay(Calendar.SATURDAY);
	}

	/**
	 * 获得周日日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 *
	 * @return
	 */
	public static Date sunday() {
		return weekDay(Calendar.SUNDAY);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 *
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetime(String datetime) throws ParseException {
		return datetimeFormat.parse(datetime);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm
	 *
	 * @param datetime
	 * @return
	 */
	public static Date parsePartDatetime(String datetime) throws ParseException {
		return dateparttimeFormat.parse(datetime);
	}

	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	public static Date parse(String date,String format) throws ParseException {
		return new SimpleDateFormat(format).parse(date);
	}

	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 时间格式 HH:mm:ss
	 *
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String time) throws ParseException {
		return timeFormat.parse(time);
	}

	/**
	 * 给当前日期加上整数年返回
	 *
	 * @param year
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}

	/**
	 * 给传入时间加上整数月返回
	 *
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	/**
	 * 给传入日期加上整数天返回
	 *
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, day);
		return c.getTime();
	}

	/**
	 * 给传入日期加上整数小时返回
	 *
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}

	/**
	 * 给传入日期加上整数分钟返回
	 *
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	/**
	 * 给传入日期加上整数秒返回
	 *
	 * @param minute
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}

	/**
	 * 获得传入日期月的天数
	 *
	 * @param date
	 * @param int
	 * @return
	 */
	public static int getMonthDays(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取传入月份上个月第一天日期（格式：yyyy-MM-dd）
	 * @param yearMonth
	 * @return
	 * @throws @throws
	 * Exception
	 */
	public static String getLastMonthFirstDate(String yearMonth) throws ParseException {
		Date date = parseDate(yearMonth + "-01");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return formatDate(calendar.getTime());
	}

	/**
	 * 获取传入月份下个月最后一天日期（格式：yyyy-MM-dd）
	 * @param yearMonth
	 * @return
	 * @throws YytException
	 */
	public static String getNextMonthLastDate(String yearMonth) throws ParseException {
			Calendar calendar = Calendar.getInstance();
			Date date = parseDate(yearMonth + "-01");
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DATE, -1);
			return formatDate(calendar.getTime());
	}

	/**
	 * 获取本周第一天
	 * @return
	 */
	public static Date getCurrWeekFirstDate(){
		Calendar cal = Calendar.getInstance();
		if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1){//是周日获取上周的
			cal.add(Calendar.DAY_OF_YEAR,-1);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date date = cal.getTime();
		return date;
   }

	/**
	 * 获取下周第一天日期
	 * @return
	 */
	public static Date getNextWeekFirstDate(){
		Calendar cal = Calendar.getInstance();
		if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1){//是周日获取上周的
			cal.add(Calendar.DAY_OF_YEAR,-1);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, 7);
		Date date = cal.getTime();
		return date;
   }


	/**
	 * 是否小于当前日期
	 * @param DateStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static boolean isLtCurrDate(String dateStr, String format) throws Exception {
		long currDateTime = DateUtil.parse(DateUtil.getFormatNow(format),format).getTime();
		long dateTime     = DateUtil.parse(dateStr,format).getTime();
		return dateTime < currDateTime;
	}

	/**
	 * 获取2个日期天数差(格式为：yyyy-MM-dd)
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String date1,String date2) throws ParseException {
		Date date11 = parse(date1, DateUtil.YYYY_MM_DD);
		Date date22 = parse(date2, DateUtil.YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date11);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date22);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Math.abs(Integer.parseInt(String.valueOf(between_days)));
    }

	public static boolean isDateTime(String dateStr) {
		try{
			if(StringUtils.isBlank(dateStr)){
				return false;
			}
			parse(dateStr, YYYY_MM_DD_HH_MM_SS);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 设置日期指定字段值
	 *
	 * @param field 日历对应字段
	 * @param value 日历对应字段值
	 * @return
	 */
	public static Date setDate(int field,int value) {
		return setDate(new Date(), field, value);
	}

	public static Date setDate(Date date,int field,int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(field, value);
		return cal.getTime();
	}
	/**
	 * 根据传入的格式化字符串一级日期来获取指定日期格式
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatString(String date,String format) throws ParseException {
		Date date1 = dateparttimeFormat.parse(date);
		return new SimpleDateFormat(format).format(date1);
	}


	/**
	 * 获取某天开始时间,时间为00:00:00
	 *
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getStartTimeOfDay(Date date){
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取某天结束时间,时间为23:59:59
	 *
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getEndTimeOfDay(Date date){
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定年、月、日的某一天
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getDate(int year,int month,int day){
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.set(year, month, day);
		Date date = cal.getTime();
		return DateUtil.getFormatDate(date, DateUtil.YYYY_MM_DD);
	}
	/**
	 * 返回当前时分
	 * @return
	 */
	public static String currentHour(){
		return new SimpleDateFormat(DateUtil.HH_MM).format(now());
	}

	public static String EngWeekToChs(String week){
		if(StringUtils.isBlank(week)){
			return "";
		}
		return weekMap.get(week);
	}

	/**
	 * 拆分日期
	 *
	 * @param week
	 * @return
	 */
	public static String[] spliteDate(String date){
		String[] dates = new String[3];
		if(StringUtils.isNotBlank(date)){
			if(date.length() == 4){
				date += "-00-00";
			}else if(date.length() == 7){
				date += "-00";
			}else if(date.length() >= 10){
				date = date.substring(0,10);
			}
			return date.split("-");
		}
		return dates;
	}

	public static void main(String[] args) {
		String dd = "2012-04-12 12:34";
		String[] dates = spliteDate(dd);
		System.out.println(dates[0]);
		System.out.println(dates[1]);
		System.out.println(dates[2]);
	}

	/**
	 * 获取当前日期的0点和24点
	 * @return {@link String}* @throws ParseException 解析异常
	 */
	public static String DateToStringBeginOrEnd(String zy, Boolean flag) throws ParseException {
		String time = null;
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateformat1.parse(zy);
		Calendar calendar1 = Calendar.getInstance();
		//获取某一天的0点0分0秒 或者 23点59分59秒
		if (flag) {
			calendar1.setTime(date);
			calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
					0, 0, 0);
			Date beginOfDate = calendar1.getTime();
			time = dateformat1.format(beginOfDate);
			System.out.println(time);
		}else{
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(date);
			calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
					23, 59, 59);
			Date endOfDate = calendar1.getTime();
			time = dateformat1.format(endOfDate);
			System.out.println(time);
		}
		return time;
	}
}
