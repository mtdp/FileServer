package me.wanx.file.server.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.wanx.file.server.response.ResultMessage;

public class BaseAction{
	private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);
	
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	
	private String charset = "UTF-8";
	private String contentType = "text/plain; charset=UTF-8";
	
	//每个请求都执行
	@ModelAttribute
	public void setRequestAndResponse(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	
	public void setResultMessage4Json(ResultMessage msg){
		//设置返回编码
		response.setContentType(this.contentType);
		response.setCharacterEncoding(this.charset);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			logger.error("获取输出流出错!",e);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			String resultJson = mapper.writeValueAsString(msg);
			pw.write(resultJson);
			pw.flush();
		} catch (Exception e) {
			logger.error("输出json出错!",e);
		}finally{
			pw.close();
		}
	}
	
	public void setResultMessage4Json(String code,String info){
		setResultMessage4Json(new ResultMessage(code, info));
	}
	
}
