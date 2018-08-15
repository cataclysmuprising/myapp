/*
 * =========================================================================
 * app.js ! version : 1.0 Copyright (c) 2017 - license.txt, Than Htike Aung
 * =========================================================================
 */
/**
 * @file app.js
 * @description This js file initiate all global settings for website. This file
 *              may include in most of pages.
 * @version 1.0
 * @author Than Htike Aung
 * @contact rage.cataclysm@gmail.com
 * @copyright Copyright (c) 2017-2018, Than Htike Aung. This source file is free
 *            software, available under the following license: MIT license. See
 *            the license file for details.
 */

/**
 * ################################## # Global Variables
 * ##################################
 */
var ROW_PER_PAGE = 25;
var SECONDARY_ROW_PER_PAGE = 10;
var FILE_SIZE_UNITS = new Array('Bytes', 'KB', 'MB', 'GB');
var $pushMenu = $('[data-toggle="push-menu"]').data('lte.pushmenu');
var $controlSidebar = $('[data-toggle="control-sidebar"]').data(
    'lte.controlsidebar');
var $layout = $('body').data('lte.layout');
var ws;
var appSkins = ['skin-blue', 'skin-black', 'skin-red', 'skin-yellow',
    'skin-purple', 'skin-green', 'skin-blue-light', 'skin-black-light',
    'skin-red-light', 'skin-yellow-light', 'skin-purple-light',
    'skin-green-light'];

var PAGE_MODE = "";
var COMPANY_CODE = "";
var CONTY_NO_JSON = "application/x-www-form-urlencoded; charset=UTF-8";
/**
 * ################################## # JS working functions on page
 * ##################################
 */
$(window).on('beforeunload', function () {
    sessionStorage.clear();
    $("form input[readonly]").each(function (e) {
        var key = $(this).attr("name");
        if (!key) {
            key = $(this).attr("id");
        }
        if (key) {
            sessionStorage.setItem(key, $(this).val());
        }
    });
});
$.ajaxSetup({
    dataType: "json",
    contentType: "application/json; charset=utf-8",
    timeout: 100000,
});
$(document)
    .ajaxSend(function (e, xhr, options) {
        var $csrf_token = $("meta[name='_csrf']").attr("content");
        var $csrf_header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader($csrf_header, $csrf_token);
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
        xhr.setRequestHeader('Accept', 'application/json');
    })
    .ajaxComplete(
        function (event, xhr, settings) {
            if (xhr.status == 403) {
                alert("This page contains unauthorized contents for you.Please contact administrators !");
                window.location.href = getContextPath() + "/accessDenied";
            } else if (xhr.status == 208) {
                document.write(xhr.responseText);
            } else if (xhr.status == 226) {
                document.write(xhr.responseText);
            }
        })
    .ajaxStop(function () {
        if ($.fn.selectpicker) {
            // $(".selectpicker").selectpicker('refresh');
        }
    })
    .ajaxError(
        function (event, jqxhr, settings, thrownError) {
            alert("Error occured while loading page informations! Check your internet connection.");
            console.log(thrownError);
        });

/**
 * ################################## # Application's Main Functions
 * ##################################
 */
$(function () {
    PAGE_MODE = $('#pageMode').val();

    COMPANY_CODE = $("#companyCode").val();

    baseInit();
    baseBind();
    if (typeof init === "function") {
        init();
    }
    if (typeof bind === "function") {
        bind();
    }
});

function baseInit() {
    $('.disabled').attr('tabindex', '-1');
    $('.disabled input').attr('tabindex', '-1');
    $('form,input').attr('autocomplete', 'off');
    initSessionExpiredHandler();
    initAdminLTETheme();
    initSelectPickers();
    initDatePicker();
    initICheck();
    initJQueryValidator();
    initLobiboxSettings();
    initJQueryDataTable();
    loadValidationErrors();
    initPageMessage();

    $('img').error(function () {
        $(this).attr("src", getStaticResourcePath() + "/images/no-image.png");
    });
    $('#preloader_status').fadeOut("slow");
    $('#preloader').fadeOut("slow");
    $('body').css({
        'overflow': 'visible'
    });

    // webSocketConnect();
}

function baseBind() {
    $('form').on('reset', function (e) {
        setTimeout(function () {
            $(".selectpicker").trigger('loaded.bs.select');
        });
    });

    bindForCustomBootstrapTabEvent();
    setBootstrapDropDownAbsolute();
    disableFormSubmitEvent();
    bindScrollToTopButtonEvent();
}

/**
 * ############################################## # To notify users for their
 * expired sessions ##############################################
 */

