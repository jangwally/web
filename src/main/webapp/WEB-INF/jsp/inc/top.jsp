<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ry" uri="http://www.ruanyun.com/taglib/ry" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>筑心科技，第二代心理云平台领军企业</title>
<meta name="description" content="筑心科技是一家利用移动互联网技术，改造心理行业的新型互联网公司。公司核心产品“第二代心理云平台”，可将工作平台、成长平台、社交平台与电商平台集成于一身，打造心理行业全产业链整合" />
<meta name="keywords" content="筑心,筑心科技,心理云平台,北京筑心科技" />

</head>
<body>
<!--kf-->
<!-- 代码 开始 -->
<div id="rightArrow"><a href="javascript:;" title="在线客户"></a></div>
<div id="floatDivBoxs">
	<div class="floatDtt">QQ在线咨询</div>
    <div class="floatShadow">
        <ul class="floatDqq">
            <li style="padding-left:0px;"><a target="_blank" href="tencent://message/?uin=5277772&Site=www.lanrentuku.com&Menu=yes"><img src="img/qq.png" align="absmiddle" width="30px" style="margin-left: 60px">&nbsp;&nbsp;</a></li>
        </ul>
        <div class="floatDtxt" style="font-size: 14px;text-align: center;">联系电话：</div>
        <div class="floatDtel" >
        	<p style="font-size: 14px"><b>021-51947836</b></p>
        	<p style="font-size: 14px">业务咨询&nbsp;转&nbsp;<b>1</b></p>
        	<p style="font-size: 14px">招商合作&nbsp;转&nbsp;<b>2</b></p>
        	<p style="font-size: 14px">售后服务&nbsp;转&nbsp;<b>3</b></p>
        </div>
        
    </div>
    <div class="floatDbg"></div>
</div>
<!-- 代码 结束 -->
<script>
var flag=1;
$('#rightArrow').click(function(){
	if(flag==1){
		$("#floatDivBoxs").animate({right: '-175px'},300);
		$(this).animate({right: '-5px'},300);
		$(this).css('background-position','-50px 0');
		flag=0;
	}else{
		$("#floatDivBoxs").animate({right: '0'},300);
		$(this).animate({right: '170px'},300);
		$(this).css('background-position','0px 0');
		flag=1;
	}
});
</script>

<!--kf-->
<div class="head_bg"></div>
<div class="head clearfix">
<div class="auto2">
	<h2 class="fl">
    	<a href="#"><img src="img/logo.png" width="135" alt="logo"/></a>
    </h2>
    <p class="fl" style="margin:40px 0 0 20px"><img src="img/hel.png" width="220"/></p>
   <div class="nav fr">
    	<ul class="clearfix">
        	<li><a <c:if test="${css==1}"> class="active"</c:if> href="home">首页</a></li>
            <li><a <c:if test="${css==2}"> class="active"</c:if> href="zxyx">筑心云校</a></li>
            <li>
            	<a   <c:if test="${css==3}"> class="active"</c:if>>筑心产品</a>
                <div class="index_down clearfix">
                	<div class="fl down1">
                    	<span class="down1_span">智能设备</span>
                    	<c:forEach items="${goodstype1}" var="item">
                    	  <div class="down2">
                        	<span style="width: 300px;text-align: left;"><a style="color: #ff6600" href="info?provinceCode=${item.goodsTypeId }">${item.goodsType}</a></span>
                        </div>
                    	</c:forEach>
                    </div>
                    <div class="fl down1" >
                    	<span class="down1_span">非智能设备</span>
                      	<c:forEach items="${goodstype2}" var="item">
                    	  <div class="down2">
                        	<span>${item.goodsType}</span>
                            <div class="down2_all">
                            <c:forEach items="${item.goodsTypes}" var="items">
                            <a href="info?provinceCode=${items.goodsTypeId }">${items.goodsType}</a>
                            </c:forEach>
                            </div>
                        </div>
                    	</c:forEach>
                    </div>
                    <div class="fl down1" >
                    	<span class="down1_span">专案定制服务</span>
                      <c:forEach items="${goodstype3}" var="item">
                    	  <div class="down2">
                        	<span><a style="color: #ff6600" href="info?provinceCode=${item.goodsTypeId }">${item.goodsType}</a></span>
                             
                        </div>
                    	</c:forEach>
                    </div>
                    <div class="fl down1" >
                    	<span class="down1_span">技术协作服务</span>
                       	<c:forEach items="${goodstype4}" var="item">
                    	  <div class="down2">
                        	<span ><a style="color: #ff6600" href="info?provinceCode=${item.goodsTypeId }">${item.goodsType}</a></span>
                            
                        </div>
                    	</c:forEach>
                    </div>
                </div>
                
            </li>
            <li><a <c:if test="${css==4}"> class="active"</c:if> href="lszq">老师专区</a></li>
            <li><a <c:if test="${css==5}"> class="active"</c:if> href="jxzq">经销专区</a></li>
            <li><a <c:if test="${css==6}"> class="active"</c:if> href="xsxy">心商学院</a></li>
            <li><a <c:if test="${css==7}"> class="active"</c:if> href="gywm">关于我们</a></li>
        </ul>
    </div>
</div>    
</div>

</body>
</html>