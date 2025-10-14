$(window).scroll(function(){
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function(){
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

function fCheck() {
	let mid = $("#mid").val().trim();
	let nickName = '${sNickName}';
	let kategorie = $("#kategorie").val();
	let title = $("#title").val();
	let content = CKEDITOR.instances.CKEDITOR.getData();
	let price = $("#price").val().trim();
	let quantity = $("#quantity").val().trim();
	let pwd = $("#pwd").val().trim();
	
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
	if(Number.isNaN(price) || price < 0) {
		if(price < 0) alert("상품의 가격은 음수가 될 수 없습니다.");
		alert("상품의 가격을 숫자로 입력해주세요.");
		return false;
	}
	if(Number.isNaN(quantity) || quantity < 0) {
		if(quantity < 0) alser("재고의 개수는 음수가 될 수 없습니다.");
		alert("재고 개수를 숫자로 입력해주세요.");
		return false;
	}
	if(pwd == "") {
		alert("비밀번호를 입력해주세요.");
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

function subCheck(ctp) {
	let mid = $("#mid").val().trim();
	let nickName = '${sNickName}';
	let kategorie = $("#kategorie").val();
	let title = $("#title").val();
	let content = CKEDITOR.instances.CKEDITOR.getData();
	let price = $("#price").val().trim();
	let quantity = $("#quantity").val().trim();
	let pwd = $("#pwd").val().trim();
	
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
	if(Number.isNaN(price) || price < 0) {
		if(price < 0) alert("상품의 가격은 음수가 될 수 없습니다.");
		alert("상품의 가격을 숫자로 입력해주세요.");
		return false;
	}
	if(Number.isNaN(quantity) || quantity < 0) {
		if(quantity < 0) alser("재고의 개수는 음수가 될 수 없습니다.");
		alert("재고 개수를 숫자로 입력해주세요.");
		return false;
	}
	if(pwd == "") {
		alert("비밀번호를 입력해주세요.");
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

function soldCheck() {
	let orderQuantity = $("#orderQuantity").val();
	if(orderQuantity <= 0 || orderQuantity == null) {
		alert("구매하실 수량을 입력해주세요.");
		return false;
	}
	
	productForm.submit();
}

function addShoppingBag(idx, mid, nickName) {
	let orderQuantity = $("#orderQuantity").val();
	if(Number.isNaN(orderQuantity) || orderQuantity < 1) {
		alert("주문 개수를 확인해주세요.");
		return false;
	}
	
	let quary = {
		"idx" : idx,
		"mid" : mid,
		"nickName" : nickName,
		"orderQuantity" : orderQuantity
	};
	$.ajax({
		url : "ShoppingBag",
		type: "post",
		data: quary,
		success : (res) => {
			if(res != 0) {
				let ans = confirm("쇼핑을 계속하시겠습니까?");
				if(ans) location.href = "Goods";
				else location.href = "ShoppingBag";
			}
			else alert("장바구니에 넣지 못했습니다.\n다시 시도해주시요.");
		},
		error : () => alert("전송오류")
	});
}

// 초기 구매 가격들 설정.
$(() => {
	let totPrice = 0;
	let price = 0;
	
	for(let i=1; i<=$("[name=orderQuantity]").length; i++) {
		price = Number.parseInt($("#price"+i).text()) * $("#orderQuantity"+i).val();
		$("#price"+i).html(price+"원");
		
		totPrice += Number.parseInt($("#price"+i).text());
	}
	$("#totPrice").html(totPrice+"원");
});

// 테이블의 체크박스 클릭시.
function tableCheckChange() {
	let length = 1;
	if($("[name=bagCheck]").length-1 >= 2) length = $("[name=bagCheck]").length-1;
	
	if(document.getElementById("bagCheck").checked == true) {
		for(let i=1; i<=length; i++) {
			document.getElementById("bagCheck"+i).checked = true;
			priceChange(i, i);
		}
	}
	else {
		for(let i=1; i<=length; i++) {
			document.getElementById("bagCheck"+i).checked = false;
			priceChange(i, i);
		}
	}
}
// 상품의 체크박스 클릭시.
function checkChange(count, price) {
	let cnt = 0;
	let length = 1;
	if($("[name=bagCheck]").length-1 >= 2) length = $("[name=bagCheck]").length-1;
	
	for(let i=1; i<=length; i++) {
		if(document.getElementById("bagCheck"+i).checked == true) cnt++;
	}
	if(length == cnt) document.getElementById("bagCheck").checked = true;
	else document.getElementById("bagCheck").checked = false;
	priceChange(count, price);
}

// 구매가격 변경.
function priceChange(count, price) {
	let length = 1;
	if($("[name=bagCheck]").length-1 >= 2) length = $("[name=bagCheck]").length-1;
	
	// 체크박스로 불러왔을 때.
	if(price == count) price = Number.parseInt($("#price"+count).text())/$("#orderQuantity"+count).val();
	
	let totPrice = 0;
	
	$("#price"+count).html(price*$("#orderQuantity"+count).val()+"원");
	
	for(let i=1; i<=length; i++) {
		if(document.getElementById("bagCheck"+i).checked == true) {
			totPrice += Number.parseInt($("#price"+i).text());
		}
	}
	$("#totPrice").html(totPrice+"원");
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