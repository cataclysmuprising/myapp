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
 *  	myapp-ui-backend - SecurityConfig.java
 *  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
 * 	    Last Modified - 8/14/18 12:24 PM
 *  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
 *  	@Since 2018
 */

package com.github.cataclysmuprising.myapp.ui.backend.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.github.cataclysmuprising.myapp.persistence.service.api.RoleService;
import com.github.cataclysmuprising.myapp.ui.backend.config.security.CustomAuthenticationSuccessHandler;
import com.github.cataclysmuprising.myapp.ui.backend.config.security.RoleBasedAccessDecisionVoter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String REMEMBER_ME_COOKIE = "myapp_rbm";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.successHandler(successHandler);
		http
				.rememberMe()
				.key("ci11c2VyIiwic2NvcGUiOlsiYmFja2VuZCIsInJlYWQiLCJ3cml0ZSIsInVwZG") // hash-key
				.rememberMeCookieName(REMEMBER_ME_COOKIE)
				.tokenValiditySeconds(604800) // 1 week
				.rememberMeParameter("remember-me")
				.userDetailsService(userDetailsService);
		http
				.exceptionHandling()
				.accessDeniedPage("/accessDenied");
		http	
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.deleteCookies(REMEMBER_ME_COOKIE)
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/login");
		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.sessionFixation().migrateSession();
		http
				.authorizeRequests()
				.antMatchers(
						"/login*"
						, "/accessDenied"
						, "/error/*"
				).permitAll()
				.antMatchers("/api/*","/ajax/*","/files/*").access("isAuthenticated()")
				.anyRequest().authenticated()
				.accessDecisionManager(accessDecisionManager(roleService));
		// @formatter:on
	}

	@Bean
	public AccessDecisionManager accessDecisionManager(RoleService roleService) {
		// @formatter:off
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                new RoleBasedAccessDecisionVoter(roleService)                         
        );
     // @formatter:on
		return new UnanimousBased(decisionVoters);
	}
}