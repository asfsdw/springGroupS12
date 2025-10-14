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
	<title></title>
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
		
		#green td {background-color: #6F6 !important;}
		#red td {
			background-color: #F66 !important;
			color: #FFF !important;
		}
	</style>
</head>
<body>
	<div class="container text-center">
		<h2>신청하기</h2>
		<hr/>
		<form name="subForm" method="post">
			<table class="table">
				<tr class="table-secondary">
					<th>신청자</th>
					<th>신청내용</th>
					<th>신청</th>
				</tr>
				<tr>
					<td style="width:200px"><input type="text" id="nickName" name="nickName" value="${sNickName}" readonly class="form-control" /></td>
					<td>
						<select id="subContent" name="subContent" class="form-control">
							<option>선택</option>
							<option>등급업</option>
							<option>기타</option>
						</select>
						<div id="demo1" style="display:none">
							<select id="levelUp" name="levelUp" class="form-control">
								<option>선택</option>
								<option>업자</option>
								<option>우수회원</option>
								<option>정회원</option>
							</select>
						</div>
						<div id="demo2" style="display:none"><input type="text" id="subEtc" name="subEtc" placeholder="신청내용을 적어주세요." class="form-control" /></div>
					</td>
					<td style="width:200px">
						<input type="button" value="신청" onclick="subCheck('${sMid}')" class="btn btn-success" />
					</td>
					<td></td>
				</tr>
			</table>
			<input type="hidden" id="mid" name="mid" value="" />
		</form>
		<hr/>
		<h2>신청내역</h2>
		<table class="table table-hover">
			<tr class="table-secondary">
				<th>신청내용</th>
				<th>신청일자</th>
				<th>신청현황</th>
			</tr>
			<c:forEach var="vo" items="${shopVOS}">
				<tr id="${vo.openSW=='신청접수'?'green':vo.openSW=='신청반려'?'red':''}">
					<td>${vo.title}</td>
					<td>${vo.shopDate}</td>
					<td>${vo.openSW}</td>
				</tr>
			</c:forEach>
			<c:forEach var="vo" items="${subVOS}">
				<tr id="${vo.subProgress=='신청접수'?'green':vo.subProgress=='신청반려'?'red':''}">
					<td>${vo.subContent}</td>
					<td>${vo.subDate}</td>
					<td>${vo.subProgress}</td>
				</tr>
			</c:forEach>
		</table>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>