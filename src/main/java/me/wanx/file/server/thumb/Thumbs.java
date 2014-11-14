package me.wanx.file.server.thumb;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 
* @ClassName: Thumbs 
* @Description: 压缩图片 配置类 
* @author gqwang
* @date 2014年11月14日 上午11:22:04 
*
 */
@Service
public class Thumbs {
	private static Logger logger = LoggerFactory.getLogger(Thumbs.class);
	
	public static final String POINT = ".";
	public static final String CUT_POINT = "\\.";
	public static final String LINK = "-";
	public static final String UNDERLINE = "_";
	public static final String IS_EQUAL_TO = "=";
	public static final String SEPARATOR = "/";
	
	/** 压缩参数全局参数 **/
	public static final String THUMB_GLOBAL = "global";
	
	/** 存储压缩图片的配置参数 **/
	public static LinkedHashMap<String,LinkedHashMap<String,String>> thumbArgsMap = new LinkedHashMap<String, LinkedHashMap<String,String>>();
	
	/**
	 * 加载配置文件
	 */
	@PostConstruct
	public void initThumbs(){
		logger.info("Thumbs 配置文件初始化...");
		Properties prop = new Properties();
		try {
			prop.load(Thumbs.class.getResourceAsStream("/thumbImage.properties"));
		} catch (IOException e) {
			logger.error("加载配置文件出错 !",e);
		}
		//获取所有的key
		Set<String> thumbImageKeys = prop.stringPropertyNames();
		//遍历所有的压缩图片keys
		for(Iterator<String> ite = thumbImageKeys.iterator(); ite.hasNext();){
			String key = ite.next();
			String[] preKeys = key.split(Thumbs.CUT_POINT);
			if(preKeys.length != 2){
				logger.error("thumbImage.properties配置参数错误:{}",Arrays.toString(preKeys));
				return;
			}
			if(thumbArgsMap.containsKey(preKeys[0])){
				LinkedHashMap<String,String> curMap = thumbArgsMap.get(preKeys[0]);
				curMap.put(preKeys[1], prop.getProperty(key));
				
			}else{
				LinkedHashMap<String,String> curMap = new LinkedHashMap<String,String>();
				curMap.put(preKeys[1], prop.getProperty(key));
				thumbArgsMap.put(preKeys[0], curMap);
			}
		}
		logger.info("Thumbs 配置文件初始化成功!{}",Thumbs.thumbArgsMap);
	}
	
	public static void main(String[] args) {
		new Thumbs().initThumbs();
		logger.info("配置文件初始化的值为:"+thumbArgsMap);
		System.out.println(Arrays.toString("/thumb/test/3.jpg_t1.jpg".split("[_\\.]")));
	}

}
