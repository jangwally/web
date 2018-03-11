package com.ruanyun.web.model.sys;
// Generated 2013-11-22 10:06:50 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TDictionary generated by hbm2java
 */
@Entity
@Table(name="t_dictionary"
)
public class TDictionary  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
     private String parentCode;
     private String parentName;
     private String itemCode;
     private String itemName;
     private Integer orderby;
     private Integer status;
     private String flag1;
     private Double versionCode;
     private Integer isRead;
     private String keyWord;

    public TDictionary() {
    }

    public TDictionary(String parentCode, String parentName, String itemCode, String itemName, Integer orderby, Integer status, String flag1) {
       this.parentCode = parentCode;
       this.parentName = parentName;
       this.itemCode = itemCode;
       this.itemName = itemName;
       this.orderby = orderby;
       this.status = status;
       this.flag1 = flag1;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="parent_code", length=20)
    public String getParentCode() {
        return this.parentCode;
    }
    
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    
    @Column(name="parent_name", length=200)
    public String getParentName() {
        return this.parentName;
    }
    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    @Column(name="item_code", length=50)
    public String getItemCode() {
        return this.itemCode;
    }
    
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    @Column(name="item_name", length=200)
    public String getItemName() {
        return this.itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    @Column(name="orderby")
    public Integer getOrderby() {
        return this.orderby;
    }
    
    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
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

    @Column(name="version_code")
	public Double getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Double versionCode) {
		this.versionCode = versionCode;
	}
	@Column(name="is_read")
	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	
	@Column(name="key_word")
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	
	
	
	

	



}


