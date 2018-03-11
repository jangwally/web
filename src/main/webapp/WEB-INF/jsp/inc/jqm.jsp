<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

<link href="app/css/mystyle.css" rel="stylesheet" type="text/css">
<link href="app/css/jquery.mobile-1.4.2.css" rel="stylesheet" type="text/css">
<script src="app/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/jquery.mobile-1.4.2.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/html5zoo.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/lovelygallery.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/qrcode.js" type="text/javascript" charset="utf-8"></script>

<script src="app/js/fontscroll.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/touchslider.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/common.js" type="text/javascript" charset="utf-8"></script>
<script src="app/js/datePicker.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/ajax2.js"></script>
<script src="http://api.map.baidu.com/api?v=1.2"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ry" uri="http://www.ruanyun.com/taglib/ry" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ry:binding type="2"></ry:binding>
<ry:binding type="3"></ry:binding>