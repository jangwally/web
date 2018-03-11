package com.ruanyun.web.util;

import java.math.BigDecimal;
 

 

public class Constants {
	public static String FILE_SEPARATOR=System.getProperty("file.separator");
    /**
     * url 地址配置
     */
    public static  String URL_PATH = "/url.properties";
    
    /**
     * 敏感词
     */
    public static  String FILE_DOCUMENT = "file/document/";
    /**
     * email 地址配置
     */
    public static  String EMAILCONFIG_PATH = "/emailconfig.properties";

    /**
     * 页面消息配置 路径
     */
    public static  String PAGECONFIG_PATH = "/pagemessage.properties";

    /**
     * 短信通知配置文件
     */
    public static final String SMS_PATH = "/sms.properties";
    /**
     * 用户的session key值
     */
    public static  String SESSION_KEY_USER = "systemUser";

    /**
     * 存用户实名信息
     */
    public static  String SESSION_KEY_USER_INFO = "systemUserInfo";

    /**
     * 用户的session key值
     */
    public static  String SESSION_KEY_USERAPP = "appUser";

    /**
     * 微信登录session key值
     */
    public static final String SESSION_KEY_WEIXIN_USERINFO = "systemWeixinUser";
    
    /**
     * 微信用户的session key值
     */
    public static final String SESSION_KEY_WEIXIN_OPEN_ID = "systemWeixinOpenId";
    public static final String SESSION_KEY_WEIXIN_ACCESS_TOKEN = "systemWeixinAccessToken";
    
    /**
     * 微信用户的session key值
     */
    public static final String SESSION_KEY_WEIXIN_USER = "systemWeixinUser";

    /**
     * 商品图片
     */
	public static final String FILE_GOODS = "file"+FILE_SEPARATOR+"goods"+FILE_SEPARATOR;
    
    /**
     * 左边菜单url sessionKEY
     */
    public static  String SEESION_KEY_LEFTURLS = "leftUrls";


 

    /**
     * 权限类型URL
     */
    public static  String AUTHORITY_TYPE_URL = "1";

    /**
     * 权限类型 权限
     */
    public static  String AUTHORITY_TYPE_AUTH = "2";


    /**
     * 新加用户默认密码
     */
    public static  String USER_DEFULT_PASSWORD = "123456";

    /**
     * 上传文件最大50
     */
    public static  int FILE_MAX_SIZE_FILE = 50 * 1000 * 1000;
    /**
     * 上传文件名长度，最大30
     */
    public static  int FILE_NAME_MAX_SIZE=60;
    /**
	 * 没有选择文件
	 */
	public static  int FILE_ERROR_NOFILE=-5;   
	/**
	 * 文件名太长
	 */
	public static  int FILE_ERROR_MOREFILENAME=-2;   
                       
	/**
	 * 文件格式不正确
	 */
	public static  int FILE_ERROR_NOTYPE=-3;  
	
	/**
	 * 文件太大
	 */
	public static  int FILE_ERROR_MOREFILESIZE=-4; 
	
	/**
	 * 文件上传证成功
	 */
	public static  int FILE_SUCCESS=1;  
	
	/**
	 * 文件上传失败
	 */
	public static  int FILE_FAIL=-1;		
	
	
	 /**
     * 全局通用禁用状态
     */
    public static  int GLOBAL_ENBLE_STATUS = 2;

    /**
     * 全局通用正常状态
     */
    public static  int GLOBAL_STATUS = 1;
               
    /**
     * 全局通用删除状态
     */
    public static  int GLOBAL_DEL = 0;


    /**
     * 手机端返回access_token 判断账户是否异常
     */
    public static  String  APP_ACCESS_TOKEN = "access_token";
    /**
     * 手机端返回成功状态
     */
    public static  int APP_SUCCESS = 1;
    /**
     * 手机端返回失败状态
     */
    public static  int APP_ERROR = -1;

    /**
     * 需要的点号
     */
    public static  String FILE_BIT = ".";

    /**
     * 需要的逗号
     */
    public static  String FILE_COMMA = ",";
    
    /**
     * 需要的负号
     */
    public static  String SUBTRACT = "-";
    
    /**
     * 兑换类型图片路劲
     */

    public static  String FILE_GOODS_TYPE = "file"+FILE_SEPARATOR+"goodstype"+FILE_SEPARATOR;
    /**
     * 广告图片
     */

