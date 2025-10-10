$(() => {
	$("#subContent").on("change", () => {
		if($("#subContent").val() == '기타') $("#demo").show();
		if($("#subContent").val() != '기타') $("#demo").hide();
	});
});

function subCheck(mid) {
	if(mid.length > 20 || mid == 'noMember' || mid.replace(' ', '') == '') {
		alert("아이디에 문제가 있습니다.\n다시 로그인해주세요.");
		return false;
	}
	else $("#mid").val(mid);
	if($("#nickName").val().length > 20 || $("#nickName").val() == '비회원' || $("#nickName").val().replace(' ', '') == '') {
		alert("닉네임에 문제가 있습니다.\n다시 로그인해주세요.");
		return false;
	}
	if($("#subContent").val() == '선택') {
		alert("신청 내용을 선택해주세요.");
		return false;
	}
	else if($("#subContent").val() == '기타') {
		if($("#subEtc").val().trim() == '') {
			alert("신청 내용을 적어주세요.");
			return false;
		}
	}
	
	subForm.submit();
}