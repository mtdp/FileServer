package me.wanx.file.server.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: FileServerUtils 
* @Description: 工具类 
* @author gqwang
* @date 2014年11月14日 上午11:18:53 
*
 */
public class FileServerUtils {
	private static Logger logger = LoggerFactory.getLogger(FileServerUtils.class);
	
	public static String OSName4Windows = "windows";
	
	public static String getCurrentOSName(){
		return System.getProperty("os.name");
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static void main(String[] args) {
		logger.info(getCurrentOSName());
		int b = FileServerUtils.getCurrentOSName().toLowerCase().indexOf(FileServerUtils.OSName4Windows);
		logger.info(""+b);
	}

}
