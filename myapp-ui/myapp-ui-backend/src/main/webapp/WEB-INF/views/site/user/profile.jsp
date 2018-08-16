<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<section class="content-header">
	<h1>
		Profile
		<small>(Your Personal informations)</small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="${homeUrl}"> Home </a>
		</li>
		<li class="active">Profile</li>
	</ol>
</section>
<section class="content">
	<div class="inline-content">
		<div class="box box-solid small-panel">
			<div class="box-header with-border">
				<div class="profileImagePanel">
					<button type="button" class="btn btn-outline btn-default btn-flat btn-sm btn-imageUpload" id="editImage">Change Picture</button>
					<img class="img-responsive img-thumbnail img-circle" id="profile-img" src="" width="150px" height="150px">
				</div>
			</div>
			<div class="box-body">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<c:set var="submitUrl" value="/user/profile" />
					<spring:url value="${submitUrl}" var="userSubmitUrl" />
					<div class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="nav-tabs-custom" style="box-shadow: none; margin-bottom: 0;">
								<ul class="nav nav-tabs">
									<li class="active">
										<a href="#personalInfo" data-toggle="tab">Personal Info:</a>
									</li>
									<li>
										<a href="#changePasswordInfo" data-toggle="tab">Change Password</a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="personalInfo">
										<form:form id="userForm" class="form-horizontal" action="${userSubmitUrl}" method="post" modelAttribute="user">
											<form:hidden id="contentId" path="contentId" />
											<div class="row">
												<div class="col-sm-6 form-group">
													<label for="name" id="name" class="col-sm-4 control-label required">Name :</label>
													<div class="col-sm-8">
														<form:input type="text" id="name" path="name" class="form-control input-sm" />
													</div>
												</div>
												<div class="col-sm-6 form-group">
													<label for="email" class="col-sm-4 control-label">Email :</label>
													<div class="col-sm-8">
														<form:input type="text" id="email" path="email" disabled="true" class="form-control input-sm" />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-6 form-group">
													<label for="nrc" class="col-sm-4 control-label">NRC :</label>
													<div class="col-sm-8">
														<form:input type="text" id="nrc" path="nrc" class="form-control input-sm" />
													</div>
												</div>
												<div class="col-sm-6 form-group">
													<label for="phone" class="col-sm-4 control-label">Phone :</label>
													<div class="col-sm-8">
														<form:input type="text" id="phone" path="phone" class="form-control input-sm" />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-6 form-group">
													<label for="dob" class="col-sm-4 control-label">DOB :</label>
													<div class="col-sm-8">
														<div class="input-group date">
															<c:if test="${not empty user.dob}">
																<joda:format var="dobValue" value="${user.dob}" pattern="dd-MM-yyyy" />
															</c:if>
															<form:input id="dob" type="text" path="dob" class="form-control input-sm" value="${dobValue}" />
															<span class="input-group-addon">
																<span class="glyphicon glyphicon-calendar"></span>
															</span>
														</div>
													</div>
												</div>
												<div class="col-sm-6 form-group">
													<label for="age" class="col-sm-4 control-label">Age :</label>
													<div class="col-sm-4">
														<form:input type="text" id="age" path="age" class="form-control input-sm" />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="form-group">
													<label for="gender" class="col-sm-2 control-label">Gender :</label>
													<div class="col-sm-9 radio-inline-group">
														<form:radiobutton id="male" data-input-type="iCheck" value="MALE" path="gender" />
														<label for="male">Male</label>
														<form:radiobutton id="female" data-input-type="iCheck" value="FEMALE" path="gender" />
														<label for="female">Female</label>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="form-group">
													<label for="address" class="col-sm-2 control-label">Address :</label>
													<div class="col-sm-9">
														<form:textarea id="address" path="address" rows="5" class="form-control input-sm" placeholder="Address" />
													</div>
												</div>
											</div>
											<div style="text-align: center;">
												<input id="btnSave" type="submit" value="Update profile" class="btn btn-success btn-flat btn-sm">
											</div>
										</form:form>
									</div>
									<div class="tab-pane" id="changePasswordInfo" style="padding: 20px">
										<form class="form-horizontal" id="reset_PasswordForm" role="form">
											<div class="form-group">
												<label for="change_oldPassword" class="control-label col-sm-4 required">Old Password : </label>
												<div class="col-sm-5">
													<div class="input-group">
														<input id="change_oldPassword" name="change_oldPassword" type="password" placeholder="Old Password" class="form-control input-sm" />
														<span class="input-group-addon passwordFormatTooltip">
															<i class="fa fa-info-circle help-tooltip"></i>
														</span>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4 required">New Password : </label>
												<div class="col-sm-5">
													<div class="input-group">
														<input id="change_newPassword" name="change_newPassword" type="password" placeholder="New Password" class="form-control input-sm" />
														<span class="input-group-addon passwordFormatTooltip">
															<i class="fa fa-info-circle help-tooltip"></i>
														</span>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4 required">Confirm New Password : </label>
												<div class="col-sm-5">
													<div class="input-group">
														<input id="change_confirmPassword" name="change_confirmPassword" type="password" placeholder="Confirm New Password" class="form-control input-sm" />
														<span class="input-group-addon passwordFormatTooltip">
															<i class="fa fa-info-circle help-tooltip"></i>
														</span>
													</div>
												</div>
											</div>
										</form>
										<div class="col-sm-12  text-center" style="padding-left: 0px;">
											<button class="btn btn-success btn-flat btn-sm" id="confirmChangePassword">Update Password</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="modal fade" id="profileImageCropper" aria-labelledby="modalLabel" role="dialog" tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">Edit Profile Image</h4>
			</div>
			<div class="modal-body">
				<div>
					<div class="selected_image_panel">
						<div class="preview">
							<img class="img-responsive img-thumbnail img-circle" id="selectedImage" src="" width="150px" height="150px" />
						</div>
					</div>
					<div class="row cropArea">
						<hr />
						<img id="imageHolder" src="" style="display: none" alt="" width="600px" height="250px" />
						<input type="file" id="profilePhotoFileInput" accept="image/*" style="display: none;" />
					</div>
					<div class="docs-toolbar">
						<div class="btn btn-default btn-sm btn-group">
							<button class="btn btn-primary" data-method="zoom" data-option="0.1" type="button" title="Zoom In">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, 0.1)">
									<span class="fa fa-search-plus"></span>
								</span>
							</button>
							<button class="btn btn-primary" data-method="zoom" data-option="-0.1" type="button" title="Zoom Out">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, -0.1)">
									<span class="fa fa-search-minus"></span>
								</span>
							</button>
							<button class="btn btn-primary" data-method="setDragMode" data-option="move" type="button" title="Move">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;setDragMode&quot;, &quot;move&quot;)">
									<span class="fa fa-arrows"></span>
								</span>
							</button>
							<button class="btn btn-primary" data-method="setDragMode" data-option="crop" type="button" title="Crop">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;setDragMode&quot;, &quot;crop&quot;)">
									<span class="fa fa-crop"></span>
								</span>
							</button>
							<button class="btn btn-primary" data-method="clear" type="button" title="Clear">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;clear&quot;)">
									<span class="fa fa-recycle"></span>
								</span>
							</button>
							<button class="btn btn-primary download" type="button" title="Download cropped image">
								<span class="docs-tooltip" data-toggle="tooltip" title="">
									<span class="fa fa-download"></span>
								</span>
							</button>
						</div>
					</div>
				</div>
			</div>
			<div class="box-footer text-center">
				<button type="button" class="btn bg-olive btn-flat small-margin btn-sm btn-primary" id="btnUploadImage">Upload from my Computer</button>
				<button type="button" class="btn bg-teal btn-flat small-margin btn-sm btn-primary" id="btnConfirmImage">Confirm</button>
				<button type="button" class="btn bg-purple btn-flat small-margin btn-sm btn-primary" id="btnSetGravatorImage">Use Random Gravator</button>
			</div>
		</div>
	</div>
</div>
