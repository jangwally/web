<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_ACCOUNT_ORDER_STATUS" bingdingName="orderStatus"></ry:binding>
<ry:binding type="3"></ry:binding>
<form id="pagerForm" method="post" action="userInfoCheck/list">
    <input type="hidden" name="pageNum" value="${pageList.pageNum }"/>
    <input type="hidden" name="numPerPage" value="${pageList.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>

    <!-- 分页时 带模糊查询的值 -->
    <input type="hidden" name="loginName" value="${param.loginName}"/>
    <input type="hidden" name="userName" value="${param.userName}"/>
    <input type="hidden" name="belongValue" value="${param.belongValue}"/>
    <input type="hidden" name="belongName" value="${belongName}"/>
    <%--<input type="hidden" name="startTime" value='<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>' />--%>
    <%--<input type="hidden" name="endDate" value='<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>' />--%>

</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="userInfoCheck/list" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li style="width: 250px"><label>上级归属：</label>
                    <input name="belongName" id="belongName" type="text" readonly value="${belongName}"
                           style="width:120px;"/>
                    <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                    <input type="hidden" name="belongValue" id="belongValue" value="${param.belongValue}"/>
                </li>
                <li style="width: 250px">
                    <label>登录账号：${params.userType}</label>
                    <input style="width: 150px" type="text" name="loginName" value="${user.loginName }"/>
                </li>
                <li style="width: 200px">
                    <label>姓名：</label>
                    <input style="width: 100px" type="text" name="userName" value="${param.userName}">
                </li>

            </ul>
            <ul class="searchContent">
                <li style="width: 250px">
                    <label>审核状态：</label>
                    <select id="checkResult" name="checkResult" style="width: 125px">
                        <option value="">--选择审核状态--</option>
                        <option value="0">待审核</option>
                        <option value="1">审核通过</option>
                        <option value="2">审核未通过</option>
                    </select>
                </li>

                <li style="width: 280px;"><label>银行卡号：</label>
                    <input type="number" style="width: 150px" name="bankCardNumber" value="${param.bankCardNumber}">
                </li>
                <script>
                    $("#checkResult").val(${param.checkResult});
                </script>
            </ul>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <%--<li><a class="add" onclick="add('admin/user/add','添加信息',900,500,'main_')"><span>添加</span></a></li>--%>

            <!--<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="user/batchDelete" class="delete"><span>批量删除</span></a></li>
--><!--			<li class="line">line</li>-->
            <!--			<li><a class="icon" href="user/exportExcel" target="dwzExport" targetType="navTab" title="您确定导出这些记录吗?"><span>导出EXCEL</span></a></li>-->
        </ul>
    </div>
    <table class="table" width="110%" layoutH="166">
        <thead>
        <tr>
            <!--<th width="center" align="center"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
             -->
            <th width="center" align="center">序号</th>
            <th width="center" align="center">所属上级机构</th>
            <th width="center" align="center">姓名</th>
            <th width="center" align="center">登录账号</th>
            <th width="center" align="center">身份证号码</th>
            <th width="center" align="center">开户行</th>
            <th width="center" align="center">银行卡号</th>
            <th width="center" align="center">提交时间</th>
            <th width="center" align="center">银行卡审核状态</th>
            <th width="center" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${pageList.result}" varStatus="row">
            <tr>
                <td>
                    <input type="hidden" name="id" value="${item.id}">
                        ${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
                <td>${item.orgName}</td>
                <td>${item.userName}</td>
                <td>
                    <c:if test="${systemUser.userType==1}">${item.loginName}</c:if>
                    <c:if test="${systemUser.userType!=1}">*******${fn:substring(item.loginName, fn:length(item.loginName) - 4, fn:length(item.loginName))}</c:if>
                  <%--  <ry:authorize ifAllGranted="A_1592">${item.loginName} </ry:authorize>
                    <ry:authorize
                            ifNotGranted="A_1592">*******${fn:substring(item.loginName, fn:length(item.bankCardNumber) - 4, fn:length(item.bankCardNumber))}</ry:authorize>--%>
                 </td>
                <td>
                    <c:if test="${systemUser.userType==1}">${item.idNumber}</c:if>
                    <c:if test="${systemUser.userType!=1}">*******${fn:substring(item.idNumber, fn:length(item.idNumber) - 4, fn:length(item.idNumber))}</c:if>
                <%--    <ry:authorize ifAllGranted="A_1592">${item.idNumber} </ry:authorize>
                    <ry:authorize
                            ifNotGranted="A_1592"> ${fn:substring(item.idNumber, 0, 4)}**************</ry:authorize>--%>
                </td>
                    <%--<td>--%>
                    <%--<img src="${constants.QINIU_USER_IMGURL}${item.idCardFrontView}" class="headeait" width="20px" height="10px">--%>
                    <%--</td>--%>
                    <%--<td>${item.userName}</td>--%>
                <td>${item.bankId}</td>
                <td>
                    <c:if test="${systemUser.userType==1}">${item.bankCardNumber}</c:if>
                    <c:if test="${systemUser.userType!=1}">*******${fn:substring(item.bankCardNumber, fn:length(item.bankCardNumber) - 4, fn:length(item.bankCardNumber))}</c:if>
                 <%--   <ry:authorize ifAllGranted="A_1592">${item.bankCardNumber}</ry:authorize>
                    <ry:authorize ifNotGranted="A_1592">
                        ****&nbsp;****&nbsp;****&nbsp;${fn:substring(item.bankCardNumber, fn:length(item.bankCardNumber) - 4, fn:length(item.bankCardNumber))}
                    </ry:authorize>--%>
                </td>
                <td><ry:formatDate date="${item.createTime}" toFmt="MM-dd HH:mm"/></td>
                <td>
                    <c:choose>
                        <c:when test="${item.checkResult == 0}">未审核</c:when>
                        <c:when test="${item.checkResult == 1}">审核通过</c:when>
                        <c:otherwise>审核不通过</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a onclick="add('userInfoCheck/check/detail?id=${item.id}','详情',900,600,'main_')" rel=""
                       class="btn btn-success btn-sm">详情</a>
                    <c:choose>
                    <c:when test="${item.checkResult == 0}">
                    <a onclick="add('userInfoCheck/check?id=${item.id}','详情',900,600,'main_')" rel="users_saveedit"
                       class="btn btn-success btn-sm">审核</a></td>
                </c:when>
                </c:choose>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- 分页 -->
    <%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>



