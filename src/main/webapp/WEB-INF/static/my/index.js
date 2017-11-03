//alarm table JS
var dataUrl = '/vtas/arrayInfo/getArrayInfoWithUptime';
var dataColumns = [
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
	    field: 'smallVersion',
	    title: 'Version'
	},{
	    field: 'upTime',
	    title: 'Up time(h)'
	},{
	    field: 'status',
	    title: 'Status',
	    cellStyle:function(value){
		            if(value=='0'){
		                return { classes: 'success' };
		            }else if(value=='1'){
		                return { classes: 'warning' };
		            }else{
		            	return { classes: 'danger' };
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
        pagination: true,
        sortOrder: "asc",
        sidePagination: "client",
        pageNumber:1,
        pageSize: 20,
        pageList: [10, 25, 50, 100],
        strictSearch: false,
        clickToSelect: true,
        cardView: false,
        detailView: false,
        showRefresh:true,
        search:true,
        searchAlign:'right',
        columns: dataColumns
    });
}


//init functions
$(function(){
	upTimeClientPagination();
})
