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
	<script>
		$(() => {
			$("#orderQuantity").on("change", () => {
				$("#demo").html(${vo.price}*$("#orderQuantity").val()+"원");
			});
		});
	</script>
	<style>
		#content img {
			max-width: 100%;
			height: auto;
		}
	</style>
</head>
<body>
	<div class="container text-center">
	<p></p>
	<div class="row">
		<div class="col"><img src="${ctp}/shop/${vo.productImage}" style="width:500px" /></div>
		<div class="col text-start">
			<div><h2>${vo.title}</h2></div>
			<div><h3>가격: ${vo.price}원</h3></div>
			<div><h3>재고: ${vo.quantity}개</h3></div>
			<div><h4>분류: ${vo.kategorie}</h4></div>
			<hr/>
			<div><h4>판매자: ${vo.nickName}</h4></div>
			<hr/>
			<form name="producForm" method="post">
				<div class="input-group">
					<input type="number" value="1" min="1" max="10" id="orderQuantity" name="orderQuantity" />
					<div class="input-group-text">개</div>
				</div>
				<p></p>
				<div class="input-group">
					<div id="demo" class="input-group-text">${vo.price*1}원</div>
					<input type="button" value="구매" onclick="soldCheck()" class="btn btn-success" />
				</div>
			</form>
		</div>
	</div>
	<div><h2>상품설명</h2></div>
	<div id="content">${vo.content}</div>
	</div>
</body>
</html>