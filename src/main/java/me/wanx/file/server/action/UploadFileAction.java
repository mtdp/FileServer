package me.wanx.file.server.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Scope(value = "prototype")
public class UploadFileAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileAction.class);
	
	
	@RequestMapping(value="upload/uploadFile.do",method = RequestMethod.POST)
	public @ResponseBody void testUploadFile(@RequestParam(value="uploadFile") MultipartFile uploadFile,
							   @RequestParam(value="name") String name){
		logger.info("获取的name:"+name);
		String fileName = uploadFile.getOriginalFilename();
		logger.info("获取的文件名是:"+fileName);
		try {
			logger.info("休息6s");
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setResultMessage4Json("success", "处理成功"); 
	}
	
	@RequestMapping(value="upload/autoUploadFile.do",method = RequestMethod.POST)
	public @ResponseBody void testUploadFile(@RequestParam(value="uploadFile") MultipartFile uploadFile){
		String fileName = uploadFile.getOriginalFilename();
		logger.info("获取的文件名是:"+fileName);
		//读取文件
		int index = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(uploadFile.getInputStream()));
			String lineText = null;
			while((lineText = br.readLine()) != null){
				index ++;
			}
		} catch (IOException e1) {
			logger.error("读取文件出错",e1);
		}
		try {
			logger.info("休息2s");
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setResultMessage4Json("success", "处理成功,"+fileName+"文件内容有【"+index+"】行"); 
	}
	
}
