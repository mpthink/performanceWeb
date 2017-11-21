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
	    title: 'Start Time'
	},{
	    field: 'END_TIME',
	    title: 'End Time'
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

//init functions
$(function(){
	clientPagination();
})
