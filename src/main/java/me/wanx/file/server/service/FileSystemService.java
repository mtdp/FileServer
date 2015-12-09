package me.wanx.file.server.service;

import me.wanx.file.server.response.CommonResponse;
import me.wanx.file.server.response.DownloadFileResponse;
import me.wanx.file.server.response.UploadFileResponse;

/**
 * 
* @ClassName: FileSystemService 
* @Description: 通过文件服务器上传,下载,删除文件 
* @author gqwang
* @date 2015年12月8日 上午9:17:27 
*
 */
public interface FileSystemService {
	
	/**
	 * 上传文件
	 * @param fileByte 上传文件的字节数组
	 * @param extName 上传文件的后缀
	 * @return
	 */
	public CommonResponse<UploadFileResponse> upload(byte[] fileByte,String extName);
	
	/**
	 * 
	 * 下载文件
	 * @param fileName 文件名称,不包括后缀
	 * @param remoteFilePath 存储服务器的文件路径
	 * @return
	 */
	public CommonResponse<DownloadFileResponse> download(String fileName,String remoteFilePath);
	
	/**
	 * 删除文件
	 * @param fileName 文件名称,不包括后缀
	 * @param remoteFilePath 存储服务器的文件路径
	 * @return
	 */
	public boolean delete(String fileName,String remoteFilePath);
	
	
}
