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
		viewIOPS();
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
		viewIOPS();
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
		viewIOPS();
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
		dataUrl = "/vtas/arraymonitor/getIOPSList/"+tableName+"/"+arrayName;
	}else{
		timeArr = timerange.split(" - ");	
		beginTime = timeArr[0];
		endTime = timeArr[1];
		dataUrl = "/vtas/arraymonitor/getIOPSList/"+tableName+"/"+arrayName+"?beginTime="+beginTime+"&endTime="+endTime;
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
			 var dataUrl = generateUrl();
			 var Chart=ec.init(document.getElementById("main"));  
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

