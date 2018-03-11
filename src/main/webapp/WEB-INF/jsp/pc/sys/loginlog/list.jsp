<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="loginlog/getlist">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="loginName" value="${param.loginName }"/>
	<input type="hidden" name="loginTime" value="${param.loginTime}" />
	<input type="hidden" name="endDate" value="${param.endDate}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="loginlog/getlist" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>登录名称：</label>
				<input type="text" name="loginName" value="${tloginlog.loginName }"/>
			</li>
			<li style="width: 400px;"> 
				<label>登陆时间：</label>
				<input id="loginTime"  type="text" name="loginTime" value="<fmt:formatDate value="${tloginlog.loginTime}" pattern="yyyy-MM-dd"/>" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
				~
				<input id="endDate"  type="text" name="endDate" value="<fmt:formatDate value="${tloginlog.endDate}" pattern="yyyy-MM-dd"/>" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'loginTime\')}'})" onclick="WdatePicker({minDate:'#F{$dp.$D(\'loginTime\')}'})"/>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
	</div>
	<div class="pageContent">
	<div class="panelBar">
		
	</div>
</div>

	<table class="table" width="100%" layoutH="133">
		<thead>
			<tr>
				<th width="40" align="center">序号</th>
				<th width="370" align="center">登录名称</th>
				<th width="370" align="center">登录IP</th>
				<th width="100" align="center">登录时间</th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach var="item" items="${pageList.result}" varStatus="row" >
	               <tr>
	                   <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
	                   <td class="center">${item.loginName}</td>
	                   <td>${item.logIp}</td>
	                   <td><ry:formatDate date="${item.loginTime}" ></ry:formatDate></td>
	               </tr>
              </c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

