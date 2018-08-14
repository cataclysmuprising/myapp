<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%--
  ~
  ~   This source file is free software, available under the following license: MIT license.
  ~   Copyright (c) 2018, Than Htike Aung(https://github.com/cataclysmuprising) All Rights Reserved.
  ~
  ~   Permission is hereby granted, free of charge, to any person obtaining a copy
  ~   of this software and associated documentation files (the "Software"), to deal
  ~   in the Software without restriction, including without limitation the rights
  ~   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  ~   copies of the Software, and to permit persons to whom the Software is
  ~   furnished to do so, subject to the following conditions:
  ~
  ~   The above copyright notice and this permission notice shall be included in all
  ~   copies or substantial portions of the Software.
  ~
  ~   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  ~   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  ~   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  ~   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  ~   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  ~   SOFTWARE.
  ~
  ~  	myapp-ui-backend - header.jsp
  ~  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
  ~ 	    Last Modified - 8/14/18 11:01 AM
  ~  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
  ~  	@Since 2018
  --%>

<jsp:useBean id="today" class="java.util.Date"/>
<header class="main-header">
    <input type="hidden" id="pageMode" value="${pageMode}">
    <c:if test="${not empty pageMessage}">
        <span id="pageMessage" data-title="${pageMessage.title}" data-info="${pageMessage.message}"
              data-style="${pageMessage.style}" style="display: none;"></span>
    </c:if>
    <c:forEach var="access" items="${accessments}">
        <input type="hidden" id="${access.key}" value="${access.value}">
    </c:forEach>
    <div id="validationErrors" style="display: none;">
        <c:forEach items="${validationErrors}" var="item">
            <span class="error-item" data-id="${item.key}" data-error-message="${item.value}"></span>
        </c:forEach>
    </div>
    <a class="scrollToTop">
        <i class="fa fa-angle-double-up"></i>
    </a>
    <!-- Logo -->
    <spring:url value="/" var="homeUrl" scope="application"/>
    <a href="${homeUrl}" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <div class="logo-mini">
            <img src="${baseStaticRssDir}/images/logo.png">
        </div>
        <!-- logo for regular state and mobile devices -->
        <div class="logo-lg">
            <img src="${baseStaticRssDir}/images/logo.png">
            <span class="site-name">MyApp Backend</span>
        </div>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <c:choose>
                            <c:when test="${not empty contentId}">
                                <img class="user-image" src="${root}/files/${contentId}" alt="">
                            </c:when>
                            <c:otherwise>
                                <img class="user-image" src="${baseStaticRssDir}/images/avatar/guest.jpg" alt="">
                            </c:otherwise>
                        </c:choose>
                        <span class="hidden-xs"><c:out value="${loginUserName}" default="Guest"/></span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <c:choose>
                                <c:when test="${not empty contentId}">
                                    <img class="img-circle" src="${root}/files/${contentId}" alt="">
                                </c:when>
                                <c:otherwise>
                                    <img class="img-circle" src="${baseStaticRssDir}/images/avatar/guest.jpg" alt="">
                                </c:otherwise>
                            </c:choose>
                            <p>
                                <c:out value="${loginUserName}" default="Guest"/>
                                <small>Member since ${since}</small>
                            </p>
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <spring:url value="${portalAppUrl}/user/profile" var="profileUrl" scope="application"/>
                                <a href="${profileUrl}" class="btn btn-default btn-flat">Profile</a>
                            </div>
                            <div class="pull-right">
                                <c:if test="${not empty loginUserId}">
                                    <input id="loginUserId" type="hidden" value="${loginUserId}"/>
                                </c:if>
                                <spring:url value="${not empty loginUserName ? '/logout': '/login'}"
                                            var="authenticationUrl"/>
                                <a class="btn btn-default btn-flat"
                                   href="${authenticationUrl}"> ${not empty loginUserName ? 'Sign out': 'Sign in'} </a>
                            </div>
                        </li>
                    </ul>
                </li>
                <!-- Control Sidebar Toggle Button -->
                <li>
                    <a href="#" data-toggle="control-sidebar">
                        <i class="fa fa-sliders"></i>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>