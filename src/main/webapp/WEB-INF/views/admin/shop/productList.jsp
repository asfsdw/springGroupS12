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
		<div class="row mb-2">
			<div class="col">
				<span class="d-flex input-group-text ms-2" style="width:246px">상품상태별로 보기:&nbsp;&nbsp;
					<select name="openSWPage" id="openSWPage" onchange="openSWPageCheck()" class="form-select" style="width:102px">
						<option ${openSW=='전체'?'selected':''}>전체</option>
						<option ${openSW=='공개'?'selected':''}>공개</option>
						<option ${openSW=='신청접수'?'selected':''}>신청접수</option>
						<option ${openSW=='비공개'?'selected':''}>비공개</option>
					</select>
				</span>
			</div>
			<div class="col d-flex justify-content-end">
				<select id="openSW" class="form-select" style="width:140px">
					<option>선택</option>
					<option>공개</option>
					<option>비공개</option>
				</select>
				<input type="button" value="변경" onclick="openSWAllChange()" class="btn btn-success btn-sm" />
			</div>
		</div>
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
				<th>상품수정</th>
			</tr>
			<c:set var="cnt" value="0" />
			<c:if test="${!empty vos}">
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
						<td>
							<input type="button" value="수정" onclick="productUpdate('${ctp}', '${vo.idx}')" class="btn btn-primary btn-sm" />
							<c:if test="${vo.openSW == '비공개'}">
								<input type="button" value="삭제" onclick="productDelete('${ctp}', '${vo.idx}')" class="btn btn-danger btn-sm" />
							</c:if>
						</td>
					</tr>
					<input type="hidden" id="idx${cnt}" value="${vo.idx}" />
					<c:set var="cnt" value="${cnt+1}" />
				</c:forEach>
			</c:if>
			<c:if test="${!empty vo}">
				<tr>
					<td><input type="checkbox" id="idxFlag0" name="idxFlag" /></td>
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
			</c:if>
		</table>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>