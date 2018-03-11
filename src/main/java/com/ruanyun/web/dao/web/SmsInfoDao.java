package com.ruanyun.web.dao.web;

import com.ruanyun.web.model.TSmsInfo;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;

import com.ruanyun.web.model.sys.TUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("smsInfoDao")
public class SmsInfoDao extends BaseDaoImpl<TSmsInfo> {
   /**
	* 查看分页查看所有短信信息；
 	* @param page
 	* @param smsInfo
 	* @param user
 	* @return
 	*/
	public Page<TSmsInfo> queryPage(Page<TSmsInfo> page,TSmsInfo smsInfo,TUser user){
		StringBuffer hql = new StringBuffer("select * from t_sms_info where 1=1");
		if(EmptyUtils.isNotEmpty(smsInfo)){
			hql.append(SQLUtils.popuHqlEq("sms_type", smsInfo.getSmsType()));
			//hql.append(SQLUtils.popuHqlMinDate("createTime", smsInfo.getStartTime()));
			hql.append(SQLUtils.popuHqlMaxDate("create_time", smsInfo.getCreateTime()));
			hql.append(SQLUtils.popuHqlEq("user_num", smsInfo.getUserNum()));
			hql.append(SQLUtils.popuHqlEq("shop_num", smsInfo.getShopNum()));
		}
		hql.append(" order by create_time DESC");
		return sqlDao.queryPage(page,TSmsInfo.class, hql.toString());
	}
	
	/**
	 * 功能描述:修改状态
	 * @author cqm  2016-12-8 下午03:15:24
	 * @param
	 * @param smsInfoNum
	 * @return
	 */
	public int updateSmsStatus(String smsInfoNum){
		String sqlString = "update t_sms_info set status=1 where sms_info_num='"+smsInfoNum+"'";
		return sqlDao.execute(sqlString);
	}
	
	/**
	 * 功能描述:更新消息
	 * @author cqm  2017-4-26 上午09:05:58
	 * @param userNum
	 * @param orderExpressNum
	 * @return
	 */
	public int updateSmsInfo(String userNum,String orderExpressNum,String title,String content){
		String sqlString = "update t_sms_info set title='"+title+"',content='"+content+"' where user_num='"+userNum+"' and order_express_num='"+orderExpressNum+"'";
		return sqlDao.execute(sqlString);
	}
	
	/**
	 * 功能描述:删除消息
	 * @author cqm  2016-12-19 上午09:05:00
	 * @param orderNum
	 * @return
	 */
	public Integer deleteSmsInfo(String orderNum){
		String sqlString = "delete from t_sms_info where common_num='"+orderNum+"'";
		return sqlDao.execute(sqlString);
	}

	@Override
	public List<TSmsInfo> getAll(TSmsInfo tSmsInfo) {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("  sms_info_id   AS smsInfoId,");
		sql.append("  sms_info_num  AS smsInfoNum,");
		sql.append("  title         AS title,");
		sql.append("  content       AS content,");
		sql.append("  sms_type      AS smsType,");
		sql.append("  create_time   AS createTime,");
		sql.append("  send_user_num AS sendUserNum,");
		sql.append("  user_num      AS userNum,");
		sql.append("  status        AS status,");
		sql.append("  shop_num      AS shopNum,");
		sql.append("  user_type     AS userType,");
		sql.append("  flag1         AS flag1,");
		sql.append("  flag2         AS flag2,");
		sql.append("  flag3         AS flag3");
		sql.append(" from t_sms_info ");
		sql.append("  where 1=1 ");
		if(EmptyUtils.isNotEmpty(tSmsInfo.getContent())){
			sql.append("  and  content = :content");
			map.put("content",tSmsInfo.getContent());
		}
		if(EmptyUtils.isNotEmpty(tSmsInfo.getSendUserNum())){
			sql.append("  and  send_user_num = :sendUserNum");
			map.put("sendUserNum",tSmsInfo.getSendUserNum());
		}
		if(EmptyUtils.isNotEmpty(tSmsInfo.getSmsType())){
			sql.append("  and  sms_type = :smsType");
			map.put("smsType",tSmsInfo.getSmsType());
		}
		sql.append(" order by createTime desc");
		return sqlDao.getAll(TSmsInfo.class,sql.toString(),map);
	}
}
