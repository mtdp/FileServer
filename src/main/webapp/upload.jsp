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
<form id="uploadFileId" action="upload/uploadFile.do" enctype="multipart/form-data" method="post">
	<div class="u">
		<div class="displayInfo"></div>
		<label>
			name:<input name="name" type="text"/>
		</label>
		<br/>
		<label>
			file:<input name="uploadFile" type="file"/>
		</label>
		<br/>
		<input type="button" value="upload" id="buttonId"/>
	</div>
</form>
<script type="text/javascript">
	$(function(){
		$('#buttonId').on('click',function(){
			console.log("button click");
			//禁用按钮
			$('#buttonId').attr('disabled','disabled');
			var opt = {
					url:"upload/uploadFile.do",
					type:"POST",
					data:$('#uploadFileId').serialize(),
					dataType:"json",//约定返回的数据格式
					success:function(resp){
						$('.displayInfo').html(resp.info);
						//启用
						$('#buttonId').removeAttr('disabled');
					}
			};
			/* 
			此种方法上传文件 不可以
			$.ajax({
				url:"upload/uploadFile.do",
				type:"POST",
				data:$('#uploadFileId').serialize(),
				success:function(resp){
					console.log(resp);
				}
			}); */
			$('#uploadFileId').ajaxSubmit(opt);
			console.log('start upload');
		});
	});
</script>
</body>
</html>