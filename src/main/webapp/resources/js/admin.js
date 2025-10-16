$(window).scroll(function(){
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function(){
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

// 메뉴 숨기기, 보이기.
$(function() {
	$(".sub").hide();
	
	$(".main").off("click").on("click", function(e) {
		e.preventDefault();
		e.stopPropagation();
		
		let $target = $(this).next(".sub");
		if($target.is(":visible")) $target.slideUp(500);
		else{
			$(".sub").slideUp(500);
			$target.slideDown(500);
		}
	});
});

// 전체선택.
function allCheck() {
	let length = 1;
	if(myform.idxFlag.length > 2) length = myform.idxFlag.length;
	if(length > 1) {
		for(let i=0; i<length; i++) {
			if(!myform.idxFlag[i].disabled) myform.idxFlag[i].checked = true;
		}
	}
	else if(!myform.idxFlag.disabled) myform.idxFlag.checked = true;
}
// 전체해제.
function allReset() {
	let length = 1;
	if(myform.idxFlag.length > 2) length = myform.idxFlag.length;
	if(length > 1) {
		for(let i=0; i<length; i++) {
			if(!myform.idxFlag[i].disabled) myform.idxFlag[i].checked = false;
		}
	}
	else if(!myform.idxFlag.disabled) myform.idxFlag.checked = false;
}
// 선택반전.
function reverseCheck() {
	let length = 1;
	if(myform.idxFlag.length > 2) length = myform.idxFlag.length;
	if(length > 1) {
		for(let i=0; i<length; i++) {
			if(!myform.idxFlag[i].disabled) myform.idxFlag[i].checked = !myform.idxFlag[i].checked;
		}
	}
	else if(!myform.idxFlag.disabled) myform.idxFlag.checked = !myform.idxFlag.checked;
}
// 테이블의 체크박스 클릭시.
function checkClick() {
	let length = 1;
	if($("[name=idxFlag]").length >= 2) length = $("[name=idxFlag]").length;
	
	if(document.getElementById("check").checked == true) {
		for(let i=0; i<length; i++) {
			document.getElementById("idxFlag"+i).checked = true;
		}
	}
	else {
		for(let i=0; i<length; i++) {
			document.getElementById("idxFlag"+i).checked = false;
		}
	}
}