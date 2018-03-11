package com.ruanyun.web.service.general;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.Exception.RuanYunException;
import com.ruanyun.web.dao.general.OrgInfoDao;
import com.ruanyun.web.model.general.TOrgInfo;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.sys.UserRoleService;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangwei
 * @description： 机构服务类
 * @create 2018-03-08 11:25
 **/
@Service
public class OrgInfoService extends BaseServiceImpl<TOrgInfo> {


    @Autowired
    private OrgInfoDao orgInfoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;



    /**
     * 功能描述:后台，分页获取机构信息
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 11:26

     * @param page    分页对象
     * @param orgInfo 机构对象
     * @param curUser 当前登录后台的用户
     * @param parentOrgValue
     * @return 包含所需信息的分页对象
     */
    public Page<TOrgInfo> getQueryPage(Page<TOrgInfo> page, TOrgInfo orgInfo, TUser curUser, String parentOrgValue) {
        return orgInfoDao.getQueryPage(page, orgInfo, curUser,parentOrgValue);
    }



    /**
     * 功能描述:增加机构
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 10:55
     * @param orgInfo 前端传递的机构对象
     * @param curUser 当前登录后台的用户对象
     * @return 1 成功 0 没有选择上级机构（在特殊的情况下） -1 不能再添加代理商
     */
    public Integer addOrgInfo(TOrgInfo orgInfo, TUser curUser) {

        if (orgInfo.getOrgType() == Constants.ORG_TYPE_1) {

            orgInfo.setParentCode(Constants.PARENT_CODE_DEFAULT);

        } else if (orgInfo.getOrgType() == Constants.ORG_TYPE_2) {

            if (EmptyUtils.isEmpty(orgInfo.getParentCode())) {
                //没有选择上级机构
                return 0;
            }

        }else if(orgInfo.getOrgType()==Constants.ORG_TYPE_3){
            //代理共分5级,一级代理商的上级机构为会员中心，二级代理商的上级机构为一级代理，以此类推。
            //只有当前端传递过来的上级机构代码为空时，添加的是一级代理
            if(EmptyUtils.isEmpty(orgInfo.getParentCode())){

                //上级机构默认为当前登录后台用户的代码
                orgInfo.setParentCode(curUser.getOrgCode());

            }else {
                //获取上级机构代码的机构用户
                TOrgInfo otherOrgInfo=getOrgForParentCode(orgInfo.getParentCode());

                if(otherOrgInfo.getOrgType()==Constants.ORG_TYPE_7){
                    //已经是最后以及代理了，不能在添加了
                    return -1;
                }

                //这里要重新设置机构类型，根据上级机构确定下级机构类型。
                orgInfo.setOrgType(otherOrgInfo.getOrgType()+1);
            }
        }

        //添加基本信息
        orgInfo.setCreateTime(new Date());
        orgInfo.setCreateUserNum(curUser.getUserNum());

        // 创建机构代码
        createOrgCode(orgInfo);

        super.save(orgInfo);

        //设置机构表用户编号
        orgInfo.setUserNum(NumUtils.getCommondNum(NumUtils.PIX_ORG_INFO_USER, orgInfo.getOrgInfoId()));

        //设置邀请码
        orgInfo.setCode(createCode(orgInfo.getOrgInfoId()));

        //设置机构表编号
        orgInfo.setOrgInfoNum(NumUtils.getCommondNum(NumUtils.PIX_ORG_INFO, orgInfo.getOrgInfoId()));

        //添加到用户表
        userService.addUser(orgInfo.getLoginName(), orgInfo.getOrgName(), orgInfo.getLoginPass(), orgInfo.getCreateUserNum(), orgInfo.getOrgCode(),Constants.USER_TYPE_2 , Constants.USER_SOURCE_ORG, orgInfo.getUserNum());

        return 1;
    }



    /**
     * 功能描述: 创建机构代码
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 14:20
     * @param orgInfo 机构对象
     */
    private void createOrgCode(TOrgInfo orgInfo) {
        //判断下级用户的数量
        Integer count = orgInfoDao.getCountByParentCode(orgInfo.getParentCode());

        //设置机构编号
        orgInfo.setOrgCode(NumUtils.getNum(count + 1, 4, 28, orgInfo.getParentCode(), Constants.PARENT_CODE_DEFAULT));
    }



    /**
     * 功能描述: 根据机构id创建邀请码，邀请码共六位，首位固定为1
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 14:27
     * @param orgInfoId 机构id
     * @return 完整的邀请码
     */
    private String createCode(Integer orgInfoId) {
        String code = String.format("%05d", orgInfoId);

        return Constants.FIRST_CODE_DEFAULT + code;
    }



    /**
     * 功能描述: 根据机构代码查找机构
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 22:11
     * @param orgCode 机构代码
     * @return 机构对象
     */
    private TOrgInfo getOrgForParentCode(String orgCode){
        TOrgInfo orgInfo=super.get(TOrgInfo.class,"orgCode",orgCode);

        if(EmptyUtils.isEmpty(orgInfo)){
            throw new RuanYunException("上级机构编号不存在或者填写有误");
        }

        return orgInfo;
    }



    /**
     * 功能描述: 增加机构用户后台登录权限
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 15:29
     * @param loginName 登录名
     * @param roleId 权限ID
     * @return 1 成功 0 不是机构用户 其他 异常
     */
    public Integer addRoleForOrg(String loginName ,Integer roleId){
        TUser user=super.get(TUser.class,"loginName",loginName);

        if(user.getUserType()==Constants.USER_TYPE_2){

            userRoleService.addUserRole(user.getUserId(),roleId);

            return 1;
        }
        return 0;
    }



    /**
     * 功能描述:获取上级机构列表
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 16:20
     * @param code 登录后台用户的机构代码
     * @return 当前登录用户下所有的机构list集合
     */
    public List<HashMap> getParentOrgList(String code) {
        return orgInfoDao.getParentOrgList(code);
    }



    /**
     * 功能描述: 修改机构名称
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 9:30
     * @param orgInfoNum 机构编号
     * @return 0 参数为空 -1  机构不存在  1 成功 2 修改用户信息失败 其他 异常
     */
    public Integer updateOrgName(String orgInfoNum,String newOrgName){

        if(EmptyUtils.isEmpty(orgInfoNum)){
            return 0;
        }

        TOrgInfo orgInfo=super.get(TOrgInfo.class,"orgInfoNum",orgInfoNum);

        if(EmptyUtils.isEmpty(orgInfo)){
            return -1;
        }

        orgInfo.setOrgName(newOrgName);

        super.update(orgInfo);

        int result=userService.updateNickName(orgInfo.getUserNum(),newOrgName);

        if(result!=Constants.GLOBAL_STATUS_1){
            return 2;
        }

        return 1;

    }
}
