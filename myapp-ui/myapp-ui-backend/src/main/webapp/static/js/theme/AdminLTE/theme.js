/*! AdminLTE app.js
* ================
* Main JS application file for AdminLTE v2. This file
* should be included in all pages. It controls some layout
* options and implements exclusive AdminLTE plugins.
*
* @Author  Almsaeed Studio
* @Support <https://www.almsaeedstudio.com>
* @Email   <abdullah@almsaeedstudio.com>
* @version 2.4.0
* @repository git://github.com/almasaeed2010/AdminLTE.git
* @license MIT <http://opensource.org/licenses/MIT>
*/

// Make sure jQuery has been loaded
if (typeof jQuery === 'undefined') {
	throw new Error('AdminLTE requires jQuery')
}

/*
 * Layout() ======== Implements AdminLTE layout. Fixes the layout height in case min-height fails.
 * 
 * @usage activated automatically upon window load. Configure any options by passing data-option="value" to the body tag.
 */
+function($) {
	'use strict'

	var DataKey = 'lte.layout'

	var Default = {
		slimscroll: true,
		resetHeight: true
	}

	var Selector = {
		wrapper: '.wrapper',
		contentWrapper: '.content-wrapper',
		layoutBoxed: '.layout-boxed',
		mainFooter: '.main-footer',
		mainHeader: '.main-header',
		sidebar: '.sidebar',
		controlSidebar: '.control-sidebar',
		fixed: '.fixed',
		sidebarMenu: '.sidebar-menu',
		logo: '.main-header .logo'
	}

	var ClassName = {
		fixed: 'fixed',
		holdTransition: 'hold-transition'
	}

	var Layout = function(options) {
		this.options = options
		this.bindedResize = false
		this.activate()
	}

	Layout.prototype.activate = function() {
		this.fix()
		this.fixSidebar()

		$('body').removeClass(ClassName.holdTransition)

		if (this.options.resetHeight) {
			$('body, html, ' + Selector.wrapper).css({
				'height': 'auto',
				'min-height': '100%'
			})
		}

		if (!this.bindedResize) {
			$(window).resize(function() {
				this.fix()
				this.fixSidebar()

				$(Selector.logo + ', ' + Selector.sidebar).one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function() {
					this.fix()
					this.fixSidebar()
				}.bind(this))
			}.bind(this))

			this.bindedResize = true
		}

		$(Selector.sidebarMenu).on('expanded.tree', function() {
			this.fix()
			this.fixSidebar()
		}.bind(this))

		$(Selector.sidebarMenu).on('collapsed.tree', function() {
			this.fix()
			this.fixSidebar()
		}.bind(this))
	}

	Layout.prototype.fix = function() {
		// Remove overflow from .wrapper if layout-boxed exists
		$(Selector.layoutBoxed + ' > ' + Selector.wrapper).css('overflow', 'hidden')

		// Get window height and the wrapper height
		var footerHeight = $(Selector.mainFooter).outerHeight() || 0
		var neg = $(Selector.mainHeader).outerHeight() + footerHeight
		var windowHeight = $(window).height()
		var sidebarHeight = $(Selector.sidebar).height() || 0

		// Set the min-height of the content and sidebar based on
		// the height of the document.
		var inlineContentDiff = 70;
		if ($('body').hasClass(ClassName.fixed)) {
			var minContentHeight = windowHeight - footerHeight;
			$(Selector.contentWrapper).css('min-height', minContentHeight);
			$('.inline-content').css('min-height', minContentHeight - inlineContentDiff);

		}
		else {
			var postSetHeight;

			if (windowHeight >= sidebarHeight) {
				var minContentHeight = windowHeight - neg;
				$(Selector.contentWrapper).css('min-height', minContentHeight);
				$('.inline-content').css('min-height', minContentHeight - inlineContentDiff);
				postSetHeight = windowHeight - neg;
			}
			else {
				$(Selector.contentWrapper).css('min-height', sidebarHeight);
				$('.inline-content').css('min-height', sidebarHeight - inlineContentDiff);
				postSetHeight = sidebarHeight;
			}

			// Fix for the control sidebar height
			var $controlSidebar = $(Selector.controlSidebar)
			if (typeof $controlSidebar !== 'undefined') {
				if ($controlSidebar.height() > postSetHeight) {
					$(Selector.contentWrapper).css('min-height', $controlSidebar.height());
					$('.inline-content').css('min-height', $controlSidebar.height() - inlineContentDiff)
				}
			}
		}
	}

	Layout.prototype.fixSidebar = function() {
		// Make sure the body tag has the .fixed class
		if (!$('body').hasClass(ClassName.fixed)) {
			if (typeof $.fn.slimScroll !== 'undefined') {
				$(Selector.sidebar).slimScroll({
					destroy: true
				}).height('auto')
			}
			return
		}

		this.check(item)
	}

	TodoList.prototype.check = function(item) {
		this.options.onCheck.call(item)
	}

	TodoList.prototype.unCheck = function(item) {
		this.options.onUnCheck.call(item)
	}

	// Private

	TodoList.prototype._setUpListeners = function() {
		var that = this
		$(this.element).on('change ifChanged', 'input:checkbox', function() {
			that.toggle($(this))
		})
	}

	// Plugin Definition
	// =================
	function Plugin(option) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data(DataKey)

			if (!data) {
				var options = $.extend({}, Default, $this.data(), typeof option == 'object' && option)
				$this.data(DataKey, (data = new TodoList($this, options)))
			}

			if (typeof data == 'string') {
				if (typeof data[option] == 'undefined') {
					throw new Error('No method named ' + option)
				}
				data[option]()
			}
		})
	}

	var old = $.fn.todoList

	$.fn.todoList = Plugin
	$.fn.todoList.Constructor = TodoList

	// No Conflict Mode
	// ================
	$.fn.todoList.noConflict = function() {
		$.fn.todoList = old
		return this
	}

	// TodoList Data API
	// =================
	$(window).on('load', function() {
		$(Selector.data).each(function() {
			Plugin.call($(this))
		})
	})

}(jQuery)

/*
 * DirectChat() =============== Toggles the state of the control sidebar
 * 
 * @Usage: $('#my-chat-box').directChat() or add [data-widget="direct-chat"] to the trigger
 */
+ function($) {
	'use strict'

	var DataKey = 'lte.directchat'

	var Selector = {
		data: '[data-widget="chat-pane-toggle"]',
		box: '.direct-chat'
	}

	var ClassName = {
		open: 'direct-chat-contacts-open'
	}

	// DirectChat Class Definition
	// ===========================
	var DirectChat = function(element) {
		this.element = element
	}

	DirectChat.prototype.toggle = function($trigger) {
		$trigger.parents(Selector.box).first().toggleClass(ClassName.open)
	}

	// Plugin Definition
	// =================
	function Plugin(option) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data(DataKey)

			if (!data) {
				$this.data(DataKey, (data = new DirectChat($this)))
			}

			if (typeof option == 'string') {
				data.toggle($this)
			}
		})
	}

	var old = $.fn.directChat

	$.fn.directChat = Plugin
	$.fn.directChat.Constructor = DirectChat

	// No Conflict Mode
	// ================
	$.fn.directChat.noConflict = function() {
		$.fn.directChat = old
		return this
	}

	// DirectChat Data API
	// ===================
	$(document).on('click', Selector.data, function(event) {
		if (event) {
			event.preventDefault()
		}
		Plugin.call($(this), 'toggle')
	})

}(jQuery)
