<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ry" uri="http://www.ruanyun.com/taglib/ry" %>
<!DOCTYPE html>
<html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath %>"/>
<head>
    <link rel="shortcut icon" href="all_references/<ry:showWeb name="PROJECT_PATH"></ry:showWeb>/img/favicon.png" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><ry:showWeb name='MANAGER_PROJECT_NAME'></ry:showWeb></title>
    <script src="dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
    <link href="dwz/themes/css/login.css" rel="stylesheet" type="text/css">

</head>

<body onload="init();" onkeydown="submitForm();" style="overflow: hidden">
<div class="auto" onload="init();" onkeydown="submitForm();">
    <form method="post" action="login?type=1" id="form1">
        <div class="left fl">
            <div class="logo"></div>
        </div>
        <div class="right fr">
            <h1 class="title"><ry:showWeb name='PROJECT_NAME'></ry:showWeb>管理平台</h1>
            <div class="fl content" style="width: 253px;">
                <div>
                    <p style="height:30px; line-height:30px;" class="fl">用户名：</p>
                    <input type="text" class="box fl" name="loginName" id="loginName" value="admin" style="width: 179px;">
                    <div class="clearfix"></div>
                </div>
                <div>
                    <p style="height:30px; line-height:30px;" class="fl">密&nbsp;&nbsp;码：</p>
                    <input type="password" class="box fl" name="password" id="loginPass" value="123456" style="width: 179px;">
                </div>
                <div>
                    <p style="height:30px; line-height:30px;" class="fl">验证码：</p>
                    <input type="text" class="box1 fl" id="checkCode" name="checkCode"  value="##">
                    <div class="yzm fl">
                        <img id="check_code" border=0 alt="看不清，请点击刷新！" src="img/image.jsp"
                                             style="cursor: hand;"
                                             onclick="this.src='img/image.jsp?'+new Date().getTime()" ;/>
                    </div>
                </div>
            </div>
            <div class="fl" style="width: 100px;">
                <button class="btn" name="LoginButton" onclick="submitcheck();" id="LoginButton">登录</button>
                <div class="ui-form-explain" id="error"
                     style="color: red; margin-top: 72px;height: 0px;margin-left: -200px"></div>
            </div>
        </div>
        <div class="clearfix"></div>
        <%--<p class="bq">Copyright © 2016 | 建议使用google浏览器 | 技术支持：安徽软云信息科技有限公司</p>--%>
    </form>
</div>
<script>
    function submitForm(event) {
        var event = window.event ? window.event : event;
        if (event.keyCode == 13) {
            loginsumbit();
        }
    }
    $(function () {
        $(".box").mouseover(function () {
            $(this).css("border-color", "#5ac5f8")
        })
        $(".box").mouseout(function () {
            $(this).css("border-color", "#dedede")
        })
    })

    function submitForm(event) {
        var event = window.event ? window.event : event;
        if (event.keyCode == 13) {
            loginsumbit();
        }
    }
    //登录错误信息提示
    function submitcheck() {
        if ($('#loginName').val().length > 0 && $('#loginPass').val().length > 0) {
            $("#form1").submit();
        } else {
            $("#error").text("用户名或者密码不能为空!!!");
        }
    }
    $(function () {
        if (${not empty loginResult}) {

            if (${loginResult==-2}) {
                $("#error").text("密码错误！登录失败!");
                $("#loginPass").focus();
                return false;
            }
            if (${loginResult==-1}) {
                $("#error").text("用户名错误！登录失败!");
                $("#loginName").focus();
                return false;
            }
            if (${loginResult==-5}) {
                $("#error").text("店铺用户待审核！登录失败!");
                $("#loginName").focus();
                return false;
            }
            if (${loginResult==-4}) {
                $("#error").text("用户名或密码错误!");
                $("#checkCode").focus();
                return false;
            }
            if (${loginResult==-6}) {
                $("#error").text("没有后台登录权限!");
                $("#checkCode").focus();
                return false;
            }
        }
    });
    //记住登录信息

    function loginsumbit() {
        if (document.getElementById("AutoLogin").checked) {
            SetCookie("loginName", $('#loginName').val());
            SetCookie("loginPass", $('#loginPass').val());
        }
        form1.submit();
    }

    function init() {
        //$('#loginName').val(getCookie("loginName") || "");
        //$('#loginPass').val(getCookie("loginPass") || "");
    }

    function SetCookie(name, value) {
        var Days = 30; //此 cookie 将被保存 30 天
        var exp = new Date();    //new Date("December 31, 9998");
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }

    function getCookie(name) {
        var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
        if (arr != null) return unescape(arr[2]);
        return null;
    }

    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }

    //监听输入框
    $(function () {
        if (${not empty loginResult}) {
            if (${loginResult==-3}) {
                $("#error").text("验证码错误或已失效！登录失败!");
                $("#checkCode").focus();
                return false;
            }
            if (${loginResult==-5}) {
                $("#error").text("店铺用户待审核！登录失败!");
                $("#loginName").focus();
                return false;
            }
            if (${loginResult==-2}) {
                $("#error").text("密码错误！登录失败!");
                $("#loginPass").focus();
                return false;
            }
            if (${loginResult==-1}) {
                $("#error").text("用户名错误！登录失败!");
                $("#loginName").focus();
                return false;
            }
        }
    });
</script>

</body>
</html>