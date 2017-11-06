//init functions
$(function(){
	programSelect();

	$('#programSelect').change(function(){
		var program = $('#programSelect').val();
		var arrayName = $('#arrayName').val();
		$('#arrayName').empty();
		$("#buildVersion").select2({data:""});
		$('#buildVersion').empty();
		arraySelect(program);
	});
	
	$('#arrayName').change(function(){
		var program = $('#programSelect').val();
		var arrayName = $('#arrayName').val();
		$('#buildVersion').empty();
		buildSelect(program,arrayName);
	});
	
	$('input[name="reservationtime"]').daterangepicker({
		timePicker: true,
		timePickerIncrement: 30,
		timePicker24Hour:true,
		locale: {
			format: 'YYYY-MM-DD HH:mm:ss'
		}
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
	var smallVersion = $('#buildVersion').val() || '';
	var timeArr;
	var beginTime;
	var endTime;
	var dataUrl;
	if(tableName==''||arrayName==''||smallVersion==''){
		layer.alert('Please select program,array,build', {icon: 0,title:'Major',closeBtn: 0,skin: 'layui-layer-molv'});
		return;
	}
	if(timerange==null||timerange==''){
		dataUrl = "/vtas/arraymonitor/getMemoryList/"+tableName+"/"+arrayName+"/"+smallVersion;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getMemoryList/"+tableName+"/"+arrayName+"/"+smallVersion+"?beginTime="+beginTime+"&endTime="+endTime;
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
		        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
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
					                tooltip: {
					                	trigger: 'axis',
					                    show: true    
					                },    
					                legend: {
					                	selected: {
					                		'SPA_peservice_R' : false,
					                		'SPA_csx_ic_safe_R' : false,
					                		'SPA_ECOM_R' : false,
					                		'SPA_mozzo_sh_R' : false,
					                		'SPA_tomcat_R' : false,
					                		'SPA_TLDlistener_R' : false,
					                		'SPB_peservice_R' : false,
					                		'SPB_csx_ic_safe_R' : false,
					                		'SPB_ECOM_R' : false,
					                		'SPB_mozzo_sh_R' : false,
					                		'SPB_tomcat_R' : false,
					                		'SPB_TLDlistener_R' : false
					                    },
					                    data: ['SPA_MemoryUsed','SPA_peservice_R','SPA_csx_ic_safe_R','SPA_ECOM_R','SPA_mozzo_sh_R','SPA_tomcat_R','SPA_TLDlistener_R','SPB_MemoryUsed','SPB_peservice_R','SPB_csx_ic_safe_R','SPB_ECOM_R','SPB_mozzo_sh_R','SPB_tomcat_R','SPB_TLDlistener_R']     
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
				    					realtime : true,
				    					start : 20,
				    					end : 80
				    				},
					                xAxis: [    
					                    {    
					                        type: 'category',    
					                        data: times
					                    }    
					                ],    
					                yAxis: [    
					                    {    
					                        type: 'value'    
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
					                		'name': 'SPA_peservice_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_peserviceRSSData    
					                		},
					                		{
					                		'name': 'SPA_csx_ic_safe_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_csxRSSData    
					                		},
					                		{
					                		'name': 'SPA_ECOM_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_ecomRSSData    
					                		},
					                		{
					                		'name': 'SPA_mozzo_sh_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_mozzoRSSData    
					                		},
					                		{
					                		'name': 'SPA_tomcat_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_tomcatRSSData    
					                		},
					                		{
					                		'name': 'SPA_TLDlistener_R',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'emptyCircle',
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
					                		'name': 'SPB_peservice_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_peserviceRSSData    
					                		},
					                		{
					                		'name': 'SPB_csx_ic_safe_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_csxRSSData    
					                		},
					                		{
					                		'name': 'SPB_ECOM_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_ecomRSSData    
					                		},
					                		{
					                		'name': 'SPB_mozzo_sh_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_mozzoRSSData    
					                		},
					                		{
					                		'name': 'SPB_tomcat_R',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_tomcatRSSData    
					                		},
					                		{
					                		'name': 'SPB_TLDlistener_R',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'emptyCircle',
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

