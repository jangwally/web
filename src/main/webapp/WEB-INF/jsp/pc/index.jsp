<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html >
<html >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<link href="css/centen.css" rel="stylesheet" type="text/css">
	<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
	<title><ry:showWeb name='MANAGER_PROJECT_NAME'></ry:showWeb></title>
	<%@include file="/WEB-INF/jsp/inc/base-dwz.jsp" %>
	<link rel="shortcut icon" href="all_references/<ry:showWeb name="PROJECT_PATH"></ry:showWeb>/img/favicon.png" type="image/x-icon">
	 <ry:binding type="3"></ry:binding>
	<style>
		body{background: #F5F5F5;}
		*{ margin:0; padding:0; font-family:"微软雅黑"; font-size: 12px;color: black;}
		h1,h2,h3,h4,h5,h6{ font-weight:normal;}
		.biaoge{width: 94%;margin: 20px 3%;}
		.biaoge h3{width: 100%;background: #e1e1e1;font-size: 14px;height: 30px;line-height:30px;
			text-indent: 20px;color: #323232;font-weight: bold;}
		.biaoge table{background: white;width: 100%;border: 1px solid #d6d7dc;border-bottom: none;}
		.biaoge table tr th{height: 30px;border-bottom: 1px solid #E1E1E1;}
		.biaoge table tr td{height: 30px;border-bottom: 1px solid #E1E1E1;text-align: center;color: #646464;}
	</style>
</head>
<body scroll="no">
    <!-- 整个index主页显示区域 -->
	<div id="layout">
		<!-- 头部-->
		<%@include file="/WEB-INF/jsp/inc/header.jsp" %>
		<!-- 左边菜单栏 -->
		<%@include file="/WEB-INF/jsp/inc/left.jsp" %>

		<!-- main区域 -->
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
						    <li tabid="main" class="main"><a href="user/index"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
				    <li><a href="javascript:;">我的主页</a></li>
				</ul>
				<!-- 数据显示面板 -->
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox" >
					    <!-- 面板显示区域的头部 -->

						<ry:authorize ifAllGranted="${authMap.SYSTEM_AUTH}">
						<div class="biaoge">
							<h3>用户信息</h3>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th>今日注册量</th>
									<th>用户总量</th>
								</tr>
								<tr>
									<td>${day}</td>
									<td>${all}</td>
								</tr>
							</table>
						</div>
						<div class="biaoge">
							<h3>资金信息</h3>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th>今日入金</th>
									<th>今日出金</th>
									<th>总出金</th>
								</tr>
								<tr>
									<td>${transactionOrder}</td>
									<td>${withdraw}</td>
									<td>${allWithdraw}</td>
								</tr>

							</table>
						</div>
						<div class="biaoge">
							<h3>下单信息</h3>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th>今日服务费</th>
									<th>今日管理费</th>
									<th></th>

								</tr>
								<tr>
									<td>${service}</td>
									<td>${manage}</td>
									<td></td>


								</tr>
								<tr>
									<th>总服务费</th>
									<th>总管理费</th>
									<th>合计</th>
								</tr>
								<tr>
									<td>${serviceAll}</td>
									<td>${manageAll}</td>
									<td>${smAll}</td>
								</tr>
							</table>
						</div>
						</ry:authorize>

					</div>
				</div>
			</div>

		</div>
	</div>

	<!-- footer底部信息 -->
	<%--<div id="footer">Copyright &copy; 2017安徽软云信息科技有限公司 <!-- Tel：0551-52897073 --></div>--%>




</body>

</html>