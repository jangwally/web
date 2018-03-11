package com.ruanyun.web.model;
/**
 * 
 *  #(c) IFlytek zqb <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 手机端信息返回-公共实体类
 * 
 *  <br/>创建说明: 2014-4-3 上午10:21:53 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class AppCommonModel {
	
	
	public AppCommonModel() {
		super();
	}
	public AppCommonModel(Integer result) {
		super();
		this.result = result;
	}
	public AppCommonModel(Integer result, String msg) {
		super();
		this.result = result;
		this.msg = msg;
	}
	public AppCommonModel(Integer result, String msg, Object obj) {
		super();
		this.result = result;
		this.msg = msg;
		this.obj = obj;
	}
	public AppCommonModel(Integer result, String msg, Object obj,String access_token) {
		this.result = result;
		this.msg = msg;
		this.obj = obj;
		this.access_token=access_token;
	}
	
	/**
	 * 执行结果
	 */
	private Integer result=1;
	/**
	 * 消息
	 */
	private String msg="";
	
	/**
	 * 返回对象
	 */
//	private Object obj="{}";
	private Object obj;
	/**
	 * 判断账户是否异常
	 */
	private String access_token="";

	
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}
	
	

}
