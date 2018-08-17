<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
			<c:if test="${dashboard}">
				<li class="${page eq 'dashboard' ? 'active' : '' }">
					<spring:url value="/dashboard" var="dashboardURL" scope="application" />
					<a href="${dashboardURL}">
						<i class="fa fa-dashboard"></i>
						<span>Dashboard</span>
					</a>
				</li>
			</c:if>
			<c:if test="${userList}">
				<li class="${page eq 'user' ? 'active' : '' }">
					<spring:url value="/user" var="userHomeUrl" scope="application" />
					<a href="${userHomeUrl}">
						<i class="fa fa-users"></i>
						<span>User</span>
					</a>
				</li>
			</c:if>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>