<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  ~
  ~   This source file is free software, available under the following license: MIT license.
  ~   Copyright (c) 2018, Than Htike Aung(https://github.com/cataclysmuprising) All Rights Reserved.
  ~
  ~   Permission is hereby granted, free of charge, to any person obtaining a copy
  ~   of this software and associated documentation files (the "Software"), to deal
  ~   in the Software without restriction, including without limitation the rights
  ~   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  ~   copies of the Software, and to permit persons to whom the Software is
  ~   furnished to do so, subject to the following conditions:
  ~
  ~   The above copyright notice and this permission notice shall be included in all
  ~   copies or substantial portions of the Software.
  ~
  ~   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  ~   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  ~   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  ~   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  ~   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  ~   SOFTWARE.
  ~
  ~  	myapp-ui-backend - 409.jsp
  ~  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
  ~ 	    Last Modified - 8/8/18 11:16 AM
  ~  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
  ~  	@Since 2018
  --%>

<section class="card">
	<div class="cardHeader">
		<h1>
			<span>
				<strong>4</strong>
			</span>
			<span>
				<strong>0</strong>
			</span>
			<span>
				<strong>9</strong>
			</span>
		</h1>
	</div>
	<div class="cardContent">
		<h2>
			Error !
			<span>Already Reported</span>
		</h2>
		<hr />
		<div class="cardFooter">
			<p>Your last submitted informations contain duplicated value(s) that the server won't allow to process.</p>
			<spring:url value="/" var="homeUrl" />
			<spring:url value="/login" var="loginUrl" />
			Contact your administrators (or) Go
			<a href="${header.referer}">Back</a>
			to your last submitted form.
		</div>
	</div>
</section>