<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USERSEX,ORG_TYPE" bingdingName="usersexs,orgTypeList"></ry:binding>
<ry:binding type="3"></ry:binding>
<form id="pagerForm" method="post" action="user/users?userType=${tuser.userType}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="loginName" value="${param.loginName}"/>
	<input type="hidden" name="nickName" value="${param.nickName}"/>
	<input type="hidden" name="userSex" value="${param.userSex}"/>
	<input type="hidden" name="createTime" value="${param.createTime}" />
	<input type="hidden" name="endDate" value="${param.endDate}" />
	
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="user/users?userType=${tuser.userType}" method="post">
	<div class="searchBar">
		
		<ul class="searchContent">
			<li>
					<label>登录名称：</label>
					<input type="text" name="loginName" value="${tuser.loginName }" />
			</li>
			<li>
					<label>用户昵称：</label>
					<input type="text" name="nickName" value="${tuser.nickName }"/></td>
			</li>
				<li>
					<label>性别：</label>
					<select name="userSex" >
						<option value="">---请选择性别---</option>
						<c:forEach items="${usersexs}" var="item">
						   <option value="${item.itemCode }" ${item.itemCode==tuser.userSex ? 'selected':'' } >${item.itemName }</option>
		                </c:forEach>
				    </select>
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
			<li><a class="add" onclick="add('user/toUserEdit?userType=${tuser.userType}','添加信息',900,500,'main_')"><span>添加</span></a></li>
				
			<!--<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="user/batchDelete" class="delete"><span>批量删除</span></a></li>
--><!--			<li class="line">line</li>-->
<!--			<li><a class="icon" href="user/exportExcel" target="dwzExport" targetType="navTab" title="您确定导出这些记录吗?"><span>导出EXCEL</span></a></li>-->
		</ul>
	</div>
	<table class="table" width="100%" layoutH="132">
		<thead>
			<tr>
				
				<th width="40"  align="center">序号</th>
				<th width="180" align="center">登录名称</th>
				<th width="180" align="center">用户昵称</th>

				<th width="50" align="center">性别</th>
				<th width="60" align="center" >电话号码</th>
			
				<th width="100" align="center">创建时间</th>
				<th width="70" align="center">状态</th>
				<th width="80" >操作</th>
			</tr>
		</thead>
		<tbody>
		    <c:forEach var="item" items="${pageList.result}" varStatus="row">
			<tr target="sid_user" rel="1">
				
				<td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
				<td>${item.loginName}</td>
				<td>${item.nickName}</td>


				
				<td><ry:show parentCode="USERSEX" itemCode="${item.userSex}"></ry:show></td>
				<td>${item.userPhone}</td>
				
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
				<td>
				<ry:authorize ifAllGranted="${authMap.SYSATEM_AUTH}">
					<c:if test="${item.userStatus==1}">
					<a href="javascript:void(0)" onclick="makeTop(2,'${item.userNum}','user_status','user/updateQuery','t_user','user_num')"><span style="color: green">启用</span></a>
					</c:if>
					<c:if test="${item.userStatus==2}">
					<a href="javascript:void(0)" onclick="makeTop(1,'${item.userNum}','user_status','user/updateQuery','t_user','user_num')"><span style="color: red">禁用</span></a>
					</c:if>
					</ry:authorize>
					<ry:authorize ifAllGranted="${authMap.SYSTEM_AUTH}">
					${item.userStatus ==1? '启用' : '禁用'}
					</ry:authorize>
				</td>
				<td>
					<a title="您确定重置用户 “${item.loginName}”的密码吗？" target="ajaxTodo" href="user/resetPassword?userId=${item.userId }"  class="btnView">重置密码</a>
					<!--<a title="修改用户" target="navTab" href="user/toUserEdit?userId=${item.userId }&userType=${item.userType}" rel="users_saveedit" class="btnEdit">编辑</a>
					
					--><a class="btnEdit" onclick="add('user/toUserEdit?userId=${item.userId }&userType=${item.userType}','修改用户信息',950,500,'main_')"><span>修改用户</span></a>	
					
					<a title="您确定删除用户 ”${item.loginName}“ 吗？" target="ajaxTodo" href="user/del?userId=${item.userId}&loginName=${item.loginName}&userType=${item.userType}&userStatus=${item.userStatus}" class="btnDel">删除</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
