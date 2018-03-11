<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
<style>
    .w-e-text p{
        width: 100%;
    }
</style>
<style>
    .black_overlay{
        display: none;
        position: absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 100%;
        background-color: black;
        z-index:1001;
        -moz-opacity: 0.8;
        opacity:.80;
        filter: alpha(opacity=80);
    }
    .white_content {
        display: none;
        position: absolute;
        top: 20%;
        left: 15%;
        width: 70%;
        height: 60%;
        border:0px solid;
        border-radius:5px;
        background-color: white;
        z-index:1002;
        overflow: hidden;
    }
    .white_content_small {
        display: none;
        position: absolute;
        top: 20%;
        left: 30%;
        width: 40%;
        height: 50%;
        /*border: 16px solid lightblue;*/
        background-color: white;
        z-index:1002;
        overflow: hidden;
    }

    .close-reveal-modal {
        font-size: 22px;
        line-height: .5;
        position: absolute;
        top: 8px;
        right: 11px;
        color: #aaa;
        text-shadow: 0 -1px 1px rbga(0,0,0,.6);
        font-weight: bold;
        cursor: pointer;
    }
</style>
<div class="pageContent">
    <form method="post" action="${url}" id="forms" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
        <div class="pageFormContent nowrap" layoutH="57">
            <div class="index1-div-div" id="add_photo">
                <div class="fl" style="width: 250px; height: 50px; margin-left: 140px; margin-top: 20px; ">
                    <span class="fl color-white" style="font-size: 16px; ">姓名：</span>
                    <div ><span style="font-size: 16px; ">${userInfoCheck.userName}</span></div>
                </div>
                <div class="fl" style="width: 250px; height: 50px; margin-left: 20px; margin-top: 20px;">
                    <span class="fl color-white" style="font-size: 16px; ">身份证号：</span>
                    <div><span style="font-size: 16px; ">${userInfoCheck.idNumber}</span></div>
                </div>
                <div class="fl" style="width:250px; height: 50px; margin-left: 140px;">
                    <span class="fl color-white" style="font-size: 16px; ">所属银行：</span>
                    <div><span style="font-size: 16px; ">${userInfoCheck.bankId}</span></div>
                </div>
                <div class="fl" style="width: 250px; height: 50px; margin-left: 20px; ">
                    <span class="fl color-white" style="font-size: 16px; ">银行卡号：</span>
                    <div><span style="font-size: 16px; ">${userInfoCheck.bankCardNumber}</span></div>
                </div>
                <div id="id_card_front" class="fl" style="width: 504px; height: 150px; margin-left: 140px">
                    <span class="fl color-white" style="font-size: 16px; ">身份证正面照片：</span>
                    <span class="fr">
                        <a onclick="url_img(this)" url = '${constants.QINIU_USER_IMGURL}${userInfoCheck.idCardFrontView}' target="_blank">
                            <%--<a onclick="ShowDiv('MyDiv','fade','${constants.QINIU_USER_IMGURL}${userInfoCheck.idCardFrontView}')">--%>
                                <img src="${constants.QINIU_USER_IMGURL}${userInfoCheck.idCardFrontView}" class="headeait" width="240px" height="140px">
                            <%--</a>--%>
                        </a>
                    </span>

                </div>
                <div id="id_card" class="fl" style="width: 504px; height: 150px; margin-left: 140px">
                    <span class="fl color-white" style="font-size: 16px; ">身份证反面照片：</span>
                    <span class="fr">
                        <a onclick="url_img(this)" url = '${constants.QINIU_USER_IMGURL}${userInfoCheck.idCardBackView}'   target="_blank">
                        <%--<a onclick="ShowDiv('MyDiv','fade','${constants.QINIU_USER_IMGURL}${userInfoCheck.idCardBackView}')">--%>
                            <img src="${constants.QINIU_USER_IMGURL}${userInfoCheck.idCardBackView}" class="headeait" width="240px" height="140px">
                        </a>
                    </span>
                </div>

                <div id="back_card" class="fl" style="width: 504px; height: 150px; margin-left: 140px">
                    <h4 class="fl color-white" style="font-size: 16px; ">银行卡照片：</h4>
                    <span class="fr">
                        <a  onclick="url_img(this)" url="${constants.QINIU_USER_IMGURL}${userInfoCheck.backCardPhoto}" target="_blank">
                        <%--<a onclick="ShowDiv('MyDiv','fade','${constants.QINIU_USER_IMGURL}${userInfoCheck.backCardPhoto}')">--%>
                            <img src="${constants.QINIU_USER_IMGURL}${userInfoCheck.backCardPhoto}" class="headeait" width="240px" height="140px">
                        </a>
                    </span>
                </div>
            </div>
            <!--弹出层时背景层DIV-->
            <div id="fade" class="black_overlay">
            </div>
            <div id="MyDiv" class="white_content">
                <div style="text-align: right; cursor: default; position: absolute; right: 0;">
                    <span class="close-reveal-modal"  onclick="CloseDiv('MyDiv','fade')">&#215;</span>
                </div>
                <img id="bigImg" src="" width="608px" height="314px">
            </div>
        </div>
        <!-- 隐藏的值 -->
        <input type="hidden" name="id" value="${userInfoCheck.id}"/>
        <input type="hidden" id="checkResult" name="checkResult" value="">
    </form>
</div>
<script>
    function url_img(obj) {
        var objs  = $(obj).attr('url');
        window.open('userInfoCheck/check/imgdetail?imgurl='+objs);
    }
    var isVal=true;
    function formSubmit(str){
        $("#checkResult").val(str);
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

<script type="text/javascript">
    //弹出隐藏层
    function ShowDiv(show_div,bg_div,url){
//        alert(url);
        document.getElementById(show_div).style.display='block';
        document.getElementById(bg_div).style.display='block' ;
//        document.getElementById(bigImg).src = url;
        $("#bigImg").attr("src",url);
        $("#check1").attr("disabled","disabled");
        $("#check2").attr("disabled","disabled");
        $("#check3").attr("disabled","disabled");
        var bgdiv = document.getElementById(bg_div);
        bgdiv.style.width = document.body.scrollWidth;
// bgdiv.style.height = $(document).height();
        $("#"+bg_div).height($(document).height());
    };
    //关闭弹出层
    function CloseDiv(show_div,bg_div)
    {
        document.getElementById(show_div).style.display='none';
        document.getElementById(bg_div).style.display='none';
        $("#check1").removeAttr("disabled");
        $("#check2").removeAttr("disabled");
        $("#check3").removeAttr("disabled");
    };
</script>

