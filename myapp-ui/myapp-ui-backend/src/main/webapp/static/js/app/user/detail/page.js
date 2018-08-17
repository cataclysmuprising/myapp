var dataTable;
function init() {
	initDataTable();
}
function bind() {
}

function initDataTable() {

	var columns = [ {
	    "mData" : "ipAddress",
	    "bSortable" : false,
	    "sClass" : "text-center"
	}, {
	    "mData" : "os",
	    "bSortable" : false,
	    "sClass" : "text-center"
	}, {
	    "mData" : "userAgent",
	    "bSortable" : false,
	    "sClass" : "text-center"
	}, {
	    "mData" : "loginDate",
	    "bSortable" : false,
	    "sClass" : "text-center"
	} ];

	dataTable = $('#loginHistoryTable').DataTable({
	    aoColumns : columns,
	    ordering : false,
	    "pageLength" : SECONDARY_ROW_PER_PAGE,
	    ajax : {
	        url : getApiResourcePath() + 'login_history/datatable/search',
	        type : "POST",
	        data : function(d) {
		        var criteria = {};
		        criteria.offset = d.start;
		        criteria.limit = d.length;
		        criteria.userId = $("#userId").val();
		        return JSON.stringify(criteria);
	        }
	    },
	    drawCallback : function(settings) {
	    }
	});
}
