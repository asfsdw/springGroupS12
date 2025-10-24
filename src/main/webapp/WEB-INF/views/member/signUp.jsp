<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="${ctp}/js/postCode.js"></script>
	<script src="${ctp}/js/signUp.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/member.css" />
	<title></title>
</head>
<body>
	<div class="container text-center">
		<form name="myform" method="post" enctype="multipart/form-data" class="was-validated">
			<h2 class="text-center">회 원 가 입</h2>
			<br/>
			<div class="input-group mb-3">
				<label for="mid" class="input-group-text boxWidth">아이디</label>
				<input type="text" id="mid" name="mid" placeholder="아이디를 입력하세요." autofocus required class="form-control" />
				<input type="button" id="midBtn" value="아이디 중복체크" onclick="idCheck()" class="btn btn-secondary btn-sm" />
			</div>
			<div class="input-group mb-3">
				<label for="pwd" class="input-group-text boxWidth">비밀번호</label>
				<input type="password" id="pwd" name="pwd" placeholder="비밀번호를 입력하세요." required class="form-control" />
			</div>
			<div class="input-group mb-3">
				<label for="nickName" class="input-group-text boxWidth">닉네임</label>
				<input type="text" id="nickName" name="nickName" placeholder="닉네임을 입력하세요." required class="form-control" />
				<input type="button" id="nickNameBtn" value="닉네임 중복체크" onclick="nickNameCheck()" class="btn btn-secondary btn-sm" />
			</div>
			<div class="input-group mb-3">
				<label for="name" class="input-group-text boxWidth">성 명</label>
				<input type="text" id="name" name="name" placeholder="성명을 입력하세요." required class="form-control" />
			</div>
			<div class="input-group mb-3">
				<label for="birthday" class="input-group-text boxWidth">생년월일</label>
				<input type="date" id="birthday" name="birthday" required class="form-control" />
			</div>
			<div class="input-group mb-3">
				<label for="email" class="input-group-text boxWidth">이메일</label>
				<input type="text" id="email1" name="email1" placeholder="이메일을 입력하세요." required class="form-control" />
				<span class="input-group-text">@</span>
				<select id="email2" name="email2" class="form-select">
					<option>gmail.com</option>
					<option>naver.com</option>
					<option>daum.net</option>
				</select>
				<input type="button" id="emailCheckBtn" value="이메일 중복체크" onclick="emailCheck()" class="btn btn-secondary btn-sm" />
				<input type="button" id="certificationBtn" value="인증번호받기" onclick="emailCertification(${year})" class="btn btn-success btn-sm" style="display:none" />
			</div>
			<div id="demoSpin"></div>
			<div id="addContent" style="display:none">
				<div class="input-group mb-3 col" >
					<label for="address" class="input-group-text boxWidth">주 소</label>
					<input type="text" id="sample6_postcode" name="postcode" placeholder="우편번호" class="form-control">
					<input type="button" value="우편번호 찾기" onclick="sample6_execDaumPostcode()" class="btn btn-secondary btn-sm">
				</div>
				<div class="input-group mb-3">
					<input type="text" id="sample6_address" name="roadAddress" size="50" placeholder="주소" class="form-control mb-1" />
				</div>
				<div class="input-group mb-3">
					<input type="text" id="sample6_detailAddress" name="detailAddress" placeholder="상세주소" class="form-control me-2" />
					<input type="text" id="sample6_extraAddress" name="extraAddress" placeholder="참고항목" class="form-control" />
				</div>
				<div class="input-group mb-3">
					<label for="content" class="input-group-text boxWidth">자기소개</label>
					<textarea rows="6" id="content" name="content" placeholder="자기소개를 입력해주세요." class="form-control"></textarea>
				</div>
				<div class="input-group mb-3 border ">
					<label for="content" class="input-group-text boxWidth">정보공개여부</label>&nbsp;&nbsp;
					<div class="form-check-inline mt-2">
						<label class="form-check-label">
							<input type="radio" name="userInfo" value="공개" checked class="form-check-input" /> 공개&nbsp;
						</label>
					</div>
					<div class="form-check-inline mt-2">
						<label class="form-check-label">
							<input type="radio" name="userInfo" value="비공개" class="form-check-input" /> 비공개
						</label>
					</div>
				</div>
				<div class="input-group mb-3">
					<label for="photo" class="input-group-text boxWidth">프로필 사진</label>
					<input type="file" id="file" name="fName" class="form-control" />
				</div>
			</div>
			<hr/>
			<div class="text-center">
				<button type="button" id="fCheck" onclick="fCheck(${year})" disabled class="btn btn-success">회원가입</button>&nbsp;
				<button type="button" onclick="location.reload()" class="btn btn-warning" >다시작성</button>&nbsp;
				<button type="button" onclick="location.href='${ctp}/member/Login'" class="btn btn-info" >돌아가기</button>&nbsp;
			</div>
			<input type="hidden" id="age" name="age" value="" />
			<input type="hidden" id="email" name="email" value="" />
			<input type="hidden" id="address" name="address" value="" />
		</form>
	</div>
	<br/>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>