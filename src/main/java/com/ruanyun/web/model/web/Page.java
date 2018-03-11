package com.ruanyun.web.model.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Page {

    @JsonProperty("Page_size")
    private String Page_size;

    @JsonProperty("Page_no")
    private String Page_no;


    public Page() {
        this.setPage_no("1");
        setPage_size("10");
    }

    public Page(String page_size, String page_no) {
        super();
        Page_size = page_size;
        Page_no = page_no;
    }

    @JsonIgnore
    public String getPage_size() {
        return Page_size;
    }

    @JsonIgnore
    public void setPage_size(String page_size) {
        Page_size = page_size;
    }

    @JsonIgnore
    public String getPage_no() {
        return Page_no;
    }

    @JsonIgnore
    public void setPage_no(String page_no) {
        Page_no = page_no;
    }

    @Override
    public String toString() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Page_size", getPage_size());
        map.put("Page_no", getPage_no());
        String result = JSONObject.fromObject(map).toString();
        System.out.println(result);
        return result;
    }

}
