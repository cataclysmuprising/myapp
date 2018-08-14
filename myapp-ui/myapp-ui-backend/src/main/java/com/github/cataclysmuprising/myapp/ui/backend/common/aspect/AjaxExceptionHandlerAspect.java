/*
 *
 *   This source file is free software, available under the following license: MIT license.
 *   Copyright (c) 2018, Than Htike Aung(https://github.com/cataclysmuprising) All Rights Reserved.
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 *
 *  	myapp-ui-backend - AjaxExceptionHandlerAspect.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/14/18 10:44 AM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */
package com.github.cataclysmuprising.myapp.ui.backend.common.aspect;

import com.github.cataclysmuprising.myapp.common.exception.ConsistencyViolationException;
import com.github.cataclysmuprising.myapp.common.exception.DuplicatedEntryException;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessage;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessageStyle;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.HandleAjaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@Aspect
@Order(1)
public class AjaxExceptionHandlerAspect extends BaseAspect {
	private static final Logger errorLogger = LogManager.getLogger("errorLogs." + AjaxExceptionHandlerAspect.class.getName());

	private MessageSource messageSource;
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@SuppressWarnings("unchecked")
	@Around(value = "methodAnnotatedWithHandleAjaxException(ajaxException) && publicMethod() && !initBinderMethod()")
	public @ResponseBody
	Map<String, Object> handleExeptionforajaxMethods(ProceedingJoinPoint joinPoint, HandleAjaxException ajaxException) throws Throwable {
		Map<String, Object> response = new HashMap<>();
		try {
			response = (Map<String, Object>) joinPoint.proceed();
		} catch (Exception e) {
			errorLogger.error(LOG_BREAKER_OPEN);
			errorLogger.error(e.getMessage(), e);
			errorLogger.error(LOG_BREAKER_CLOSE);
			response.put("status", HttpStatus.BAD_REQUEST);
			// default title and message
			String title = "Bad Request";
			String message = messageSource.getMessage("Serverity.common.Page.AjaxError", null, Locale.ENGLISH);

			if (e instanceof ServletRequestBindingException) {
				title = "";
				message = e.getMessage();
				// send an e-mail to authorized person
			} else if (e instanceof DuplicatedEntryException) {
				title = "Duplicated";
				message = messageSource.getMessage("Serverity.common.Page.DuplicatedRecordErrorMessage", null, Locale.ENGLISH);
			} else if (e instanceof ConsistencyViolationException) {
				title = "Rejected";
				message = messageSource.getMessage("Serverity.common.Page.ConsistencyViolationErrorMessage", null, Locale.ENGLISH);
			}
			response.put("pageMessage", new PageMessage(title, message, PageMessageStyle.ERROR.getValue()));
		}
		return response;
	}

	@Pointcut("@annotation(ajaxException)")
	public void methodAnnotatedWithHandleAjaxException(HandleAjaxException ajaxException) {
	}
}
