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
	        "email" : {
	            required : true,
	            email : true
	        },
	        "password" : {
		        required : {
			        depends : function(element) {
				        return getPageMode() == "CREATE";
			        }
		        }
	        },
	        "confirmPassword" : {
	            required : {
		            depends : function(element) {
			            return getPageMode() == "CREATE";
		            }
	            },
	            equalTo : "#password"
	        },
	    },
	    messages : {
	        "name" : {
	            required : "'Name' should not be empty.",
	            maxlength : "'Name' should not exceeds 50 characters."
	        },
	        "email" : {
	            required : "'Email' should not be empty.",
	            email : "Email address is invalid email format."
	        },
	        "password" : "'Password' should not be empty.",
	        "confirmPassword" : {
	            required : "'Password Confirm' should not be empty.",
	            equalTo : "'Password' and 'Password Confirm' do not match."
	        },
	    }
	});
}
