package com.ruanyun.common.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.*;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import com.ruanyun.web.model.UploadVo;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.QiNiuUploadUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * 
 *  #(c) IFlytek baseweb <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 
 * 
 *  <br/>创建说明: 2013-7-18 下午05:29:17 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class ImageUtil {
	
	private final static int RESIZETIMES=5;
	
	
	/**
	 * 功能描述:压缩
	 *
	 * @author yangliu  2013-7-18 下午05:30:21
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] getLessenImage(InputStream is){
		BufferedImage im=null;
		try {
			 im=javax.imageio.ImageIO.read(is);
			 BufferedImage bi=zoomImage(RESIZETIMES,im);
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 ImageIO.write(bi,"gif",out);
			 byte[] bs= out.toByteArray();
			 out.close();
			 return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	/**
	 * 功能描述:压缩
	 *
	 * @author yangliu  2013-7-18 下午05:30:21
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] getLessenImage(InputStream is,int width){
		
        
		BufferedImage im=null;
		try {
			 im=javax.imageio.ImageIO.read(is);
			 /*原始图像的宽度和高度*/
		     int picwidth = im.getWidth();
		     //如果原始宽度小于 缩小比例宽度 就返回空
		     if (picwidth<=350) {
				  return null;
			}
			 BufferedImage bi=zoomImage(width,im);
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 ImageIO.write(bi,"gif",out);
			 byte[] bs= out.toByteArray();
			 out.close();
			 return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	/**
	 * 功能描述:把图片按倍数压缩
	 *
	 * @author yangliu  2013-7-18 下午05:28:22
	 * 
	 * @param im 图片
	 * @param resizeTimes  倍数 如缩小2倍
	 * @return 图片流
	 */
	public static BufferedImage zoomImage(int toWidth,BufferedImage im) {
        /*原始图像的宽度和高度*/
        int picwidth = im.getWidth();
        int toHeight = im.getHeight()*toWidth/picwidth;
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
	
	
	private static byte[] bufferedImageTobytes(BufferedImage image, float quality) { 
		byte[] bytes=null;
        System.out.println("jpeg" + quality + "质量开始打包" + new Date().getTime());  
        // 如果图片空，返回空  
        if (image == null) {  
            return null;  
        }     
        // 得到指定Format图片的writer  
        Iterator<ImageWriter> iter = ImageIO  
                .getImageWritersByFormatName("jpeg");// 得到迭代器  
        ImageWriter writer = (ImageWriter) iter.next(); // 得到writer  
  
        // 得到指定writer的输出参数设置(ImageWriteParam )  
        ImageWriteParam iwp = writer.getDefaultWriteParam();  
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩  
        iwp.setCompressionQuality(quality); // 设置压缩质量参数  
  
        iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);  
  
        ColorModel colorModel = ColorModel.getRGBdefault();  
        // 指定压缩时使用的色彩模式  
        iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,  
                colorModel.createCompatibleSampleModel(16, 16)));  
  
        // 开始打包图片，写入byte[]  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流  
        IIOImage iIamge = new IIOImage(image, null, null);  
        try {  
            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput  
            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput  
            writer.setOutput(ImageIO  
                    .createImageOutputStream(byteArrayOutputStream));  
            writer.write(null, iIamge, iwp); 
            bytes=byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {  
            System.out.println("write errro");  
            e.printStackTrace();  
        }
        System.out.println("jpeg" + quality + "质量完成打包-----" + new Date().getTime() 
                + "----lenth----" + byteArrayOutputStream.toByteArray().length);  
        return bytes;  
    }  
	
	/**
	 * 功能描述: 质量压缩
	 *
	 * @author yangliu  2016年12月26日 上午11:43:09
	 * 
	 * @param is 流
	 * @param quality 质量 0-1
	 * @return
	 */
	public static byte[] getLessenImage(InputStream is,float quality){
		BufferedImage im=null;
		byte [] bs=null;
		try {
			 im=javax.imageio.ImageIO.read(is);
			 bs=bufferedImageTobytes(im, quality);
			 return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	/**
	 * 功能描述:缩小MultipartFile上传的图片 ，然后重新写入File里
	 *
	 * @author L H T  2013-12-10 下午02:42:01
	 * 
	 * @param is 输入流
	 * @param lessen_path 缩小后图片地址
	 * @param width 制定的宽度
	 * @return
	 */
	public static File getLessen_Image(InputStream is,String lessen_path,int width ){
		BufferedImage im=null;
		try {
			 im=javax.imageio.ImageIO.read(is);
			 /*原始图像的宽度和高度*/
		     int picwidth = im.getWidth();
		     //如果原始宽度小于 缩小比例宽度 就返回空
		     if (picwidth>width) {
		    	 BufferedImage bi=zoomImage(width, im);
		    	 File destFile = new File(lessen_path);
		    	//判断是否存在，不存在则创建
					if(!destFile.exists()){
						destFile.mkdirs();
					}
				 ImageIO.write(bi,"gif",destFile);
				 return destFile;
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	public static void main(String[] args) throws IOException {
		File file1=new File("D:\\26.PNG");
		FileInputStream is =new FileInputStream(file1);
		byte[] bytes=getLessenImage(is, 0.4f);
		FileOutputStream os=new FileOutputStream("D:\\2.png");
		os.write(bytes);
		os.close();
	}
	
	/**
	 * 功能描述:获得图片的宽高
	 *
	 * @author L H T  2014-3-27 上午11:05:40
	 * 
	 * @return
	 */
	public static Map<String ,Integer> getImageSize(InputStream pic){
		BufferedImage im=null;
		 try {
			im=javax.imageio.ImageIO.read(pic);
			 /*原始图像的宽度和高度*/
		     int picWidth = im.getWidth();
		     int picHeight=im.getHeight();
		     Map<String,Integer> map=new HashMap<String, Integer>();
		     map.put("width", picWidth);
		     map.put("height", picHeight);
		     return map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/***
	 * 功能描述:判断上传的图片尺寸是否符合要求
	 * @author  程三发 2015-12-22 下午02:45:29
	 * @param picFile 图片
	 * @param width   宽
	 * @param heigth  高
	 * @return
	 * @throws IOException
	 */
	public static boolean checkeImageSize(InputStream inputStream,int width,int heigth) throws IOException{
		BufferedImage bi = ImageIO.read(inputStream);
		int width1=bi.getWidth();
		int height1=bi.getHeight();
		if(width1!=width&&height1!=heigth){
			return true;
		}
		return false;
	}

	/** 功能描述:base64字符串 转bite上传七牛
	 * @author linguibing  2017-4-12 下午03:12:23
	 * @param request
	 * @param imgStr
	 * @param pathName
	 * @return
	 */
	public static UploadVo GenerateImage2(String imgStr)  {   //对字节数组字符串进行Base64解码并生成图片

		//文件上传结果保存对象
		UploadVo vo=new UploadVo();

		if (imgStr == null) //图像数据为空
			return null;
		try
		{
			byte[] b=null;
        	/*BASE64Decoder decoder = new BASE64Decoder();
        	b = decoder.decodeBuffer(imgStr);*/

			Base64 base64 = new Base64();
			b = base64.decode(imgStr);

			for(int i=0;i<b.length;++i){
				if(b[i]<0)
				{//调整异常数据
					b[i]+=256;
				}
			}

			String name="";
			name=TimeUtil.getCurrentDay("yyyyMMddHHmmssSSS")+(".jpg");//文件名称

			//保存文件  --
			QiNiuUploadUtils upload = new QiNiuUploadUtils();

			int upload_result=upload.uploadImg(b, name, Constants.QINIU_COMMONT_BUCKET);

			//改变图片名称为当前时间

			//判断返回的结果  --显示给用户
			if (upload_result==Constants.FILE_SUCCESS) {
				//文件保存所在路径
				vo.setFilename(name);
			}
			vo.setResult(upload_result);
			return vo;

		}
		catch (Exception e)   {
			e.printStackTrace();
			return null;
		}
	}

}
