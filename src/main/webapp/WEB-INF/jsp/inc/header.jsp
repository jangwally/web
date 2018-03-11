<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
.nav label{
position:relative;
color:#fff
}
</style>
<div id="header"  >
	<div class="headerNav" style="z-index:-1;" >
		<a style="padding-top: 3" href="javascript:void(0);"><img style="height: 36px;margin: 23px" src="all_references/<ry:showWeb name='PROJECT_PATH'></ry:showWeb>/img/system/logo1.png"></a>
		<ul class="nav" style="top:10px;font-size:12px;" >
			
			<!-- <li><a href="user/editPersion?userId=${systemUser.userId }" target="navTab" rel="persion_edit"><label>个人信息</label></a></li> -->
			<li><a href="user/editpass?userId=${systemUser.userId }" target="dialog" mask="true"><label>密码修改</label></a></li>
			<li><label><a href="loginout">退出</a></label></li>
		</ul>
		<ul class="nav" style="top:40px;font-size:12px;" >
			<li style="color:#fff;">欢迎您！&nbsp;&nbsp;&nbsp;&nbsp;${systemUser.nickName} 
			</li>
		</ul>
	</div>
</div>
	<script type="text/javascript">
	function loginout(){
		window.location.href='loginout';
	}

	</script>