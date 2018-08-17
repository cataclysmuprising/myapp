/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - GlobalExceptionHandler.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprising.myapp.ui.backend.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.ContentNotFoundException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.util.LoggerConstants;
import com.github.cataclysmuprising.myapp.ui.backend.common.exception.ValidationFailedException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    private static final Logger errorLogger = LogManager.getLogger("errorLogs." + GlobalExceptionHandler.class.getName());
    @Autowired
    protected Environment environment;

    @ExceptionHandler(HttpSessionRequiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleSessionExpired(Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error("Session was exipired!");
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/401", auth);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAuthenticationException(AuthenticationException e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/403", auth);
    }

    @ExceptionHandler({ NoHandlerFoundException.class, ContentNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoHandlerFoundException(Exception e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/404", auth);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleUnAuthorizeAccess(Exception e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/403", auth);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class, UnsatisfiedServletRequestParameterException.class, ServletRequestBindingException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBadRequest(Exception e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/405", auth);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView handleMethodArgumentTypeMismatchException(BusinessException e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/405", auth);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, NoSuchMethodException.class, SecurityException.class, HttpRequestMethodNotSupportedException.class })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView handleMethodNotAllowed(Exception e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/405", auth);
    }

    @ExceptionHandler(DuplicatedEntryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView handleDuplicatedEntryException(DuplicatedEntryException e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/409", auth);
    }

    @ExceptionHandler(ConsistencyViolationException.class)
    @ResponseStatus(HttpStatus.IM_USED)
    public ModelAndView handleConsistencyViolationException(ConsistencyViolationException e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/226", auth);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleBusinessException(BusinessException e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/500", auth);
    }

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ModelAndView handleValidationFailedException(ValidationFailedException e, Authentication auth) {
        if (e.getModel() != null && e.getErrorView() != null) {
            return new ModelAndView(e.getErrorView(), e.getModel().asMap());
        } else {
            return getErrorView("error/500", auth);
        }
    }

    @ExceptionHandler({ RuntimeException.class, Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleServerError(Exception e, Authentication auth) {
        errorLogger.error(LoggerConstants.LOG_BREAKER_OPEN);
        errorLogger.error(e.getMessage(), e);
        errorLogger.error(LoggerConstants.LOG_BREAKER_CLOSE);
        return getErrorView("error/500", auth);
    }

    private ModelAndView getErrorView(String errorPage, Authentication auth) {
        ModelAndView modelAndView = new ModelAndView(errorPage);
        // set the project version
        modelAndView.addObject("projectVersion", "1.0");
        // set profile mode
        modelAndView.addObject("isProduction", "prod".equals(environment.getActiveProfiles()[0]));
        return modelAndView;
    }
}
