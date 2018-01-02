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
	
})


function programSelect(){
	
	$('#programSelect').select2({
		placeholder: 'Select Release',
		ajax: {
			cache:true,
		    url: '/vtas/programMap/getAll',
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });
}


function arraySelect(program){
	
	$('#arrayName').select2({
		placeholder: 'Select Array',
		allowClear:true,
		ajax: {
			cache:true,
		    url: '/vtas/jobRuntime/getArrayByProgram/'+program+'/',
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });	
}

function buildSelect(program,arrayName){
	
	$('#buildVersion').select2({
		placeholder: 'Select Build',
		allowClear:true,
		ajax: {
		    url: '/vtas/jobRuntime/getVersionByProgramAndArray/'+program+'/'+arrayName,
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });	
}

function viewSingleAraay(){
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
			 var program = $('#programSelect').val();
			 var arrayName = $('#arrayName').val();
			 var buildVersion = $('#buildVersion').val();
			 if(buildVersion == ''||buildVersion==null){buildVersion = 'all';}
			 if(arrayName == ''||arrayName==null){arrayName = 'all';}
			 
			 
			 //不选择array，只选择program的图形
			 if(arrayName == 'all'){
				 var dataUrl = '/vtas/jobRuntime/getAllArraysRunHour/'+program;
				 var Chart=ec.init(document.getElementById("main"));  
				 Chart.showLoading({text: 'Loding...'});  
				 var xAxisData;
				 var legendData=['RunHour'];
				 var serieData;
				 var arrayVersionMap={};
				 $.ajax({  
				        url:dataUrl,  
				        dataType:"json",  
				        type:'post',  
				        success:function(result){
				        	if(result==null||result==''){
				        		layer.alert('No data', {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
						 		Chart.hideLoading();
						 	}else{
						 		xAxisData = result.arrayNameList;
						 		serieData = result.arrayMaxRunHourList;
						 		arrayVersionMap = result.arrayBuildMapToMaxRunHour;
						 		
						 	}
				        	
							var option = {
								tooltip : {
									trigger: 'axis',
									axisPointer : {            // 坐标轴指示器，坐标轴触发有效
										type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
									},
									show: true,
				                    formatter: function (params,ticket,callback) {
				                    	var arrayName= params[0].name;
				                        var relVal = params[0].name;
				                        var version = arrayVersionMap[arrayName];
				                        relVal += '<br/>' + params[0].seriesName + ' : ' + params[0].value+",&nbsp;&nbsp;&nbsp;&nbsp;version:"+version;
				                        return relVal;
				                      }
								},
								legend: {
									data:legendData
								},
								toolbox: {
									show : true,
									orient: 'vertical',
									x: 'right',
									y: 'center',
									feature : {
										mark : {show: true},
										dataView : {show: true, readOnly: false},
										magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
										restore : {show: true},
										saveAsImage : {show: true}
									}
								},
								calculable : true,
								xAxis : [
									{
										type : 'category',
										data : xAxisData
									}
								],
								yAxis : [
									{
										type : 'value'
									}
								],
								series : [
									{
										'name': 'RunHour',    
										'type': 'bar',
										'data': serieData,
										itemStyle:{
											normal:{
												color: function(params) {
						                            // build a color map as your need.
						                            var colorList = [
						                              '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
						                               '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
						                               '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
						                            ];
						                            return colorList[params.dataIndex]
						                        },
											}
										},
										 markPoint : {
							                data : [
							                    {type : 'max', name: 'Max'},
							                    {type : 'min', name: 'Min'}
							                ]
							            },
							            markLine : {
							                data : [
							                    {type : 'average', name: 'AVG'}
							                ]
							            }
			                		},
			                		 {
			                            name:'Expection',
			                            type:'line',
			                            'smooth':true,
				                		'symbol':'none',
			                            data:[672],
			                            markLine : {
			                                data : [
			                                    {type : 'average', name : 'Expection'}
			                                ]
			                            }
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
				    }); //end----选择array
				 
				 
				 
			 }else{
			 //选择单个array或者选择array+build的图形
			 var dataUrl = '/vtas/jobRuntime/getRunHourByProgramAndArray/'+program+'/'+arrayName+'/'+buildVersion+'/';
			 var Chart=ec.init(document.getElementById("main"));  
			 Chart.showLoading({text: 'Loding...'});  
			 var xAxisData;
			 var legendData=[];
			 var serieData;
			 var serie=[];
			 $.ajax({  
			        url:dataUrl,  
			        dataType:"json",  
			        type:'post',  
			        success:function(result){
			        	if(result==null||result==''){
			        		layer.alert('No data', {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
					 		Chart.hideLoading();
					 	}else{
					 		xAxisData = result.xAxis_data.split(',');    
					 		serieData = result.series_data;
					 		for(var i=0;i<serieData.length;i++){
					 			legendData.push('RunHour'+i);
					 			var item = {
										name:'RunHour'+i,
										type:'bar',
										data:serieData[i].split(',')
									};
					 			serie.push(item);
					 		 }
					 	}
			        	
						var option = {
							tooltip : {
								trigger: 'axis',
								axisPointer : {            // 坐标轴指示器，坐标轴触发有效
									type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
								}
							},
							legend: {
								data:legendData
							},
							toolbox: {
								show : true,
								orient: 'vertical',
								x: 'right',
								y: 'center',
								feature : {
									mark : {show: true},
									dataView : {show: true, readOnly: false},
									magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
									restore : {show: true},
									saveAsImage : {show: true}
								}
							},
							calculable : true,
							xAxis : [
								{
									type : 'category',
									data : xAxisData
								}
							],
							yAxis : [
								{
									type : 'value'
								}
							],
							series : serie
						};
						
				        Chart.hideLoading();  
				        Chart.setOption(option);    
			        },
			        error : function() {
						 //请求失败时执行该函数
						 alert("Failed to get ajax data!");
						 Chart.hideLoading();
					 }
			    }); //end----选择array
			 }
			 
		    })
}

