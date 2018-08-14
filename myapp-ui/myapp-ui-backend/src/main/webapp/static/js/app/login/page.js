$(document).ready(function(e) {
	$('[data-input-type="iCheck"]').iCheck({
		checkboxClass: 'icheckbox_minimal-red',
		radioClass: 'iradio_minimal-red',
	});

	$("#forgot-password").click(function(e) {
		e.preventDefault();
		var form = $('<form action="' + getContextPath() + '/reset-password" method="POST"/>');
		form.append('<input type="hidden" name="email" value="' + $("#email").val() + '">');
		form.append('<input type="hidden" name="' + $("#csrfToken").attr("name") + '" value="' + $("#csrfToken").val() + '">');
		$(form).appendTo($(document.body)).submit();
	});

});
function getContextPath() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}