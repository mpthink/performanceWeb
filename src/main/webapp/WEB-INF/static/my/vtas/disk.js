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
		viewDisk();
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
		viewDisk();
	});
	
	//add for redirect
	showRedirect();
	
})



function redirectTo(pageName) {
	 var program = $('#programSelect').val();
	 var arrayName = $('#arrayName').val();
	 var reservationtime = $('#reservationtime').val();
     window.open("/vtas/arraymonitor/"+pageName+"?flag=true&program="+program+"&arrayName="+arrayName+"&reservationtime="+reservationtime);
 }

function showRedirect(){
	var flag = getUrlParam("flag");
	if(flag == "true" ){
		var program = getUrlParam("program");
		var arrayName = getUrlParam("arrayName");
		var reservationtime = getUrlParam("reservationtime");
		$("#programSelect").empty().append("<option value="+program+">"+program+"</option>").val(program).trigger('change');
		$("#arrayName").empty().append("<option value="+arrayName+">"+arrayName+"</option>").val(arrayName).trigger('change');
		$('#reservationtime').val(reservationtime);
		viewDisk();
	}
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}


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




function viewDisk(){
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
					                		'SPB_tmp' : false,
					                		'SPB_cores' : false,
					                		'SPB_pramfs' : false,
					                		'SPB_service' : false,
					                		'SPB_CEM' : false,
					                		'SPB_nbsnas' : false,
					                		'SPB_shared' : false,
					                		'SPB_stats' : false,
					                		'SPB_metricsluna1' : false,
					                		'SPB_fastvp' : false,
					                		
					                		'SPA_tmp' : false,
					                		'SPA_cores' : false,
					                		'SPA_pramfs' : false,
					                		'SPA_service' : false,
					                		'SPA_CEM' : false,
					                		'SPA_nbsnas' : false,
					                		'SPA_shared' : false,
					                		'SPA_stats' : false,
					                		'SPA_metricsluna1' : false,
					                		'SPA_fastvp' : false,
					                	},
					                	data: ['SPA_/','SPA_tmp','SPA_cores','SPA_pramfs','SPA_service','SPA_CEM','SPA_nbsnas','SPA_shared','SPA_stats','SPA_metricsluna1','SPA_fastvp','',
					                		   'SPB_/','SPB_tmp','SPB_cores','SPB_pramfs','SPB_service','SPB_CEM','SPB_nbsnas','SPB_shared','SPB_stats','SPB_metricsluna1','SPB_fastvp']
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
					                		'name': 'SPA_tmp',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_tmp    
					                		},
					                		{
					                		'name': 'SPA_cores',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_cores    
					                		},
					                		{
					                		'name': 'SPA_pramfs',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_pramfs    
					                		},
					                		{
					                		'name': 'SPA_service',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_service    
					                		},
					                		{
					                		'name': 'SPA_CEM',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_CEM    
					                		},
					                		{
					                		'name': 'SPA_nbsnas',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_nbsnas    
					                		},
					                		{
					                		'name': 'SPA_shared',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_log_shared    
					                		},
					                		{
					                		'name': 'SPA_stats',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_perf_stats    
					                		},
					                		{
					                		'name': 'SPA_metricsluna1',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_s_EMC_s_backend_s_metricsluna1    
					                		},
					                		{
					                		'name': 'SPA_fastvp',    
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
						                		'name': 'SPB_tmp',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_tmp    
						                		},
						                		{
						                		'name': 'SPB_cores',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_cores    
						                		},
						                		{
						                		'name': 'SPB_pramfs',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_pramfs    
						                		},
						                		{
						                		'name': 'SPB_service',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_service    
						                		},
						                		{
						                		'name': 'SPB_CEM',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_CEM    
						                		},
						                		{
						                		'name': 'SPB_nbsnas',
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_nbsnas    
						                		},
						                		{
						                		'name': 'SPB_shared',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_log_shared    
						                		},
						                		{
						                		'name': 'SPB_stats',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_perf_stats    
						                		},
						                		{
						                		'name': 'SPB_metricsluna1',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_s_EMC_s_backend_s_metricsluna1    
						                		},
						                		{
						                		'name': 'SPB_fastvp',    
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

