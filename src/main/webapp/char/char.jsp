<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>char demo page</title>
<script type="text/javascript" src="../js/lib/jquery1.8.1.js"></script>
<script type="text/javascript" src="../js/lib/Chart.js"></script>
<style type="text/css">
	.sDiv{
		text-align: center;
		height: 500px;
		line-height: 500px;
		margin-top:150px;
	}
</style>
</head>
<body>
	<div class="sDiv">
		<canvas id="myChart" width="900" height="400"></canvas>
	</div>
	<script type="text/javascript">
		$(function(){
			var currTime = new Date("2014-09-23 15:52:24");
			var arrLabels = new Array();
			for(var i = 0; i<= 60 ;i++){
				currTime.setMilliseconds(currTime.getMilliseconds()+i);
				arrLabels[i] = i+"";
			}
			console.log(arrLabels);
			var data = {
					labels:arrLabels,
					//labels:['15:52:24','15:52:25','15:52:26','15:52:27','15:53:22','15:53:23'],
					datasets:[{
					          data:[14,38,50,46,55,48,49,48,50,49,50,52,51,50,47,48,40,46,51,46,49,47,52,46,48,50,47,50,48,51,50,49,50,51,49,49,46,49,50,45,48,47,45,48,48,49,50,46,50,48,49,50,49,47,49,51,50,51,48,32]
					}]
			};
			var ctx = $('#myChart').get(0).getContext("2d");
			//数据，选项设置
			var myChart = new Chart(ctx).Line(data,/* {datasetFill:false} */null);
		});
	</script>
</body>
</html>