package me.wanx.file.server.response;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
* @ClassName: CommonResponse 
* @Description: 请求想要基类 
* @author gqwang
* @date 2015年12月8日 上午9:18:45 
*
 */
public class CommonResponse<T> implements Serializable{
	
	private static final long serialVersionUID = 4076783606157268701L;
	
	/**处理成功发回 true **/
	private boolean isResp;
	
	private String errorCode;
	
	private String errorMsg;
	
	private Throwable throwable;
	
	/** 响应数据 **/
	private T respData;

	public boolean isResp() {
		return isResp;
	}

	public void setResp(boolean isResp) {
		this.isResp = isResp;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public T getRespData() {
		return respData;
	}

	public void setRespData(T respData) {
		this.respData = respData;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
