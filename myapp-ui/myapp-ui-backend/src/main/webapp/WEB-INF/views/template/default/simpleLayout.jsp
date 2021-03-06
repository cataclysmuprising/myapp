<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="root" scope="application" value="${pageContext.request.contextPath}"/>
<c:set var="baseStaticRssDir" scope="application" value="${root}/static"/>
<tiles:importAttribute name="pageScripts" ignore="true"/>
<tiles:importAttribute name="pageStyles" ignore="true"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <meta content="" name="description">
    <meta content="" name="author">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="shortcut icon" href="${baseStaticRssDir}/common/images/favicon.png" type="image/x-icon"/>
    <title>My App</title>
    <c:choose>
        <c:when test="${isProduction}">
            <link href="${baseStaticRssDir}/css/common/bootstrap.min.css?v=${projectVersion}" rel="stylesheet">
            <link href="${baseStaticRssDir}/css/common/font-awesome.min.css?v=${projectVersion}" rel="stylesheet">
            <link href="${baseStaticRssDir}/css/theme/AdminLTE/theme.min.css?v=${projectVersion}" rel="stylesheet">
            <c:forEach var="attr" items="${pageStyles}">
                <c:if test="${fn:endsWith(attr, 'page')}">
                    <link href="${baseStaticRssDir}/css/<c:out value='${attr}' />.min.css?v=${projectVersion}" rel="stylesheet">
                </c:if>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <link href="${baseStaticRssDir}/css/common/bootstrap.css?v=${projectVersion}" rel="stylesheet">
            <link href="${baseStaticRssDir}/css/common/font-awesome.css?v=${projectVersion}" rel="stylesheet">
            <link href="${baseStaticRssDir}/css/theme/AdminLTE/theme.css?v=${projectVersion}" rel="stylesheet">
            <c:forEach var="attr" items="${pageStyles}">
                <link href="${baseStaticRssDir}/css/<c:out value='${attr}' />.css?v=${projectVersion}" rel="stylesheet">
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${isProduction}">
            <script type="text/javascript" src="${baseStaticRssDir}/js/common/jquery-1.12.3.min.js?v=${projectVersion}"></script>
            <script type="text/javascript" src="${baseStaticRssDir}/js/common/bootstrap.min.js?v=${projectVersion}"></script>
            <c:forEach var="attr" items="${pageScripts}">
                <c:if test="${fn:endsWith(attr, 'page')}">
                    <script type="text/javascript" src="${baseStaticRssDir}/js/<c:out value='${attr}' />.min.js?v=${projectVersion}"></script>
                </c:if>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <script type="text/javascript" src="${baseStaticRssDir}/js/common/jquery-1.12.3.js?v=${projectVersion}"></script>
            <script type="text/javascript" src="${baseStaticRssDir}/js/common/bootstrap.js?v=${projectVersion}"></script>
            <c:forEach var="attr" items="${pageScripts}">
                <script type="text/javascript" src="${baseStaticRssDir}/js/<c:out value='${attr}' />.js?v=${projectVersion}"></script>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</head>
<tiles:insertAttribute name="content"/>