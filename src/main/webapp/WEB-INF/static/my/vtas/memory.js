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
			 var SPA_peserviceVSZData=[];
			 var SPA_peserviceRSSData=[];
			 var SPA_csxVSZData=[];
			 var SPA_csxRSSData=[];
			 var SPA_ecomVSZData=[];
			 var SPA_ecomRSSData=[];
			 var SPA_mozzoVSZData=[];
			 var SPA_mozzoRSSData=[];
			 var SPA_tomcatVSZData=[];
			 var SPA_tomcatRSSData=[];
			 var SPA_TLDlistenerVSZData=[];
			 var SPA_TLDlistenerRSSData=[];
			 
			 var SPB_memoryUsedData=[];
			 var SPB_peserviceVSZData=[];
			 var SPB_peserviceRSSData=[];
			 var SPB_csxVSZData=[];
			 var SPB_csxRSSData=[];
			 var SPB_ecomVSZData=[];
			 var SPB_ecomRSSData=[];
			 var SPB_mozzoVSZData=[];
			 var SPB_mozzoRSSData=[];
			 var SPB_tomcatVSZData=[];
			 var SPB_tomcatRSSData=[];
			 var SPB_TLDlistenerVSZData=[];
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
						 			times.push(formatDateTime(result[i]['poll_datetime']));
						 			SPA_memoryUsedData.push(result[i]['MEM_USED']);
						 			SPA_peserviceVSZData.push(result[i]['peservice_exe_vsz']);
						 			SPA_peserviceRSSData.push(result[i]['peservice_exe_rss']);
						 			SPA_csxVSZData.push(result[i]['csx_ic_safe_vsz']);
						 			SPA_csxRSSData.push(result[i]['csx_ic_safe_rss']);
						 			SPA_ecomVSZData.push(result[i]['ECOM_vsz']);
						 			SPA_ecomRSSData.push(result[i]['ECOM_rss']);
						 			SPA_mozzoVSZData.push(result[i]['mozzo_sh_vsz']);
						 			SPA_mozzoRSSData.push(result[i]['mozzo_sh_rss']);
						 			SPA_tomcatVSZData.push(result[i]['tomcat_sh_vsz']);
						 			SPA_tomcatRSSData.push(result[i]['tomcat_sh_rss']);
						 			SPA_TLDlistenerVSZData.push(result[i]['TLDlistener_exe_vsz']);
						 			SPA_TLDlistenerRSSData.push(result[i]['TLDlistener_exe_rss']);
					 			}else if(result[i]['sp']=='SPB'){
						 			SPB_memoryUsedData.push(result[i]['MEM_USED']);
						 			SPB_peserviceVSZData.push(result[i]['peservice_exe_vsz']);
						 			SPB_peserviceRSSData.push(result[i]['peservice_exe_rss']);
						 			SPB_csxVSZData.push(result[i]['csx_ic_safe_vsz']);
						 			SPB_csxRSSData.push(result[i]['csx_ic_safe_rss']);
						 			SPB_ecomVSZData.push(result[i]['ECOM_vsz']);
						 			SPB_ecomRSSData.push(result[i]['ECOM_rss']);
						 			SPB_mozzoVSZData.push(result[i]['mozzo_sh_vsz']);
						 			SPB_mozzoRSSData.push(result[i]['mozzo_sh_rss']);
						 			SPB_tomcatVSZData.push(result[i]['tomcat_sh_vsz']);
						 			SPB_tomcatRSSData.push(result[i]['tomcat_sh_rss']);
						 			SPB_TLDlistenerVSZData.push(result[i]['TLDlistener_exe_vsz']);
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
					                		'SPA_peservice.exe_vsz' : false,
					                		'SPA_csx_ic_safe_vsz' : false,
					                		'SPA_ECOM_vsz' : false,
					                		'SPA_mozzo_sh_vsz' : false,
					                		'SPA_tomcat.sh_vsz' : false,
					                		'SPA_TLDlistener.exe_vsz' : false,
					                		'SPA_peservice.exe_rss' : false,
					                		'SPA_csx_ic_safe_rss' : false,
					                		'SPA_ECOM_rss' : false,
					                		'SPA_mozzo_sh_rss' : false,
					                		'SPA_tomcat.sh_rss' : false,
					                		'SPA_TLDlistener.exe_rss' : false,
					                		'SPB_peservice.exe_vsz' : false,
					                		'SPB_csx_ic_safe_vsz' : false,
					                		'SPB_ECOM_vsz' : false,
					                		'SPB_mozzo_sh_vsz' : false,
					                		'SPB_tomcat.sh_vsz' : false,
					                		'SPB_TLDlistener.exe_vsz' : false,
					                		'SPB_peservice.exe_rss' : false,
					                		'SPB_csx_ic_safe_rss' : false,
					                		'SPB_ECOM_rss' : false,
					                		'SPB_mozzo_sh_rss' : false,
					                		'SPB_tomcat.sh_rss' : false,
					                		'SPB_TLDlistener.exe_rss' : false
					                    },
					                    data: ['SPA_MemoryUsed','SPA_peservice.exe_vsz','SPA_peservice.exe_rss','SPA_csx_ic_safe_vsz','SPA_csx_ic_safe_rss','SPA_ECOM_vsz','SPA_ECOM_rss','SPA_mozzo_sh_vsz','SPA_mozzo_sh_rss','SPA_tomcat.sh_vsz','SPA_tomcat.sh_rss','SPA_TLDlistener.exe_vsz','SPA_TLDlistener.exe_rss','SPB_MemoryUsed','SPB_peservice.exe_vsz','SPB_peservice.exe_rss','SPB_csx_ic_safe_vsz','SPB_csx_ic_safe_rss','SPB_ECOM_vsz','SPB_ECOM_rss','SPB_mozzo_sh_vsz','SPB_mozzo_sh_rss','SPB_tomcat.sh_vsz','SPB_tomcat.sh_rss','SPB_TLDlistener.exe_vsz','SPB_TLDlistener.exe_rss']     
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
					                		'name': 'SPA_peservice.exe_vsz',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_peserviceVSZData    
					                		},
					                		{
					                		'name': 'SPA_peservice.exe_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_peserviceRSSData    
					                		},
					                		{
					                		'name': 'SPA_csx_ic_safe_vsz',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'emptyCircle',
					                		'data': SPA_csxVSZData    
					                		},
					                		{
					                		'name': 'SPA_csx_ic_safe_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_csxRSSData    
					                		},
					                		{
					                		'name': 'SPA_ECOM_vsz',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_ecomVSZData    
					                		},
					                		{
					                		'name': 'SPA_ECOM_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_ecomRSSData    
					                		},
					                		{
					                		'name': 'SPA_mozzo_sh_vsz',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_mozzoVSZData    
					                		},
					                		{
					                		'name': 'SPA_mozzo_sh_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_mozzoRSSData    
					                		},
					                		{
					                		'name': 'SPA_tomcat.sh_vsz',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_tomcatVSZData    
					                		},
					                		{
					                		'name': 'SPA_tomcat.sh_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_tomcatRSSData    
					                		},
					                		{
					                		'name': 'SPA_TLDlistener.exe_vsz',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPA_TLDlistenerVSZData    
					                		},
					                		{
					                		'name': 'SPA_TLDlistener.exe_rss',
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
					                		'name': 'SPB_peservice.exe_vsz',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_peserviceVSZData    
					                		},
					                		{
					                		'name': 'SPB_peservice.exe_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_peserviceRSSData    
					                		},
					                		{
					                		'name': 'SPB_csx_ic_safe_vsz',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'emptyCircle',
					                		'data': SPB_csxVSZData    
					                		},
					                		{
					                		'name': 'SPB_csx_ic_safe_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_csxRSSData    
					                		},
					                		{
					                		'name': 'SPB_ECOM_vsz',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_ecomVSZData    
					                		},
					                		{
					                		'name': 'SPB_ECOM_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_ecomRSSData    
					                		},
					                		{
					                		'name': 'SPB_mozzo_sh_vsz',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_mozzoVSZData    
					                		},
					                		{
					                		'name': 'SPB_mozzo_sh_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_mozzoRSSData    
					                		},
					                		{
					                		'name': 'SPB_tomcat.sh_vsz',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_tomcatVSZData    
					                		},
					                		{
					                		'name': 'SPB_tomcat.sh_rss',    
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_tomcatRSSData    
					                		},
					                		{
					                		'name': 'SPB_TLDlistener.exe_vsz',
					                		'type': 'line',
					                		'smooth':true,
					                		'symbol':'none',
					                		'data': SPB_TLDlistenerVSZData    
					                		},
					                		{
					                		'name': 'SPB_TLDlistener.exe_rss',
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

