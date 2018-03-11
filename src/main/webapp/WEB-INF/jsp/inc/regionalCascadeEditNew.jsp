<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<select name="province" id="provinceList" onchange="queryCitiesList(this.value)"  >
		<option value="">请选择</option>
     		</select> 
	<select name="city" id="cityList" style="margin-left:20px" >
		<option></option>
	</select>
	
<script>

var citiesid=document.getElementById("citiesid_list").value;
var provinceid=document.getElementById("provinceid_lsit").value;



queryProvinceList();
function queryProvinceList(){
	$.ajax({
		type:'post',
		url:'mechInfo/getprovince',
		dataType:'json',
		success:function(data){
		var province="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(provinceid==data[i].provinceCode){
				status="selected";
				queryCitiesList(data[i].provinceCode);
			}
			province+="<option "+status+" value="+data[i].provinceCode+">"+data[i].provinceName+"</option>";
			}
		$("#provinceList").html(province);
			},error:function(){
			alert("没有信息");
			}
		});
}
function queryCitiesList(provinceid){
	$("#areaList").html("<option value=''>请选择</option>");
	if(provinceid == null ){
		$("#areaList").html("<option value=''>请选择</option>");
		return;
	}
	$.ajax({
		type:'post',
		url:'mechInfo/getcity',
		data:'provinceId='+provinceid,
		dataType:'json',
		success:function(data){
		var city="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(citiesid==data[i].cityCode){
				status="selected";
			}
			city+="<option "+status+" value="+data[i].cityCode+">"+data[i].cityName+"</option>";
			}
		$("#cityList").html(city);
			},error:function(){
			alert("没有信息");
			}
		});


}
</script>