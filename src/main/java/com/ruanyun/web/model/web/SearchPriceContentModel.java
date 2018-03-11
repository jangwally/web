package com.ruanyun.web.model.web;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchPriceContentModel {

    @JsonProperty("Market_type")
    private String Market_type;
    @JsonProperty("Exercise_mode")
    private String Exercise_mode;
    @JsonProperty("Price_mode")
    private String Price_mode;
    @JsonProperty("Insure_date")
    private String Insure_date;
    @JsonProperty("Symbols")
    private String[] Symbols;
    @JsonProperty("Option_type")
    private String Option_type;
    @JsonProperty("Expire_date")
    private String Expire_date;
    @JsonProperty("Min_option_price")
    private String Min_option_price;
    @JsonProperty("Max_option_price")
    private String Max_option_price;
    @JsonProperty("page")
    private Page page;
    @JsonProperty("Cycle")
    private String Cycle;

    public String getMarket_type() {
        return Market_type;
    }

    @JsonIgnore
    public void setMarket_type(String market_type) {
        Market_type = market_type;
    }

    @JsonIgnore
    public String getExercise_mode() {
        return Exercise_mode;
    }

    @JsonIgnore
    public void setExercise_mode(String exercise_mode) {
        Exercise_mode = exercise_mode;
    }

    @JsonIgnore
    public String getPrice_mode() {
        return Price_mode;
    }

    @JsonIgnore
    public void setPrice_mode(String price_mode) {
        Price_mode = price_mode;
    }

    @JsonIgnore
    public String getInsure_date() {
        return Insure_date;
    }

    @JsonIgnore
    public void setInsure_date(String insure_date) {
        Insure_date = insure_date;
    }

    @JsonIgnore
    public String[] getSymbols() {
        return Symbols;
    }

    @JsonIgnore
    public void setSymbols(String[] symbols) {
        Symbols = symbols;
    }

    @JsonIgnore
    public String getOption_type() {
        return Option_type;
    }

    @JsonIgnore
    public void setOption_type(String option_type) {
        Option_type = option_type;
    }

    @JsonIgnore
    public String getExpire_date() {
        return Expire_date;
    }

    @JsonIgnore
    public void setExpire_date(String expire_date) {
        Expire_date = expire_date;
    }

    @JsonIgnore
    public String getMin_option_price() {
        return Min_option_price;
    }

    @JsonIgnore
    public void setMin_option_price(String min_option_price) {
        Min_option_price = min_option_price;
    }

    @JsonIgnore
    public String getMax_option_price() {
        return Max_option_price;
    }

    @JsonIgnore
    public void setMax_option_price(String max_option_price) {
        Max_option_price = max_option_price;
    }

    @JsonIgnore
    public Page getPage() {
        return page;
    }

    @JsonIgnore
    public void setPage(Page page) {
        this.page = page;
    }

    @JsonIgnore
    public String getCycle() {
        return Cycle;
    }

    @JsonIgnore
    public void setCycle(String cycle) {
        Cycle = cycle;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
