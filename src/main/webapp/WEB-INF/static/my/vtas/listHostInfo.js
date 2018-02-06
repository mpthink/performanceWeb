//alarm table JS
var officalVersionsDataUrl = '/vtas/hostInformation/getOfficalSWVersions';
var hostInfoDataUrl = '/vtas/hostInformation/getHostInfos';

var officalVersionsDataColumns = [
	{
	    field: 'name',
	    title: 'Name'
	},{
	    field: 'version',
	    title: 'Version'
	}];


function officalVersionsClientPagination() {
    $('#versionTable').bootstrapTable({
        url: officalVersionsDataUrl,
        method: 'get',
        toolbar: '#toolbar',
        striped: true,
        cache: true,
        pagination: false,
        sortable: true, 
        sortOrder: "asc",
        sidePagination: "client",
        pageNumber:1,
        pageSize: 5,
        pageList: [10, 20],
        strictSearch: false,
        clickToSelect: true,
        cardView: false,
        detailView: false,
        showRefresh:false,
        search:false,
        columns: officalVersionsDataColumns,
        rowStyle:function rowStyle(row, index) {
        	  return {
        		    classes: 'text-nowrap another-class',
        		    css: {"font-size": "13px"}
        		  };
        		}      
    });
}


var hostInfoDataColumns = [
	{
	    field: 'arrayName',
	    title: 'Array'
	},{
	    field: 'ipv4',
	    title: 'IPV4'
	},{
	    field: 'os',
	    title: 'OS'
	},{
	    field: 'cpu',
	    title: 'CPU'
	},{
	    field: 'mem',
	    title: 'Memory(MB)'
	},{
	    field: 'iox',
	    title: 'IOX',
	    formatter: function (value, row, index) {
	    	var IOXStat = row.IOXStat;
	    	var iox = row.iox;
	    	if(IOXStat == 0){
	    		return '<p class="text-red"><b>' + iox + '</b></p>';
	    	}else{
	    		return iox;
	    	}
        }
	},{
	    field: 'fio',
	    title: 'FIO',
	    formatter: function (value, row, index) {
	    	var FIOStat = row.FIOStat;
	    	var fio = row.fio;
	    	if(FIOStat == 0){
	    		return '<p class="text-red"><b>' + fio + '</b></p>';
	    	}else{
	    		return fio;
	    	}
        }
	},{
	    field: 'vjtree',
	    title: 'VJTREE',
	    formatter: function (value, row, index) {
	    	var VJTREEStat = row.VJTREEStat;
	    	var vjtree = row.vjtree;
	    	if(VJTREEStat == 0){
	    		return '<p class="text-red"><b>' + vjtree + '</b></p>';
	    	}else{
	    		return vjtree;
	    	}
        }
	},{
	    field: 'daq',
	    title: 'DAQ',
	    formatter: function (value, row, index) {
	    	var DAQStat = row.DAQStat;
	    	var daq = row.daq;
	    	if(DAQStat == 0){
	    		return '<p class="text-red"><b>' + daq + '</b></p>';
	    	}else{
	    		return daq;
	    	}
        }
	},{
	    field: 'perl',
	    title: 'Perl',
	    formatter: function (value, row, index) {
	    	var PERLStat = row.PERLStat;
	    	var perl = row.perl;
	    	if(PERLStat == 0){
	    		return '<p class="text-red"><b>' + perl + '</b></p>';
	    	}else{
	    		return perl;
	    	}
        }
	},{
	    field: 'xmlrpc',
	    title: 'XMLRPC',
	    formatter: function (value, row, index) {
	    	var XMLRPCStat = row.XMLRPCStat;
	    	var xmlrpc = row.xmlrpc;
	    	if(XMLRPCStat == 0){
	    		return '<p class="text-red"><b>' + xmlrpc + '</b></p>';
	    	}else{
	    		return xmlrpc;
	    	}
        }
	},{
	    field: 'pollDatetime',
	    title: 'Poll time'
	}];


function hostInfoClientPagination() {
    $('#hostInfoTable').bootstrapTable({
        url: hostInfoDataUrl,
        method: 'get',
        toolbar: '#toolbar',
        striped: true,
        cache: true,
        pagination: false,
        sortable: true, 
        sortOrder: "asc",
        sidePagination: "client",
        pageNumber:1,
        pageSize: 20,
        pageList: [50, 100],
        strictSearch: false,
        clickToSelect: true,
        cardView: false,
        detailView: false,
        showRefresh:false,
        search:true,
        showColumns:false, 
        columns: hostInfoDataColumns,
        rowStyle:function rowStyle(row, index) {
        	  return {
        		    classes: 'text-nowrap another-class',
        		    css: {"font-size": "13px"}
        		  };
        		}      
    });
}

//init functions
$(function(){
	officalVersionsClientPagination();
	hostInfoClientPagination();
})
