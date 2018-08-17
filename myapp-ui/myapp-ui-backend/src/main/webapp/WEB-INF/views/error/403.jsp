<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="card">
	<div class="cardHeader">
		<h1>
			<span>
				<strong>4</strong>
			</span>
			<span>
				<strong>0</strong>
			</span>
			<span>
				<strong>3</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>Access Denied</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>
				Sorry
				<span class="userName">
					<c:out value="${loginUserName}"></c:out>
				</span>
				! You don't have permission to access this content.
			</p>
			<spring:url value="/" var="homeUrl" />
			<spring:url value="/logout" var="logoutUrl" />
			Goback to
			<a href="${homeUrl}">Home</a>
			(or)
			<a href="${logoutUrl}">Sign In</a>
			with different Account.
		</div>
	</div>
</section>