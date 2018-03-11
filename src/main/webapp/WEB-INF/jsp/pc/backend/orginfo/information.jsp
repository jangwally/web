<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
<form id="pagerForm" method="post" action="orginfo/toInformation?code=${bean.code}">
    <input type="hidden" name="pageNum" value="${pageList.pageNum }"/>
    <input type="hidden" name="numPerPage" value="${pageList.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
    <!-- 分页时 带模糊查询的值 -->
    <input type="hidden" name="orgName" value="${bean.orgName}"/>

</form>
<div class="pageHeader">
    <form onsubmit="return dwzSearch(this, 'dialog');" action="orginfo/toInformation?code=${bean.code}" method="post">
        <div class="searchBar">
            <ul class="searchContent">

                <li><label>机构名称：</label>
                    <input type="text" name="orgName" value="${bean.orgName}">
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
<table class="table" width="100%" layoutH="135">
    <thead>
    <tr>
        <th width="center" align="center">序号</th>
        <th width="center" align="center">机构名称</th>
        <th width="center" align="center">机构编号</th>

        <th width="center" align="center">手机号</th>

        <th width="center" align="center">创建时间</th>
    </tr>
    </thead>
    <tbody >
    <c:forEach var="item" items="${pageList.result}" varStatus="row">
        <tr>
            <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
            <td>${item.orgName}</td>
            <td>${item.code}</td>

            <td>${item.linkTel}</td>

            <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        </tr>
    </c:forEach>

    </tbody>
</table>
<!-- 分页 -->
<%@include file="/WEB-INF/jsp/inc/dialogPage.jsp"%>

<div class="pageContent">
    <div class="formBar">
        <ul>

            <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
        </ul>
    </div>
</div>
