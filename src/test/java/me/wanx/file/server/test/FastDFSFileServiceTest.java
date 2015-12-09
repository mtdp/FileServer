package me.wanx.file.server.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import me.wanx.file.server.response.CommonResponse;
import me.wanx.file.server.response.DownloadFileResponse;
import me.wanx.file.server.response.UploadFileResponse;
import me.wanx.file.server.service.FileSystemService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FastDFSFileServiceTest extends BaseTest{

	@Autowired
	private FileSystemService fastDFSFileService;
	
	@Test
	public void testUpload(){
		CommonResponse<UploadFileResponse> commResp = null;
		
		ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream(1024*8);
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(new File("d:/tmp/1.jpg"));
			byte[] buff = new byte[1024*8];
			int n = -1;
			while((n = fis.read(buff)) != -1){
				byteArrOS.write(buff, 0, n);
			}
			byte[] fileByte = byteArrOS.toByteArray();
			commResp = this.fastDFSFileService.upload(fileByte, "");
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("***************"+commResp);
	}
	
	@Test
	public void testDownload(){
		CommonResponse<DownloadFileResponse> commResp = this.fastDFSFileService.download("CjDCpVZn1CKANo9fAAAFvCUFja097", "group1/M00/00/00/");
		System.out.println("***************"+commResp);
		if(commResp.isResp()){
			byte[] byteArr = commResp.getRespData().getByteArr();
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File("d:/tmp/fdfs/ttt.jpg"));
				fos.write(byteArr);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("success");
	}
	
	@Test
	public void testDelete(){
		boolean b = this.fastDFSFileService.delete("CjDCpVZngSeAK6DoAAACC8-oMqo14", "group1/M00/00/00/");
		System.out.println(b == true ? "success" : "fail");
	}
	
}
