<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%pageContext.setAttribute("CRLF","\r\n");%>
<%pageContext.setAttribute("LF","\n");%>
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
	<script>
		$(() => {
			$("#orderQuantity").on("change", () => {
				let price = ${vo.price};
				let quantity = $("#orderQuantity").val();
				let totPrice = price * quantity;
				$("#tot").html(totPrice.toLocaleString()+"원");
				$("#price").val(totPrice);
			});
		});
	</script>
	<!-- 리뷰용 style -->
	<style>
		th {background-color: #eee !important;}
		
		/* right to left = 배열을 오른쪽에서 왼쪽으로 뒤집는다. */
		#reviewForm fieldset {direction: rtl;}
		
		/* 라디오버튼 감추기 */
		#reviewForm input[type=radio] {display: none;}
		
		/* 별의 크기, 색 변경 */
		#reviewForm label {
			font-size: 1.6em;
			color: transparent;
			text-shadow: 0 0 0 #f0f0f0;
		}
		
		/* 범위에 마우스 올리면 노란색으로 출력 */
		#reviewForm label:hover {text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);}
		
		/* 형제 선택자(~)를 이용해 hover시 같은 형제의 label을 이어서 노란색으로 출력 */
		#reviewForm label:hover ~ label {text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);}
		
		/* 클릭시 별점 고정 */
		#reviewForm input[type=radio]:checked ~ label {text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);}
	</style>
