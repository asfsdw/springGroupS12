<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<link type="text/css" rel="stylesheet" href="${ctp}/css/linkOrange.css">
	<script src="${ctp}/js/shop.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/shop.css" />
	<title></title>
</head>
<body>
	<div class="container text-center">
		<h2></h2>
		<div class="text-start">
			<c:if test="${sLevel == 1}">
				<input type="button" value="상품등록" onclick="location.href='${ctp}/shop/ProductAdd'" class="btn btn-success btn-sm" />
			</c:if>
			<c:if test="${sLevel == 2}">
				<input type="button" value="상품등록신청" onclick="location.href='${ctp}/shop/ProductAddSub'" class="btn btn-success btn-sm" />
			</c:if>
		</div>
		<hr/>
		<div class="row">
			<c:set var="cnt" value="0" />
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<div class="col" style="width:200px">
					<a href="${ctp}/shop/Product?idx=${vo.idx}" style="display:inline-block">
						<div><img src=${ctp}/data/shop/${vo.productImage} style="width:200px" /></div>
						<div>${vo.title}</div>
						<div>가격: ${vo.price}원 / 재고: ${vo.quantity}개</div>
					</a>
				</div>
				<c:set var="cnt" value="${cnt+1}" />
				<c:if test="${cnt > 4}"></div><div class="row"></c:if>
			</c:forEach>
		</div>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>