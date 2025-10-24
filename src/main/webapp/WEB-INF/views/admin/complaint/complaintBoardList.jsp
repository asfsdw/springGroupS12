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
	<script src="${ctp}/js/admin.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/admin.css" />
	<title></title>
</head>
<body>
	<div class="container text-center">
		<h2>신고된 게시글</h2>
		<hr/>
		<table class="table table-bordered">
			<tr class="table-secondary">
				<th><input type="checkbox" id="check" name="check" onclick="checkClick()" /></th>
				<th>게시글 번호</th>
				<th>게시글 제목</th>
				<th>올린이</th>
				<th>신고상태</th>
				<th>신고이유</th>
				<th>신고날짜</th>
				<th>공개상태</th>
				<th>비고</th>
			</tr>
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<tr>
					<td><input type="checkbox" id="idxFlag${st.index}" name="idxFlag" /></td>
					<td>${vo.idx}</td>
					<td>${vo.title}</td>
					<td>${vo.mid}</td>
					<td>${vo.complaint}</td>
					<td>${vo.cpContent}</td>
					<td>${fn:substring(vo.cpDate,0,10)}</td>
					<td class="d-flex justify-content-center">
						<select id="openSW${st.index}" class="form-select" style="width:90px">
							<option ${vo.openSW=='공개'?'selected':''}>공개</option>
							<option ${vo.openSW=='비공개'?'selected':''}>비공개</option>
							<option ${vo.openSW=='숨기기'?'selected':''}>숨기기</option>
							<option>삭제</option>
						</select>
					</td>
					<td><input type="button" value="변경" onclick="complaintBoardOpenSWChange(${st.index}, ${vo.idx})" class="btn btn-success btn-sm" /></td>
				</tr>
			</c:forEach>
		</table>
		<p><br/></p>
		<!-- 검색기 시작 -->
		<form name="searchForm" action="ComplaintBoardSearch" class="d-flex justify-content-center">
			<div class="input-group" style="width:500px">
				<div class="input-group-text"><b>검색:</b></div>
				<select name="search" id="search" class="form-select" style="width:120px; flex:0 0 auto;">
					<option value="idx">게시글 번호</option>
					<option value="mid">올린이</option>
				</select>
				<input type="text" name="searchStr" id="searchStr" required class="form-control" />
				<input type="button" value="검색" onclick="complaintBoardSearch()" class="btn btn-info" />
			</div>
		</form>
		<!-- 검색기 끝 -->
		<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
	</div>
</body>
</html>