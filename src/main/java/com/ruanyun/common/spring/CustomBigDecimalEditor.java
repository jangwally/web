package com.ruanyun.common.spring;

import java.beans.PropertyEditorSupport;

import com.ruanyun.common.utils.CommonUtils;
import com.ruanyun.common.utils.EmptyUtils;
/**
 * 
 *  #(c) ruanyun czy <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明:  自定义转 BigDecimal
 * 
 *  <br/>创建说明: 2016年9月1日 下午3:59:39 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class CustomBigDecimalEditor extends PropertyEditorSupport{
	
	 public void setAsText(String text) throws IllegalArgumentException {  
	        if (EmptyUtils.isEmpty(text)) {  
	            setValue(null);  
	        } else {  
	            setValue(CommonUtils.getBigDecimal(text));  
	        }  
	    }  

}
