<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="card">
	<div class="cardHeader">
		<h1>
			<span>
				<strong>5</strong>
			</span>
			<span>
				<strong>0</strong>
			</span>
			<span>
				<strong>0</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>Internal Server Problem</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>We are very sorry, it seems there is a problem with our servers. Please try your request again in a moment.</p>
			<spring:url value="/" var="homeUrl" />
			Goback to
			<a href="${homeUrl}">Home</a>
			(or) Contact Administrators
			<p>
				<small>
					<em>(We apologize for the inconvenience and appreciate your patient)</em>
				</small>
			</p>
		</div>
	</div>
</section>