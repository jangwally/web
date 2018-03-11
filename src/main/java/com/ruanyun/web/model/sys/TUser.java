package com.ruanyun.web.model.sys;
// Generated 2013-11-22 9:55:13 by Hibernate Tools 3.2.2.GA


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * 用户基础信息
 */
@Entity
@Table(name = "t_user"
)
public class TUser implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String userNum;
    private String nickName;
    private String loginName;
    private String loginPass;

    private String createUserNum;
    private Date createTime;
    private Integer userStatus;
    private String userCode;
    private Integer userType;

    private Integer bindStatus;

    private Date startTime;

    private Integer userSource;

    /**
     * 用户类型 在登录时候设置
     */
    private String requestType;

    /**
     * 所属机构编号，普通用户表示的是所属的机构编号，机构用户表示的是自己的编号
     */
    private String orgCode;

    /**
     * 角色
     */
    private TRole role;

    private TUserRole userRole;
    private Integer roleId;
    /**
     * 权限
     */
    private List<TAuthority> auths = new ArrayList<TAuthority>(0);
    /**
     * url
     */
    private List<TAuthority> urls = new ArrayList<TAuthority>(0);
    /**
     * 手机用户登录返回信息
     */
    private String loginError;

    /**
     * 结束时间
     */
    public Date endDate;


    public TUser() {
    }

    public TUser(String loginName, String nickName, String loginPass, String createUserNum, Date createTime, Integer userStatus, String userPhoto) {
        this.loginName = loginName;
        this.nickName = nickName;
        this.loginPass = loginPass;


        this.createUserNum = createUserNum;
        this.createTime = createTime;
        this.userStatus = userStatus;

    }

    public TUser(String userNum, String nickName, String loginName, String loginPass, String userPhone, String createUserNum, Date createTime, Integer userStatus, String userPhoto, Integer userType, Integer bindStatus) {
        this.userNum = userNum;
        this.nickName = nickName;
        this.loginName = loginName;
        this.loginPass = loginPass;

        this.createUserNum = createUserNum;
        this.createTime = createTime;
        this.userStatus = userStatus;
        this.userType = userType;


        this.bindStatus = bindStatus;
    }

    public TUser(String userNum) {
        super();
        this.userNum = userNum;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "user_id", unique = true, nullable = false)
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "login_name", length = 100)
    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "nick_name", length = 100)
    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "login_pass", length = 100)
    public String getLoginPass() {
        return this.loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    @Column(name = "create_user_num")
    public String getCreateUserNum() {
        return this.createUserNum;
    }

    public void setCreateUserNum(String createUserNum) {
        this.createUserNum = createUserNum;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "user_source")
    public Integer getUserSource() {
        return userSource;
    }

    public void setUserSource(Integer userSource) {
        this.userSource = userSource;
    }


    @Column(name = "user_status")
    public Integer getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Transient
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Transient
    public TRole getRole() {
        return role;
    }

    public void setRole(TRole role) {
        this.role = role;
    }

    @Transient
    public List<TAuthority> getAuths() {
        return auths;
    }

    public void setAuths(List<TAuthority> auths) {
        this.auths = auths;
    }

    @Transient
    public List<TAuthority> getUrls() {
        return urls;
    }

    public void setUrls(List<TAuthority> urls) {
        this.urls = urls;
    }

    @Transient
    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    @Transient
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    @Column(name = "user_type")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Column(name = "user_num")
    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }


    @Column(name = "bind_status")
    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }


    @Transient
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }


    private String flag1;
    private String flag2;
    private String flag3;

    @Transient
    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    @Transient
    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    @Transient
    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    private TUser user;

    @Transient
    public TUser getUser() {
        return user;
    }

    public void setUser(TUser user) {
        this.user = user;
    }

    @Transient
    public TUserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(TUserRole userRole) {
        this.userRole = userRole;
    }

    @Transient
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    @Column(name = "org_code",length = 100)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}


