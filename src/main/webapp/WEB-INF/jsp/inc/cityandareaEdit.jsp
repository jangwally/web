<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<select name="area" title="区" id="areaEdit" class="mustFill"  style="width: 90px" <c:if test="${not empty user.area}">disabled</c:if>>
		<option></option>
	</select>
<script>
var cityId=$("#citiesid_edit").val();
if($("#areaid_edit").val!=''){
	var areaid_edit=$("#areaid_edit").val();
	
}else{
var areaid_edit=document.getElementById("areaid_edit").value;
}

queryAreaEdit(cityId,areaid_edit)
function queryAreaEdit(cityId,areaid){
	$("#areaEdit").html("<option value=''>请选择</option>");
	if(cityId == ""){
		$("#areaEdit").html("<option value=''>请选择</option>");
		return;
	}
	$.ajax({
		type:'post',
		url:'mechInfo/getarea',
		data:'cityId='+cityId,
		dataType:'json',
		success:function(data){
		var area="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(areaid==data[i].areaid){
				status="selected";
			}
			if(data[i].level==1){
				area+="<option "+status+" value="+data[i].areaid+">"+data[i].area+"</option>";
			}
			}
		$("#areaEdit").html(area);
			},error:function(){
			alert("没有信息");
			}
		});
}

</script>