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
		viewCPU();
		viewBandwidth();
		viewIOPS();
		viewDisk();
	});
	
	$('#reservationtime').daterangepicker(		
      {
		 locale: {format: 'YYYY-MM-DD HH:mm:ss' },
        ranges   : {
          'Last 24 Hours'   : [moment().subtract(1, 'days'), moment()],
          'Last 3 Days' : [moment().subtract(3, 'days'), moment()],
          'Last 7 Days' : [moment().subtract(6, 'days'), moment()],
          'Last 30 Days': [moment().subtract(29, 'days'), moment()],
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
		viewCPU();
		viewBandwidth();
		viewIOPS();
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
		viewMemory();
	}
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}


function generateMemoryUrl(){
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
		dataUrl = "/vtas/arraymonitor/getMemoryList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getMemoryList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
	}
	return dataUrl;
}

function generateCPUUrl(){
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
		dataUrl = "/vtas/arraymonitor/getCPUList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getCPUList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
	}
	return dataUrl;
}

function generateBandwidthUrl(){
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
		dataUrl = "/vtas/arraymonitor/getBandwidthList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getBandwidthList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
	}
	return dataUrl;
}

function generateIOPSUrl(){
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
		dataUrl = "/vtas/arraymonitor/getIOPSList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getIOPSList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
	}
	return dataUrl;
}

