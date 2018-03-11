package com.ruanyun.web.model.general;
// Generated 2018-3-8 11:07:52 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 基础股票信息类型
 */
@Entity
@Table(name="t_stock_info"
)
public class TStockInfo  implements java.io.Serializable {


     private int id;
     private String name;
     private String code;
     private String simpleSpell;
     private String allSpell;
     private Integer codeType;
     private Integer status;

    public TStockInfo() {
    }

	
    public TStockInfo(int id) {
        this.id = id;
    }
    public TStockInfo(int id, String name, String code, String simpleSpell, String allSpell, Integer codeType, Integer status) {
       this.id = id;
       this.name = name;
       this.code = code;
       this.simpleSpell = simpleSpell;
       this.allSpell = allSpell;
       this.codeType = codeType;
       this.status = status;
    }
   
     @Id
     @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="name", length=100)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="code", length=100)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="simple_spell", length=100)
    public String getSimpleSpell() {
        return this.simpleSpell;
    }
    
    public void setSimpleSpell(String simpleSpell) {
        this.simpleSpell = simpleSpell;
    }
    
    @Column(name="all_spell", length=100)
    public String getAllSpell() {
        return this.allSpell;
    }
    
    public void setAllSpell(String allSpell) {
        this.allSpell = allSpell;
    }
    
    @Column(name="code_type")
    public Integer getCodeType() {
        return this.codeType;
    }
    
    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }
    
    @Column(name="status")
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }




}


