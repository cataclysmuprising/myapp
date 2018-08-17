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
				<strong>9</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>Already Reported</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>Your last submitted informations contain duplicated value(s) that the server won't allow to process.</p>
			<spring:url value="/" var="homeUrl" />
			<spring:url value="/login" var="loginUrl" />
			Contact your administrators (or) Go
			<a href="${header.referer}">Back</a>
			to your last submitted form.
		</div>
	</div>
</section>