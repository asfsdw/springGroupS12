// 폴더이동.
function selectFolder(extName) {
	location.href="FileManagement?part="+extName;
}

// 전체선택.
function allCheck() {
	for(let i=0; i<document.getElementsByName("fileCheck").length; i++) {
		document.getElementsByName("fileCheck")[i].checked = true;
	}
}
// 전체해제.
function allReset() {
	for(let i=0; i<document.getElementsByName("fileCheck").length; i++) {
		document.getElementsByName("fileCheck")[i].checked = false;
	}
}
// 선택삭제.
function fileDelete(part, file) {
	console.log(part);
	console.log(file);
	// 파일 선택했는지.
	if(file == "") {
		swal.fire("삭제할 파일을 선택해주세요.","","info");
		return false;
	}
	
	swal.fire({
		title: "선택한 파일을 삭제하시겠습니까?",
		icon : "warning",
		showCancelButton: true
	// 사용자의 응답을 기다리기 위해 필요함.
	}).then((res) => {
		// OK를 클릭하면.
		if(res.isConfirmed) {
			let cnt = 0;
			let index = [];
			let fName = "";
			
			// 선택삭제(체크박스 체크)를 했을 경우.
			if(document.getElementsByName("fileCheck")[0].checked != false) {
				for(let i=0; i<document.getElementsByName("fileCheck").length; i++) {
					// 체크한 파일의 이름(value(file))을 fName에 저장.
					if(document.getElementsByName("fileCheck")[i].checked) fName += document.getElementsByName("fileCheck")[i].value+"/";
				}
				// 마지막 / 제거.
				fName = fName.substring(0, fName.length-1);
			}
			
			// 폴더(분류), 삭제버튼(단일파일이름), 선택삭제(복수파일이름).
			let qurey = {
					"part" : part,
					"fileName" : file,
					"fNames" : fName
			}
			$.ajax({
				url : "${ctp}/admin/folder/FileManagement",
				type: "post",
				data: qurey,
				success : (res) => {
					if(res != 0) {
						swal.fire({
							title: "파일이 삭제되었습니다.",
							icon : "success"
						}).then(() => location.reload());
					}
					else swal.fire("파일 삭제에 실패했습니다.","","error");
				},
				error : () => alert("전송오류")
			});
		}
	});
}