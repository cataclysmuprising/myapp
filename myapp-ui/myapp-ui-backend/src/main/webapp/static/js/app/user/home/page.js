var dataTable;
function init() {
  initDataTable();
}

function bind() {

  $("#btnSearch").click(function(e) {
    dataTable.search($(this).val()).draw();
  });

  $("#btnReset").click(function(e) {
    $('form.control-block').trigger('reset');
    dataTable.search($(this).val()).draw();
  });

}

function initDataTable() {
  var columns = [{
    "mData": "name",
  }, {
    "mData": "email",
  }, {
    "mData": "nrc",
    "bSortable": false,
  }, {
    "mData": "phone",
    "bSortable": false,
  }, {
    "render": function(data, type, full, meta) {
      if (full.status) {
        if (full.status == "ACTIVE") {
          return '<i class="fa fa-check-square-o text-green"></i>';
        } else if (full.status == "TEMPORARY") {
          return '<i class="fa fa-warning text-yellow"></i>';
        } else if (full.status == "LOCK") {
          return '<i class="fa fa-times-circle-o text-red"></i>';
        } else {
          return '-';
        }

      } else {
        return '-';
      }

    },
    "sClass": "text-center",
    "bSortable": false,
  }, ];
  if (hasAuthority("userDetail") || hasAuthority("userEdit") || hasAuthority("userRemove")) {
    columns.push({
      "render": function(data, type, full, meta) {
        var detailButton = {
          label: "View",
          authorityName: "userDetail",
          url: getContextPath() + "/user/" + full.id,
          styleClass: "",
          data_id: full.id
        }
        var editButton = {
          label: "Edit",
          authorityName: "userEdit",
          url: getContextPath() + "/user/" + full.id + '/edit',
          styleClass: "",
          data_id: full.id
        }
        var removeButton = {
          label: "Remove",
          authorityName: "userRemove",
          url: getContextPath() + "/user/" + full.id + '/delete',
          styleClass: "remove",
          data_id: full.id
        }
        return generateAuthorizedButtonGroup([detailButton, editButton, removeButton]);
      },
      "bSortable": false,
      "sClass": "text-center"
    });
  }
  dataTable = $('#userTable').DataTable({
    aoColumns: columns,
    "aaSorting": [],
    ajax: {
      type: "POST",
      url: getApiResourcePath() + 'user/datatable/search',
      data: function(d) {
        var criteria = {};
        if (d.order.length > 0) {
          var index = $(d.order[0])[0].column;
          var dir = $(d.order[0])[0].dir;
          var head = $("#userTable").find("thead");
          var sortColumn = head.find("th:eq(" + index + ")");
          criteria.order = dir.toUpperCase();
          criteria.orderBy = $(sortColumn).attr("data-id");
        }
        criteria.offset = d.start;
        criteria.limit = d.length;
        criteria.word = $("#keyword").val();
        return JSON.stringify(criteria);
      }
    },
    initComplete: function() {
      var api = this.api();
      $('#keyword').off('.DT').on('keyup.DT', function(e) {
        if (e.keyCode == 13) {
          api.search(this.value).draw();
        }
      });
    },
    drawCallback: function(settings) {
      fixedFunctionColumn("#userTable");
      bindRemoveButtonEvent();
    }
  });
}
