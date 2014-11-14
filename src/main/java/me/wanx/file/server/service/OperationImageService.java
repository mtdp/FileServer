package me.wanx.file.server.service;

import java.io.OutputStream;

public interface OperationImageService{
	
	/** 
	 * 返回压缩图片的url 
	 * @param imageUrl
	 * @return
	 */
	public String compressImage(String imageUrl);
	
	/**
	 * 返回压缩图片的输出流
	 * @param imageUrl
	 * @return
	 */
	public OutputStream compressImage4Stream(String imageUrl);
	

	/**
	 * 
	 * @param bytes
	 * @param fileName
	 * @param category    目录
	 * @param project     项目名称
	 * @param dataPatten  日期格式
	 * @return
	 */
	public String uploadImage(byte[] bytes,String fileName,String category,String project,String dataPatten);
} 