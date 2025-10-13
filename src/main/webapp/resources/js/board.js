$(window).scroll(function(){
	if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
	else $("#topBtn").removeClass("on");
	
	$("#topBtn").click(function(){
		window.scrollTo({top:0, behavior: "smooth"});
	});
});

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
function fCheck(mid, fVO) {
	let nickName = $("#nickName").val().replace(" ","");
	let title = $("#title").val().trim();
	let content = CKEDITOR.instances.CKEDITOR.getData().trim();
	let openSW = $("[name='openSW']").val();
	
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
	else if(openSW != "공개") {
		let pwd = $("#pwd").val().replace(" ","");
		if(pwd.length > 64) {
			alert("비밀번호가 너무 깁니다.");
			return false;
		}
		else if(pwd == "") {
			alert("비밀번호를 입력해주세요.");
			return false;
		}
	}
	else submitFlag = 1;
	
	let files = $("#file")[0].files;
	let fName = "";
	let maxSize = 1024 * 1024 * 30;
	let fileSize = 0;
	let ext = "";
	
	if(fVO != null && files.length == 0) {
		let ans = confirm("첨부파일이 존재하던 글입니다.\n첨부파일을 업로드하지 않고 수정하시겠습니까?");
		if(!ans) return false;
	}
	
	for(let i=0; i<files.length; i++) {
		fName = files[i].name;
		ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
		if(ext != 'jpg' && ext != 'gif' && ext != 'png' && ext != 'mp4' &&
			ext != 'zip' && ext != 'hwp' && ext != 'doc' && ext != 'ppt' && ext != 'pptx' && ext != 'pdf' && ext != 'txt') {
			alert("업로드 가능한 파일은 'jpg/gif/png/mp4/zip/hwp/doc/ppt/pptx/pdf/txt'파일 입니다.");
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

// 좋아요 처리.
function goodCheckPlus(idx) {
	$.ajax({
		url : "GoodCheckPlusMinus",
		type : "post",
		data : {
			"idx" : idx,
			"goodCnt" : 1
		},
		success : (res) => {
			if(res != 0) location.reload();
		},
		error : () => alert("전송오류")
	});
}
function goodCheckMinus(idx) {
	$.ajax({
		url : "GoodCheckPlusMinus",
		type : "post",
		data : {
			"idx" : idx,
			"goodCnt" : -1
		},
		success : (res) => {
			if(res != 0) location.reload();
		},
		error : () => alert("전송오류")
	});
}

// 게시글 삭제.
function deleteCheck(idx) {
	let ans = confirm("게시글을 삭제하시겠습니까?");
	if(ans) location.href = "BoardDelete?idx="+idx;
}

// 댓글 입력.
function replyCheck(ctp, parentIdx, mid, nickName, hostIP) {
	let content = $("#content").val();
	if(content.trim() == "") {
		alert("내용을 입력해주세요.");
		return false;
	}
	
	let query = {
		"parentIdx" : parentIdx,
		"part" : "board",
		"mid" : mid,
		"nickName" : nickName,
		"content" : content,
		"hostIP" : hostIP
	};
	
	$.ajax ({
		url : ctp+"/ReplyInput",
		type : "post",
		data : query,
		success : (res) => {
			if(res != 0) {
				alert("댓글이 입력되었습니다.");
				location.reload();
			}
			else alert("댓글이 입력되지 않았습니다.");
		},
		error : () => alert("전송오류")
	});
}
// 댓글 삭제.
function replyDelete(ctp, idx) {
	let ans = confirm("댓글을 삭제하시겠습니까?");
	if(ans) {
		$.ajax ({
			url : ctp+"/ReplyDelete",
			type : "post",
			data : {"idx" : idx},
			success : (res) => {
				if(res != 0) {
					alert("댓글이 삭제되었습니다.");
					location.reload();
				}
				else alert("댓글이 삭제되지 않았습니다.");
			},
			error : () => alert("전송오류")
		});
	}
}
// 댓글 수정.
function replyUpdate(ctp, idx, content) {
	$("[id^=demo]").html("");
	
	let str = "";
	str += '<td colspan="8" id="replyContent">';
	str += '<table class="table">';
	str += '<tr>';
	str += '<td colspan="4">';
	str += '<textarea rows="4" name="content'+idx+'" id="content'+idx+'" class="form-control">'+content+'</textarea>';
	str += '</td>';
	str += '<tr>';
	str += '<td colspan="2">';
	str += '<span>작성자: ${sNickName}</span>';
	str += '</td>';
	str += '<td colspan="2" class="text-end">';
	str += '<span><input type="button" value="댓글수정" id="replyUpdateOk" class="btn btn-info btn-sm me-1" /></span>';
	str += '<span><input type="button" value="닫기" onclick="replyClose('+idx+')" class="btn btn-warning btn-sm" /></span>';
	str += '</td>';
	str += '</tr>';
	str += '</table>';
	str += '</td>';
	$("#demo"+idx).html(str);
	
	$(() => {
		$("#replyUpdateOk").on("click", () => {
			let query = {
				"idx" : idx,
				"content" : $("#content"+idx).val(),
			};
			$.ajax({
				url : ctp+"/ReplyUpdate",
				type : "post",
				data : query,
				success : (res) => {
					if(res != 0) {
						alert("댓글이 수정되었습니다.");
						location.reload();
					}
					else alert("댓글 수정에 실패했습니다.");
				},
				error : () => alert("전송오류")
			});
		});
	});
}
// 댓글 수정창 닫기.
function replyClose(idx) {
	$("#demo"+idx).html("");
}

// 대댓글 창 열기.
function reReplyForm(ctp, replyIdx, parentIdx, mid, nickName, hostIP) {
	console.log(mid);
	$("[id^=demo]").html("");
	
	let str = "";
	str += '<td colspan="8" id="replyContent">';
	str += '<table class="table">';
	str += '<tr>';
	str += '<td colspan="4">';
	str += '<textarea rows="4" name="content'+replyIdx+'" id="content'+replyIdx+'" class="form-control"></textarea>';
	str += '</td>';
	str += '<tr>';
	str += '<td colspan="2">';
	if(nickName == '') str += '<span>작성자: '+hostIP+'</span>';
	if(nickName != '') str += '<span>작성자: '+nickName+'</span>';
	str += '</td>';
	str += '<td colspan="2" class="text-end">';
	str += '<span><input type="button" value="대댓글달기" id="reReplyInput" class="btn btn-info btn-sm me-1" /></span>';
	str += '<span><input type="button" value="닫기" onclick="replyClose('+replyIdx+')" class="btn btn-warning btn-sm" /></span>';
	str += '</td>';
	str += '</tr>';
	str += '</table>';
	str += '</td>';
	$("#demo"+replyIdx).html(str);
	
	// 대댓글 등록.
	$(() => {
		$("#reReplyInput").on("click", () => {
			let query = {
				"replyIdx" : replyIdx,
				"parentIdx" : parentIdx,
				"mid" : mid,
				"nickName" : nickName,
				"hostIP" : hostIP,
				"content" : $("#content"+replyIdx).val(),
				"flag" : "reReply"
			};
			if($("#content"+replyIdx).val().trim() == "") {
				alert("댓글 내용을 입력해주세요.");
				$("#content"+replyIdx).focus();
				return false;
			}
			
			$.ajax({
				url : ctp+"/ReplyInput",
				type : "post",
				data : query,
				success : (res) => {
					if(res != 0) {
						alert("대댓글이 입력되었습니다.");
						location.reload();
					}
					else alert("대댓글 입력에 실패했습니다.");
				},
				error : () => alert("전송오류")
			});
		});
	});
}

// modal 창에서 신고 시, 기타 항목을 선택했을 때 textarea 보여주기.
function etcShow() {
	$("#etcTxt").show();
}
$(() => {
	$("[id^=complaint]").on("change", () => {
		$("#etcTxt").hide();
	});
});

// 게시글 신고 처리.
function complaintCheck(idx, mid) {
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
	
	let query = {
			"part" : "board",
			"partIdx" : idx,
			"cpMid" : mid,
			"cpContent" : cpContent
	}
	
	$.ajax({
		url : "BoardComplaint",
		type: "post",
		data: query,
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

// modal에 hidden값 세팅
function setModalHidden(idx, pag, pageSize, search, searchStr) {
	$("#idx").val(idx);
	$("#pag").val(pag);
	$("#pageSize").val(pageSize);
	modalForm.search.value = search;
	modalForm.searchStr.value = searchStr;
}