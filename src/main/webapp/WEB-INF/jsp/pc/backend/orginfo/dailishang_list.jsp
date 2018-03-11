<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<form id="pagerForm" method="post" action="orginfo/list?orgType=${bean.orgType}">
    <input type="hidden" name="pageNum" value="${pageList.pageNum }"/>
    <input type="hidden" name="numPerPage" value="${pageList.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
    <!-- 分页时 带模糊查询的值 -->
    <input type="hidden" name="orgName" value="${bean.orgName}"/>
    <input type="hidden" name="loginName" value="${bean.loginName}"/>

</form>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="orginfo/list?orgType=${bean.orgType}" method="post">
        <div class="searchBar">
            <ul class="searchContent">

                <li style="width: 260px"><label>所属上级机构：</label>
                    <input name="parentOrgName" id="parentOrgName" type="text" readonly value="${parentOrgName}"
                           style="width:120px;"/>
                    <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                    <input type="hidden" name="parentOrgValue" id="parentOrgValue" value="${parentOrgValue}"/>
                </li>

                <li>
                    <label>机构名称：</label>
                    <input type="text" name="orgName" value="${bean.orgName}">
                </li>
                <li>
                    <label>登录账号：</label>
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
                       onclick="add('orginfo/toAdd?orgInfoNum=${bean.orgInfoNum}&orgType=3','添加信息',450,300,'main_')"><span>添加代理</span></a>
                </li>
        </ul>
    </div>
</div>
<table class="table" width="150%" layoutH="132">
    <thead>
    <tr>
        <th width="center" align="center">序号</th>
        <th width="center" align="center">所属上级机构</th>
        <th width="center" align="center">机构名称</th>
        <th width="center" align="center">机构代码</th>
        <th width="center" align="center">登录帐号</th>
        <th width="center" align="center">自定义价格</th>
        <th width="center" align="center">自定义价格执行方式</th>
        <th width="center" align="center">邀请码</th>
        <th width="center" align="center">推广地址</th>
        <th width="center" align="center">二维码</th>
        <th width="center" align="center">创建人</th>
        <th width="center" align="center">创建时间</th>
        <th width="center" align="center">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${pageList.result}" varStatus="row">
        <tr>
            <input type="hidden" name="orgInfoId" value="${item.orgInfoId }"/>
            <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
            <td>${item.parentName}</td>
            <td>${item.orgName}</td>
            <td>${item.orgCode}</td>
            <td>${item.loginName}</td>
            <td>${item.stockRatio}</td>
            <td><ry:show parentCode="STOCK_RATIO_TYPE" itemCode="${item.stockRatioType}"/></td>
            <td>${item.code}</td>
            <td>暂无</td>
            <td>暂无</td>
            <td>${item.createUserNum}</td>
            <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <a class="btn btn-success btn-sm"
                   onclick="add('orginfo/toUpdateOrgName?orgInfoNum=${item.orgInfoNum}&orgType=${item.orgType}','修改名称',450,300,'main_')">修改名称</a>

                <a class="btn btn-success btn-sm" target="ajaxTodo"
                   href="orginfo/resetPwd?userNum=${item.userNum}&orgType=${item.orgType}">重置密码</a>

                <a class="btn btn-success btn-sm"
                   onclick="add('orginfo/toAddRole?loginName=${item.loginName}&orgType=${item.orgType}','配置权限',450,300,'main_')">配置权限</a>

                <%--<c:if test="${item.userStatus==1}">
                    <a href="javascript:void(0)" class="btn btn-success btn-sm"
                       onclick="makeTop(0,'${item.userNum}','user_status','user/updateQuery2','t_user','user_num')">禁用</a>
                </c:if>
                <c:if test="${item.userStatus==0}">
                    <a href="javascript:void(0)" class="btn btn-success btn-sm"
                       onclick="makeTop(1,'${item.userNum}','user_status','user/updateQuery2','t_user','user_num')">启用</a>
                </c:if>--%>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- 分页 -->
<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
<!--查找上级归属-->
<div id="menuContent" class="menuContent aaa"
     style="height:200px;overflow:auto;display:none; position: absolute;left: 73px!important;    top: 36px!important;">
    <div style="background: #ffffff;" class="accordionContent" id="left"></div>
</div>
<script>

    jQuery(function ($) {
        var data = ${orgInfoList};
        console.log(data);
        var pId = '${systemUser.orgCode}';
        var systemUserType = '${systemUser.userType}';
        pId = '0'
        var item1 =
            {
                "id": '${systemUser.orgCode}',
                "pId": '0',
                "name": "全部"
            }
        data.push(item1);
        $("#left").html(getTree(data, pId, true).replaceAll("<ul></ul>", ""));
    });


</script>

