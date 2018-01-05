//alarm table JS
var dataUrl = '/vtas/jobRuntime/getLatestJobs';
var dataColumns = [{  
	    field: 'Number',
	    title: 'No',  
	    formatter: function (value, row, index) {  
	    return index+1;  
	    }  
	},{
	    field: 'PROGRAM',
	    title: 'Program Name'
	},{
	    field: 'ARRAY_NAME',
	    title: 'Array Name'
	},{
	    field: 'JOB_URL',
	    title: 'Jenkins Job',
	    formatter: function (value, row, index) {
            return formatUrl(row.JOB_URL);
        }
	},{
	    field: 'VERSION',
	    title: 'Version'
	},{
	    field: 'STATUS',
	    title: 'Status'
	},{
	    field: 'START_TIME',
	    title: 'Start Time'
	},{
	    field: 'END_TIME',
	    title: 'End Time'
	},{
	    field: 'RUN_HOURS',
	    title: 'Running Time(h)'
	},{
	    title: 'Expected Time(h)',
	    formatter: function (value, row, index) {
            return 168;
        }
	}];

function formatUrl(jobUrl){
	var res = jobUrl.split("/");
	var jobName = res[res.length-3];
	return '<a href="' + "" + jobUrl + "" + '">'+"" + jobName + ""+'</a>';
}

function detailFormatter(index, row) {
    var html = [];
    var bbtStatus = row.BBT_STATUS.split(";");
    for(var i=0;i<bbtStatus.length;i++){
    	var tempRow = bbtStatus[i];
    	if(tempRow.includes("NotRun")){
    		html.push('<p class="text-yellow"><b>' + tempRow + '</b></p>');
    	}else if(tempRow.includes("Pass")){
    		html.push('<p class="text-green">' + tempRow + '</p>');
    	}else if(tempRow.includes("Fail") || tempRow.includes("Kill")){
    		html.push('<p class="text-red"><b>' + tempRow + '</b></p>');
    	}else{
    		html.push('<p>' + tempRow + '</p>');
    	}
    }
    return html.join('');
}


var $table = $('#dataTable');

$table.on('expand-row.bs.table', function(e, index, row, $detail) {
  $detailContainer.html(detailFormatter(index, row));
  $detailContainer.slideDown();
});

$table.on("click-row.bs.table", function(e, row, $tr) {

  if ($tr.next().is('tr.detail-view')) {
      $tr.next().slideUp(function() {
          // when animation complete, otherwise element will be deleted before animation
          $table.bootstrapTable('collapseRow', $tr.data('index'));
      });
  } else {
    $table.bootstrapTable('expandRow', $tr.data('index'));
  }
});


function latestJobPagination() {
    $('#dataTable').bootstrapTable({
        url: dataUrl,
        method: 'get',     
        toolbar: '#toolbar',        //一个jQuery 选择器，指明自定义的toolbar 例如: #toolbar, .toolbar.
        striped: true,           //是否显示行间隔色
        cache: true,            //设置为 false 禁用 AJAX 数据缓存,default is true
        pagination: true,          //设置为 true 会在表格底部显示分页条,default is false
        sortOrder: "asc",          //定义排序方式 'asc' 或者 'desc',default is "asc"
        sidePagination: "client", 
        pageNumber:1,          
        pageSize: 20,   
        pageList: [20, 50, 100],
        strictSearch: false,
        clickToSelect: true,
        showRefresh:true,           //是否显示 刷新按钮. default: false, 
        search:false,                //是否启用搜索框,default: false
        showColumns:false,           //是否显示 内容列下拉框,  default: false
        columns: dataColumns,   //列配置项,详情请查看 列参数 表格.
        rowStyle:function rowStyle(row, index) {
      	  return {
      		    classes: 'text-nowrap another-class',
      		    css: {"font-size": "12px"}
      		  };
      		}
    });
}

//init functions
$(function(){
	latestJobPagination();
})
