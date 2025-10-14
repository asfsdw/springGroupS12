<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title></title>
	<script src="${ctp}/js/admin.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/admin.css" />
</head>
<body>
	<div class="text-center">
		<h2>관리자 메인화면</h2>
		<hr/>
		<table class="table table-bordered table-sm">
			<tr>
				<th>방명록 새글</th>
				<td><a href="${ctp}/admin/guest/GuestList?flag=new"><font color="blue">${guestNewCount}</font></a>건</td>
			</tr>
			<tr>
				<th>게시판 새글</th>
				<td><a href="${ctp}/admin/board/BoardList?flag=new"><font color="blue">${boardNewCount}</font></a>건</td>
			</tr>
			<tr>
				<th>신고글 새글</th>
				<td><a href="${ctp}/admin/complaint/ComplaintList?flag=new"><font color="blue">${complaintNewCount}</font></a>건</td>
			</tr>
			<tr>
				<th>신규회원</th>
				<td><a href="${ctp}/admin/complaint/MemberList?flag=new"><font color="blue">${memberNewCount}</font></a>건</td>
			</tr>
			<tr>
				<th>탈퇴신청회원</th>
				<td><a href="${ctp}/admin/complaint/MemberList?level=999"><font color="blue">${cancelMember}</font></a>건</td>
			</tr>
		</table>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>