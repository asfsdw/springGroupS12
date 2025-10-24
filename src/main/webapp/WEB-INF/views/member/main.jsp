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
	<script src="${ctp}/js/member.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctp}/css/member.css" />
	<title></title>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		// 관리자일 경우에만 스크립트 실행.
		if(${sLevel == 0}) {
			google.charts.load('current', {'packages':['bar']});
			google.charts.setOnLoadCallback(drawChart1);
			
			function drawChart1() {
				let str = [];
				let statNickNames = "${statNickName}".split("/");
				let statLoginCnts = "${statLoginCnt}".split("/");
				let statPoints = "${statPoint}".split("/");
				
				for(let i=0; i<statNickNames.length; i++) {
					str.push([statNickNames[i],Number(statLoginCnts[i]),Number(statPoints[i])]);
				}
				
				var data = google.visualization.arrayToDataTable([
					["닉네임", "총 방문일", "보유 포인트"],
					// 전개 연산자.
					...str
				]);
				
				var options = {"chart" : {"title" : "최다방문자(1개월), 포인트"}};
				
				var chart = new google.charts.Bar(document.getElementById("chartView"));
				
				chart.draw(data, google.charts.Bar.convertOptions(options));
			}
		}
	
		// chart 변경.
		function chartView(flag) {
			if(flag == 1) {
				$("#chart1").hide();
				$("#chart2").show();
				
				drawChart1();
			}
			if(flag == 2) {
				$("#chart2").hide();
				$("#chart1").show();
				
				google.charts.load('current', {'packages':['corechart']});
				google.charts.setOnLoadCallback(drawChart2);
				
				function drawChart2() {
					let str = [];
					let statNickNames = "${statNickName}".split("/");
					let statLoginCnts = "${statLoginCnt}".split("/");
					
					for(let i=0; i<statNickNames.length; i++) {
						str.push([statNickNames[i],Number(statLoginCnts[i])]);
					}
					
					var data = google.visualization.arrayToDataTable([
						["닉네임", "총 방문일"],
						// 전개 연산자.
						...str
					]);
					
					var options = {"title" : "최다방문자(1개월)"};
					
					var chart = new google.visualization.PieChart(document.getElementById("chartView"));
					
					chart.draw(data, options);
				}
			}
		}
	</script>
</head>
<body>
	<div class="container text-center">
		<h2>${sNickName} 회원님 전용 방입니다.</h2>
		<c:if test="${sLoginNew == 'OK'}">
			<p></p>
			<div>
				<h4>현재 임시 비밀번호를 사용 중입니다.<br/>계정 보호를 위해 비밀번호를 변경해주세요.</h4>
				<input type="button" value="비밀번호 변경" onclick="location.href='${ctp}/member/MemberPwdCheck/p'" class="btn btn-success" />
			</div>
		</c:if>
		<hr/>
		<div class="row">
			<div class="col">
				현재 회원 등급: ${sStrLevel}<br/>
				현재 포인트: ${mVO.point}<br/>
				이전 방문일: ${fn:substring(sLastDate,0,10)}<br/>
				총 방문일: ${mVO.loginCnt}<br/>
			</div>
			<div class="col">
				<img src="${ctp}/member/${mVO.myImage}" style="width:200px" />
			</div>
		</div>
		<hr/>
		<div class="row">
			<div class="col">
				<div><h2>신청내역</h2></div>
				<table class="table table-bordered">
					<tr class="table-secondary">
						<th>신청내용</th>
						<th>신청현황</th>
					</tr>
					<c:forEach var="vo" items="${shopVOS}">
						<tr id="${vo.openSW=='신청접수'?'yellow':vo.openSW=='신청반려'?'red':''}">
							<td>${vo.title}</td>
							<td>${vo.openSW}</td>
						</tr>
					</c:forEach>
					<c:forEach var="vo" items="${subVOS}">
						<tr id="${vo.subProgress=='신청접수'?'yellow':vo.subProgress=='신청반려'?'red':''}">
							<td>${vo.subContent}</td>
							<td>${vo.subProgress}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="col">
				<div><h2>구매상품</h2></div>
				<table class="table table-bordered">
					<tr>
						<th>구매상품</th>
						<th>배송상태</th>
					</tr>
					<c:forEach var="vo" items="${dVOS}">
						<c:if test="${deliveryIdx != vo.deliveryIdx}">
							<c:set var="cnt" value="0" />
							<c:forEach var="cntCalc" items="${dVOS}">
								<c:if test="${vo.deliveryIdx == cntCalc.deliveryIdx}">
									<c:set var="cnt" value="${cnt+1}" />
								</c:if>
							</c:forEach>
						</c:if>
						<tr>
							<td>${vo.title}</td>
							<c:if test="${vo.deliverySW == '구매완료'}">
								<c:if test="${vo.replyIdx != 0}">
									<td class="align-middle"><input type="button" value="리뷰작성완료" disabled class="btn btn-success btn-sm"></td>
								</c:if>
								<c:if test="${vo.replyIdx == 0}">
									<td class="align-middle"><a href="${ctp}/shop/Product?mid=${sMid}&idx=${vo.parentIdx}" class="btn btn-primary btn-sm">리뷰작성하기</a></td>
								</c:if>
							</c:if>
							<c:if test="${vo.deliverySW != '구매완료'}">
								<c:if test="${vo.deliverySW == '배송완료'}">
									<td class="align-middle"><a href="${ctp}/shop/DeliveryOk?mid=${sMid}" class="btn btn-info btn-sm">구매완료하기</a></td>
								</c:if>
								<c:if test="${vo.deliverySW != '배송완료'}">
									<td class="align-middle">${vo.deliverySW}</td>
								</c:if>
							</c:if>
							<c:set var="deliveryIdx" value="${vo.deliveryIdx}" />
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 관리자일 경우에만 chart 표시. -->
		<c:if test="${sLevel == 0}">
			<hr/>
			<div class="row">
				<div class="col">
					<div id="chartView" style="width: 400px; height: 300px;"></div>
					<div class="text-start mt-2">
						<input type="button" value="그래프보기" id="chart1" onclick="chartView(1)" 
							class="btn btn-primary btn-sm" style="width: 100px; margin-left: 100px; display: none;" />
						<input type="button" value="비율보기" id="chart2" onclick="chartView(2)" 
							class="btn btn-primary btn-sm" style="width: 100px; margin-left: 100px;"/>
					</div>
				</div>
			</div>
		</c:if>
		<hr/>
		<p><br/></p>
	</div>
	<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동" /></h6>
</body>
</html>