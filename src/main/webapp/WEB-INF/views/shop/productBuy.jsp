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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="${ctp}/js/postCode.js"></script>
	<script src="${ctp}/js/shop.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/shop.css" />
	<title></title>
	<script>
		'use strict';
		
		// 초기 구매 가격들 설정.
		$(() => {
			let totPrice = 0;
			
			for(let i=1; i<=$("[id^=price]").length; i++) {
				totPrice += parseInt($("#price"+i).text());
			}
			$("#totPrice").html(totPrice+"원");
		});
	</script>
</head>
<body>
	<div class="container text-center">
		<h2>주문확인</h2>
		<hr/>
		<form name="buyForm" method="post" action="Buy">
		<table class="table table-bordered">
			<tr class="table-secondary">
				<th>주문자</th>
				<th>상품이미지</th>
				<th>상품명</th>
				<th>구매수량</th>
				<th>구매가격</th>
			</tr>
			<c:if test="${deliveryVOS == null}">
				<tr>
					<td>${dVO.mid}</td>
					<td><img src="${ctp}/data/shop/${dVO.productImage}" style="width:150px"/></td>
					<td>${dVO.title}</td>
					<td>${dVO.orderQuantity}개</td>
					<td id="price1">${dVO.price}원</td>
				</tr>
				<input type="hidden" name="idx" value="${dVO.idx}" />
				<input type="hidden" name="mid" value="${dVO.mid}" />
				<c:if test="${dVO.nickName != null}">
					<input type="hidden" name="nickName" value="${dVO.nickName}" />
				</c:if>
				<c:if test="${dVO.nickName == null}">
					<input type="hidden" name="nickName" value="비회원" />
				</c:if>
				<c:if test="${dVO.email != null}">
					<input type="hidden" name="email" value="${dVO.email}" />
				</c:if>
				<c:if test="${dVO.email == null}">
					<input type="hidden" name="email" value="" />
				</c:if>
				<input type="hidden" name="title" value="${dVO.title}" />
				<input type="hidden" name="orderQuantity" value="${dVO.orderQuantity}" />
				<input type="hidden" name="price" value="${dVO.price}" />
				<input type="hidden" name="productImage" value="${dVO.productImage}" />
			</c:if>
			<c:set var="addresss" value="" />
			<c:if test="${deliveryVOS != null}">
				<c:forEach var="vo" items="${deliveryVOS}" varStatus="st">
					<c:if test="${vo.address != ' / / / '}">
						<c:set var="addresss" value="${fn:split(vo.address, '/')}" />
					</c:if>
					<tr>
						<td>${vo.mid}</td>
						<td><img src="${ctp}/data/shop/${vo.productImage}" style="width:150px"/></td>
						<td>${vo.title}</td>
						<td>${vo.orderQuantity}</td>
						<td id="price${st.count}">${vo.price * vo.orderQuantity}원</td>
					</tr>
					<input type="hidden" name="idxs" value="${vo.idx}" />
					<input type="hidden" name="parentIdx" value="${vo.parentIdx}" />
					<input type="hidden" name="mid" value="${vo.mid}" />
					<input type="hidden" name="nickName" value="${vo.nickName}" />
					<input type="hidden" name="email" value="${vo.email}" />
					<input type="hidden" name="title" value="${vo.title}" />
					<input type="hidden" name="orderQuantity" value="${vo.orderQuantity}" />
					<input type="hidden" name="price" value="${vo.price}" />
					<input type="hidden" name="productImage" value="${vo.productImage}" />
				</c:forEach>
			</c:if>
		</table>
		<p></p>
		<div class="row input-group ps-3">
			<div id="totPrice" class="col input-group-text justify-content-center"></div>
			<c:if test="${addresss == ''}">
				<input type="button" value="주소입력" id="buyBtn" onclick="buy()" class=" col btn btn-primary" />
			</c:if>
			<c:if test="${addresss != ''}">
				<input type="button" value="주소입력" id="buyBtn" onclick="buy('${addresss[0]}','${addresss[1]}','${addresss[2]}','${addresss[3]}')" class=" col btn btn-primary" />
			</c:if>
		</div>
		<p><br/></p>
		<div id="demo"></div>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>