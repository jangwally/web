package com.ruanyun.web.dao.sys;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dictionaryDao")
public class DictionaryDao  extends BaseDaoImpl<TDictionary> {
	
	public int updateAll(String oldName,String newCode,String newName){
		String sql="update TDictionary set parentName=?, parentCode=? where parentName=?";
		try{
		hqlDao.execute(sql,newName,newCode,oldName);
		return 1;
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * 功能描述：查询当前最大序号
	 * 
	 * @param
	 * @return
	 */
	public Integer totalNum(String parentCode) {
		String sql = "select Max(t.orderby+1) from TDictionary t where parentCode=?";
		try {
			return hqlDao.getCount(sql, parentCode);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取最大版本号
	 * @param parentCode
	 * @return
	 */
	public TDictionary totalVersion(String parentCode,String flag1) {
		String sql = "select * from T_Dictionary  where parent_Code='"+parentCode+"' and flag1='"+flag1+"'";
		return sqlDao.get(TDictionary.class,sql);
		 
	}
	
	/**
	 * 功能描述:根据父类编号分组 
	 * @author zhujingbo
	 * @return
	 */
	public List<TDictionary> getList(){
		String sql = "select  * from t_dictionary where status = 1 and is_read<>1 group by parent_code order by parent_code DESC";
		return sqlDao.getAll(TDictionary.class, sql);
	}

	public List<TDictionary> getQuoteModeList(String parentCode){
		String sql = "select * from T_Dictionary where parent_Code='"+parentCode+"'";
		return sqlDao.getAll(TDictionary.class, sql);
	}

	/**
	 * 查询字典表
	 * @param dictionary
	 * @return
	 */
	public List<TDictionary> getList(TDictionary dictionary){
//		StringBuffer hql = new StringBuffer("from TDictionary where status = 1");
		StringBuffer hql = new StringBuffer("from TDictionary where 1=1");
		if(EmptyUtils.isNotEmpty(dictionary)){
			hql.append(SQLUtils.popuHqlEq("parentCode", dictionary.getParentCode()));
			hql.append(SQLUtils.popuHqlEq("parentName", dictionary.getParentName()));
		}
		hql.append(" order by orderby DESC");
		return hqlDao.getAll(hql.toString());
	}

	/**
	 * 查询字典表
	 * @param dictionary
	 * @return
	 */
	public List<TDictionary> getListNew(TDictionary dictionary){
		StringBuffer hql = new StringBuffer("from TDictionary where 1=1 ");
		if(EmptyUtils.isNotEmpty(dictionary)){
			if (dictionary.getParentCode().indexOf(",") > -1) {
				hql.append(SQLUtils.popuHqlIn("parentCode", dictionary.getParentCode()));
			}else {
				hql.append(SQLUtils.popuHqlEq("parentCode", dictionary.getParentCode()));
			}
			hql.append(SQLUtils.popuHqlEq("parentName", dictionary.getParentName()));
		}
		hql.append(" order by orderby DESC");
		return hqlDao.getAll(hql.toString());
	}

	 
	
	
	public int getOrderby(TDictionary dictionary) {
		String sql = "select ifnull(max(orderby),1) from t_dictionary where parent_code='"+dictionary.getParentCode()+"'";
		return sqlDao.getCount(sql.toString());
	}

}
