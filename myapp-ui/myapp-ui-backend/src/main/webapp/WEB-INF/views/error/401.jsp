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
				<strong>1</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>Session Time Out</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>
				Sorry
				<span class="userName">
					<c:out value="${loginUserName}"></c:out>
				</span>
				! You were <strong>AFK</strong><em> (away from keyboard)</em> for a long-time.Your session was expired.
			</p>
			<spring:url value="/" var="homeUrl" />
			<spring:url value="/login" var="loginUrl" />
			<a href="${loginUrl}">Re-login</a>
			with a new session (or)
			<a href="${header.referer}">Redirect</a>
			to your last page.
		</div>
	</div>
</section>