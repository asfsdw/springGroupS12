<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script src="${ctp}/js/login.js"></script>
  <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
  <title>memberLogin.jsp</title>
  <script>
		window.Kakao.init("6bf1408a4cfa868792077ab97ebbbff1");
		function kakaoLogin() {
			window.Kakao.Auth.login({
				scope : "profile_nickname, account_email, age_range",
				success : () => {
					window.Kakao.API.request({
						url : "/v2/user/me",
						success : (res) => {
							const kakao_account = res.kakao_account;
							location.href = "${ctp}/member/KakaoLogin?nickName="+kakao_account.profile.nickname+"&email="+kakao_account.email+"&ageRange="+kakao_account.age_range+"&accessToken="+Kakao.Auth.getAccessToken();
						}
					});
				}
			});
		}
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
	<p><br/></p>
	<div class="container">
		<form name="myform" method="post">
			<table class="table table-bordered text-center">
				<tr>
					<td colspan="2" class="bg-secondary-subtle"><font size="5">로 그 인</font></td>
				</tr>
				<tr>
					<th class="align-middle">아이디</th>
					<td><input type="text" name="mid" id="mid" value="${mid}" autofocus required class="form-control"/></td>
				</tr>
				<tr>
					<th class="align-middle">비밀번호</th>
					<td><input type="password" name="pwd" id="pwd" value="1234" required class="form-control"/></td>
				</tr>
				<tr>
					<td colspan="2">
					<div class="mb-2">
						<input type="submit" value="로그인" class="btn btn-success me-2"/>
						<input type="reset" value="다시입력" class="btn btn-warning me-2"/>
						<input type="button" value="회원가입" onclick="location.href='${ctp}/member/SignUp';" class="btn btn-secondary me-2" />
						<a href="javascript:kakaoLogin()"><img src="${ctp}/images/kakaoLogin.png" width="145px"/></a>
					</div>
					<div style="font-size:0.8em">
						<input type="checkbox" name="idSave" checked /> 아이디저장 /
						<a id="idFind" class="text-decoration-none text-dark link-primary">아이디찾기</a> /
						<a id="pwdFind" class="text-decoration-none text-dark link-primary">비밀번호찾기</a>
					</div>
				</td>
				</tr>
			</table>
		</form>
	</div>
	<p><br/></p>
	<!-- The Modal -->
	<div class="modal fade" id="myModal">
		<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 id="modal-title" class="modal-title"></h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<!-- Modal body -->
				<div class="modal-body">
					<div id="modal-body"></div>
					<div id="modal-body2"></div>
					<div id="modal-body3"></div>
				</div>
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>