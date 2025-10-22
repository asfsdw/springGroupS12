<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title></title>
	<script src="${ctp}/js/admin.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/admin.css" />
</head>
<body>
	<div class="text-start">
		<h3 class="ms-3"><a href="${ctp}/"><b>HOME</b></a></h3>
		<h3 class="ms-3"><a href="${ctp}/admin/AdminMain"><b>관리자메뉴</b></a></h3>
		<hr/>
		<div>
			<div class="main ms-3 mb-2"><b>신청처리</b></div>
			<div class="sub">
				<p><a href="${ctp}/admin/SubScriptList">신청보기</a></p>
				<p><a href="${ctp}/admin/MemberList">멤버관리</a></p>
			</div>
			<div class="main ms-3 mb-2"><b>상품관리</b></div>
			<div class="sub">
				<p><a href="${ctp}/admin/ProductList">상품목록</a></p>
				<p><a href="${ctp}/admin/DeliveryList">배송관련</a></p>
			</div>
			<div class="main ms-3 mb-2"><b>신고처리</b></div>
			<div class="sub">
				<p><a href="${ctp}/admin/ComplaintList">신고목록</a></p>
				<p><a href="${ctp}/admin/ComplaintBoardList">신고된 게시글</a></p>
			</div>
		</div>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>