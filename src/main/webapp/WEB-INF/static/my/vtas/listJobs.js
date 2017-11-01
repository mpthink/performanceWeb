//alarm table JS
var dataUrl = '/vtas/jobRuntime/getall';
var dataColumns = [{  
	    field: 'Number',
	    title: 'No',  
	    formatter: function (value, row, index) {  
	    return index+1;  
	    }  
	},{
	    field: 'PROGRAM',
	    title: 'Program Name'
	},{
	    field: 'ARRAY_NAME',
	    title: 'Array Name'
	},{
	    field: 'JOB_URL',
	    title: 'Jenkins Job',
	    formatter: function (value, row, index) {
            return formatUrl(row.JOB_URL);
        }
	},{
	    field: 'VERSION',
	    title: 'Version'
	},{
	    field: 'STATUS',
	    title: 'Status'
	},{
	    field: 'START_TIME',
	    title: 'Start Time',
	    formatter: function (value, row, index) {
	            return timeStamp2String(row.START_TIME);
	        }
	},{
	    field: 'END_TIME',
	    title: 'End Time',
	    formatter: function (value, row, index) {
            return timeStamp2String(row.END_TIME);
        }
	},{
	    field: 'RUN_HOURS',
	    title: 'Running Time(h)'
	},{
	    title: 'Expected Time(h)',
	    formatter: function (value, row, index) {
            return 168;
        }
	}];

function formatUrl(jobUrl){
	var res = jobUrl.split("/");
	var jobName = res[res.length-3];
	return '<a href="' + "" + jobUrl + "" + '">'+"" + jobName + ""+'</a>';
}

function timeStamp2String(time){
    var datetime = new Date();
     datetime.setTime(time);
     var year = datetime.getFullYear();
     var month = datetime.getMonth() + 1;
     var date = datetime.getDate();
     var hour = datetime.getHours();
     if(hour<10){
    	 hour = '0' + hour;
     }
     var minute = datetime.getMinutes();
     if(minute<10){
    	 minute = '0' + minute;
     }
     var second = datetime.getSeconds();
     if(second<10){
    	 second = '0' + second;
     }
     return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
};

//init functions
$(function(){
	clientPagination();
})
