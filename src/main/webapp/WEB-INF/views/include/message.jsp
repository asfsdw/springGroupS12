<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
    <title>message</title>
    <script>
    	'use strict';
    	let url = "";
    	if("${message}" != "") alert("${message}");
   		
    	if("${mid}" != "") url = "${url}?mid=${mid}";
    	else url = "${url}";
    	location.href = "${ctp}/"+url;
    </script>
  </head>
<body>
</body>
</html>