</head>
<body>
	<div class="container text-center">
		<p></p>
		<div class="row">
			<div class="col"><img src="${ctp}/data/shop/${vo.productImage}" style="width:500px" /></div>
			<div class="col text-start">
				<div><h2>${vo.title}</h2></div>
				<div><h3>가격: <fmt:formatNumber value="${vo.price}" />원</h3></div>
				<div><h3>재고: ${vo.quantity}개</h3></div>
				<div><h4>분류: ${vo.kategorie}</h4></div>
				<hr/>
				<div><h4>판매자: ${vo.nickName}</h4></div>
				<div><h5>판매횟수: ${vo.sold}회</h5></div>
				<hr/>
				<form name="productForm" method="post" action="${ctp}/shop/Product">
					<div class="input-group">
						<input type="number" value="1" min="1" max="10" <if test="${vo.quantity == 0}">disabled</if> id="orderQuantity" name="orderQuantity" />
						<div class="input-group-text">개</div>
					</div>
					<p></p>
					<div class="input-group">
						<div id="tot" class="input-group-text"><fmt:formatNumber value="${vo.price*1}" />원</div>
						<input type="button" value="구매" onclick="soldCheck()" class="btn btn-success" />
					</div>
					<input type="hidden" name="mid" value="${sMid}" />
					<input type="hidden" name="idx" value="${vo.idx}" />
					<input type="hidden" name="title" value="${vo.title}" />
					<input type="hidden" id="price" name="price" value="${vo.price}" />
					<input type="hidden" name="productImage" value="${vo.productImage}" />
				</form>
				<p></p>
				<c:if test="${sLevel < 5}">
					<input type="button" value="장바구니에 담기" onclick="addShoppingBag('${vo.idx}','${sMid}','${sNickName}')" class="btn btn-primary me-2" />
				</c:if>
				<input type="button" value="굿즈일람" onclick="location.href='${ctp}/shop/Goods'" class="btn btn-warning" />
			</div>
		</div>
		<hr/>
		<div class="text-start"><h2>상품설명</h2></div>
		<p></p>
		<div id="content" class="text-start">${vo.content}</div>
		<p></p>
		<div class="row">
			<div class="col">
			</div>
			<div class="col">
				<input type="button" value="굿즈일람" onclick="location.href='${ctp}/shop/Goods'" class="btn btn-warning" />
			</div>
			<div class="col">
				<c:if test="${(vo.mid == sMid || sLevel == 0) && vo.complaint != 'OK'}">
					<input type="button" value="수정" onclick="location.href='${ctp}/shop/ProductUpdate?idx=${vo.idx}'" class="btn btn-info me-1" />
				</c:if>
				<c:if test="${!empty sMid && vo.complaint != 'OK'}">
					<input type="button" value="신고" data-bs-toggle="modal" data-bs-target="#myModal" class="btn btn-danger" />
				</c:if>
				<c:if test="${vo.complaint == 'OK'}">
					<input type="button" value="신고중" disabled class="btn btn-outline-danger" />
				</c:if>
			</div>
		</div>
	</div>
	<p></p>
	<!-- 별점 및 리뷰 -->
	<c:if test="${reviewSW != 'on' || sLevel >= 4}">
		<div class="text-center"><h3>리뷰는 상품을 구입한 <font color="blue">정회원</font> 이상만 작성할 수 있습니다.</h3></div>
	</c:if>
	<c:if test="${reviewSW == 'on' && sLevel < 4}">
	<div class="ps-5" style="width:94%">
		<form name="reviewForm" id="reviewForm">
			<fieldset style="border:0px;">
				<div class="text-start m-0 b-0">
					<input type="radio" name="star" value="5" id="star1"><label for="star1">★</label>
					<input type="radio" name="star" value="4" id="star2"><label for="star2">★</label>
					<input type="radio" name="star" value="3" id="star3"><label for="star3">★</label>
					<input type="radio" name="star" value="2" id="star4"><label for="star4">★</label>
					<input type="radio" name="star" value="1" id="star5"><label for="star5">★</label>
					: 별점을 선택해 주세요 ■
				</div>
			</fieldset>
			<div class="m-0 p-0">
				<textarea rows="3" name="review" id="review" class="form-control mb-1" placeholder="리뷰를 남겨주시면 100포인트를 지급합니다."></textarea>
			</div>
			<div>
				<input type="button" value="별점/리뷰등록" onclick="reviewCheck('${ctp}','${vo.idx}','${sMid}','${sNickName}','${pageContext.request.remoteAddr}')" class="btn btn-primary btn-sm form-control" />
			</div>
		</form>
	</div>
	</c:if>
	<p></p>
	<div class="row ps-5" style="width:94%">
		<div class="col">
			<input type="button" value="리뷰보이기" id="reviewShowBtn" onclick="reviewShow()" class="btn btn-info" style="display:none" />
			<input type="button" value="리뷰가리기" id="reviewHideBtn" onclick="reviewHide()" class="btn btn-secondary"/>
		</div>
		<div class="col text-end">
			<c:if test="${!empty reviewAVG}">
				<b>리뷰평점 : <fmt:formatNumber value="${reviewAVG}" pattern="#,##0.0" /></b>
			</c:if>
			<c:if test="${empty reviewAVG}">
				<b>등록된 리뷰가 없습니다</b>
			</c:if>
		</div>
	</div>
	<div class="ps-5" style="width:94%">
		<hr/>
	</div>
	<div id="reviewBox" class="ps-5" style="width:94%">
		<c:set var="imsiIdx" value="0" />
		<c:forEach var="vo" items="${rVOS}">
			<c:if test="${imsiIdx != vo.idx}">
				<div class="row mt-3" style="width:94%">
					<div class="col ms-2 text-start">
						<b>${vo.nickName}</b>
						<span style="font-size:11px">${fn:substring(vo.replyDate, 0, 10)}</span>
						<c:if test="${vo.mid == sMid || sLevel == 0}">
							<a href="javascript:reviewDelete('${ctp}','${vo.idx}')" title="리뷰삭제" class="text-decoration-none">🗑</a>
						</c:if>
					</div>
					<div class="col text-end me-2">
						<c:forEach var="i" begin="1" end="${vo.star}">
							<font color="gold">★</font>
						</c:forEach>
						<c:forEach var="i" begin="1" end="${5 - vo.star}">☆</c:forEach>
					</div>
				</div>
				<div class="row border m-1 p-2" style="border-radius:5px">
				${fn:replace(fn:replace(vo.content,CRLF,'<br/>'),LF,'<br/>')}
				</div>
			</c:if>
			<c:set var="imsiIdx" value="${vo.idx}" />
		</c:forEach>
	</div>
	<!-- 신고 -->
	<div class="modal fade" id="myModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">현재 상품을 신고합니다.</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<b>신고사유 선택</b>
					<hr class="border border-secondary">
					<form name="modalForm">
						<div><input type="radio" name="complaint" id="complaint1" value="광고,홍보,영리목적"/> 광고,홍보,영리목적</div>
						<div><input type="radio" name="complaint" id="complaint2" value="욕설,비방,차별,혐오"/> 욕설,비방,차별,혐오</div>
						<div><input type="radio" name="complaint" id="complaint3" value="불법정보"/> 불법정보</div>
						<div><input type="radio" name="complaint" id="complaint4" value="음란,청소년유해"/> 음란,청소년유해</div>
						<div><input type="radio" name="complaint" id="complaint5" value="개인정보노출,유포,거래"/> 개인정보노출,유포,거래</div>
						<div><input type="radio" name="complaint" id="complaint6" value="도배,스팸"/> 도배,스팸</div>
						<div><input type="radio" name="complaint" value="기타" onclick="etcShow()"/> 기타</div>
						<div id="etc"><textarea rows="2" id="etcTxt" class="form-control" style="display:none"></textarea></div>
						<hr class="border border-secondary">
						<input type="button" value="신고하기" onclick="complaintCheck('${vo.idx}','${sMid}','${vo.title}')" class="btn btn-success form-control" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 신고 끝 -->
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>