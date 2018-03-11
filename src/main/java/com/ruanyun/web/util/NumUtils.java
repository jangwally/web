package com.ruanyun.web.util;

import com.ruanyun.common.utils.EmptyUtils;

public class NumUtils {


	
	/**
	 * 敏感字属性关联
	 */
	public final static String PIX_SENSITIVE_WORDS="SW";
	

	/**
	 * 首页模块
	 */
	public final static String PIX_TYPE_INFO = "TI";

	/**
	 * 普通用户
	 */
	public final static String PIX_USER_NUM="UM";


	/**
	 * 机构编号
	 */
	public final static String PIX_ORG_INFO="OI";
	/**
	 * 机构用户
	 */
	public final static String PIX_ORG_INFO_USER="OIU";


	/**
	 * 功能描述:机构编码
	 *
	 * @author yangliu  2015年11月26日 下午4:41:43
	 * 
	 * @param cityBh 城市编号
	 * @param id 主键
	 * @return
	 */
	public static String getOrgNum(String cityBh,int id){
		return "O_"+cityBh.substring(0,6)+String.format("%06d", id);
	}
	
	public static String getDayTime(String str){
		String str1=str.substring(8, 10);;
		Integer day=Integer.valueOf(str1)+1;
		String newStr=str.substring(0, 8)+day.toString();
		return newStr;
	}
	
	/**
	 * 功能描述: 获取编号
	 *
	 * @author yangliu  2015年11月26日 下午5:15:32
	 * 
	 * @param pix 前缀
	 * @param id 主键
	 * @return
	 */
	public static String getCommondNum(String pix,int id){
		return pix+String.valueOf((int)(Math.random()*9000+1000))+String.format("%010d", id);
	}
	
	public static String getNum(String pix,int id){
		return pix+String.valueOf((int)(Math.random()*90+10))+String.format("%06d", id);
	}
	
	/**
	 * 获取订单编号
	 * @param pix
	 * @param id
	 * @return
	 */
	public static String getOrderNum(String pix,String date,int id,Integer orderType){
		return orderType+pix+date+String.valueOf((int)(Math.random()*90+10))+String.format("%06d", id);
	}
	
	public static String getOrderNum(String pix,String date,int id){
		return pix+date+String.valueOf((int)(Math.random()*90+10))+String.format("%06d", id);
	}
	
	/**
	 * 功能描述: 获取编号
	 *
	 * @author yangliu  2015年11月26日 下午5:15:32
	 * 
	 * @param pix 前缀
	 * @param id 主键 
	 * @return
	 */
	public static String getCommondNum(String pix,Long id){
		return pix+String.valueOf((int)(Math.random()*10000))+String.format("%010d", id);
	}
	
	/**
	 * 功能描述: 获取有上下级关系的编号 例如 110000 与110100 和 110101 三个有上下级关系
	 *
	 * @author yangliu  2016年8月6日 上午9:53:17
	 * 
	 * @param childNum 子类数量
	 * @param childLength 子类长度 例如 2 表示两位为一类
	 * @param lengthAll 总长度
	 * @param parentCode 父类编号
	 * @param defuatParentCode 默认父类编号 也就是一级的时候 父类编号是多少
	 * @return
	 */
	public static String getNum(Integer childNum,int childLength,int lengthAll,String parentCode,String defuatParentCode){
		String str=String.format("%0"+childLength+"d", childNum);
		String pixStr=String.format("%0"+childLength+"d", 0);
		if(defuatParentCode.equals(parentCode)||EmptyUtils.isEmpty(parentCode))
			return str+String.format("%0"+(lengthAll-childLength)+"d", 0);
		String childrenCode="";
		for(int i=1;i<childLength;i++){
			childrenCode=parentCode.substring(i*childLength,(i+1)*childLength);
			if(pixStr.equals(childrenCode)) {
                String result = parentCode.substring(0, i * childLength) + str + parentCode.substring((i + 1) * childLength);
                return result;
			}
		}
		return parentCode.replaceFirst(pixStr, str);
	}

	
	/**
	 * 功能描述: 清零
	 *
	 * @author yangliu  2016年9月1日 上午11:43:35
	 * 
	 * @param num
	 * @param length
	 * @return
	 */
	public static String clearNum(String num,int length){
		if(EmptyUtils.isEmpty(num)|| length<1)
			return num;
		String str=String.format("%0"+length+"d", 0);
		while(true){
			if(num.endsWith(str)){
				num=num.substring(0, num.length()-length);
			}else{
				break;
			}
		}
		return num;
	}

	public  static  void main(String [] args){
		System.out.println(clearNum("0001000100000000",4));
	}
}
