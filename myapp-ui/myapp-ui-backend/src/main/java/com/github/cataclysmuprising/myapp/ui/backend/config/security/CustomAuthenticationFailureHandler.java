package com.github.cataclysmuprising.myapp.ui.backend.config.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String causes = "loginfailed";
        Locale locale = localeResolver.resolveLocale(request);
        String errorMessage = messageSource.getMessage("Serverity.common.auth.message.badCredentials", null, locale);
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            causes = "account-disabled";
            errorMessage = messageSource.getMessage("Serverity.common.auth.message.disabled", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
            causes = "account-locked";
            errorMessage = messageSource.getMessage("Serverity.common.auth.message.locked", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            causes = "account-expired";
            errorMessage = messageSource.getMessage("Serverity.common.auth.message.expired", null, locale);
        }
        setDefaultFailureUrl("/login?error=" + causes);
        super.onAuthenticationFailure(request, response, exception);

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
