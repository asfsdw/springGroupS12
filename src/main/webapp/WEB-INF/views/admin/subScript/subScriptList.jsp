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
	<title></title>
	<script src="${ctp}/js/admin.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/admin.css" />
</head>
<body>
	<div class="container text-center">
		<h2>신청내역</h2>
		<div class="row mb-2">
			<div class="col text-end">
				<input type="button" value="전체선택" onclick="allCheck()" class="btn btn-success btn-sm me-1"/>
				<input type="button" value="전체취소" onclick="allReset()" class="btn btn-primary btn-sm me-1"/>
				<input type="button" value="선택반전" onclick="reverseCheck()" class="btn btn-info btn-sm me-1"/>
			</div>
		</div>
		<div class="row">
			<div class="col d-flex justify-content-end">
				<select id="openSW" class="form-select" style="width:140px">
					<option>선택</option>
					<option>신청접수</option>
					<option>신청반려</option>
					<option>공개/처리완료</option>
					<option>삭제</option>
				</select>
				<input type="button" value="변경" onclick="openSWAllChange()" class="btn btn-success btn-sm" />
			</div>
		</div>
		<p></p>
		<table class="table table-bordered">
			<tr class="table-secondary">
				<th><input type="checkbox" id="check" name="check" onclick="checkClick()" /></th>
				<th>구분</th>
				<th>신청자 아이디</th>
				<th>신청자 닉네임</th>
				<th>신청내용</th>
				<th>신청일</th>
				<th>신청현황</th>
				<th>비고</th>
			</tr>
			<c:set var="cnt" value="0" />
			<c:forEach var="vo" items="${shopVOS}" varStatus="st">
				<tr>
					<td><input type="checkbox" id="idxFlag${cnt}" name="idxFlag" /></td>
					<td id="part${cnt}">상품</td>
					<td>${vo.mid}</td>
					<td>${vo.nickName}</td>
					<td>${vo.title}</td>
					<td>${vo.shopDate}</td>
					<td class="d-flex justify-content-center">
						<select id="openSW${cnt}" class="form-select" style="width:102px">
							<option ${vo.openSW=='신청접수'?'selected':''}>신청접수</option>
							<option ${vo.openSW=='신청반려'?'selected':''}>신청반려</option>
							<option ${vo.openSW=='공개'?'selected':''}>공개</option>
							<option>삭제</option>
						</select>
					</td>
					<td><input type="button" value="변경" onclick="openSWChange('${cnt}','shop','${vo.idx}')" class="btn btn-success btn-sm" /></td>
				</tr>
				<input type="hidden" id="idx${cnt}" value="${vo.idx}" />
				<c:set var="cnt" value="${cnt+1}" />
			</c:forEach>
			<c:forEach var="vo" items="${subVOS}" varStatus="st">
				<tr>
					<td><input type="checkbox" id="idxFlag${cnt}" name="idxFlag" /></td>
					<td id="part${cnt}">일반</td>
					<td>${vo.mid}</td>
					<td>${vo.nickName}</td>
					<c:if test="${fn:substring(vo.subContent, 0, 3).equals('등급업')}">
						<td><a href="${ctp}/admin/MemberSearch?search=mid&searchStr=${vo.mid}" class="link-primary">${vo.subContent}</a></td>
					</c:if>
					<c:if test="${!fn:substring(vo.subContent, 0, 3).equals('등급업')}">
						<td>${vo.subContent}</td>
					</c:if>
					<td>${vo.subDate}</td>
					<td class="d-flex justify-content-center">
						<select id="openSW${cnt}" class="form-select" style="width:102px">
							<option ${vo.subProgress=='신청접수'?'selected':''}>신청접수</option>
							<option ${vo.subProgress=='신청반려'?'selected':''}>신청반려</option>
							<option ${vo.subProgress=='처리완료'?'selected':''}>처리완료</option>
							<option>삭제</option>
						</select>
					</td>
					<td><input type="button" value="변경" onclick="openSWChange('${cnt}','sub','${vo.idx}')" class="btn btn-success btn-sm" /></td>
				</tr>
				<input type="hidden" id="idx${cnt}" value="${vo.idx}" />
				<c:set var="cnt" value="${cnt+1}" />
			</c:forEach>
		</table>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>