function initSessionExpiredHandler() {
    if ($("body").data("sss-tmo")) {
        var sessionTimeOutInSeconds = parseInt($("body").data("sss-tmo")) - 10;
        window.setInterval(function () {
            $.ajax({
                type: "GET",
                dataType: "json",
                url: getContextPath() + "/refreshSession"
            });
        }, sessionTimeOutInSeconds * 1000);
    }
}

/**
 * ################################## # Base Init Functions
 * ##################################
 */
function initSelectPickers() {
    // set selected value for select picker
    $(".selectpicker").on('loaded.bs.select', function (e) {
        setTimeout(function () {
            $(".selectpicker").selectpicker('refresh');
            var _selected = $(this).attr("data-selected");
            if (_selected && _selected.trim().length > 0) {
                // for multiple select picker
                if ($(this).attr("multiple")) {
                    _selected = _selected.replaceSome("]", "[", " ", "");
                    $(this).selectpicker('val', _selected.split(","));
                }

                else {
                    $(this).selectpicker('val', _selected).change();
                }
            }
        }, 500);

    });
}

function initAdminLTETheme() {
    var oldSkin = $('#t3k-portal-theme-skin').val();
    if (oldSkin && oldSkin.length > 0) {
        $(".available-skins li").removeClass("active");
        $('[data-skin="' + oldSkin + '"]').parent("li").addClass("active");
    }

    var oldLayout = $('#t3k-portal-theme-layout').val();
    if (oldLayout) {
        $('[data-layout="' + oldLayout + '"]').iCheck('check');
    }

    var oldSideBar = $('#t3k-portal-theme-sidebar').val();
    if (oldSideBar) {
        $('[data-sidebar="' + oldSideBar + '"]').iCheck('check');
    }

    var oldSideBarSkin = $('#t3k-portal-theme-sidebar-skin').val();
    if (oldSideBarSkin && oldSideBarSkin == "light") {
        $('[data-sidebarskin="toggle"]').iCheck('check');
    }

    $('[data-toggle="control-sidebar"]').controlSidebar();
    $('[data-toggle="push-menu"]').pushMenu();

    $('[data-layout]').on('ifChanged', function () {
        changeLayout($(this).data('layout'));
        saveUserPreference();
    });

    $('[data-sidebar]').on('ifChanged', function () {
        changeLayout($(this).data('sidebar'));
        saveUserPreference();
    });

    $('[data-sidebarskin="toggle"]').on('ifChanged', function () {
        var $sidebar = $('.control-sidebar');
        if ($sidebar.hasClass('control-sidebar-dark')) {
            $sidebar.removeClass('control-sidebar-dark');
            $sidebar.addClass('control-sidebar-light');
        } else {
            $sidebar.removeClass('control-sidebar-light');
            $sidebar.addClass('control-sidebar-dark');
        }
        saveUserPreference();
    });

    // Add the change skin listener
    $('[data-skin]').on('click', function (e) {
        if ($(this).hasClass('knob')) {
            return false;
        }
        e.preventDefault();
        $(".available-skins li").removeClass("active");
        $(this).parent("li").addClass("active");
        changeSkin($(this).data('skin'));
    });

    // Reset options
    if ($('body').hasClass('fixed')) {
        $('[data-layout="fixed"]').attr('checked', 'checked');
    }
    if ($('body').hasClass('layout-boxed')) {
        $('[data-layout="layout-boxed"]').attr('checked', 'checked');
    }
    if ($('body').hasClass('sidebar-collapse')) {
        $('[data-layout="sidebar-collapse"]').attr('checked', 'checked');
    }

    $('[data-toggle="tooltip"]').tooltip();

}

function initPageMessage() {
    var pageMessage = $("#pageMessage");
    if (pageMessage) {
        var title = $(pageMessage).attr("data-title");
        var message = $(pageMessage).attr("data-info");
        var style = $(pageMessage).attr("data-style");
        notify(title, message, style);
    }
}

function clearOldValidationErrorMessages() {
    $(".help-block").remove();
    $(".form-group").removeClass("has-error");
    $("div.input-fieldless").hide();
}

