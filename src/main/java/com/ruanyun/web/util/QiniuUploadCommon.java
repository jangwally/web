package com.ruanyun.web.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.Exception.RuanYunException;
import com.ruanyun.web.model.UploadVo;
/**
 * 七牛上传图片
 *
 */
public class QiniuUploadCommon{
	/**
	 * 功能描述:文件上传
	 *
	 * @author L H T  2013-12-3 下午05:49:12
	 * 
	 * @param files
	 * @param request
	 * @param folderUrl 需要保存的文件路劲
	 * @param validType  文件验证的类型
	 * @param id          数据的id，根据数据的id创建个文件夹
	 * @return
	 * @throws IOException
	 */
	public static UploadVo uploadPic(MultipartFile files,HttpServletRequest request,String folderUrl,String validType) throws IOException{
		if(files==null)
			return null;
		//文件上传结果保存对象
		UploadVo vo=new UploadVo();
		if(EmptyUtils.isEmpty(files) || EmptyUtils.isEmpty(files.getOriginalFilename())){
			vo.setResult(Constants.FILE_FAIL);
			return vo;
		}
		//文件后缀
		String nameSuffix = files.getOriginalFilename();
		nameSuffix=nameSuffix.substring(nameSuffix.lastIndexOf(".")+1);
		//获取文件保存路劲
		String path= CommonMethod.getProjectPath(request)+folderUrl+Constants.FILE_SEPARATOR;
		//保存文件  --
		FileUtils upload = new FileUtils();
		String filename=System.nanoTime()+"."+nameSuffix;
		
		int upload_result=upload.uploadFile(files,path,filename,validType);
		vo.setResult(upload_result);
		vo.setFileSize(files.getSize());
		vo.setFileOldName(files.getOriginalFilename());
		vo.setFilename(folderUrl+filename);
		return vo;
		
	}
	
	/**
	 * 功能描述: 上传图片
	 *
	 * @author yangliu  2016年9月3日 下午4:03:05
	 * 
	 * @param request 图片流
	 * @param fileName 文件参数名称
	 * @param folderUrl 地址
	 * @param validType 验证格式
	 * @return
	 */
	public static UploadVo uploadPic(HttpServletRequest request,String fileName,String folderUrl,String validType){
		if(!(request instanceof MultipartHttpServletRequest)){ //没有流图片
			return null;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			return uploadPic(multipartRequest.getFile(fileName), request, folderUrl, validType);
		} catch (IOException e) {
			throw new RuanYunException("上传失败");
		}
	}
	
	/**
	 * 功能描述:批量上传图片
	 *
	 * @author yangliu  2016年9月3日 下午4:06:10
	 * 
	 * @param request 图片流
	 * @param fileName 文件名称
	 * @param folderUrl 文件路径
	 * @param validType 验证类型
	 * @return
	 */
	public static List<UploadVo> uploadPics(HttpServletRequest request,String fileName,String folderUrl,String validType){
//		//上传文件的二进制请求
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		// 文件
		List<MultipartFile> files = multipartRequest.getFiles(fileName);
		if(files==null || files.isEmpty()){
			return null;
		}
		//文件后缀
		String nameSuffix = files.get(0).getOriginalFilename();
		nameSuffix=nameSuffix.substring(nameSuffix.lastIndexOf(".")+1);
		//获取文件保存路劲
		String path= CommonMethod.getProjectPath(request)+folderUrl+Constants.FILE_SEPARATOR;
		//保存文件  --
		FileUtils upload = new FileUtils();
		List<UploadVo> uploadVos=new ArrayList<UploadVo>();
		UploadVo vo=null;
		String filename="";
		for(MultipartFile file : files){
			vo=new UploadVo();
			filename=System.nanoTime()+"."+nameSuffix;
			vo.setFileType(nameSuffix);
			vo.setFileSize(file.getSize());
			vo.setFileOldName(file.getOriginalFilename());
			int upload_result=upload.uploadFile(file,path,filename,validType);
			vo.setResult(upload_result);
			vo.setFilename(folderUrl+filename);
			uploadVos.add(vo);
		}
		return uploadVos;
	}
	
