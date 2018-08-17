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
				<strong>4</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>Page Not Found</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>The page you are looking for has been removed,had its name changed or temporarily unavailable.</p>
			<spring:url value="/" var="homeUrl" />
			Goback to
			<a href="${homeUrl}">Home</a>
			(or) check again your requested URL
		</div>
	</div>
</section>
