$(window).scroll(function() {
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function() {
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

let str = "";
let str2 = "";

$(() => {
	// 아이디 찾기.
	$("#idFind").on("click", function() {
		// 이전 modal 내용 초기화.
		$("#modal-body").html("");
		$("#modal-body2").html("");
		$("#modal-title").html("아이디 찾기");
		// 이전 str 내용 초기화.
		str = '';
		str += '<div class="input-group mb-3">';
		str += '<input type="text" id="email" placeholder="가입시 입력한 이메일을 입력해주세요." class="form-control" />';
		str += '<input type="button" value="아이디찾기" onclick="memberIdFind()" class="btn btn-success" />';
		str += '</div>';
		$("#modal-body").html(str);
		$('#myModal').modal('show');
	});
	// 비밀번호 찾기.
	$("#pwdFind").on("click", function() {
		// 이전 modal 내용 초기화.
		$("#modal-body").html("");
		$("#modal-body2").html("");
		$("#modal-title").html("비밀번호 찾기");
		// 이전 str 내용 초기화.
		str = '';
		str += '<div class="input-group mb-3">';
		str += '<input type="text" id="searchMid" placeholder="아이디를 입력해주세요." class="form-control" />';
		str += '<input type="button" value="아이디찾기" onclick="MemberIdCheck()" class="btn btn-success" />';
		str += '</div>';
		$("#modal-body").html(str);
		$('#myModal').modal('show');
	});
});

const regEmail = /^[a-zA-Z0-9-_]+@[a-zA-Z.]+\.[a-zA-Z]{2,}$/;
// 아이디 찾기.
function memberIdFind() {
	let email = $("#email").val().replace(" ","");
	if(email == "") {
		alert("이메일을 입력해주세요.");
		$("#email").focus();
		return false;
	}
	else if(!regEmail.test(email)) {
		alert("이메일을 아이디@메일주소.도메인으로 입력해주세요.");
		$("#email").focus();
		return false;
	}
	
	$.ajax({
		url : "MemberIdFind",
		type: "post",
		data: {"email" : email},
		success : (res) => {
			if(res.length > 0) {
				//이전 str2의 내용 초기화.
				str2 = "";
				str2 += '<div class="text-center">';
				// 아이디 길이에 따라 th의 위치가 달라지는 것 방지.
				str2 += '<table class="table table-hover" style="table-layout:fixed">';
				str2 += '<tr class="table-secondary">';
				str2 += '<th>아이디</th>';
				str2 += '<th>닉네임</th>';
				str2 += '</tr>';
				for(let vo of res) {
					str2 += '<tr>';
					str2 += '<td>'+vo.mid+'</td>';
					str2 += '<td>'+vo.nickName+'</td>';
					str2 += '</tr>';
				}
				str2 += '</table>';
				str2 += '</div>';
				$("#modal-body2").html(str2);
			}
			else alert("가입하신 아이디가 존재하지 않습니다.");
		},
		error : () => alert("전송오류")
	});
}

// 비밀번호 찾기.
function MemberIdCheck() {
	let mid = $("#searchMid").val().replace(" ","");
	if(mid == "") {
		alert("아이디를 입력해주세요.");
		$("#searchMid").focus();
		return false;
	}
	
	$.ajax({
		url : "MemberIdCheck",
		type: "post",
		data: {"mid" : mid},
		success : (res) => {
			// 검색 결과 존재하는 아이디일 때.
			if(res != "") {
				// 이전 str 내용 초기화.
				str = '';
				str += '<div class="input-group mb-3">';
				str += '<input type="text" value="'+res.mid+'" readonly class="form-control" />';
				str += '</div>';
				str += '';
				$("#modal-body").html(str);
				// 이전 str2 내용 초기화.
				str2 = '';
				str2 += '<div class="input-group mb-3">';
				str2 += '<input type="text" id="email" placeholder="임시 비밀번호를 받을 이메일을 입력해주세요." class="form-control" />';
				str2 += '<input type="button" value="임시비밀번호발급" onclick="tempPwd(\''+mid+'\')" class="btn btn-success"></a>';
				str2 += '</div>';
				$("#modal-body2").html(str2);
			}
			else {
				alert("없는 아이디입니다.");
				$("#searchMid").val("");
				$("#searchMid").focus();
				return false;
			}
		},
		error : () => alert("전송오류")
	});
}
// 임시비밀번호 발급.
function tempPwd(mid) {
	let email = $("#email").val().replace(" ","");
	if(email == "") {
		alert("이메일을 입력해주세요.");
		$("#email").focus();
		return false;
	}
	spinStr = "<div class='text-center'><div class='spinner-border text-muted me-3'></div>";
	spinStr += "메일 발송중입니다. 잠시만 기다려주세요</div>";
	$("#modal-body3").html(spinStr);
	
	$.ajax({
		url : "MemberTempPwd",
		type: "post",
		data: {
			"mid" : mid,
			"email" : email
		},
		success : (res) => {
			if(res == 1) {
				// spin 삭제.
				$("#modal-body2").html("");
				alert("임시 비밀번호가 발송되었습니다.");
				location.reload();
			}
			else {
				alert("임시 비밀번호 발급에 실패했습니다.\n다시 시도해주세요.");
				return false;
			}
		},
		error : () => alert("전송오류")
	});
}