function loadValidationErrors() {
    if ($("#validationErrors .error-item").length > 0) {
        clearOldValidationErrorMessages();
        $.each($("#validationErrors .error-item"), function (index, item) {
            var elementId = $(item).attr("data-id").replace(new RegExp("\\.", "g"),
                '_');
            if ($("#" + elementId).length) {
                $("#" + elementId).closest(".form-group").addClass("has-error");
                var container;
                if ($("#" + elementId).closest("div").hasClass("input-fieldless")) {
                    container = $("#" + elementId).closest("div.input-fieldless")
                        .children("blockquote");
                    $("#" + elementId).closest("div.input-fieldless").show();
                } else if ($("#" + elementId).closest("form").hasClass(
                    "form-horizontal")) {
                    container = $("#" + elementId).closest(".form-group > div");
                } else {
                    container = $("#" + elementId).closest(".form-group");
                }
                container.append('<span class="help-block">'
                    + $(item).attr("data-error-message") + '</span>');
            }
        });
        // remake readonly or disable fields to readonly
        Object.keys(sessionStorage).forEach(function (key) {
            var item = $("[name='" + key + "']");
            if (!item || item.length == 0) {
                item = $("#" + key);
            }
            if (item && item.length > 0) {
                $(item).val(sessionStorage.getItem(key)).attr("readonly", "readonly");
            }
        });
        // show active validation error tab
        $.each($('.nav-tabs-custom .tab-pane'), function (index, item) {
            if ($(this).find(".has-error").length > 0) {
                if (!$(this).hasClass("active")) {
                    $('.nav-tabs > li > a[href="#' + $(this).attr("id") + '"]').trigger(
                        "click");
                }
                return false;
            }
        });
    }
}

function initJQueryDataTable() {
    if ($.fn.DataTable) {
        $.extend(true, $.fn.dataTable.defaults, {
            "dom": '<"top">rt<"bottom"ifp><"clear">',
            "bFilter": false,
            pagingType: "first_last_numbers",
            "pageLength": ROW_PER_PAGE,
            processing: false,
            serverSide: true,
            aaSorting: [[0, "desc"]],
            "language": {
                "zeroRecords": "No matching records found."
            },
            autoWidth: false,
            infoCallback: function (roles, start, end, max, total, pre) {
                if (total > 0) {
                    return "Showing " + start + " to " + end + " of " + total
                        + " entries";
                } else {
                    return "No Entry to Show";
                }
            },
            footerCallback: function (roles, start, end, max, total, pre) {
                $('table.dataTable').off('processing.dt').on('processing.dt',
                    function (e, settings, processing) {
                        if (processing) {
                            Pace.start();
                        } else {
                            Pace.stop();
                        }
                    });
            }
        });

        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            if ($(".table").length) {
                $.fn.dataTable.tables({
                    visible: true,
                    api: true
                }).columns.adjust();
            }
        });

        $(window).resize(function () {
            if (this.resizeTO) {
                clearTimeout(this.resizeTO);
            }
            this.resizeTO = setTimeout(function () {
                $(this).trigger('resizeEnd');
            }, 500);
        });

        $(window).bind('resizeEnd', function () {
            $(".datatable_scrollArea").scroll();
        });
    }
}

function initDatePicker() {
    if ($.fn.datetimepicker) {
        $(".input-group.date").each(
            function (index, item) {
                var format = $(this).attr("data-format");
                $(this).datetimepicker(
                    {
                        format: format == undefined || format.length == 0
                            ? 'DD-MM-YYYY' : format,
                        showClear: true
                    });
            });
    }
}

function initICheck() {
    if ($.fn.iCheck) {
        $('[data-input-type="iCheck"]').iCheck({
            checkboxClass: 'icheckbox_minimal-red',
            radioClass: 'iradio_minimal-red',
        });
    }
}

function initLobiboxSettings() {
    if (typeof Lobibox !== 'undefined' && typeof Lobibox.notify === "function") {
        Lobibox.notify.DEFAULTS = $.extend({}, Lobibox.notify.DEFAULTS, {
            size: 'mini',
            iconSource: "fontAwesome",
            showClass: 'zoomIn',
            hideClass: 'lightSpeedOut',
            continueDelayOnInactiveTab: true,
            pauseDelayOnHover: true,
            sound: false,
            delay: 10000,
            img: getStaticResourcePath() + "/images/portal-logo.png",
            warning: {
                title: 'Warning',
                iconClass: 'fa fa-exclamation-circle'
            },
            info: {
                title: 'Information',
                iconClass: 'fa fa-info-circle'
            },
            success: {
                title: 'Success',
                iconClass: 'fa fa-check-circle'
            },
            error: {
                title: 'Error',
                iconClass: 'fa fa-times-circle'
            }
        });
    }
}

