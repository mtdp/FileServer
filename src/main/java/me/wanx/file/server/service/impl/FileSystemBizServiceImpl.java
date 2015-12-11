package me.wanx.file.server.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import me.wanx.file.server.response.CommonResponse;
import me.wanx.file.server.response.UploadFileResponse;
import me.wanx.file.server.service.FileSystemBizService;
import me.wanx.file.server.service.FileSystemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileSystemBizServiceImpl implements FileSystemBizService{
	
	private static Logger logger = LoggerFactory.getLogger(FileSystemBizServiceImpl.class);
	
	@Autowired
	private FileSystemService fileSystemService;

	@Override
	public String upload(byte[] fileByte, String extName) {
		String remoteName = null;
		//1.调用上传文件服务
		CommonResponse<UploadFileResponse> comResp = this.fileSystemService.upload(fileByte, "");
		if(comResp.isResp()){
			UploadFileResponse resp = comResp.getRespData();
			remoteName = resp.getUploadFileName();
			String remoteFilePath = resp.getUploadFilePath();
			//2.保存文件的存储路径及文件名称 TODO
			
		}
		//3.将文件服务器的文件名称返回
		return remoteName;
	}

	@Override
	public String upload(File file) {
		String remoteName = null;
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
			remoteName = this.upload(fileByte, extName);
		}catch(IOException e){
			logger.error("上传文件出错",e);
			return remoteName;
		}finally{
			try{
				fis.close();
				byteArrOS.close();
			}catch(IOException e){
				logger.error("关闭流出错",e);
			}
		}
		return remoteName;
	}

}
