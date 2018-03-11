<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<div class="pageContent">
    <form method="post" action="orginfo/addRole" id="forms" class="pageForm required-validate"
          onsubmit="return iframeCallback(this,dialogAjaxDone);" enctype="multipart/form-data">
        <div class="pageFormContent nowrap" layoutH="63">
            <p style="float: none;">
                <label>登陆账号：</label>
                <input name="loginName" style="width: 180px;" class="required mustFill" type="text" size="20"  value="${loginName}" readonly/>
            </p>

            <p style="float: none;">
                <label>权限列表：</label>
                <select name="roleId">
                    <option value="">请选择权限</option>
                    <c:forEach items="${roleType}" var="item">
                        <option value="${item.roleId}">${item.roleName}</option>
                    </c:forEach>
                </select>
            </p>

            <!-- 隐藏值 -->
            <input type="hidden" name="orgType" value="${orgType}">

        </div>

        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="button" onclick="todo()">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script type="text/javascript">

    function todo() {
        $("#forms").submit();
    }

</script>