function initJQueryValidator() {
    if ($.fn.validate) {
        $.validator.setDefaults({
            success: "",
            errorElement: "span",
            errorClass: "help-block with-errors",
            ignore: [],
            errorPlacement: function (error, element) {
                if (element.parent('.input-group').length) {
                    error.insertAfter(element.parent());
                } else if (element.hasClass('selectpicker')) {
                    if ((element).closest('.form-group .input-group').length) {
                        error.insertAfter(element.parent().closest(
                            '.form-group .input-group'));
                    } else {
                        error.insertAfter(element.parent().closest('.bootstrap-select'));
                    }
                } else if (element.prop('type') === 'radio') {
                    if (element.parent().closest('.radio-inline-group')) {
                        error.appendTo(element.parent().closest('.radio-inline-group'))
                            .wrap('<div/>');
                    }
                } else {
                    error.insertAfter(element);
                }
            },
            highlight: function (element) {
                var container = $(element).closest('.form-group');
                var inputElemCounts = $(container).find("input:visible").length;
                if (inputElemCounts > 1
                    && $(container).find(".radio-inline-group").length == 0) {
                    $(element).closest(".control-block").addClass('has-error');
                } else {
                    $(container).addClass("has-error");
                }
            },
            unhighlight: function (element) {
                var container = $(element).closest('.form-group');
                var inputElemCounts = $(container).find("input:visible").length;
                if (inputElemCounts > 1
                    && $(container).find(".radio-inline-group").length == 0) {
                    $(element).closest(".control-block").removeClass('has-error');
                } else {
                    $(container).removeClass("has-error");
                }
            },
            success: function (error) {
                $(error).remove();
            },
            onkeyup: function () {
                return false;
            },
            onfocusout: function () {
                return false;
            }
        });
    }
}

/**
 * ################################## # Base Bind Functions
 * ##################################
 */

// stackoverflow.com/questions/32526201/bootstrap-dropdown-menu-hidden-behind-other-elements#32527231
function setBootstrapDropDownAbsolute() {
    // hold onto the drop down menu
    var dropdownMenu;

    // and when you show it, move it to the body
    $(window).on('show.bs.dropdown', function (e) {

        // grab the menu
        dropdownMenu = $(e.target).find('.datatable-cell-item.dropdown-menu');

        // detach it and append it to the body
        $('body').append(dropdownMenu.detach());

        // grab the new offset position
        var eOffset = $(e.target).offset();

        // make sure to place it where it would normally go (this could
        // be improved)
        dropdownMenu.css({
            'display': 'block',
            'top': eOffset.top + $(e.target).outerHeight(),
            'left': eOffset.left
        });
    });

    // and when you hide it, reattach the drop down, and hide it normally
    $(window).on('hide.bs.dropdown', function (e) {
        if (dropdownMenu) {
            $(e.target).append(dropdownMenu.detach());
            dropdownMenu.hide();
        }
    });
}

// to make disable tabs and I don't want to see bootstrap's #url for tabs
// https://stackoverflow.com/questions/9237314/can-you-disable-tabs-in-bootstrap
// https://stackoverflow.com/questions/824349/modify-the-url-without-reloading-the-page
function bindForCustomBootstrapTabEvent() {
    $(".nav-tabs a[data-toggle=tab]").on("click", function (e) {
        var hash = window.location.hash;
        hash && $('ul.nav a[href="' + hash + '"]').tab('show');
        if ($(this).closest("li").hasClass("disabled")) {
            e.preventDefault();
            return false;
        } else {
            $(this).tab('show');
            history.replaceState({}, null, location.href);
        }
    });
}

function disableFormSubmitEvent() {
    $('form').on('keyup keypress', function (e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });
}

function bindRemoveButtonEvent(selector) {

    if (!isNotEmpty(selector)) {
        selector = ".remove";
    }

    $(selector).on("click", function (e) {
        e.preventDefault();
        var url = $(this).attr("href");
        $("#deleteConfirmModal").modal({
            backdrop: 'static',
            keyboard: false
        });
        $("#confirmDelete").off('click').on('click', function (e) {
            $("#deleteConfirmModal").modal("hide");
            window.location.href = url;
        });
    });
}

function bindScrollToTopButtonEvent() {
    $(window).scroll(function () {
        if (($(window).width() >= 1280)) {
            if ($(this).scrollTop() > 150) {
                $('.layout-boxed .scrollToTop').fadeIn();
            } else {
                $('.layout-boxed .scrollToTop').fadeOut();
            }

        }
    });
    $('.scrollToTop').click(function () {
        $('html, body').animate({
            scrollTop: 0
        }, 800);
        return false;
    });
}

/**
 * ################################## # Global Functions
 * ##################################
 */
