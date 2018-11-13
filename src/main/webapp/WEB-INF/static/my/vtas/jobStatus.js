//array status list
var arrayStatusListUrl = "/vtas/jobRuntime/getArrayStatusList";
var dataColumns = [{
	    field: 'programName',
	    title: 'Program Name'
	},{
	    field: 'arrayName',
	    title: 'Array Name'
	},{
	    field: 'build',
	    title: 'Build'
	},{
	    field: 'owner',
	    title: 'Owner'
	},{
	    field: 'jobs',
	    title: 'Jenkins Job Running',
        formatter: function (value, row, index) {
	        var jobTemp = row.jobs;
            var jobs = jobTemp.substring(0,jobTemp.length-1).split(";");
            if(jobs.length > 1){
                var result = "";
                for(var i=0;i<jobs.length;i++){
                    var jobDetail = jobs[i].split(",")
                    result = result + '<a href="' + "" + jobDetail[1] + "" + '" target="_blank">'+"" + jobDetail[0] + ""+'</a>' +"\n";
                }
                return result;
            }else if(jobs.length == 1){
                var jobDetail = jobs[0].split(",");
                if(jobDetail[0].indexOf('No job')>=0){
                    return "No jobs in the time slot!";
                }else{
                    return '<a href="' + "" + jobDetail[1] + "" + '" target="_blank">'+"" + jobDetail[0] +""+'</a>';
                }
            }else{
                return "";
            }
        }
	},{
	    field: 'jobStatus',
	    title: 'Job Status',
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
	    field: 'runningHours',
	    title: 'Running Time(hr)'
	},{
	    field: 'idleHours',
	    title: 'Idle Time(hr)'
	},{
	    field: 'cpuRatio',
	    title: 'Live CPU Load'
	}];

function arrayStatusPagination() {
    $('#dataTable').bootstrapTable({
        url: arrayStatusListUrl,
        method: 'get',     
        toolbar: '#toolbar',        //一个jQuery 选择器，指明自定义的toolbar 例如: #toolbar, .toolbar.
        striped: true,           //是否显示行间隔色
        cache: true,            //设置为 false 禁用 AJAX 数据缓存,default is true
        pagination: false,          //设置为 true 会在表格底部显示分页条,default is false
        sortOrder: "asc",          //定义排序方式 'asc' 或者 'desc',default is "asc"
        sidePagination: "client", 
        pageNumber:1,          
        pageSize: 30,
        pageList: [20, 50, 100],
        strictSearch: false,
        clickToSelect: true,
        showRefresh:false,           //是否显示 刷新按钮. default: false,
        search:false,                //是否启用搜索框,default: false
        showColumns:false,           //是否显示 内容列下拉框,  default: false
        columns: dataColumns,   //列配置项,详情请查看 列参数 表格.
        rowStyle:function rowStyle(row, index) {
      	  return {
      		    classes: 'text-nowrap another-class',
      		    css: {"font-size": "12px"}
      		  };
      		}
    });
}

function updateTable(){
    $("#dataTable").bootstrapTable('destroy');
    var timerange = $("#reservationtime").val();
    if(timerange != ''){
        var timeArr = timerange.split(" - ");
        var beginTime = timeArr[0];
        var endTime = timeArr[1];
        arrayStatusListUrl = "/vtas/jobRuntime/getArrayStatusList?beginTime="+beginTime+"&endTime="+endTime;
    }
    arrayStatusPagination();
}

function clockFunction(){
    $('#reservationtime').daterangepicker(
        {
            locale: {format: 'YYYY-MM-DD HH:mm:ss' },
            ranges   : {
                'Last 7 Days' : [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month'  : [moment().startOf('month'), moment().endOf('month')],
                'Last Month'  : [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                'Last 60 Days': [moment().subtract(59, 'days'), moment()]
            },
            startDate: moment().subtract(30, 'days'),
            endDate  : moment()
        },
        function (start, end) {
            $('#reservationtime span').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
        }
    );
}

$('#reservationtime').change(function(){
    updateTable();
    viewRunningUsageRatio();
});


function viewRunningUsageRatio(){
    require.config({
        paths: {
            echarts: '/plugins/echart/dist'
        }
    });
    require(
        [
            'echarts',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            var Chart=ec.init(document.getElementById("main"));
            Chart.showLoading({text: 'Loding...'});
            var timerange = $("#reservationtime").val();
            var dataUrl = "/vtas/jobRuntime/getArrayrrRunningRatioList";
            if(timerange != ''){
                var timeArr = timerange.split(" - ");
                var beginTime = timeArr[0];
                var endTime = timeArr[1];
                dataUrl = "/vtas/jobRuntime/getArrayrrRunningRatioList?beginTime="+beginTime+"&endTime="+endTime;
            }

            var arrayName=[];
            var runningHours=[];
            var idleHours=[];
            $.ajax({
                url:dataUrl,
                dataType:"json",
                type:'post',
                success:function(result){
                    if(result==null||result==''){
                        layer.alert('No data found', {icon: 0,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
                        Chart.hideLoading();
                    }else{
                        for(var i=0;i<result.length;i++) {
                            arrayName.push(result[i]['arrayName']);
                            runningHours.push(result[i]['runningHours']);
                            idleHours.push(result[i]['idleHours']);
                        }
                    }

                    var option = {
                        title : {
                            text: 'Array Occupation Ratio',
                            textStyle:{
                                fontSize: 15,
                                fontWeight: 'bolder',
                                color: '#333'
                            }
                        },
                        noDataLoadingOption: {
                            text: 'No data in this timeslot',
                            textStyle: {fontSize:16,fontWeight:'bold'},
                            effect:'bubble'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        legend: {
                            x:"center",
                            textStyle:{color:'auto'},
                            borderColor:'#333',
                            borderWidth:1,
                            data: ['Running Time','Idle Time']
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {show: true, type: ['line', 'bar']},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        xAxis: [
                            {
                                type: 'category',
                                data: arrayName,
                                axisLabel:{
                                    interval:0,
                                    rotate:55,
                                    margin:2
                                }
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                axisLabel : {
                                    formatter: '{value} hrs'
                                }
                            }
                        ],
                        series: [
                            {
                                'name': 'Running Time',
                                'type': 'bar',
                                'stack':'group1',
                                barWidth: 30,
                                'data': runningHours,
                                itemStyle: { normal: { color: '#228B22' } }
                            },
                            {
                                'name': 'Idle Time',
                                'type': 'bar',
                                'stack':'group1',
                                barWidth: 30,
                                'data': idleHours,
                                itemStyle: { normal: { color: '#d33724' } }

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

//init functions
$(function(){
    clockFunction();
    arrayStatusPagination();
    viewRunningUsageRatio();
})
