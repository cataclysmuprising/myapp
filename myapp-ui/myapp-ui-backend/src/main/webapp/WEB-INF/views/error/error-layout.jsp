<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" scope="application" value="${pageContext.request.contextPath}"/>
<c:set var="baseStaticRssDir" scope="application" value="${root}/static"/>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Error !</title>
<c:choose>
	<c:when test="${isProduction}">
		<link href="${baseStaticRssDir}/css/common/error.min.css" rel="stylesheet">		
	</c:when>
	<c:otherwise>
		<link href="${baseStaticRssDir}/css/common/error.css" rel="stylesheet">
	</c:otherwise>
</c:choose>
</head>
<body>
	<tiles:insertAttribute name="content" />
</body>
</html>