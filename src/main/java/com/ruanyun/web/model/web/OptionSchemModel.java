package com.ruanyun.web.model.web;

/**
 * 期权方案申请接口请求数据模型
 */
public class OptionSchemModel {
    //期权编码
    public String Option_code;
    //名义本金
    public Float Notional_principal;
    //行权价格方式
    public Integer Price_mode;
    //期权报价
    public Float Offer_price;
    //下单方式
    public Integer Order_type;
    //限价价格
    public Float Limit_price;

    public String getOption_code() {
        return Option_code;
    }

    public void setOption_code(String option_code) {
        Option_code = option_code;
    }

    public Float getNotional_principal() {
        return Notional_principal;
    }

    public void setNotional_principal(Float notional_principal) {
        Notional_principal = notional_principal;
    }

    public Integer getPrice_mode() {
        return Price_mode;
    }

    public void setPrice_mode(Integer price_mode) {
        Price_mode = price_mode;
    }

    public Float getOffer_price() {
        return Offer_price;
    }

    public void setOffer_price(Float offer_price) {
        Offer_price = offer_price;
    }

    public Integer getOrder_type() {
        return Order_type;
    }

    public void setOrder_type(Integer order_type) {
        Order_type = order_type;
    }

    public Float getLimit_price() {
        return Limit_price;
    }

    public void setLimit_price(Float limit_price) {
        Limit_price = limit_price;
    }
}
