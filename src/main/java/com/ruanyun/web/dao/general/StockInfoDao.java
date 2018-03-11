package com.ruanyun.web.dao.general;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.general.TStockInfo;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

/**
 * @author zhangwei
 * @description：
 * @create 2018-03-10 9:57
 **/
@Repository
public class StockInfoDao extends BaseDaoImpl<TStockInfo>{


    /**
     * 功能描述:分页，获取股票基础数据列表
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 10:39
     * @param page 分页对象
     * @param stockInfo 股票基础对象
     * @return 有数据的分页数据
     */
    public Page<TStockInfo> getQueryPage(Page<TStockInfo> page, TStockInfo stockInfo){

        StringBuffer sql=new StringBuffer("select * from t_stock_info where 1=1");

        if(EmptyUtils.isNotEmpty(stockInfo)){
            sql.append(SQLUtils.popuHqlLike("code",stockInfo.getCode()));
            sql.append(SQLUtils.popuHqlLike("name",stockInfo.getName()));
        }

        sql.append(" order by code asc");

        return sqlDao.queryPage(page,TStockInfo.class,sql.toString());
    }



    /**
     * 功能描述: 清空股票基础信息数据
     * 创建者: zhangwei
     * 创建时间: 2018/03/10 10:00
     */
    public void updateTruncate(){

        SQLQuery query = sqlDao.createQuery("TRUNCATE TABLE t_stock_info; ");

        query.executeUpdate();

    }
}
