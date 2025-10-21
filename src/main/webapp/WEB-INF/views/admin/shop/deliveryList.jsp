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
	<script src="${ctp}/js/admin.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/admin.css" />
	<title></title>
</head>
<body>
	<div class="container text-center">
		<h2></h2>
		<div class="row mb-2">
			<div class="col text-start">
				<select id="deliverySW">
					<option ${deliverySW=='전체'?'selected':''}>전체</option>
					<option ${deliverySW=='준비중'?'selected':''}>준비중</option>
					<option ${deliverySW=='준비완료'?'selected':''}>준비완료</option>
					<option ${deliverySW=='배송중'?'selected':''}>배송중</option>
					<option ${deliverySW=='배송완료'?'selected':''}>배송완료</option>
					<option ${deliverySW=='구매완료'?'selected':''}>구매완료</option>
				</select>
			</div>
			<div class="col text-end">
				<input type="button" value="전체선택" onclick="allCheck()" class="btn btn-success btn-sm me-1"/>
				<input type="button" value="전체취소" onclick="allReset()" class="btn btn-primary btn-sm me-1"/>
				<input type="button" value="선택반전" onclick="reverseCheck()" class="btn btn-info btn-sm me-1"/>
			</div>
		</div>
		<div class="row">
			<div class="col text-end">
				<select id="deliverySWChange">
					<option>준비중</option>
					<option>준비완료</option>
					<option>배송중</option>
					<option>배송완료</option>
				</select>
				<input type="button" value="변경" onclick="deliverySWAllChange()" class="btn btn-success btn-sm" />
			</div>
		</div>
		<p></p>
		<form name="myform">
			<table class="table table-bordered">
				<tr class="table-secondary">
					<th><input type="checkbox" id="check" name="check" onclick="checkClick()" /></th>
					<th>배송번호</th>
					<th>상품이미지</th>
					<th>상품명</th>
					<th>구매자</th>
					<th>구매날짜</th>
					<th>배송현황</th>
					<th>비고</th>
				</tr>
				<c:forEach var="vo" items="${dVOS}" varStatus="st">
					<c:if test="${deliveryIdx != vo.deliveryIdx}">
						<c:set var="cnt" value="0" />
						<c:forEach var="cntCalc" items="${dVOS}">
							<c:if test="${vo.deliveryIdx == cntCalc.deliveryIdx}">
								<c:set var="cnt" value="${cnt+1}" />
							</c:if>
						</c:forEach>
					</c:if>
					<tr>
						<c:if test="${deliveryIdx != vo.deliveryIdx}">
							<td rowspan="${cnt}"><input type="checkbox" id="idxFlag${st.index}" name="idxFlag" /></td>
							<td rowspan="${cnt}">${vo.deliveryIdx}</td>
						</c:if>
						<td><img src="${ctp}/data/shop/${vo.productImage}" style="width:150px" /></td>
						<td>${vo.title}</td>
						<c:if test="${deliveryIdx != vo.deliveryIdx}">
							<td rowspan="${cnt}">${vo.nickName}</td>
							<td rowspan="${cnt}">${fn:substring(vo.orderDate, 0, 10)}</td>
							<td rowspan="${cnt}">
								<select id="deliverySW${st.index}">
									<option ${vo.deliverySW=='대기중'?'selected':''}>대기중</option>
									<option ${vo.deliverySW=='준비중'?'selected':''}>준비중</option>
									<option ${vo.deliverySW=='준비완료'?'selected':''}>준비완료</option>
									<option ${vo.deliverySW=='배송중'?'selected':''}>배송중</option>
									<option ${vo.deliverySW=='배송완료'?'selected':''}>배송완료</option>
									<option ${vo.deliverySW=='구매완료'?'selected':''}>구매완료</option>
								</select>
							</td>
							<td rowspan="${cnt}">
								<input type="button" value="변경" onclick="deliverySWChange('${st.index}','${vo.deliveryIdx}')" class="btn btn-success mb-1" /><br/>
								<c:if test="${vo.deliverySW == '구매완료' && vo.compDate > 29}">
									<input type="button" value="삭제" onclick="deliveryDelete('${st.index}','${vo.deliveryIdx}')" class="btn btn-danger" />
								</c:if>
							</td>
							<c:set var="deliveryIdx" value="${vo.deliveryIdx}" />
						</c:if>
					</tr>
					<input type="hidden" id="deliveryIdx${st.index}" value="${vo.deliveryIdx}" />
				</c:forEach>
			</table>
			<input type="hidden" id="deliveryIdx" name="deliveryIdx" value="" />
		</form>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>