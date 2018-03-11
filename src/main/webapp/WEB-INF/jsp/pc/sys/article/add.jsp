<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
<style>
	.w-e-text p{
		width: 100%;
	}
</style>
<div class="pageContent">
	<form method="post" action="${url}" id="forms" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)"  enctype="multipart/form-data">
		<div class="pageFormContent nowrap" layoutH="57">
			<dl>
				<dt>文章标题：</dt>
				<dd>
					<input title="文章标题" type="text" id="loginName" name="title"  value="${article.title}" maxlength="40" size="40" class="mustFill" title="文章标题" alt="请输入文章标题" />
				</dd>
			</dl>
			<dl>
				<dt>文章类型：</dt>
				<dd>
					<select id="articleType" name="articleType">
						<option value="1">行业资讯</option>
						<option value="2">实时解盘</option>
					</select>
				</dd>
			</dl>
			<input type="hidden" name="mediaType"  value="1"/>
			<dl style="width: 750px;height: 150px;" >
				<dt>文章摘要：</dt>
				<dd>
					<textarea id="description" title="文章摘要" class="mustFill" style="width: 700px;height: 100px;resize: none;" name="description">${article.description}</textarea>
					<p>还可以输入<span id="text-count" style="color: red">300</span>字/共可输入300字</p>
				</dd>
			</dl>
			<dl style="width: 750px;height: 520px;" >
				<dt>文章正文：</dt>
				<dd><div id="myeditor"  style="width:600px;height: 400px;" title="文章正文"></div>
				</dd>
			</dl>
			<div style="display: none">
				<textarea id="content" class="mustFill" title="文章正文"  name="content">${article.content}</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="formSubmit();">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="articleId" value="${article.articleId}"/>
	</form>
</div>
<script type="text/javascript">
    /*字数限制*/
    $("#description").on("input propertychange", function() {
        var $this = $(this),
            _val = $this.val(),
            count = "";
        if (_val.length > 300) {
            $this.val(_val.substring(0, 300));
        }
        count = 300 - $this.val().length;
        $("#text-count").text(count);
    });
</script>
<script>
    var isVal=true;
    function formSubmit(){
        if(isVal==false){
            $('#fali_show').show();
            return false;
        }else{
            if(check())
                $('#forms').submit();
        }

    }

    function validateCallback(form, callback, confirmMsg) {
        var $form = $(form);
        if (!$form.valid()) {
            return false;
        }

        var _submitFn = function(){
            $.ajax({
                type: form.method || 'POST',
                url:$form.attr("action"),
                data:$form.serializeArray(),
                dataType:"json",
                cache: false,
                success: callback || DWZ.ajaxDone,
                error: DWZ.ajaxError
            });
        }

        if (confirmMsg) {
            alertMsg.confirm(confirmMsg, {okCall: _submitFn});
        } else {
            _submitFn();
        }

        return false;
    }
</script>

<script>
    var ue=UE.getEditor("myeditor",{
        autoHeightEnabled: false});
    ue.addListener("blur",function(){
        var html=UE.getEditor('myeditor').getContent();
        $("#content").val(html);
    });
    ue.addListener("ready", function () {
        var Basecontent = $("#content").val();
        ue.setContent(Basecontent,false);
    });
    $("#articleType").val(${article.articleType});
</script>
