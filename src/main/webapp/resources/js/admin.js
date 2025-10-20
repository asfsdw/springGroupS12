$(window).scroll(function(){
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function(){
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

$(() => {
	$("#deliverySW").on("change", () => {
		let deliverySW = $("#deliverySW").val();
		console.log(deliverySW);
		location.href = "DeliveryList?deliverySW="+deliverySW;
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

// 신청현황 변경.
function openSWChange(i, part, idx) {
	let ans = confirm("신청현황을 변경하시겠습니까?");
	if(ans) {
		if(part == 'shop') {
			let openSW = $("#openSW"+i).val();
			
			$.ajax({
				url : "OpenSWChange",
				type: "post",
				data: {
					"part" : part,
					"idx" : idx,
					"flag" : openSW
				},
				success : (res) => {
					if(res != 0) {
						alert("신청현황이 변경되었습니다.");
						location.reload();
					}
					else alert("신청현황 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
				},
				error : () => alert("전송오류")
			});
		}
		else if(part == 'sub') {
			let subProgress = $("#openSW"+i).val();
			
			$.ajax({
				url : "OpenSWChange",
				type: "post",
				data: {
					"part" : part,
					"idx" : idx,
					"flag" : subProgress
				},
				success : (res) => {
					if(res != 0) {
						alert("신청현황이 변경되었습니다.");
						location.reload();
					}
					else alert("신청현황 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
				},
				error : () => alert("전송오류")
			});
		}
	}
}

// 배송현황 변경.
function deliverySWChange(i, deliveryIdx) {
	let ans = confirm("상품의 배송현황을 변경하시겠습니까?");
	if(ans) {
		let deliverySW = $("#deliverySW"+i).val();
		
		$.ajax({
			url : "DeliverySWChange",
			type: "post",
			data: {
				"deliveryIdx" : deliveryIdx,
				"deliverySW" : deliverySW
			},
			success : (res) => {
				if(res != 0) {
					alert("배송현황이 변경되었습니다.");
					location.reload();
				}
				else alert("배송현황 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}
// 배송 끝난 상품 삭제.
function deliveryDelete(i, deliveryIdx) {
	if($("#deliverySW"+i).val() != '구매완료') alert("구매완료가 되지 않은 상품입니다.\n구매자가 구매완료를 누를 때까지 삭제할 수 없습니다.");
	let ans = confirm("선택하신 주문 내역을 삭제하시겠습니까?");
	if(ans) {
		$.ajax({
			url : "DeliveryDelete",
			type: "post",
			data: {"deliveryIdx" : deliveryIdx},
			success : (res) => {
				if(res != 0) {
					alert("배송내역이 삭제되었습니다.");
					location.reload();
				}
				else alert("배송내역 삭제에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}