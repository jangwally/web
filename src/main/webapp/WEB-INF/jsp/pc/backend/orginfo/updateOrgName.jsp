<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
<div class="pageContent">
    <form method="post" action="orginfo/updateOrgName" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);" enctype="multipart/form-data">
        <div class="pageFormContent nowrap" layoutH="63">

            <p style="float: none;">
                <label>原机构名称：</label>
                <input  style="width: 180px;" class="required" type="text" size="30"  value="${bean.orgName}" readonly/>
            </p>

            <p style="float: none;">
                <label>新机构名称：</label>
                <input name="newOrgName" style="width: 180px;" class="required" type="text" size="30"  />
            </p>

            <!-- 隐藏值 -->
            <input type="hidden" value="${bean.orgInfoNum}" name="orgInfoNum"/>
            <input type="hidden" value="${orgType}" name="orgType"/>
        </div>

        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="todo()">保存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>
<script type="text/javascript">



    function todo() {

        $("#forms").submit();
    }

</script>