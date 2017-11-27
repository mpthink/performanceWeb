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
		dataUrl = "/vtas/arraymonitor/getMemoryList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getMemoryList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
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


