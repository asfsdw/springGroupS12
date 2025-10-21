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
	<link type="text/css" rel="stylesheet" href="${ctp}/css/linkOrange.css" />
	<title></title>
	<script>
		'use strict';
		
		$(window).scroll(function() {
			if($(this).scrollTop() > 100) $("#topBtn").addClass("on");
			else $("#topBtn").removeClass("on");
			
			$("#topBtn").click(function() {
				window.scrollTo({top:0, behavior: "smooth"});
			});
		});
	</script>
	<style>
		h6 {
			position: fixed;
			right: 1rem;
			bottom: -50px;
			transition: 0.7s ease;
			z-index: 2;
		}
		.on {
			opacity: 0.8;
			cursor: pointer;
			bottom: 0;
		}
	</style>
</head>
<body>
	<div class="container text-center">
	<p><br/></p>
	<p class="border border-secondary pt-2 pb-2" style="background-color:#DDD;">
		<font size=3><b><a href="${ctp}/shop/Goods">굿즈</a></b></font>
	</p>
	<div class="w3-row">
		<c:forEach var="vo" items="${sVOS}">
			<div class="w3-col l3 s6" style="height:280px">
				<a href="${ctp}/shop/Product?idx=${vo.idx}&mid=${sMid}" style="display:inline-block">
					<div class="w3-container text-start">
						<img src="${ctp}/data/shop/${vo.productImage}" style="width:200px" />
						<p>${vo.title}<br>가격: <b><fmt:formatNumber value="${vo.price}" />원</b> / 재고: ${vo.quantity}개</p>
					</div>
				</a>
			</div>
		</c:forEach>
	</div>
	<p class="border border-secondary pt-2 pb-2" style="background-color:#DDD;">
		<font size=3><b><a href="${ctp}/board/BoardList">새글</a></b></font>
	</p>
	<div class="row">
		<div class="col">
			<table class="table table-bordered">
				<tr class="table-secondary">
					<th>글제목</th>
					<th>댓글</th>
					<th>조회수</th>
				</tr>
					<c:forEach var="vo" items="${bVOS}">
					<tr>
						<td><a href="${ctp}/board/BoardContent?idx=${vo.idx}">${vo.title}</a></td>
						<td>${vo.replyCnt}</td>
						<td>${vo.views}</td>
					</tr>
					</c:forEach>
			</table>
		</div>
	</div>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>