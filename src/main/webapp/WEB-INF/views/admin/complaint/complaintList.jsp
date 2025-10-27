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
		<h2>신고목록</h2>
		<div class="row mb-2">
			<div class="col">
				<span class="d-flex input-group-text ms-2" style="width:246px">신고상태별로 보기:&nbsp;&nbsp;
					<select name="progressPage" id="progressPage" onchange="progressPageCheck()" class="form-select" style="width:102px">
						<option ${progress=='전체'?'selected':''}>전체</option>
						<option ${progress=='신고접수'?'selected':''}>신고접수</option>
						<option ${progress=='신고반려'?'selected':''}>신고반려</option>
						<option ${progress=='처리완료'?'selected':''}>처리완료</option>
					</select>
				</span>
			</div>
		</div>
		<hr/>
		<div class="container">
			<table class="table table-bordered">
				<tr class="table-secondary">
					<th><input type="checkbox" id="check" name="check" onclick="checkClick()" /></th>
					<th>분류</th>
					<th>신고글 제목</th>
					<th>신고이유</th>
					<th>신고한 사람</th>
					<th>신고일</th>
					<th>신고상태</th>
					<th>비고</th>
				</tr>
				<c:forEach var="vo" items="${vos}" varStatus="st">
					<tr>
						<td><input type="checkbox" id="idxFlag${st.index}" name="idxFlag" /></td>
						<c:if test="${vo.part == 'board'}"><td id="part${st.index}">게시판</td></c:if>
						<c:if test="${vo.part == 'shop'}"><td id="part${st.index}">굿즈</td></c:if>
						<td>${vo.parentTitle}</td>
						<c:if test="${vo.part == 'board'}">
							<td><a href="${ctp}/admin/ComplaintBoardList?idx=${vo.partIdx}">${vo.cpContent}</a></td>
						</c:if>
						<c:if test="${vo.part == 'shop'}">
							<td><a href="${ctp}/">${vo.cpContent}</a></td>
						</c:if>
						<td>${vo.cpMid}</td>
						<td>${fn:substring(vo.cpDate,0,10)}</td>
						<td class="d-flex justify-content-center">
							<select id="progress${st.index}" class="form-select" style="width:102px">
								<option ${vo.progress=='신고접수'?'selected':''}>신고접수</option>
								<option ${vo.progress=='신고반려'?'selected':''}>신고반려</option>
								<option ${vo.progress=='처리하기'?'selected':''}>처리하기</option>
								<option ${vo.progress=='처리완료'?'selected':''}>처리완료</option>
								<option>삭제</option>
							</select>
						</td>
						<td><input type="button" value="변경" onclick="complaintChange('${ctp}','${st.index}','${vo.idx}','${vo.partIdx}')" class="btn btn-success btn-sm" /></td>
					</tr>
					<input type="hidden" id="idx${st.index}" value="${vo.idx}" />
				</c:forEach>
			</table>
		</div>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>