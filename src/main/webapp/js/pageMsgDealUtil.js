if(isThisName("msg")){
    alert(GetQueryString("msg"));
}
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  decodeURI(r[2]); return null;
}

function isThisName(name){
    var reg=eval("/"+name+"/g");
    var r = window.location.search.substr(1);
    var flag=reg.test(r);
    if(flag){
        return true;
    }else{
        return false;
    }
}