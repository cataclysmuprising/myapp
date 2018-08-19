/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - BaseController.java
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
package com.github.cataclysmuprising.myapp.ui.backend.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessage;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessageStyle;
import com.github.cataclysmuprising.myapp.common.util.response.PageMode;
import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.persistence.service.api.ActionService;
import com.github.cataclysmuprising.myapp.ui.backend.common.validation.BaseValidator;
import com.github.cataclysmuprising.myapp.ui.backend.config.security.LoggedUserBean;

public abstract class BaseController {
    protected static final Logger applicationLogger = LogManager.getLogger("applicationLogs." + BaseController.class.getName());

    @Autowired
    protected MessageSource messageSource;
    @Autowired
    protected Environment environment;
    @Autowired
    private ActionService actionService;

    @Resource(name = "passwordEncoder")
    protected PasswordEncoder passwordEncoder;

    @Value("${build.version}")
    private String projectVersion;

    @Autowired
    protected BaseValidator baseValidator;

    @Autowired
    private ObjectMapper mapper;

    @ModelAttribute
    public void init(Model model) {
        LoggedUserBean authUser = getSignInUserInfo();
        if (authUser != null) {
            AuthenticatedUserBean user = authUser.getUserDetail();
            model.addAttribute("loginUserName", user.getName());
            model.addAttribute("loginUserId", user.getId());
            model.addAttribute("contentId", user.getContentId());
            model.addAttribute("since", user.getSince());
        }
        setMetaData(model);
        subInit(model);
    }

    protected void setAuthorities(Model model, String page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            model.addAttribute("page", page.toLowerCase());
            List<String> loginUserRoles = new ArrayList<>();
            auth.getAuthorities().forEach(role -> {
                loginUserRoles.add(role.getAuthority());
            });
            HashMap<String, Boolean> accessments = new HashMap<>();
            HashMap<String, Object> request = new HashMap<>();
            // capitalize start character
            if (!Character.isUpperCase(page.charAt(0))) {
                page = page.substring(0, 1).toUpperCase() + page.substring(1);
            }
            request.put("pageName", page);
            LoggedUserBean authUser = getSignInUserInfo();
            AuthenticatedUserBean user = authUser.getUserDetail();
            List<String> actions = null;
            try {
                actions = actionService.selectAvailableActionsForUser(page, user.getRoleIds());
            } catch (BusinessException e) {
                e.printStackTrace();
            }
            if (actions != null) {
                actions.forEach(actionName -> {
                    model.addAttribute(actionName, true);
                    accessments.put(actionName, true);
                });
                model.addAttribute("accessments", accessments);
            }
        }
    }

    protected String getPureString(String input) {
        return input.replaceAll("[^a-zA-Z0-9> \\/\\-\\.]+", "").trim();
    }

    protected void setFormFieldErrors(Errors errors, Model model, PageMode pageMode) {
        model.addAttribute("pageMode", pageMode);
        Map<String, String> validationErrors = new HashMap<>();
        List<FieldError> errorFields = errors.getFieldErrors();
        errorFields.forEach(item -> {
            if (!validationErrors.containsKey(item.getField())) {
                validationErrors.put(item.getField(), item.getDefaultMessage());
            }
        });
        model.addAttribute("validationErrors", validationErrors);
        setPageMessage(model, "Validation Error", "Validation.common.Page.ValidationErrorMessage", PageMessageStyle.ERROR);
    }

    protected Map<String, Object> setAjaxFormFieldErrors(Errors errors, String errorKeyPrefix) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        response.put("status", HttpStatus.METHOD_NOT_ALLOWED);

        List<FieldError> errorFields = errors.getFieldErrors();
        errorFields.forEach(item -> {
            if (errorKeyPrefix != null) {
                if (!fieldErrors.containsKey(errorKeyPrefix + item.getField())) {
                    fieldErrors.put(errorKeyPrefix + item.getField(), item.getDefaultMessage());
                }
            } else {
                if (!fieldErrors.containsKey(item.getField())) {
                    fieldErrors.put(item.getField(), item.getDefaultMessage());
                }
            }
        });
        response.put("fieldErrors", fieldErrors);
        response.put("type", "validationError");
        setAjaxPageMessage(response, "Validation Error", "Validation.common.Page.ValidationErrorMessage", PageMessageStyle.ERROR);
        return response;
    }

    protected void setPageMessage(Model model, String messageTitle, String messageCode, PageMessageStyle style, Object... messageParams) {
        model.addAttribute("pageMessage", new PageMessage(messageTitle, messageSource.getMessage(messageCode, messageParams, Locale.ENGLISH), style.getValue()));
    }

    protected void setPageMessage(RedirectAttributes redirectAttributes, String messageTitle, String messageCode, PageMessageStyle style, Object... messageParams) {
        redirectAttributes.addFlashAttribute("pageMessage", new PageMessage(messageTitle, messageSource.getMessage(messageCode, messageParams, Locale.ENGLISH), style.getValue()));
    }

    protected Map<String, Object> setAjaxPageMessage(Map<String, Object> response, String messageTitle, String messageCode, PageMessageStyle style, Object... messageParams) {
        if (response == null) {
            response = new HashMap<>();
        }
        response.put("pageMessage", new PageMessage(messageTitle, messageSource.getMessage(messageCode, messageParams, Locale.ENGLISH), style.getValue()));
        return response;
    }

    protected boolean isRolePresent(Collection<? extends GrantedAuthority> collection, String role) {
        boolean isRolePresent = false;
        for (GrantedAuthority grantedAuthority : collection) {
            isRolePresent = grantedAuthority.getAuthority().equals(role);
            if (isRolePresent) {
                break;
            }
        }
        return isRolePresent;
    }

    protected Long getLoginUserId() {
        LoggedUserBean authUser = getSignInUserInfo();
        if (authUser != null) {
            return authUser.getId();
        }
        return null;
    }

    protected LoggedUserBean getSignInUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getPrincipal().equals("anonymousUser")) {
            return mapper.convertValue(auth.getPrincipal(), LoggedUserBean.class);
        }
        return null;
    }

    private void setMetaData(Model model) {
        // set the project version
        model.addAttribute("projectVersion", projectVersion);
        // set profile mode
        model.addAttribute("isProduction", !"dev".equals(environment.getActiveProfiles()[0]));
    }

    public abstract void subInit(Model model);
}
