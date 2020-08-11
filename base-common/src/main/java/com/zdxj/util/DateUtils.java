package com.zdxj.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String FORMAT_TIME_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_STR = "yyyy-MM-dd";
    public static final String FORMAT_DATE_YEAR_MONTH_STR = "yyyy-MM";
    public static final String FORMAT_DATE_YEAR_STR = "yyyy";
    public static final String FORMAT_TIME_MS_STR = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_DATETIME_STR = "yyyyMMddHHmmss";
    public static final long MINUTE_MILLI = 60 * 1000;


    /**
     * 自定义格式化日期
     * @author zhaodx
     * @date 2018年12月26日
     * @param formatStr
     * @param date
     * @return 
     */
    public static String formatDateToStr(String formatStr, Date date) {
        DateFormat df = new SimpleDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 时间转化成String型
     * @author zhaodx
     * @date 2018年12月26日
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getTimeStr(Date date) {
        return null ==date?null:formatDateToStr(FORMAT_TIME_STR, date);
    }

    /**
     * 时间转换成String型
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getDateStr(Date date) {
        return null ==date?null:formatDateToStr(FORMAT_DATE_STR, date);
    }

    /**
     * 时间转换成String型
     * @param date
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getMsTimeStr(Date date) {
        return null ==date?null:formatDateToStr(FORMAT_TIME_MS_STR,date);
    }

    public static String getNowTimeStr(String formatStr) {
		DateFormat df = new SimpleDateFormat(formatStr);

		return df.format(new Date());
	}

	public static String getNowTimeStr() {
		return getNowTimeStr(FORMAT_TIME_STR);
	}
	
    public static Date getDateBefore(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(5, now.get(5) - day);
        return now.getTime();
    }

    public static Date getDateAfter(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(5, now.get(5) + day);
        return now.getTime();
    }

    public static Date toDate(String sDate) {
        Date dt = null;
        try {
        	SimpleDateFormat formatter = null ;
            if(sDate.length()>10) {
            	formatter = new SimpleDateFormat(FORMAT_TIME_STR);
            }else {
            	formatter = new SimpleDateFormat(FORMAT_DATE_STR);
            }
            
            dt = formatter.parse(sDate);
        } catch (Exception e) {
            dt = null;
        }
        return dt;
    }

    /**
     * 时间字符串转日期格式
     * @author zhaodx
     * @date 2020-03-10 13:38:18
     * @param sDate
     * @param format
     * @return
     */
    public static Date strToDate(String sDate,String format) {
        Date dt = null;
        try {
        	SimpleDateFormat formatter = new SimpleDateFormat(format);
            dt = formatter.parse(sDate);
        } catch (Exception e) {
            dt = null;
        }
        return dt;
    }
    
    public static Date toDate(String sDate,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dt = null;
        try {
            dt = formatter.parse(sDate);
        } catch (Exception e) {
            dt = null;
        }
        return dt;
    }

    /**
     * 当前时间增加或者减少几分钟
     * @author zdx
     * @date 2016-9-13
     * @param minute
     * @return
     */
    public static Date addMinute(int minute) {
        return new Date(System.currentTimeMillis() + minute * DateUtils.MINUTE_MILLI);
    }
    
    /**
     * 增加或者减少几分钟
     * @author zdx
     * @date 2016-9-13
     * @param minute
     * @return
     */
    public static Date addMinute(Date date ,int minute) {
        return new Date(date.getTime() + minute * DateUtils.MINUTE_MILLI);
    }
    
    /**
     * 增加月数
     * @author zhaodx
     * @date 2019年2月27日
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH,month);
        return now.getTime();
    }
    /**
     * 增加天数
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH,day);
        return now.getTime();
    }
    
    /**
    * 功能描述: 根据日期获取星期几
    * @param: [date]
    * @return: java.lang.String
    */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    /**
     * 转日历格式
     * @param date
     * @return
     */
    public static Calendar getCalendar(Date date){
		if(date==null)
		{
			return null;
		}
		DateFormat df = DateFormat.getDateInstance();
		df.format(date);		 
		return df.getCalendar();		
	}
    
    /**
     * 获取时间的小时 
     * @param date
     * @return
     */
    public static Integer getHour(Date date){
		if(date==null)
		{
			return null;
		}				
		return getCalendar(date).get(Calendar.HOUR_OF_DAY);
	}
    
    /**
     * 获取时间的日
     * @param date
     * @return
     */
    public static Integer getDay(Date date){
    	if(date==null)
    	{
    		return null;
    	}				
    	return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取本月有多少天
     * @param date
     * @return
     */
    public static Integer getDayCount(Date date){
    	if(date==null)
    	{
    		return null;
    	}				
    	return getCalendar(date).getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取某一天是星期几
     * @param date
     * @return
     */
    public static Integer getDayOfWeek(Date date){
    	if(date==null)
    	{
    		return null;
    	}				
    	return getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 获取时间的23:59:59
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
    	Calendar calendarEnd = Calendar.getInstance();
    	calendarEnd.setTime(date);
    	calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
    	calendarEnd.set(Calendar.MINUTE, 59);
    	calendarEnd.set(Calendar.SECOND, 59);
    	//防止mysql自动加一秒,毫秒设为0
    	calendarEnd.set(Calendar.MILLISECOND, 0);
    	return calendarEnd.getTime();
    }
    
    /**
     * 两个时间相隔时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static String dateDiff(Date startTime, Date endTime) {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
		long nh = 1000 * 60 * 60;// 一小时的毫秒数  
		long nm = 1000 * 60;// 一分钟的毫秒数  
		long ns = 1000;// 一秒钟的毫秒数  
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 获得两个时间的毫秒时间差异  
		try {
			diff = endTime.getTime() - startTime.getTime();
			day = diff / nd;// 计算差多少天  
			hour = diff % nd / nh + day * 24;// 计算差多少小时  
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟  
			sec = diff % nd % nh % nm / ns;// 计算差多少秒  
			return (day>0?day+"天":"")+((hour - day * 24)>0?(hour - day * 24)+"小时":"")+((min - day * 24 * 60)>0?(min - day * 24 * 60)+"分钟":"")+ (sec>0?sec+"秒":"0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

    /**
     * 获取每天的开始时间 00:00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        dateStart.set(Calendar.MILLISECOND, 0);
        return dateStart.getTime();
    }

    /**
     * 获取每天的结束时间 23:59:59:999
     *
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        dateEnd.set(Calendar.MILLISECOND, 0);
        return dateEnd.getTime();
    }
    /**
     * 获取当前日期星期一日期
     *
     * @return date
     */
    public static Date getFirstDayOfWeek() {
        Calendar currCal=Calendar.getInstance();
        currCal.setTime(new Date());
        currCal.set(Calendar.DAY_OF_WEEK, 2); // Monday
        currCal.set(Calendar.HOUR_OF_DAY, 0);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        return currCal.getTime();
    }


    /**
     * 获取当前月的第一天0时
     *
     * @return date
     */
    public static Date getFirstDayOfMonth() {
        Calendar currCal=Calendar.getInstance();
        currCal.setTime(new Date());
        currCal.set(Calendar.DAY_OF_MONTH, 1);
        currCal.set(Calendar.HOUR_OF_DAY, 0);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        return currCal.getTime();
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        currCal.set(Calendar.DAY_OF_YEAR, 1);
        currCal.set(Calendar.HOUR_OF_DAY, 0);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        return currCal.getTime();
    }

    /**
     * 获取时间戳(精确到秒)
     * @author zhaodx
     * @date 2019年8月5日
     * @param date
     * @return
     */
    public static int getSecondTimestamp(Date date){
	    if (null == date) {  
	        return 0;  
	    }  
	    String timestamp = String.valueOf(date.getTime());  
	    int length = timestamp.length();  
	    if (length > 3) {  
	        return Integer.valueOf(timestamp.substring(0,length-3));  
	    } else {  
	        return 0;  
	    }  
	}
}
