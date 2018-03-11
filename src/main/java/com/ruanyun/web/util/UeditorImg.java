package com.ruanyun.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 


/**
 * 
 *  #(c) ruanyun 
 *
 *  版本说明: $id:$ 
 *
 *  功能说明: 获取Ueditor中的img标签
 * 
 * 创建说明: 2013-10-15 上午09:51:03 houkun
 * 
 *  修改历史:<br/>
 *
 */
public class UeditorImg {
	public static   List<String> getImgSrc(String htmlStr) {  
        String img = "";  
        Pattern p_image;  
        Matcher m_image;  
        List<String> pics = new ArrayList<String>();  
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";  
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);  
        m_image = p_image.matcher(htmlStr);  
        while (m_image.find()) {  
            img = img + "," + m_image.group();  
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);  
            while (m.find()) {  
                pics.add(m.group(1));  
            }  
        }  
        return pics;  
    }
}
