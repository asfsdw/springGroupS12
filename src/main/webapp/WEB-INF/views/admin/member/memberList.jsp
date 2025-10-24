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
</head>
<body>
	<div class="container text-center">
		<h2>회원 리스트</h2>
		<div class="row mb-2">
			<div class="col">
				<span class="d-flex input-group-text ms-2" style="width:214px">등급별로 보기:&nbsp;&nbsp;
					<select name="levelPage" id="levelPage" onchange="levelPageCheck()" class="form-select" style="width:100px">
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
					<select id="levelSelect" class="form-select" style="width:102px">
						<option>선택</option>
						<option value="1">업자</option>
						<option value="2">우수회원</option>
						<option value="3">정회원</option>
						<option value="4">준회원</option>
					</select>
					<input type="button" value="등급변경" onclick="levelAllChange()" class="btn btn-success btn-sm" />
				</span>
			</div>
		</div>
		<hr/>
		<table class="table table-hover">
			<tr class="table-secondary">
				<th><input type="checkbox" id="check" name="check" onclick="checkClick()" /></th>
				<th>아이디</th>
				<th>닉네임</th>
				<th>성명</th>
				<th>나이</th>
				<th>최종방문일</th>
				<th>활동여부</th>
				<th>회원등급</th>
				<th>비고</th>
			</tr>
			<c:forEach var="vo" items="${mVOS}" varStatus="st">
				<tr>
					<td><input type="checkbox" id="idxFlag${st.index}" name="idxFlag" ${vo.level==0?'disabled':''} /></td>
					<td>${vo.mid}</td>
					<td>${vo.nickName}</td>
					<td>${vo.name}</td>
					<td>${vo.age}</td>
					<td>${fn:substring(vo.lastDate,0,16)}</td>
					<td>
						<c:if test="${vo.userDelete == '활동'}">활동중</c:if>
						<c:if test="${vo.userDelete == '삭제'}">탈퇴대기중</c:if>
					</td>
					<td class="d-flex justify-content-center">
						<select id="level${st.index}" name="level" class="form-select" style="width:102px">
							<option value="0" ${vo.level==0?'selected':''}>관리자</option>
							<option value="1" ${vo.level==1?'selected':''}>업자</option>
							<option value="2" ${vo.level==2?'selected':''}>우수회원</option>
							<option value="3" ${vo.level==3?'selected':''}>정회원</option>
							<option value="4" ${vo.level==4?'selected':''}>준회원</option>
							<option value="999" ${vo.level==999?'selected':''}>탈퇴신청</option>
						</select>
					</td>
					<td>
						<input type="button" value="변경" onclick="levelChange(${st.index},${vo.idx})" class="btn btn-success btn-sm" />
					</td>
				</tr>
				<input type="hidden" id="idx${st.index}" value="${vo.idx}" />
			</c:forEach>
		</table>
		<p><br/></p>
	</div>
	<!-- 검색기 시작 -->
	<form name="searchForm" action="MemberSearch" class="d-flex justify-content-center">
		<div class="input-group" style="width:500px">
			<b class="input-group-text">검색:</b>
			<select name="search" id="search" class="form-select" style="width:120px; flex:0 0 auto;">
				<option value="mid">아이디</option>
			</select>
			<input type="text" name="searchStr" id="searchStr" required />
			<input type="submit" value="검색" class="btn btn-info btn-sm" />
		</div>
	</form>
	<!-- 검색기 끝 -->
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>