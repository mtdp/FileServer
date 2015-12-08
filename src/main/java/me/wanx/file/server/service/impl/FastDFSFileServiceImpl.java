package me.wanx.file.server.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import me.wanx.file.server.response.CommonResponse;
import me.wanx.file.server.response.DownloadFileResponse;
import me.wanx.file.server.response.UploadFileResponse;
import me.wanx.file.server.service.FastDFSFileService;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
* @ClassName: FastDFSFileServiceImpl 
* @Description: 实现fastdfs上传,下载,删除文件  
* @author gqwang
* @date 2015年12月8日 上午9:25:07 
*
 */

@Service
public class FastDFSFileServiceImpl implements FastDFSFileService {
	
	private static Logger logger = LoggerFactory.getLogger(FastDFSFileServiceImpl.class);
	
	private TrackerClient trackerClient = null;
	
	private TrackerServer trackerServer = null;
	
	private StorageClient storageClient = null;
	
	/**
	 * 初始化fastdfs-client配置
	 */
	public FastDFSFileServiceImpl(){
		this.init();
	}
	
	private void init(){
		URL url = FastDFSFileServiceImpl.class.getResource("/fastdfs-client.properties");
		try {
			ClientGlobal.init(url.getPath());
			//初始化tracker 及 storage
			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
			storageClient = new StorageClient(trackerServer,null);
		} catch (FileNotFoundException e) {
			logger.error("fastdfs client配置文件不存在",e);
		} catch (IOException e) {
			logger.error("读取配置文件异常",e);
		} catch (MyException e) {
			logger.error("读取配置文件异常",e);
		}
	}

	@Override
	public CommonResponse<UploadFileResponse> upload(File file) {
		CommonResponse<UploadFileResponse> commonResp = new CommonResponse<UploadFileResponse>();
		ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream(1024*8);
		FileInputStream fis = null;
		try {
			String extName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			fis = new FileInputStream(file);
			byte[] buff = new byte[1024*8];
			int n = -1;
			while((n = fis.read(buff)) != -1){
				byteArrOS.write(buff, 0, n);
			}
			byte[] fileByte = byteArrOS.toByteArray();
			//resultArr[0]=group name,resultArr[1]=new file name and path
			String[] resultArr = this.storageClient.upload_file(fileByte, extName, null);
			logger.info("-----------resultArr={}",Arrays.toString(resultArr));
			if(resultArr.length >= 2){
				UploadFileResponse respData = new UploadFileResponse();
				respData.setGroupName(resultArr[0]);
				respData.setOriginalFileName(file.getName());
				String newFileName = resultArr[1].substring(resultArr[1].lastIndexOf("/") + 1);
				respData.setUploadFileName(newFileName);
				String newFilePath = resultArr[1].substring(0,resultArr[1].lastIndexOf("/") + 1);
				respData.setUploadFilePath(newFilePath);
				commonResp.setRespData(respData);
			}
		} catch (IOException e) {
			logger.error("获取TrackerServer异常",e);
			commonResp.setResp(false);
		} catch (MyException e) {
			logger.error("上传文件异常",e);
			commonResp.setResp(false);
		}finally{
			try {
				fis.close();
				byteArrOS.close();
			} catch (IOException e) {
				logger.error("关闭文件流异常",e);
			}
		}
		commonResp.setResp(true);
		return commonResp;
	}

	@Override
	public CommonResponse<DownloadFileResponse> download(String fileName,String filePath,String groupName) {
		CommonResponse<DownloadFileResponse> commonResp = new CommonResponse<DownloadFileResponse>();
		String remoteName = filePath + fileName;
		try {
			byte[] byteArr = this.storageClient.download_file(groupName, remoteName);
			DownloadFileResponse respData = new DownloadFileResponse();
			respData.setByteArr(byteArr);
			commonResp.setRespData(respData);
		} catch (IOException e) {
			logger.error("下载文件出错",e);
			commonResp.setResp(false);
		} catch (MyException e) {
			logger.error("下载文件出错",e);
			commonResp.setResp(false);
		}
		commonResp.setResp(true);
		return commonResp;
	}
	
	@Override
	public boolean delete(String fileName, String filePath, String groupName) {
		String remoteName = filePath + fileName;
		try {
			int n = this.storageClient.delete_file(groupName, remoteName);
			logger.info("删除文件返回code={}",n);
			if( 0 == n){
				return true;
			}
		} catch (IOException e) {
			logger.error("删除文件出错",e);
			return false;
		} catch (MyException e) {
			logger.error("删除文件出错",e);
			return false;
		}
		return false;
	}

}
