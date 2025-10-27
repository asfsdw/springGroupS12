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
	<script src="${ctp}/js/fileManagement.js"></script>
	<title>File Management</title>
</head>
<body>
	<div class="container text-center">
		<h2>파일 관리자입니다.</h2>
		<div class="row mb-2">
		<p></p>
			<div class="col text-start">
				<!-- 폴더에 들어갔을 경우 -->
				<c:if test="${pVO.part != '전체'}">
					<input type="button" value="최상위폴더로" onclick="location.href='${ctp}/admin/FileManagement'" class="btn btn-warning btn-sm" />
				</c:if>
			</div>
			<div class="col"><font size="5">폴더, 파일 ${pVO.totRecCnt}건</font></div>
			<div class="col text-end">
				<input type="button" value="전체선택" onclick="allCheck()" class="btn btn-success btn-sm" />
				<input type="button" value="선택해제" onclick="allReset()" class="btn btn-primary btn-sm" />
				<!-- 폴더명(분류)과 파일이름을 보낸다. 선택삭제의 경우 체크한 파일의 이름을 따로 불러오기 때문에 공백. -->
				<input type="button" value="선택삭제" onclick="fileDelete('${pVO.part}','')" class="btn btn-danger btn-sm" />
			</div>
		</div>
		<hr/>
		<table class="table table-hover">
			<tr class="table-secondary">
				<th>번호</th>
				<th>파일명</th>
				<th>파일형식</th>
				<th>비고</th>
			</tr>
			<c:forEach var="file" items="${files}" varStatus="st">
			<!-- 파일(폴더)명과 확장자 분리. -->
			<c:set var="ext" value="${fn:split(file,'.')}"></c:set>
			<c:set var="extName" value="${ext[fn:length(ext)-1]}"></c:set>
				<!-- files는 pageSize만큼만 채우고 남은 공간은 비어있기 때문에 null이 아닐때만 출력하게 한다. -->
				<c:if test="${file != null}">
					<tr>
						<td>
							${st.count}
							<!-- 확장자가 있을 때(폴더가 아닐 때) 체크박스 표시. -->
							<c:if test="${extName != ext[0]}">
								<input type="checkbox" id="fileCheck${st.index}" name="fileCheck" value="${file}" />
							</c:if>
						</td>
						<td>${file}</td>
						<td>
							<!-- 확장자가 없을 때(폴더일 때). -->
							<c:if test="${extName == ext[0]}">폴더</c:if>
							<!-- 확장자가 있을 때(폴더가 아닐 때) 파일형식 표시. -->
							<c:if test="${extName != ext[0]}">
								<c:if test="${extName == 'zip'}">압축파일</c:if>
								<c:if test="${extName == 'hwp'}">한글문서파일</c:if>
								<c:if test="${extName == 'doc'}">word파일</c:if>
								<c:if test="${extName == 'ppt' || extName == 'pptx'}">파워포인트파일</c:if>
								<c:if test="${extName == 'pdf'}">pdf파일</c:if>
								<c:if test="${extName == 'txt'}">텍스트파일</c:if>
								<c:if test="${extName == 'mp4'}">동영상파일</c:if>
								<c:if test="${extName == 'jpg' || extName == 'gif' || extName == 'png'}">
									<!-- 폴더 경로 설정(ckeditor의 경로가 겹치기 때문에 servlet-context.xml에서 경로 바꿔주는 게 쉬움). -->
									<c:if test="${pVO.part == 'shop'}">
										<img src="${ctp}/data/${pVO.part}/${file}" width="150px" />
									</c:if>
									<c:if test="${pVO.part != 'shop'}">
										<img src="${ctp}/${pVO.part}/${file}" width="150px" />
									</c:if>
								</c:if>
							</c:if>
						</td>
						<td>
							<!-- 폴더일 때는 선택 버튼 출력해서 폴더 안에 들어갈 수 있게 한다. -->
							<c:if test="${extName == ext[0]}"><input type="button" value="선택" onclick="selectFolder('${extName}')" class="btn btn-success" /></c:if>
							<!-- 파일일 때는 삭제 버튼 출력해서 파일을 삭제할 수 있게 한다(폴더(분류)명과 파일 이름을 보낸다). -->
							<c:if test="${extName != ext[0]}"><input type="button" value="삭제" onclick="fileDelete('${pVO.part}','${file}')" class="btn btn-danger" /></c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<p><br/></p>
	</div>
	<!-- 블록페이지 시작 -->
	<div class="input-group justify-content-center">
		<div class="pagination">
			<c:if test="${pVO.pag > 1}"><a href="${ctp}/admin/folder/FileManagement?pag=1&pageSize=${pVO.pageSize}&part=${pVO.part}" class="page-item page-link text-dark">첫 페이지</a></c:if>
			<c:if test="${pVO.curBlock > 0}">
				<a href="${ctp}/admin/FileManagement?pag=${(pVO.curBlock - 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}&part=${pVO.part}" class="page-item page-link text-dark">이전 블록</a>
			</c:if>
			<c:forEach var="i" begin="${(pVO.curBlock * pVO.blockSize) + 1}" end="${(pVO.curBlock * pVO.blockSize) + pVO.blockSize}" varStatus="st">
				<c:if test="${i <= pVO.totPage && i == pVO.pag}">
					<span class="page-item active page-link bg-secondary border-secondary">${i}</span>
				</c:if>
				<c:if test="${i <= pVO.totPage && i != pVO.pag}">
					<a href="${ctp}/admin/FileManagement?pag=${i}&pageSize=${pVO.pageSize}&part=${pVO.part}" class="page-item page-link text-dark">${i}</a>
				</c:if>
			</c:forEach>
			<c:if test="${pVO.curBlock < pVO.lastBlock}">
				<a href="${ctp}/admin/FileManagement?pag=${(pVO.curBlock + 1) * pVO.blockSize + 1}&pageSize=${pVO.pageSize}&part=${pVO.part}" class="page-item page-link text-dark">다음 블록</a>
			</c:if>
			<c:if test="${pVO.pag < pVO.totPage}">
				<a href="${ctp}/admin/FileManagement?pag=${pVO.totPage}&pageSize=${pVO.pageSize}&part=${pVO.part}" class="page-item page-link text-dark">마지막 페이지</a>
			</c:if>
		</div>
	</div>
	<p></p>
	<!-- 블록페이지 끝 -->
</body>
</html>