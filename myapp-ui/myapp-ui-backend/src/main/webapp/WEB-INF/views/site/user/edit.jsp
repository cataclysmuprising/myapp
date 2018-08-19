<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		User Setup <small> (Edit existing User) </small>
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
		<li class="active">Edit</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="inline-content">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<c:set var="submitUrl" value="/user/${userDto.id}/edit" />
				<spring:url value="${submitUrl}" var="userSubmitUrl" />
				<form:form class="form-horizontal" modelAttribute="userDto" id="userForm" action="${userSubmitUrl}" method="post">
					<form:hidden path="id" id = "userId"/>
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
								<label class="control-label col-sm-4">Email :</label>
								<div class="col-sm-6">
									<div class="input-group">
										<span class="input-group-addon">@</span>
										<form:input id="email" type="email" path="email" placeholder="Email-Address" class="form-control input-sm" readonly="true" tabindex="-1" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="status" class="control-label col-sm-4">Status :</label>
								<div class="col-sm-6">
									<div class="input-group">
										<span class="input-group-addon">
											<i class="fa fa-unlock-alt"></i>
										</span>
										<form:select id="status" class="form-control input-sm selectpicker show-tick" data-selected="userDto.status" path="status">
											<form:option value="ACTIVE" label="Active" />
											<form:option value="TEMPORARY" label="Temporary" />
											<form:option value="LOCK" label="Locked" />
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4">Password :</label>
								<div class="col-sm-6">
									<button type="button" class="btn btn-primary btn-flat btn-sm btn-social" data-id="${userDto.id}" id="btnResetPassword">
										<i class="fa fa-eraser"></i>Reset Password
									</button>
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
<div class="modal fade" id="resetPasswordModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">Reset Password</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="reset_PasswordForm" role="form">
					<div class="form-group">
						<label class="control-label col-sm-4 required">New Password : </label>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">
									<i class="fa fa-lock"></i>
								</span>
								<input type="password" id="reset_newPassword" placeholder="Define for new password" class="form-control input-sm" />
								<span class="input-group-addon passwordFormatTooltip">
									<i class="fa fa-info-circle help-tooltip"></i>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4 required">Confirm Password : </label>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">
									<i class="fa fa-expeditedssl"></i>
								</span>
								<input type="password" id="reset_confirmPassword" placeholder="Write your new password again" class="form-control input-sm" />
								<span class="input-group-addon passwordFormatTooltip">
									<i class="fa fa-info-circle help-tooltip"></i>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="box-footer text-center">
				<button class="btn bg-navy btn-flat small-margin btn-sm btn-social" type="button" id="confirmResetPassword">
					<i class="fa fa-save"></i>Save
				</button>
				<button class="btn btn-danger btn-flat btn-sm btn-social" type="button" data-dismiss="modal">
					<i class="fa fa-undo"></i>Cancel
				</button>
			</div>
		</div>
	</div>
</div>