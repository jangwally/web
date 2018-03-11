package com.ruanyun.web.controller.sys;


import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.IBaseService;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.sys.TRole;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.sys.OperationLogService;
import com.ruanyun.web.service.sys.RoleService;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	

	@Autowired
	@Qualifier("userService")
	UserService userService;
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired
	private OperationLogService operationLogService;

	/**
	 * 功能描述:绑定时间
	 */
	@InitBinder
	public void initBinders(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
	
	/**
	 * 功能描述:用户列表
	 *
	 * @author yangliu  2013-9-16 上午09:17:34
	 * 
	 * @param tuser 用户信息
	 * @return
	 */
	@RequestMapping("/users")
	public String users(HttpSession session,TUser tuser,Page<TUser> page,Model model,Integer userType){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);

		addModel(model, "pageList",userService.queryPage(page, tuser,currentUser));
		addModel(model, "tuser", tuser);
		addModel(model, "userType", userType);

		return "pc/sys/user/list";

	}


	
	/**
	 * 功能描述:跳转到用户的添加和修改页面 
	 *
	 * @author L H T  2013-11-26 下午04:05:04
	 * 
	 * @param user 用户实体
	 * @param
	 * @return
	 */
	@RequestMapping("/toUserEdit")
	public String userAddOrEditJsp(TUser user,Model model,Integer userType){
		
		if (EmptyUtils.isNotEmpty(user.getUserId())) {
			//通过id查询用户信息
			user=userService.getUserById(user.getUserId());
			
			//通过用户id查询用户拥有的角色
			TRole  userRole =roleService.getRoleListByUserId(user.getUserId());
			addModel(model, "userRole", userRole);
		}
		addModel(model, "user",user );
		//查询所有角色
		List<TRole> allRoles=roleService.getAll(TRole.class,"orderby",IBaseService.ORDER_DESC);
		addModel(model, "allRoles", allRoles);
		addModel(model,"userType",userType);

			return "pc/sys/user/userEdit";
	}
	
	/**
	 * 功能描述://保存和修改用户
	 *
	 * @author L H T  2013-10-11 下午12:02:17
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/saveOrUpdate")
	public void saveOrUpdate(TUser user,HttpSession session,Integer roleId, HttpServletResponse response,Model model,HttpServletRequest request) throws Exception{
		//获取当前登录人的session信息
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);

		int result=userService.saveOrUpdate(currentUser, roleId, user,  request);
		if (result==1) {
			super.writeJsonData(response,CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "user/users?userType="+user.getUserType(), "forward"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	

	
	/**
	 * 功能删除：用户
	 * 
	 * zhujingbo 
	 */
	@RequestMapping("/del")
	public void userDel(HttpServletResponse response,String loginName,Integer userStatus,Integer userType,HttpSession session){
		TUser currentUser =HttpSessionUtils.getCurrentUser(session);
		if(userStatus!=1){
			int result = userService.delete(TUser.class, new String[]{"loginName","userType"}, new Object[]{loginName,userType});
			//操作日志
			operationLogService.addOperationLogThead(currentUser, "用户管理", "删除用户："
					+ currentUser.getLoginName() + "", "删除用户");
			if(result == 1){
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","user/users", "redirect"));
			}else{
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
			}
			
		}else {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE,"此用户非禁用状态，不可删除！！", "main_","user/users", "forward"));
		}
	}
	
	/***
	 * 
	 * ajax判断登录名是否存在
	 * 
	 */
	@RequestMapping("searchAjaxName")
	public void getUser(HttpServletResponse response,String loginName,Integer userType){
		String result = null;
		TUser info = userService.getAjaxLoginName(loginName,userType);
		if(info == null){
			result = "success";
		}else{
			result = "fail";
		}
		super.writeText(response,result);
	}
	/**
	 * 功能描述:跳转到修改个人信息页面
	 *
	 * @author L H T  2013-12-2 下午05:10:11
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("editPersion")
    public String showPersonalInfo(Model model,Integer userId){
    	//通过id查询用户信息
		TUser user=userService.getUserById(userId);
		addModel(model, "user", user);
		return "pc/sys/user/personlEdit";
	}
	/**
	 * 功能描述: 更新个人信息修改
	 *
	 * @author L H T  2013-12-2 下午05:26:27
	 * 
	 * @param model
	 * @param session
	 * @param response
	 * @param user
	 */
	@RequestMapping("updatePersonalInfo")
	public void updatePersonalInfo(Model model,HttpSession session,HttpServletResponse response,TUser user){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userService.updatePersonalInfo(user, currentUser);
		if (result==1) {
			String url="user/editPersion?userId="+user.getUserId();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "persion_edit", url, ""));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}


	/**
	 * 功能描述: 后台，跳转到修改当前登录用户密码（右上角“修改密码”）
	 * 创建者: zhangwei
	 * 创建时间: 2018/03/10 9:06
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("editpass")
	public String editPassword(Model model,Integer userId){
		//通过id查询用户信息
		TUser user=userService.getUserById(userId);
		addModel(model, "user", user);
		return "pc/sys/user/editPassword";
	}


	/**
	 * 功能描述: 后台，修改当前登录用户密码（右上角“修改密码”）
	 * 创建者: zhangwei
	 * 创建时间: 2018/03/10 9:06
	 * @param user
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("updatePass")
	public void updatePass(TUser user,HttpServletResponse response,HttpSession session){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userService.updatePass(user, currentUser);
		if (result==1) {
			//return REDIRECT +"/loginout";
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"密码修改成功！请重新登录...", "", "/loginout", "redirect"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));

		}
	}
	



}
