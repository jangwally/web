package com.ruanyun.web.model;
// Generated 2016-12-1 16:38:47 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ruanyun.web.model.sys.TUser;

/**
 * 消息表
 */
@Entity
@Table(name="t_sms_info"
)
public class TSmsInfo  implements java.io.Serializable {


     private Integer smsInfoId;
     private String smsInfoNum;
     private String title;
     private String content;
     private String smsType;
     private Date createTime;
     private String sendUserNum;
     private String userNum;
     private String shopNum;
     private Integer status;
     private String flag1;
     private String flag2;
     private String flag3;
     private Integer userType;   //用户类型
    public TSmsInfo() {
    }

	
    public TSmsInfo(Date createTime) {
        this.createTime = createTime;
    }
    public TSmsInfo(String smsInfoNum, String title, String content, String smsType, Date createTime, String sendUserNum, String userNum, Integer status, String flag1, String flag2, String flag3) {
       this.smsInfoNum = smsInfoNum;
       this.title = title;
       this.content = content;
       this.smsType = smsType;
       this.createTime = createTime;
       this.sendUserNum = sendUserNum;
       this.userNum = userNum;
       this.status = status;
       this.flag1 = flag1;
       this.flag2 = flag2;
       this.flag3 = flag3;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="sms_info_id", unique=true, nullable=false)
    public Integer getSmsInfoId() {
        return this.smsInfoId;
    }
    
    public void setSmsInfoId(Integer smsInfoId) {
        this.smsInfoId = smsInfoId;
    }
    
    @Column(name="sms_info_num", length=100)
    public String getSmsInfoNum() {
        return this.smsInfoNum;
    }
    
    public void setSmsInfoNum(String smsInfoNum) {
        this.smsInfoNum = smsInfoNum;
    }
    
    @Column(name="title", length=100)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="content", length=500)
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="sms_type", length=100)
    public String getSmsType() {
        return this.smsType;
    }
    
    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
    
    


	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", nullable=false, length=19)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="send_user_num", length=100)
    public String getSendUserNum() {
        return this.sendUserNum;
    }
    
    public void setSendUserNum(String sendUserNum) {
        this.sendUserNum = sendUserNum;
    }
    
    @Column(name="user_num", length=100)
    public String getUserNum() {
        return this.userNum;
    }
    
    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
    
    
    @Column(name="shop_num", length=100)
    public String getShopNum() {
		return shopNum;
	}
	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}


	@Column(name="status")
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="flag1", length=100)
    public String getFlag1() {
        return this.flag1;
    }
    
    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }
    
    @Column(name="flag2", length=100)
    public String getFlag2() {
        return this.flag2;
    }
    
    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }
    
    @Column(name="flag3", length=100)
    public String getFlag3() {
        return this.flag3;
    }
    
    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }



	@Column(name="user_type")
	public Integer getUserType() {
		return userType;
	}


	public void setUserType(Integer userType) {
		this.userType = userType;
	}
    
	
}


