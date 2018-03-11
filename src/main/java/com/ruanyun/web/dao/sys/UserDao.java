package com.ruanyun.web.dao.sys;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.util.NumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.sys.DictionaryService;
import com.ruanyun.web.util.Constants;


@Repository("userDao")
public class UserDao extends BaseDaoImpl<TUser>{
	
	@Autowired
	private DictionaryService dictionaryService;

	@Override
	protected String queryPageSql(TUser tuser, Map<String, Object> params) {

		//and userStatus=1
			StringBuffer  sql = new StringBuffer("from TUser where 1=1 ");
			if(tuser!=null){
			    sql.append(SQLUtils.popuHqlLike("nickName", tuser.getNickName(),params));
				sql.append(SQLUtils.popuHqlLike("loginName",tuser.getLoginName(),params));
				sql.append(SQLUtils.popuHqlMin("createTime",tuser.getCreateTime(),params));
				sql.append(SQLUtils.popuHqlMax("createTime",tuser.getEndDate(),params));
				sql.append(SQLUtils.popuHqlEq("userType",tuser.getUserType(),params));
				sql.append(SQLUtils.popuHqlEq("userStatus",tuser.getUserStatus(),params));


			}
			sql.append(" ORDER BY createTime DESC");
		return sql.toString();
	}

	/**
	 * 功能描述：获取未审核的技师或店铺用户
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<TUser> getList(Page<TUser> page, TUser user) {
		StringBuffer hql = new StringBuffer("from TUser where 1=1 ");
		if (EmptyUtils.isNotEmpty(user)) {
			hql.append(SQLUtils.popuHqlEq("userNum", user.getUserNum()));
			hql.append(SQLUtils.popuHqlLike("nickName", user.getNickName()));
			hql.append(SQLUtils.popuHqlMin("createTime", user.getStartTime()));
			hql.append(SQLUtils.popuHqlMax("createTime", user.getCreateTime()));
			hql.append(SQLUtils.popuHqlEq("userType", user.getUserType()));
		}
		return hqlDao.queryPage(page, hql.toString());
	}

	/**
	 * 功能描述:ajax判断登录名称是否重复
	 *
	 * @author L H T  2013-11-26 下午06:24:41
	 * 
	 * @param loginName 登录名称
	 * @return
	 */
	public TUser getAjaxLoginName(String loginName,Integer userType){
		String sql = "from TUser where loginName = '"+loginName+"'";
		return hqlDao.get(sql.toString());
	}
	
	public int getUserCount(Integer orgId){
		String sql = "select count(*) from t_user where org_id = ?";
		return sqlDao.getCount(sql, orgId);
		
	}
	/**
	 * 功能描述:通过用户id查询用户的名称
	 *
	 * @author L H T  2013-12-2 下午02:30:11
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserNameById(Integer userId){
		return hqlDao.get("select loginName from TUser where userId=? ", userId);
	}
	
	/**
	 * 功能描述:根据账户查询信息
	 * @author cqm  2016-12-29 上午09:58:40
	 * @param loginName
	 * @return
	 */
	public TUser getLoginName(String  loginName){
		return hqlDao.get("select userNum from TUser where loginName=? ", loginName);
	}
	/**
	 * 功能描述:通过组织org_Code查询用户
	 *
	 * @author L H T  2013-12-18 下午03:34:31
	 * 
	 * @param orgId
	 * @return
	 */
	public List<TUser> getUserByOrgId(Integer orgId){
		return hqlDao.getAll("from TUser where orgId=? and userStatus=1", orgId);
	}
	 
	
	/**
	 * 功能描述: 根据用户编号获取用户信息 返回值为map key为userNum value 为 user对象
	 *
	 * @author yangliu  2016年9月5日 上午10:48:16
	 * 
	 * @param userNums 用户编号格式为 A,B,C,D
	 * @return
	 */
	public Map<String,TUser> getUserByUserNums(String userNums){
		final Map<String,TUser> map = new HashMap<String, TUser>();
		String sql ="select * from t_user where user_num in ("+SQLUtils.sqlForIn(userNums)+")";
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String userNum=rs.getString("user_num");
				TUser user=new TUser();
				user.setUserNum(userNum);
				user.setNickName(rs.getString("nick_name"));
				user.setLoginName(rs.getString("login_name"));
				map.put(userNum, user);
			}
		});
		return map;
	}



	/**
	 * 功能描述：清除设备号
	 * @author cqm  2017-8-22 下午03:12:27
	 * @param registrationId
	 * @param userType
	 * @return
	 */
	 public int updateRegistrationId(String registrationId,Integer userType){
		 StringBuffer hql = new StringBuffer("update t_user set registration_id=Null  where user_type="+userType+"  and registration_id='"+registrationId+"' ");
		 return sqlDao.update(hql.toString());
	 }
	
	 public int updateRegistrationId(String registrationId,Integer userType,String userNum){
		 StringBuffer hql = new StringBuffer("update t_user set registration_id='"+registrationId+"'  where user_type="+userType+"  and user_num='"+userNum+"' ");
		 return sqlDao.update(hql.toString());
	 }
	
	public int updateQuery(String filedValue,String filed,String queryFiledValue,String tableName,String queryFiled){
		String sqls= "update "+tableName+" set "+filed+" = "+filedValue+" where "+queryFiled+"='"+queryFiledValue+"'";
		sqlDao.update(sqls);
		return 1;
	}

	
	public TUser getUserInfo(String loginName,String userType){
		if(userType.equals("1")){
			userType ="(1,5)";
		}else if(userType.equals("3")){
			userType="(3)";
		}else {
			userType ="(2)";
		}
		String sql = "select * from t_user where login_name='"+loginName.trim()+"' and user_status="+Constants.GLOBAL_STATUS+" and user_type in "+userType+"";
		return sqlDao.get(TUser.class,sql);
	}

	/**
	 * 重新写上面的登录
	 * @param loginName
	 * @param userType
	 * @return
	 */
	public TUser getUserInfomation(String loginName,String userType){

		String sql = "select * from t_user where login_name='"+loginName.trim()+"' and user_status="+Constants.GLOBAL_STATUS;
		return sqlDao.get(TUser.class,sql);
	}



	/**
	 * 功能描述: 检查用户登录帐号是否重复
	 * 创建者: zhangwei
	 * 创建时间: 2018/03/09 10:44
	 * @param loginName 登录名
	 * @return 当前登录名的数量
	 */
	public Integer getCheckRepeatForLoginName(String loginName){

		StringBuffer sql=new StringBuffer("select count(1) from t_user where login_name='"+loginName+"'");

		return sqlDao.getCount(sql.toString());

	}

}
