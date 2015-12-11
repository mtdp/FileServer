package me.wanx.file.server.service;

import java.io.File;

/**
 * 
* @ClassName: FileSystemBizService 
* @Description: 文件服务业务service,文件相关信息落地数据库
* @author gqwang
* @date 2015年12月10日 下午12:57:45 
*
 */
public interface FileSystemBizService {
	
	public String upload(byte[] fileByte,String extName);
	
	public String upload(File file);

}
