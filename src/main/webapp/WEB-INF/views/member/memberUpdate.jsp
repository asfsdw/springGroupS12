<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="${ctp}/js/postCode.js"></script>
	<script src="${ctp}/js/signUp.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/member.css" />
	<title></title>
</head>
<body>
	<p><br/></p>
	<div class="container">
		<form name="myform" method="post" enctype="multipart/form-data" class="was-validated">
			<h2 class="text-center">회 원 가 입</h2>
			<br/>
			<div class="input-group mb-3">
				<label for="mid" class="input-group-text boxWidth">아이디</label>
				<input type="text" class="form-control" name="mid" id="mid" value="${vo.mid}" readonly />
			</div>
			<div class="input-group mb-3">
				<label for="nickName" class="input-group-text boxWidth">닉네임</label>
				<input type="text" name="nickName" id="nickName" value="${vo.nickName}" class="form-control" required />
				<input type="button" id="nickNameBtn" value="닉네임 중복체크" class="btn btn-secondary btn-sm" onclick="nickCheck()"/>
			</div>
			<div class="input-group mb-3">
				<label for="name" class="input-group-text boxWidth">성 명</label>
				<input type="text" name="name" id="name" value="${vo.name}" class="form-control" required />
			</div>
			<div class="input-group mb-3">
				<label for="birthday" class="input-group-text boxWidth">나 이</label>
				<input type="number" name="age" id="age" value="${vo.age}" min="20" max="100" class="form-control" />
			</div>
			<div class="input-group mb-3">
				<c:set var="email" value="${fn:split(vo.email, '@')}" />
				<label for="email" class="input-group-text boxWidth">이메일</label>
				<input type="text" name="email1" id="email1" value="${email[0]}" required class="form-control" />
				<span class="input-group-text">@</span>
				<select name="email2" id="email2" class="form-select">
					<option value="naver.com" ${email[1]=='naver.com' ? 'selected' : ''}>naver.com</option>
					<option value="gmail.com" ${email[1]=='gmail.com' ? 'selected' : ''}>gmail.com</option>
					<option value="hanmail.net" ${email[1]=='daum.net' ? 'selected' : ''}>hanmail.net</option>
				</select>
			</div>
			<div class="input-group mb-3 col" >
				<c:set var="address" value="${fn:split(vo.address, '/')}" />
				<label for="address" class="input-group-text boxWidth">주 소</label>
				<input type="text" name="postcode" id="sample6_postcode" value="${fn:trim(address[0])}" placeholder="우편번호" class="form-control">
				<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary btn-sm">
			</div>
			<div class="input-group mb-3">
				<input type="text" name="roadAddress" id="sample6_address" size="50" value="${fn:trim(address[1])}" placeholder="주소" class="form-control mb-1">
			</div>
			<div class="input-group mb-3">
				<input type="text" name="detailAddress" id="sample6_detailAddress" value="${fn:trim(address[2])}" placeholder="상세주소" class="form-control me-2">
				<input type="text" name="extraAddress" id="sample6_extraAddress" value="${fn:trim(address[3])}" placeholder="참고항목" class="form-control">
			</div>
			<div class="input-group mb-3">
				<label for="content" class="input-group-text boxWidth">자기소개</label>
				<textarea rows="6" name="content" id="content" placeholder="자기소개를 입력해주세요." class="form-control">${vo.content}</textarea>
			</div>
			<div class="input-group mb-3 border ">
				<label for="content" class="input-group-text boxWidth">정보공개여부</label>&nbsp;&nbsp;
				<div class="form-check-inline mt-2">
					<label class="form-check-label">
						<input type="radio" class="form-check-input" name="userInfo" value="공개" ${vo.userInfo=='공개'?'checked':''}> 공개&nbsp;
					</label>
				</div>
				<div class="form-check-inline mt-2">
					<label class="form-check-label">
						<input type="radio" class="form-check-input" name="userInfo" value="비공개" ${vo.userInfo=='비공개'?'checked':''}> 비공개
					</label>
				</div>
			</div>
			<div class="input-group mb-3">
				<label for="photo" class="input-group-text boxWidth">프로필 사진</label>
				<input type="file" name="fName" id="file" class="form-control" />
			</div>
			<div class="input-group mb-3">
				<label for="photo" class="input-group-text boxWidth">이전 프로필 사진</label>
				<div class="d-flex" style="flex-grow: 1; justify-content: flex-end;">
					<img src="${ctp}/member/${vo.myImage}" style="width:200px" />
				</div>
			</div>
			<hr/>
			<div class="text-center">
				<button type="button" class="btn btn-success" onclick="updateCheck()">회원정보수정</button>&nbsp;
				<button type="button" class="btn btn-warning" onclick="location.reload()">다시작성</button>&nbsp;
				<button type="button" class="btn btn-info" onclick="location.href='${ctp}/member/Main'">돌아가기</button>&nbsp;
			</div>
			<input type="hidden" name="email" id="email" value="" />
			<input type="hidden" name="address" id="address" value="" />
			<input type="hidden" name="myImage" id="myImage" value="${vo.myImage}" />
		</form>
	</div>
	<p><br/></p>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>