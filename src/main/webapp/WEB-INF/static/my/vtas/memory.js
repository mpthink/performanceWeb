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
			 
			 var SPB_memoryUsedData=[];
			 var SPB_peserviceRSSData=[];
			 var SPB_csxRSSData=[];
			 var SPB_ecomRSSData=[];
			 var SPB_mozzoRSSData=[];
			 var SPB_tomcatRSSData=[];
			 var SPB_TLDlistenerRSSData=[];
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
					 			}else if(result[i]['sp']=='SPB'){
						 			SPB_memoryUsedData.push(result[i]['MEM_USED']);
						 			SPB_peserviceRSSData.push(result[i]['peservice_exe_rss']);
						 			SPB_csxRSSData.push(result[i]['csx_ic_safe_rss']);
						 			SPB_ecomRSSData.push(result[i]['ECOM_rss']);
						 			SPB_mozzoRSSData.push(result[i]['mozzo_sh_rss']);
						 			SPB_tomcatRSSData.push(result[i]['tomcat_sh_rss']);
						 			SPB_TLDlistenerRSSData.push(result[i]['TLDlistener_exe_rss']);
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

