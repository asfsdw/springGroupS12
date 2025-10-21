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
	<script src="${ctp}/js/admin.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/admin.css" />
	<script>
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
				type: "post",
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
						<option value="1" ${level==1?'selected':''}>업자</option>
						<option value="2" ${level==2?'selected':''}>우수회원</option>
						<option value="3" ${level==3?'selected':''}>정회원</option>
						<option value="4" ${level==4?'selected':''}>준회원</option>
						<option value="999" ${level==999?'selected':''}>탈퇴대기회원</option>
					</select>
				</span>
			</div>
			<div class="col">
				<span class="d-flex ms-2" style="flex-grow: 1; justify-content: flex-end;">
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
					<th>나이</th>
					<th>최종방문일</th>
					<th>활동여부</th>
					<th>회원등급</th>
				</tr>
				<c:forEach var="vo" items="${mVOS}" varStatus="st">
					<tr>
						<td>
							<input type="checkbox" name="idxFlag" id="idxFlag${vo.idx}" value="${vo.idx}" ${vo.level==0?'disabled':''} />
							${st.count}
						</td>
						<td>${vo.mid}</td>
						<td>${vo.nickName}</td>
						<td>${vo.name}</td>
						<td>${vo.age}</td>
						<td>${fn:substring(vo.lastDate,0,16)}</td>
						<td>
							<c:if test="${vo.userDelete == '활동'}">활동중</c:if>
							<c:if test="${vo.userDelete == '삭제'}">탈퇴대기중</c:if>
						</td>
						<td>
							<select name="level" id="level" onchange="levelChange(this)">
								<option value="0/${vo.idx}" ${vo.level==0?'selected':''}>관리자</option>
								<option value="1/${vo.idx}" ${vo.level==1?'selected':''}>업자</option>
								<option value="2/${vo.idx}" ${vo.level==2?'selected':''}>우수회원</option>
								<option value="3/${vo.idx}" ${vo.level==3?'selected':''}>정회원</option>
								<option value="4/${vo.idx}" ${vo.level==4?'selected':''}>준회원</option>
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
	<!-- 검색기 시작 -->
	<div class="text-center">
		<form name="searchForm" action="MemberSearch">
			<b>검색:</b>
			<select name="search" id="search">
				<option value="mid">아이디</option>
			</select>
			<input type="text" name="searchStr" id="searchStr" required />
			<input type="submit" value="검색" class="btn btn-info btn-sm mb-1" />
		</form>
	</div>
	<!-- 검색기 끝 -->
</body>
</html>