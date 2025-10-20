<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%pageContext.setAttribute("CRLF","\r\n");%>
<%pageContext.setAttribute("LF","\n");%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title>Member List</title>
	<script>
		'use strict';
		
		// 등급별로 보기.
		function levelPageCheck() {
			let level = $("#levelPage").val();
			location.href = "${ctp}/admin/member/MemberList?level="+level;
		}
		// 한 페이지 최대 수 변경.
		function viewPageCheck() {
			let pageSize = $("#viewPageCnt").val();
			location.href = "${ctp}/admin/member/MemberList?pageSize="+pageSize;
		}
		
		// 회원 등급변경.
		function levelChange(e) {
			let ans = confirm("선택한 회원의 등급을 변경하시겠습니까?");
			if(!ans) {
				location.reload();
				return false;
			}
			
			let items = e.value.split("/");
			let query = {
					"idx" : items[1],
					"level" : items[0]
			};
			
			$.ajax({
				url : "${ctp}/admin/member/MemberLevelChange",
				type: "POST",
				data: query,
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
		
		// 전체선택.
		function allCheck() {
			let length = 1;
			if(myform.idxFlag.length > 2) length = myform.idxFlag.length;
			if(length > 1) {
				for(let i=0; i<length; i++) {
					if(!myform.idxFlag[i].disabled) myform.idxFlag[i].checked = true;
				}
			}
			else if(!myform.idxFlag.disabled) myform.idxFlag.checked = true;
		}
		// 전체해제.
		function allReset() {
			let length = 1;
			if(myform.idxFlag.length > 2) length = myform.idxFlag.length;
			if(length > 1) {
				for(let i=0; i<length; i++) {
					if(!myform.idxFlag[i].disabled) myform.idxFlag[i].checked = false;
				}
			}
			else if(!myform.idxFlag.disabled) myform.idxFlag.checked = false;
		}
		// 선택반전.
		function reverseCheck() {
			let length = 1;
			if(myform.idxFlag.length > 2) length = myform.idxFlag.length;
			if(length > 1) {
				for(let i=0; i<length; i++) {
					if(!myform.idxFlag[i].disabled) myform.idxFlag[i].checked = !myform.idxFlag[i].checked;
				}
			}
			else if(!myform.idxFlag.disabled) myform.idxFlag.checked = !myform.idxFlag.checked;
		}
		// 선택 등급 변경.
		function levelSelectCheck() {
			let ans = confirm("선택한 회원의 등급을 변경하시겠습니까?");
			if(!ans) {
				location.reload();
				return false;
			}
			let select = document.getElementById("levelSelect");
			let levelSelectText = select.options[select.selectedIndex].text;
			let levelSelect = select.options[select.selectedIndex].value;
			
			let idxCheckedArray = "";
			
			for(let i=0; i<myform.idxFlag.length; i++) {
				if(myform.idxFlag[i].checked) idxCheckedArray += myform.idxFlag[i].value + "/";
			}
			
			if(idxCheckedArray == "") {
				alert("등급을 변경할 회원을 선택해주세요.");
				return false;
			}
			
			idxCheckedArray = idxCheckedArray.substring(0, idxCheckedArray.lastIndexOf("/"));
			let query = {
					"idxCheckedArray" : idxCheckedArray,
					"levelSelect" : levelSelect
			}
			
			$.ajax({
				url : "${ctp}/admin/member/MemberLevelSelectChange",
				type: "POST",
				data: query,
				success : (res) => {
					if(res != 0) {
						alert("선택한 회원의 등급이 "+levelSelectText+"으로 변경되었습니다.");
						location.reload();
					}
					else alert("회원 등급 변경에 실패했습니다.");
				},
				error : () => alert("전송오류")
			});
		}
		
		// 회원삭제.
		function memberDelete(idx) {
			let ans = confirm("선택한 회원을 삭제하시겠습니까?");
			if(ans) {
				$.ajax({
					url : "${ctp}/admin/member/MemberDelete",
					type: "POST",
					data: {"idx" : idx},
					success : (res) => {
						if(res != 0) {
							alert("회원이 삭제되었습니다.");
							location.reload();
						}
						else alert("회원 삭제에 실패했습니다.");
					},
					error : () => alert("전송오류")
				});
			}
		}
		
		// 회원 상세정보 modal로 표시.
		function modal(idx,mid,nickName,email,gender,birthday,tel,address,homePage,job,hobby,photo,
				content,userInfor,userDel,point,level,visitCnt,todayCnt,startDate,lastDate) {
			let strLevel = "";
			if(level == 0) strLevel = "관리자";
			else if(level == 1) strLevel = "우수회원";
			else if(level == 2) strLevel = "정회원";
			else if(level == 3) strLevel = "준회원";
			else if(level == 999) strLevel = "탈퇴대기회원";
			let str = "";
			str += '<table class="table table-hover">';
			str += '<tr><th>회원번호</th><td>'+idx+'</td></tr>';
			str += '<tr><th>아이디</th><td>'+mid+'</td></tr>';
			str += '<tr><th>닉네임</th><td>'+nickName+'</td></tr>';
			str += '<tr><th>이메일</th><td>'+email+'</td></tr>';
			str += '<tr><th>성별</th><td>'+gender+'</td></tr>';
			str += '<tr><th>생일</th><td>'+birthday.substring(0,10)+'</td></tr>';
			str += '<tr><th>전화번호</th><td>'+tel+'</td></tr>';
			str += '<tr><th>주소</th><td>'+address+'</td></tr>';
			str += '<tr><th>홈페이지</th><td>'+homePage+'</td></tr>';
			str += '<tr><th>직업</th><td>'+job+'</td></tr>';
			str += '<tr><th>취미</th><td>'+hobby+'</td></tr>';
			str += '<tr><th>프로필사진</th><td><img src="${ctp}/member/'+photo+'" width="150px" /></td></tr>';
			str += '<tr><th>자기소개</th><td>'+content+'</td></tr>';
			str += '<tr><th>정보공개여부</th><td>'+userInfor+'</td></tr>';
			str += '<tr><th>회원탈퇴여부</th><td>'+userDel+'</td></tr>';
			str += '<tr><th>포인트</th><td>'+point+'</td></tr>';
			str += '<tr><th>등급</th><td>'+strLevel+'</td></tr>';
			str += '<tr><th>총방문횟수</th><td>'+visitCnt+'</td></tr>';
			str += '<tr><th>오늘방문횟수</th><td>'+todayCnt+'</td></tr>';
			str += '<tr><th>가입일</th><td>'+startDate+'</td></tr>';
			str += '<tr><th>마지막방문일</th><td>'+lastDate+'</td></tr>';
			str += '</table>';
			$("#modal-body").html(str);
		}
	</script>
</head>
<body>
	<div class="text-center">
		<h2>회원 리스트</h2>
		<div class="row mb-2">
			<div class="col">
				<span class="d-flex ms-2" style="flex-grow: 1; justify-content: flex-start;">등급별로 보기:&nbsp;&nbsp;
					<select name="levelPage" id="levelPage" onchange="levelPageCheck()">
						<option value="100" ${level==100?'selected':''}>전체회원</option>
						<option value="0" ${level==0?'selected':''}>관리자</option>
						<option value="1" ${level==1?'selected':''}>우수회원</option>
						<option value="2" ${level==2?'selected':''}>정회원</option>
						<option value="3" ${level==3?'selected':''}>준회원</option>
						<option value="999" ${level==999?'selected':''}>탈퇴대기회원</option>
					</select>
				</span>
			</div>
			<div class="col">
				<span class="d-flex me-2" style="flex-grow: 1; justify-content: flex-end;">한 페이지에 최대:&nbsp;&nbsp;
					<select name="viewPageCnt" id="viewPageCnt" onchange="viewPageCheck()">
						<option value="5" ${pageSize==5 ? 'selected' : ''}>5개씩 보기</option>
						<option value="10"<c:if test="${pageSize == 10}">selected</c:if>>10개씩 보기</option>
						<option value="15"<c:if test="${pageSize == 15}">selected</c:if>>15개씩 보기</option>
						<option value="20"<c:if test="${pageSize == 20}">selected</c:if>>20개씩 보기</option>
						<option value="30"<c:if test="${pageSize == 30}">selected</c:if>>30개씩 보기</option>
					</select>
				</span>
			</div>
		</div>
		<div class="row mb-2">
			<div class="col">
				<span class="d-flex ms-2" style="flex-grow: 1; justify-content: flex-start;">
					<input type="button" value="전체선택" onclick="allCheck()" class="btn btn-success btn-sm me-1"/>
					<input type="button" value="전체취소" onclick="allReset()" class="btn btn-primary btn-sm me-1"/>
					<input type="button" value="선택반전" onclick="reverseCheck()" class="btn btn-info btn-sm me-1"/>
					<select name="levelSelect" id="levelSelect" class="form-select" style="flex: 0 0 200px;">
						<option value="1">우수회원</option>
						<option value="2" selected>정회원</option>
						<option value="3">준회원</option>
					</select>
					<input type="button" value="등급변경" onclick="levelSelectCheck()" class="btn btn-success btn-sm" />
				</span>
			</div>
		</div>
		<hr/>
		<form name="myform">
			<table class="table table-hover">
				<tr class="table-secondary">
					<th>번호</th>
					<th>아이디</th>
					<th>닉네임</th>
					<th>성명</th>
					<th>생일</th>
					<th>성별</th>
					<th>최종방문일</th>
					<th>오늘방문횟수</th>
					<th>활동여부</th>
					<th>회원등급</th>
				</tr>
				<c:forEach var="vo" items="${vos}" varStatus="st">
					<tr>
						<td>
							<input type="checkbox" name="idxFlag" id="idxFlag${vo.idx}" value="${vo.idx}" ${vo.level==0?'disabled':''} />
							${st.count}
						</td>
						<td>
							<!-- CRLF, LF 모두 바꿔야 해결할 수 있다. -->
							<c:set var="content" value="${fn:replace(fn:replace(vo.content,CRLF,'<br/>'),LF,'<br/>')}" />
							<a href="" onclick="modal('${vo.idx}','${vo.mid}','${vo.nickName}','${vo.email}','${vo.gender}','${vo.birthday}','${vo.tel}',
								'${vo.address}','${vo.homePage}','${vo.job}','${vo.hobby}','${vo.photo}','${content}','${vo.userInfor}','${vo.userDel}',
								'${vo.point}','${vo.level}','${vo.visitCnt}','${vo.todayCnt}','${vo.startDate}','${vo.lastDate}')" 
								data-bs-toggle="modal" data-bs-target="#myModal" class="link-primary">${vo.mid}</a>
						</td>
						<td>${vo.nickName}</td>
						<td>${vo.name}</td>
						<td>${fn:substring(vo.birthday,0,10)}</td>
						<td>${vo.gender}</td>
						<td>${fn:substring(vo.lastDate,0,16)}</td>
						<td>${vo.todayCnt}</td>
						<td>
							<c:if test="${vo.userDel == 'NO'}">활동중</c:if>
							<c:if test="${vo.userDel == 'OK'}">탈퇴대기중
								<c:if test="${vo.cancelDate >= 30}">
									<font color="red"><br/>(<a href="javascript:memberDelete('${vo.idx}')">${vo.cancelDate}</a>일)</font>
								</c:if>
								<c:if test="${vo.cancelDate < 30}"><br/>(${vo.cancelDate}일)</c:if>
							</c:if>
						</td>
						<td>
							<select name="level" id="level" onchange="levelChange(this)">
								<option value="0/${vo.idx}" ${vo.level==0?'selected':''}>관리자</option>
								<option value="1/${vo.idx}" ${vo.level==1?'selected':''}>우수회원</option>
								<option value="2/${vo.idx}" ${vo.level==2?'selected':''}>정회원</option>
								<option value="3/${vo.idx}" ${vo.level==3?'selected':''}>준회원</option>
								<option value="999/${vo.idx}" ${vo.level==999?'selected':''}>탈퇴신청</option>
							</select>
						</td>
					</tr>
					<c:set var="curScrStartNo" value="${curScrStartNo - 1}" />
				</c:forEach>
			</table>
		</form>
		<p><br/></p>
	</div>
	<!-- 블록페이지 시작 -->
	<div class="input-group justify-content-center">
		<div class="pagination">
			<!-- pag와 pageSize를 BoardList에 보내준다. -->
			<c:if test="${pVO.pag > 1}"><a href="${ctp}/admin/member/MemberList?pag=1&pageSize=${pVO.pageSize}" 
				class="page-item page-link text-dark">첫 페이지</a></c:if>
				<c:if test="${pVO.curBlock > 0}">
					<a href="${ctp}/admin/member/MemberList?pag=${(pVO.curBlock - 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" 
						class="page-item page-link text-dark">이전 블록</a>
				</c:if>
				<c:forEach var="i" begin="${(pVO.curBlock * pVO.blockSize) + 1}" end="${(pVO.curBlock * pVO.blockSize) + pVO.blockSize}" varStatus="st">
					<c:if test="${i <= pVO.totPage && i == pVO.pag}">
						<span class="page-item active page-link bg-secondary border-secondary">${i}</span>
					</c:if>
					<c:if test="${i <= pVO.totPage && i != pVO.pag}">
						<a href="${ctp}/admin/member/MemberList?pag=${i}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">${i}</a>
					</c:if>
				</c:forEach>
				<c:if test="${pVO.curBlock < pVO.lastBlock}">
					<a href="${ctp}/admin/member/MemberList?pag=${(pVO.curBlock + 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" 
						class="page-item page-link text-dark">다음 블록</a>
				</c:if>
					<c:if test="${pVO.pag < pVO.totPage}">
				<a href="${ctp}/admin/member/MemberList?pag=${pVO.totPage}&pageSize=${pVO.pageSize}&pageSize=${pVO.pageSize}" 
					class="page-item page-link text-dark">마지막 페이지</a>
			</c:if>
		</div>
	</div>
	<p></p>
	<!-- 블록페이지 끝 -->
	<!-- The Modal -->
	<div class="modal fade" id="myModal">
		<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 id="modal-title" class="modal-title">회원 상세정보</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<!-- Modal body -->
				<div class="modal-body">
					<div id="modal-body"></div>
					<div id="modal-body2"></div>
				</div>
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>