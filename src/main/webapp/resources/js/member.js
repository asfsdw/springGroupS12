$(window).scroll(function(){
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function(){
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

$(() => {
	$("#subContent").on("change", () => {
		if($("#subContent").val() == '등급업') {
			$("#demo1").show();
			$("#demo2").show();
		}
		if($("#subContent").val() != '등급업') {
			$("#demo1").hide();
			$("#demo2").hide();
		}
		if($("#subContent").val() == '기타') $("#demo2").show();
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
	else if($("#subContent").val() == '등급업') {
		if($("#levelUp").val().trim() == '선택') {
			alert("신청할 등급을 선택해주세요.");
			return false;
		}
		else if($("#subEtc").val().trim() == '') {
			alert("신청 이유를 적어주세요.");
			return false;
		}
	}
	else if($("#subContent").val() == '기타') {
		if($("#subEtc").val().trim() == '') {
			alert("신청 내용을 적어주세요.");
			return false;
		}
	}
	
	subForm.submit();
}

function pwdCheck(ctp) {
	let pwd = $("#pwd").val().trim();
	if(pwd == "") {
		alert("비밀번호를 입력해주세요.");
		$("#pwd").focus();
		return false;
	}
	
	$.ajax({
		url : "${ctp}/member/MemberPwdCheck",
		type: "post",
		data: {
			"mid" : "${sMid}",
			"pwd" : pwd
		},
		success : (res) => {
			if(res != "0") {
				if("${flag}" == "p") {
					$("#myform").hide();
					$("#yourform").show();
				}
				else {
					location.href = ctp+"/member/MemberUpdate?mid=${sMid}";
				}
			}
			else {
				alert("비밀번호가 틀렸습니다.\n비밀번호를 확인해주세요.");
				$("#pwd").focus();
				return false;
			}
		},
		error : () => alert("전송오류")
	});
}

// 비밀번호 변경.
function pwdChange(ctp) {
	let newPwd = $("#newPwd").val().trim();
	let rePwd = $("#rePwd").val().trim();
	if(newPwd == "" || rePwd == "") {
		alert("비밀번호를 입력해주세요.");
		if(newPwd == "") $("#newPwd").focus();
		else $("#rePwd").focus();
		return false;
	}
	else if(newPwd != rePwd) {
		alert("입력하신 비밀번호가 서로 다릅니다.");
		$("#rePwd").focus();
		return false;
	}
	else {
		yourform.action = ctp+"/member/MemberPwdChange";
		yourform.submit();
	}
}