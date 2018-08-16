package com.github.cataclysmuprising.myapp.ui.backend.config.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.CollectionUtils;

import com.github.cataclysmuprising.myapp.common.exception.BusinessException;
import com.github.cataclysmuprising.myapp.persistence.service.api.RoleService;

public class RoleBasedAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

	private static final Logger applicationLogger = LogManager.getLogger("applicationLogs." + RoleBasedAccessDecisionVoter.class.getName());

	private RoleService roleService;

	public RoleBasedAccessDecisionVoter(RoleService roleService) {
		this.roleService = roleService;
	}

	private static List<String> filterBlackList = Arrays.asList(new String[] { "/login", "/logout", "/error", "/api", "/accessDenied", "/ajax", "/files" });

	@Override
	public int vote(Authentication auth, FilterInvocation filterInvoker, Collection<ConfigAttribute> collection) {
		if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
			String requestURL = filterInvoker.getRequestUrl();
			if (shouldIntercept(requestURL)) {
				applicationLogger.debug("Filtering process executed by RoleBaseAccessDecisionVoter for requested URL ==> {}", requestURL);
				List<String> urlAssociatedRoles = getAssociatedRolesByUrl(requestURL);
				if (urlAssociatedRoles == null || urlAssociatedRoles.isEmpty()) {
					applicationLogger.debug("Access restrictions were not defined for URL ==> {}.", requestURL);
					return ACCESS_ABSTAIN;
				}
				applicationLogger.debug("URL ==> {} was requested for Roles => {} ", requestURL, urlAssociatedRoles);
				List<String> authorities = auth.getAuthorities().parallelStream().map(e -> e.getAuthority()).collect(Collectors.toList());
				applicationLogger.debug("Current Authenticated User has owned ==> {}", authorities);
				boolean hasAuthority = CollectionUtils.containsAny(urlAssociatedRoles, authorities);
				if (hasAuthority) {
					applicationLogger.debug("Access Granted : Filtered by RoleBaseAccessDecisionVoter.");
					return ACCESS_GRANTED;
				}
				else {
					applicationLogger.debug("Access Denied : Filtered by RoleBaseAccessDecisionVoter.");
					return ACCESS_DENIED;
				}
			}
			else {
				applicationLogger.debug("Skipping from RoleBaseAccessDecisionVoter for URL ==> {}. Requested URL was defined as public.", requestURL);
			}
		}
		applicationLogger.debug("Invalid authentication : skipping from accessDecisionVoter.");
		return ACCESS_ABSTAIN;
	}

	public List<String> getAssociatedRolesByUrl(String url) {
		List<String> urlAssociatedRoles = null;
		try {
			urlAssociatedRoles = roleService.selectRolesByActionUrl(url);
		}
		catch (BusinessException e) {
			e.printStackTrace();
		}
		return urlAssociatedRoles;
	}

	private boolean shouldIntercept(String requestURL) {
		for (String blackUrl : filterBlackList) {
			if (requestURL.equals("/") || requestURL.startsWith(blackUrl)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
