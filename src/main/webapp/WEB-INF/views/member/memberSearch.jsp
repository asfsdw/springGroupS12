<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<script src="${ctp}/js/login.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/member.css" />
	<title></title>
	<script>
		'use strict';
		
		// 등급별로 보기.
		function levelPageCheck() {
			let level = $("#levelPage").val();
			location.href="${ctp}/member/MemberList?level="+level;
		}
		// 한 페이지 최대 수 변경.
		function viewPageCheck() {
			let pageSize = $("#viewPageCnt").val();
			location.href="${ctp}/member/MemberList?pageSize="+pageSize;
		}
	</script>
</head>
<body>
	<div class="container text-center">
		<h2>회원 리스트</h2>
		<div class="row mb-2">
			<div class="col">
				<span class="d-flex me-2" style="flex-grow: 1; justify-content: flex-end;">한 페이지에 최대:&nbsp;&nbsp;
					<select name="viewPageCnt" id="viewPageCnt" onchange="viewPageCheck()">
						<option value="5" ${pVO.pageSize==5 ? 'selected' : ''}>5개씩 보기</option>
						<option value="10"<c:if test="${pVO.pageSize == 10}">selected</c:if>>10개씩 보기</option>
						<option value="15"<c:if test="${pVO.pageSize == 15}">selected</c:if>>15개씩 보기</option>
						<option value="20"<c:if test="${pVO.pageSize == 20}">selected</c:if>>20개씩 보기</option>
						<option value="30"<c:if test="${pVO.pageSize == 30}">selected</c:if>>30개씩 보기</option>
					</select>
				</span>
			</div>
		</div>
		<hr/>
		<table class="table table-hover">
			<tr class="table-secondary">
				<th>번호</th>
				<th>아이디</th>
				<th>닉네임</th>
				<th>자기소개</th>
				<th>등급</th>
				<th>최종방문일</th>
			</tr>
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<c:set var="strLevel" value="${vo.level==0?'관리자':vo.level==1?'업자':vo.level==2?'우수회원':vo.level==3?'정회원':'준회원'}" />
				<tr>
					<td>${st.count}</td>
					<c:if test="${vo.userInfo=='공개'}"><td>${vo.mid}</td></c:if>
					<c:if test="${vo.userInfo!='공개'}"><td>비공개</td></c:if>
					<td>${vo.nickName}</td>
					<c:if test="${vo.userInfo=='공개'}"><td>${vo.content}</td></c:if>
					<c:if test="${vo.userInfo!='공개'}"><td>비공개</td></c:if>
					<c:if test="${vo.userInfo=='공개'}"><td>${strLevel}</td></c:if>
					<c:if test="${vo.userInfo!='공개'}"><td>비공개</td></c:if>
					<c:if test="${vo.userInfo=='공개'}"><td>${fn:substring(vo.lastDate,0,16)}</td></c:if>
					<c:if test="${vo.userInfo!='공개'}"><td>비공개</td></c:if>
				</tr>
			</c:forEach>
		</table>
		<p><br/></p>
	</div>
	<!-- 블록페이지 시작 -->
	<div class="input-group justify-content-center">
		<div class="pagination">
			<!-- pag와 pageSize를 BoardList에 보내준다. -->
			<c:if test="${pVO.pag > 1}"><a href="${ctp}/member/MemberList?pag=1&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">첫 페이지</a></c:if>
				<c:if test="${pVO.curBlock > 0}">
					<a href="${ctp}/member/MemberList?pag=${(pVO.curBlock - 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">이전 블록</a>
				</c:if>
				<c:forEach var="i" begin="${(pVO.curBlock * pVO.blockSize) + 1}" end="${(pVO.curBlock * pVO.blockSize) + pVO.blockSize}" varStatus="st">
					<c:if test="${i <= pVO.totPage && i == pVO.pag}">
						<span class="page-item active page-link bg-secondary border-secondary">${i}</span>
					</c:if>
					<c:if test="${i <= pVO.totPage && i != pVO.pag}">
						<a href="${ctp}/member/MemberList?pag=${i}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">${i}</a>
					</c:if>
				</c:forEach>
				<c:if test="${pVO.curBlock < pVO.lastBlock}">
					<a href="${ctp}/member/MemberList?pag=${(pVO.curBlock + 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">다음 블록</a>
				</c:if>
					<c:if test="${pag < totPage}">
				<a href="${ctp}/member/MemberList?pag=${pVO.totPage}&pageSize=${pVO.pageSize}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">마지막 페이지</a>
			</c:if>
		</div>
	</div>
	<p></p>
	<!-- 블록페이지 끝 -->
	<!-- 검색기 시작 -->
	<form name="searchForm" action="MemberSearch" class="d-flex justify-content-center">
		<div class="input-group" style="width:500px">
			<b class="input-group-text">검색:</b>
			<select name="search" id="search" class="form-select" style="width:120px; flex:0 0 auto;">
				<option value="nickName">닉네임</option>
			</select>
			<input type="text" name="searchStr" id="searchStr" required />
			<input type="submit" value="검색" class="btn btn-info btn-sm" />
		</div>
	</form>
	<!-- 검색기 끝 -->
	<p><br/></p>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>