    public static  String FILE_ADVERTISEMENT = "file"+FILE_SEPARATOR+"advertisement"+FILE_SEPARATOR;
    
    /**
     * 商品管理
     */
    public static  String FILE_GOODSINFO = "file"+FILE_SEPARATOR+"goodsinfo"+FILE_SEPARATOR;
    
    /**
     * 版本更新
     */
    public static  String FILE_VERSIONUPDATE = "file"+FILE_SEPARATOR+"versionupdate"+FILE_SEPARATOR;

    /**
     * 默认图片
     */
    public static String DEFAULT_IMG = "img/ewm.png";

    /**
     * 图片---用户头像路劲
     */
    
    public static  String FILE_USER_PHOTO = "file"+FILE_SEPARATOR+"userphoto"+FILE_SEPARATOR;
    
   
    /**
     * 附件上传文件夹
     */
    public static final String attachName="docx";
   
	 /**
	 * xhEditor编辑器文件上传路径
	 */
	public static  String XHEDITOR_UPLOAD_PATH = "file//xheditor//";
	
	/**
	 * 图片压缩比例的宽度compression ratio
	 */
	public static  int PIC_COMPRESS_RATIO=350;
  
	 /**
     * 七牛图片默认上传类型
     */
    public static  String QINIU_PIC_TYPE = "gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG,apk,mp4";
	
    /**
     * 七牛公共图片上传地址
     */
    public static  String QINIU_COMMONT_BUCKET = "userimg";
    
    /**
     * ueditor上传图片
     */
    public static  String QINIU_UEDITOR_BUCKET = "ueditor";
    

	/**
     * 七牛上传用户图片域名地址
     */
    public static String QINIU_USER_IMGURL="http://ow72stc9k.bkt.clouddn.com/";
    
    /**
     * ueditor
     */
    public static String QINIU_UEDITOR_IMGURL="http://ow72eu7w7.bkt.clouddn.com/";
   
	
	 //==============================JQYERY UI数据操作返回值=================================================================

    /**
     * JQYERY UI 表单提交 返回值--正常状态 statusCode
     */
    public static  String STATUS_SUCCESS_CODE = "200";
    /**
     * JQYERY UI 表单提交 返回值--失败状态 statusCode
     */
    public static  String STATUS_FAILD_CODE = "300";
    /**
     * JQYERY UI 表单提交 返回值--会话失效状态 statusCode
     */
    public static  String STATUS_FAILSESSION_CODE = "301";
    /**
     * JQYERY UI 提示信息-- 返回 成功提示信息 message
     */
    public static  String MESSAGE_SUCCESS = "数据操作成功";
    /**
     * JQYERY UI 提示信息-- 返回 失败提示信息 message
     */
    public static  String MESSAGE_FAILED = "数据操作失败";
    public static  String MESSAGE_FAILED_ORGINFO = "上级已经是最低代理商，不能再添加";
    public static  String MESSAGE_CHECK_LOGIN_NAME = "该登录名已存在";

    

    
    /**
     * 同一用户每天限制兑换金额的次数
     */
    public static  int EXCHANGE_COUNT =3;
    



    /**
     * 通过微信绑定时,用来存放短信验证码和用户的手机号
     */
    public static String validateContent = "validateContent";



    /**
     * 短信的类型
     * 0 验证码
     * 1 短信提醒
     */
    public static String SMS_TYPE_0 = "0";
    public static String SMS_TYPE_1 = "1";


    public static Integer V_MID = 16976;

    /**
     *
     */
    public static String V_KEY = "test";

    /**
     * 公钥文件地址
     */
    public static String KEY_PATH = "src/main/resources/Public1024.key";


    /************************start 附件类型******************************/
    public static String  ATTACH_INFO_ATTACHTYPE_1="t_goods_info";//1、商品

    public static String  ATTACH_INFO_ATTACHTYPE_2="t_shop_info";//2、商家信息

    public static String  ATTACH_INFO_ATTACHTYPE_5="t_local_new"; //5、新闻图片

    public static String  ATTACH_INFO_ATTACHTYPE_11="t_version_update";//11、版本更新
    /************************ end 附件类型******************************/





    /************************************************** 金牛3.0项目 常量******************************************************/

