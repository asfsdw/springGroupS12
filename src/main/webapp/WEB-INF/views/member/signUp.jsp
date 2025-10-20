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
	<title>Sign Up</title>
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
		
		label {width:100px;}
	</style>
</head>
<body>
	<div class="container text-center">
		<form name="myform" method="post" enctype="multipart/form-data" class="was-validated">
			<h2 class="text-center">회 원 가 입</h2>
			<br/>
			<div class="input-group mb-3">
				<label for="mid" class="input-group-text boxWidth">아이디</label>
				<input type="text" class="form-control" name="mid" id="mid" placeholder="아이디를 입력하세요." required autofocus/>
				<input type="button" value="아이디 중복체크" id="midBtn" class="btn btn-secondary btn-sm" onclick="idCheck()"/>
			</div>
			<div class="input-group mb-3">
				<label for="pwd" class="input-group-text boxWidth">비밀번호</label>
				<input type="password" name="pwd" id="pwd" placeholder="비밀번호를 입력하세요." class="form-control" required />
			</div>
			<div class="input-group mb-3">
				<label for="nickName" class="input-group-text boxWidth">닉네임</label>
				<input type="text" name="nickName" id="nickName" placeholder="닉네임을 입력하세요." class="form-control" required />
				<input type="button" id="nickNameBtn" value="닉네임 중복체크" class="btn btn-secondary btn-sm" onclick="nickNameCheck()"/>
			</div>
			<div class="input-group mb-3">
				<label for="name" class="input-group-text boxWidth">성 명</label>
				<input type="text" name="name" id="name" placeholder="성명을 입력하세요." class="form-control" required />
			</div>
			<div class="input-group mb-3">
				<label for="birthday" class="input-group-text boxWidth">생년월일</label>
				<input type="date" name="birthday" id="birthday" class="form-control" required />
			</div>
			<div class="input-group mb-3">
				<label for="email" class="input-group-text boxWidth">이메일</label>
				<input type="text" name="email1" id="email1" placeholder="이메일을 입력하세요." required class="form-control" />
				<span class="input-group-text">@</span>
				<select name="email2" id="email2" class="form-select">
					<option>naver.com</option>
					<option selected>gmail.com</option>
					<option>daum.net</option>
				</select>
				<input type="button" value="이메일중복확인" onclick="emailCheck()" id="emailCheckBtn" class="btn btn-success" />
				<input type="button" value="인증번호받기" onclick="emailCertification(${year})" id="certificationBtn" class="btn btn-success" style="display:none" />
			</div>
			<div id="demoSpin"></div>
			<div id="addContent" style="display:none">
				<div class="input-group mb-3 col" >
					<label for="address" class="input-group-text boxWidth">주 소</label>
					<input type="text" name="postcode" id="sample6_postcode" placeholder="우편번호" class="form-control">
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary btn-sm">
				</div>
				<div class="input-group mb-3">
					<input type="text" name="roadAddress" id="sample6_address" size="50" placeholder="주소" class="form-control mb-1">
				</div>
				<div class="input-group mb-3">
					<input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소" class="form-control me-2">
					<input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목" class="form-control">
				</div>
				<div class="input-group mb-3">
					<label for="content" class="input-group-text boxWidth">자기소개</label>
					<textarea rows="6" name="content" id="content" placeholder="자기소개를 입력해주세요." class="form-control"></textarea>
				</div>
				<div class="input-group mb-3 border ">
					<label for="content" class="input-group-text boxWidth">정보공개여부</label>&nbsp;&nbsp;
					<div class="form-check-inline mt-2">
						<label class="form-check-label">
							<input type="radio" class="form-check-input" name="userInfo" value="공개" checked> 공개&nbsp;
						</label>
					</div>
					<div class="form-check-inline mt-2">
						<label class="form-check-label">
							<input type="radio" class="form-check-input" name="userInfo" value="비공개" > 비공개
						</label>
					</div>
				</div>
				<div class="input-group mb-3">
					<label for="photo" class="input-group-text boxWidth">프로필 사진</label>
					<input type="file" name="fName" id="file" class="form-control" />
				</div>
			</div>
			<hr/>
			<div class="text-center">
				<button type="button" class="btn btn-success" onclick="fCheck(${year})">회원가입</button>&nbsp;
				<button type="button" class="btn btn-warning" onclick="location.reload()">다시작성</button>&nbsp;
				<button type="button" class="btn btn-info" onclick="location.href = '${ctp}/member/Login'">돌아가기</button>&nbsp;
			</div>
			<input type="hidden" name="age" id="age" value="" />
			<input type="hidden" name="email" id="email" value="" />
			<input type="hidden" name="address" id="address" value="" />
		</form>
	</div>
	<br/>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>