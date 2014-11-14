package me.wanx.file.server.response;

import java.io.Serializable;

/**
 * 
* @ClassName: ResultMessage 
* @Description: TODO 
* @author gqwang
* @date 2014年11月14日 上午11:54:48 
*
 */
public class ResultMessage implements Serializable {
	private static final long serialVersionUID = -2753307512189953499L;
	
	private String code;
	private String info;
	
	public ResultMessage(){
		
	}
	
	public ResultMessage(String code ,String info){
		this.code = code;
		this.info = info;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
