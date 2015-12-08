package me.wanx.file.server.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.wanx.file.server.response.CommonResponse;
import me.wanx.file.server.response.DownloadFileResponse;
import me.wanx.file.server.response.UploadFileResponse;
import me.wanx.file.server.service.FastDFSFileService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FastDFSFileServiceTest extends BaseTest{

	@Autowired
	private FastDFSFileService fastDFSFileService;
	
	@Test
	public void testUpload(){
		CommonResponse<UploadFileResponse> commResp = this.fastDFSFileService.upload(new File("d:/tmp/mon.txt"));
		System.out.println("***************"+commResp);
	}
	
	@Test
	public void testDownload(){
		CommonResponse<DownloadFileResponse> commResp = this.fastDFSFileService.download("CjDCpVZmP5qAC6XeAAJJEKCAPbs050.txt", "M00/00/00/", "group1");
		System.out.println("***************"+commResp);
		if(commResp.isResp()){
			byte[] byteArr = commResp.getRespData().getByteArr();
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File("d:/tmp/fdfs/t.txt"));
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
		boolean b = this.fastDFSFileService.delete("CjDCpVZmQkKAYZ8nAAACC8-oMqo324.txt", "M00/00/00/", "group1");
		System.out.println(b == true ? "success" : "fail");
	}
}
