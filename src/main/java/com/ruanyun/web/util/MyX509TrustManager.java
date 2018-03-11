package com.ruanyun.web.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
 *  #(c) IFlytek mangguo <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明:  证书信任管理器（用于https请求） 
 * 
 *  <br/>创建说明: 2014-3-24 上午10:28:58 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class MyX509TrustManager implements X509TrustManager{
	
    /**
     * 该方法检查客户端的证书，若不信任该证书则抛出异常。
     */
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * 该方法检查服务器的证书，若不信任该证书同样抛出异常。
     * 通过自己实现该方法，可以使之信任我们指定的任何证书。
     * 在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
     */
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
