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
	<link type="text/css" rel="stylesheet" href="${ctp}/css/linkOrange.css">
	<script src="${ctp}/js/shop.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/shop.css" />
	<title></title>
</head>
<body>
	<div class="container text-center">
		<br/>
		<div class="col text-start">
			<c:if test="${sLevel < 2}">
				<input type="button" value="상품등록" onclick="location.href='${ctp}/shop/ProductAdd'" class="btn btn-success btn-sm" />
				<p></p>
			</c:if>
			<c:if test="${sLevel == 2}">
				<input type="button" value="상품등록신청" onclick="location.href='${ctp}/shop/ProductAddSub'" class="btn btn-success btn-sm" />
				<p></p>
			</c:if>
		</div>
		<div class="col input-group d-flex jusify-content-start">
			<div class="input-group-text">카테고리 선택</div>
			<select id="kategorie" name="kategorie" onchange="kategorieChange('${ctp}')" class="form-select" style="width:300px; flex:0 0 auto;">
				<option>선택</option>
				<option ${kategorie=='누이'?'selected':''}>누이</option>
				<option ${kategorie=='퍼펫'?'selected':''}>퍼펫</option>
				<option ${kategorie=='아크릴스탠드'?'selected':''}>아크릴스탠드</option>
				<option ${kategorie=='의류'?'selected':''}>의류</option>
				<option ${kategorie=='기타'?'selected':''}>기타</option>
				<option ${kategorie=='통합'?'selected':''}>통합</option>
			</select>
		</div>
		<hr/>
		<div class="row">
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<div class="col" style="height:280px">
					<a href="${ctp}/shop/Product?idx=${vo.idx}&mid=${sMid}" style="display:inline-block">
						<div><img src="${ctp}/data/shop/${vo.productImage}" style="width:200px" /></div>
						<div>${vo.title}</div>
						<c:if test="${vo.quantity > 0}">
							<div>가격: <fmt:formatNumber value="${vo.price}" />원 / 재고: ${vo.quantity}개</div>
						</c:if>
						<c:if test="${vo.quantity < 1}">
							<div>가격: <fmt:formatNumber value="${vo.price}" />원 / 재고: <font color="red">없음</font></div>
						</c:if>
					</a>
				</div>
			</c:forEach>
		</div>
		<!-- 블록페이지 시작 -->
		<div class="input-group justify-content-center">
			<div class="pagination">
				<c:if test="${pVO.pag > 1}"><a href="${ctp}/shop/Goods?pag=1&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">첫 페이지</a></c:if>
				<c:if test="${pVO.curBlock > 0}">
					<a href="${ctp}/shop/Goods?pag=${(pVO.curBlock - 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">이전 블록</a>
				</c:if>
				<c:forEach var="i" begin="${(pVO.curBlock * pVO.blockSize) + 1}" end="${(pVO.curBlock * pVO.blockSize) + pVO.blockSize}" varStatus="st">
					<c:if test="${i <= pVO.totPage && i == pVO.pag}">
						<span class="page-item active page-link bg-secondary border-secondary">${i}</span>
					</c:if>
					<c:if test="${i <= pVO.totPage && i != pVO.pag}">
						<a href="${ctp}/shop/Goods?pag=${i}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">${i}</a>
					</c:if>
				</c:forEach>
				<c:if test="${pVO.curBlock < pVO.lastBlock}">
					<a href="${ctp}/shop/Goods?pag=${(pVO.curBlock + 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">다음 블록</a>
				</c:if>
				<c:if test="${pVO.pag < pVO.totPage}">
					<a href="${ctp}/shop/Goods?pag=${pVO.totPage}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">마지막 페이지</a>
				</c:if>
			</div>
		</div>
		<p></p>
		<!-- 블록페이지 끝 -->
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>