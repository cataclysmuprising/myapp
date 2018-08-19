<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		User Setup <small> (Create a new User) </small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="${homeUrl}">
				<i class="fa fa-home"></i> Home
			</a>
		</li>
		<li>
			<a href="${userHomeUrl}">
				<i class="fa fa-users"></i> User
			</a>
		</li>
		<li class="active">Add New</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="inline-content">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<c:set var="submitUrl" value="/user/add" />
				<spring:url value="${submitUrl}" var="userSubmitUrl" />
				<form:form class="form-horizontal" modelAttribute="userDto" id="userForm" action="${userSubmitUrl}" method="post">
					<div class="box box-solid row" style="width: 50%; margin: 0 auto;">
						<div class="box-header with-border text-center custom-box-header">
							<h5 class="text-bold">Please fill-up required informations</h5>
						</div>
						<div class="box-body">
							<div class="form-group">
								<label for="name" class="control-label col-sm-4 required">Name :</label>
								<div class="col-sm-6">
									<div class="input-group">
										<span class="input-group-addon">
											<i class="fa fa-user"></i>
										</span>
										<form:input path="name" id="name" placeholder="Full Name" class="form-control input-sm" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="control-label col-sm-4 required">Email :</label>
								<div class="col-sm-6">
									<div class="input-group">
										<span class="input-group-addon">@</span>
										<form:input id="email" type="email" path="email" placeholder="Email-Address" class="form-control input-sm" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="control-label col-sm-4 required">Password :</label>
								<div class="col-sm-6">
									<div class="input-group">
										<span class="input-group-addon">
											<i class="fa fa-lock"></i>
										</span>
										<form:password id="password" path="password" placeholder="Enter Your Password" class="form-control input-sm" />
										<span class="input-group-addon passwordFormatTooltip">
											<i class="fa fa-info-circle help-tooltip"></i>
										</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="confirmPassword" class="control-label col-sm-4 required">Password Confirm :</label>
								<div class="col-sm-6">
									<div class="input-group">
										<span class="input-group-addon">
											<i class="fa fa-expeditedssl"></i>
										</span>
										<form:password id="confirmPassword" path="confirmPassword" placeholder="Type again your password" class="form-control input-sm" />
										<span class="input-group-addon passwordFormatTooltip">
											<i class="fa fa-info-circle help-tooltip"></i>
										</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="status" class="control-label col-sm-4">Status :</label>
								<div class="col-sm-6">
									<form:select id="status" class="form-control input-sm selectpicker show-tick" path="status">
										<form:option value="ACTIVE" label="Active" />
										<form:option value="TEMPORARY" label="Temporary" />
										<form:option value="LOCK" label="Locked" />
									</form:select>
								</div>
							</div>
						</div>
						<div class="box-footer text-center">
							<button class="btn bg-olive btn-flat small-margin btn-sm btn-social" type="button" id="btnReset">
								<i class="fa fa-recycle"></i>Reset
							</button>
							<button class="btn bg-navy btn-flat small-margin btn-sm btn-social" type="submit" id="btnSave">
								<i class="fa fa-save"></i>Save
							</button>
							<button class="btn btn-danger btn-flat small-margin btn-sm btn-social cancelButton" type="button" id="btnCancel">
								<i class="fa fa-undo"></i>Cancel
							</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>
