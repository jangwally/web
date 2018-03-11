<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="admin/article/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />

	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="title" value="${param.title }"/>
	<input type="hidden" name="mediaType" value="1"/>
	<%--<input type="hidden" name="loginTime" value="${param.loginTime}" />--%>
	<%--<input type="hidden" name="endDate" value="${param.endDate}" />--%>
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="admin/article/list" method="post">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>文章标题：</label>
					<input type="text" name="title" value="${param.title}"/>
					<input type="hidden" name="mediaType" value="1"/>
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
		<ul class="toolBar">
			<li><a class="add" onclick="add('admin/article/add','添加信息',1100,800,'main_')"><span>添加</span></a></li>
		</ul>	</div>
</div>

<table class="table" width="100%" layoutH="132">
	<thead>
	<tr>
		<!--<th width="center" align="center"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
         -->
		<th width="center" align="center">序号</th>
		<th width="center" align="center">文章标题</th>
		<th width="center" align="center">操作</th></tr>
	</thead>
	<tbody>
	<c:forEach var="item" items="${pageList.result}" varStatus="row">
		<tr>
			<td>
				<input type="hidden" name="userApplicationId" value="${item.articleId}">
					${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
			<td>${item.title}</td>
			<td>
				<a onclick="add('admin/article/update?articleId=${item.articleId}','修改文章',900,600,'main_')" rel="users_saveedit" class="btn btn-success btn-sm">修改</a></td>
				<%--<ry:authorize ifAnyGranted="${authMap.SYSTEM_AUTH}">--%>
				<%--<td>--%>
				<%--<c:if test="${item.status==2}">--%>
				<%--<a title="修改提现信息" style="cursor: pointer;" onclick="add('userapplication/toEdit?userApplicationNum=${item.userApplicationNum}','修改提现信息',900,550,'main_')"--%>
				<%--rel="users_saveedit"><span style="color: blue;">提现处理</span> </a>--%>
				<%--</c:if>--%>
				<%--</td>--%>
				<%--</ry:authorize>--%>
		</tr>
	</c:forEach>
	</tbody>
</table>
<!-- 分页 -->
<%@include file="/WEB-INF/jsp/inc/page.jsp" %>



