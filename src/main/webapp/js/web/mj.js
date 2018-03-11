$(document).ready(function(){
	$(".header ul li").click(function(){
		$(".header ul li a").removeClass("header-ul-select");
		$(this).find("a").addClass("header-ul-select");
	});
	$(".content-div .choose-type").click(function(){
		$(".content-div .choose-type h4").css("color","black")
		$(this).find("h4").css("color","white")
		
		$(".content-div .choose-type .white").hide();
		$(this).find(".white").show();
		
		$(".content-div .choose-type .grey").show();
		$(this).find(".grey").hide();
		
		$(".content-div .choose-type div").css("background","white");
		$(this).find("div").css("background","#e7b865");
	});
});
			