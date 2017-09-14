//alarm table JS
var dataUrl = '/system/lab/getall';
var dataColumns = [{  
	    field: 'Number',  
	    title: 'No',  
	    formatter: function (value, row, index) {  
	    return index+1;  
	    }  
	},{
	    field: 'labName',
	    title: 'Lab Name',
	    formatter: function (value, row, index) {
	    	var labName = row.labName;
	    	var wikiLabName = "CloudLab"+labName.substring(4);
            return '<a href="' + "" + row.labUrl + "" + '" target="_blank">' + "" + labName + "" + '</a> | '+ 
            '<a href="https://confluence.int.net.nokia.com/display/OSSOPLSVL/' + "" + wikiLabName + "" + '" target="_blank">' + "" + wikiLabName + "" + '</a>' ;
        }
	},{
	    field: 'dbIpv4',
	    title: 'DB IPv4'
	}, {
	    field: 'viisIpv4',
	    title: 'Viis IPv4'
	},{
		title: '操作',
		 formatter: function (value, row, index){
			 var id = row.id;
			 return '<a class="btn btn-xs btn-warning" onclick="addFavorite(' + "'" + id + "'" + ')"><i class="fa fa-pencil"></i>Add to Favarite</a> ';
		 }
	}];

function addFavorite(id){
	var url = "/system/lab/addFavorite/"+id;
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		success:function(data){
			if(data.meta.success){
				layer.alert('添加成功', {icon: 1,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
			}else{
				layer.alert(data.meta.message, {icon: 0,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
			}
		}
	});
	
	
}


//init functions
$(function(){
	clientPagination();
})
