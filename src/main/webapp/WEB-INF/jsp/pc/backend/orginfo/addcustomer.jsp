<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
<script>
    $(function(){
        $("#linkTel").blur(function(){

            if (!$("#linkTel").val().match(/^^1[34578]\d{9}$/)) {
                alert("手机格式不正确");
            }


        });
    })


</script>
<div class="pageContent">
    <form method="post" action="orginfo/addCustomer" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);" enctype="multipart/form-data">
        <div class="pageFormContent nowrap" layoutH="63">

           <p style="float: none;">
                <label>姓名：</label>
                <input name="nickName" style="width: 180px;" class="required" type="text" size="30" />
            </p>
            <p style="float: none;">
                <label>手机号：</label>
                <input name="linkTel" style="width: 180px;" class="required" type="text" size="30" id="linkTel"/>
            </p>
            <p style="float: none;">
                <label>登录密码：</label>
                <input name="loginPass" style="width: 180px;" class="required" type="text" size="30" id="loginPass"/>
            </p>

            <%-- <p style="float: none;">
                 <input name="orderby" style="width: 180px;" class="required number" type="hidden" size="30" value="${not empty bean.orderby?bean.orderby : num+1 }" />
             </p>--%>
            <!-- 隐藏值 -->
            <input type="hidden" value="${orgInfoNum }" name="orgInfoNum"/>
        </div>

        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>

    </form>
</div>