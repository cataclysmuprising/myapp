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
				<strong>5</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>Illegal Access</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>
				Sorry
				<span class="userName">
					<c:out value="${loginUserName}"></c:out>
				</span>
				! We assume you are trying to access with illegal ways.Don't be mess-up.
			</p>
			<spring:url value="/" var="homeUrl" />
			<spring:url value="/login" var="loginUrl" />
			Goback to
			<a href="${homeUrl}">Home</a>
			(or)
			<a href="${loginUrl}">Sign In</a>
			with different Account.
		</div>
	</div>
</section>