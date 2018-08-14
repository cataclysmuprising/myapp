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
 *  	myapp-ui-backend - CheckValidationAspect.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/14/18 10:47 AM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */
package com.github.cataclysmuprising.myapp.ui.backend.common.aspect;

import com.github.cataclysmuprising.myapp.common.domain.bean.BaseBean;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessage;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessageStyle;
import com.github.cataclysmuprising.myapp.common.util.response.PageMode;
import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.ValidateEntity;
import com.github.cataclysmuprising.myapp.ui.backend.common.exception.ValidationFailedException;
import com.github.cataclysmuprising.myapp.ui.backend.common.validation.BaseValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@Aspect
@Order(1)
public class CheckValidationAspect extends BaseAspect {
	private static final Logger errorLogger = LogManager.getLogger("errorLogs." + CheckValidationAspect.class.getName());
	private ApplicationContext appContext;

	private MessageSource messageSource;

	@Autowired
	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Before(value = "methodAnnotatedWithValidateEntity(validateEntity) && publicMethod() && !initBinderMethod()")
	public void handleExeptionforServletMethods(JoinPoint joinPoint, ValidateEntity validateEntity) throws Throwable {
		Locale locale = LocaleContextHolder.getLocale();
		String errorView = validateEntity.errorView();
		PageMode pageMode = validateEntity.pageMode();
		Model model = null;
		BeanPropertyBindingResult beanBindingResults = null;
		Object validationTarget = null;
		Object[] arguments = joinPoint.getArgs();
		for (Object arg : arguments) {
			if (arg != null) {
				if (arg instanceof BindingAwareModelMap) {
					model = (Model) arg;
				}
				if (arg instanceof BeanPropertyBindingResult) {
					beanBindingResults = (BeanPropertyBindingResult) arg;
				}
				if (arg instanceof BaseBean) {
					validationTarget = arg;
				}
			}
		}
		BaseValidator validator = appContext.getBean(validateEntity.validator());
		validator.setPageMode(pageMode);
		validator.validate(validationTarget, beanBindingResults);
		if (beanBindingResults.hasErrors()) {
			errorLogger.error(LOG_PREFIX + "Validation failed for '" + validationTarget.getClass().getSimpleName() + "'." + LOG_SUFFIX);
			errorLogger.error(LOG_BREAKER_OPEN);
			if (errorLogger.isDebugEnabled()) {
				Map<String, String> validationErrors = new HashMap<>();
				List<FieldError> errorFields = beanBindingResults.getFieldErrors();
				errorFields.forEach(item -> {
					if (!validationErrors.containsKey(item.getField())) {
						validationErrors.put(item.getField(), item.getDefaultMessage());
					}
				});
				validationErrors.entrySet().forEach(entry -> {
					errorLogger.debug(entry.getKey() + " ==> " + entry.getValue());
				});
			}
			errorLogger.error(LOG_BREAKER_CLOSE);
			if (model != null) {
				model.addAttribute("pageMode", pageMode);
			}
			if (model != null) {
				Map<String, String> validationErrors = new HashMap<>();
				List<FieldError> errorFields = beanBindingResults.getFieldErrors();
				errorFields.forEach(item -> {
					if (!validationErrors.containsKey(item.getField())) {
						validationErrors.put(item.getField(), item.getDefaultMessage());
					}
				});
				model.addAttribute("validationErrors", validationErrors);
				model.addAttribute("pageMessage", new PageMessage("Validation Error", messageSource.getMessage("Validation.common.Page.ValidationErrorMessage", null, locale),
						PageMessageStyle.ERROR.getValue()));
			}
			throw new ValidationFailedException(model, errorView);
		}
	}

	@Pointcut("@annotation(validateEntity)")
	public void methodAnnotatedWithValidateEntity(ValidateEntity validateEntity) {
	}
}
