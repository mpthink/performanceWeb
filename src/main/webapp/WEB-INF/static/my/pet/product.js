//alarm table JS
var dataUrl = '/pet/product/getall';
var dataColumns = [{
		checkbox: true
	},{  
	    field: 'Number',  
	    title: '序号',  
	    formatter: function (value, row, index) {  
	    return index+1;  
	    }  
	},{
	    field: 'productName',
	    title: '项目名称',
	    formatter: function (value, row, index) {
            return '<a class="btn btn-primary btn-xs" href="/pet/product/showpet' + "" + row.id + "" + '" data-toggle="tooltip" data-placement="bottom">'+"" + row.productName + ""+'</a>';
        }
	},{
	    field: 'productRelease',
	    title: '项目版本'
	},{
	    field: 'productClassName',
	    title: '项目分类'
	}, {
	    field: 'gmtCreate',
	    title: '创建时间',
	    formatter: function (value, row, index) {
            return timeStamp2String(row.gmtCreate);
        }
	},{
		title: '操作',
		formatter : operateFormatter
	}];

/** 
 * 列表操作栏格式化 
 */  
function operateFormatter(value, row, index) {  
    var editDef = '&nbsp;&nbsp;<a class="btn btn-primary btn-xs" href="/pet/product/edit/' + "" + row.id + "" + '.html" data-toggle="tooltip" title="编辑" data-placement="bottom">编辑</a>&nbsp;&nbsp;'+
    		'<a class="btn btn-danger btn-xs" href="javascript:DeleteRow(' + "'" + row.id + "'" + ')">删除</a>';  
    return editDef;  
}


//删除单条记录
function DeleteRow(id){
	layer.confirm("确认要删除该条数据吗?", {icon: 0,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'},function(){
    	var url = "/pet/product/delete";
    	var param = { "id" : id };  
        $.ajax({  
            type : "post",  
            url : url,  
            data : param,  
            datatype : "json",  
            success : function(data) {  
                if (data.meta.success){
                	layer.alert('删除成功', {icon: 1,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
					$('#dataTable').bootstrapTable('refresh',{url:dataUrl}); 
                }else{
                	layer.alert(data.meta.message, {icon: 0,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
                }
            }
        });
    });
}

//批量删除
function BtchDeleteData(){
	var rows = $('#dataTable').bootstrapTable('getSelections');    //返回所有选择的行，当没有选择的记录时，返回一个空数组  
    if (rows.length == 0) {
    	layer.alert('请选择要删除的数据', {icon: 2,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
        return;  
    }  
    /*表单提示确认框*/
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {icon: 0,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'},function(){
    	var url = "/pet/product/deleteBatch";  
        var ids = new Array();  
        //遍历所有选择的行数据，取每条数据对应的ID  
        $.each(rows, function(i, row) {  
            ids[i] = row['id'];  
        });  
        //定义ajax请求参数  
        var param = {  
            "ids" : ids  
        };  
        $.ajax({  
            type : "post",  
            url : url,  
            data : param,  
            datatype : "json",  
            success : function(data) {  
                if (data.meta.success){
                	layer.alert('删除成功', {icon: 1,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
					$('#dataTable').bootstrapTable('refresh',{url:dataUrl}); 
                }else{
                	layer.alert(data.meta.message, {icon: 0,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
                }
            }
        });
    });
}

function timeStamp2String(time){
    var datetime = new Date();
     datetime.setTime(time);
     var year = datetime.getFullYear();
     var month = datetime.getMonth() + 1;
     var date = datetime.getDate();
     var hour = datetime.getHours();
     var minute = datetime.getMinutes();
     var second = datetime.getSeconds();
     return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
};

//init functions
$(function(){
	clientPagination();
})
