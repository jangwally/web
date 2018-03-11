<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
<form id="pagerForm" method="post" action="dictionary/toList?parentCode=${bean.parentCode }">
</form>
<div class="pageHeader">
<form onsubmit="return divSearch(this, 'jbsxBox');" action="dictionary/toList?parentCode=${bean.parentCode }" method="post">
<div class="searchBar" style="display: none;">
<ul class="searchContent">
    

</ul>

<div class="subBar" >
<ul>
    <li>
	<button class="btn btn-default" TYPE="submit" style="top: 40px;position: relative;">查询</button>
	</li>
</ul>
</div>
</div>
</form>
</div>
<div class="pageContent" >
<div class="panelBar" >
<ul class="toolBar">
	<c:if test="${bean.isRead==3}">
	<li><a class="add" onclick="add('dictionary/toEdit?parentCode=${bean.parentCode}&parentName=${bean.parentName}&isRead=${bean.isRead }','添加“${bean.parentName }”',550,300,'main_01')"><span>添加</span></a></li>
	</c:if>
</ul>

</div>
<table class="table" width="100%" layoutH="132">
	<thead>
		<tr>
		 	
			<th width="center" align="center">序号</th>
			<th width="center" align="center">父类型编码</th>
			<th width="center" align="center">父类型名称</th>
			<th width="center" align="center">编码</th>
			<th width="center" align="center">名称</th>
			<th width="center" align="center">排序</th>
			<th width="center" align="center">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${dictionaryList }" varStatus="row">
			<tr >
				
				<td>${row.count}</td>
				<td>${item.parentCode}</td>
				<td>${item.parentName }</td>
				<td>${item.itemCode }</td>
				<td>${item.itemName }</td>
				<td>${item.orderby }</td>
				<c:if test="${bean.isRead==3 || bean.isRead==2}">
				<td>
					<c:if test="${bean.isRead==3 || bean.isRead==2}">
						<a title="修改“${item.parentName }”"   onclick="add('dictionary/toEdit?id=${item.id}','修改“${item.parentName }”',550,300,'main_01')" class="btn btn-success btn-sm" >修改</a>
					</c:if>
					<c:if test="${bean.isRead==3}">
					<a title="您确定删除“${item.parentName }”吗？" target="ajaxTodo" href="dictionary/del?id=${item.id}" class="btn btn-danger btn-sm" >删除</a>
					</c:if>
				</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
