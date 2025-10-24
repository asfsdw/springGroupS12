<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
			let price = 0;
			let totPrice = 0;
			
			for(let i=0; i<$("[id^=price]").length; i++) {
				totPrice += parseInt($("#price"+i).text().replace(",","").replace("원",""));
			}
			$("#totPrice").html(totPrice.toLocaleString()+"원");
			
			// 포인트 사용시 최소 100단위로 사용해야함.
			$("#point").on("blur", function() {
				let val = parseInt($(this).val()) || 0;
				val = Math.floor(val / 100) * 100; // 100단위 내림
				if(val > ${point}) val = ${point};
				$(this).val(val);
			});
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
			<c:set var="addresss" value="" />
			<c:if test="${deliveryVOS == null}">
				<c:if test="${vo.address != ' / / / '}">
					<c:set var="addresss" value="${fn:split(dVO.address, '/')}" />
				</c:if>
				<tr>
					<td>${dVO.mid}</td>
					<td><img src="${ctp}/data/shop/${dVO.productImage}" style="width:150px"/></td>
					<td>${dVO.title}</td>
					<td>${dVO.orderQuantity}개</td>
					<td id="price0"><fmt:formatNumber value="${dVO.price}" />원</td>
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
						<td id="price${st.index}"><fmt:formatNumber value="${vo.price * vo.orderQuantity}" />원</td>
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
		<c:if test="${!empty sMid}">
			<div class="row input-group ps-3">
				<div class="col input-group-text justify-content-center">보유 포인트: ${point}</div>
				<input type="number" id="point" name="point" value="0" min="0" max="${point}" step="100" class="col form-control text-end" />
			</div>
			<p></p>
			<div class="row">
				<div class="col text-center" style="margin-left:130px">(포인트를 사용하면 구매할 때<br/>포인트 10 : 가격 1 비율로 구매가격이 할인됩니다.)</div>
				<div class="col text-end"><input type="button" value="포인트적용" onclick="pointUse(1)" class="btn btn-success" /></div>
				<div class="col"><input type="button" value="포인트해제" onclick="pointUse(2)" class="btn btn-warning" /></div>
			</div>
			<p></p>
		</c:if>
		<input type="hidden" id="usedPoint" name="usedPoint" value="0" />
		<div class="row input-group ps-3">
			<div id="totPrice" class="col input-group-text justify-content-center"></div>
			<c:if test="${addresss == ''}">
				<input type="button" value="배송지입력" id="buyBtn" onclick="buy()" class=" col btn btn-primary" />
			</c:if>
			<c:if test="${addresss != ''}">
				<input type="button" value="배송지입력" id="buyBtn" onclick="buy('${addresss[0]}','${addresss[1]}','${addresss[2]}','${addresss[3]}')" class=" col btn btn-primary" />
			</c:if>
		</div>
		<p><br/></p>
		<div id="demo"></div>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>