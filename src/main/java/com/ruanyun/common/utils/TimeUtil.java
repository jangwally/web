package com.ruanyun.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {


	/**
	 * 根据 <code>weekDay</code> 返回那一天的日期
	 * @param weekDay 星期几 1 - 7
	 * @param basisDate 基准的时间,基准参数所在的星期
	 * @return Date日期
	 */
	public static Date getDateForWeekDay(int weekDay,Date basisDate) {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(basisDate);
		int basisWeekDay = currentCalendar.get(Calendar.DAY_OF_WEEK);
		currentCalendar.add(Calendar.DATE, weekDay - basisWeekDay);
		return currentCalendar.getTime();
	}
	
	/**
	 * 自定义格式化日期输出
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String doFormatDate(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}
	
	/**
	 * 自定义格式化日期输出  
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date doFormatDate(String date, String format) {
		try {
			return (new SimpleDateFormat(format)).parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * 功能描述:获取当前时间的小时分钟 格式为：hh:mm
	 *
	 * @author yangliu  2013-7-25 上午09:22:41
	 * 
	 * @return
	 */
	public static String getCurrentHoursMin(){
		return doFormatDate(new Date(),"HH:mm");
	}
	
	/**
	 * 功能描述: 获取当前日期
	 *
	 * @author yangliu  2013-7-25 下午07:43:42
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDay(String format){
		return doFormatDate(new Date(), format);
	}
	
	/**
	 * 功能描述:获取两个日期之间相差多少小时(firstDate要大于secondDate)
	 *
	 * @author L H T  2014-5-21 下午03:25:30
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static long getBetweenHour(Date firstDate,Date secondDate){
		if (EmptyUtils.isEmpty(firstDate) || EmptyUtils.isEmpty(secondDate)) {
			return 0;
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long hour=0;
		try {
		//获得两个时间的毫秒时间差异
		long betweenNs = sd.parse(sd.format(firstDate)).getTime() - sd.parse(sd.format(secondDate)).getTime();
		
		hour = betweenNs%(1000*24*60*60)/(1000*60*60);//计算差多少小时
		
		//输出结果
		System.out.println("时间相差："+hour+"小时");
		} catch (ParseException e) {
		e.printStackTrace();
		}
		return hour;
	}
	
	//测试方法
	public static int getTest(Date firstDate,Date secondDate){
		if (EmptyUtils.isEmpty(firstDate) || EmptyUtils.isEmpty(secondDate)) {
			return 0;
		}
		SimpleDateFormat sdf=new SimpleDateFormat();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long diff;
		try {
		//获得两个时间的毫秒时间差异
		diff = sd.parse("2014-05-22 00:00:00").getTime() - sd.parse(sd.format(secondDate)).getTime();
		long day = diff/nd;//计算差多少天
		long hour = diff%nd/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		long sec = diff%nd%nh%nm/ns;//计算差多少秒
		//输出结果
		System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
		} catch (ParseException e) {
		e.printStackTrace();
		}
		return 0;
	}

	
	/**
	 * 功能描述:获取当前时间-1970年的秒数
	 *
	 * @author L H T  2014-5-5 下午04:26:36
	 *
	 * @return
	 */
	public static Long getCurrentSeconds(){
		return System.currentTimeMillis()/1000;
	}


    public static void main(String[] args) throws ParseException {
    	String str1 = "2017-12-10 17:30:30";
		String str2 = "2017-12-30 17:30:30";
        System.out.println(getMonthDays(str1, str2));   
    }
    
    /**
     * 功能描述:计算俩日期相差几个月
     * @author cqm  2017-8-18 下午06:02:02
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonthDays(String date1,String date2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
			bef.setTime(sdf.parse(date1));
		    aft.setTime(sdf.parse(date2));
	        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
	        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
	        return Math.abs(month + result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return 0;
    }
	
	/**
	 * 功能描述:获取俩日期之间的天数
	 * @author cqm  2017-8-18 下午05:45:49
	 * @param date1
	 * @param date2
	 * @return
	 */
    public static int differentDays(Date date1,Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
}
