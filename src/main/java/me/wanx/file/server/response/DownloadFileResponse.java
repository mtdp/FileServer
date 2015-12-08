package me.wanx.file.server.response;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
* @ClassName: UploadFileResponse 
* @Description: 上传文件返回 
* @author gqwang
* @date 2015年12月8日 上午9:18:19 
*
 */
public class DownloadFileResponse implements Serializable{

	private static final long serialVersionUID = 33078302956162252L;
	
	private File file;

	/**文件的字节数组**/
	private byte[] byteArr;
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getByteArr() {
		return byteArr;
	}

	public void setByteArr(byte[] byteArr) {
		this.byteArr = byteArr;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
