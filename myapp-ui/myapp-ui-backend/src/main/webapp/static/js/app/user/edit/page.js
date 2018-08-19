function init() {
	initValidator();
}

function bind() {
	$('.passwordFormatTooltip').tooltipster({
	    animation : 'fade',
	    delay : 200,
	    maxWidth : 300,
	    trigger : 'hover',
	    content : 'Password must be minimum of 8 characters with (at least 1 uppercase alphabet, 1 lowercase alphabet, 1 number and 1 special character) but should not start with special characters.'

	});

	$("#btnReset").on("click", function(e) {
		$("form").trigger("reset");
		// $("#status option:first").prop('selected', true).selectpicker('refresh');
	});

	$("#btnResetPassword").on("click", function(e) {
		e.preventDefault();
		$("#resetPasswordModal").modal({
		    backdrop : 'static',
		    keyboard : false
		});
	});

	$("#confirmResetPassword").click(function(e) {
		var loginId = $(this).attr("data-id");
		$.ajax({
		    url : getContextPath() + "/user/" + $("#userId").val() + "/resetPassword",
		    type : "POST",
		    data : {
		        'newPassword' : $("#reset_newPassword").val(),
		        'confirmPassword' : $("#reset_confirmPassword").val(),
		    },
		    contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		    success : function(response) {
			    handleServerResponse(response);
		    }
		});
	});

	$("#btnCancel").on("click", function(e) {
		goToHomePage();
	});

}

function initValidator() {
	$("#userForm").validate({
	    rules : {
		    "name" : {
		        required : true,
		        maxlength : 50
		    },
	    },
	    messages : {
		    "name" : {
		        required : "'Name' should not be empty.",
		        maxlength : "'Name' should not exceeds 50 characters."
		    },
	    }
	});
}
