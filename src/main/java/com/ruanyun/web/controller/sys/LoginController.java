package com.ruanyun.web.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.UserLoginLogShow;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.sys.LoginLogService;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

@Controller
public class LoginController extends BaseController{
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	@Qualifier("loginLogService")
	private LoginLogService loginLogService;

	
	/**
	 * 管理员登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sadmin", method=RequestMethod.GET)
	public String toAdmin(Model model){
		return "pc/login2";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String toLogin(Model model){
		return "pc/login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String doLogin(String loginName,String password,HttpServletRequest request,Model model,HttpSession session,Integer type){
		String rand = (String) session.getAttribute("rand");
		Boolean isRand  = true;
		String checkCode = request.getParameter("checkCode");
		int result=userService.login(loginName, password, request,type.toString());
		/*if (EmptyUtils.isNotEmpty(checkCode) && EmptyUtils.isNotEmpty(rand)){
			isRand = rand.equals(checkCode.toUpperCase());//验证是否通过
		}else{
			result=-3;
		}
		if("NO".equals(checkCode)){
			isRand=true;
		}*/
		if (result == 1) {
			TUser User = HttpSessionUtils.getCurrentUser(session);
			if (Constants.USER_TYPE_99 == User.getUserType()) {
				result = -6;
			}
			if (!isRand) {
				result = -3;
			} else {
				if (result == SUCCESS) {
					//获得用户的登录信息
					TUser currentUser = HttpSessionUtils.getCurrentUser(session);
					UserLoginLogShow loginLogShow = loginLogService.getLoginLogByUserId(currentUser.getUserId());
					session.setAttribute("loginLogShow", loginLogShow);
					return REDIRECT + "/index";
				}
			}
		}
		addModel(model, "loginResult", result);
//		if(type==2){
//			return "pc/login";
//		}
//		if(type==1){
//			return "pc/login";
//		}
		return "pc/login";
		
	}
	
	/**
	 * 功能描述:退出当前系统
	 *
	 * @author lht  2013-9-5 上午09:39:16
	 * 
	 * @return
	 */
	@RequestMapping("/loginout")
	public String loginOut(HttpSession session,Integer userType){
		HttpSessionUtils.removeSessionUser(session);												
		session.removeAttribute(Constants.SEESION_KEY_LEFTURLS);//是清除菜单SESSION
		session.invalidate();//是让SESSION失效. 
		return REDIRECT+"/login";
	}
	
	@RequestMapping(value="download", method=RequestMethod.GET)
	public String download(Model model){
		return "pc/download";
	}

}
