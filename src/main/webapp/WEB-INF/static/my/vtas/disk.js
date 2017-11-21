//init functions
$(function(){
	programSelect();

	$('#programSelect').change(function(){
		var program = $('#programSelect').val();
		var arrayName = $('#arrayName').val();
		$('#arrayName').empty();
		arraySelect(program);
	});

	$('#arrayName').change(function(){
		viewMemory();
	});
	
	$('#reservationtime').daterangepicker(		
      {
		 locale: {format: 'YYYY-MM-DD HH:mm:ss' },
        ranges   : {
          'Today'       : [moment(), moment()],
          'Yesterday'   : [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
          'Last 7 Days' : [moment().subtract(6, 'days'), moment()],
          'Last 30 Days': [moment().subtract(29, 'days'), moment()],
          'This Month'  : [moment().startOf('month'), moment().endOf('month')],
          'Last Month'  : [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        startDate: moment().subtract(3, 'days'),
        endDate  : moment()
      },
      function (start, end) {
        $('#reservationtime span').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
      }
    );
	
	$('#reservationtime').change(function(){
		viewMemory();
	});
	
})

//this function is not used
function formatDateTime(inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    var h = date.getHours();  
    h = h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    var second = date.getSeconds();  
    minute = minute < 10 ? ('0' + minute) : minute;    
    second = second < 10 ? ('0' + second) : second;   
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
};

function generateUrl(){
	var timerange = $("#reservationtime").val();
	var tableName = $('#programSelect').val() || '';
	var arrayName = $('#arrayName').val() || '';
	var timeArr;
	var beginTime;
	var endTime;
	var dataUrl;
	if(tableName==''||arrayName==''){
		layer.alert('Please select program,array,build', {icon: 0,title:'Major',closeBtn: 0,skin: 'layui-layer-molv'});
		return;
	}
	if(timerange==null||timerange==''){
		dataUrl = "/vtas/arraymonitor/getDiskList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getDiskList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
	}
	return dataUrl;
}

function programSelect(){
	
	$('#programSelect').select2({
		placeholder: 'Select Release',
		ajax: {
			cache:true,
		    url: '/vtas/arraymonitor/getPrograms',
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });
}


function arraySelect(tableName){
	
	$('#arrayName').select2({
		placeholder: 'Select Array',
		allowClear:true,
		ajax: {
			cache:true,
		    url: '/vtas/arraymonitor/getDistinctArrayList/'+tableName+'/',
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });	
}

//commented...
function buildSelect(tableName,arrayName){
	
	$('#buildVersion').select2({
		placeholder: 'Select Build',
		allowClear:true,
		ajax: {
		    url: '/vtas/arraymonitor/getDistinctVersionByArray/'+tableName+'/'+arrayName,
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });	
}




function viewMemory(){
	require.config({
	    paths: {
	        echarts: '/plugins/echart/dist'
	    }
	});
	require(
		    [
		        'echarts',
		        'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
		        'echarts/chart/bar'
		        ],
			function (ec) {
			 var dataUrl = generateUrl();
			 var Chart=ec.init(document.getElementById("main"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var xAxisData;

			 var SPA_s_=[];
			 var SPA_s_tmp=[];
			 var SPA_s_cores=[];
			 var SPA_s_pramfs=[];
			 var SPA_s_EMC_s_backend_s_service=[];
			 var SPA_s_EMC_s_backend_s_CEM=[];
			 var SPA_s_nbsnas=[];
			 var SPA_s_EMC_s_backend_s_log_shared=[];
			 var SPA_s_EMC_s_backend_s_perf_stats=[];
			 var SPA_s_EMC_s_backend_s_metricsluna1=[];
			 var SPA_s_EMC_s_fastvp=[];
			 
			 var SPB_s_=[];
			 var SPB_s_tmp=[];
			 var SPB_s_cores=[];
			 var SPB_s_pramfs=[];
			 var SPB_s_EMC_s_backend_s_service=[];
			 var SPB_s_EMC_s_backend_s_CEM=[];
			 var SPB_s_nbsnas=[];
			 var SPB_s_EMC_s_backend_s_log_shared=[];
			 var SPB_s_EMC_s_backend_s_perf_stats=[];
			 var SPB_s_EMC_s_backend_s_metricsluna1=[];
			 var SPB_s_EMC_s_fastvp=[];

			 var times=[];
			 $.ajax({  
			        url:dataUrl,  
			        dataType:"json",  
			        type:'post',  
			        success:function(result){
			        	if(result==null||result==''){
			        		layer.alert('No data found', {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
					 		Chart.hideLoading();
					 	}else{
					 		for(var i=0;i<result.length;i++){
					 			if(result[i]['sp']=='SPA'){
					 				
						 			times.push(result[i]['poll_datetime']);
						 			SPA_s_.push(result[i]['_s_']);
						 			SPA_s_tmp.push(result[i]['_s_tmp']);
						 			SPA_s_cores.push(result[i]['_s_cores']);
						 			SPA_s_pramfs.push(result[i]['_s_pramfs']);
						 			SPA_s_EMC_s_backend_s_service.push(result[i]['_s_EMC_s_backend_s_service']);
						 			SPA_s_EMC_s_backend_s_CEM.push(result[i]['_s_EMC_s_backend_s_CEM']);
						 			SPA_s_nbsnas.push(result[i]['_s_nbsnas']);
						 			SPA_s_EMC_s_backend_s_log_shared.push(result[i]['_s_EMC_s_backend_s_log_shared']);
						 			SPA_s_EMC_s_backend_s_perf_stats.push(result[i]['_s_EMC_s_backend_s_perf_stats']);
						 			SPA_s_EMC_s_backend_s_metricsluna1.push(result[i]['_s_EMC_s_backend_s_metricsluna1']);
						 			SPA_s_EMC_s_fastvp.push(result[i]['_s_EMC_s_fastvp']);
					 			}else if(result[i]['sp']=='SPB'){
					 				
					 				SPB_s_.push(result[i]['_s_']);
						 			SPB_s_tmp.push(result[i]['_s_tmp']);
						 			SPB_s_cores.push(result[i]['_s_cores']);
						 			SPB_s_pramfs.push(result[i]['_s_pramfs']);
						 			SPB_s_EMC_s_backend_s_service.push(result[i]['_s_EMC_s_backend_s_service']);
						 			SPB_s_EMC_s_backend_s_CEM.push(result[i]['_s_EMC_s_backend_s_CEM']);
						 			SPB_s_nbsnas.push(result[i]['_s_nbsnas']);
						 			SPB_s_EMC_s_backend_s_log_shared.push(result[i]['_s_EMC_s_backend_s_log_shared']);
						 			SPB_s_EMC_s_backend_s_perf_stats.push(result[i]['_s_EMC_s_backend_s_perf_stats']);
						 			SPB_s_EMC_s_backend_s_metricsluna1.push(result[i]['_s_EMC_s_backend_s_metricsluna1']);
						 			SPB_s_EMC_s_fastvp.push(result[i]['_s_EMC_s_fastvp']);
						 			
					 		  }
					 		}
					 	}
			        	
			        	 var option = {
			        			 noDataLoadingOption: {
			                         	text: 'No data in this timeslot',
			 							textStyle: {fontSize:16,fontWeight:'bold'},
			 							effect:'bubble'
			 					 	},
					                tooltip: {
					                	trigger: 'axis',
					                    show: true    
					                },    
					                legend: {
					                	x:"center",
					                	textStyle:{color:'auto'},
					                	borderColor:'#333',
					                	borderWidth:1,
					                	selected: {
					                		'SPB_/tmp' : false,
					                		'SPB_/cores' : false,
					                		'SPB_/pramfs' : false,
					                		'SPB_/EMC_s_backend_s_service' : false,
					                		'SPB_/EMC_s_backend_s_CEM' : false,
					                		'SPB_/nbsnas' : false,
					                		'SPB_/EMC_s_backend_s_log_shared' : false,
					                		'SPB_/EMC_s_backend_s_perf_stats' : false,
					                		'SPB_/EMC_s_backend_s_metricsluna1' : false,
					                		'SPB_/EMC_s_fastvp' : false,
					                		
					                		'SPA_/tmp' : false,
					                		'SPA_/cores' : false,
					                		'SPA_/pramfs' : false,
					                		'SPA_/EMC_s_backend_s_service' : false,
					                		'SPA_/EMC_s_backend_s_CEM' : false,
					                		'SPA_/nbsnas' : false,
					                		'SPA_/EMC_s_backend_s_log_shared' : false,
					                		'SPA_/EMC_s_backend_s_perf_stats' : false,
					                		'SPA_/EMC_s_backend_s_metricsluna1' : false,
					                		'SPA_/EMC_s_fastvp' : false,
					                	},
					                	data: ['SPA_/','SPA_/tmp','SPA_/cores','SPA_/pramfs','SPA_/EMC_/backend_/service','SPA_/EMC_/backend_/CEM','SPA_/nbsnas','SPA_/EMC_/backend_/log_shared','SPA_/EMC_/backend_/perf_stats','SPA_/EMC_/backend_/metricsluna1','SPA_/EMC_/fastvp','',
					                		   'SPB_/','SPB_/tmp','SPB_/cores','SPB_/pramfs','SPB_/EMC_/backend_/service','SPB_/EMC_/backend_/CEM','SPB_/nbsnas','SPB_/EMC_/backend_/log_shared','SPB_/EMC_/backend_/perf_stats','SPB_/EMC_/backend_/metricsluna1','SPB_/EMC_/fastvp']
					                },
					                toolbox: {
					    				show : false,
					    				feature : {
					    				    mark : {show: true},
					    				    dataZoom : {show: true},
					    				    dataView : {show: true},
					    				    magicType : {show: true, type: ['line','bar']},
					    				    restore : {show: true},
					    				    saveAsImage : {show: true}
					    				}
				    				},
				    				calculable : true,
				    				dataZoom : {
				    					show : true,
				    					realtime : true
				    				},
					                xAxis: [    
					                    {    
					                        type: 'category',    
					                        data: times
					                    }    
					                ],    
					                yAxis: [    
					                    {    
					                        type: 'value',
					                        axisLabel : {
					                            formatter: '{value} %'
					                        }
					                    }    
					                ],    
					                series: [
					                	{
					                		'name': 'SPA_/',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		center: ['50%', '90%'],
					                		'data': SPA_s_    
					                		},
					                		{
					                		'name': 'SPA_/tmp',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_tmp    
					                		},
					                		{
					                		'name': 'SPA_/cores',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_cores    
					                		},
					                		{
					                		'name': 'SPA_/pramfs',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_pramfs    
					                		},
					                		{
					                		'name': 'SPA_/EMC_s_backend_s_service',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_service    
					                		},
					                		{
					                		'name': 'SPA_/EMC_s_backend_s_CEM',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_CEM    
					                		},
					                		{
					                		'name': 'SPA_/nbsnas',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_nbsnas    
					                		},
					                		{
					                		'name': 'SPA_/EMC_s_backend_s_log_shared',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_log_shared    
					                		},
					                		{
					                		'name': 'SPA_/EMC_s_backend_s_perf_stats',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_perf_stats    
					                		},
					                		{
					                		'name': 'SPA_/EMC_s_backend_s_metricsluna1',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_metricsluna1    
					                		},
					                		{
					                		'name': 'SPA_/EMC_s_fastvp',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_fastvp    
					                		},
						                	{
						                		'name': 'SPB_/',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_    
						                		},
						                		{
						                		'name': 'SPB_/tmp',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_tmp    
						                		},
						                		{
						                		'name': 'SPB_/cores',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_cores    
						                		},
						                		{
						                		'name': 'SPB_/pramfs',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_pramfs    
						                		},
						                		{
						                		'name': 'SPB_/EMC_s_backend_s_service',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_service    
						                		},
						                		{
						                		'name': 'SPB_/EMC_s_backend_s_CEM',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_CEM    
						                		},
						                		{
						                		'name': 'SPB_/nbsnas',
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_nbsnas    
						                		},
						                		{
						                		'name': 'SPB_/EMC_s_backend_s_log_shared',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_log_shared    
						                		},
						                		{
						                		'name': 'SPB_/EMC_s_backend_s_perf_stats',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_perf_stats    
						                		},
						                		{
						                		'name': 'SPB_/EMC_s_backend_s_metricsluna1',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_metricsluna1    
						                		},
						                		{
						                		'name': 'SPB_/EMC_s_fastvp',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_fastvp    
						                		}
					                ]
					            };
						
				        Chart.hideLoading();  
				        Chart.setOption(option);    
			        },
			        error : function() {
						 //请求失败时执行该函数
						 alert("Failed to get ajax data!");
						 Chart.hideLoading();
					 }
			    }); 
		    })
}

