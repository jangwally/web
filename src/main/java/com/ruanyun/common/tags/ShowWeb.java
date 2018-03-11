package com.ruanyun.common.tags;

import com.ruanyun.common.cache.impl.StaticObjectCache;
import com.ruanyun.common.utils.EmptyUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class ShowWeb extends TagSupport {
	private String name="";

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = (JspWriter) pageContext.getOut();
			if (EmptyUtils.isNotEmpty(name)) {
				String sb = getValue(StaticObjectCache.webMap.get(name));
				out.print(sb);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public String getValue(Object o){
		if(o!=null)
			return String.valueOf(o);
		return "";
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
