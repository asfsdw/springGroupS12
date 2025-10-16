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
	<link type="text/css" rel="stylesheet" href="${ctp}/css/shop.css" />
	<title></title>
	<script>
		$(() => {
			$("#orderQuantity").on("change", () => {
				$("#demo").html(${vo.price}*$("#orderQuantity").val()+"원");
			});
		});
	</script>
</head>
<body>
	<div class="container text-center">
	<p></p>
	<div class="row">
		<div class="col"><img src="${ctp}/data/shop/${vo.productImage}" style="width:500px" /></div>
		<div class="col text-start">
			<div><h2>${vo.title}</h2></div>
			<div><h3>가격: ${vo.price}원</h3></div>
			<div><h3>재고: ${vo.quantity}개</h3></div>
			<div><h4>분류: ${vo.kategorie}</h4></div>
			<hr/>
			<div><h4>판매자: ${vo.nickName}</h4></div>
			<hr/>
			<form name="productForm" method="post">
				<div class="input-group">
					<input type="number" value="1" min="1" max="10" id="orderQuantity" name="orderQuantity" />
					<div class="input-group-text">개</div>
				</div>
				<p></p>
				<div class="input-group">
					<div id="demo" class="input-group-text">${vo.price*1}원</div>
					<input type="button" value="구매" onclick="soldCheck()" class="btn btn-success" />
				</div>
				<input type="hidden" name="mid" value="${sMid}" />
				<input type="hidden" name="title" value="${vo.title}" />
				<input type="hidden" id="price" name="price" value="" />
				<input type="hidden" name="productImage" value="${vo.productImage}" />
			</form>
			<p></p>
			<c:if test="${sLevel < 5}">
				<input type="button" value="장바구니에 담기" onclick="addShoppingBag('${vo.idx}','${sMid}','${sNickName}')" class="btn btn-info me-2" />
			</c:if>
			<input type="button" value="돌아가기" onclick="location.href='${ctp}/shop/Goods'" class="btn btn-warning" />
		</div>
	</div>
	<hr/>
	<div class="text-start"><h2>상품설명</h2></div>
	<p></p>
	<div id="content" class="text-start">${vo.content}</div>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>