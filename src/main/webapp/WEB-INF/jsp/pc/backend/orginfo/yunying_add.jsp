<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<div class="pageContent">
    <form method="post" action="orginfo/add" id="forms" class="pageForm required-validate"
          onsubmit="return iframeCallback(this,dialogAjaxDone);" enctype="multipart/form-data">
        <div class="pageFormContent nowrap" layoutH="63">

            <p style="float: none;">
                <label>机构名称：</label>
                <input name="orgName" style="width: 180px;" class="required" type="text" size="30"/>
            </p>
            <p style="float: none;">
                <label>登录帐号：</label>
                <input name="loginName" style="width: 180px;" class="required mustFill" type="text" size="20"
                       id="loginName"/>
            </p>
            <p style="float: none;">
                <label>登录密码：</label>
                <input name="loginPass" style="width: 180px;" class="required mustFill" id="loginPass" type="text" size="30" />
            </p>
            <p style="float: none;">
                <label>自定义价格：</label>
                <input name="stockRatio" style="width: 180px;" class="required mustFill"  id="stockRatio" type="text" size="30" value="0.0" />
            </p>
            <p style="float: none;">
                <label>自定义价格执行方式：</label>
                <select name="stockRatioType">
                    <c:forEach items="${stockRatioType}" var="item">
                        <option value="${item.itemCode}">${item.itemName}</option>
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
                            <button type="button" onclick="todo(this)">保存</button>
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

    function todo(obj) {

        $("#forms").submit();

    }

</script>