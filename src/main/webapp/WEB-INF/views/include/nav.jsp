<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<script>
	function userDeleteCheck() {
		let ans = confirm("회원탈퇴를 하시겠습니까?");
		if(ans) {
			ans = confirm("탈퇴하시면 1개월간 같은 아이디, 닉네임, 이메일로는 가입하실 수 없습니다.\n그래도 탈퇴하시겠습니까?");
			if(ans) {
				$.ajax({
					url : "${ctp}/member/Delete",
					type: "post",
					success : (res) => {
						if(res != 0) {
							alert("회원탈퇴되었습니다.");
							location.href = "${ctp}/";
						}
						else alert("회원탈퇴에 실패했습니다.\n다시 시도해주세요.");
					},
					error : () => alert("전송오류")
				});
			}
		}
	}
</script>
<!-- Navbar -->
<div class="w3-top">
	<div class="w3-bar w3-black w3-card">
		<a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		<a href="http://localhost:9090/springGroupS12/" class="w3-bar-item w3-button w3-padding-large">HOME</a>
		<a href="${ctp}/member/Main" class="w3-bar-item w3-button w3-padding-large">내정보</a>
		<a href="${ctp}/board/BoardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">게시판</a>
		<a href="${ctp}/shop/Goods" class="w3-bar-item w3-button w3-padding-large w3-hide-small">굿즈</a>

		<div class="w3-dropdown-hover w3-hide-small">
			<button class="w3-padding-large w3-button" title="More">MyPage <i class="fa fa-caret-down"></i></button>     
			<div class="w3-dropdown-content w3-bar-block w3-card-4">
				<a href="${ctp}/member/SubScript" class="w3-bar-item w3-button">신청</a>
				<a href="${ctp}/member/MemberList" class="w3-bar-item w3-button">회원 리스트</a>
				<a href="${ctp}/member/MemberPwdCheck/u" class="w3-bar-item w3-button">회원정보 수정</a>
				<a href="${ctp}/member/MemberPwdCheck/p" class="w3-bar-item w3-button">비밀번호 변경</a>
				<a href="javascript:userDeleteCheck()" class="w3-bar-item w3-button">회원탈퇴</a>
				<a href="#" class="w3-bar-item w3-button">일정관리</a>
				<a href="#" class="w3-bar-item w3-button">웹메시지</a>
				<a href="#" class="w3-bar-item w3-button">사진첩</a>
				<a href="${ctp}/admin/AdminMain" class="w3-bar-item w3-button">관리자 메뉴</a>
			</div>
		</div>
		<c:if test="${empty sLevel}">
			<a href="${ctp}/member/Login" class="w3-bar-item w3-button w3-padding-large w3-hide-small">로그인</a>
			<a href="${ctp}/member/SignUp" class="w3-bar-item w3-button w3-padding-large w3-hide-small">회원가입</a>
		</c:if>
		<c:if test="${!empty sLevel}">
			<a href="${ctp}/member/Logout" class="w3-bar-item w3-button w3-padding-large w3-hide-small">로그아웃</a>
		</c:if>
		<a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a>
	</div>
</div>
<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-black w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
	<a href="${ctp}/board/BoardList" class="w3-bar-item w3-button w3-padding-large">게시판</a>
	<a href="${ctp}/shop/Goods" class="w3-bar-item w3-button w3-padding-large">굿즈</a>
	<c:if test="${empty sLevel}">
		<a href="${ctp}/member/Login" class="w3-bar-item w3-button w3-padding-large">로그인</a>
		<a href="${ctp}/member/SignUp" class="w3-bar-item w3-button w3-padding-large">회원가입</a>
	</c:if>
	<c:if test="${!empty sLevel}">
		<a href="${ctp}/member/Logout" class="w3-bar-item w3-button w3-padding-large">로그아웃</a>
	</c:if>
</div>