package com.github.cataclysmuprising.myapp.ui.backend.config.security;

import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_PREFIX;
import static com.github.cataclysmuprising.myapp.common.util.LoggerConstants.LOG_SUFFIX;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.cataclysmuprising.myapp.domain.bean.AuthenticatedUserBean;
import com.github.cataclysmuprising.myapp.domain.bean.LoginHistoryBean;
import com.github.cataclysmuprising.myapp.persistence.service.api.LoginHistoryService;
import com.github.cataclysmuprising.myapp.persistence.service.api.UserService;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private UserService userService;
	@Autowired
	private LoginHistoryService loginHistoryService;

	private static final Logger applicationLogger = LogManager.getLogger("applicationLogs." + CustomAuthenticationSuccessHandler.class.getName());
	private static final Logger errorLogger = LogManager.getLogger("errorLogs." + CustomAuthenticationSuccessHandler.class.getName());

	private RequestCache requestCache = new HttpSessionRequestCache();

	public CustomAuthenticationSuccessHandler() {
		super();
		setUseReferer(true);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		String email = authentication.getName();
		applicationLogger.info(LOG_PREFIX + "User with Email '" + email + "' has successfully signed in." + LOG_SUFFIX);
		try {
			AuthenticatedUserBean authUser = userService.selectAuthenticatedUser(email);
			if (authUser == null) {
				throw new UsernameNotFoundException("User doesn`t exist");
			}
			LoginHistoryBean loginHistory = new LoginHistoryBean();
			loginHistory.setIpAddress(getClientIp(request));
			loginHistory.setOs(getOperatingSystem(request));
			loginHistory.setUserAgent(getUserAgent(request));
			loginHistory.setLoginDate(DateTime.now());
			loginHistory.setUserId(authUser.getId());
			loginHistoryService.insert(loginHistory, authUser.getId());
			applicationLogger.info(LOG_PREFIX + "Recorded in loginHistory for Email '" + email + "'." + LOG_SUFFIX);
		}
		catch (Exception e) {
			e.printStackTrace();
			errorLogger.error(LOG_PREFIX + "Can't save in loginHistory for Email '" + email + "'" + LOG_SUFFIX, e);
		}
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			String targetUrl = "/dashboard";
			if (response.isCommitted()) {
				logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
				return;
			}

			getRedirectStrategy().sendRedirect(request, response, targetUrl);
			return;
		}
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}
		clearAuthenticationAttributes(request);
		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		applicationLogger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	@Override
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	private String getClientIp(HttpServletRequest request) {
		return request.getRemoteAddr();

	}

	private String getOperatingSystem(HttpServletRequest request) {
		String os = "";
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		}
		else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		}
		else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		}
		else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		}
		else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		}
		else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		return os;
	}

	private String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		String user = userAgent.toLowerCase();

		String browser = "";
		if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		}
		else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		}
		else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")) {
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			}
			else if (user.contains("opr")) {
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
			}
		}
		else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		}
		else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";

		}
		else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		}
		else if (user.contains("rv")) {
			browser = "IE";
		}
		else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		return browser;
	}
}
