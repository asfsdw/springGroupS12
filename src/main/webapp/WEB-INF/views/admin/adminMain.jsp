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
	<div class="text-center">
		<p><br/></p>
		<h2>관리자 메인화면</h2>
		<hr/>
		<div class="d-flex justify-content-center">
			<table class="table table-bordered" style="width:400px">
				<tr>
					<th>7일 이내 올라온 정보</th>
					<th>개수</th>
				</tr>
				<tr>
					<td>새로운 신청</td>
					<td><a href="${ctp}/admin/SubScriptList">${newSubScript}</a></td>
				</tr>
				<tr>
					<td>새로 가입한 회원</td>
					<td><a href="${ctp}/admin/MemberList">${newMember}</a></td>
				</tr>
				<tr>
					<td>새로 등록된 상품</td>
					<td><a href="${ctp}/admin/ProductList">${newMember}</a></td>
				</tr>
				<tr>
					<td>신규 주문</td>
					<td><a href="${ctp}/admin/DeliveryList">${newDelivery}</a></td>
				</tr>
			</table>
		</div>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>