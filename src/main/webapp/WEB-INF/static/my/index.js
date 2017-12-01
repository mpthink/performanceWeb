//init functions
$(function(){
	upTimeClientPagination();
	arrayUptimeChart();
	initCPUMemoryIOData();
	arrayCPUChart();
	arrayMemoryChart();
	arrayIOChart();
})



//alarm table JS
var dataUrl = '/vtas/arrayInfo/getArrayInfoWithUptime';
var dataColumns = [
	{
	    field: 'programName',
	    title: 'Project',
	    sortable: true
	},{
	    field: 'arrayName',
	    title: 'Rig'
	},{
	    field: 'model',
	    title: 'Model'
	},{
	    field: 'tfa',
	    title: 'TFA'
	},{
	    field: 'smallVersion',
	    title: 'Current Bundle',
	    sortable: true,
	    formatter: function (value, row, index) {
            return row.smallVersion+' - '+row.versionTime;
        }
	},{
	    field: 'upTime',
	    title: 'Up Time(Hrs)',
	    formatter: function (value) {
	    	var day= Math.round(value/24);

            return value+' ('+day+')days';
        }
	},{
	    field: 'serviceTime',
	    title: 'Service Time(Hrs)',
	    formatter: function (value) {
	    	var day=Math.round(value/24);
            return value+' ('+day+')days';
        }
	},{
	    field: 'status',
	    title: 'Status',
	    formatter: function (value) {
			    	return "";
        },
	    cellStyle:function(value){
		            if(value=='0'){
		                return { classes: 'bg-green' };
		            }else if(value=='1'){
		                return { classes: 'bg-yellow' };
		            }else{
		            	return { classes: 'bg-red' };
		            }            
		        }
	},{
	    field: 'comments',
	    title: 'Comments'
	}];

function upTimeClientPagination() {
    $('#upTimeTable').bootstrapTable({
        url: dataUrl,
        method: 'get',
        toolbar: '#toolbar',
        striped: true,
        cache: true,
        pagination: false,
        sortName:"programName",
        sortable: true, 
        sortOrder: "asc",
        sidePagination: "client",
        pageNumber:1,
        pageSize: 20,
        pageList: [10, 25, 50, 100],
        strictSearch: false,
        clickToSelect: true,
        cardView: false,
        detailView: false,
        showRefresh:false,
        search:false,
        searchAlign:'right',
        columns: dataColumns
    });
}


