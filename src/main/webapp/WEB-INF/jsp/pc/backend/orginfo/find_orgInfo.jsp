<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding type="3"></ry:binding>

<form id="pagerForm" method="post" action="orginfo/findOrgBack">
    <input type="hidden" name="pageNum" value="${pageList.pageNum }"/>
    <input type="hidden" name="numPerPage" value="${pageList.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>

    <!-- 分页时 带模糊查询的值 -->
    <input type="hidden" name="orgName" value="${bean.orgName}">
    <input type="hidden" name="linkTel" value="${bean.loginName}">

</form>
<div class="pageHeader">
    <form  onsubmit="return dwzSearch(this, 'dialog');" targetType="dialog" action="orginfo/findOrgBack" method="post">
        <div class="searchBar">
            <input type="hidden" name="orgType" value="${orgType}">
            <ul class="searchContent">
                <li>
                    <label>机构名称：</label>
                    <input type="text" name="orgName" value="${bean.orgName}">
                </li>
                <li>
                    <label>登陆账号：</label>
                    <input type="text" name="loginName" value="${bean.loginName}">
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
<table class="table" width="100%" layoutH="105">
    <thead>
    <tr>
        <th width="center" align="center">序号</th>
        <th width="center" align="center">机构名称</th>
        <th width="center" align="center">机构编号</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${pageList.result}" varStatus="row">
        <tr  onclick="javascript:$.bringBack({parentCode:'${item.orgCode}',
                orgName:'${item.orgName}'})" style="cursor:pointer;">
            <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
            <td>${item.orgName}</td>
            <td>${item.orgCode}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- 分页 -->
<%--
<div class="panelBar">
    <div class="pages" style="width: 270px;">
		<span>
			<c:if test="${pageList.totalCount!=0 }">
                当前第${pageList.pageNum}页,共 ${pageList.totalCount }条数据,共 ${pageList.totalPage}页
            </c:if>
		</span>
    </div>
    <div class="pagination"  targetType="navTab" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage }" pageNumShown="10" currentPage="${pageList.pageNum}" rel="jbsxBox"></div>
</div>
--%>
<!-- 分页 -->
<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