    /**
     * 全局判断常量
     */
    public final static int GLOBAL_STATUS_1=1;
    public final static int GLOBAL_STATUS_0=0;
    public final static int GLOBAL_STATUS_OWE_1=-1;
    public final static int GLOBAL_STATUS_2=2;

    /**
     * 机构编码顶级代码
     */
    public final static String PARENT_CODE_DEFAULT = "0000000000000000000000000000";

    /**
     * 机构用户在用户表中user_code 为固定值
     */
    public final static String ORG_USER_CODE_DEFAULT= "000000";

    /**
     * 机构邀请码首位固定数字
     */
    public final static String FIRST_CODE_DEFAULT="1";

    /**
     * 用户邀请码首位固定数字
     */
    public final static String USER_FIRST_CODE_DEFAULT="2";

    /**
     * 用户表用户来源  1 机构邀请  2 用户邀请
     */
    public final static int USER_SOURCE_ORG=1;
    public final static int USER_SOURCE_USER=2;

    /**
     * 用户类型 1 管理员 2机构用户 99 普通用户
     */
    public static int USER_TYPE_1=1;
    public static int USER_TYPE_2=2;
    public static int USER_TYPE_99=99;

    /**
     * 用户状态 1 启用 2禁用
     */
    public static int USER_STATUS_1=1;
    public  static int USER_STATUS_0=0;

    /**
     * 用户表中的实名状态 1实名 0未实名
     */
    public static int USER_BIND_STATUS_1=1;
    public static int USER_BIND_STATUS_0=0;

    /**流水消费类型**/
    public static int CONSUM_TYPE_1=1;//购物消费 
    public static int CONSUM_TYPE_2=2;//积分兑换
    public static int CONSUM_TYPE_3=3;//充值
    public static int CONSUM_TYPE_4=4;//提现
    public static int CONSUM_TYPE_5=5;//充值送积分
    public static int CONSUM_TYPE_6=6;//会员用户购物返现
    public static int CONSUM_TYPE_7=7;//分销提成
    public static int CONSUM_TYPE_8=8;//系统赠送会员



    /**
     * 提现状态 1//提现成功 2//已提交 3//提现失败
     */

    public static int APPLICATION_STATUS_1=1;
    public static int APPLICATION_STATUS_2=2;
    public static int APPLICATION_STATUS_1_=-1;
    


 

    
    /**发送验证码次数*/
    public static int SEND_COUNT=5;    //短信次数
 
    /****用户角色***/
    public static  int USER_ROLE_SYS = 1;//系统用户
    public static  int USER_ROLE_SHOP = 2;//经销商
    public static  int USER_ROLE_MEMBER = 3;//会员用户

    public static  int USER_ROLE_AREA_MANAGER=5;//区域管理员
    

    




    /**
     * 方案状态
     */
    public static String STOCK_PLAN_ORDER_STATUS_APPLY = "1";
    public static String STOCK_PLAN_ORDER_STATUS_POSITION = "2";
    public static String STOCK_PLAN_ORDER_STATUS_EXERCISE = "3";
    public static String STOCK_PLAN_ORDER_STATUS_SETTLEMENT = "4";
    public static String STOCK_PLAN_ORDER_STATUS_FAIL = "-1";


    /**
     * 方案周期
     */
    public static final String oneMonth = "1m";
    public static final String twoMonth = "2m";
    public static final String threeMonth = "3m";
    public static final String fourMonth = "4m";
    public static final String fiveMonth = "5m";
    public static final String sixMonth = "6m";
    public static final String sevenMonth = "7m";
    public static final String eightMonth = "8m";
    public static final String nineMonth = "9m";
    public static final String tenMonth = "10m";
    public static final String elevenMonth = "11m";
    public static final String twelveMonth = "12m";


    /**
     * 机构类型 1 运营 2会员 3 代理 4二级代理 5 三级代理 6 四级代理 7 五级代理
     */
    public static int ORG_TYPE_2=2;
    public static int ORG_TYPE_1=1;
    public static int ORG_TYPE_3=3;
    public static int ORG_TYPE_4=4;
    public static int ORG_TYPE_5=5;
    public static int ORG_TYPE_6=6;
    public static int ORG_TYPE_7=7;



    /**
     * 权限类型,值与权限表id对应
     */
    public static int ROLE_ID_1=1;
    public static int ROLE_ID_2=2;
    public static int ROLE_ID_3=3;
    public static int ROLE_ID_4=4;

}
