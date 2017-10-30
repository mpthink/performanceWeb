$(function() {
	$('input[name="reservationtime"]').daterangepicker({
		timePicker: true,
		timePickerIncrement: 30,
		timePicker24Hour:true,
		locale: {
			format: 'YYYY-MM-DD HH:mm:ss'
		}
	});
});


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

function generateUrl(id){
	var timerange = $("#reservationtime").val();
	var timeArr = timerange.split(" - ");	
	var begin = timeArr[0];
	var end = timeArr[1];
	var aggType = $("#aggType").val();
	var dataUrl = "";
	if(aggType=="raw"){
		dataUrl = "/pet/cpuRaw/getData/"+id+"/"+begin+"/"+end;	
	}else if(aggType=="hour"){
		dataUrl = "/pet/cpuHour/getData/"+id+"/"+begin+"/"+end;
	}else if(aggType=="day"){
		dataUrl = "/pet/cpuDay/getData/"+id+"/"+begin+"/"+end;
	}
	return dataUrl;
}



//展示CPU利用率
function viewCPU(id){
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
			 var dataUrl = generateUrl(id);
			 var Chart=ec.init(document.getElementById("main"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var times=[];  
			 var values=[];  
			 $.ajax({  
			        url:dataUrl,  
			        dataType:"json",  
			        type:'post',  
			        success:function(result){
			        	if(result==''){
			        		layer.alert('暂无数据', {icon: 0,title:'信息',closeBtn: 0,skin: 'layui-layer-molv'});
					 		Chart.hideLoading();
					 	}else{
					 		for(var i=0;i<result.length;i++){       
					 			times.push(formatDateTime(result[i]['gmtGenerate']));    //挨个取出日期并填入日期数组
					 			values.push(result[i]['cpuUtilization']);    //挨个取出利用率并填入利用率数组
							 }
					 	}
			        	
				        var option = {    
				                tooltip: {
				                	trigger: 'axis', //add
				                    show: true    
				                },    
				                legend: {    
				                    data: ['CPU Ratio']    
				                },
				                toolbox: {
				    				show : true,
				    				feature : {
				    				    mark : {show: true},
				    				    dataZoom : {show: true},
				    				    dataView : {show: true},
				    				    magicType : {show: true, type: ['line']},
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
				                series: function(){
				                	var item = {
				                        'name': 'CPU Ratio',    
				                        'type': 'line',    
				                        'data': values    
				                    	}
				                	var serie=[];
				                	serie.push(item);
				                	return serie;
				                }()
				            };  
				        Chart.hideLoading();  
				        Chart.setOption(option);    
			        },
			        error : function() {
						 //请求失败时执行该函数
						 alert("ajax获取数据失败");
						 Chart.hideLoading();
					 }
			    }); 
		    })
}
