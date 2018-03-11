<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="../../js/jquery.min.js" ></script>
<style>
    html,body{margin: 0;padding: 0;height: 100%;}
    html{background: #000005}
    img{
        position: absolute;top: 50%;left: 50%;
        margin:20px auto;background-color:#75C934;
        text-align:center;line-height:100px;font-size:18px;
        /*过渡效果*/
        -webkit-transition: transform 0.8s;
    }
    input{background: yellowgreen; border: none; padding: 1em; color: white; border-radius: 5px;}
</style>
<div class="pageContent">
    <img src="${imgUrl}" class="headeait" >
    <input style="position: absolute;top: 50%;left: 50%;" onclick="rotate()" type="button" value="点击旋转" />
</div>

<script type="text/javascript">
    var height = $('img').height();
    var width = $('img').width();
    var input_width = $('input').width();
    $('img').css('margin-top',-(height/1.5));
    $('img').css('margin-left',-(width/2));
    $('input').css('margin-top',height/2);
    $('input').css('margin-left',-(input_width/2));
    var i = 1
    function rotate(){
        //$('img').css('animation-duration','2s');
        $('img').css('transform','rotate('+90*i+'deg)');
        i++
    }
</script>