	/**
	 * 上传文件到七牛空间
	 * @param picFile
	 * @return
	 */
	public static UploadVo uploadFile(MultipartFile picFile,String type)
	  {
	    UploadVo vo = null;
	    if ((picFile != null) && (EmptyUtils.isNotEmpty(picFile.getOriginalFilename())))
	      try {
	        vo = QiniuUploadCommon.uploadFile(picFile, Constants.QINIU_PIC_TYPE, type);
	        return vo;
	      } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	      }

	    return null;
	  }
	
	
	/**
	 * 功能描述:批量上传图片
	 *
	 * @author yangliu  2016年9月3日 下午4:06:10
	 * 
	 * @param request 图片流
	 * @param fileName 文件名称
	 * @param folderUrl 文件路径
	 * @param validType 验证类型
	 * @return
	 * @throws IOException 
	 */
	public static List<UploadVo> uploadPics2(HttpServletRequest request,String fileName,String folderUrl,String validType) throws IOException{
//		//上传文件的二进制请求
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		// 文件
		List<MultipartFile> files = multipartRequest.getFiles(fileName);
		if(files==null || files.isEmpty()){
			return null;
		}
	
		List<UploadVo> uploadVos=new ArrayList<UploadVo>();
		UploadVo vo=null;
		for(MultipartFile file : files){
			vo=new UploadVo();
			vo.setFileSize(file.getSize());
			vo.setFileOldName(file.getOriginalFilename());
			UploadVo upload_result=uploadFile(file,validType,Constants.QINIU_COMMONT_BUCKET);  //userimg
			vo.setResult(1);
			vo.setFilename(upload_result.getFilename());
			uploadVos.add(vo);
		}
		return uploadVos;
	}
	
	public static UploadVo uploadFile(MultipartFile files,String validType,String bucket) throws IOException{
		
		System.out.println("进来了吗？");
		//文件上传结果保存对象
		UploadVo vo=new UploadVo();
		//文件名称
		String name = files.getOriginalFilename();
		if(EmptyUtils.isNotEmpty(name)){
			//改变图片名称为当前时间 
			name=TimeUtil.getCurrentDay("yyyyMMddHHmmssSSS")+name.substring(name.lastIndexOf("."));//截取文件名称

			//保存文件  --
			QiNiuUploadUtils upload = new QiNiuUploadUtils();
			/**
			 * 返回上传结果
			 * 1  上传成功
			 * -1 上传失败（或者创建文件夹时失败）
			 * -2 上传文件文件名太长
			 * -3 上传文件格式不正确
			 * -4 上传文件太大
			 * -5 上传 文件不存在
			 * 
			 */
			int upload_result=upload.uploadImg(files, name, validType, bucket);
			
			//判断返回的结果  --显示给用户
			if (upload_result==Constants.FILE_SUCCESS) {
				//文件保存所在路径
				vo.setFilename(name);
			}
			vo.setResult(upload_result);
			vo.setFileSize(files.getSize());
		}
		
		return vo;
		
	}
	
	
	public static UploadVo uploadFile(byte [] bytes,String name,String bucket) throws IOException{
		//文件上传结果保存对象
		UploadVo vo=new UploadVo();
		//改变图片名称为当前时间 
		name=TimeUtil.getCurrentDay("yyyyMMddHHmmssSSS")+name.substring(name.lastIndexOf("."));//截取文件名称

		//保存文件  --
		QiNiuUploadUtils upload = new QiNiuUploadUtils();
		/**
		 * 返回上传结果
		 * 1  上传成功
		 * -1 上传失败（或者创建文件夹时失败）
		 * -2 上传文件文件名太长
		 * -3 上传文件格式不正确
		 * -4 上传文件太大
		 * -5 上传 文件不存在
		 * 
		 */
		int upload_result=upload.uploadImg(bytes, name, bucket);
		//判断返回的结果  --显示给用户
		if (upload_result==Constants.FILE_SUCCESS) {
			//文件保存所在路径
			vo.setFilename(name);
		}
		vo.setResult(upload_result);
		return vo;
	}
	
	////////////////////////////////////七牛图片处理///////////////////////////////////////////////////
	
	
	/**
	 * 上传文件到七牛空间
	 * @param picFile
	 * @return
	 */
	public static UploadVo uploadPic(MultipartFile picFile,String type)
	  {
	    UploadVo vo = null;
	    if ((picFile != null) && (EmptyUtils.isNotEmpty(picFile.getOriginalFilename())))
	      try {
	        vo = QiniuUploadCommon.uploadPic(picFile, Constants.QINIU_PIC_TYPE, type);
	        return vo;
	      } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	      }

	    return null;
	  }
	
	
	public static UploadVo uploadPic(MultipartFile files,String validType,String bucket) throws IOException{
		//文件上传结果保存对象
		UploadVo vo=new UploadVo();
		//文件名称
		String name = files.getOriginalFilename();
		//改变图片名称为当前时间 
		name=TimeUtil.getCurrentDay("yyyyMMddHHmmssSSS")+name.substring(name.lastIndexOf("."));//截取文件名称

		//保存文件  --
		QiNiuUploadUtils upload = new QiNiuUploadUtils();
		/**
		 * 返回上传结果
		 * 1  上传成功
		 * -1 上传失败（或者创建文件夹时失败）
		 * -2 上传文件文件名太长
		 * -3 上传文件格式不正确
		 * -4 上传文件太大
		 * -5 上传 文件不存在
		 * 
		 */
		int upload_result=upload.uploadImg(files, name, validType, bucket);
		//判断返回的结果  --显示给用户
		if (upload_result==Constants.FILE_SUCCESS) {
			//文件保存所在路径
			vo.setFilename(name);
		}
		vo.setResult(upload_result);
		
		return vo;
	}
	
	
	//七牛上传文件
	public static List<UploadVo> uploadPics3(HttpServletRequest request,String fileName,String folderUrl,String validType) throws IOException{
//		//上传文件的二进制请求
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		// 文件
		List<MultipartFile> files = multipartRequest.getFiles(fileName);
		if(files==null || files.isEmpty()){
			return null;
		}
	
		List<UploadVo> uploadVos=new ArrayList<UploadVo>();
		UploadVo vo=null;
		for(MultipartFile file : files){
			vo=new UploadVo();
			vo.setFileSize(file.getSize());
			vo.setFileOldName(file.getOriginalFilename());
			UploadVo upload_result=uploadFile3(file,validType,Constants.QINIU_COMMONT_BUCKET);  //userimg
			vo.setResult(1);
			vo.setFilename(upload_result.getFilename());
			uploadVos.add(vo);
		}
		return uploadVos;
	}
	
	
	//七牛上传文件
	public static UploadVo uploadFile3(MultipartFile files,String validType,String bucket) throws IOException{
		
		System.out.println("进来了吗？");
		//文件上传结果保存对象
		UploadVo vo=new UploadVo();
		//文件名称
		String name = files.getOriginalFilename();
		if(EmptyUtils.isNotEmpty(name)){
			//改变图片名称为当前时间 
			name=TimeUtil.getCurrentDay("yyyyMMddHHmmssSSS")+name.substring(name.lastIndexOf("."));//截取文件名称

			//保存文件  --
			QiNiuUploadUtils upload = new QiNiuUploadUtils();
			/**
			 * 返回上传结果
			 * 1  上传成功
			 * -1 上传失败（或者创建文件夹时失败）
			 * -2 上传文件文件名太长
			 * -3 上传文件格式不正确
			 * -4 上传文件太大
			 * -5 上传 文件不存在
			 * 
			 */
			int upload_result=upload.uploadImg1(files, name, validType, bucket);
			
			//判断返回的结果  --显示给用户
			if (upload_result==Constants.FILE_SUCCESS) {
				//文件保存所在路径
				vo.setFilename(name);
			}
			vo.setResult(upload_result);
			vo.setFileSize(files.getSize());
		}
		
		return vo;
		
	}

}
