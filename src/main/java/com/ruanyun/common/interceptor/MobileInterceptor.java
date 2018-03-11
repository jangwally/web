package com.ruanyun.common.interceptor;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.ExpressionToRegex;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MobileInterceptor extends HandlerInterceptorAdapter {
	int i=0;
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(MobileInterceptor.class);
	
	//开启是否验证权限 是否修改试图返回页面
	private boolean validationAuthority=false,modifyView=false;
	
	
	
	/////////////////////////权限验证开始///////////////////////////////////////
	
	private String whiteList, blackList, noSession;

	private String[] whiteListURLs = null, blackListURLs = null;

	private final String[] NULL_STRING_ARRAY = new String[0];
	private final String URL_SPLIT_PATTERN = "[, ;\r\n]";// 逗号 空格 分号 换行

	// 用spring的Ant过滤的通配符似乎不太适合自己的写法
	// private final PathMatcher pathMatcher = new AntPathMatcher();

	public String getBlackList() {
		return blackList;
	}

	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}

	public String getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}

	public String getNoSession() {
		return noSession == null ? "" : noSession;
	}

	public void setNoSession(String noSession) {
		this.noSession = noSession;
	}
	
	
	public boolean isValidationAuthority() {
		return validationAuthority;
	}

	public void setValidationAuthority(boolean validationAuthority) {
		this.validationAuthority = validationAuthority;
	}

	public boolean isModifyView() {
		return modifyView;
	}

	public void setModifyView(boolean modifyView) {
		this.modifyView = modifyView;
	}

	/**
	 * 功能描述:拦截UIL  权限过滤
	 *
	 * @author yangliu  2013-8-31 上午10:57:25
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO 用户NoSession拦截器
		if (validationAuthority) {
			whiteListURLs = strToArray(getWhiteList());
			blackListURLs = strToArray(getBlackList());

			String currentServlet = request.getServletPath();
			/**
			 * 解决手机端不能播放视频的问题
			 */
			if(EmptyUtils.isNotEmpty(currentServlet)&&currentServlet.startsWith("/sp")){
				HttpSessionUtils.setUserToSession(request.getSession(), new TUser());
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				return super.preHandle(request, response, handler);
			}
			boolean inWhite = false, inBlack = false;

			ExpressionToRegex e2r = new ExpressionToRegex();

			for (String urlPattern : whiteListURLs) {
				inWhite = e2r.match(urlPattern, currentServlet);
				if (inWhite)
					break;
			}

			for (String urlPattern : blackListURLs) {
				inBlack = e2r.match(urlPattern, currentServlet);
				if (inBlack)
					break;
//					return false;
			}

			if (!inWhite && inBlack) {

				LOGGER.info("Servlet:" + request.getRequestURL() + "被拦截");
				TUser userObj=HttpSessionUtils.getCurrentUser(request.getSession());
				if ( userObj== null) {
//					String url=request.getServletPath();
//					if(!"/".equals(url)){
//						String authCodesStr=AuthCache.getAuthCodeByUrl(url);
//						if(EmptyUtils.isEmpty(authCodesStr) || SecurityUtils.isAnyGranted(authCodesStr, userObj.getUrls())){
//							return super.preHandle(request, response, handler);
//						}
//					}
					LOGGER.info("Servlet:" + request.getRequestURL() + "被跳转");
					
					response.getWriter().print(			
							"<script>top.window.location.href = \""
									+ request.getContextPath() + getNoSession()
									+ "\";</script>");
					return false;
				}
				/*if(EmptyUtils.isNotEmpty(userObj.getUserType())&& Constants.USER_TYPE_CUSTOM_05!=userObj.getUserType()){
					LOGGER.info("Servlet:" + request.getRequestURL() + "被拦截");

					response.getWriter().print(
							"<script>top.window.location.href = \""
									+ request.getContextPath() + getNoSession()
									+ "\";</script>");
					return false;
				}*/
			}
		}
		return super.preHandle(request, response, handler);
	}

	private String[] strToArray(String urlStr) {
		if (urlStr == null) {
			return NULL_STRING_ARRAY;
		}
		String[] urlArray = urlStr.split(URL_SPLIT_PATTERN); 

		List<String> urlList = new ArrayList<String>();

		for (String url : urlArray) {
			url = url.trim();
			if (url.length() == 0) {
				continue;
			}
			urlList.add(url);
		}

		return urlList.toArray(NULL_STRING_ARRAY);
	}
	
	
	/////////////////////////权限验证结束///////////////////////////////////////

	/////////////////////////修改页面跳转路径开始///////////////////////////////////////
	
	
	

	/////////////////////////修改页面跳转路径结束///////////////////////////////////////
}
