package me.wanx.file.server.action;

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
		
		this.setResultMessage4Json("success", "处理成功"); 
	}
	
}
