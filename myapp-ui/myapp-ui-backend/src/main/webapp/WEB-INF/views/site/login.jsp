<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<body class="hold-transition login-page">
<section class="login-box">
    <div class="login-logo">
        <img src="${root}/static/images/logo.png">
        <span class="site-name">MyApp Backend</span>
    </div>
    <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>
        <form action="login" method="post">
            <c:if test="${not empty pageMessage}">
                <div class="alert alert-sm ${messageStyle} fade in">
                    <a class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <div class="message">${pageMessage}</div>
                </div>
            </c:if>
            <input id="csrfToken" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group has-feedback">
                <input type="text" name="email" id="email" class="form-control" placeholder="Email" value="${email}">
                <span class="fa fa-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" id="password" class="form-control" placeholder="Password">
                <span class="fa fa-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input type="checkbox" data-input-type="iCheck" name="remember-me">
                            <span class="lblRememberMe">Keep me signed in</span>
                        </label>
                    </div>
                </div>
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                </div>
            </div>
        </form>
    </div>
    <div class="login-box-footer text-center">
        <a href="#" id="forgot-password">Forgot your password ?</a>
    </div>
</section>
</body>
