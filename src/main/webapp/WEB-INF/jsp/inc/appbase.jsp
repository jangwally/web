<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<script src="app/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/fontscroll.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/touchslider.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/common.js" type="text/javascript" charset="utf-8"></script>
<link href="app/css/style.css" rel="stylesheet" type="text/css">

