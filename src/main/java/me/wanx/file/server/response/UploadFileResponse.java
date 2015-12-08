package me.wanx.file.server.response;

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
public class UploadFileResponse implements Serializable{

	private static final long serialVersionUID = 33078302956162252L;
	
	/**原文件名称**/
	private String originalFileName;
	
	/**上传的新文件名称**/
	private String uploadFileName;
	
	/**上传的文件存储的目录**/
	private String uploadFilePath;

	/**文件的组名称**/
	private String groupName;

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
