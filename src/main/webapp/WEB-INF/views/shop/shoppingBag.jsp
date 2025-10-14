<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<script src="${ctp}/js/shop.js"></script>
	<title></title>
	<style>
		h6 {
			position: fixed;
			right: 1rem;
			bottom: -50px;
			transition: 0.7s ease;
			z-index: 2;
		}
		.on {
			opacity: 0.8;
			cursor: pointer;
			bottom: 0;
		}
	</style>
</head>
<body>
	<div class="container text-center">
		<h2>장바구니</h2>
		<hr/>
		<form name="buyForm">
			<table class="table table-bordered">
				<tr class="table-secondary">
					<th><input type="checkbox" id="bagCheck" onchange="tableCheckChange()" name="bagCheck" checked /></th>
					<th>상품이미지</th>
					<th>상품명</th>
					<th>구매수량<br/>(변경가능)</th>
					<th>구매가격</th>
					<th>비고</th>
				</tr>
				<c:forEach var="vo" items="${vos}" varStatus="st">
					<tr>
						<td><input type="checkbox" id="bagCheck${st.count}" name="bagCheck" onchange="checkChange(${st.count},${vo.price})" checked /></td>
						<td><img src="${ctp}/data/shop/${vo.productImage}" style="width:150px" /></td>
						<td>${vo.title}</td>
						<td><input type="number" value="${vo.orderQuantity}" id="orderQuantity${st.count}" name="orderQuantity" min="1" max="10" onchange="priceChange(${st.count}, ${vo.price})" class="form-control" /></td>
						<td id="price${st.count}">${vo.price}원</td>
						<td><input type="button" value="삭제" onclick="shoppingBagDelete(${vo.idx})" class="btn btn-danger" /></td>
					</tr>
				</c:forEach>
			</table>
			<div class="row input-group ps-3">
				<div id="totPrice" class="col input-group-text justify-content-center"></div>
				<input type="button" value="구입" onclick="buyCheck()" class=" col btn btn-success" />
			</div>
		</form>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동"/></h6>
</body>
</html>