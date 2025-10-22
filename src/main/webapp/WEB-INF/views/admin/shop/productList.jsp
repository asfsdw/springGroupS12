<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<h2>상품목록</h2>
		<hr/>
		<table class="table table-bordered">
			<tr class="table-secondary">
				<th><input type="checkbox" id="check" name="check" onclick="checkClick()" /></th>
				<th>상품이미지</th>
				<th>상품명</th>
				<th>상품가격</th>
				<th>상품재고</th>
				<th>판매개수</th>
				<th>상품상태</th>
				<th>상품신고</th>
			</tr>
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<tr>
					<td><input type="checkbox" id="idxFlag${st.index}" name="idxFlag" /></td>
					<td><img src="${ctp}/data/shop/${vo.productImage}" style="width:200px" /></td>
					<td>${vo.title}</td>
					<td>${vo.price}</td>
					<td>${vo.quantity}</td>
					<td>${vo.sold}</td>
					<td>${vo.openSW}</td>
					<c:if test="${vo.complaint == 'NO'}">
						<td>정상</td>
					</c:if>
					<c:if test="${vo.complaint != 'NO'}">
						<td><font color="red">신고</font></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>