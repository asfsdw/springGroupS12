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
	<script src="${ctp}/js/admin.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/admin.css" />
</head>
<body>
	<div class="container text-center">
		<h2>신청내역</h2>
		<hr/>
		<div class="row">
			<div class="col text-end">
				<input type="button" value="전체선택" onclick="allCheck()" class="btn btn-success btn-sm me-1"/>
				<input type="button" value="전체취소" onclick="allReset()" class="btn btn-primary btn-sm me-1"/>
				<input type="button" value="선택반전" onclick="reverseCheck()" class="btn btn-info btn-sm me-1"/>
			</div>
		</div>
		<p></p>
		<form name="myform">
			<table class="table table-bordered">
				<tr class="table-secondary">
					<th><input type="checkbox" id="check" name="check" onclick="checkClick()" /></th>
					<th>구분</th>
					<th>신청자 아이디</th>
					<th>신청자 닉네임</th>
					<th>신청내용</th>
					<th>신청일</th>
					<th>신청현황</th>
				</tr>
				<c:set var="cnt" value="0" />
				<c:forEach var="vo" items="${shopVOS}">
					<tr>
						<td><input type="checkbox" id="idxFlag${cnt}" name="idxFlag" /></td>
						<td>상품</td>
						<td>${vo.mid}</td>
						<td>${vo.nickName}</td>
						<td>${vo.title}</td>
						<td>${vo.shopDate}</td>
						<td>${vo.openSW}</td>
					</tr>
					<c:set var="cnt" value="${cnt+1}" />
				</c:forEach>
				<c:forEach var="vo" items="${subVOS}">
					<tr>
						<td><input type="checkbox" id="idxFlag${cnt}" name="idxFlag" /></td>
						<td>일반</td>
						<td>${vo.mid}</td>
						<td>${vo.nickName}</td>
						<td>${vo.subContent}</td>
						<td>${vo.subDate}</td>
						<td>${vo.subProgress}</td>
					</tr>
					<c:set var="cnt" value="${cnt+1}" />
				</c:forEach>
			</table>
		</form>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>