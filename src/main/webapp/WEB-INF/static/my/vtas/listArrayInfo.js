//alarm table JS
var dataUrl = '/vtas/arrayInfo/getAllArrayInfo';
var dataColumns = [{  
	    field: 'Number',
	    title: 'No',  
	    formatter: function (value, row, index) {  
	    return index+1;  
	    }  
	},{
	    field: 'arrayName',
	    title: 'Array'
	},{
	    field: 'owner',
	    title: 'Owner'
	},{
	    field: 'model',
	    title: 'Model'
	},{
	    field: 'mgmtIp',
	    title: 'Management IP'
	},{
	    field: 'spaIp',
	    title: 'SPA IP'
	},{
	    field: 'spbIp',
	    title: 'SPB IP'
	},{
	    field: 'usageType',
	    title: 'Usage Type'
	},{
	    field: 'arrayStatus',
	    title: 'Status'
	},{
	    field: 'tfa',
	    title: 'TFA'
	},{
	    field: 'serviceTime',
	    title: 'Service Time'
	},{
	    field: 'comment',
	    title: 'Comment'
	},{
	    title: 'Operation',
	    formatter: function (value, row, index) {
	    	var arrayName = row.arrayName;
			return '<a class="btn btn-xs btn-primary" href="/vtas/arrayInfo/edit/'+ "" + arrayName + "" +'">Edit</a>   <a class="btn btn-xs btn-primary" href="javascript:DeleteRow(' + "'" + arrayName + "'" + ')">Delete</a>';
        }
	}];



//删除单条记录
function DeleteRow(arrayName){
	layer.confirm("Are you sure to delete?", {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'},function(){
    	var url = '/vtas/arrayInfo/delete/'+ "" + arrayName + "" +'';
        $.ajax({  
            type : "post",  
            url : url,
            datatype : "json",  
            success : function(data) {  
                if (data.meta.success){
                	layer.alert('Delete successfuly', {icon: 1,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
					$('#dataTable').bootstrapTable('refresh',{url:dataUrl}); 
                }else{
                	layer.alert(data.meta.message, {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
                }
            }
        });
    });
}


function programMapClientPagination() {
    $('#dataTable').bootstrapTable({
        url: dataUrl,
        method: 'get',
        toolbar: '#toolbar',
        striped: true,
        cache: true,
        pagination: false,
        sortName:"majorVersion",
        sortable: true, 
        sortOrder: "asc",
        sidePagination: "client",
        pageNumber:1,
        pageSize: 20,
        pageList: [50, 100],
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

//init functions
$(function(){
	programMapClientPagination();
})
