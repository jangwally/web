<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
<form id="pagerForm" method="post" action="stockinfo/list">
    <input type="hidden" name="pageNum" value="${pageList.pageNum }" />
    <input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
    <input type="hidden" name="orderField" value="${param.orderField}" />
    <!-- 分页时 带模糊查询的值 -->
    <input type="hidden" name="name" value="${bean.name}" />
    <input type="hidden" name="code" value="${bean.code}" />
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="stockinfo/list" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>股票编号：</label>
                    <input type="text" name="code" value="${bean.code}">
                </li>
                <li>
                    <label>股票名称：</label>
                    <input type="text" name="name" value="${bean.name}">
                </li>
            </ul>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div>
                    </li>

                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <a href="javascript:;" onclick="$('#stockInfoFile').click()">导入</a>
                            </div>

                        </div>
                    </li>
                </ul>


            </div>
        </div>
    </form>
</div>

<table class="table" width="100%" layoutH="103">
    <thead>
    <tr>
        <th width="center" align="center">序号</th>
        <th width="center" align="center">股票编号</th>
        <th width="center" align="center">股票名称</th>
        <th width="center" align="center">拼音简称</th>
        <th width="center" align="center">拼音全称</th>
        <th width="center" align="center">股票类型</th>
        <th width="center" align="center">状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${pageList.result}" varStatus="row">
        <tr>
            <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
            <td>${item.code}</td>
            <td>${item.name}</td>
            <td>${item.simpleSpell}</td>
            <td>${item.allSpell}</td>
            <td><ry:show parentCode="CODE_TYPE" itemCode="${item.codeType}"/></td>
            <td><ry:show parentCode="STOCK_IFNO_STATUS" itemCode="${item.status}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!--导入表单-->

<form id="fileForm" action="stockinfo/importStockInfo" method="post" enctype="multipart/form-data" target="_blank">
    <input type="file" name="stockInfoFile" id="stockInfoFile" style="display: none;"
           onchange="$('#fileForm').submit()">
</form>



<!-- 分页 -->
<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
