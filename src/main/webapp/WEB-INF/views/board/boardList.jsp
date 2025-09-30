<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
		<title>게시판</title>
		<script>
			'use strict';
			
			// 게시글 x개 표시하기.
			$(() => {
				$("#viewPageCnt").on("change", () => {
					let startIndexNo = ${pVO.startIndexNo};
					let pageSize = $("#viewPageCnt").val();
					// 페이지 도중에 바꿨을 때, 가장 위에 글이 포함된 페이지로 이동.
					let pag = Math.floor(startIndexNo / pageSize) + 1;
					location.href="${ctp}/board/BoardList?pag="+pag+"&pageSize="+pageSize;
				});
			});
		</script>
	</head>
<body>
	<p><br/></p>
	<div class="container">
		<h2 class="text-center">게시글 리스트</h2>
		<table class="table table-bordeless m-0 p-0">
			<tr>
				<td class="text-start"><a href="${ctp}/board/BoardInput" class="btn btn-success btn-sm">글쓰기</a></td>
				<td class="text-end">한 페이지에 최대:&nbsp;&nbsp;
					<select name="viewPageCnt" id="viewPageCnt" onchange="viewPageCheck()">
						<option value="5" ${pVO.pageSize==5 ? 'selected' : ''}>5개씩 보기</option>
						<option value="10"<c:if test="${pVO.pageSize == 10}">selected</c:if>>10개씩 보기</option>
						<option value="15"<c:if test="${pVO.pageSize == 15}">selected</c:if>>15개씩 보기</option>
						<option value="20"<c:if test="${pVO.pageSize == 20}">selected</c:if>>20개씩 보기</option>
						<option value="30"<c:if test="${pVO.pageSize == 30}">selected</c:if>>30개씩 보기</option>
					</select>
				</td>
			</tr>
		</table>
		<table class="table table-hover text-center">
			<tr class="table-secondary">
				<th>번호</th>
				<th>글제목</th>
				<th>글쓴이</th>
				<th>올린날짜</th>
				<th>조회수(👍)</th>
			</tr>
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<tr>
					<td>${pVO.curScrStartNo-st.index}</td>
					<td class="text-start">
						<c:if test="${vo.complaint == 'HI' && sMid != 'admin'}">
							신고된 글입니다.
						</c:if>
						<c:if test="${vo.complaint == 'HI' && sMid == 'admin'}">
							<a href="${ctp}/board/BoardContent?idx=${vo.idx}&pag=${pVO.pag}&pageSize=${pVO.pageSize}"
									class="text-primary link-secondary link-underline-opacity-0 link-underline-opacity-100-hover">${vo.title}</a>
							<font color="red">(신고글) </font>
							<c:if test="${vo.replyCnt != 0}">(${vo.replyCnt})</c:if>
						</c:if>
						<c:if test="${vo.openSW == 'NO' && vo.complaint != 'HI'}">
							<c:if test="${vo.mid == sMid || sMid == 'admin'}">
								<a href="${ctp}/board/BoardContent?idx=${vo.idx}&pag=${pVO.pag}&pageSize=${pVO.pageSize}"
									class="text-primary link-secondary link-underline-opacity-0 link-underline-opacity-100-hover"></a>
								<c:if test="${sMid == 'admin'}"><font color="red">(비밀글) </font></c:if>${vo.title}
								<c:if test="${vo.replyCnt != 0}">(${vo.replyCnt})</c:if>
							</c:if>
							<c:if test="${vo.mid != sMid && sAdmin != 'adminOK'}">비밀글입니다.</c:if>
						</c:if>
						<c:if test="${vo.openSW != 'NO' && vo.complaint != 'HI'}">
							<a href="${ctp}/board/BoardContent?idx=${vo.idx}&pag=${pVO.pag}&pageSize=${pVO.pageSize}"
								class="text-primary link-secondary link-underline-opacity-0 link-underline-opacity-100-hover">${vo.title}</a>
							<c:if test="${vo.replyCnt != 0}">(${vo.replyCnt})</c:if>
						</c:if>
						<c:if test="${vo.hourDiff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>
					</td>
					<td>${vo.nickName}</td>
					<td>
						${vo.dateDiff == 0 ? fn:substring(vo.boardDate,11,19) : vo.dateDiff == 1 ? fn:substring(vo.boardDate,5,19) : fn:substring(vo.boardDate,0,10)}
					</td>
					<td>
						${vo.views}<c:if test="${vo.good != 0}">(${vo.good})</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		<!-- 블록페이지 시작 -->
		<div class="input-group justify-content-center">
			<div class="pagination">
				<c:if test="${pVO.pag > 1}"><a href="${ctp}/board/BoardList?pag=1&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">첫 페이지</a></c:if>
				<c:if test="${pVO.curBlock > 0}">
					<a href="${ctp}/board/BoardList?pag=${(pVO.curBlock - 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">이전 블록</a>
				</c:if>
				<c:forEach var="i" begin="${(pVO.curBlock * pVO.blockSize) + 1}" end="${(pVO.curBlock * pVO.blockSize) + pVO.blockSize}" varStatus="st">
					<c:if test="${i <= pVO.totPage && i == pVO.pag}">
						<span class="page-item active page-link bg-secondary border-secondary">${i}</span>
					</c:if>
					<c:if test="${i <= pVO.totPage && i != pVO.pag}">
						<a href="${ctp}/board/BoardList?pag=${i}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">${i}</a>
					</c:if>
				</c:forEach>
				<c:if test="${pVO.curBlock < pVO.lastBlock}">
					<a href="${ctp}/board/BoardList?pag=${(pVO.curBlock + 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">다음 블록</a>
				</c:if>
				<c:if test="${pVO.pag < pVO.totPage}">
					<a href="${ctp}/board/BoardList?pag=${pVO.totPage}&pageSize=${pVO.pageSize}" class="page-item page-link text-dark">마지막 페이지</a>
				</c:if>
			</div>
		</div>
		<p></p>
		<!-- 블록페이지 끝 -->
		<!-- 검색기 시작 -->
		<div class="text-center">
			<form name="searchForm" method="get" action="BoardSearchList">
				<b>검색:</b>
				<select name="search" id="search">
					<option value="title">글제목</option>
					<option value="nickName">글쓴이</option>
					<option value="content">글내용</option>
				</select>
				<input type="text" name="searchStr" id="searchStr" required />
				<input type="submit" value="검색버튼" class="btn btn-info btn-sm" />
			</form>
		</div>
		<!-- 검색기 끝 -->
	</div>
	<p><br/></p>
</body>
</html>