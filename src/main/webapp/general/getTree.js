/*  function onClick(e, treeId, treeNode) {
      var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
          nodes = zTree.getSelectedNodes(),
          v = "";
      nodes.sort(function compare(a, b) {
          return a.id - b.id;
      });
      for (var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
      }
      if (v.length > 0) v = v.substring(0, v.length - 1);
      var cityObj = $("#citySel");
      cityObj.attr("value", v);
  }
*/
function showMenu() {
     var cityObj = $("#citySel");
     var cityOffset = $("#citySel").offset();
    $("#menuContent").css({left: 76 + "px", top: 36 + "px"}).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);

    var classArray = new Array();
    $('.aaa ul li div a').siblings('div').each(function () {
        var className = $(this).attr('class');
        classArray.push(className);
        if (className == 'folder_collapsable' || className == 'folder_expandable' || className == 'file') {
            var $len = classArray.length;
            var $a_len = $(this).siblings('a').html().length;
            $(this).parent().css('width', $len * 22 + $a_len * 12 + 22);
            classArray.splice(0, classArray.length);
        }
    });
}

function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);

}

function onBodyDown(event) {

    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
        hideMenu();
    }
}

function getTree(data, pId, isInit) {
    var tree;
    if (isInit) {
        var rootData = null;
        for (var i = 0; i < data.length; i++) {
            if (data[i].id == pId) {
                rootData = data[i];
                break;
            }
        }
        tree = '<ul class="tree treeFolder">';
        if (rootData != null)
            tree += "<a onclick='addInfo(this)' data-belongValue='" + rootData.id + "'>" + rootData.name + "</a>";
    } else {
        tree = '<ul>';
    }
    for (var i in data) {
        if (data[i].pId == pId) {
            tree += "<li><a onclick='addInfo(this)' data-belongValue='" + data[i].id + "'>" + data[i].name + "</a>";
            tree += getTree(data, data[i].id, false);
            tree += "</li>";
        }
    }
    return tree + "</ul>";

}

function addInfo(obj) {
    var parentOrgValue = $(obj).attr("data-belongValue");
    console.log($(obj).attr("data-belongValue"));
    $("#parentOrgValue").val(parentOrgValue);
    $("#parentOrgName").val($(obj).html());

    hideMenu();
}
