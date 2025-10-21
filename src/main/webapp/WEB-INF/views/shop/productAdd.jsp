<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<script src="${ctp}/resources/ckeditor/ckeditor.js"></script>
	<script src="${ctp}/js/shop.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/shop.css" />
	<title></title>
</head>
<body>
	<div class="container text-center">
		<h2></h2>
		<h4>상품등록</h4>
		<hr/>
		<form name="productAddForm" method="post" enctype="multipart/form-data" class="was-validated">
			<table class="table table-bordered">
				<tr>
					<th>등록자</th>
					<td><input type="text" value="${sNickName}" id="nickName" name="nickName" readonly class="form-control" /></td>
					<th>상품명</th>
					<td><input type="text" id="title" name="title" placeholder="상품의 이름을 입력해주세요." required class="form-control" /></td>
					<th>상품대표이미지</th>
					<td><input type="file" id="fName" name="fName" accept=".jpg,.jpge,.png,.gif" required class="form-control" /></td>
				</tr>
				<tr>
					<th>상품분류</th>
					<td>
						<select id="kategorie" name="kategorie" class="form-control">
							<option>선택</option>
							<option>누이</option>
							<option>퍼펫</option>
							<option>아크릴스탠드</option>
							<option>의류</option>
							<option>기타</option>
							<option>통합</option>
						</select>
					</td>
					<th>상품가격</th>
					<td><input type="number" id="price" name="price" min="0" step="100" placeholder="상품의 가격을 입력해주세요." required class="form-control" /></td>
					<th>상품수량</th>
					<td><input type="number" id="quantity" name="quantity" min="0" placeholder="재고 개수를 입력해주세요." required class="form-control" /></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td colspan="5"><input type="password" id="pwd" name="pwd" required class="form-control" /></td>
				</tr>
				<tr>
					<th>상품설명</th>
					<td colspan="5">
						<textarea rows="6" id="CKEDITOR" name="content" placeholder="내용을 입력해주세요." required class="form-control"></textarea>
						<script>
							CKEDITOR.replace("content", {
								height : 500,
								filebrowserUploadUrl : "${ctp}/ImageUpload",
								uploadUrl : "${ctp}/ImageUpload"
							});
						</script>
					</td>
				</tr>
			</table>
			<input type="hidden" value="${sMid}" id="mid" name="mid" />
		</form>
		<c:if test="${sLevel == 1}">
			<input type="button" value="상품등록" onclick="fCheck()" class="btn btn-success btn-sm me-1" />
		</c:if>
		<c:if test="${sLevel == 2}">
			<input type="button" value="상품등록신청" onclick="subCheck('${ctp}')" class="btn btn-success btn-sm me-1" />
		</c:if>
		<input type="button" value="돌아가기" onclick="location.href = '${ctp}/shop/Goods'" class="btn btn-warning btn-sm" />
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>