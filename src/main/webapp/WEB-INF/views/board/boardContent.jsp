<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
		<script src="${ctp}/js/board.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctp}/css/board.css" />
		<title>${vo.title}</title>
	</head>
<body>
	<p><br/></p>
	<div class="container">
		<h2 class="text-center">
			${vo.title}
			<c:if test="${sLevel < 99}">
				/
				<!-- 한 번 누른 좋아요, 싫어요를 누른 게시글에서는 좋아요, 싫어요를 누르지 못하게 한다. -->
				<c:if test="${!fn:contains(sContentIdx, 'boardGood'+=sMid+=vo.idx)}">
					<a href="javascript:goodCheckPlus(${vo.idx})" title="좋아요" class="text-decoration-none">👍</a>
					<a href="javascript:goodCheckMinus(${vo.idx})" title="싫어요" class="text-decoration-none">👎</a>
				</c:if>
				<c:if test="${fn:contains(sContentIdx, 'boardGood'+=sMid+=vo.idx)}">
					<a>👌</a>
				</c:if>
			</c:if>
		</h2>
		<p><br/></p>
		<table class="table table-bordered">
			<tr>
				<th>글쓴이</th>
				<td>${vo.nickName}</td>
				<th>글쓴날짜</th>
				<td colspan="2">${vo.boardDate}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${vo.views}</td>
				<th>좋아요</th>
				<td>${vo.good}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="5" style="height:230px">${fn:replace(vo.content, newLine, "<br/>")}</td>
			</tr>
			<c:if test="${!empty fVO}">
				<tr>
					<th>첨부파일</th>
					<td colspan="5">
						<c:set var="oFileNames" value="${fn:split(fVO.OFileName,'/')}" />
						<c:set var="sFileNames" value="${fn:split(fVO.SFileName,'/')}" />
						<c:set var="fileSizes" value="${fn:split(fVO.fileSize,'/')}" />
						<c:forEach var="i" begin="0" end="${fn:length(fileSizes)}">
								<c:if test="${fileSizes[i] != '0'}">
									<a href="${ctp}/board/${sFileNames[i]}" download=${oFileNames[i]}>${oFileNames[i]}</a>
								</c:if>
						</c:forEach>
					</td>
				</tr>
			</c:if>
			</table>
			<table class="table table-borderless">
			<tr>
				<td class="text-start">
					<c:if test="${empty pVO.search}">
						<input type="button" value="돌아가기" onclick="location.href='${ctp}/board/BoardList?pag=${pVO.pag}&pageSize=${pVO.pageSize}'" class="btn btn-info" />
					</c:if>
					<c:if test="${!empty pVO.search}">
						<input type="button" value="돌아가기" onclick="location.href='${ctp}/board/BoardSearchList?pag=${pVO.pag}&pageSize=${pVO.pageSize}&search=${pVO.search}&searchStr=${pVO.searchStr}'" class="btn btn-info" />
					</c:if>
				</td>
				<td class="text-end">
					<c:if test="${vo.mid == sMid && vo.complaint != 'OK'}">
						<input type="button" value="수정" onclick="location.href='${ctp}/board/BoardUpdate?idx=${vo.idx}&pag=${pVO.pag}&pageSize=${pVO.pageSize}&search=${pVO.search}&searchStr=${pVO.searchStr}'" class="btn btn-warning" />
					</c:if>
					<c:if test="${vo.mid == sMid || sLevel == 0 && vo.complaint != 'OK'}">
						<input type="button" value="삭제" onclick="deleteCheck(${vo.idx})" class="btn btn-danger" />
					</c:if>
					<c:if test="${!empty sMid && vo.complaint != 'OK'}">
						<input type="button" value="신고" data-bs-toggle="modal" data-bs-target="#myModal" class="btn btn-secondary" />
					</c:if>
					<c:if test="${vo.complaint == 'OK'}">
						<input type="button" value="신고중" disabled class="btn btn-outline-danger" />
					</c:if>
				</td>
			</tr>
		</table>
		<hr/>
		<!-- 댓글 시작 -->
		<c:if test="${!empty reVOS}">
			<p>댓글</p>
			<table class="table table-hover text-start">
				<tr>
					<th>작성자</th>
					<th colspan="2">댓글내용</th>
					<th>댓글일자</th>
					<th>대댓글/수정/삭제</th>
				</tr>
				<c:forEach var="reVO" items="${reVOS}" varStatus="st">
					<tr>
						<td>
							<c:if test="${reVO.reStep > 1}">
								<c:forEach var="i" begin="1" end="${reVO.reStep}"> &nbsp;</c:forEach>
								└▶ 
							</c:if>
							<c:if test="${reVO.mid == 'noMember'}">${reVO.hostIP}</c:if>
							<c:if test="${reVO.mid != 'noMember'}">${reVO.nickName}</c:if>
						</td>
						<td colspan="2">${fn:replace(reVO.content, newLine, "<br/>")}</td>
						<td class="text-center">${reVO.replyDate}</td>
						<td class="text-center">
							<a href="javascript:reReplyForm('${ctp}','${reVO.idx}','${reVO.parentIdx}','${sMid}','${sNickName}','${pageContext.request.remoteAddr}')" title="대댓글" class="text-decoration-none">💬</a>
							<c:if test="${reVO.nickName == sNickName || sLevel == 0}">
								<a href="javascript:replyUpdate('${ctp}','${reVO.idx}','${fn:replace(reVO.content, newLine, '<br/>')}')" title="수정" class="text-decoration-none">/✏️</a>
								<a href="javascript:replyDelete('${ctp}','${reVO.idx}')" title="삭제" class="text-decoration-none">/🗑️</a>
							</c:if>
						</td>
					</tr>
					<tr id="demo${reVO.idx}">
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<form name="replyForm">
			<table class="table">
				<tr>
					<td colspan="2">
						댓글 쓰기
						<textarea rows="4" name="content" id="content" class="form-control"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<c:if test="${empty sNickName}"><span>작성자: ${pageContext.request.remoteAddr}</span></c:if>
						<c:if test="${!empty sNickName}"><span>작성자: ${sNickName}</span></c:if>
					</td>
					<td class="text-end">
						<span><input type="button" value="댓글달기" onclick="replyCheck('${ctp}','${vo.idx}','${sMid}','${sNickName}','${pageContext.request.remoteAddr}')" class="btn btn-info btn-sm" /></span>
					</td>
				</tr>
			</table>
		</form>
		<!-- 댓글 끝 -->
		<hr/>
		<!-- 이전글, 다음글 -->
		<table class="table table-borderless">
			<tr>
				<td>
					<c:if test="${!empty nextVO.title}">
						<c:if test="${nextVO.openSW == 'NO'}">
							비밀글입니다.
						</c:if>
						<c:if test="${nextVO.openSW != 'NO'}">
							⬆<a href="${ctp}/board/BoardContent?idx=${nextVO.idx}&pag=${pVO.pag}&pageSize=${pVO.pageSize}">다음글: ${nextVO.title}</a>
						</c:if>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>
					<c:if test="${!empty preVO.title}">
						<c:if test="${preVO.openSW == 'NO'}">
							비밀글입니다.
						</c:if>
						<c:if test="${preVO.openSW != 'NO'}">
							⬇<a href="${ctp}/board/BoardContent?idx=${preVO.idx}&pag=${pVO.pag}&pageSize=${pVO.pageSize}">이전글: ${preVO.title}</a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<p><br/></p>
	<!-- 신고 -->
	<div class="modal fade" id="myModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">현재 게시글을 신고합니다.</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<b>신고사유 선택</b>
					<hr class="border border-secondary">
					<form name="modalForm">
						<div><input type="radio" name="complaint" id="complaint1" value="광고,홍보,영리목적"/> 광고,홍보,영리목적</div>
						<div><input type="radio" name="complaint" id="complaint2" value="욕설,비방,차별,혐오"/> 욕설,비방,차별,혐오</div>
						<div><input type="radio" name="complaint" id="complaint3" value="불법정보"/> 불법정보</div>
						<div><input type="radio" name="complaint" id="complaint4" value="음란,청소년유해"/> 음란,청소년유해</div>
						<div><input type="radio" name="complaint" id="complaint5" value="개인정보노출,유포,거래"/> 개인정보노출,유포,거래</div>
						<div><input type="radio" name="complaint" id="complaint6" value="도배,스팸"/> 도배,스팸</div>
						<div><input type="radio" name="complaint" value="기타" onclick="etcShow()"/> 기타</div>
						<div id="etc"><textarea rows="2" id="etcTxt" class="form-control" style="display:none"></textarea></div>
						<hr class="border border-secondary">
						<input type="button" value="신고하기" onclick="complaintCheck('${vo.idx}','${sMid}','${vo.title}')" class="btn btn-success form-control" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 신고 끝 -->
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>