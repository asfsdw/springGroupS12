$(window).scroll(function() {
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function() {
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

$(() => {
	$("#deliverySW").on("change", () => {
		let deliverySW = $("#deliverySW").val();
		location.href="DeliveryList?deliverySW="+deliverySW;
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
	if($("[name=idxFlag]").length > 1) length = $("[name=idxFlag]").length;
	if(length > 1) {
		for(let i=0; i<length; i++) {
			if(!$("#idxFlag"+i).prop("disabled")) $("#idxFlag"+i).prop("checked", true);
		}
	}
	else if(!$("#idxFlag0").prop("disabled")) $("#idxFlag0").prop("checked", true);
}
// 전체해제.
function allReset() {
	let length = 1;
	if($("[name=idxFlag]").length > 1) length = $("[name=idxFlag]").length;
	if(length > 1) {
		for(let i=0; i<length; i++) {
			if(!$("#idxFlag"+i).prop("disabled")) $("#idxFlag"+i).prop("checked", false);
		}
	}
	else if(!$("#idxFlag0").prop("disabled")) $("#idxFlag0").prop("checked", false);
}
// 선택반전.
function reverseCheck() {
	let length = 1;
	if($("[name=idxFlag]").length > 1) length = $("[name=idxFlag]").length;
	if(length > 1) {
		for(let i=0; i<length; i++) {
			if(!$("#idxFlag"+i).prop("disabled")) $("#idxFlag"+i).prop("checked", !$("#idxFlag"+i).prop("checked"));
		}
	}
	else if(!$("#idxFlag0").prop("disabled")) $("#idxFlag0").prop("checked", !$("#idxFlag0").prop("checked"));
}
// 테이블의 체크박스 클릭시.
function checkClick() {
	let length = 1;
	if($("[name=idxFlag]").length >= 1) length = $("[name=idxFlag]").length;
	
	if($("#check").is(":checked") == true) {
		for(let i=0; i<length; i++) {
			if(!$("#idxFlag"+i).prop("disabled")) $("#idxFlag"+i).prop("checked", true);
		}
	}
	else {
		for(let i=0; i<length; i++) {
			$("#idxFlag"+i).prop("checked", false);
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
// 신청현황 한번에 바꾸기.
function openSWAllChange() {
	let flag = $("#openSW").val();
	if(flag == '선택') {
		alert("변경할 항목을 선택해주세요.");
		return false;
	}
	
	let ans = confirm("선택한 신청의 신청현황을 변경하시겠습니까?");
	if(ans) {
		let index = [];
		let cnt = 0;
		for(let i=0; i<$("[name=idxFlag]").length; i++) {
			if($("#idxFlag"+i).is(":checked") == true) {
				index[cnt] = i;
				cnt = cnt+1;
			}
		}
		if(index.length == 0) {
			alert("배송현황을 변경할 회원을 선택해주세요.");
			return false;
		}
		
		let part = "";
		let idxs = "";
		
		for(let i=0; i<index.length; i++) {
			if($("#part"+index[i]).text() == '상품') part += "shop,"
			else if($("#part"+index[i]).text() == '일반') part += "sub,"
			idxs += $("#idx"+index[i]).val()+",";
		}
		part = part.substring(0, part.length-1);
		idxs = idxs.substring(0, idxs.length-1);
		
		$.ajax({
			url : "OpenSWChange",
			type: "post",
			data: {
				"part" : part,
				"idxs" : idxs,
				"flag" : flag
			},
			success : (res) => {
				if(res == -1) {
					alert("신청현황이 변경되지 않는 신청이 존재합니다.\n신청현황을 확인하시고 다시 시도해주세요.");
					location.reload();
				}
				else if(res != 0) {
					alert("선택한 신청들의 신청현황이 변경되었습니다.");
					location.reload();
				}
				else alert("신청현황 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
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
// 배송현황 한번에 바꾸기.
function deliverySWAllChange() {
	if($("#deliverySWChange").val() == '선택') {
		alert("변경할 항목을 선택해주세요.");
		return false;
	}
	
	let ans = confirm("선택한 배송의 배송현황을 변경하시겠습니까?");
	if(ans) {
		let index = [];
		let cnt = 0;
		for(let i=0; i<$("[name=idxFlag]").length; i++) {
			if($("#idxFlag"+i).is(":checked") == true) {
				index[cnt] = i;
				cnt = cnt+1;
			}
		}
		if(index.length == 0) {
			alert("배송현황을 변경할 회원을 선택해주세요.");
			return false;
		}
		
		let deliveryIdx = "";
		let deliverySW = $("#deliverySWChange").val();
		
		for(let i=0; i<index.length; i++) {
			deliveryIdx += $("#deliveryIdx"+index[i]).val()+",";
		}
		deliveryIdx = deliveryIdx.substring(0, deliveryIdx.length-1);
		
		$.ajax({
			url : "DeliverySWChange",
			type: "post",
			data: {
				"deliveryIdx" : deliveryIdx,
				"deliverySW" : deliverySW
			},
			success : (res) => {
				if(res == -1) {
					alert("배송현황이 변경되지 않는 배송이 존재합니다.\n배송현황을 확인하시고 다시 시도해주세요.");
					location.reload();
				}
				else if(res != 0) {
					alert("선택한 주문들의 배송현황이 변경되었습니다.");
					location.reload();
				}
				else alert("배송현황 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}

// 등급별로 보기.
function levelPageCheck() {
	let level = $("#levelPage").val();
	location.href="MemberList?level="+level;
}
// 신고상태별로 보기.
function progressPageCheck() {
	let progress = $("#progressPage").val();
	location.href="ComplaintList?progress="+progress;
}
// 한 페이지 최대 수 변경.
function viewPageCheck() {
	let pageSize = $("#viewPageCnt").val();
	location.href="MemberList?pageSize="+pageSize;
}

// 회원 등급변경.
function levelChange(i, idx) {
	let ans = confirm("회원의 등급을 변경하시겠습니까?");
	if(ans) {
		$.ajax({
		url : "MemberLevelChange",
		type: "post",
		data: {
			"idx" : idx,
			"level" : $("#level"+i).val()
		},
		success : (res) => {
			if(res != 0) {
				alert("회원의 등급이 변경되었습니다.");
				location.reload();
			}
			else alert("회원 등급 변경에 실패했습니다.");
		},
		error : () => alert("전송오류")
	});
	}
}
// 선택 등급 변경.
function levelAllChange() {
	let ans = confirm("선택한 회원의 등급을 변경하시겠습니까?");
	if(ans) {
		
		let index = [];
		let cnt = 0;
		for(let i=0; i<$("[name=idxFlag]").length; i++) {
			if($("#idxFlag"+i).is(":checked") == true) {
				index[cnt] = i;
				cnt = cnt+1;
			}
		}
		if(index.length == 0) {
			alert("등급을 변경할 회원을 선택해주세요.");
			return false;
		}
		
		let idxs = "";
		for(let i=0; i<index.length; i++) {
			idxs += $("#idx"+index[i]).val()+",";
		}
		idxs = idxs.substring(0, idxs.length-1);
		
		$.ajax({
			url : "MemberLevelChange",
			type: "post",
			data: {
				"idxs" : idxs,
				"level" : $("#levelSelect").val()
			},
			success : (res) => {
				if(res == -1) {
					alert("등급이 변경되지 않은 회원이 있습니다.\n회원의 등급을 확인 후, 다시 시도해주세요.");
					location.reload();
				}
				else if(res != 0) {
					alert("회원의 등급이 변경되었습니다.");
					location.reload();
				}
				else alert("회원 등급 변경에 실패했습니다.");
			},
			error : () => alert("전송오류")
		});
	}
}

// 신고 처리.
function complaintChange(ctp, i, idx, partIdx) {
	let ans = confirm("신고상태를 변경하시겠습니까?");
	if(ans) {
		let part = $("#part"+i).text();
		let progress = $("#progress"+i).val();
		if(progress == '처리하기') {
			if(part == '게시판') {
				location.href=ctp+"/admin/ComplaintBoardList?idx="+partIdx;
				return false;
			}
			else if(part == '굿즈') {
				location.href=ctp+"/admin/ProductList?idx="+partIdx;
				return false;
			}
		}
		
		alert("확인");
		$.ajax({
			url : "ComplaintChange",
			type: "post",
			data: {
				"idx" : idx,
				"progress" : progress,
				"part" : part
			},
			success : (res) => {
				if(res != 0) {
					alert("신고상태가 변경되었습니다.");
					location.reload();
				}
				else alert("신고상태 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}

// 신고된 개시글 검색.
function complaintBoardSearch() {
	let searchStr = $("#searchStr").val();
	if(searchStr.replace(" ","") == "") {
		alert("게시글 번호를 입력해주세요.");
		return false;
	}
	if(isNaN(searchStr)) {
		alert("게시글 번호를 숫자로 입력해주세요.");
		return false;
	}
	
	searchForm.submit();
}
// 신고된 개시글 공개상태 변경.
function complaintBoardOpenSWChange(i, idx) {
	let openSW = $("#openSW"+i).val();
	
	$.ajax({
		url : "ComplaintBoardOpenSWChange",
		type: "post",
		data: {
			"idx" : idx,
			"openSW" : openSW
		},
		success : (res) => {
			if(res != 0) {
				alert("게시글의 공개상태를 변경했습니다.");
				location.reload();
			}
			else alert("게시글의 공개상태 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.")
		},
		error : () => alert("전송오류")
	});
}