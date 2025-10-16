$(window).scroll(function(){
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function(){
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

// 정규식을 이용한 유효성검사처리.
const regMid = /^[a-zA-Z0-9_]{4,20}$/;	// 아이디는 4~20의 영문 대/소문자와 숫자와 밑줄 가능
const regNickName = /^[a-zA-Z가-힣0-9_]{2,20}$/;	// 닉네임은 영문, 한글, 숫자, 밑줄만 가능
const regName = /^[a-zA-Z가-힣]+$/;	// 이름은 한글/영문 가능
const regEmail = /^[a-zA-Z0-9-_]+@[a-zA-Z.]+\.[a-zA-Z]{2,}$/; //이메일 형식 맞춰야함.
const regURL = /^(https?:\/\/)?([a-z\d\.-]+)\.([a-z\.]{2,6})([\/\w\.-]*)*\/?$/;

let idCheckSW = 0;
let nickNameCheckSW = 0;

// 아이디 중복체크.
function idCheck() {
	let mid = $("#mid").val().replace(" ","");
	if(mid== "") {
		alert("아이디를 입력해주세요.");
		$("#mid").focus();
		return false;
	}
	else if(!regMid.test(mid)) {
		alert("아이디는 4~20자리의 영문, 숫자, 언더바(_)만 사용가능합니다.");
		$("#mid").focus();
		return false;
	}
	
	$.ajax({
		url : "SignUpIdCheck",
		type: "post",
		data: {"mid" : mid},
		success : (res) => {
			if(res != 0) {
				alert("이미 있는 아이디입니다.");
				$("#mid").val("");
				$("#mid").focus();
				return false;
			}
			else {
				alert("사용 가능한 아이디입니다.");
				myform.mid.readOnly=true;
				$("#pwd").focus();
				idCheckSW = 1;
				return false;
			}
		},
		error :() => alert("전송오류")
	});
}
// 닉네임 중복체크.
function nickNameCheck() {
	let nickName = $("#nickName").val().replace(" ","");;
	if(nickName == "") {
		alert("닉네임을 입력해주세요.");
		$("#nickName").focus();
		return false;
	}
	else if(!regNickName.test(nickName)) {
		alert("닉네임은 2~20자리의 영문, 한글, 숫자, 언더바(_)만 사용가능합니다.");
		$("#nickName").focus();
		return false;
	}
	
	$.ajax({
		url : "SignUpNickNameCheck",
		type: "post",
		data: {"nickName" : nickName},
		success : (res) => {
			if(res != 0) {
				alert("이미 있는 닉네임입니다.");
				$("#nickName").val("");
				$("#nickName").focus();
				return false;
			}
			else {
				alert("사용 가능한 닉네임입니다.");
				myform.nickName.readOnly=true;
				$("#name").focus();
				nickNameCheckSW = 1;
				return false;
			}
		},
		error :() => alert("전송오류")
	});
}
// 중복체크 버튼 클릭했는지 확인.
$(() => {
	$("#midBtn").on("blur", () => idCheckSW = 1);
	$("#nickNameBtn").on("blur", () => nickNameCheckSW = 1);
});

// 이메일 중복체크.
function emailCheck() {
	let email = $("#email1").val().replace(" ","")+"@"+$("#email2").val();
	if($("#email1").val().replace(" ","") == "") {
		alert("이메일을 입력해주세요.");
		$("#email1").focus();
		return false;
	}
	
	$.ajax({
		url : "MemberEmailCheck",
		type: "post",
		data: {"email" : email},
		success : (res) => {
			if(res == 0) {
				alert("사용할 수 있는 이메일입니다.");
				$("#emailCheckBtn").hide();
				$("#certificationBtn").show();
			}
			else {
				alert("중복된 이메일입니다.");
				$("#email1").focus();
				return false;
			}
		},
		error : () => alert("전송오류")
	});
}
// 이메일 인증.
function emailCertification(year) {
	// 아이디, 닉네임 충복체크했는지 확인.
	if(idCheckSW == 0) {
		alert("아이디 중복체크버튼을 눌러주세요.");
		$("#midBtn").focus();
		return false;
	}
	else if(nickNameCheckSW == 0) {
		alert("닉네임 중복체크버튼을 눌러주세요.");
		$("#nickNameBtn").focus();
		return false;
	}
	// 메일 인증 전에 정보를 입력했는지 확인.
	let pwd = $("#pwd").val().replace(" ","");
	let name = $("#name").val().replace(" ","");
	let ageYear = $("#birthday").val().replace(" ","").split("-");
	let age = year-ageYear[0];
	let email1 = $("#email1").val().replace(" ","");
	let email2 = $("#email2").val();
	let email = email1+"@"+email2;
	
	//입력한 정보의 정규식 체크.
	if(pwd.length < 4 || pwd.length > 20) {
		alert("비밀번호는 4~20 자리로 작성해주세요.");
		$("#pwd").focus();
		return false;
	}
	else if(!regName.test(name)) {
		alert("성명은 한글과 영문대소문자만 사용가능합니다.");
		$("#name").focus();
		return false;
	}
	else if(age < 20) {
		alert("미성년자는 가입하실 수 없습니다.");
		$("#birthday").focus();
		return false;
	}
	else if(!regEmail.test(email)) {
		alert("이메일 주소를 확인해주세요.");
		$("#email1").focus();
		return false;
	}
	
	// 인증번호를 메일로 전송하는 동안 사용자 폼에는 스피너가 출력되도록 처리.
	let spin = "<div class='text-center'><div class='spinner-border text-muted me-3'></div>";
	spin += "메일 발송중입니다. 잠시만 기다려주세요</div>";
	$("#demoSpin").html(spin);
	spin = "";
	
	// ajax로 인증번호 발송.
	$.ajax({
		url : "SignUpEmailCheck",
		type: "post",
		data: {"email" : email},
		success : (res) => {
			if(res == 1) {
				alert("인증번호가 발송되었습니다.\n메일 확인 후, 인증번호를 입력해주세요.");
				let str = '<div class="input-group mb-3">';
				str += '<input type="text" name="checkKey" id="checkKey" class="form-control" />';
				str += '<span id="accessTime" class="input-group-text">남은 시간: 120초</span>';
				str += '<input type="button" value="인증번호확인" onclick="emailCertificationOk()" class="btn btn-primary" />';
				str += '</div>';
				$("#demoSpin").html(str);
				timer();
			}
			else alert("인증번호를 다시 받아주세요.");
		},
	error : () => alert("전송오류")
	});
}
// 제한시간 처리.
// 120초.
let accessTime = 119;
let interval;
function timer() {
	interval = setInterval(() => {
	$("#accessTime").html("남은 시간: "+accessTime+"초");
	
	if(accessTime == 0) {
		// 인증번호 확인용 div 초기화.
		$("#demoSpin").html("");
		// 0이된 120초 제한시간 다시 세팅.
		accessTime = 119;
		// 인증번호 세션삭제.
		$.ajax({
			url : "SignUpEmailCheckNo",
			type: "post",
			success : (res) => alert("인증시간이 만료되었습니다.\n인증번호를 다시 받아주세요."),
			error : () => alert("전송오류")
		});
		// 제한시간 처리 function 초기화.
		clearInterval(interval);
	}
	
	accessTime--;
	//1초
	}, 1000);
}
// 인증번호확인.
function emailCertificationOk() {
	let checkKey = $("#checkKey").val().replace(" ","");
	if(checkKey == "") {
		alert("인증번호를 입력해주세요.");
		$("#checkKey").focus();
		return false;
	}
	
	$.ajax({
		url : "SignUpEmailCheckOk",
		type: "post",
		data: {"checkKey" : checkKey},
		success : (res) => {
			if(res == 1) {
				clearInterval(interval);
				$("#demoSpin").hide();
				$("#addContent").show();
			}
			else alert("인증번호를 다시 받아주세요.");
		},
		error : () => alert("전송오류")
	});
}

// 전송전 마지막 체크.
function fCheck(year) {
	// 검사를 끝내고 필요한 내역들을 변수에 담아 회원가입처리한다.
	let pwd = $("#pwd").val().replace(" ","");
	let name = $("#name").val().replace(" ","");
	let ageYear = $("#birthday").val().replace(" ","").split("-");
	let age = year-ageYear[0];
	if($("#age").val() != '') age = $("#age").val();
	let email1 = $("#email1").val().replace(" ","");
	let email2 = $("#email2").val();
	let email = email1+"@"+email2;
	let postcode = myform.postcode.value + " ";
	let roadAddress = myform.roadAddress.value + " ";
	let detailAddress = myform.detailAddress.value + " ";
	let extraAddress = myform.extraAddress.value + " ";
	let address = postcode + "/" + roadAddress + "/" + detailAddress + "/" + extraAddress;
	let submitFlag = 0; // 체크완료를 위한 변수.
	
	// DB에 NOT NULL 처리한 것들과 DEFAULT값이 없는 것들만 프론트에서 체크한다(입력받지 않았고 NOT NULL이 아닌 것은 백에서 DEFAULT처리).
	// 아이디, 닉네임 중복체크 했는지 확인.
	if(pwd.length < 4 || pwd.length > 20) {
		alert("비밀번호는 4~20 자리로 작성해주세요.");
		$("#pwd").focus();
		return false;
	}
	else if(!regName.test(name)) {
		alert("성명은 한글과 영문대소문자만 사용가능합니다.");
		$("#name").focus();
		return false;
	}
	else if(age < 20) {
		alert("미성년자는 가입하실 수 없습니다.");
		$("#birthday").focus();
		return false;
	}
	else if(!regEmail.test(email)) {
		alert("이메일 주소를 확인해주세요.");
		$("#email1").focus();
		return false;
	}
	else submitFlag = 1;
	
	// 올린 파일이 이미지인지 확인.
	let fName = $("#file").val();
	let maxSize = 1024 * 1024 * 10;
	let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
	
	if(fName != "") {
		let fileSize = $("#file")[0].files[0].size;
		if(fileSize > maxSize) {
			alert("프로필 사진의 파일 크기는 10MB까지입니다.")
			return false;
		}
	}
	// 프로필 사진은 안 올려도 상관 없기 때문에 이미지가 맞는지만 체크. 업로드 하지 않을 경우 공백이기 때문에 공백도 허용.
	if(ext != "jpg" && ext != "jpeg" && ext != "gif" && ext != "png" && ext != "") {
		alert("프로필 사진입니다. 그림파일만 선택해주세요.");
		return false;
	}
	else submitFlag = 1;
	
	if(submitFlag != 1) {
		alert("체크하지 않은 항목이 있습니다.");
		return false;
	}
	else {
		$("#age").val(age);
		$("#email").val(email);
		$("#address").val(address);
		myform.submit();
	}
}

// 회원정보 수정 전 체크.
function updateCheck() {
	let name = $("#name").val().replace(" ","");
	let age = $("#age").val();
	let email1 = $("#email1").val().replace(" ","");
	let email2 = $("#email2").val();
	let email = email1+"@"+email2;
	let postcode = myform.postcode.value + " ";
	let roadAddress = myform.roadAddress.value + " ";
	let detailAddress = myform.detailAddress.value + " ";
	let extraAddress = myform.extraAddress.value + " ";
	let address = postcode + "/" + roadAddress + "/" + detailAddress + "/" + extraAddress;
	let submitFlag = 0; // 체크완료를 위한 변수.
	
	if(!regName.test(name)) {
		alert("성명은 한글과 영문대소문자만 사용가능합니다.");
		$("#name").focus();
		return false;
	}
	else if(age < 20) {
		alert("미성년자는 가입하실 수 없습니다.");
		$("#age").focus();
		return false;
	}
	else if(!regEmail.test(email)) {
		alert("이메일 주소를 확인해주세요.");
		$("#email1").focus();
		return false;
	}
	else submitFlag = 1;
	
	// 올린 파일이 이미지인지 확인.
	let fName = $("#file").val();
	let maxSize = 1024 * 1024 * 10;
	let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
	
	if(fName != "") {
		let fileSize = $("#file")[0].files[0].size;
		if(fileSize > maxSize) {
			alert("프로필 사진의 파일 크기는 10MB까지입니다.")
			return false;
		}
	}
	// 프로필 사진은 안 올려도 상관 없기 때문에 이미지가 맞는지만 체크. 업로드 하지 않을 경우 공백이기 때문에 공백도 허용.
	if(ext != "jpg" && ext != "jpeg" && ext != "gif" && ext != "png" && ext != "") {
		alert("프로필 사진입니다. 그림파일만 선택해주세요.");
		return false;
	}
	else submitFlag = 1;
	
	if(submitFlag != 1) {
		alert("체크하지 않은 항목이 있습니다.");
		return false;
	}
	else {
		$("#email").val(email);
		$("#address").val(address);
		myform.submit();
	}
}