<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload page</title>
<script type="text/javascript" src="js/lib/jquery1.8.1.js"></script>
<script type="text/javascript" src="js/lib/jquery.form.js"></script>
</head>
<body>
<form id="uploadFileId" action="upload/autoUploadFile.do" enctype="multipart/form-data" method="post">
	<div class="u">
		<div class="displayInfo"></div>
		<label>
			file:<input name="uploadFile" type="file" accept="text/html,text/plain" onchange="c()"/>
		</label>
	</div>
</form>
<script type="text/javascript">
	function c (){
		console.log(1);
		var opt = {
			url:"upload/autoUploadFile.do",
			type:"POST",
			data:$('#uploadFileId').serialize(),
			dataType:"json",//约定返回的数据格式
			success:function(resp){
				$('.displayInfo').html(resp.info);
			}
		};
		$('#uploadFileId').ajaxSubmit(opt);
	}
</script>
</body>
</html>