<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>T3k User Informations</h1>
	<ol class="breadcrumb">
		<li>
			<a href="${homeUrl}"> Home </a>
		</li>
		<li>
			<a href="${userHomeUrl}"> Admin User </a>
		</li>
		<li class="active">${user.name}</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<div class="smallPanel">
			<!-- Custom Tabs -->
			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs">
					<li class="active">
						<a data-toggle="tab" href="#userTab">Personal </a>
					</li>
					<li>
						<a data-toggle="tab" href="#loginHistoryTab">loginHistory </a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="userTab">
						<input type="hidden" id="userId" value="${user.id}">
						<div class="box-body">
							<img class="profile-user-img img-responsive img-circle" src="${root}/files/${user.contentId}" width="250px" alt="" />
							<h4 class="text-center">${user.name}</h4>
							<ul class="list-group list-group-unbordered custom-list-group">
								<li class="list-group-item custom-list-group-item">
									<b>Email</b>
									<a class="pull-right">${user.email}</a>
								</li>
								<li class="list-group-item custom-list-group-item">
									<b>NRC</b>
									<a class="pull-right">${user.nrc}</a>
								</li>
								<li class="list-group-item custom-list-group-item">
									<b>Gender</b>
									<a class="pull-right">${user.gender}</a>
								</li>	
								<li class="list-group-item custom-list-group-item">
									<b>Date of Birth</b>
									<a class="pull-right">${user.dob}</a>
								</li>	
								<li class="list-group-item custom-list-group-item">
									<b>Status</b>
									<a class="pull-right">${user.status}</a>
								</li>	
								<li class="list-group-item custom-list-group-item">
									<b>UserType</b>
									<a class="pull-right">${user.userType}</a>
								</li>																																					
							</ul>
						</div>
					</div>
					<div id="loginHistoryTab" class="tab-pane">
						<div class="box-body">
							<table id="loginHistoryTable" class="table table-bordered" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>Client IP</th>
										<th>Operating System</th>
										<th>User Agent</th>
										<th>Login Date/Time</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
