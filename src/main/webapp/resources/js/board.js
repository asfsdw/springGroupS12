// 공개, 비공개 체크.
$(() => {
	$("#openNO").on("click", () => {
		$("#pwdDemo").show();
	});
	$("#openOK").on("click", () => {
		$("#pwdDemo").hide();
	});
});

// 게시글 등록 전, 프론트 체크.
function fCheck(mid) {
	let nickName = $("#nickName").val().replace(" ","");
	let title = $("#title").val().trim();
	let content = CKEDITOR.instances.CKEDITOR.getData().trim();
	let pwd = $("#pwd").val().replace(" ","");
	
	let submitFlag = 0;
	
	if(mid.replace(" ","") == "") {
		alert("글쓰기는 로그인 후, 사용 가능합니다.");
		location.href = "member/Login";
		return false;
	}
	else if(nickName == "") {
		alert("글쓰기는 로그인 후, 사용 가능합니다.");
		location.href = "member/Login";
		return false;
	}
	else if(title == "") {
		alert("제목을 입력해주세요.");
		return false;
	}
	else if(content == "") {
		alert("내용을 입력해주세요.");
		return false;
	}
	else if(pwd.length > 64) {
		alert("비밀번호가 너무 깁니다.");
		return false;
	}
	else submitFlag = 1;
	
	let files = $("#file")[0].files;
	let fName = "";
	let maxSize = 1024 * 1024 * 30;
	let fileSize = 0;
	let ext = "";
	
	for(let i=0; i<files.length; i++) {
		fName = files[i].name;
		ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
		if(ext != 'jpg' && ext != 'gif' && ext != 'png' && 
			ext != 'zip' && ext != 'hwp' && ext != 'doc' && ext != 'ppt' && ext != 'pptx' && ext != 'pdf' && ext != 'txt') {
			alert("업로드 가능한 파일은 'jpg/gif/png/zip/hwp/doc/ppt/pptx/pdf/txt'파일 입니다.");
			return false;
		}
		fileSize += files[i].size;
	}
	if(fileSize > maxSize) {
		alert("파일이 너무 큽니다.");
		return false;
	}
	
	if(submitFlag == 0) {
		alert("다시 시도해주세요.");
		return false;
	}
	else {
		$("#fName").val(fName);
		myform.submit();
	}
}