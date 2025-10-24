<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
</head>
<body>
	<div class="container text-center">
		<h2>배송상황</h2>
		<hr/>
		<c:if test="${vos == '[]'}">
			<form name="deliveryForm">
				<div class="d-flex justify-content-center">
					<div class="input-group" style="width:50%">
						<input type="text" id="deliveryIdx" name="deliveryIdx" placeholder="주문번호를 입력해주세요." class="form-control" />
						<input type="button" value="검색" onclick="deliverySearch()" class="btn btn-success" />
					</div>
				</div>
			</form>
		</c:if>
		<c:if test="${vos != '[]'}">
			<table class="table table-bordered">
				<tr class="table table-secondary">
					<th>주문번호<c:if test="${empty sMid}"><br/>(비회원일경우 반드시 주문번호를 기억해주세요.<br/>주문번호를 모르면 배송상황을 검색할 수 없습니다.)</c:if></th>
					<th colspan="2">주문상품</th>
					<th>주문개수</th>
					<th>주문가격</th>
					<th>배송상태</th>
					<th>주문취소<br/>(상품이 준비중일때만 취소할 수 있습니다.)</th>
				</tr>
				<c:forEach var="vo" items="${vos}">
					<c:if test="${deliveryIdx != vo.deliveryIdx}">
						<c:set var="cnt" value="0" />
						<c:forEach var="cntCalc" items="${vos}">
							<c:if test="${vo.deliveryIdx == cntCalc.deliveryIdx}">
								<c:set var="cnt" value="${cnt+1}" />
							</c:if>
						</c:forEach>
					</c:if>
					<tr>
						<c:if test="${deliveryIdx != vo.deliveryIdx}">
							<td rowspan="${cnt}">${vo.deliveryIdx}</td>
						</c:if>
						<td><img src="${ctp}/data/shop/${vo.productImage}" style="width:150px" /></td>
						<td>${vo.title}</td>
						<td>${vo.orderQuantity}</td>
						<td>
							<fmt:formatNumber value="${vo.price}" />
							<c:if test="${vo.usedPoint != 0}">
							<br/>
							(사용한 포인트: ${vo.usedPoint})
							</c:if>
						</td>
						<c:if test="${deliveryIdx != vo.deliveryIdx}">
							<td rowspan="${cnt}">${vo.deliverySW}</td>
							<c:if test="${vo.deliverySW == '준비중'}">
								<td rowspan="${cnt}"><input type="button" value="주문취소" onclick="deliveryCancel('${vo.deliveryIdx}','${vo.usedPoint}')" class="btn btn-danger" /></td>
							</c:if>
							<c:if test="${vo.deliverySW == '배송완료'}">
								<td rowspan="${cnt}"><input type="button" value="구매완료" onclick="deliveryComp('${vo.deliveryIdx}')" class="btn btn-success" /></td>
							</c:if>
							<c:if test="${vo.deliverySW != '준비중' && vo.deliverySW != '배송완료'}">
								<td rowspan="${cnt}"><input type="button" value="${vo.deliverySW}" disabled class="btn btn-outline-success" /></td>
							</c:if>
							<c:set var="deliveryIdx" value="${vo.deliveryIdx}" />
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>