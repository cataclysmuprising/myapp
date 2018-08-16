/*
 * =======================================================================================
 * dataTables-util.js ! version : 1.0 Copyright (c) 2017 - license.txt, Than
 * Htike Aung
 * =======================================================================================
 */

/**
 * @file dataTables-util.js
 * @description Make JQuery DataTable selectable with the help of iCheck plugin
 *              (http://icheck.fronteed.com/).
 * @version 1.0
 * @author Than Htike Aung
 * @contact rage.cataclysm@gmail.com
 * @copyright Copyright (c) 2017-2018, Than Htike Aung. This source file is free
 *            software, available under the following license: MIT license. See
 *            the license file for details.
 */

function setScrollX(tableId) {
  var scrollArea = $('<div></div>');
  $(scrollArea).addClass("datatable_scrollArea");
  $(tableId).wrap(scrollArea);
}

function fixedFunctionColumn(tableId) {
  if ($(".functionColumn").length == 0) { return false; }
  // remove existing fixedColumnsTable
  $(tableId).siblings(".fixedColumnsTable").remove();
  // make sure it is not an empty table
  if ($(tableId + " tbody tr").length == 1) {
    if ($(tableId + " tbody tr td:last-child").hasClass("dataTables_empty")) { return false; }
  }
  if (!$(tableId).parent().hasClass("datatable_scrollArea")) {
    setScrollX(tableId);
  }
  // hide original table's header
  $(".functionColumn").css("opacity", "0");
  var tableWidth = $(tableId + " thead tr th:last-child").outerWidth() + 1;
  var scrollArea = $(tableId).closest(".datatable_scrollArea");
  // remove existing events
  $(scrollArea).unbind();
  // create table
  var table = $('<table class="fixedColumnsTable"></table>');
  $(table).addClass($(tableId).attr("class"));
  // create table header
  var thead = $("<thead></thead>");
  var tr_head = $('<tr><th class="text-center sorting_disabled">Function</th></tr>');
  thead.append(tr_head);
  table.append(thead);
  // create table body
  var tbody = $("<tbody></tbody>");
  $.each($(tableId + " tbody tr td:last-child"), function(index, lastChild) {
    var tr = $('<tr></tr>');
    var td = $(lastChild).clone();
    // hide original row
    $(lastChild).css("opacity", "0");
    // centering the content
    $(td).addClass("text-center");
    tr.append(td);
    tbody.append(tr);
  });
  table.append(tbody);
  $(table).attr("style", "width:" + tableWidth + "px !important;");
  $(table).css({
    "right": 1,
    "position": "absolute",
    "top": 0,
    "background-color": "#FDFDFD",
  });
  // add the table
  scrollArea.append(table);
  // bind the scroll event on scroll panel
  $(scrollArea).on("scroll", function(e) {
    var _this = this;
    // don't move. Stay at your position
    var fixedColumnsTable = $(this).children('.fixedColumnsTable');
    var leftOffset = $(this).width() - tableWidth + _this.scrollLeft;
    $(fixedColumnsTable).css({
      'left': leftOffset,
      "right": 1
    });
  });
}

function generateAuthorizedButtonGroup(buttons) {
  var html = [];
  var availableButtons = [];
  for (i = 0; i < buttons.length; i++) {
    var button = buttons[i];
    if (button != undefined && hasAuthority(button.authorityName)) {
      if (button.showElementOnExpression != undefined && button.showElementOnExpression != null) {
        if (button.showElementOnExpression == true) {
          availableButtons.push(button);
        }
      } else {
        availableButtons.push(button);
      }
    }
  }
  if (availableButtons.length == 0) { return '<i title="Actions do not allowed to perform." class="fa fa-times-circle-o text-red" style="font-size:18px;"></i>'; }
  if (availableButtons.length == 1) {
    var button = availableButtons[0];
    html.push('<a href="' + availableButtons[0].url + '" data-id="' + button.data_id + '" class="btn btn-sm btn-default btn-flat ' + button.styleClass + '">' + availableButtons[0].label + '</a>');
  } else if (availableButtons.length > 1) {
    var button = availableButtons[0];
    html.push('<div class="btn-group">');
    html.push('<a href="' + button.url + '" data-id="' + button.data_id + '" class="btn btn-sm btn-default btn-flat ' + button.styleClass + '">' + button.label + '</a>');
    html.push('<button type="button" class="btn btn-sm btn-default btn-flat dropdown-toggle" data-toggle="dropdown">');
    html.push('<span class="caret"></span>');
    html.push('</button>');
    html.push('<ul class="datatable-cell-item dropdown-menu" role="menu">');
    for (i = 1; i < availableButtons.length; i++) {
      var button = availableButtons[i];
      html.push('<li><a href="' + button.url + '" data-id="' + button.data_id + '" class="' + button.styleClass + '">' + button.label + '</a></li>');
    }
    html.push("</ul>");
    html.push('<div>');
  }
  return html.join('');
}
