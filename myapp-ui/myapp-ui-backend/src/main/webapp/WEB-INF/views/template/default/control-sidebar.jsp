<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
  ~  	myapp-ui-backend - control-sidebar.jsp
  ~  	Using Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
  ~ 	    Last Modified - 8/8/18 11:16 AM
  ~  	@author Than Htike Aung {@literal <rage.cataclysm@gmail.com>}
  ~  	@Since 2018
  --%>

<aside class="control-sidebar control-sidebar-${empty userPreference.themeSidebarSkin?'dark':userPreference.themeSidebarSkin}">
	<!-- Create the tabs -->
	<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
		<li class="active">
			<a href="#control-sidebar-theme-options-tab" data-toggle="tab">
				<i class="fa fa-desktop"></i>
			</a>
		</li>
		<li>
			<a href="#control-sidebar-home-tab" data-toggle="tab">
				<i class="fa fa-list-alt"></i>
			</a>
		</li>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content">
		<div id="control-sidebar-theme-options-tab" class="tab-pane active">
			<h4 class="control-sidebar-heading">Layout Options</h4>
			<div class="form-group">
				<div class="pull-right">
					<input id="normalLayout" checked="checked" data-layout="normal" data-input-type="iCheck" name="layoutStyle" type="radio">
				</div>
				<label for="normalLayout" class="control-sidebar-subheading">Normal layout</label>
				<p>Normal layout. Default layout for full width.</p>
			</div>
			<div class="form-group">
				<div class="pull-right">
					<input id="fixedLayout" data-layout="fixed" data-input-type="iCheck" name="layoutStyle" type="radio">
				</div>
				<label for="fixedLayout" class="control-sidebar-subheading">Fixed layout</label>
				<p>Fixed layout. Fix menu-bar and navigator.</p>
			</div>
			<div class="form-group">
				<div class="pull-right">
					<input id="boxedLayout" data-layout="layout-boxed" data-input-type="iCheck" name="layoutStyle" type="radio">
				</div>
				<label for="boxedLayout" class="control-sidebar-subheading">Boxed Layout</label>
				<p>Boxed layout. Show screen as fit-width.</p>
			</div>
			<hr />
			<div class="form-group">
				<div class="pull-right">
					<input id="sidebar-collapse" data-sidebar="sidebar-collapse" class="pull-right" data-input-type="iCheck" type="checkbox">
				</div>
				<label for="sidebar-collapse" class="control-sidebar-subheading"> Toggle Sidebar </label>
				<p>Toggle the left sidebar's state (open or collapse)</p>
			</div>
			<div class="form-group">
				<div class="pull-right">
					<input id="data-sidebarskin" data-sidebarskin="toggle" class="pull-right" data-input-type="iCheck" type="checkbox">
				</div>
				<label for="data-sidebarskin" class="control-sidebar-subheading"> Toggle Right Sidebar Skin </label>
				<p>Toggle between dark and light skins for the right sidebar</p>
			</div>
		</div>
		<div class="tab-pane" id="control-sidebar-home-tab">
			<h4 class="control-sidebar-heading">Skins</h4>
			<ul class="list-unstyled clearfix available-skins">
				<li class="active">
					<div data-skin="skin-blue" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left" style="background: #367fa9"></span>
							<span class="skin-header right bg-light-blue"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #222d32"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Blue</p>
				</li>
				<li>
					<div data-skin="skin-black" class="skin-item clearfix full-opacity-hover">
						<div style="box-shadow: 0 0 2px rgba(0, 0, 0, 0.1)" class="clearfix">
							<span class="skin-header left" style="background: #4f4f4f"></span>
							<span style="background: #666" class="skin-header right"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #222"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Brown</p>
				</li>
				<li>
					<div data-skin="skin-purple" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left bg-purple-active"></span>
							<span class="bg-purple skin-header right"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #222d32"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Purple</p>
				</li>
				<li>
					<div data-skin="skin-green" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left bg-green-active"></span>
							<span class="skin-header right bg-green"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #222d32"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Green</p>
				</li>
				<li>
					<div data-skin="skin-red" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left" style="background: #70061A"></span>
							<span class="skin-header right" style="background: #7F071D"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #222d32"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Red</p>
				</li>
				<li>
					<div data-skin="skin-yellow" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left bg-yellow-active"></span>
							<span class="skin-header right bg-yellow"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #222d32"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Yellow</p>
				</li>
				<li>
					<div data-skin="skin-blue-light" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left" style="background: #367fa9"></span>
							<span class="skin-header right bg-light-blue"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #f9fafc"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Blue Light</p>
				</li>
				<li>
					<div data-skin="skin-black-light" class="skin-item clearfix full-opacity-hover">
						<div style="box-shadow: 0 0 2px rgba(0, 0, 0, 0.1)" class="clearfix">
							<span class="skin-header left" style="background: #fefefe"></span>
							<span style="background: #fefefe" class="skin-header right"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #f9fafc"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Black Light</p>
				</li>
				<li>
					<div data-skin="skin-purple-light" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left bg-purple-active"></span>
							<span class="skin-header right bg-purple"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #f9fafc"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Purple Light</p>
				</li>
				<li>
					<div data-skin="skin-green-light" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left bg-green-active"></span>
							<span class="skin-header right bg-green"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #f9fafc"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Green Light</p>
				</li>
				<li>
					<div data-skin="skin-red-light" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left" style="background: #70061A"></span>
							<span class="skin-header right" style="background: #7F071D"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #f9fafc"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Red Light</p>
				</li>
				<li>
					<div data-skin="skin-yellow-light" class="skin-item clearfix full-opacity-hover">
						<div>
							<span class="skin-header left bg-yellow-active"></span>
							<span class="skin-header right bg-yellow"></span>
						</div>
						<div>
							<span class="skin-body left" style="background: #f9fafc"></span>
							<span class="skin-body right" style="background: #f4f5f7"></span>
						</div>
					</div>
					<p class="text-center no-margin">Yellow Light</p>
				</li>
			</ul>
		</div>
	</div>
</aside>
<div class="control-sidebar-bg"></div>