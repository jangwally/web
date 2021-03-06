package com.ruanyun.web.model.general;
// Generated 2018-3-10 14:35:48 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TStockRatioOrg generated by hbm2java
 */
@Entity
@Table(name="t_stock_ratio_org"
)
public class TStockRatioOrg  implements java.io.Serializable {


     private Integer stockRatioOrgId;
     private String stockRatioOrgNum;
     private String orgInfoNum;
     private Integer orgType;
     private BigDecimal money;
     private String cycle;
     private BigDecimal priceRatio;
     private String symbol;
     private Integer status;

    public TStockRatioOrg() {
    }

    public TStockRatioOrg(String stockRatioOrgNum, String orgInfoNum, Integer orgType, BigDecimal money, String cycle, BigDecimal priceRatio, String symbol, Integer status) {
       this.stockRatioOrgNum = stockRatioOrgNum;
       this.orgInfoNum = orgInfoNum;
       this.orgType = orgType;
       this.money = money;
       this.cycle = cycle;
       this.priceRatio = priceRatio;
       this.symbol = symbol;
       this.status = status;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="stock_ratio_org_id", unique=true, nullable=false)
    public Integer getStockRatioOrgId() {
        return this.stockRatioOrgId;
    }
    
    public void setStockRatioOrgId(Integer stockRatioOrgId) {
        this.stockRatioOrgId = stockRatioOrgId;
    }
    
    @Column(name="stock_ratio_org_num", length=100)
    public String getStockRatioOrgNum() {
        return this.stockRatioOrgNum;
    }
    
    public void setStockRatioOrgNum(String stockRatioOrgNum) {
        this.stockRatioOrgNum = stockRatioOrgNum;
    }
    
    @Column(name="org_info_num", length=100)
    public String getOrgInfoNum() {
        return this.orgInfoNum;
    }
    
    public void setOrgInfoNum(String orgInfoNum) {
        this.orgInfoNum = orgInfoNum;
    }
    
    @Column(name="org_type")
    public Integer getOrgType() {
        return this.orgType;
    }
    
    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }
    
    @Column(name="money", precision=18)
    public BigDecimal getMoney() {
        return this.money;
    }
    
    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    
    @Column(name="cycle", length=100)
    public String getCycle() {
        return this.cycle;
    }
    
    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
    
    @Column(name="price_ratio", precision=18, scale=4)
    public BigDecimal getPriceRatio() {
        return this.priceRatio;
    }
    
    public void setPriceRatio(BigDecimal priceRatio) {
        this.priceRatio = priceRatio;
    }
    
    @Column(name="symbol", length=100)
    public String getSymbol() {
        return this.symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    @Column(name="status")
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }


}


