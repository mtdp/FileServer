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
<div>exec task ing</div>
<script type="text/javascript">
		function r() {
			$.ajax({
				url:"http://10.48.171.169/sgsFront/notify/jz/notifyJZMT.action",
				type:"POST",
				data:{"args":"60317924,13917382240,DELIVRD,2015-02-16 09:49:00"},
				dataType:"json",//约定返回的数据格式
				success:function(resp){
					console.log(resp);
				}
			});
		}
		var t = window.setInterval(r(),0);
</script>

<div><input type="button" value="stop setInterval" onclick="window.clearInterval(t)"></input></div>
</body>
</html>