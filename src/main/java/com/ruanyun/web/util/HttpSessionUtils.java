package com.ruanyun.web.util;

import javax.servlet.http.HttpSession;

import com.ruanyun.web.model.sys.TUser;
 

public class HttpSessionUtils {
	
	/**
	 * 功能描述:把user对象保存在 session中
	 *
	 * @author yangliu  2013-9-11 下午03:09:01
	 * 
	 * @param session session对象
	 * @param user user对象
	 */
	public static void setUserToSession(HttpSession session,TUser user){
		session.setAttribute(Constants.SESSION_KEY_USER, user);
	}
	
	 
	
	/**
	 * 功能描述:把对象
	 *
	 * @author yangliu  2013-9-14 上午09:01:09
	 * 
	 * @param session
	 * @param key
	 * @param obj
	 */
	public static void setObjectToSession(HttpSession session,String  key,Object obj){
		session.setAttribute(key, obj);
	}
	
	/**
	 * 功能描述: 获取当前用户信息
	 *
	 * @author yangliu  2013-9-11 下午03:13:14
	 * 
	 * @param session session
	 * @return
	 */
	public static TUser getCurrentUser(HttpSession session){
		return (TUser) session.getAttribute(Constants.SESSION_KEY_USER);
	}
	
	 
	
	/**
	 * 功能描述:获取session中的值
	 *
	 * @author yangliu  2013-9-11 下午03:17:07
	 * 
	 * @param session session
	 * @param key 关键字
	 * @return
	 */
	public static TUser getSessionValue(HttpSession session,String key){
		return (TUser) session.getAttribute(key);
	}
	
	/**
	 * 功能描述: 删除session中的用户信息
	 *
	 * @author yangliu  2013-9-11 下午03:15:04
	 * 
	 * @param session session
	 */
	public static void removeSessionUser(HttpSession session){
		session.removeAttribute(Constants.SESSION_KEY_USER);
	}

	public static void removeSession(HttpSession session,String session_key){
		session.removeAttribute(session_key);
	}
}
