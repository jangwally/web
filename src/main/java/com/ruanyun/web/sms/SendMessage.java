package com.ruanyun.web.sms;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.ruanyun.web.util.WebConstans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ruanyun.common.utils.EmptyUtils;
/**
 * 
 *  #(c) IFlytek shouhou <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 发送短息
 * 
 *  <br/>创建说明: 2013-11-21 上午11:20:18 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Service("sendMessage")
public class SendMessage {
	
	
	
	/**
	 * 功能描述:短信验证码
	 * @author cqm  2017-7-18 下午06:10:23
	 * @param linkTel
	 * @return
	 * @throws ClientException 
	 */
    @RequestMapping("doAliyunSendSms")
	public static int doAliyunSend(String linkTel,Integer random) throws ClientException{
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "600000");
		System.setProperty("user.timezone","GMT +08");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名
		//替换成你的AK
		final String accessKeyId = WebConstans.ALIYUN_ACCESSKEYID;//"LTAInMTZ9TCBT2EA";//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret =WebConstans.ALIYUN_ACCESSKEYSECRET ;//"yPPopvpRGqluOIJsvOJMUsL4iYkvSd";//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		 //组装请求对象
		
		 SendSmsRequest request = new SendSmsRequest();
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		 request.setPhoneNumbers(linkTel);
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName(WebConstans.ALIYUN_SIGN_NAME);//"金牛护航"
		 //必填:短信模板-可在短信控制台中找到
		 request.setTemplateCode(WebConstans.ALIYUN_SMS_CODE);//"SMS_102315002"
		 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		 request.setTemplateParam("{\"code\":\""+random+"\"}");
		 request.setOutId("yourOutId");		 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者

		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			//执行成功
			System.out.println("====");
		}
		System.out.println("============================================="+random);
		return 1;
		
	}

	/**
	 *  发送订单信息
	 * @param linkTel
	 * @param smsCode
	 * @param msg
	 * @return
	 */
	@RequestMapping("doAliyunSendOrder")
	public static int doAliyun(String linkTel,String smsCode,String msg){
		//不发送短信通知
		if("NO".equals(smsCode) || EmptyUtils.isEmpty(smsCode) || linkTel.length()!=11)
			return 1;
		try {
			//设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "600000");
			//初始化ascClient需要的几个参数
			final String product = "Dysmsapi";//短信API产品名称
			final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名
			//替换成你的AK
			final String accessKeyId = WebConstans.ALIYUN_ACCESSKEYID;//"LTAInMTZ9TCBT2EA";//你的accessKeyId,参考本文档步骤2
			final String accessKeySecret = WebConstans.ALIYUN_ACCESSKEYSECRET;//"yPPopvpRGqluOIJsvOJMUsL4iYkvSd";//你的accessKeySecret，参考本文档步骤2
			//初始化ascClient,暂时不支持多region
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
					accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			//组装请求对象

			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(linkTel);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(WebConstans.ALIYUN_SIGN_NAME);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(smsCode);//
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam("{\"code\":\"" + msg + "\"}");
			request.setTemplateParam("{\"CODE\":\"" + msg + "\"}");
			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");
			//请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				//执行成功
				System.out.println("====");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return 1;

	}
	
	
	 
	
	public static void main(String[] args) throws IOException, ClientException {
		System.out.println(doAliyunSend("13966005078", 2221));
		//System.out.println(batchSend("13705604031", "您好，您的验证码是123456"));
	}
	
	 
}
