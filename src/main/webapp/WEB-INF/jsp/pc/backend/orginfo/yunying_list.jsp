<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<form id="pagerForm" method="post" action="orginfo/list?orgType=${orgInfo.orgType}">
    <input type="hidden" name="pageNum" value="${pageList.pageNum }"/>
    <input type="hidden" name="numPerPage" value="${pageList.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
    <!-- 分页时 带模糊查询的值 -->
    <input type="hidden" name="orgName" value="${bean.orgName}"/>
    <input type="hidden" name="loginName" value="${bean.loginName}"/>

</form>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="orginfo/list?orgType=${orgInfo.orgType}" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>机构名称：</label>
                    <input type="text" name="orgName" value="${bean.orgName}">
                </li>
                <li>
                    <label>登录帐号：</label>
                    <input type="text" name="loginName" value="${bean.loginName}">
                </li>
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

                <li><a class="add"
                       onclick="add('orginfo/toAdd?orgType=${orgInfo.orgType}','添加',450,300,'main_')"><span>添加</span></a>
                </li>
        </ul>
    </div>
</div>
<table class="table" width="100%" layoutH="132">
    <thead>
    <tr>
        <th width="center" align="center">序号</th>
        <th width="center" align="center">机构名称</th>
        <th width="center" align="center">机构代码</th>
        <th width="center" align="center">登录帐号</th>
        <th width="center" align="center">自定义价格</th>
        <th width="center" align="center">自定义价格执行方式</th>
        <th width="center" align="center">创建人</th>
        <th width="center" align="center">创建时间</th>
        <th width="center" align="center">操作</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${pageList.result}" varStatus="row">
        <tr>
            <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
            <td>${item.orgName}</td>
            <td>${item.orgCode}</td>
            <td>${item.loginName}</td>
            <td>${item.stockRatio}</td>
            <td><ry:show parentCode="STOCK_RATIO_TYPE" itemCode="${item.stockRatioType}"/></td>
            <td>${item.createUserNum}</td>
            <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>

                <a class="btn btn-success btn-sm"
                   onclick="add('orginfo/toUpdateOrgName?orgInfoNum=${item.orgInfoNum}&orgType=${orgInfo.orgType}','修改用户信息',450,300,'main_')">修改名称</a>
                <a class="btn btn-success btn-sm" target="ajaxTodo"
                   href="orginfo/resetPwd?userNum=${item.userNum}&orgType=${orgInfo.orgType}">重置密码</a>
                <a class="btn btn-success btn-sm"
                   onclick="add('orginfo/toAddRole?loginName=${item.loginName}&orgType=${orgInfo.orgType}','配置权限',450,300,'main_')">配置权限</a>
                <%--<c:if test="${item.userStatus==1}">--%>
                    <%--<a href="javascript:void(0)" class="btn btn-success btn-sm"--%>
                       <%--onclick="makeTop(0,'${item.userNum}','user_status','user/updateQuery2','t_user','user_num')">禁用</a>--%>
                <%--</c:if>--%>
                <%--<c:if test="${item.userStatus==0}">--%>
                    <%--<a href="javascript:void(0)" class="btn btn-success btn-sm"--%>
                       <%--onclick="makeTop(1,'${item.userNum}','user_status','user/updateQuery2','t_user','user_num')">启用</a>--%>
                <%--</c:if>--%>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- 分页 -->
<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
