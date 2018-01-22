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
		viewCPU();
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
		viewCPU();
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
		viewCPU();
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
		dataUrl = "/vtas/arraymonitor/getCPUList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getCPUList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
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
			 var dataUrl = generateUrl();
			 var Chart=ec.init(document.getElementById("main"));  
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

