package com.ruanyun.web.dao.general;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.general.TOrgInfo;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository("orgInfoDao")
public class OrgInfoDao extends BaseDaoImpl<TOrgInfo> {



    /**
     * 功能描述: 后台，分页获取机构信息
     * 创建者: zhangwei
     * 创建时间: 2018/03/08 11:17
     * @param page 分页对象
     * @param orgInfo 机构对象
     * @param curUser 当前登录后台的用户
     * @param parentOrgValue 条件查询的上级机构代码
     * @return 包含所需信息的分页对象
     */
    public Page<TOrgInfo> getQueryPage(Page<TOrgInfo> page, TOrgInfo orgInfo, TUser curUser, String parentOrgValue){

        StringBuffer sql=new StringBuffer("select toi.*,u.login_name ,t2.org_name as parentName from t_org_info toi " +
                "left join t_user u on toi.user_num=u.user_num " +
                "left join t_org_info t2 on t2.org_code=toi.parent_code where 1=1");

        if(EmptyUtils.isNotEmpty(orgInfo)){
            sql.append(SQLUtils.popuHqlLike("toi.org_name",orgInfo.getOrgName()));
            sql.append(SQLUtils.popuHqlLike("toi.login_name",orgInfo.getLoginName()));
            sql.append(SQLUtils.popuHqlLike("toi.org_code",orgInfo.getOrgCode()));
        }

        //显示的机构类别
        if(EmptyUtils.isNotEmpty(orgInfo.getOrgType())){
            if(orgInfo.getOrgType()< Constants.ORG_TYPE_3){
                sql.append(SQLUtils.popuHqlEq("toi.org_type", orgInfo.getOrgType()));
            }else{
                sql.append(" and toi.org_type not in (1,2)");
            }
        }

        //查看自己及以下的机构信息
        if(EmptyUtils.isNotEmpty(curUser.getOrgCode())){
            sql.append(SQLUtils.popuHqlRLike("u.org_code", NumUtils.clearNum(curUser.getOrgCode(),4)));
        }


        //条件查询中的自己下级机构的代码
        if(EmptyUtils.isNotEmpty(parentOrgValue)){
            sql.append(SQLUtils.popuHqlRLike("u.org_code", NumUtils.clearNum(parentOrgValue,4)));
        }

        return sqlDao.queryPage(page,TOrgInfo.class,sql.toString());
    }


    /**
     * 功能描述：根据parentCode获取子机构的数目
     * @param parentCode
     * @return
     */
    public Integer getCountByParentCode(String parentCode) {

        StringBuffer sql = new StringBuffer(" select count(*) from t_org_info where 1=1 ");

        sql.append(SQLUtils.popuHqlEq("parent_Code",parentCode));

        Integer result = sqlDao.getCount(sql.toString());

        if (EmptyUtils.isEmpty(result)) {
            result = 0;
        }
        return result;
    }



    /**
     * 功能描述: 获取上级机构列表
     * 创建者: zhangwei
     * 创建时间: 2018/03/09 16:16
     * @param code 登录后台用户的机构代码
     * @return 当前登录用户下所有的机构list集合
     */
    public List<HashMap> getParentOrgList(String code) {
        StringBuffer sql = new StringBuffer();
        sql.append("select org_name as name,");
        sql.append("parent_code as pId,");
        sql.append("org_code as id,");
        sql.append("org_type as type ");
        sql.append(" from t_org_info where 1=1 ");
        if (EmptyUtils.isNotEmpty(code)) {
            sql.append(SQLUtils.popuHqlRLike("org_code", NumUtils.clearNum(code,4)));
        }
        sql.append(" order by org_type asc ");
        return sqlDao.getAll(sql.toString());
    }
}
