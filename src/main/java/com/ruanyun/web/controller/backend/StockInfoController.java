package com.ruanyun.web.controller.backend;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.general.TStockInfo;
import com.ruanyun.web.service.general.StockInfoService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangwei
 * @description：
 * @create 2018-03-10 10:32
 **/
@Controller
@RequestMapping("stockinfo")
public class StockInfoController extends BaseController {


    @Autowired
    private StockInfoService stockInfoService;





    /**
     * 功能描述: 后台，获取股票数据列表
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 10:42
     * @param model 视图数据对象
     * @param page 分页对象
     * @param stockInfo 股票基础数据对象
     * @return 列表地址
     */
    @RequestMapping("list")
    public String list(Model model, Page<TStockInfo> page, TStockInfo stockInfo){

        addModel(model,"pageList",stockInfoService.getQueryPage(page,stockInfo));

        addModel(model,"bean",stockInfo);

        return "pc/backend/stockinfo/list";
    }



    /**
     * 功能描述: 后台，导入股票基础数据
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 10:36
     * @param response 响应对象
     * @param stockInfoFile excel表上传对象
     */
    @RequestMapping("importStockInfo")
    public void importStockInfo(HttpServletResponse response,  MultipartFile stockInfoFile) {

        if(EmptyUtils.isEmpty(stockInfoFile)){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "未读取到excel表", "", "", ""));
        }

        int result = stockInfoService.insertImportStockInfoList(stockInfoFile);

        if (result == Constants.GLOBAL_STATUS_1) {
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "", "stockinfo/list", "forward"));
        } else if (result == Constants.GLOBAL_STATUS_OWE_1) {
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, "导入失败，原因：保存数据发生异常", "", "", ""));
        } else if(result==Constants.GLOBAL_STATUS_0){
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "读取excel表数据失败，原因：格式不正确", "", "", ""));
        }else {
            super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
        }
    }
}