function changeLayout(cls) {
    if (cls == "normal") {
        $('body').removeClass("fixed,layout-boxed");
    } else if (cls == "fixed") {
        $('body').removeClass("normal,layout-boxed");
    }
    $('body').toggleClass(cls);
    $(window).trigger("resize");
    // $layout.fixSidebar();
    // if ($('body').hasClass('fixed') && cls == 'fixed') {
    // $pushMenu.expandOnHover();
    // $layout.activate();
    // }
    // $controlSidebar.fix();
}

function changeSkin(cls) {
    $.each(appSkins, function (i) {
        $('body').removeClass(appSkins[i]);
    })
    $('body').addClass(cls);
    saveUserPreference();
    return false;
}

function handleServerResponse(response) {
    if (response.status == "METHOD_NOT_ALLOWED") {
        if (response.type == "validationError") {
            $("#validationErrors").empty();
            $.each(response.fieldErrors, function (key, value) {
                $("#validationErrors").append(
                    '<span class="error-item" data-id="' + key
                    + '" data-error-message="' + value + '" />');
            });
            loadValidationErrors();
        }
    } else if (response.status == "OK") {
        $(".modal").modal("hide");
    }
    if (response.pageMessage) {
        var pageMessage = response.pageMessage;
        notify(pageMessage.title, pageMessage.message, pageMessage.style);
    }
}

String.prototype.replaceSome = function () {
    var replaceWith = Array.prototype.pop.apply(arguments), i = 0, r = this, l = arguments.length;
    for (; i < l; i++) {
        r = r.replace(arguments[i], replaceWith);
    }
    return r;
}

function notify(title, message, style) {
    if (typeof Lobibox !== 'undefined' && typeof Lobibox.notify === "function") {
        $(".lobibox-notify-wrapper").remove();
        Lobibox.notify(style, {
            msg: message,
            title: title
        });
    }
}

function getLocalStorageItem(name) {
    if (typeof (Storage) !== 'undefined') {
        return localStorage.getItem(name);
    } else {
        window.alert('Please use a modern browser to properly view this template!');
    }
}

function saveInLocalStorage(name, val) {
    if (typeof (Storage) !== 'undefined') {
        localStorage.setItem(name, val);
    } else {
        window.alert('Please use a modern browser to properly view this template!');
    }
}

function removeFromLocalStorage(name) {
    if (typeof (Storage) !== 'undefined') {
        localStorage.removeItem(name);
    } else {
        window.alert('Please use a modern browser to properly view this template!');
    }
}

function goToHomePage() {
    $(".breadcrumb > li > a")[1].click();
}

function reloadCurrentPage() {
    location.reload(true);
}

function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname
        .indexOf("/", 2));
}

function getStaticResourcePath() {
    return $("#baseStaticRssDir").val();
}

function getPageMode() {
    return $("#pageMode").val();
}

function hasAuthority(actionName) {
    return $("#" + actionName).val() == "true";
}

function convertJSONValueToCommaSeparateString(acceptanceElemSelector) {
    try {
        var json = JSON.parse($(acceptanceElemSelector).val());
        $(acceptanceElemSelector).val(json);
    } catch (exception) {
    }
}

function removeElementByIndex(arr, x) {
    var newArr = [];
    for (var i = 0; i < arr.length; i++) {
        if (i != x) {
            newArr.push(arr[i]);
        }
    }
    return newArr;
}

function formatNumber(x) {
    if (x) {
        var parts = x.toString().split(".");
        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        return parts.join(".");
    }
    return "-";
}

/**
 * ####################################### # JavaScript utility methods for
 * Array #######################################
 */
function convertUniqueArray(a) {
    var temp = {};
    for (var i = 0; i < a.length; i++) {
        temp[a[i]] = true;
    }
    var r = [];
    for (var k in temp)
        r.push(k);
    return r;
}

function findWithAttr(array, attr, value) {
    for (var i = 0; i < array.length; i += 1) {
        if (array[i][attr] === value) {
            return i;
        }
    }
    return -1;
}

function saveUserPreference() {

    var preference = {
        "themeLayout": $('[data-layout]:checked').attr("data-layout"),
        "themeSidebar": $('[data-sidebar]').prop('checked') ? "sidebar-collapse"
            : "",
        "themeSidebarSkin": $(".control-sidebar").hasClass("control-sidebar-dark")
            ? 'dark' : 'light',
        "themeSkin": $(".available-skins li.active div").attr("data-skin")
    }
    $.ajax({
        type: "PUT",
        url: getPortalResourcePath() + "site_preference/save",
        data: preference,
        data: JSON.stringify(preference),
        success: function (result) {
        }
    });
}