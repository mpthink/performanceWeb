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
	},{
	    field: 'condit',
	    title: 'Condition'
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
	    	var iox = row.iox.trim();
	    	var ipv4 = row.ipv4.trim();
	    	var os = row.os.trim();
	    	if(IOXStat == 0 && iox != 'NA'){
	    		return '<p class="text-red"><b>' + iox + '</b></p><button type="button"  class="btn btn-primary btn-sm" onclick=updateVersion("iox","'+os+'","'+ipv4+'")  >Update</button>';
	    	}else{
	    		return iox;
	    	}
        }
	},{
	    field: 'fio',
	    title: 'FIO',
	    formatter: function (value, row, index) {
	    	var FIOStat = row.FIOStat;
	    	var fio = row.fio.trim();
	    	var ipv4 = row.ipv4.trim();
	    	var os = row.os.trim();
	    	if(FIOStat == 0 && fio != 'NA'){
	    		return '<p class="text-red"><b>' + fio + '</b></p><button type="button"  class="btn btn-primary btn-sm" onclick=updateVersion("fio","'+os+'","'+ipv4+'")  >Update</button>';
	    	}else{
	    		return fio;
	    	}
        }
	},{
	    field: 'vjtree',
	    title: 'VJTREE',
	    formatter: function (value, row, index) {
	    	var VJTREEStat = row.VJTREEStat;
	    	var vjtree = row.vjtree.trim();
	    	var ipv4 = row.ipv4.trim();
	    	var os = row.os.trim();
	    	if(VJTREEStat == 0 && vjtree != 'NA'){
	    		return '<p class="text-red"><b>' + vjtree + '</b></p><button type="button"  class="btn btn-primary btn-sm" onclick=updateVersion("vjtree","'+os+'","'+ipv4+'")  >Update</button>';
	    	}else{
	    		return vjtree;
	    	}
        }
	},{
	    field: 'daq',
	    title: 'DAQ',
	    formatter: function (value, row, index) {
	    	var DAQStat = row.DAQStat;
	    	var daq = row.daq.trim();
	    	var ipv4 = row.ipv4.trim();
	    	var os = row.os.trim();
	    	if(DAQStat == 0 && daq != 'NA'){
	    		return '<p class="text-red"><b>' + daq + '</b></p><button type="button"  class="btn btn-primary btn-sm" onclick=updateVersion("daq","'+os+'","'+ipv4+'")  >Update</button>';
	    	}else{
	    		return daq;
	    	}
        }
	},{
	    field: 'perl',
	    title: 'Perl',
	    formatter: function (value, row, index) {
	    	var PERLStat = row.PERLStat;
	    	var perl = row.perl.trim();
	    	var ipv4 = row.ipv4.trim();
	    	var os = row.os.trim();
	    	if(PERLStat == 0 && perl != 'NA'){
	    		return '<p class="text-red"><b>' + perl + '</b></p><button type="button"  class="btn btn-primary btn-sm" onclick=updateVersion("perl","'+os+'","'+ipv4+'")  >Update</button>';
	    	}else{
	    		return perl;
	    	}
        }
	},{
	    field: 'xmlrpc',
	    title: 'XMLRPC',
	    formatter: function (value, row, index) {
	    	var XMLRPCStat = row.XMLRPCStat;
	    	var xmlrpc = row.xmlrpc.trim();
	    	var ipv4 = row.ipv4.trim();
	    	var os = row.os.trim();
	    	if(XMLRPCStat == 0 && xmlrpc != 'NA'){
	    		return '<p class="text-red"><b>' + xmlrpc + '</b></p><button type="button"  class="btn btn-primary btn-sm" onclick=updateVersion("xmlrpc","'+os+'","'+ipv4+'")  >Update</button>';
	    	}else{
	    		return xmlrpc;
	    	}
        }
	},{
	    field: 'pollDatetime',
	    title: 'Poll time'
	}];


function updateVersion(type,os,ip){
	var command = '';
	var content = '';
	var url = "/vtas/hostInformation/executeSSHcommand";
	if(type == 'iox'){
		if(os == 'windows'){
			command = '/usr/bin/python /space/vtas/IOX-Win.py '+ip;
		}else{
			command = '/usr/bin/python /space/vtas/IOX-Linux.py '+ip;
		}
	}else if(type == 'fio'){
		if(os == 'windows'){
			command = '/usr/bin/python /space/vtas/fio-Win.py '+ip;
		}else{
			command = '/usr/bin/python /space/vtas/fio-Linux.py '+ip;
		}
	}else if(type == 'vjtree'){
		if(os == 'windows'){
			command = '/usr/bin/python /space/vtas/vjtree-Win.py '+ip;
		}else{
			command = '/usr/bin/python /space/vtas/vjtree-Linux.py '+ip;
		}
	}else if(type == 'daq'){
		command = '/usr/bin/python /space/vtas/Daq-Linux.py '+ip;
	}else if(type == 'perl'){
		if(os == 'windows'){
			command = '/usr/bin/python /space/vtas/perl-Win.py '+ip;
		}else{
			command = '/usr/bin/python /space/vtas/perl-Linux.py '+ip;
		}
	}else if(type == 'xmlrpc'){
		if(os == 'windows'){
			command = '/usr/bin/python /space/vtas/rpc-Win.py '+ip;
		}else{
			command = '/usr/bin/python /space/vtas/rpc-Linux.py '+ip;
		}
	}
	
	if(os == 'windows'){
		content = 'UserName: <input type="text" id="hostUserName" name="hostUserName" value="Administrator"/><br>Password: <input type="text" id="hostPassword" name="hostPassword" value="Password123!"/><br>';
	}else{
		content = 'UserName: <input type="text" id="hostUserName" name="hostUserName" value="root"/><br>Password: <input type="text" id="hostPassword" name="hostPassword" value="Password123!"/><br>'
	}
	
	layer.open({
		  title: 'Update Version',
		  skin: 'layui-layer-molv',
		  content: content,
		  yes: function(index, layero){
			  		  	var username= $(layero.find('#hostUserName')).val().trim();
			  		  	var password= $(layero.find('#hostPassword')).val().trim();
					  	command = command +' '+username+' '+password;
					  	var param = { "command" : command };  
						$.ajax({  
					            type : "post",  
					            url : url,  
					            data : param,  
					            datatype : "json",  
					            success : function(data) {  
					                if (data.meta.success){
					                	layer.alert('Execute updating successfully', {icon: 1,title:'Info',closeBtn: 0,skin: 'layui-layer-molv'});
										$('#dataTable').bootstrapTable('refresh',{url:dataUrl}); 
					                }else{
					                	layer.alert(data.meta.message, {icon: 0,title:'Error',closeBtn: 0,skin: 'layui-layer-molv'});
					                }
					            }
					        });
						layer.close(index);
		  			}
		}); 
}


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
