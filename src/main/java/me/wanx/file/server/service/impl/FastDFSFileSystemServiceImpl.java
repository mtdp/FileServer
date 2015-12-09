package me.wanx.file.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import me.wanx.file.server.response.CommonResponse;
import me.wanx.file.server.response.DownloadFileResponse;
import me.wanx.file.server.response.UploadFileResponse;
import me.wanx.file.server.service.FileSystemService;

import org.apache.commons.lang.StringUtils;
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
* @ClassName: FastDFSFileSystemServiceImpl 
* @Description: 实现fastdfs上传,下载,删除文件  
* @author gqwang
* @date 2015年12月8日 上午9:25:07 
*
 */

@Service
public class FastDFSFileSystemServiceImpl implements FileSystemService {
	
	private static Logger logger = LoggerFactory.getLogger(FastDFSFileSystemServiceImpl.class);
	
	private TrackerClient trackerClient = null;
	
	private TrackerServer trackerServer = null;
	
	private StorageClient storageClient = null;
	
	/**默认文件后缀 fdfs存储文件统一后缀**/
	private String defaultFileExt = "fdfs";
	
	/**
	 * 初始化fastdfs-client配置
	 */
	public FastDFSFileSystemServiceImpl(){
		this.init();
	}
	
	private void init(){
		URL url = FastDFSFileSystemServiceImpl.class.getResource("/fastdfs-client.properties");
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
	public CommonResponse<UploadFileResponse> upload(byte[] fileByte,String extName) {
		CommonResponse<UploadFileResponse> commonResp = new CommonResponse<UploadFileResponse>();
		try {
			if(StringUtils.isBlank(extName)){
				//缀默认后缀
				extName = defaultFileExt;
			}
			String[] resultArr = this.storageClient.upload_file(fileByte, extName, null);
			logger.info("-----------upload file resultArr={}",Arrays.toString(resultArr));
			if(resultArr.length >= 2){
				UploadFileResponse respData = new UploadFileResponse();
				String newFileName = resultArr[1].substring(resultArr[1].lastIndexOf("/") + 1);
				//剔除文件后缀
				newFileName = newFileName.split("\\.")[0];
				respData.setUploadFileName(newFileName);
				String newFilePath =resultArr[0] + "/" + resultArr[1].substring(0,resultArr[1].lastIndexOf("/") + 1);
				respData.setUploadFilePath(newFilePath);
				commonResp.setRespData(respData);
			}
		} catch (IOException e) {
			logger.error("获取TrackerServer异常",e);
			commonResp.setResp(false);
		} catch (MyException e) {
			logger.error("上传文件异常",e);
			commonResp.setResp(false);
		}
		commonResp.setResp(true);
		return commonResp;
	}
	
	/*
	private CommonResponse<UploadFileResponse> upload(File file) {
		CommonResponse<UploadFileResponse> commonResp = new CommonResponse<UploadFileResponse>();
		String extName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream(1024*8);
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			byte[] buff = new byte[1024*8];
			int n = -1;
			while((n = fis.read(buff)) != -1){
				byteArrOS.write(buff, 0, n);
			}
			byte[] fileByte = byteArrOS.toByteArray();
			CommonResponse<UploadFileResponse> comResp = this.upload(fileByte, extName);
			BeanUtils.copyProperties(comResp, commonResp);
		}catch(IOException e){
			logger.error("上传文件file出错",e);
			commonResp.setResp(false);
		}finally{
			try {
				fis.close();
				byteArrOS.close();
			} catch (IOException e) {
				logger.error("关闭流出错",e);
			}
		}
		return commonResp;
	}
	 */
	
	@Override
	public CommonResponse<DownloadFileResponse> download(String fileName,String remoteFilePath) {
		CommonResponse<DownloadFileResponse> commonResp = new CommonResponse<DownloadFileResponse>();
		StringBuffer sb = new StringBuffer(remoteFilePath.substring(remoteFilePath.indexOf("/") + 1));
		sb.append(fileName);
		if(fileName.lastIndexOf(".") == -1){
			sb.append(".").append(this.defaultFileExt);
		}
		String remoteName = sb.toString();
		String groupName = remoteFilePath.substring(0,remoteFilePath.indexOf("/"));
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
	public boolean delete(String fileName, String remoteFilePath) {
		StringBuffer sb = new StringBuffer(remoteFilePath.substring(remoteFilePath.indexOf("/") + 1));
		sb.append(fileName);
		if(fileName.lastIndexOf(".") == -1){
			sb.append(".").append(this.defaultFileExt);
		}
		String remoteName = sb.toString();
		String groupName = remoteFilePath.substring(0,remoteFilePath.indexOf("/"));
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
	
	public static void main(String[] args) {
		String fileName = "group1/M00/00/00/ttt.txt";
		System.out.println(fileName.indexOf("."));
		System.out.println(fileName.substring(0,fileName.indexOf("/")));
		System.out.println(fileName.substring(fileName.indexOf("/")+1));
	}

}
