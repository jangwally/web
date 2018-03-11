package com.ruanyun.web.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
public class TimerUtiles {
    public static void main(String[] args) {
        timer1();
        //timer2();
        //timer3();
        //timer4();
    }
    // 第一种方法：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)
    public static void timer1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, 2000);// 设定指定的时间time,此处为2000毫秒
    }
    // 第二种方法：设定指定任务task在指定延迟delay后进行固定延迟peroid的执行
    // schedule(TimerTask task, long delay, long period)
    public static void timer2() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, 1000, 5000);
    }
    // 第三种方法：设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
    // scheduleAtFixedRate(TimerTask task, long delay, long period)
    public static void timer3() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, 1000, 2000);
    }

    // 第四种方法：安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．
    // Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)
    public static void timer4() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制时
        calendar.set(Calendar.MINUTE, 0);       // 控制分
        calendar.set(Calendar.SECOND, 0);       // 控制秒
        Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
    
    /**
     * 功能描述:获取当前系统时间加上多少天的日期
     * @author cqm  2017-4-18 上午11:53:22
     * @param day
     * @return
     * @throws ParseException 
     */
    public static Date getDateDays(Integer day) throws ParseException {
     	  Date d = new Date();
    	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Calendar ca = Calendar.getInstance();
    	  ca.add(Calendar.DATE, day);// 30为增加的天数，可以改变的
    	  d = ca.getTime();
    	  String backTime = format.format(d);
          return format.parse(backTime);
    }
    
    /**
     * 功能描述:根据某个时间加上的天数 返回日期
     * @author cqm  2017-4-18 下午02:53:33
     * @param dates
     * @param day
     * @return
     * @throws ParseException
     */
	public static Date getDate(String dates,Integer day) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
		Date date = dateFormat.parse(dates); // 指定日期
		Date newDate = addDate(date, day); // 指定日期加上20天
		return newDate;
		}


	public static Date addDate(Date date,long day) throws ParseException {
		 long time = date.getTime(); // 得到指定日期的毫秒数
		 day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
		 time+=day; // 相加得到新的毫秒数
		 return new Date(time); // 将毫秒数转换成日期
	}
		
    
}