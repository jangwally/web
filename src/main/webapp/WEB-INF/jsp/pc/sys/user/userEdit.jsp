<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<ry:binding parentCode="USERSEX,ORG_TYPE" bingdingName="usersexs,orgTypeList"></ry:binding>
<div class="pageContent">
	<form method="post" action="admin/user/update?userType=${user.userType}&urlType=${param.invite}" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" enctype="multipart/form-data">
		<div class="pageFormContent nowrap" layoutH="57">
			<input type="hidden" name="urlType" value="${urlType}"/>
			<dl>
				<dt><span style="color: red;">*</span>姓名：</dt>
				<dd>
					<input type="text" id="loginName" name="nickName"  value="${user.nickName}" maxlength="20" size="30" class="mustFill" title="昵称名称" alt="请输入昵称名称"/>
				</dd>
			</dl>

			<dl>
				<dt>用户性别：</dt>
				<dd>
				    <c:forEach items="${usersexs}" var="item">
					   <input type="radio" value="${item.itemCode }" name="userSex" ${item.itemCode==user.userSex ? 'checked':(empty user.userSex ? 'checked':'') } >${item.itemName }
		            </c:forEach>
				</dd>
			</dl>
			<dl>
				<dt><span style="color: red;">*</span>电话号码：</dt>
				<dd>
					<input type="text" name="userPhone" value="${user.userPhone}" size="30" class="mustFill phone " readonly maxlength="11" title="电话号码" alt="请输入电话号码" />
				</dd>
			</dl>

			</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="formSubmit();">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="userId" value="${user.userId}"/>
	</form>
</div>
<script>

	function formSubmit(){

	    	if(check())
            $('#forms').submit();

	}
</script>