package com.ruanyun.web.controller.backend;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.general.TOrgInfo;
import com.ruanyun.web.model.sys.TDictionary;
import com.ruanyun.web.model.sys.TRole;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.general.OrgInfoService;
import com.ruanyun.web.service.sys.DictionaryService;
import com.ruanyun.web.service.sys.RoleService;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zhangwei
 * @description： 机构处理
 * @create 2018-03-08 10:52
 **/
@Controller
@RequestMapping("orginfo")
public class OrgInfoController extends BaseController{

    @Autowired
    private OrgInfoService orgInfoService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;




    @InitBinder
    public void initBinders(WebDataBinder binder) {
        super.initBinder(binder, "yyyy-MM-dd", true);
    }



    /**
     * 功能描述: 后台，分页获取机构信息
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 11:41
     * @param page 分页对象
     * @param orgInfo 机构对象
     * @param session session对象
     * @param model 视图对象
     * @param parentOrgName 条件查询的上级机构名称
     * @param parentOrgValue 条件查询的上级机构代码
     * @return 机构列表地址
     */
    @RequestMapping("list")
    public String list(HttpSession session, Model model,Page<TOrgInfo> page , TOrgInfo orgInfo,String parentOrgName,String parentOrgValue){
        TUser curUser = HttpSessionUtils.getCurrentUser(session);

        addModel(model, "pageList", orgInfoService.getQueryPage(page, orgInfo, curUser,parentOrgValue));

        addModel(model, "bean", orgInfo);

        addModel(model,"orgInfoList", JSONArray.fromObject(orgInfoService.getParentOrgList(curUser.getOrgCode())));

        addModel(model,"parentOrgName",parentOrgName);

        if(orgInfo.getOrgType()== Constants.ORG_TYPE_1){
            return "pc/backend/orginfo/yunying_list";
        }else if(orgInfo.getOrgType()==Constants.ORG_TYPE_2){
            return "pc/backend/orginfo/huiyuan_list";
        }else{
            return "pc/backend/orginfo/dailishang_list";
        }

    }



