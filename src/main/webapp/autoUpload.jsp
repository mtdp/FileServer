<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload page</title>
<script type="text/javascript" src="js/lib/jquery1.8.1.js"></script>
<script type="text/javascript" src="js/lib/jquery.form.js"></script>
<script type="text/javascript" src="js/lib/jquery-ui.js"></script>
<script type="text/javascript" src="js/lib/jquery-ui-i18n.js"></script>
</head>
<body>
<form id="uploadFileForm" action="upload/autoUploadFile.do" enctype="multipart/form-data" method="post">
	<div class="u">
		<div class="displayInfo"></div>
		<label>
			file:<input id="uploadFileId" name="uploadFile" type="file" accept="text/html,text/plain" onchange="c(this)"/>
		</label>
		<label>
			jquery dialog:<input id="openId" type="button" value="open jquery dialog"/>
		</label>
		<div id="showDialog" style="display:none;">
			hello jquery dialog!
		</div>
	</div>
</form>
<script type="text/javascript">
	function c ($this){
		/* 
		//复制file文件
		var file = $('#uploadFileId');
		console.log(file);
		file.after(file.clone().val("test"));
		file.remove();
		 */
		var opt = {
			url:"upload/autoUploadFile.do",
			type:"POST",
			data:$('#uploadFileForm').serialize(),
			dataType:"json",//约定返回的数据格式
			success:function(resp){
				console.log(resp);
				console.log(resp.info);
				console.log(resp.obj.msg);
				$('.displayInfo').html(resp.info);
			}
		};
		$('#uploadFileForm').ajaxSubmit(opt);
	}
	
	$('#openId').on('click',function(){
		//$('#showDialog').dialog('open');
		$('#showDialog').dialog({
			 bgiframe : true,
			   autoOpen : false,
			   height   : '160px',
			   width    : '360px',
			   title	: "jquery dialog",
			   modal 	: true
		}).dialog('open');
	});
	
	function test$(){
		console.log(11);
	}
	function test$$(){
		//window.setInterval("console.log(22)",5000);
	}
	//window.onload=test$;
	$(function(){
		test$$();
	});
</script>
</body>
</html>