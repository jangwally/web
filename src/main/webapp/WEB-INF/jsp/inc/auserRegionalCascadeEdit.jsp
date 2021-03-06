<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<select name="province" title="省"  id="provinceEdit" class="mustFill" onchange="queryCitiesEdit(this.value,'','')"  style="width: 90px">
		<option value="">请选择</option>
     		</select> 
	<select name="city" title="市"  id="cityEdit" class="mustFill" onchange="queryAreaEdit(this.value,'')"   style="width: 90px">
	<option></option>
	</select>
	<select name="area" title="区" id="areaEdit"   style="width: 90px">
		<option></option>
	</select>
<script>
var citiesid_edit=document.getElementById("citiesid_edit").value;
var areaid_edit=document.getElementById("areaid_edit").value;
var provinceId_edit=document.getElementById("provinceid_edit").value;

queryProvinceEdit();
function queryProvinceEdit(){
	$("#provinceid").html("<option value=''>请选择</option>");
	$.ajax({
		type:'post',
		url:'mechInfo/getprovince',
		dataType:'json',
		success:function(data){
		var province="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(provinceId_edit==data[i].provinceCode){
				status="selected";
				queryCitiesEdit(provinceId_edit,citiesid_edit,areaid_edit);
			}
			province+="<option "+status+" value="+data[i].provinceCode+">"+data[i].provinceName+"</option>";
			}
		$("#provinceEdit").html(province);
			},
			error:function(){
			alert("没有信息");
			}
		});
}

function queryCitiesEdit(provinceId,citiesid,areaid){
	$("#citiesid").html("<option value=''>请选择</option>");
	if(provinceId == ""){
		$("#citiesid").html("<option value=''>请选择</option>");
		return;
	}
	$.ajax({
		type:'post',
		url:'mechInfo/getcity',
		data:'provinceId='+provinceId,
		dataType:'json',
		success:function(data){
		var city="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(citiesid==data[i].cityCode){
				status="selected";
				queryAreaEdit(data[i].cityCode,areaid);
			}
			city+="<option "+status+" value="+data[i].cityCode+">"+data[i].cityName+"</option>";
			}
			$("#cityEdit").html(city);
		
		},error:function(){
			alert("没有信息");
			}
		});
}
function queryAreaEdit(cityID,areaid){
	$("#areaEdit").html("<option value=''>请选择</option>");
	if(cityID == ""){
		$("#areaEdit").html("<option value=''>请选择</option>");
		return;
	}
	$.ajax({
		type:'post',
		url:'mechInfo/getarea',
		data:'cityId='+cityID,
		dataType:'json',
		success:function(data){
		var area="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(areaid==data[i].areaid){
				status="selected";
			}
			if(data[i].level==2){
				area+="<option "+status+" value="+data[i].areaid+">"+data[i].area+"</option>";
			}
			//area+="<option "+status+" value="+data[i].areaid+">"+data[i].area+"</option>";
			}
		$("#areaEdit").html(area);
			},error:function(){
			alert("没有信息");
			}
		});
}

</script>