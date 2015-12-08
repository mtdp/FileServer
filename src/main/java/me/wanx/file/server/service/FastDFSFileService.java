package me.wanx.file.server.service;

import java.io.File;

import me.wanx.file.server.response.CommonResponse;
import me.wanx.file.server.response.DownloadFileResponse;
import me.wanx.file.server.response.UploadFileResponse;

/**
 * 
* @ClassName: FastDFSFileService 
* @Description: 通过fastdfs上传,下载,删除文件 
* @author gqwang
* @date 2015年12月8日 上午9:17:27 
*
 */
public interface FastDFSFileService {
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	public CommonResponse<UploadFileResponse> upload(File file);

	/**
	 * 下载文件
	 * @param fileName
	 * @return
	 */
	public CommonResponse<DownloadFileResponse> download(String fileName,String filePath,String groupName);
	
	/**
	 * 删除文件
	 * @param fileName
	 * @param filePath
	 * @param groupName
	 * @return
	 */
	public boolean delete(String fileName,String filePath,String groupName);
	
	
}
