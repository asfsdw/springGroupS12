$(window).scroll(function() {
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function() {
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

// 상품 등록.
function fCheck() {
	let mid = $("#mid").val().replace(" ","");
	let nickName = $("#nickName").val().replace(" ","");
	let kategorie = $("#kategorie").val();
	let title = $("#title").val();
	let content = CKEDITOR.instances.CKEDITOR.getData();
	let price = $("#price").val().replace(" ","");
	let quantity = $("#quantity").val().replace(" ","");
	
	if(mid == "") {
		alert("로그인해주세요.");
		return false;
	}
	if(nickName == "") {
		alert("로그인해주세요.");
		return false;
	}
	if(kategorie == "선택") {
		alert("상품분류를 선택해주세요.");
		return false;
	}
	if(title.trim() == "") {
		alert("상품명을 입력해주세요.");
		return false;
	}
	if(content.trim() == "") {
		alert("상품설명를 입력해주세요.");
		return false;
	}
	if(isNaN(price) || price < 0) {
		if(price < 0) alert("상품의 가격은 음수가 될 수 없습니다.");
		alert("상품의 가격을 숫자로 입력해주세요.");
		return false;
	}
	if(isNaN(quantity) || quantity < 0) {
		if(quantity < 0) alser("재고의 개수는 음수가 될 수 없습니다.");
		alert("재고 개수를 숫자로 입력해주세요.");
		return false;
	}
	
	let fName = $("#fName").val().toLowerCase();
	let ext = fName.substring(fName.lastIndexOf(".")+1);
	if(!ext == "jpg" || !ext == "jpeg" || !ext == "png" || !ext == "gif") {
		alser("상품 대표 이미지의 확장자는 jpg, jpeg, png, gif만 허용됩니다.");
		return false;
	}
	
	let maxSize = 1024 * 1024 * 20;
	if(document.getElementById("fName").files[0].size > maxSize) {
		alert("상품 대표 이미지의 파일 크기가 너무 큽니다. 파일은 최대 20mb까지입니다.");
		return false;
	}
	
	productAddForm.submit();
}

// 상품 등록 신청.
function subCheck(ctp) {
	let mid = $("#mid").val().replace(" ","");
	let nickName = $("#nickName").val().replace(" ","");
	let kategorie = $("#kategorie").val();
	let title = $("#title").val();
	let content = CKEDITOR.instances.CKEDITOR.getData();
	let price = $("#price").val().replace(" ","");
	let quantity = $("#quantity").val().replace(" ","");
	
	if(mid == "") {
		alert("로그인해주세요.");
		return false;
	}
	if(nickName == "") {
		alert("로그인해주세요.");
		return false;
	}
	if(kategorie == "선택") {
		alert("상품분류를 선택해주세요.");
		return false;
	}
	if(title.trim() == "") {
		alert("상품명을 입력해주세요.");
		return false;
	}
	if(content.trim() == "") {
		alert("상품설명를 입력해주세요.");
		return false;
	}
	if(isNaN(price) || price < 0) {
		if(price < 0) alert("상품의 가격은 음수가 될 수 없습니다.");
		alert("상품의 가격을 숫자로 입력해주세요.");
		return false;
	}
	if(isNaN(quantity) || quantity < 0) {
		if(quantity < 0) alser("재고의 개수는 음수가 될 수 없습니다.");
		alert("재고 개수를 숫자로 입력해주세요.");
		return false;
	}
	
	let fName = $("#fName").val().toLowerCase();
	let ext = fName.substring(fName.lastIndexOf(".")+1);
	if(!ext == "jpg" || !ext == "jpeg" || !ext == "png" || !ext == "gif") {
		alser("상품 대표 이미지의 확장자는 jpg, jpeg, png, gif만 허용됩니다.");
		return false;
	}
	
	let maxSize = 1024 * 1024 * 20;
	if(document.getElementById("fName").files[0].size > maxSize) {
		alert("상품 대표 이미지의 파일 크기가 너무 큽니다. 파일은 최대 20mb까지입니다.");
		return false;
	}
	
	productAddForm.action = ctp+"/shop/ProductAddSub";
	productAddForm.submit();
}

// 상품 바로 구매.
function soldCheck() {
	let orderQuantity = $("#orderQuantity").val();
	if(orderQuantity <= 0 || orderQuantity == null) {
		alert("구매하실 수량을 입력해주세요.");
		return false;
	}
	
	productForm.submit();
}

// 장바구니에 담기.
function addShoppingBag(idx, mid, nickName) {
	let orderQuantity = $("#orderQuantity").val();
	if(isNaN(orderQuantity) || orderQuantity < 1) {
		alert("주문 개수를 확인해주세요.");
		return false;
	}
	
	$.ajax({
		url : "ShoppingBag",
		type: "post",
		data: {
			"idx" : idx,
		"mid" : mid,
		"nickName" : nickName,
		"orderQuantity" : orderQuantity
		},
		success : (res) => {
			if(res != 0) {
				let ans = confirm("쇼핑을 계속하시겠습니까?");
				if(ans) location.href="Goods";
				else location.href="ShoppingBag?mid="+mid;
			}
			else alert("장바구니에 넣지 못했습니다.\n다시 시도해주시요.");
		},
		error : () => alert("전송오류")
	});
}

// 초기 구매 가격들 설정.
$(() => {
	let price = 0;
	let totPrice = 0;
	
	for(let i=0; i<$("[name=bagCheck]").length-1; i++) {
		price = parseInt($("#productPrice"+i).text().replace(",","").replace("원","")) * $("#orderQuantity"+i).val();
		$("#price"+i).val(price);
		$("#productPrice"+i).html(price.toLocaleString()+"원");
		
		totPrice += parseInt($("#productPrice"+i).text().replace(",","").replace("원",""));
	}
	$("#totPrice").html(totPrice.toLocaleString()+"원");
});

// 테이블의 체크박스 클릭시.
function tableCheckChange() {
	let length = 1;
	if($("[name=bagCheck]").length-1 > 1) length = $("[name=bagCheck]").length-1;
	
	if(document.getElementById("bagCheck").checked == true) {
		for(let i=0; i<length; i++) {
			document.getElementById("bagCheck"+i).checked = true;
			priceChange(i, i);
		}
	}
	else {
		for(let i=0; i<length; i++) {
			document.getElementById("bagCheck"+i).checked = false;
			priceChange(i, i);
		}
	}
}
// 상품의 체크박스 클릭시.
function checkChange(index, price) {
	let cnt = 0;
	let length = 1;
	if($("[name=bagCheck]").length-1 > 1) length = $("[name=bagCheck]").length-1;
	
	for(let i=0; i<length; i++) {
		if(document.getElementById("bagCheck"+i).checked == true) cnt++;
	}
	if(length == cnt) document.getElementById("bagCheck").checked = true;
	else document.getElementById("bagCheck").checked = false;
	priceChange(index, price);
}

// 구매가격 변경.
function priceChange(index, price) {
	let length = 1;
	if($("[name=bagCheck]").length-1 > 1) length = $("[name=bagCheck]").length-1;
	
	// 체크박스로 불러왔을 때.
	if(price == index) price = parseInt($("#productPrice"+index).text().replace(",","").replace("원",""))/$("#orderQuantity"+index).val();
	
	let totPrice = 0;
	
	$("#productPrice"+index).html((price*$("#orderQuantity"+index).val()).toLocaleString()+"원");
	
	for(let i=0; i<length; i++) {
		if(document.getElementById("bagCheck"+i).checked == true) {
			totPrice += parseInt($("#productPrice"+i).text().replace(",","").replace("원",""));
		}
	}
	$("#totPrice").html(totPrice.toLocaleString()+"원");
}

// 장바구니 상품 삭제.
function shoppingBagDelete(idx) {
	let ans = confirm("장바구니에서 물품을 삭제하시겠습니까?");
	if(ans) {
		$.ajax({
			url : "ShoppingBagDelete",
			type: "post",
			data: {"idx" : idx},
			success : (res) => {
				if(res != 0) {
					alert("장바구니에서 상품이 삭제되었습니다.");
					location.reload();
				}
				else alert("오류가 발생했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}

// 장바구니에서 구매.
function buyCheck() {
	let idx = [];
	let title = [];
	let orderQuantity = [];
	let cnt = 0;
	for(let i=0; i<$("[name=bagCheck]").length-1; i++) {
		if(document.getElementById("bagCheck"+i).checked == true) {
			idx[cnt] = $("#idx"+i).val();
			title[cnt] = $("#title"+i).val();
			orderQuantity[cnt] = $("#orderQuantity"+i).val();
			cnt++;
		}
	}
	for(let i=0; i<idx.length; i++) {
		if(isNaN(orderQuantity[i]) || orderQuantity[i] < 1) {
			alert("구매하실 상품의 개수가 정상적이지 않습니다.\n다시 시도해주세요.");
			return false;
		}
	}
	
	$("#idxs").val(idx);
	$("#titles").val(title)
	$("#orderQuantitys").val(orderQuantity);
	
	buyForm.action = "Product";
	buyForm.submit();
}

// 포인트 사용.
function pointUse(flag) {
	let point = $("#point").val();
	if(flag == 1) {
		if(point == 0 || point%100 != 0) {
			alert("포인트를 사용하시려면 포인트를 입력해주세요.");
			return false;
		}
		
		$("#point").attr("disabled", true);
		
		totPrice = Number($("#totPrice").text().replace(",","").replace("원",""));
		totPrice = totPrice - (point/10);
		$("#totPrice").text(totPrice.toLocaleString()+"원");
	}
	else if(flag == 2) {
		$("#point").attr("disabled", false);
		$("#point").val(0);
		
		totPrice = Number($("#totPrice").text().replace(",","").replace("원",""));
		totPrice = totPrice + (point/10);
		$("#totPrice").text(totPrice.toLocaleString()+"원");
	}
	$("#usedPoint").val(point);
}

// 구매.
function buy(address1, address2, address3, address4) {
	$("#buyBtn").attr("disabled", true);
	
	let str = "";
	if(address1 != undefined) {
		str += '<table class="table table-bordered">';
		str += '<tr class="table-secondary">';
		str += '<th colspan="3">배송지</th>';
		str += '</tr>';
		str += '<tr>';
		str += '<th>우편번호</th>';
		str += '<td><input type="text" name="postcode" id="sample6_postcode" value="'+address1+'" placeholder="우편번호" class="form-control"></td>';
		str += '<td><input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary btn-sm"></td>';
		str += '</tr>';
		str += '<tr>';
		str += '<th>주소</th>';
		str += '<td colspan="2"><input type="text" name="roadAddress" id="sample6_address" size="50" value="'+address2+'" placeholder="주소" class="form-control mb-1"></td>';
		str += '</tr>';
		str += '<tr>';
		str += '<th>상세주소</th>';
		str += '<td><input type="text" name="detailAddress" id="sample6_detailAddress" value="'+address3+'" placeholder="상세주소" class="form-control me-2"></td>';
		str += '<td colspan="2"><input type="text" name="extraAddress" id="sample6_extraAddress" value="'+address4+'" placeholder="참고항목" class="form-control"></td>';
		str += '</tr>';
		str += '</table>';
	}
	else{
		str += '<table class="table table-bordered">';
		str += '<tr class="table-secondary">';
		str += '<th colspan="3">배송지</th>';
		str += '</tr>';
		str += '<tr>';
		str += '<th>우편번호</th>';
		str += '<td><input type="text" name="postcode" id="sample6_postcode" placeholder="우편번호" class="form-control"></td>';
		str += '<td><input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary btn-sm"></td>';
		str += '</tr>';
		str += '<tr>';
		str += '<th>주소</th>';
		str += '<td colspan="2"><input type="text" name="roadAddress" id="sample6_address" size="50" placeholder="주소" class="form-control mb-1"></td>';
		str += '</tr>';
		str += '<tr>';
		str += '<th>상세주소</th>';
		str += '<td><input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소" class="form-control me-2"></td>';
		str += '<td colspan="2"><input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목" class="form-control"></td>';
		str += '</tr>';
		str += '</table>';
	}
	str += '<div class="row">';
	str += '<div class="col">';
	str += '</div>';
	str += '<div class="col text-start">';
	str += '<input type="button" value="구매" onclick="addressCheck()" class="btn btn-success" />';
	str += '</div>';
	str += '<div class="col text-end">';
	str += '<input type="button" value="돌아가기" onclick="history.back()" class="btn btn-warning" />';
	str += '</div>';
	str += '<div class="col">';
	str += '</div>';
	str += '</div>';
	str += '<input type="hidden" id="address" name="address" value="" />';
	str += '</form>';
	
	$("#demo").html(str);
}
function addressCheck() {
	let address1 = $("#sample6_postcode").val().trim();
	let address2 = $("#sample6_address").val().trim();
	let address3 = $("#sample6_detailAddress").val().trim();
	let address4 = $("#sample6_extraAddress").val().trim();
	if(address1 == "" || address2 == "" || address3 == "" || address4 == "") {
		alert("주소를 입력해주세요.");
		return false;
	}
	
	let address = address1+"/"+address2+"/"+address3+"/"+address4;
	$("#address").val(address);
	
	buyForm.submit();
}

// 주문취소.
function deliveryCancel(deliveryIdx, usedPoint) {
	let ans = confirm("주문을 취소하시면 장바구니에서도 삭제됩니다.\n정말로 주문을 취소하시겠습니까?");
	if(ans) {
		$.ajax({
			url : "DeliveryCancel",
			type: "post",
			data: {
				"deliveryIdx" : deliveryIdx,
				"usedPoint" : usedPoint
			},
			success : (res) => {
				if(res != 0) {
					alert("주문이 취소되었습니다.");
					location.reload();
				}
				else alert("주문 취소에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}
// 구매완료.
function deliveryComp(deliveryIdx) {
	let ans = confirm("구매하신 상품을 받으셨습니까?");
	if(ans) {
		$.ajax({
			url : "DeliveryComp",
			type: "post",
			data: {"deliveryIdx" : deliveryIdx},
			success : (res) => {
				if(res != 0) {
					alert("주문이 완료되었습니다.\n구매하신 상품의 리뷰를 작성하실 수 있으시며,\n리뷰를 작성하시면 100포인트가 증정됩니다.");
					location.reload();
				}
				else alert("주문 취소에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}

// 주문검색.
function deliverySearch() {
	let deliveryIdx = $("#deliveryIdx").val().replace(" ","");
	if(deliveryIdx == "") {
		alert("주문번호를 입력해주세요.");
		$("#deliveryIdx").focus();
		return false;
	}
	
	deliveryForm.submit();
}

// 리뷰 보이기.
function reviewShow() {
	$("#reviewShowBtn").hide();
	$("#reviewHideBtn").show();
	$("#reviewBox").show();
}
// 리뷰 가리기.
function reviewHide() {
	$("#reviewShowBtn").show();
	$("#reviewHideBtn").hide();
	$("#reviewBox").hide();
}
// 리뷰 등록.
function reviewCheck(ctp, idx, mid, nickName, hostIP) {
	let star = reviewForm.star.value;
	let review = $("#review").val();
	
	if(star == "") {
		alert("별점을 부여해 주세요");
		return false;
	}
	
	let query = {
		"parentIdx" : idx,
		"mid" : mid,
		"nickName" : nickName,
		"content" : review,
		"star" : star,
		"hostIP" : hostIP
	}
	$.ajax({
		url : ctp+"/reply/ReviewInput",
		type: "post",
		data: query,
		success: (res) => {
			if(res == 2) {
				alert("리뷰가 등록되었습니다.\n첫 리뷰 보너스로 100포인트가 적립됩니다.");
				location.reload();
			}
			else if(res == 1) {
				alert("리뷰가 등록되었습니다.");
				location.reload();
			}
			else if(res == -1) {
				alert("이미 리뷰를 입력하신 상품입니다.");
				location.reload();
			}
			else if(res == -2) {
				alert("리뷰가 등록되었습니다.\n이전에 리뷰를 입력하신 상품이기 때문에\n포인트는 지급되지 않습니다.");
				location.reload();
			}
			else alert("리뷰 등록에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
		},
		error : () =>	alert("전송오류!")
	});
}
// 리뷰 삭제.
function reviewDelete(ctp, idx, mid) {
	let ans = confirm("리뷰를 삭제하시겠습니까?");
	if(ans) {
		$.ajax({
			url : ctp+"/reply/ReviewDelete",
			type: "post",
			data: {
				"idx" : idx,
				"mid" : mid
			},
			success : (res) => {
				if(res != 0) {
					alert("리뷰가 삭제되었습니다.");
					location.reload();
				}
				else alert("리뷰 삭제에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			},
			error : () => alert("전송오류")
		});
	}
}

// 상품 신고 처리.
function complaintCheck(idx, mid, title) {
	if(!$("input[type='radio'][name='complaint']:checked").is(':checked')) {
		alert("신고항목을 선택해주세요.");
		return false;
	}
	if($("input[type='radio']:checked").val() == '기타' && $("#etcTxt").val() == "") {
		alert("사유를 입력해주세요.");
		return false;
	}
	
	let cpContent = modalForm.complaint.value;
	if(cpContent == "기타") cpContent += "/"+$("#etcTxt").val();
	
	$.ajax({
		url : "ShopComplaint",
		type: "post",
		data: {
			"part" : "shop",
			"partIdx" : idx,
			"parentTitle" : title,
			"cpMid" : mid,
			"cpContent" : cpContent
		},
		success : (res) => {
			if(res != 0) {
				alert("신고되었습니다.");
				location.reload();
			}
			else alert("신고되지 않았습니다.");
		},
		error : () => alert("전송오류")
	});
}

// 상품 수정전 확인.
function ProductUpdateCheck() {
	let mid = $("#mid").val().replace(" ","");
	let nickName = $("#nickName").val().replace(" ","");
	let kategorie = $("#kategorie").val();
	let title = $("#title").val();
	let content = CKEDITOR.instances.CKEDITOR.getData();
	let price = $("#price").val().replace(" ","");
	let quantity = $("#quantity").val().replace(" ","");
	
	if(mid == "") {
		alert("로그인해주세요.");
		return false;
	}
	if(nickName == "") {
		alert("로그인해주세요.");
		return false;
	}
	if(kategorie == "선택") {
		alert("상품분류를 선택해주세요.");
		return false;
	}
	if(title.trim() == "") {
		alert("상품명을 입력해주세요.");
		return false;
	}
	if(content.trim() == "") {
		alert("상품설명를 입력해주세요.");
		return false;
	}
	if(isNaN(price) || price < 0) {
		if(price < 0) alert("상품의 가격은 음수가 될 수 없습니다.");
		alert("상품의 가격을 숫자로 입력해주세요.");
		return false;
	}
	if(isNaN(quantity) || quantity < 0) {
		if(quantity < 0) alser("재고의 개수는 음수가 될 수 없습니다.");
		alert("재고 개수를 숫자로 입력해주세요.");
		return false;
	}
	
	productUpdateForm.submit();
}