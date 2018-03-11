package com.ruanyun.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.MD5Util;
import com.ruanyun.web.model.web.OptionModel;
import com.ruanyun.web.model.web.Page;
import com.ruanyun.web.model.web.SearchPriceContentModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 期货
 */
public class FuturesUtil {

    private static Logger logger = LoggerFactory.getLogger(FuturesUtil.class);

//    报价信息查询条件接口  QUERY_PRODUCT_INFO
//    期权方案申请接口  SUBMIT_ORDER
//    行权申请接口  APPLY_EXERCISE
//    查询成单信息接口  QUERY_ORDER
//    期权审核结果通知接口  ORDER_AUDIT_NOTIFY
//    开始到期行权通知接口  EXPIRE_ORDER_NOTIFY
//    行权结果通知接口  EXERCISE_RESULT_NOTIFY
    private static String KEY="2d106dbf30f255f3";
    public static List getFuture(OptionModel optionModel){
        optionModel.setMsgTime(DateUtils.doFormatDate(new Date(),"HHmmssSSS"));
        optionModel.setToken(MD5Util.encoderByMd5(optionModel.getMsgTime()+KEY));
        optionModel.setReqSource("GCS");
        String param= JSONObject.fromObject(optionModel).toString();
        String url="http://api.optionbj.com:8091/order?option="+param;
        String result=HttpRequestUtil.stringHttpRequest(url);
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(param);
        System.out.println(result);
        List list = jsonObject.getJSONObject("content").getJSONArray("Data_list");
        return list;
    }


    public static List<JSONObject> getFutureNew(OptionModel optionModel) {

        optionModel.setMsgTime(DateUtils.doFormatDate(new Date(), "HHmmssSSS"));
        optionModel.setToken(MD5Util.encoderByMd5(optionModel.getMsgTime() + KEY));
        optionModel.setReqSource("GCS");

        String paramNew = null;
        try {
            paramNew = new ObjectMapper().writeValueAsString(optionModel);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
//        String param = JSONObject.fromObject(optionModel, config).toString();
        String url = "http://api.optionbj.com:8091/order?option=" + paramNew;
        String result = "";
        try {
            result = HttpRequestUtil.stringHttpRequest(url);
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new RuntimeException("系统繁忙，请稍后再试！");
        }
        if(EmptyUtils.isEmpty(result)){
            throw new RuntimeException("系统繁忙，请稍后再试！");
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(paramNew);
        System.out.println(result);
        List list = jsonObject.getJSONObject("content").getJSONArray("Data_list");
        return list;
    }
}
