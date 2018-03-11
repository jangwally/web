package com.ruanyun.common.cache.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ruanyun.common.cache.SystemCacheService;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;

import com.ruanyun.web.model.sys.TDictionary;

@Service("publicCache")
public class PublicCache  implements SystemCacheService {
	private static Map<String, List<TDictionary>> map = new HashMap<String, List<TDictionary>>();
	private static String localMac="112121";
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	public String getCacheName() {
		return "加载T_Dictionary结束时间为:"+TimeUtil.getCurrentDay(SysCode.DATE_FORMAT_NUM_L);
	}

	public void run() {
		update();
	}

	public void update() {
		String mc=getMAC();
		System.out.println(mc);
		map.clear();
		map=getMap(getAllDic());

	}
	
	/**
	 * 功能描述:加载字典表中所有数据
	 *
	 * @author yangliu  2013-9-14 下午04:01:04
	 * 
	 * @return
	 */
	private List<TDictionary> getAllDic(){
		return	jdbcTemplate.query("select * from t_dictionary where status=1 order by orderby ", new BeanPropertyRowMapper<TDictionary>(TDictionary.class));
	}
	

	/**
	 * 功能描述: 根据父类信息把 封装到map中去
	 *
	 * @author yangliu  2013-9-14 下午04:11:28
	 * 
	 * @param allList 所有列表
	 * @return
	 */
	private  Map<String,List<TDictionary>> getMap(List<TDictionary> allList){
		Map<String,List<TDictionary>> map = new HashMap<String, List<TDictionary>>();
		List<TDictionary> childList=null;
		for(TDictionary dic : allList){
			childList=map.get(dic.getParentCode());
			if(childList==null){
				childList=new ArrayList<TDictionary>();
				map.put(dic.getParentCode(), childList);
			}
			childList.add(dic);
		}
		return map;
	}
	
	/**
	 * 功能描述: 获取 值
	 *
	 * @author yangliu  2013-9-16 上午08:59:47
	 * 
	 * @param parentCode 父类型
	 * @param itemCode 子类型
	 * @return
	 */
	public static String getItemName(String parentCode,String itemCode){
		if(EmptyUtils.isNotEmpty(parentCode) && EmptyUtils.isNotEmpty(itemCode)){
			List<TDictionary> list = map.get(parentCode);
			if(EmptyUtils.isEmpty(list))
				return null;
			for(TDictionary dic : list){
				if(dic.getItemCode().equals(itemCode))
					return dic.getItemName();
			}
		}
		return null;
	}
	
	/**
	 * 功能描述: 获取 值
	 *
	 * @author yangliu  2013-9-16 上午08:59:47
	 * 
	 * @param parentCode 父类型
	 * @param itemCode 子类型
	 * @return
	 */
	public static String getItemShortName(String parentCode,String itemCode){
		if(EmptyUtils.isNotEmpty(parentCode) && EmptyUtils.isNotEmpty(itemCode)){
			List<TDictionary> list = map.get(parentCode);
			if(EmptyUtils.isEmpty(list))
				return null;
			for(TDictionary dic : list){
				if(dic.getItemCode().equals(itemCode))
					return dic.getFlag1();
			}
		}
		return null;
	}
	
	/**
	 * 功能描述: 积分接口类型  ---通过flag1获取itemCode
	 *
	 * @author L H T  2014-6-5 下午03:38:26
	 * 
	 * @param parentCode
	 * @param flag
	 * @return
	 */
	public static String getItemCodeByFlag(String parentCode,String flag){
		if(EmptyUtils.isNotEmpty(parentCode) && EmptyUtils.isNotEmpty(flag)){
			List<TDictionary> list = map.get(parentCode);
			if(EmptyUtils.isNotEmpty(list)){
				for(TDictionary dic : list){
					if(flag.equals(dic.getFlag1()))
						return dic.getItemCode();
				}
			}
		}
		return null;
	}
	
	/**
	 * 功能描述: 获取 子类列表
	 *
	 * @author yangliu  2013-9-16 上午09:01:21
	 * 
	 * @param parentCode 父Code
	 * @return
	 */
	public static List<TDictionary> getItemList(String parentCode){
		return map.get(parentCode);
	}
	
	public  List<TDictionary> getListAll(){
		return getAllDic();
	}
	

	
	
	/**
	 * 功能描述: 获取 单个值
	 *
	 * @author yangliu  2013-9-16 上午08:59:47
	 * 
	 * @param parentCode 父类型

	 * @return
	 */
	public static String getItemName(String parentCode){
		if(EmptyUtils.isNotEmpty(parentCode)){
			List<TDictionary> list = map.get(parentCode);
			return list.get(0).toString();
		}
		return null;
	}



	public String getMAC() {
		StringBuffer sb = new StringBuffer();
		try {
			// 获取本地IP对象
			InetAddress ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			// 下面代码是把mac地址拼装成String
			for (int i = 0; i < mac.length; i++) {
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
		}
		catch (Exception ex) {
			System.out.println("windows 7方式未获取到网卡地址");
		}
		return sb.toString();
	}

}
