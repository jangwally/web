package com.ruanyun.web.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import net.sf.json.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.ImageUtil;
import com.thoughtworks.xstream.core.util.Base64Encoder;

public class QiNiuUploadUtils {
	public static Logger logger = Logger.getLogger(QiNiuUploadUtils.class);
	private static String ACCESS_KEY = "fmchk4BU5HNrYg6iJI7S4wYcWZC2m0cIIHVELrIp";
	private static String SECRET_KEY = "bUp3U-uRy1vASWda-CONMLkzZ30HQrXQRGnZ9fK4";
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
 	public static String bucket = "userimg";
//	private static BucketManager bucketManager = new BucketManager(auth);
	public static String bucket_video_url="http://ow72stc9k.bkt.clouddn.com";
	
	
                                     
	
	
	/**
	 * huoqu 
	 * @param bucket
	 * @return
	 */
	public static String getUploadToken(String buckets){
		StringMap putPolicy = new StringMap();
		putPolicy.put("callbackBody", "{\"fileName\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		putPolicy.put("callbackBodyType", "application/json");
		long expireSeconds = 3600;
		String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
		return upToken;
	}
	
	
	
	
	// 简单上传，使用默认策略
	private String getUpTokenFile(String param_bucket){
	    return auth.uploadToken(param_bucket);
	}
	
	private String getUpToken2(){
	    return auth.uploadToken("bucket", null, 3600, new StringMap()
	         .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
	         .put("callbackBody", "key=$(key)&hash=$(etag)"));
	}


	private String getUpToken(String[] keys,String[] results){
	    return auth.uploadToken("bucket", null, 3600, new StringMap()
	         .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
	         .put("callbackBody", "key=$(key)&hash=$(etag)"));
	}
	private static String getUpTokenImg( String param_bucket){
	    return auth.uploadToken(param_bucket, null, 3600, new StringMap()
	            .putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height),\"mimeType\":$(mimeType),\"endUser\":$(endUser)}"));
	}
	
	private static String getUpTokeFile( String param_bucket){
	    return auth.uploadToken(param_bucket, null, 3600, new StringMap()
	            .putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height),\"mimeType\":$(mimeType),\"endUser\":$(endUser)}"));
	}
	/*
	* 生成上传token
	*
	* @param bucket  空间名
	* @param key     key，可为 null
	* @param expires 有效时长，单位秒。默认3600s
	* @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
	*        scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
	* @param strict  是否去除非限定的策略字段，默认true
	* @return 生成的上传token
	*/	
	static Configuration cfg = new Configuration(Zone.zone0());
	static UploadManager uploadManager = new UploadManager(cfg);
	
	/**
	 *  七牛上传附件文件(app上传图片)
	 * @param file 文件对象
	 * @param name 名称
	 * @param fileType 类型
	 * @return
	 */
	public  int uploadFile(MultipartFile file, String name,String fileType,String param_bucket){
		System.out.println("11");
		if(EmptyUtils.isEmpty(file)){
			logger.error("文件不存在");
			return Constants.FILE_ERROR_NOFILE;
		}
		
		if(name.length()>Constants.FILE_NAME_MAX_SIZE){
			logger.error("上传文件名太长");
			return Constants.FILE_ERROR_MOREFILENAME;
		}
		
		if (file.getSize() > Constants.FILE_MAX_SIZE_FILE) {
			logger.error("文件太大");
			return Constants.FILE_ERROR_MOREFILESIZE;
		}
		
		try {
			 Response res = uploadManager.put(file.getBytes(),name, getUpTokenImg(Constants.attachName));//getUpTokenImg(param_bucket));  //getUpTokenFile
		       System.out.println(res.jsonToMap().formString());
		        MyRet ret = res.jsonToObject(MyRet.class);
		        System.out.println(JSONObject.fromObject(ret).toString());
		} catch (Exception e) {
			logger.error("文件上传失败");
			return Constants.FILE_FAIL;
		}
		logger.error("文件上传成功");
		return Constants.FILE_SUCCESS;
	}
	/**
	 *  七牛上传图片文件
	 * @param file 文件对象
	 * @param name 名称
	 * @param fileType 类型
	 * @return
	 */
	public  int uploadImg(MultipartFile file, String name,String fileType,String param_bucket){
		if(EmptyUtils.isEmpty(file)){
			logger.error("文件不存在");
			return Constants.FILE_ERROR_NOFILE;
		}
		
		if(name.length()>Constants.FILE_NAME_MAX_SIZE){
			logger.error("上传文件名太长");
			return Constants.FILE_ERROR_MOREFILENAME;
		}
		
		if(EmptyUtils.isNotEmpty(fileType)&&vidateFileType(file.getOriginalFilename(), fileType)){
			logger.error("格式不正确");
			return Constants.FILE_ERROR_NOTYPE;
		}
		
		if (file.getSize() > Constants.FILE_MAX_SIZE_FILE) {
			logger.error("文件太大");
			return Constants.FILE_ERROR_MOREFILESIZE;
		}
		
		try {
			Response res = null;
			if(file.getSize()>200*1024 && file.getSize()<1024*1024*1){   //大于200K，小于1M
				res=uploadManager.put(ImageUtil.getLessenImage(file.getInputStream(), 0.7f),name, getUpTokenImg(param_bucket));
			}else if(file.getSize()>=1024*1024*1 && file.getSize()<1024*1024*3){   //大于200K，小于1M
				res=uploadManager.put(ImageUtil.getLessenImage(file.getInputStream(), 0.55f),name, getUpTokenImg(param_bucket));
			}else if(file.getSize()>=1024*1024*3 && file.getSize()<1024*1024*4.5){   //大于200K，小于1M
				res=uploadManager.put(ImageUtil.getLessenImage(file.getInputStream(), 0.40f),name, getUpTokenImg(param_bucket));
			}else if(file.getSize()>=1024*1024*4.5){   //大于200K，小于1M
				res=uploadManager.put(ImageUtil.getLessenImage(file.getInputStream(), 0.30f),name, getUpTokenImg(param_bucket));
			}else{
				res = uploadManager.put(file.getBytes(),name, getUpTokenImg(param_bucket));
			}
			 System.out.println("====="+res);
		        MyRet ret = res.jsonToObject(MyRet.class);
		       
		} catch (Exception e) {
			 e.printStackTrace();
			logger.error("文件上传失败");
			return Constants.FILE_FAIL;
		}
		logger.error("文件上传成功");
		return Constants.FILE_SUCCESS;
	}
	
	public  int uploadImg(byte[] bytes, String name,String fileType,String param_bucket){
		System.out.println("22");
		if(EmptyUtils.isEmpty(bytes)){
			logger.error("文件不存在");
			return Constants.FILE_ERROR_NOFILE;
		}
		if(name.length()>Constants.FILE_NAME_MAX_SIZE){
			logger.error("上传文件名太长");
			return Constants.FILE_ERROR_MOREFILENAME;
		}
		
		if (bytes.length > Constants.FILE_MAX_SIZE_FILE) {
			logger.error("文件太大");
			return Constants.FILE_ERROR_MOREFILESIZE;
		}
		
		try {
			 Response res = uploadManager.put(bytes,name, getUpTokenImg(param_bucket));
		       System.out.println(res.jsonToMap().formString());
		        MyRet ret = res.jsonToObject(MyRet.class);
		        System.out.println(JSONObject.fromObject(ret).toString());
		} catch (Exception e) {
			logger.error("文件上传失败");
			return Constants.FILE_FAIL;
		}
		logger.error("文件上传成功");
		return Constants.FILE_SUCCESS;
	}
	
	/**
	 *  七牛上传二维码
	 * @param file 文件
	 * @param name 名称
	 * @param param_bucket 类型
	 * @return
	 */
	public  int uploadImg(byte[] file, String name,String param_bucket){
//			try {
//		 Response res = uploadManager.put(file,name, getUpTokenImg(param_bucket));
//		        MyRet ret = res.jsonToObject(MyRet.class);
			try {
				Response res = null;
				if(file.length>200*1024 && file.length<1024*1024*1){   //大于200K，小于1M
					res=uploadManager.put(ImageUtil.getLessenImage(new ByteArrayInputStream(file), 0.7f),name, getUpTokenImg(param_bucket));
				}else if(file.length>=1024*1024*1 && file.length<1024*1024*3){   //大于200K，小于1M
					res=uploadManager.put(ImageUtil.getLessenImage(new ByteArrayInputStream(file), 0.55f),name, getUpTokenImg(param_bucket));
				}else if(file.length>=1024*1024*3 && file.length<1024*1024*4.5){   //大于200K，小于1M
					res=uploadManager.put(ImageUtil.getLessenImage(new ByteArrayInputStream(file), 0.40f),name, getUpTokenImg(param_bucket));
				}else if(file.length>=1024*1024*4.5){   //大于200K，小于1M
					res=uploadManager.put(ImageUtil.getLessenImage(new ByteArrayInputStream(file), 0.30f),name, getUpTokenImg(param_bucket));
				}else{
					res = uploadManager.put(file,name, getUpTokenImg(param_bucket));
				}
				System.out.println("====="+res);
				MyRet ret = res.jsonToObject(MyRet.class);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("文件上传失败");
				return Constants.FILE_FAIL;
			}
		/*} catch (Exception e) {
			logger.error("文件上传失败");
			return Constants.FILE_FAIL;
		}*/
		logger.error("文件上传成功");
		return Constants.FILE_SUCCESS;
	}
	/**
	 * 功能描述:验证文件格式  true 不合格 false 合格
	 *
	 * @author houkun  2013-10-15 上午09:45:27
	 * 
	 * @param fileName 文件名称
	 * @param fileType 文件类型
	 * @return
	 */
	private  boolean vidateFileType(String fileName,String fileType) {
		if(EmptyUtils.isNotEmpty(fileName) && fileName.lastIndexOf(Constants.FILE_BIT)>=0){
			String fileSuffix=fileName.substring(fileName.lastIndexOf(Constants.FILE_BIT)+1);
			if(EmptyUtils.isEmpty(fileSuffix))
				return true;
			String[] suffixs=fileType.split(Constants.FILE_COMMA);
			for(String suffix : suffixs){
				if(fileSuffix.equals(suffix))
					return false;
			}
		}
		return true;
	}
	

	
	public static void main(String[] args) {
		
	 
	}

	/**
	 * 
	 * 功能描述：返回七牛base64图片
	 * @auther xqzhang
	 * 时间：2016-2-27
	 * @param picPath
	 * @param bucket
	 * @return
	 */
	public static String getPicBase64ByUrl(String picPath,String bucket){
		URL url;
		byte[] data = null;
		if(EmptyUtils.isEmpty(picPath)){
			picPath="nologo.png";
		}
		try {
			url = new URL(bucket+picPath);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
	        //设置请求方式为"GET"  
	        conn.setRequestMethod("GET");  
	        //超时响应时间为5秒  
	        conn.setConnectTimeout(5 * 1000); 
	        //通过输入流获取图片数据  
	        InputStream inStream = conn.getInputStream();  
	        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
	        data = readInputStream(inStream);  
	        //关闭输出流  
	        inStream.close();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Base64Encoder encoder = new Base64Encoder();  
	    return encoder.encode(data); 
	}
	/**
	 * 
	 * 功能描述：格式时间
	 * @auther xqzhang
	 * 时间：2016-3-1
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
    
    
    //七牛上传文件
    public  int uploadImg1(MultipartFile file, String name,String fileType,String param_bucket){
		if(EmptyUtils.isEmpty(file)){
			logger.error("文件不存在");
			return Constants.FILE_ERROR_NOFILE;
		}
		
		if(name.length()>Constants.FILE_NAME_MAX_SIZE){
			logger.error("上传文件名太长");
			return Constants.FILE_ERROR_MOREFILENAME;
		}
		
		if(EmptyUtils.isNotEmpty(fileType)&&vidateFileType(file.getOriginalFilename(), fileType)){
			logger.error("格式不正确");
			return Constants.FILE_ERROR_NOTYPE;
		}
		
		if (file.getSize() > Constants.FILE_MAX_SIZE_FILE) {
			logger.error("文件太大");
			return Constants.FILE_ERROR_MOREFILESIZE;
		}
		
		try {
			Response res = null;
		
				res = uploadManager.put(file.getBytes(),name, getUpTokenImg(param_bucket));
			
		        MyRet ret = res.jsonToObject(MyRet.class);
		} catch (Exception e) {
			logger.error("文件上传失败");
			return Constants.FILE_FAIL;
		}
		logger.error("文件上传成功");
		return Constants.FILE_SUCCESS;
	}
}
