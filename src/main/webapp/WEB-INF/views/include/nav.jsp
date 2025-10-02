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
		<c:if test="${sLevel < 4}">
			<a href="${ctp}/member/Main" class="w3-bar-item w3-button w3-padding-large">내정보</a>
		</c:if>
		<a href="${ctp}/board/BoardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">게시판</a>
		<c:if test="${sLevel < 3}">
			<a href="${ctp}/pds/PDSList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">자료실</a>
			
			<div class="w3-dropdown-hover w3-hide-small">
				<button class="w3-padding-large w3-button" title="More">Study1 <i class="fa fa-caret-down"></i></button>     
				<div class="w3-dropdown-content w3-bar-block w3-card-4">
					<a href="${ctp}/study1/0901/Test1" class="w3-bar-item w3-button">컨트롤러 연습</a>
					<a href="${ctp}/study1/mapping/Menu" class="w3-bar-item w3-button">Mapping 연습</a>
					<a href="${ctp}/study1/aop/AOPMenu" class="w3-bar-item w3-button">AOP 연습</a>
					<a href="${ctp}/study1/xml/XMLMenu" class="w3-bar-item w3-button">XML 값 주입 연습</a>
					<a href="${ctp}/dbTest/UserList" class="w3-bar-item w3-button">DateBase 연습</a>
					<a href="${ctp}/dbTest2/UserList" class="w3-bar-item w3-button">DateBase 연습2</a>
					<%-- <a href="${ctp}/dbTest2/UserSearch" class="w3-bar-item w3-button">DateBase 연습3</a> --%>
					<a href="${ctp}/study1/restAPI/RESTAPIForm" class="w3-bar-item w3-button">REST API 연습</a>
					<a href="${ctp}/study1/ajax/AJAXForm" class="w3-bar-item w3-button">AJAX 연습</a>
					<a href="${ctp}/study1/password/PasswordForm" class="w3-bar-item w3-button">SSC 연습</a>
					<a href="${ctp}/study1/mail/MailForm" class="w3-bar-item w3-button">메일 인증 연습</a>
					<a href="${ctp}/study1/fileUpload/FileUploadForm" class="w3-bar-item w3-button">파일 업로드 연습</a>
					<a href="${ctp}/study1/sweetAlert/SweetAlertForm" class="w3-bar-item w3-button">스윗 얼럿 연습</a>
				</div>
			</div>
		</c:if>
		<div class="w3-dropdown-hover w3-hide-small">
			<c:if test="${!empty sLevel}">
				<button class="w3-padding-large w3-button" title="More">MyPage <i class="fa fa-caret-down"></i></button>     
				<div class="w3-dropdown-content w3-bar-block w3-card-4">
					<a href="${ctp}/member/MemberList" class="w3-bar-item w3-button">회원 리스트</a>
					<a href="${ctp}/member/MemberPwdCheck/u" class="w3-bar-item w3-button">회원정보 수정</a>
					<a href="${ctp}/member/MemberPwdCheck/p" class="w3-bar-item w3-button">비밀번호 변경</a>
					<a href="javascript:userDeleteCheck()" class="w3-bar-item w3-button">회원탈퇴</a>
					<c:if test="${sLevel < 3}">
						<a href="#" class="w3-bar-item w3-button">일정관리</a>
						<a href="#" class="w3-bar-item w3-button">웹메시지</a>
						<a href="#" class="w3-bar-item w3-button">사진첩</a>
					</c:if>
					<c:if test="${sLevel == 0}">
						<a href="${ctp}/admin/AdminMain" class="w3-bar-item w3-button">관리자 메뉴</a>
					</c:if>
				</div>
			</c:if>
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
	<a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">방명록</a>
	<a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">게시판</a>
	<a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">자료실</a>
	<a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">로그인</a>
	<a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">회원가입</a>
</div>