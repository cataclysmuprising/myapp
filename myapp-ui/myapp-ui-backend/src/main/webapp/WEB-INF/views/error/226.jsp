<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="card">
	<div class="cardHeader">
		<h1>
			<span>
				<strong>2</strong>
			</span>
			<span>
				<strong>2</strong>
			</span>
			<span>
				<strong>6</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>I'm still in Used</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>Rejected : Unable to remove your requested informations because this was connected with other resources.If you try to forcely remove it, the entire application will loose
				consistency.</p>
			<spring:url value="/" var="homeUrl" />
			<spring:url value="/login" var="loginUrl" />
			Contact your administrators (or) Go
			<a href="${header.referer}">Back</a>
			to your previous page.
		</div>
	</div>
</section>