<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="root" scope="application" value="${pageContext.request.contextPath}" />
<c:set var="baseStaticRssDir" scope="application" value="${root}/static"/>
<tiles:importAttribute name="layoutScripts" ignore="false"/>
<tiles:importAttribute name="pageScripts" ignore="true"/>
<tiles:importAttribute name="layoutStyles" ignore="false"/>
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
    <link rel="shortcut icon" href="${baseStaticRssDir}/images/favicon.png" type="image/x-icon"/>
    <title>MyApp Backend</title>
    <c:choose>
        <c:when test="${isProduction}">
            <link href="${baseStaticRssDir}/css/common/bootstrap.min.css?v=${projectVersion}" rel="stylesheet">
            <link href="${baseStaticRssDir}/css/common/font-awesome.min.css?v=${projectVersion}" rel="stylesheet">
            <link href="${baseStaticRssDir}/css/app/base.min.css?v=${projectVersion}" rel="stylesheet">
            <c:forEach var="attr" items="${pageStyles}">
                <c:if test="${fn:endsWith(attr, 'page')}">
                    <link href="${baseStaticRssDir}/css/<c:out value='${attr}' />.min.css?v=${projectVersion}" rel="stylesheet">
                </c:if>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <link href="${baseStaticRssDir}/css/common/bootstrap.css?v=${projectVersion}" rel="stylesheet">
            <link href="${baseStaticRssDir}/css/common/font-awesome.css?v=${projectVersion}" rel="stylesheet">
            <c:forEach var="attr" items="${layoutStyles}">
                <link href="${baseStaticRssDir}/css/<c:out value='${attr}' />.css?v=${projectVersion}" rel="stylesheet">
            </c:forEach>
            <c:forEach var="attr" items="${pageStyles}">
                <link href="${baseStaticRssDir}/css/<c:out value='${attr}' />.css?v=${projectVersion}" rel="stylesheet">
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <script>
        window.paceOptions = {
            ajax: {
                trackMethods: ['GET', 'POST']
            }
        };
    </script>
</head>
<body data-sss-tmo="${pageContext.session.maxInactiveInterval}"
      class="hold-transition sidebar-mini ${userPreference.themeLayout?'normal':userPreference.themeLayout} ${ empty userPreference.themeSkin ?'skin-black':userPreference.themeSkin} ${userPreference.themeSidebar}">
<input type="hidden" id="t3k-portal-theme-layout"
       value="${userPreference.themeLayout?'normal':userPreference.themeLayout}"/>
<input type="hidden" id="t3k-portal-theme-skin"
       value="${empty userPreference.themeSkin ?'skin-black':userPreference.themeSkin}"/>
<input type="hidden" id="t3k-portal-theme-sidebar" value="${userPreference.themeSidebar}"/>
<input type="hidden" id="t3k-portal-theme-sidebar-skin"
       value="${empty userPreference.themeSidebarSkin ?'dark':userPreference.themeSidebarSkin}"/>
<input type="hidden" id="root" value="${root}"/>
<input type="hidden" id="baseStaticRssDir" value="${baseStaticRssDir}"/>
<!-- Preloader -->
<div id="preloader">
    <div id="preloader_status">
        <img src="${baseStaticRssDir}/images/loading.gif"></img>
    </div>
</div>
<div class="wrapper">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="leftMenu"/>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <tiles:insertAttribute name="content"/>
    </div>
    <tiles:insertAttribute name="footer"/>
    <tiles:insertAttribute name="rightMenu"/>
</div>
<c:choose>
    <c:when test="${isProduction}">
        <script type="text/javascript" src="${baseStaticRssDir}/js/common/jquery-1.12.3.min.js?v=${projectVersion}"></script>
        <script type="text/javascript" src="${baseStaticRssDir}/js/common/jquery-ui.min.js?v=${projectVersion}"></script>
        <script type="text/javascript" src="${baseStaticRssDir}/js/common/bootstrap.min.js?v=${projectVersion}"></script>
        <script type="text/javascript" src="${baseStaticRssDir}/js/app/base.min.js?v=${projectVersion}"></script>
        <c:forEach var="attr" items="${pageScripts}">
            <c:if test="${fn:endsWith(attr, 'page')}">
                <script type="text/javascript" src="${baseStaticRssDir}/js/<c:out value='${attr}' />.min.js?v=${projectVersion}"></script>
            </c:if>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <script type="text/javascript" src="${baseStaticRssDir}/js/common/jquery-1.12.3.js?v=${projectVersion}"></script>
        <script type="text/javascript" src="${baseStaticRssDir}/js/common/jquery-ui.js?v=${projectVersion}"></script>
        <script type="text/javascript" src="${baseStaticRssDir}/js/common/bootstrap.js?v=${projectVersion}"></script>
        <c:forEach var="attr" items="${layoutScripts}">
            <script type="text/javascript" src="${baseStaticRssDir}/js/<c:out value='${attr}' />.js?v=${projectVersion}"></script>
        </c:forEach>
        <c:forEach var="attr" items="${pageScripts}">
            <script type="text/javascript" src="${baseStaticRssDir}/js/<c:out value='${attr}' />.js?v=${projectVersion}"></script>
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>