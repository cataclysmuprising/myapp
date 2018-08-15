<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
  ~  	myapp-ui-backend - main-sidebar.jsp
  ~  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
  ~ 	    Last Modified - 8/8/18 11:16 AM
  ~  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
  ~  	@Since 2018
  --%>
<aside class="main-sidebar">
	<section class="sidebar">
		<!-- Sidebar authUser panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<c:choose>
					<c:when test="${not empty contentId}">
						<img class="img-circle user-image" src="${root}/files/${contentId}" alt="">
					</c:when>
					<c:otherwise>
						<img class="img-circle user-image" src="${baseStaticRssDir}/images/avatar/guest.jpg" alt="">
					</c:otherwise>
				</c:choose>
			</div>
			<div class="pull-left info">
				<p>
					<c:out value="${loginUserName}" default="Guest" />
				</p>
				<a href="#">
					<i class="fa fa-circle text-success"></i> Online
				</a>
			</div>
		</div>
		<ul class="sidebar-menu" data-widget="tree">
			<li class="header">MAIN NAVIGATION</li>
			<spring:url value="/dashboard" var="dashboardURL" scope="application" />
			<li class="${page eq 'dashboard' ? 'active' : '' }">
				<a href="${dashboardURL}">
					<i class="fa fa-dashboard"></i>
					<span>Dashboard</span>
				</a>
			</li>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>