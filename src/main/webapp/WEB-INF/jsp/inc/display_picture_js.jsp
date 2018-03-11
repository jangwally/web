<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
			//						上传ID，宽，高，显示ID，显示IE
            function setImagePreview(pic,width,height,view,IEID) { //下面用于图片上传预览功能       (IEID)隐藏|| 为IE滤镜提供 
                var docObj = document.getElementById(pic);
                var imgObjPreview = document.getElementById(view);
                
                if (docObj.files && docObj.files[0]) { //火狐下，直接设img属性
                    imgObjPreview.style.display = 'block';
                    imgObjPreview.style.width = width;
                    imgObjPreview.style.height = height;
                    //imgObjPreview.src = docObj.files[0].getAsDataURL();
                    //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
                   imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                } else { //IE下，使用滤镜
                    docObj.select();
                    docObj.blur();
                    var imgSrc = document.selection.createRange().text;//IE11 document.selection.createRange(); --->window.getSelection();
                    var localImagId = document.getElementById(IEID);
                    localImagId.style.width = width;
                    localImagId.style.height = height;
                    localImagId.style.display = 'block';
                    try {
                        localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                    } catch(e) {
                        alert("您上传的图片格式不正确，请重新选择!");
                        return false;
                    }
                    imgObjPreview.style.display = 'none';
                    document.selection.empty();
                }
                return true;
            }
        </script>