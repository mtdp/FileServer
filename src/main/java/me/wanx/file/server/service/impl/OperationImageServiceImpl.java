package me.wanx.file.server.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import me.wanx.file.server.service.OperationImageService;
import me.wanx.file.server.thumb.Thumbs;
import me.wanx.file.server.utils.FileServerUtils;

import org.apache.commons.io.FileUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OperationImageServiceImpl implements OperationImageService {
	
	private static Logger logger = LoggerFactory.getLogger(OperationImageServiceImpl.class);
	
	/** GraphicsMagick 的安装路径  window系统下使用**/
	@Value("${graphicsMagickPath}")
	private String graphicsMagickPath;
	
	/** 保存缩略图的基础路径 **/
	@Value("${saveThumbBasePath}")
	private String saveThumbBasePath;
	
	/** 原图的基础路径 **/
	@Value("${originalBasePath}")
	private String originalBasePath;
	
	@Override
	public String compressImage(String imageUrl) {
		//  /thumb/test/3.jpg_t1.jpg
		//如果imageurl不是以上规则就不需要以下这一个步骤
		String oldImagePath = imageUrl.substring(imageUrl.indexOf("/", 1));
		String originalImagePath = oldImagePath.split(Thumbs.UNDERLINE)[0];
		//1.判断缩略图片是否存在
		String thumbImageFullPath = saveThumbBasePath + oldImagePath;
		File thumbFile = new File(thumbImageFullPath);
		if(thumbFile.exists()){
			//存在
			logger.error("缩略图:【{}】,存在!",thumbImageFullPath);
			return "";
		}
		//2.找到原图地址
		String originalImageFullPath = originalBasePath + originalImagePath;
		File originalFile = new File(originalImageFullPath);
		if(! originalFile.exists()){
			//原图不存在
			logger.error("原图:【{}】,不存在",originalImageFullPath);
			return "";
		}
		//3.压缩
		//获取压缩参数类型
		String compressArgsType = imageUrl.split(Thumbs.UNDERLINE)[1].split(Thumbs.CUT_POINT)[0];
		LinkedHashMap<String,String> curCompressArg = Thumbs.thumbArgsMap.get(compressArgsType);
		//全局参数
		LinkedHashMap<String,String> globalCompressArg = Thumbs.thumbArgsMap.get(Thumbs.THUMB_GLOBAL);
		//将全局参数设置到当前压缩参数中
		curCompressArg.putAll(globalCompressArg);
		GMOperation gmop = new GMOperation();
		//添加原图
		gmop.addImage(originalImageFullPath);
		//设置压缩参数
		for(Iterator<String> ite = curCompressArg.keySet().iterator(); ite.hasNext();){
			String key = ite.next();
			gmop.addRawArgs(Thumbs.LINK + key,curCompressArg.get(key));
		}
		//添加压缩后的图片位置
		//判断缩略图路径是否存在
		String makeThumbPath = thumbImageFullPath.substring(0,thumbImageFullPath.lastIndexOf("/"));
		//路径如果不存在创建
		new File(makeThumbPath).mkdirs();
		gmop.addImage(thumbImageFullPath);
		//使用graphicsMagickPath 压缩
		ConvertCmd cmd = new ConvertCmd(true);
		//在windows 系统要设置路径 linux 不需要
		if(FileServerUtils.getCurrentOSName().toLowerCase().indexOf(FileServerUtils.OSName4Windows.toLowerCase()) != -1){
			cmd.setSearchPath(graphicsMagickPath);
		}
		try {
			cmd.run(gmop);
		} catch (Exception e) {
			logger.error("压缩图片出错:",e);
		}
		logger.info("图片已经压缩成功:{}",thumbImageFullPath);
		return thumbImageFullPath;
	}

	@Override
	public OutputStream compressImage4Stream(String imageUrl) {
		try {
			return new FileOutputStream(this.compressImage(imageUrl));
		} catch (FileNotFoundException e) {
			logger.error("返回图片输出流出错!",e);
		}
		return null;
	}
	
	@Override
	public String uploadImage(byte[] bytes, String fileName, String category,
			String project, String dataPatten) {
		//上传返回的路径
		StringBuffer resultFileUri = new StringBuffer();
		//上传图片的全路径
		String descFileFullPath = this.originalBasePath + Thumbs.SEPARATOR;
		//图片后缀
		String fileSuffix = fileName.split("\\.")[1];
		
		if(null != project && project != ""){
			resultFileUri.append(project).append(Thumbs.SEPARATOR);
		}
		if(null != category && category != ""){
			resultFileUri.append(category).append(Thumbs.SEPARATOR);
		}
		//添加日期为路径
		if(null != dataPatten && dataPatten != ""){
			SimpleDateFormat sdf = new SimpleDateFormat(dataPatten);
			resultFileUri.append(sdf.format(new Date())).append(Thumbs.SEPARATOR);
		}
		resultFileUri.append(FileServerUtils.getUUID()).append(Thumbs.POINT).append(fileSuffix);
		descFileFullPath += resultFileUri;
		File descFile = new File(descFileFullPath);
		//路径是否存在
		if(! descFile.getParentFile().exists()){
			descFile.getParentFile().mkdirs();
		}
		try {
			FileUtils.writeByteArrayToFile(descFile, bytes);
		} catch (IOException e) {
			logger.error("拷贝文件出错!",e);
		}
		return resultFileUri.toString();
	}

	public static void main(String[] args) {
		//new OperationImageServiceImpl().compressImage("/thumb/test/3.jpg_t1.jpg");
		//new File("d:/media/thumb/test").mkdirs();
	}
	

}
