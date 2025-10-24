<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<script src="${ctp}/js/member.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/member.css" />
	<title></title>
</head>
<body>
	<div class="container text-center">
		<hr/>
		<form name="myform" id="myform" method="post">
			<table class="table">
				<tr class="table-secondary">
					<th colspan="2">
						<h3>비밀번호 확인</h3>
						<div>(비밀번호를 확인합니다.)</div>
					</th>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pwd" id="pwd" autofocus required class="form-control" /></td>
				</tr>
				<tr>
					<th colspan="2">
						<input type="button" value="비밀번호확인" onclick="pwdCheck('${ctp}','${sMid}','${flag}')" class="btn btn-success me-2" />
						<input type="reset" value="다시입력" class="btn btn-secondary me-2" />
						<input type="button" value="돌아가기" onclick="location.href='${ctp}/member/Main'" class="btn btn-warning me-2" />
					</th>
				</tr>
			</table>
		</form>
		<form name="yourform" id="yourform" method="post" style="display:none">
			<table class="table">
				<tr class="table-secondary">
					<th colspan="2">
						<h3>비밀번호 변경</h3>
						<div>(비밀번호를 변경합니다.)</div>
					</th>
				</tr>
				<tr>
					<th>새 비밀번호</th>
					<td><input type="password" name="newPwd" id="newPwd" autofocus required class="form-control" /></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td><input type="password" name="rePwd" id="rePwd" required class="form-control" /></td>
				</tr>
				<tr>
					<th colspan="2">
						<input type="button" value="비밀번호변경" onclick="pwdChange('${ctp}')" class="btn btn-success me-2" />
						<input type="reset" value="다시입력" class="btn btn-secondary me-2" />
						<input type="button" value="돌아가기" onclick="location.href='${ctp}/member/Main'" class="btn btn-warning me-2" />
					</th>
				</tr>
			</table>
			<input type="hidden" name="mid" value="${sMid}" />
		</form>
	</div>
	<p><br/></p>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>