var arrayUptimeUrl = "/vtas/arraymonitor/getArrayUptime";
function arrayUptimeChart(){
	require.config({
	    paths: {
	        echarts: '/plugins/echart/dist'
	    }
	});
	require(
		    [
		        'echarts',
		        'echarts/chart/bar'
		        ],
			function (ec) {
			 var dataUrl = arrayUptimeUrl;
			 var Chart=ec.init(document.getElementById("arrayUptime"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var xAxisData;
			 var SPA_Uptime=[];
			 var SPB_Uptime=[];
			 $.ajax({  
			        url:dataUrl,  
			        dataType:"json",  
			        type:'post',  
			        success:function(result){
			        	if(result==null||result==''){
			        		layer.alert('No data found', {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
					 		Chart.hideLoading();
					 	}else{
					 		xAxisData = result.arrayNameList;
							SPA_Uptime = result.spaUptimeList;
							SPB_Uptime = result.spbUptimeList;
					 	}
			        	 var option = {
			        			 title : {
			        			        text: 'Uptime (hours)',
			        			        textStyle:{
			        			            fontSize: 10,
			        			            fontWeight: 'bolder',
			        			            color: '#333'
			        			        }
			        			 },
			        			 noDataLoadingOption: {
			                         	text: 'No data found',
			 							textStyle: {fontSize:16,fontWeight:'bold'},
			 							effect:'bubble'
			 					 	},
					                tooltip: {
					                	trigger: 'axis'
					                },
					                grid:{
					                    x:40,
					                    y:35,
					                    x2:5,
					                    y2:65
					                },
					                legend: {
					                	data: ['SPA','SPB']
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
					                xAxis: [    
					                    {    
					                        type: 'category',    
					                        data: xAxisData,
					                        axisLabel:{  
					                            interval:0,
					                            rotate:60 
					                       } 
					                    }    
					                ],    
					                yAxis: [    
					                    {    
					                        type: 'value'
					                    }    
					                ],    
					                series: [
											{
												'name': 'SPA',    
												'type': 'bar',
												'data': SPA_Uptime
					                		},
					                		{
												'name': 'SPB',    
												'type': 'bar',
												'data': SPB_Uptime
					                		}
					                ]
					            };
						
				        Chart.hideLoading();  
				        Chart.setOption(option);
				        window.addEventListener("resize",function(){
				        	Chart.resize();
				        });
			        },
			        error : function() {
						 //请求失败时执行该函数
						 alert("Failed to get ajax data!");
						 Chart.hideLoading();
					 }
			    }); 
		    })
}



var arrayCPUMemoryIOUrl = "/vtas/arraymonitor/getArrayCPUMemoryIO";
var xAxisData;
var SPA_CPU;
var SPB_CPU;
var SPA_MEM;
var SPB_MEM;
var SPA_IO_READ;
var SPA_IO_WRITE;
var SPB_IO_READ;
var SPB_IO_WRITE;

function initCPUMemoryIOData(){
	
	$.ajax({  
        url:arrayCPUMemoryIOUrl,  
        dataType:"json",  
        type:'post',
        async:false,
        success:function(result){
        	if(result==null||result==''){
        		layer.alert('No data found', {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
		 	}else{
		 		xAxisData = result.arrayNameList;
		 		SPA_CPU = result.spaCPUList;
		 		SPB_CPU = result.spbCPUList;
		 		SPA_MEM = result.spaMemUsedList;
		 		SPB_MEM = result.spbMemUsedList;
		 		SPA_IO_READ = result.spaIOReadList;
		 		SPA_IO_WRITE = result.spaIOWriteList;
		 		SPB_IO_READ = result.spbIOReadList;
		 		SPB_IO_WRITE = result.spbIOWriteList;
		 	}
        },
        error : function() {
			 alert("Failed to get ajax data!");
		 }
	});
}

function arrayCPUChart(){
	require.config({
	    paths: {
	        echarts: '/plugins/echart/dist'
	    }
	});
	require(
		    [
		        'echarts',
		        'echarts/chart/bar'
		        ],
			function (ec) {
			 var Chart=ec.init(document.getElementById("arrayCPU"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var option = {
        			 title : {
        			        text: 'CPU Utilization (%)',
        			        textStyle:{
        			            fontSize: 10,
        			            fontWeight: 'bolder',
        			            color: '#333'
        			        }
        			 },
        			 noDataLoadingOption: {
                         	text: 'No data found',
 							textStyle: {fontSize:16,fontWeight:'bold'},
 							effect:'bubble'
 					 	},
		                tooltip: {
		                	trigger: 'axis'
		                },
		                grid:{
		                    x:60,
		                    y:35,
		                    x2:5,
		                    y2:65
		                },
		                legend: {
		                	data: ['SPABusy','SPBBusy']
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
		                xAxis: [    
		                    {    
		                        type: 'category',    
		                        data: xAxisData,
		                        axisLabel:{  
		                            interval:0,
		                            rotate:60 
		                       } 
		                    }    
		                ],    
		                yAxis: [    
		                    {    
		                        type: 'value'
		                    }    
		                ],    
		                series: [
								{
									'name': 'SPABusy',    
									'type': 'bar',
									'data': SPA_CPU
		                		},
		                		{
									'name': 'SPBBusy',    
									'type': 'bar',
									'data': SPB_CPU
		                		}
		                ]
		            };
			
			        Chart.hideLoading();  
			        Chart.setOption(option);
			        window.addEventListener("resize",function(){
			        	Chart.resize();
			        });
		    })
}

function arrayMemoryChart(){
	require.config({
	    paths: {
	        echarts: '/plugins/echart/dist'
	    }
	});
	require(
		    [
		        'echarts',
		        'echarts/chart/bar'
		        ],
			function (ec) {
			 var Chart=ec.init(document.getElementById("arrayMemory"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var option = {
        			 title : {
        			        text: 'Memory Utilization (MB)',
        			        textStyle:{
        			            fontSize: 10,
        			            fontWeight: 'bolder',
        			            color: '#333'
        			        }
        			 },
        			 noDataLoadingOption: {
                         	text: 'No data found',
 							textStyle: {fontSize:16,fontWeight:'bold'},
 							effect:'bubble'
 					 	},
		                tooltip: {
		                	trigger: 'axis'
		                },
		                grid:{
		                    x:40,
		                    y:35,
		                    x2:5,
		                    y2:65
		                },
		                legend: {
		                	data: ['SPA Used','SPB Used']
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
		                xAxis: [    
		                    {    
		                        type: 'category',    
		                        data: xAxisData,
		                        axisLabel:{  
		                            interval:0,
		                            rotate:60 
		                       } 
		                    }    
		                ],    
		                yAxis: [    
		                    {    
		                        type: 'value'
		                    }    
		                ],    
		                series: [
								{
									'name': 'SPA Used',    
									'type': 'bar',
									'data': SPA_MEM
		                		},
		                		{
									'name': 'SPB Used',    
									'type': 'bar',
									'data': SPB_MEM
		                		}
		                ]
		            };
			
			        Chart.hideLoading();  
			        Chart.setOption(option);
			        window.addEventListener("resize",function(){
			        	Chart.resize();
			        });
		    })
}

function arrayIOChart(){
	require.config({
	    paths: {
	        echarts: '/plugins/echart/dist'
	    }
	});
	require(
		    [
		        'echarts',
		        'echarts/chart/bar'
		        ],
			function (ec) {
			 var Chart=ec.init(document.getElementById("arrayIO"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var option = {
        			 title : {
        			        text: 'IO Throughput (KB/S)',
        			        textStyle:{
        			            fontSize: 10,
        			            fontWeight: 'bolder',
        			            color: '#333'
        			        }
        			 },
        			 noDataLoadingOption: {
                         	text: 'No data found',
 							textStyle: {fontSize:16,fontWeight:'bold'},
 							effect:'bubble'
 					 	},
		                tooltip: {
		                	trigger: 'axis'
		                },
		                grid:{
		                    x:60,
		                    y:35,
		                    x2:5,
		                    y2:65
		                },
		                legend: {
		                	data: ['SPA Read','SPA Write','SPB Read','SPB Write']
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
		                xAxis: [    
		                    {    
		                        type: 'category',    
		                        data: xAxisData,
		                        axisLabel:{  
		                            interval:0,
		                            rotate:60 
		                       } 
		                    }    
		                ],    
		                yAxis: [    
		                    {    
		                        type: 'value'
		                    }    
		                ],    
		                series: [
								{
									'name': 'SPA Read',    
									'type': 'bar',
									'data': SPA_IO_READ
		                		},
		                		{
									'name': 'SPA Write',    
									'type': 'bar',
									'data': SPA_IO_WRITE
		                		},
		                		{
									'name': 'SPB Read',    
									'type': 'bar',
									'data': SPB_IO_READ
		                		},
		                		{
									'name': 'SPB Write',    
									'type': 'bar',
									'data': SPB_IO_WRITE
		                		}
		                ]
		            };
			
			        Chart.hideLoading();  
			        Chart.setOption(option);
			        window.addEventListener("resize",function(){
			        	Chart.resize();
			        });
		    })
}