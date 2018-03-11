package com.ruanyun.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


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
	 * 给一个日期加上N月后或减去N月后得到的一个新日期
	 * 
	 * @param startDate
	 *            需要增加的日期时间
	 * @param addnos
	 *            添加的月数，可以是正数也可以是负数
	 * @return 操作后的日期
	 */
	public static Date addMonth(Date startDate, int addnos) {
		if (startDate == null)
			return null;
		Calendar cc = Calendar.getInstance();
		cc.setTime(startDate);
		cc.add(Calendar.MONTH, addnos);
		return cc.getTime();
	}
	
	/**
	 * 功能描述: 给日期加或减天
	 *
	 * @author yangliu  2016年9月13日 下午2:40:28
	 * 
	 * @param startDate 开始时间
	 * @param addnos 添加天数
	 * @return
	 */
	public static Date addDay(Date startDate, int addnos) {
		if (startDate == null)
			return null;
		Calendar cc = Calendar.getInstance();
		cc.setTime(startDate);
		cc.add(Calendar.DATE, addnos);
		return cc.getTime();

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
	
	/**
	 * 功能描述: 获取给定日期的所在月份的第一天
	 *
	 * @author yangliu  2016年9月1日 下午3:24:37
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthOfDayFirst(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return c.getTime();
	}
	
	/**
	 * 功能描述:获取给定日期的所在月份的最后一天
	 *
	 * @author yangliu  2016年9月1日 下午3:24:58
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthOfDayLast(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return c.getTime();
	}
	
	public static void main(String[] args) {
		String format="yyyyMMdd";
		Date str=DateUtils.doFormatDate("20150207", format);
		String firstDate=DateUtils.doFormatDate(getMonthOfDayFirst(str), format);
		String lastDate=DateUtils.doFormatDate(getMonthOfDayLast(str), format);
		System.out.println(firstDate);
		System.out.println(lastDate);
	}
	
	/** 
     * 计算两个日期之间相差的天数 
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
	 * @throws ParseException 
     */  
    public static int getDaysBetween(Date smdate,Date bdate)  {  
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		smdate=sdf.parse(sdf.format(smdate));
        	bdate=sdf.parse(sdf.format(bdate));
		} catch (Exception e) {
			e.printStackTrace();
		}
        Calendar cal = Calendar.getInstance();  
        cal.setTime(smdate);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(bdate);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);
          
       return Integer.parseInt(String.valueOf(between_days));         
    }  
    
	/**
	*字符串的日期格式的计算
	*/
    public static int getDaysBetween(String smdate,String bdate){
    	try {
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();  
            cal.setTime(sdf.parse(smdate));  
            long time1 = cal.getTimeInMillis();               
            cal.setTime(sdf.parse(bdate));  
            long time2 = cal.getTimeInMillis();       
            long between_days=(time2-time1)/(1000*3600*24);
              
           return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return 0;
    }

    /**
     * 功能描述:两日期比较   
     * @author wsp  2016-9-29 下午07:23:22
     * @param DATE1
     * @param DATE2
     * @return	DATE1>DATE2 为true   否则为false
     */
  public static boolean compareDate(String DATE1, String DATE2) {
	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