    /**
     * 功能描述: 后台，分页获取机构信息
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 22:21
     * @param model 视图对象
     * @param orgType 机构类型
     * @return 增加机构地址
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model,Integer orgType){

        addModel(model,"orgType",orgType);

        //获取自定义费率方式
        addModel(model,"stockRatioType",dictionaryService.getAllByCondition(TDictionary.class,"parentCode","STOCK_RATIO_TYPE"));

        //路径跳转
        if(orgType== Constants.ORG_TYPE_1){
            return "pc/backend/orginfo/yunying_add";
        }else if(orgType==Constants.ORG_TYPE_2){
            return "pc/backend/orginfo/huiyuan_add";
        }else{
            return "pc/backend/orginfo/dailishang_add";
        }
    }



    /**
     * 功能描述: 后台，增加机构
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 9:41
     * @param session 会话对象
     * @param response 响应对象
     * @param orgInfo 机构对象
     */
    @RequestMapping("add")
    public void add(HttpSession session, HttpServletResponse response, TOrgInfo orgInfo){

        TUser curUser=HttpSessionUtils.getCurrentUser(session);

        //判断机构登录名是否重复
        int checkRepeat=userService.getCheckRepeatForLoginName(orgInfo.getLoginName());

        if(checkRepeat==Constants.GLOBAL_STATUS_0){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"机构登录名重复", "main_","orginfo/list?orgType="+orgInfo.getOrgType(), "redirect"));
            return ;
        }

        //增加机构
        int result=orgInfoService.addOrgInfo(orgInfo,curUser);

        if(result==Constants.GLOBAL_STATUS_0){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"没有选择上级机构", "main_","orginfo/list?orgType="+orgInfo.getOrgType(), "forward"));
        }else if(result==Constants.GLOBAL_STATUS_OWE_1){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"不能再添加代理商，原因：已经是最后级代理", "main_","orginfo/list?orgType="+orgInfo.getOrgType(), "forward"));
        }else if(result==Constants.GLOBAL_STATUS_1){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","orginfo/list?orgType="+orgInfo.getOrgType(), "forward"));
        }else{
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_FAILED, "main_","orginfo/list?orgType="+orgInfo.getOrgType(), "forward"));
        }

    }



    /**
     * 功能描述: 查找带回
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 14:49
     * @param model 视图数据对象
     * @param orgInfo 机构对象
     * @param page 分页对象
     * @param session 会话对象
     * @return 查找带回地址，并加载所需的数据
     */
    @RequestMapping("findOrgBack")
    public String findOrgBack(Model model, TOrgInfo orgInfo, Page<TOrgInfo> page, HttpSession session) {
        TUser curUser = HttpSessionUtils.getCurrentUser(session);
        addModel(model, "bean", orgInfo);
        addModel(model, "pageList", orgInfoService.getQueryPage(page,orgInfo,curUser, null));
        return "pc/backend/orginfo/find_orgInfo";
    }



    /**
     * 功能描述:后台，跳转到配置权限页面
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 15:08
     * @param model 视图对象
     * @return 配置权限地址
     */
    @RequestMapping("toAddRole")
    public String toAddRole(Model model,String loginName,Integer orgType){

        addModel(model,"roleType",roleService.getAllByCondition(TRole.class,"type",2));

        addModel(model,"loginName",loginName);

        addModel(model,"orgType",orgType);

        return "pc/backend/orginfo/addUserRole";
    }



    /**
     * 功能描述:后台，配置权限页面
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 15:15
     * @param loginName 登录名
     * @param roleId 权限ID
     * @param orgType 机构类型
     */
    @RequestMapping("addRole")
    public void addRole(HttpServletResponse response,String loginName,Integer roleId,Integer orgType){

        int result=orgInfoService.addRoleForOrg(loginName,roleId);

        if(result==Constants.GLOBAL_STATUS_0){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"不是机构用户", "main_","orginfo/list?orgType="+orgType, "forward"));
        }else if(result==Constants.GLOBAL_STATUS_1){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","orginfo/list?orgType="+orgType, "forward"));
        }else{
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_FAILED, "main_","orginfo/list?orgType="+orgType, "forward"));
        }
    }


    /**
     * 功能描述: 重置机构用户密码（123456）
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 9:21
     * @param response 相应对象
     * @param userNum 用户编号
     * @param orgType 机构类型
     */
    @RequestMapping("resetPwd")
    public void updatePassWordForDefault(HttpServletResponse response,String userNum,Integer orgType){

        int result=userService.updatePassWordForDefault(userNum);

        if (result==Constants.GLOBAL_STATUS_1) {
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "", "orginfo/list?orgType="+orgType, "forward"));
        }else if(result==Constants.GLOBAL_STATUS_OWE_1){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"没有找到该用户", "", "", "redirect"));
        }else{
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));

        }
    }



    /**
     * 功能描述: 后台，跳转到修改机构昵称
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 9:45
     * @param model 视图对象
     * @param orgInfoNum 机构编号
     * @param orgType 机构类型
     * @return
     */
    @RequestMapping("toUpdateOrgName")
    public String toUpdateOrgName(Model model,String orgInfoNum,Integer orgType){

        if(EmptyUtils.isNotEmpty(orgInfoNum)){
            addModel(model,"bean",orgInfoService.get(TOrgInfo.class,"orgInfoNum",orgInfoNum));

        }

        addModel(model,"orgType",orgType);

        return "pc/backend/orginfo/updateOrgName";
    }



    /**
     * 功能描述: 后台，修改机构昵称，连带修改用户昵称
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 9:40
     * @param response 响应对象
     * @param orgInfoNum 机构编号
     * @param newOrgName 新的机构名称
     * @param orgType 机构类型
     */
    @RequestMapping("updateOrgName")
    public void updateOrgName(HttpServletResponse response,String orgInfoNum,String newOrgName,Integer orgType){

        int result=orgInfoService.updateOrgName(orgInfoNum,newOrgName);

        if (result==Constants.GLOBAL_STATUS_1) {
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "", "orginfo/list?orgType="+orgType, "forward"));
        }else if(result==Constants.GLOBAL_STATUS_OWE_1){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"获取机构信息失败", "", "", ""));
        }else if(result==Constants.GLOBAL_STATUS_2){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "修改用户昵称失败", "", "", ""));
        }else{
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));

        }
    }
}