function generateDiskUrl(){
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
			 var dataUrl = generateMemoryUrl();
			 var Chart=ec.init(document.getElementById("memoryMain"));
			 Chart.showLoading({text: 'Loding...'});  
			 var xAxisData;
			 var SPA_memoryUsedData=[];
			 var SPA_peserviceRSSData=[];
			 var SPA_csxRSSData=[];
			 var SPA_ecomRSSData=[];
			 var SPA_mozzoRSSData=[];
			 var SPA_tomcatRSSData=[];
			 var SPA_TLDlistenerRSSData=[];
			 //PID for SPA
			 var SPA_pid_PEService={};
			 var SPA_pid_TLDlistener={};
			 var SPA_pid_ECOM={};
			 var SPA_pid_tomcat={};
			 var SPA_pid_csx={};
			 var SPA_pid_mozzo={};
			 
			 var SPB_memoryUsedData=[];
			 var SPB_peserviceRSSData=[];
			 var SPB_csxRSSData=[];
			 var SPB_ecomRSSData=[];
			 var SPB_mozzoRSSData=[];
			 var SPB_tomcatRSSData=[];
			 var SPB_TLDlistenerRSSData=[];
			//PID for SPB
			 var SPB_pid_PEService={};
			 var SPB_pid_TLDlistener={};
			 var SPB_pid_ECOM={};
			 var SPB_pid_tomcat={};
			 var SPB_pid_csx={};
			 var SPB_pid_mozzo={};
			 
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
						 			SPA_memoryUsedData.push(result[i]['MEM_USED']);
						 			SPA_peserviceRSSData.push(result[i]['peservice_exe_rss']);
						 			SPA_csxRSSData.push(result[i]['csx_ic_safe_rss']);
						 			SPA_ecomRSSData.push(result[i]['ECOM_rss']);
						 			SPA_mozzoRSSData.push(result[i]['mozzo_sh_rss']);
						 			SPA_tomcatRSSData.push(result[i]['tomcat_sh_rss']);
						 			SPA_TLDlistenerRSSData.push(result[i]['TLDlistener_exe_rss']);
						 			//for pid
						 			SPA_pid_PEService[result[i]['poll_datetime']]=result[i]['pid_PEService_exe'];
						 			SPA_pid_TLDlistener[result[i]['poll_datetime']]=result[i]['pid_TLDlistener_exe'];
						 			SPA_pid_ECOM[result[i]['poll_datetime']]=result[i]['pid_ECOM'];
						 			SPA_pid_tomcat[result[i]['poll_datetime']]=result[i]['pid_tomcat_sh'];
						 			SPA_pid_csx[result[i]['poll_datetime']]=result[i]['pid_csx_ic_safe'];
						 			SPA_pid_mozzo[result[i]['poll_datetime']]=result[i]['pid_mozzo_sh'];
					 			}else if(result[i]['sp']=='SPB'){
						 			SPB_memoryUsedData.push(result[i]['MEM_USED']);
						 			SPB_peserviceRSSData.push(result[i]['peservice_exe_rss']);
						 			SPB_csxRSSData.push(result[i]['csx_ic_safe_rss']);
						 			SPB_ecomRSSData.push(result[i]['ECOM_rss']);
						 			SPB_mozzoRSSData.push(result[i]['mozzo_sh_rss']);
						 			SPB_tomcatRSSData.push(result[i]['tomcat_sh_rss']);
						 			SPB_TLDlistenerRSSData.push(result[i]['TLDlistener_exe_rss']);
						 			//for pid
						 			SPB_pid_PEService[result[i]['poll_datetime']]=result[i]['pid_PEService_exe'];
						 			SPB_pid_TLDlistener[result[i]['poll_datetime']]=result[i]['pid_TLDlistener_exe'];
						 			SPB_pid_ECOM[result[i]['poll_datetime']]=result[i]['pid_ECOM'];
						 			SPB_pid_tomcat[result[i]['poll_datetime']]=result[i]['pid_tomcat_sh'];
						 			SPB_pid_csx[result[i]['poll_datetime']]=result[i]['pid_csx_ic_safe'];
						 			SPB_pid_mozzo[result[i]['poll_datetime']]=result[i]['pid_mozzo_sh'];
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
					                    show: true,
					                    formatter: function (params,ticket,callback) {
					                    	var time= params[0].name;
					                        var relVal = params[0].name;
					                          for (var i = 0, l = params.length; i < l; i++) {
					                        	  var pid=-1;
					                        	  if(params[i].seriesName=='SPA_peservice_RSS'){
					                        		  pid = SPA_pid_PEService[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPA_csx_ic_safe_RSS'){
					                        		  pid = SPA_pid_csx[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPA_ECOM_RSS'){
					                        		  pid = SPA_pid_ECOM[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPA_mozzo_sh_RSS'){
					                        		  pid = SPA_pid_mozzo[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPA_tomcat_RSS'){
					                        		  pid = SPA_pid_tomcat[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPA_TLDlistener_RSS'){
					                        		  pid = SPA_pid_TLDlistener[time];
					                        	  }
					                        	  
					                        	  if(params[i].seriesName=='SPB_peservice_RSS'){
					                        		  pid = SPB_pid_PEService[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPB_csx_ic_safe_RSS'){
					                        		  pid = SPB_pid_csx[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPB_ECOM_RSS'){
					                        		  pid = SPB_pid_ECOM[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPB_mozzo_sh_RSS'){
					                        		  pid = SPB_pid_mozzo[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPB_tomcat_RSS'){
					                        		  pid = SPB_pid_tomcat[time];
					                        	  }
					                        	  if(params[i].seriesName=='SPB_TLDlistener_RSS'){
					                        		  pid = SPB_pid_TLDlistener[time];
					                        	  }
					                        	  
					                              relVal += '<br/>' + params[i].seriesName + ' : ' + params[i].value+",&nbsp;&nbsp;&nbsp;&nbsp;pid:"+pid;
					                          }
					                          return relVal;
					                      }
					                },    
					                legend: {
					                	x:"center",
					                	textStyle:{color:'auto'},
					                	borderColor:'#333',
					                	borderWidth:1,
					                	selected: {
					                		'SPA_peservice_RSS' : false,
					                		'SPA_csx_ic_safe_RSS' : false,
					                		'SPA_ECOM_RSS' : false,
					                		'SPA_mozzo_sh_RSS' : false,
					                		'SPA_tomcat_RSS' : false,
					                		'SPA_TLDlistener_RSS' : false,
					                		'SPB_peservice_RSS' : false,
					                		'SPB_csx_ic_safe_RSS' : false,
					                		'SPB_ECOM_RSS' : false,
					                		'SPB_mozzo_sh_RSS' : false,
					                		'SPB_tomcat_RSS' : false,
					                		'SPB_TLDlistener_RSS' : false
					                	},
					                	data: ['SPA_MemoryUsed','SPA_peservice_RSS','SPA_csx_ic_safe_RSS','SPA_ECOM_RSS','SPA_mozzo_sh_RSS','SPA_tomcat_RSS','SPA_TLDlistener_RSS','','SPB_MemoryUsed','SPB_peservice_RSS','SPB_csx_ic_safe_RSS','SPB_ECOM_RSS','SPB_mozzo_sh_RSS','SPB_tomcat_RSS','SPB_TLDlistener_RSS']
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
					                            formatter: '{value} MB'
					                        }
					                    }    
					                ],    
					                series: [
					                	{
					                		'name': 'SPA_MemoryUsed',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_memoryUsedData    
					                		},
					                		{
					                		'name': 'SPA_peservice_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_peserviceRSSData    
					                		},
					                		{
					                		'name': 'SPA_csx_ic_safe_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_csxRSSData    
					                		},
					                		{
					                		'name': 'SPA_ECOM_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_ecomRSSData    
					                		},
					                		{
					                		'name': 'SPA_mozzo_sh_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_mozzoRSSData    
					                		},
					                		{
					                		'name': 'SPA_tomcat_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_tomcatRSSData    
					                		},
					                		{
					                		'name': 'SPA_TLDlistener_RSS',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_TLDlistenerRSSData    
					                		},
					                		{
					                		'name': 'SPB_MemoryUsed',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_memoryUsedData    
					                		},
					                		{
					                		'name': 'SPB_peservice_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_peserviceRSSData    
					                		},
					                		{
					                		'name': 'SPB_csx_ic_safe_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_csxRSSData    
					                		},
					                		{
					                		'name': 'SPB_ECOM_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_ecomRSSData    
					                		},
					                		{
					                		'name': 'SPB_mozzo_sh_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_mozzoRSSData    
					                		},
					                		{
					                		'name': 'SPB_tomcat_RSS',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_tomcatRSSData    
					                		},
					                		{
					                		'name': 'SPB_TLDlistener_RSS',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_TLDlistenerRSSData    
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

function viewCPU(){
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
			 var dataUrl = generateCPUUrl();
			 var Chart=ec.init(document.getElementById("cpuMain"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var xAxisData;
			 var SPA_CPU_FILT=[];
			 var SPA_cpu_PEService_exe=[];
			 var SPA_cpu_csx_ic_safe=[];
			 var SPA_cpu_ECOM=[];
			 var SPA_cpu_mozzo_sh=[];
			 var SPA_cpu_tomcat_sh=[];
			 var SPA_cpu_TLDlistener_exe=[];
			 
			 var SPB_CPU_FILT=[];
			 var SPB_cpu_PEService_exe=[];
			 var SPB_cpu_csx_ic_safe=[];
			 var SPB_cpu_ECOM=[];
			 var SPB_cpu_mozzo_sh=[];
			 var SPB_cpu_tomcat_sh=[];
			 var SPB_cpu_TLDlistener_exe=[];
			 
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
						 			SPA_CPU_FILT.push(result[i]['CPU_FILT']);
						 			SPA_cpu_PEService_exe.push(result[i]['cpu_PEService_exe']);
						 			SPA_cpu_csx_ic_safe.push(result[i]['cpu_csx_ic_safe']);
						 			SPA_cpu_ECOM.push(result[i]['cpu_ECOM']);
						 			SPA_cpu_mozzo_sh.push(result[i]['cpu_mozzo_sh']);
						 			SPA_cpu_tomcat_sh.push(result[i]['cpu_tomcat_sh']);
						 			SPA_cpu_TLDlistener_exe.push(result[i]['cpu_TLDlistener_exe']);
						 			
					 			}else if(result[i]['sp']=='SPB'){
					 				
					 				SPB_CPU_FILT.push(result[i]['CPU_FILT']);
					 				SPB_cpu_PEService_exe.push(result[i]['cpu_PEService_exe']);
					 				SPB_cpu_csx_ic_safe.push(result[i]['cpu_csx_ic_safe']);
					 				SPB_cpu_ECOM.push(result[i]['cpu_ECOM']);
					 				SPB_cpu_mozzo_sh.push(result[i]['cpu_mozzo_sh']);
					 				SPB_cpu_tomcat_sh.push(result[i]['cpu_tomcat_sh']);
					 				SPB_cpu_TLDlistener_exe.push(result[i]['cpu_TLDlistener_exe']);
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
					                		'SPA_CPU_FILT' : true,
					                		'SPB_CPU_FILT' : true,
					                		'SPA_PEService' : false,
					                		'SPA_csx_ic_safe' : false,
					                		'SPA_ECOM' : false,
					                		'SPA_mozzo_sh' : false,
					                		'SPA_tomcat_sh' : false,
					                		'SPA_TLDlistener' : false,
					                		'SPB_PEService' : false,
					                		'SPB_csx_ic_safe' : false,
					                		'SPB_ECOM' : false,
					                		'SPB_mozzo_sh' : false,
					                		'SPB_tomcat_sh' : false,
					                		'SPB_TLDlistener' : false,
					                	},
					                	data: ['SPA_CPU','SPA_PEService','SPA_csx_ic_safe','SPA_ECOM','SPA_mozzo_sh','SPA_tomcat_sh','SPA_TLDlistener','','SPB_CPU','SPB_PEService','SPB_csx_ic_safe','SPB_ECOM','SPB_mozzo_sh','SPB_tomcat_sh','SPB_TLDlistener']
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
					                		'name': 'SPA_CPU',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_CPU_FILT    
					                		},
					                		{
						                		'name': 'SPA_PEService',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPA_cpu_PEService_exe    
						                	},
					                		{
						                		'name': 'SPA_csx_ic_safe',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPA_cpu_csx_ic_safe    
						                	},
					                		{
						                		'name': 'SPA_ECOM',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPA_cpu_ECOM    
						                	},
					                		{
						                		'name': 'SPA_mozzo_sh',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPA_cpu_mozzo_sh    
						                	},
					                		{
						                		'name': 'SPA_tomcat_sh',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPA_cpu_tomcat_sh    
						                	},
					                		{
						                		'name': 'SPA_TLDlistener',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPA_cpu_TLDlistener_exe    
						                	},
						                	
					                		{
						                		'name': 'SPB_CPU',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_CPU_FILT    
					                		},
						                	{
						                		'name': 'SPB_PEService',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_cpu_PEService_exe
						                	},
					                		{
						                		'name': 'SPB_csx_ic_safe',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_cpu_csx_ic_safe    
						                	},
					                		{
						                		'name': 'SPB_ECOM',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_cpu_ECOM    
						                	},
					                		{
						                		'name': 'SPB_mozzo_sh',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_cpu_mozzo_sh    
						                	},
					                		{
						                		'name': 'SPB_tomcat_sh',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_cpu_tomcat_sh    
						                	},
					                		{
						                		'name': 'SPB_TLDlistener',    
						                		'type': 'line',
						                		'smooth':true,
						                		'symbol':'none',
						                		'data': SPB_cpu_TLDlistener_exe    
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

function viewBandwidth(){
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
			 var dataUrl = generateBandwidthUrl();
			 var Chart=ec.init(document.getElementById("bandwidthMain"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var xAxisData;
			 var SPA_IO_READ=[];
			 var SPA_IO_WRITE=[];

			 var SPB_IO_READ=[];
			 var SPB_IO_WRITE=[];

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
						 			SPA_IO_READ.push(result[i]['IO_READ']);
						 			SPA_IO_WRITE.push(result[i]['IO_WRITE']);
					 			}else if(result[i]['sp']=='SPB'){
					 				
					 				SPB_IO_READ.push(result[i]['IO_READ']);
					 				SPB_IO_WRITE.push(result[i]['IO_WRITE']);

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
					                		'SPA_IO_WRITE' : false,
					                		'SPB_IO_WRITE' : false,
					                	},
					                	data: ['SPA_IO_READ','SPA_IO_WRITE','','SPB_IO_READ','SPB_IO_WRITE']
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
					                            formatter: '{value}'
					                        }
					                    }    
					                ],    
					                series: [
					                	{
					                		'name': 'SPA_IO_READ',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_IO_READ    
					                		},
					                		{
					                		'name': 'SPA_IO_WRITE',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_IO_WRITE    
					                		},
					                		{
					                		'name': 'SPB_IO_READ',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_IO_READ    
					                		},
					                		{
					                		'name': 'SPB_IO_WRITE',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_IO_WRITE    
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

function viewIOPS(){
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
			 var dataUrl = generateIOPSUrl();
			 var Chart=ec.init(document.getElementById("iopsMain"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var xAxisData;
			 var SPA_IOPS_WRITE=[];
			 var SPA_IOPS_READ=[];

			 var SPB_IOPS_WRITE=[];
			 var SPB_IOPS_READ=[];

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
						 			SPA_IOPS_WRITE.push(result[i]['IOPS_WRITE']);
						 			SPA_IOPS_READ.push(result[i]['IOPS_READ']);
					 			}else if(result[i]['sp']=='SPB'){
					 				
					 				SPB_IOPS_WRITE.push(result[i]['IOPS_WRITE']);
					 				SPB_IOPS_READ.push(result[i]['IOPS_READ']);

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
					                		'SPA_IOPS_WRITE' : false,
					                		'SPB_IOPS_WRITE' : false,
					                	},
					                	data: ['SPA_IOPS_READ','SPA_IOPS_WRITE','','SPB_IOPS_READ','SPB_IOPS_WRITE']
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
					                            formatter: '{value}'
					                        }
					                    }    
					                ],    
					                series: [
					                	{
					                		'name': 'SPA_IOPS_READ',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_IOPS_READ    
					                		},
					                		{
					                		'name': 'SPA_IOPS_WRITE',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_IOPS_WRITE    
					                		},
					                		{
					                		'name': 'SPB_IOPS_READ',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_IOPS_READ    
					                		},
					                		{
					                		'name': 'SPB_IOPS_WRITE',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_IOPS_WRITE    
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
			 var dataUrl = generateDiskUrl();
			 var Chart=ec.init(document.getElementById("diskMain"));  
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
