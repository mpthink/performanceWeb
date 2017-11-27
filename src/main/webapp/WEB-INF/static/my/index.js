//alarm table JS
var dataUrl = '/vtas/arrayInfo/getArrayInfoWithUptime';
var dataColumns = [
	{
	    field: 'programName',
	    title: 'ProgramName',
	    sortable: true
	},{
	    field: 'arrayName',
	    title: 'Array Name'
	},{
	    field: 'model',
	    title: 'Model'
	},{
	    field: 'tfa',
	    title: 'TFA'
	},{
	    field: 'smallVersion',
	    title: 'Current Bundle',
	    formatter: function (value, row, index) {
            return row.smallVersion+' | '+row.versionTime;
        }
	},{
	    field: 'upTime',
	    title: 'Up time(h)'
	},{
	    field: 'status',
	    title: 'Status',
	    formatter: function (value) {
			    	return "";
        },
	    cellStyle:function(value){
		            if(value=='0'){
		                return { classes: 'bg-green' };
		            }else if(value=='1'){
		                return { classes: 'bg-yellow' };
		            }else{
		            	return { classes: 'bg-red' };
		            }            
		        }
	},{
	    field: 'comments',
	    title: 'Comments'
	}];

function upTimeClientPagination() {
    $('#upTimeTable').bootstrapTable({
        url: dataUrl,
        method: 'get',
        toolbar: '#toolbar',
        striped: true,
        cache: true,
        pagination: false,
        sortName:"programName",
        sortable: true, 
        sortOrder: "asc",
        sidePagination: "client",
        pageNumber:1,
        pageSize: 20,
        pageList: [10, 25, 50, 100],
        strictSearch: false,
        clickToSelect: true,
        cardView: false,
        detailView: false,
        showRefresh:false,
        search:false,
        searchAlign:'right',
        columns: dataColumns
    });
}

//Traditonal table

var dataUrlTraditional = '/vtas/arrayInfo/getArrayWithCurrentHours';
var dataColumnsTraditional = [
	{
	    field: 'programName',
	    title: 'Program Name'
	},{
	    field: 'arrayName',
	    title: 'Array Name'
	},{
	    field: 'model',
	    title: 'Model'
	},{
	    field: 'tfa',
	    title: 'TFA'
	},{
	    field: 'Version',
	    title: 'Current Bundle',
	    formatter: function (value, row, index) {
            return row.Version+' | '+row.versionTime;
        }
	},{
	    field: 'currentRunTime',
	    title: 'Current Run time(h)',
	    formatter: function (value) {
	    	if(value==-1){
	    		return "NA";
	    	}else{
	    		 return value;
	    	}
        }
	},{
	    field: 'status',
	    title: 'Status',
	    formatter: function (value) {
			    	return "";
        },
	    cellStyle:function(value){
		            if(value=='0'){
		                return { classes: 'bg-green' };
		            }else if(value=='1'){
		                return { classes: 'bg-yellow' };
		            }else{
		            	return { classes: 'bg-red' };
		            }            
		        }
	},{
	    field: 'comments',
	    title: 'Comments'
	}];

function upTimeClientPaginationTraditional() {
    $('#CurrentHoursTimeTable').bootstrapTable({
        url: dataUrlTraditional,
        method: 'get',
        toolbar: '#toolbar',
        striped: true,
        cache: true,
        pagination: false,
        sortOrder: "asc",
        sidePagination: "client",
        pageNumber:1,
        pageSize: 20,
        pageList: [10, 25, 50, 100],
        strictSearch: false,
        clickToSelect: true,
        cardView: false,
        detailView: false,
        showRefresh:false,
        search:false,
        searchAlign:'right',
        columns: dataColumnsTraditional
    });
}

//init functions
$(function(){
	upTimeClientPagination();
	upTimeClientPaginationTraditional();
})
