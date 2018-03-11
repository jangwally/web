package com.ruanyun.web.service.sys;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.Exception.RuanYunException;
import com.ruanyun.web.dao.sys.DictionaryDao;
import com.ruanyun.web.model.sys.TDictionary;
import com.ruanyun.web.model.sys.TUser;
 

@Service("dictionaryService")
public class DictionaryService extends BaseServiceImpl<TDictionary> {

	@Autowired
	@Qualifier("dictionaryDao")
	private DictionaryDao dictionaryDao;

	public int addOrupdate(Integer parentId,TDictionary dictionary2){
		if(EmptyUtils.isEmpty(dictionary2.getId())){
			if(EmptyUtils.isNotEmpty(parentId)){
				TDictionary dictionary=get(TDictionary.class, parentId);
				if(EmptyUtils.isEmpty(dictionary.getItemCode())){
					dictionary.setItemName(dictionary2.getItemName());
					dictionary.setItemCode(dictionary2.getItemCode());
					dictionary.setOrderby(dictionary2.getOrderby());
					update(dictionary);
				}else{
				dictionary2.setParentCode(dictionary.getParentCode());
				dictionary2.setParentName(dictionary.getParentName());
				save(dictionary2);
				}
			}else{
				save(dictionary2);
			}
		}else{
			update(dictionary2);
		}
		return 1;
	}
	
	/**
	 * 功能描述:获取字典详情
	 * @author cqm  2017-5-16 下午02:18:53
	 * @param parentCode
	 * @param isRequired
	 * @return
	 */
	public TDictionary getDictionary(String parentCode,boolean isRequired){
		TDictionary dictionary = get(TDictionary.class, "parentCode", parentCode);
		if(isRequired && dictionary==null){
			throw new RuanYunException("字典信息不存在");
		}
		return dictionary;
	}


	/**
	 * 功能描述：查询当前最大序号
	 * 
	 * @param
	 * @return
	 */
	public Integer totalNum(String parentCode) {
		return dictionaryDao.totalNum(parentCode);
	}
	
	/**
	 * 获取最大版本号
	 * @param parentCode
	 * @return
	 */
	public TDictionary totalVersion(String parentCode,String flag1) {
		return dictionaryDao.totalVersion(parentCode,flag1);
	}
	
	
	
	/**
	 * 更新版本号
	 * @param parentCode
	 * @return
	 */
	public Integer updateVersion(String parentCode,String flag1){
		java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");  
		TDictionary bean = dictionaryDao.get(TDictionary.class,new String[]{"parentCode","flag1"},new Object[]{parentCode,flag1} );
		try {
			
			bean.setItemCode(df.format((Double.parseDouble(totalVersion(parentCode,flag1).getItemCode())+0.01))+"");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dictionaryDao.update(bean);
		return 1;
	}
	
	public int addOrupdate(TDictionary dictionary){
		if(EmptyUtils.isNotEmpty(dictionary)){
			if(EmptyUtils.isNotEmpty(dictionary.getId())){
				dictionaryDao.update(dictionary);
			}else{
				dictionaryDao.save(dictionary);
			}
		}
		return 1;
	}
	/**
	 * 功能描述: 根据父类信息把 封装到map中去
	 *
	 * @author yangliu  2013-9-14 下午04:11:28
	 * 
	 * @param allList 所有列表
	 * @return
	 */
	public  Map<String,List<TDictionary>> getMap(List<TDictionary> allList){
		Map<String,List<TDictionary>> map = new HashMap<String, List<TDictionary>>();
		List<TDictionary> childList=null;
		for(TDictionary dic : allList){
			childList=map.get(dic.getParentName());
			if(childList==null){
				childList=new ArrayList<TDictionary>();
				map.put(dic.getParentName(), childList);
			}
			childList.add(dic);
		}
		return map;
	}
	/**
	 * 功能:修改
	 * @param oldName
	 * @param newCode
	 * @param newName
	 * @return
	 */
	public int updateAll(String oldName,String newCode,String newName){
		return dictionaryDao.updateAll(oldName, newCode, newName);
	}
	
	
	/**
	 * 功能描述:查询字典表 父级
	 * @author zhujingbo
	 * @return
	 */
	public List<TDictionary> getList(){
		return dictionaryDao.getList();
	}

	/**
	 * 功能描述:查询报价模块字典表 父级
	 * @author zhujingbo
	 * @return
	 */
	public List<TDictionary> getQuoteModeList(String parentCode){
		TDictionary dictionary = dictionaryDao.getQuoteModeList(parentCode).get(0);
		List<TDictionary> list = new ArrayList<TDictionary>();
		list.add(dictionary);
		return list;
	}

	/**
	 * 功能描述:按条件查询
	 * @author zhujingbo
	 * @param dictionary
	 * @return
	 */
	public List<TDictionary> getDictionaryList(TDictionary dictionary) {
		return dictionaryDao.getListNew(dictionary);
	}

	/**
	 * 功能描述:获取最大排序值
	 * @param dictionary
	 * @return
	 */
	public int getOrderby(TDictionary dictionary) {
		if(EmptyUtils.isEmpty(dictionary.getParentCode()))
			return 1;
		return dictionaryDao.getOrderby(dictionary);
	}
	
	
	public Integer saveOrUpdate(TDictionary dictionary,TUser user){//3最大权限，增删改 2修改1不可更改删除，添加
		if(dictionary.getIsRead() == 1 ) return 0; //无修改权限
		
		if(EmptyUtils.isNotEmpty(dictionary.getId())){
			TDictionary bean = super.get(TDictionary.class, dictionary.getId());
			BeanUtils.copyProperties(dictionary, bean, new String[] {"Id","parentCode", "parentName","status","isRead","keyWord"});
			update(bean);
		}else{
			if(dictionary.getIsRead() == 1 || dictionary.getIsRead() == 2) return 2;//无添加权限
			dictionary.setStatus(1);
			save(dictionary);
			
		}
		return 1;
	}

	/**
	 * 修改状态
	 * @param dictionary
	 * @return
     */
	public Integer updateQueto(TDictionary dictionary){
		if (EmptyUtils.isEmpty(dictionary)) {
			return 0;
		}else {
			update(dictionary);
			return 1;
		}
	}

	public List<TDictionary> queryPlanCycleList(TDictionary dictionary){
		if (dictionary == null) {
			dictionary = new TDictionary();
		}
		dictionary.setParentCode("STOCK_PLAN_CYCLE");
		return getDictionaryList(dictionary);
	}

	public TDictionary queryPlanCycle(TDictionary dictionary) {
		return queryPlanCycleList(dictionary).get(0);
	}
}
