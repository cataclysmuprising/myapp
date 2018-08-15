package com.github.cataclysmuprising.myapp.ui.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessage;
import com.github.cataclysmuprising.myapp.common.util.response.PageMessageStyle;
import com.github.cataclysmuprising.myapp.common.util.response.PageMode;
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
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class BaseController {
	protected static final Logger applicationLogger = LogManager.getLogger("applicationLogs." + BaseController.class.getName());

	@Autowired
	protected MessageSource messageSource;
	@Autowired
	protected Environment environment;

	@Value("${build.version}")
	private String projectVersion;

	@Autowired
	private ObjectMapper mapper;

	@ModelAttribute
	public void init(Model model) {
//		SignInUserDetailInfoBean oauthUser = getSignInUserInfo();
//		if (oauthUser != null) {
//			model.addAttribute("loginUserName", oauthUser.getName());
//			model.addAttribute("loginUserId", oauthUser.getId());
//			model.addAttribute("contentId", oauthUser.getContentId());
//			DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MMM,yyyy");
//			String recordRegisterDateString = "";
//			LocalDateTime recordRegisterDate = oauthUser.getRecordRegDate();
//			if (recordRegisterDate != null) {
//				recordRegisterDateString = dateFormatter.print(recordRegisterDate);
//			}
//			model.addAttribute("since", recordRegisterDateString);
//			model.addAttribute("userPreference", userPreference);
//		}
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
			List<String> actions = new ArrayList<>();
			actions.forEach(actionName -> {
				model.addAttribute(actionName, true);
				accessments.put(actionName, true);
			});
			model.addAttribute("accessments", accessments);
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
			}
			else {
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

	public static String getApplicationContextPath(HttpServletRequest request) {
		return request.getRequestURL().toString().replace(request.getServletPath(), "");
	}

	// http://stackoverflow.com/questions/23699371/java-8-distinct-by-property
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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

//	protected Long getLoginUserId() {
//		SignInUserDetailInfoBean oauthUser = getSignInUserInfo();
//		if (oauthUser != null) {
//			return oauthUser.getId();
//		}
//		return null;
//	}
//
//	protected SignInUserDetailInfoBean getSignInUserInfo() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null && !auth.getPrincipal().equals("anonymousUser")) {
//			return mapper.convertValue(auth.getPrincipal(), SignInUserDetailInfoBean.class);
//		}
//		return null;
//	}

	private void setMetaData(Model model) {
		// set the project version
		model.addAttribute("projectVersion", projectVersion);
		// set profile mode
		model.addAttribute("isProduction", !"dev".equals(environment.getActiveProfiles()[0]));
	}

	public abstract void subInit(Model model);

}
