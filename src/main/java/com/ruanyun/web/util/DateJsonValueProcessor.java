package com.ruanyun.web.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateJsonValueProcessor implements JsonValueProcessor {

    private String format = "yyyy-MM-dd HH:mm:ss";

    public DateJsonValueProcessor() {
//            System.out.println("DateJsonValueProcessor");
    }

    public DateJsonValueProcessor(String format) {
//            System.out.println("DateJsonValueProcessor(String format)");
        this.format = format;
    }

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {

//            System.out.println("processArrayValue");

        String[] obj = {};
        if (value instanceof Date[]) {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date[] dates = (Date[]) value;
            obj = new String[dates.length];
            for (int i = 0; i < dates.length; i++) {
                obj[i] = sf.format(dates[i]);
            }
        }else if(value instanceof BigDecimal[]){
        	BigDecimal[] bigDecimals = (BigDecimal[]) value;
        	 obj = new String[bigDecimals.length];
        	 BigDecimal big=null;
             for (int i = 0; i < bigDecimals.length; i++) {
            	 obj[i]=bigDecimals[i]==null?"0":bigDecimals[i].toString();
             }
        }
        return obj;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {

        if (value instanceof Date) {
            String str = new SimpleDateFormat(format).format((Date) value);
            return str;
        }else if(value instanceof BigDecimal){
        	return value==null?"0":value.toString();
        }
        return value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
    public static void main(String[] args) {
		BigDecimal a=new BigDecimal("10.123");
		System.out.println(a.toString());
	}

}
