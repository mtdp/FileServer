package me.wanx.file.server.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import me.wanx.file.server.service.OperationImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Scope(value = "prototype")
public class OperationImageAction extends BaseAction{
	private static final Logger logger = LoggerFactory.getLogger(OperationImageAction.class);
	
	@Autowired
	private OperationImageService operationImageService;
	
	/**
	 * 压缩图片并返回缩略图
	 * @param thumbImageUrl
	 * @return
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value = "thumbPic/compressImage.do", method = RequestMethod.GET)
	public @ResponseBody void  thumbImages(@RequestParam(value="thumbPicUrl") String thumbPicUrl,HttpServletResponse response) throws FileNotFoundException{
		response.setContentType("image/jpg");
		logger.info("thumbPicUrl的值是:{}",thumbPicUrl);
		String outImageUrl = operationImageService.compressImage(thumbPicUrl);
		try {
			OutputStream out = response.getOutputStream();
			InputStream in = new FileInputStream(outImageUrl);
			byte[] b = new byte[8*1024];
			int r;
			while((r = in.read(b)) != -1){
				out.write(b, 0, r);
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			logger.error("返回图片出错:"+e);
		}
	}
	
	/***
	 * 上传图片并返回图片的url
	 * @param uploadImage
	 * @return
	 */
	@RequestMapping("upload/uploadImage.do")
	public @ResponseBody void uploadImage(@RequestParam(value="uploadImage") MultipartFile uploadImage){
		String uploadImageOriginalName = uploadImage.getOriginalFilename();
		String uploadImagePath = "";
		try {
			uploadImagePath = operationImageService.uploadImage(uploadImage.getBytes(), uploadImageOriginalName, null, "test", null);
		} catch (IOException e) {
			logger.error("上传图片出错!",e);
		}
		this.setResultMessage4Json("true", uploadImagePath);
	}
	
	/***
	 * 上传多张图片并返回图片的url
	 * @param uploadImage
	 * @return
	 */
	@RequestMapping("upload/uploadImages.do")
	public String uploadImages(@RequestParam(value="uploadImage") MultipartFile[] uploadImages){
		//TODO
		return"";
	}
	
	@ResponseBody
	@RequestMapping("test/test.do")
	public String test(ModelMap model){
		logger.error(""+this.response);
		model.addAttribute("test", "test value");
		return "测试中文";
	}
	
	@ResponseBody
	@RequestMapping("test/test2.do")
	public foo resultJson(){
		foo f = new foo("test","测试中文");
		return f;
	}
	
	class foo implements Serializable{
		private static final long serialVersionUID = 1L;
		public foo(){}
		public foo(String id,String name){
			this.id=id;
			this.name=name;
		}
		String id;
		String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}