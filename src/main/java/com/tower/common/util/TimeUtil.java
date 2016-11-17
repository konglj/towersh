package com.tower.common.util;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.prefs.Preferences;

import javax.servlet.http.HttpServletRequest;


/**
 * <p> [ 项目名 ] : 电子商务系统
 * <p> [ 模块名 ] : common.util
 * <p> [ 文件名 ] : TimeUtil.java
 * <p> [ 功　能 ] : 时间的工具类
 * <p> [ 作　者 ] : 崔萌
 * <p> [ 版　本 ] : 1.0
 * <p> [ 时　间 ] : 2013-9-23 13:11:44 
 */
public class TimeUtil
{

	// 以下定义时间格式
	private static final SimpleDateFormat	format			= new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat	hmFormat		= new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat	lformat			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat	mformat			= new SimpleDateFormat("yyyy/M/d HH:mm:ss");
	private static final SimpleDateFormat	chformat		= new SimpleDateFormat("yyyy年MM月dd日");
	private static final SimpleDateFormat	pathFormat		= new SimpleDateFormat("yyyy\\MM\\dd\\");
	private static final SimpleDateFormat	fileFormat		= new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat	wFormat			= new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat	gmtFormat		= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
																	Locale.ENGLISH);
	private static final SimpleDateFormat	chmformat		= new SimpleDateFormat("yyyy年MM月");
	private static final SimpleDateFormat	chwformat		= new SimpleDateFormat("yyyy年第ww周");
	private static final SimpleDateFormat	sformat			= new SimpleDateFormat("MM.dd");
	private static final SimpleDateFormat	wholeFormat		= new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat	weekFormat		= new SimpleDateFormat("yyyy-ww");
	private static final SimpleDateFormat	weekPathFormat	= new SimpleDateFormat("yyyy\\ww\\");
	private static final SimpleDateFormat	monthFormat		= new SimpleDateFormat("yyyy-MM");
	private static final SimpleDateFormat	nFormat		= new SimpleDateFormat("yyyyMMddHHmmss");
	
	

	static
	{
		gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}


    public String dateToString(String time){
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        SimpleDateFormat formatter2=new SimpleDateFormat("yyyyMMddHHmmss");  
        try {
            time=formatter1.format(formatter2.parse(time));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return time;  
    }

	/**
	 * 获得传入时间参数的第二天,返回结果仍为字符串形式
	 * <p>设置的时间格式为yyyy-MM-dd
	 * @param time
	 * @return
	 */
	public static String nextDay(String time)
	{
		// 以下情况不处理
		if (time == null || time.length() == 0)
		{
			return time;
		}

		try
		{
			Date date = format.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);// 日期加一
			time = format.format(calendar.getTime());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return time;
	}

	/**
	 * 获得当天日期, 返回结果为中文型日期字符串(形如:yyyy年MM月dd日)
	 * @return
	 */
	public static String getToday()
	{
		Calendar calendar = Calendar.getInstance();
		String today = chformat.format(calendar.getTime());
		return today;
	}

	/**获得当前时间, 返回结果为日期字符串(形如:yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getNow()
	{
		Calendar calendar = Calendar.getInstance();
		String today = lformat.format(calendar.getTime());
		return today;
	}
	
	/**
	 * 获得当前日期 格式为yyyy-mm-dd
	 * @return
	 */
	public static String getNowDay(){
		Calendar calendar = Calendar.getInstance();
		String today = format.format(calendar.getTime());
		return today;
	}
	
	/**获得当前时间, 返回结果为日期字符串(形如:yyyy/M/d HH:mm:ss)
	 * @return
	 */
	public static String getMessageNow()
	{
		Calendar calendar = Calendar.getInstance();
		String today = mformat.format(calendar.getTime());
		return today;
	}
	
	/**获得当前时间, 返回结果为日期字符串(形如:yyyyMMddHHmmss)
	 * @return
	 */
	public static String getNNow()
	{
		Calendar calendar = Calendar.getInstance();
		String today = nFormat.format(calendar.getTime());
		return today;
	}

	/**获得昨天的当前时间, 返回结果为日期字符串(形如:yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return
	 */
	public static String getYesterdayTime()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String today = lformat.format(calendar.getTime());
		return today;
	}

	/**获得当前时间减去minute的时间, 返回结果为日期字符串(形如:yyyy-MM-dd HH:mm:ss)
	 * @param minute 分钟
	 * @return
	 */
	public static String getTimeBeforeNow(int minute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -minute);
		String today = lformat.format(calendar.getTime());
		return today;
	}

	/**获得当前时间减去second的时间, 返回结果为日期字符串(形如:yyyy-MM-dd HH:mm:ss)
	 * @param second 秒钟
	 * @return
	 */
	public static String getTimeBeforeNowSecond(int second)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -second);
		String today = lformat.format(calendar.getTime());
		return today;
	}

	/**获得给定日期, 当前时间减去second的时间, 返回结果为日期字符串(形如:yyyy-MM-dd HH:mm:ss)
	 * @param second 秒钟
	 * @param time 给定日期, 型如:yyyy-MM-dd
	 * @return
	 */
	public static String getTimeBeforeDateSecond(int second, String time)
	{
		Calendar calendar_date = getCalendarForGivingDayString(time);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -second);
		calendar.set(Calendar.YEAR, calendar_date.get(Calendar.YEAR));
		calendar.set(Calendar.DAY_OF_YEAR, calendar_date.get(Calendar.DAY_OF_YEAR));
		String today = lformat.format(calendar.getTime());
		return today;
	}

	/**获得当前时间的分钟
	 * @return
	 */
	public static int getMinute()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MINUTE);
	}

	/**获得当前时间的秒钟
	 * @return
	 */
	public static int getSecond()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.SECOND);
	}

	/**获得基于当前时间的文件路径格式(形如:yyyy\MM\dd\)
	 * @return
	 */
	public static String getTimeAsPathName()
	{
		Calendar calendar = Calendar.getInstance();
		return pathFormat.format(calendar.getTime());
	}

	/**获得基于当前时间的文件名格式(形如:yyyyMMdd)
	 * @return
	 */
	public static String getTimeAsFileName()
	{
		Calendar calendar = Calendar.getInstance();
		return fileFormat.format(calendar.getTime());
	}

	/**获得基于当前时间的文件名格式(形如:yyyyMMddHHmmss)
	 * @return
	 */
	public static String getWholeTimeAsFileName()
	{
		Calendar calendar = Calendar.getInstance();
		return wholeFormat.format(calendar.getTime());
	}

	/**
	 * 取得GMT时间
	 * @return Date
	 */
	public static Date getGMTDate(String time)
	{
		Calendar calendar = Calendar.getInstance();
		int offset = calendar.get(Calendar.ZONE_OFFSET) / 3600000 + calendar.get(Calendar.DST_OFFSET) / 3600000;
		calendar.add(Calendar.HOUR, -offset);
		Date date = calendar.getTime();

		return date;
	}

	/**得到已知时区的时间 
	 * @param local 例如:GMT+8

	 * @return
	 */
	public static String getLocalDatetimeString(String local)
	{
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(local));
		cal.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
		String date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.DAY_OF_MONTH);
		String time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
		return date + " " + time;
	}

	/**转化现有字符串时间(形如:yyyy-MM-dd 或者 yyyy-MM-dd HH:mm 或者 yyyy-MM-dd HH:mm:ss)
	 * <p>为GMT时间,格式为(形如:yyyy/MM/dd HH:mm:ss)
	 * @param time 
	 * @return
	 */
	public static synchronized String getGMTTime(String time)
	{
		if (time == null)
		{
			return "";
		}

		Date date = null;
		try
		{
			// yyyy-MM-dd
			if (time.length() == 10)
			{
				date = format.parse(time);
			}
			// yyyy-MM-dd HH:mm
			else if (time.length() == 16)
			{
				date = hmFormat.parse(time);
			}
			// yyyy-MM-dd HH:mm:ss
			else if (time.length() == 19)
			{
				date = lformat.parse(time);
			}
			else
			{
				return time;
			}
			return gmtFormat.format(date);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return "";
	}

	/**转化GMT时间(形如:yyyy/MM/dd HH:mm:ss)
	 * <p>为北京时间,格式为(形如:yyyy-MM-dd HH:mm:ss)
	 * @param time
	 * @return
	 */
	public static String getTimeFromGMT(String time)
	{
		if (time == null)
		{
			return "";
		}
		Date date = null;
		try
		{

			if (time.length() > 19)
			{
				time = time.substring(0, 19);
			}
			// yyyy/MM/dd HH:mm:ss
			if (time.length() == 19)
			{
				date = gmtFormat.parse(time);
				return lformat.format(date);
			}
			else
			{
				return time;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**判断是否超出试用期
	 * @param time long 毫秒计
	 * @param month int 试用月份
	 * @return 是否超出试用期
	 */
	public static boolean isTrialTimeOut(long time, int month)
	{
		// 调整时间 1000毫秒*60秒*60分*24小时*30天*月份
		time += 1000l * 60 * 60 * 24 * 30 * month;
		if (time < Calendar.getInstance().getTimeInMillis())
		{
			return true;
		}
		return false;
	}

	/**判断是否超出试用期
	 * @param time 试用期日期
	 * @return
	 */
	public static boolean isTrialTimeOut(String time)
	{
	
		
		 Calendar calendar_time = getCalendarForGivingDayString(time);
		 String nowdate="";
		try {
			
			  URL url = new URL("http://www.ntsc.ac.cn");// 取得资源对象
	          URLConnection uc = url.openConnection();// 生成连接对象
	          uc.connect();// 发出连接
	          long ld = uc.getDate();// 读取网站日期时间
	          Date date = new Date(ld);// 转换为标准时间对象
	          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	          nowdate = sdf.format(date);
		 
		} catch (Exception e) {
			// TODO: handle exception
		}
		Calendar now_time = getCalendarForGivingDayString(nowdate);
		
		boolean bool = calendar_time.before(now_time.getInstance());

		return bool;
	}
	
	/**获取当前网络时间 yyy-mm-dd
	 * @param time 试用期日期
	 * @return
	 */
	public static String getNetDay()
	{
	
		
		 String nowdate="";
		try {
			
			  URL url = new URL("http://www.ntsc.ac.cn");// 取得资源对象
	          URLConnection uc = url.openConnection();// 生成连接对象
	          uc.connect();// 发出连接
	          long ld = uc.getDate();// 读取网站日期时间
	          Date date = new Date(ld);// 转换为标准时间对象
	          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	          nowdate = sdf.format(date);
		 
		} catch (Exception e) {
			// TODO: handle exception
		}

		return nowdate;
	}
	
	
	/**判断是否超出试用期
	 * @param time 试用期日期
	 * @return
	 */
	public static boolean isTrialTimeOut(String beginTime, String endTime)
	{
		//时间为空, 则不判断, 直接返回true
		if (beginTime == null || beginTime.length() == 0 || endTime == null || endTime.length() == 0)
		{
			return true;
		}
		
		//得到结束时间, 并比较结束时间是否早于当前时间
		Calendar calendar_time = getCalendarForGivingDayString(endTime);
		boolean bool = calendar_time.before(Calendar.getInstance());		
		
		if (!bool)
		{
			//得到开始时间, 并比较开始时间是否晚于当前时间
			calendar_time = getCalendarForGivingDayString(beginTime);
			bool = calendar_time.after(Calendar.getInstance());
		}

		return bool;
	}

	/**判断是否超出时间
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isTimeOut(String beginTime, String endTime)
	{
		//时间为空, 则不判断, 直接返回true
		if (beginTime == null || beginTime.length() == 0 || endTime == null || endTime.length() == 0)
		{
			return true;
		}
		
		//得到结束时间, 并比较结束时间是否早于当前时间
		Calendar calendar_time = getCalendarForGivingNumberString(endTime);
		boolean bool = calendar_time.before(Calendar.getInstance());		
		
		if (!bool)
		{
			//得到开始时间, 并比较开始时间是否晚于当前时间
			calendar_time = getCalendarForGivingNumberString(beginTime);
			bool = calendar_time.after(Calendar.getInstance());
		}

		return bool;
	}

	/**得到当天的开始时间, 型如:yyyy-MM-dd HH:mm:ss
	 * @return 
	 */
	public static String getDailyBeginTime(String givingTime)
	{
		Calendar calendar = getCalendarForGivingDayString(givingTime);
		// calendar.add(Calendar.DAY_OF_MONTH, -1);
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		String time = lformat.format(calendar.getTime());
		return time;
	}

	/**得到当天的结束时间, 型如:yyyy-MM-dd HH:mm:ss
	 * @return 
	 */
	public static String getDailyEndTime(String givingTime)
	{
		Calendar calendar = getCalendarForGivingDayString(givingTime);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		String time = lformat.format(calendar.getTime());
		return time;
	}

	/**得到本周的开始时间, 型如:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getWeeklyBeginTime(String givingTime)
	{
		Calendar calendar = getCalendarForGivingWeekString(givingTime);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		String time = lformat.format(calendar.getTime());
		return time;
	}

	/**得到本周的结束时间, 型如:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getWeeklyEndTime(String givingTime)
	{
		Calendar calendar = getCalendarForGivingWeekString(givingTime);
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String time = lformat.format(calendar.getTime());
		return time;
	}

	/**得到本月的开始时间, 型如:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getMonthlyBeginTime(String givingTime)
	{
		Calendar calendar = getCalendarForGivingMonthString(givingTime);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String time = lformat.format(calendar.getTime());
		return time;
	}

	/**得到本月的结束时间, 型如:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getMonthlyEndTime(String givingTime)
	{
		Calendar calendar = getCalendarForGivingMonthString(givingTime);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String time = lformat.format(calendar.getTime());
		return time;
	}


	/**将给定的时间字符串转为Calendar返回
	 * @param time 型如:yyyy-MM-dd
	 * @return
	 */
	public static Calendar getCalendarForGivingDayString(String time)
	{
		Date date = null;
		try
		{
			date = format.parse(time);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	/**将给定的时间字符串转为Calendar返回
	 * @param time 型如:yyyy-ww
	 * @return
	 */
	public static Calendar getCalendarForGivingWeekString(String time)
	{
		Date date = null;
		try
		{
			date = weekFormat.parse(time);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	/**将给定的时间字符串转为Calendar返回
	 * @param time 型如:yyyy-MM-dd
	 * @return
	 */
	public static Calendar getCalendarForGivingMonthString(String time)
	{
		Date date = null;
		try
		{
			date = monthFormat.parse(time);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}
	
	/**将给定的时间字符串转为Calendar返回
	 * @param time 型如:yyyyMMdd
	 * @return
	 */
	public static Calendar getCalendarForGivingNumberString(String time)
	{
		Date date = null;
		try
		{
			date = fileFormat.parse(time);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	/**得到格式字符串, 型如:yyyy\\MM\\dd\\
	 * @param calendar
	 * @return
	 */
	public static String getCalendarPathFormat(Calendar calendar)
	{
		return pathFormat.format(calendar.getTime());
	}

	/**得到格式字符串, 型如:yyyy\\ww\\
	 * @param calendar
	 * @return
	 */
	public static String getCalendarWeekPathFormat(Calendar calendar)
	{
		return weekPathFormat.format(calendar.getTime());
	}

	/**得到格式字符串, 型如:yyyy-ww
	 * @param calendar
	 * @return
	 */
	public static String getCalendarWeekFormat(Calendar calendar)
	{
		return weekFormat.format(calendar.getTime());
	}

	/**得到格式字符串, 型如:yyyy-MM
	 * @param calendar
	 * @return
	 */
	public static String getCalendarMonthFormat(Calendar calendar)
	{
		return monthFormat.format(calendar.getTime());
	}

	/**得到格式字符串, 型如:yyyy年MM月dd日
	 * @param calendar
	 * @return
	 */
	public static String getCalendarChFormat(Calendar calendar)
	{
		return chformat.format(calendar.getTime());
	}

	/**得到格式字符串, 型如:yyyy-MM-dd
	 * @param calendar
	 * @return
	 */
	public static String getCalendarFormat(Calendar calendar)
	{
		return format.format(calendar.getTime());
	}

	/**得到当前日期, 型如:yyyy-MM-dd
	 * 如果小时数为0, 则算作前一日的日期
	 * @return
	 */
	public static String getThisDay()
	{
		Calendar calendar = Calendar.getInstance();

//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//
//		if (hour == 0)
//		{
////			calendar.add(Calendar.DAY_OF_MONTH, -1);
//		}

		return getCalendarFormat(calendar);
	}
	
	/**得到当前日期的中文字符串作为标题使用, 型如:yyyy年MM月dd日
	 * 如果小时数为0, 则算作前一日的日期
	 * @return
	 */
	public static String getThisDayAsTitle()
	{
		return getThisDayAsTitle(getThisDay());
	}

	/**
	 * 得到给定日期, 返回中文字符串作为标题使用, 型如:yyyy年MM月dd日
	 * @param givingTime 给定字符串, 型如:yyyy-MM-dd
	 * @return
	 */
	public static String getThisDayAsTitle(String givingTime)
	{
		Calendar calendar = getCalendarForGivingDayString(givingTime);

		return getCalendarChFormat(calendar);
	}

	/**得到本周日期, 如果为周一, 且小时数为0, 则算作前一周的日期, 型如:yyyy-ww
	 * @return
	 */
	public static String getThisWeek()
	{
		Calendar calendar = Calendar.getInstance();

		// calendar.setFirstDayOfWeek(Calendar.MONDAY);
		// calendar.setMinimalDaysInFirstWeek(7);

		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		if (weekday == Calendar.MONDAY && hour == 0)
		{
			calendar.add(Calendar.WEEK_OF_MONTH, -1);
		}

		return getCalendarWeekFormat(calendar);
	}
	

	/**得到给定日期, 返回中文字符串作为标题使用, 型如:yyyy年第ww周[MM.dd-MM.dd]
	 * @param givingTime 给定字符串, 型如:yyyy-ww
	 * @return
	 */
	public static String getThisWeekAsTitle(String givingTime)
	{
		Calendar calendar = getCalendarForGivingWeekString(givingTime);

		StringBuilder builder = new StringBuilder();
		builder.append(chwformat.format(calendar.getTime()));

		builder.append("[");
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		builder.append(sformat.format(calendar.getTime()));
		builder.append("-");
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		builder.append(sformat.format(calendar.getTime()));
		builder.append("]");

		return builder.toString();
	}

	/**得到本月日期, 如果为第一天, 且小时数为0, 则算作前一月的日期, 型如:yyyy-MM
	 * @return
	 */
	public static String getThisMonth()
	{
		Calendar calendar = Calendar.getInstance();

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		if (day == 1 && hour == 0)
		{
			calendar.add(Calendar.MONTH, -1);
		}

		return getCalendarMonthFormat(calendar);
	}

	/**得到给定日期, 返回中文字符串作为标题使用, 型如:yyyy年MM月
	 * @param givingTime 给定字符串, 型如:yyyy-MM
	 * @return
	 */
	public static String getThisMonthAsTitle(String givingTime)
	{
		Calendar calendar = getCalendarForGivingMonthString(givingTime);
		
		return chmformat.format(calendar.getTime());
	}

	/**设置注册的起止时间以及记录的时间和标志
	 * @param beginTime
	 * @param endTime
	 * @param flag
	 * @param time
	 */
	public static void setRegTimeFlag(String beginTime, String endTime, String flag, String time)
	{
		Preferences preferences = Preferences.systemRoot();
		
//		preferences.put(Constants.REG_BEGIN_TIME, beginTime);
//		preferences.put(Constants.REG_END_TIME, endTime);
//		preferences.put(Constants.REG_FLAG, flag);
//		preferences.put(Constants.REG_TIME, time);
	}
	
	/**设置记录的时间和标志
	 * @param time 时间
	 * @param flag 标志
	 */
	public static void setRegTimeFlag(String flag, String time)
	{
		Preferences preferences = Preferences.systemRoot();

//		preferences.put(Constants.REG_FLAG, flag);
//		preferences.put(Constants.REG_TIME, time);
	}
	
	/**得到记录的时间
	 * @return
	 */
	public static String getRegTime()
	{
		Preferences preferences = Preferences.systemRoot();
		
//		return preferences.get(Constants.REG_TIME, "");
		return null;
	}
	
	/**得到记录的标志
	 * @return
	 */
	public static String getRegFlag()
	{
		Preferences preferences = Preferences.systemRoot();
		
//		return preferences.get(Constants.REG_FLAG, "");
		return null;
	}
	
	/**得到注册开始时间
	 * @return
	 */
	public static String getRegBeginTime()
	{
		Preferences preferences = Preferences.systemRoot();
		
//		return preferences.get(Constants.REG_BEGIN_TIME, "");
		return null;
	}
	
	/**得到注册结束时间
	 * @return
	 */
	public static String getRegEndTime()
	{
		Preferences preferences = Preferences.systemRoot();
		
//		return preferences.get(Constants.REG_END_TIME, "");
		return null;
	}
	
	/**得到记录的比较结果
	 * @return
	 */
	public static boolean isRegFlag()
	{
		Preferences preferences = Preferences.systemRoot();
		
//		String beginTime = preferences.get(Constants.REG_BEGIN_TIME, "");
//		String endTime = preferences.get(Constants.REG_END_TIME, "");
//		return isTrialTimeOut(beginTime, endTime);
		return false;
	}
	
	public static Timestamp strToTimeStamp(String time){
		
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}
	
}
