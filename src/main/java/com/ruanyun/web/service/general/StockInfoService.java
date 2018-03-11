package com.ruanyun.web.service.general;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.general.StockInfoDao;
import com.ruanyun.web.model.general.TStockInfo;
import com.ruanyun.web.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwei
 * @description： 股票基础信息 服务类
 * @create 2018-03-10 9:57
 **/
@Service
public class StockInfoService extends BaseServiceImpl<TStockInfo>{


    @Autowired
    private StockInfoDao stockInfoDao;




    /**
     * 功能描述:分页，获取股票基础数据列表
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 10:40
     * @param page 分页对象
     * @param stockInfo 股票基础对象
     * @return 有数据的分页数据
     */
    public Page<TStockInfo> getQueryPage(Page<TStockInfo> page, TStockInfo stockInfo){
        return stockInfoDao.getQueryPage(page,stockInfo);
    }



    /**
     * 功能描述: 导入股票基础信息
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 10:30
     * @param stockPlanFile excel表上传对象
     * @return 0 获取excel信息失败  -1 保存数据失败 1 成功  其他 异常
     */
    public Integer insertImportStockInfoList(MultipartFile stockPlanFile) {
        //导入股票excel
        try {
            //excel数据
            String[] columns = new String[]{"股票代码","股票名称","名称简拼","名称全拼","股票类型","是否启用"};

            List<Map> list = ExcelUtils.readWorkbook(stockPlanFile.getInputStream(), columns);

            if (list.size() == 0){
                return 0;
            }

            stockInfoDao.updateTruncate();


            //循环导入
            for (int i = 0; i < list.size(); i++) {
                //首先要跳过第一行 题头
                if (i == 0){
                    continue;
                }

                Map map = list.get(i);

                TStockInfo stockInfo=createStockInfo(map);

                if(EmptyUtils.isEmpty(stockInfo)){
                    return -1;
                }

            }
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }




    /**
     * 功能描述: 根据导入的信息，创建股票基础信息
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 10:28
     * @param map excel表中的股票基础信息记录
     * @return 返回股票基础信息对象
     */
    private TStockInfo createStockInfo( Map map) {

        TStockInfo stockInfo=new TStockInfo();

        stockInfo.setCode((String) map.get("股票代码"));
        stockInfo.setName((String) map.get("股票名称"));
        stockInfo.setSimpleSpell((String) map.get("名称简拼"));
        stockInfo.setAllSpell((String) map.get("名称全拼"));
        stockInfo.setCodeType(Integer.parseInt((String) map.get("股票类型")));
        stockInfo.setStatus(Integer.parseInt((String) map.get("是否启用")));

        super.save(stockInfo);

        return stockInfo;
    }


}
