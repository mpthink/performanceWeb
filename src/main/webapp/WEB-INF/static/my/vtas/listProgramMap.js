//alarm table JS
var dataUrl = '/vtas/programMap/getAllProgramMaps';
var dataColumns = [{  
	    field: 'Number',
	    title: 'No',  
	    formatter: function (value, row, index) {  
	    return index+1;  
	    }  
	},{
	    field: 'program',
	    title: 'Program Name'
	},{
	    field: 'majorVersion',
	    title: 'Major Version'
	},{
	    title: 'Operation',
	    formatter: function (value, row, index) {
	    	var version = row.majorVersion;
			return '<a class="btn btn-xs btn-primary" href="/vtas/programMap/edit/'+ "" + version + "" +'">Edit</a>   <a class="btn btn-xs btn-primary" href="javascript:DeleteRow(' + "'" + version + "'" + ')">Delete</a>';
        }
	}];



//删除单条记录
function DeleteRow(version){
	layer.confirm("Are you sure to delete?", {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'},function(){
    	var url = '/vtas/programMap/delete/'+ "" + version + "" +'';
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
