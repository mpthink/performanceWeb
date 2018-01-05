//alarm table JS
var dataUrl = '/vtas/arrayConfiguration/getLatestArrayConfiguration';
var dataColumns = [
	{
	    field: 'project',
	    title: 'Project'
	},{
	    field: 'ARRAY_NAME',
	    title: 'Array'
	},{
	    field: 'model',
	    title: 'Model'
	},{
	    field: 'lun_count',
	    title: 'LUN Count'
	},{
	    field: 'fs_count',
	    title: 'FS Count'
	},{
	    field: 'fc_host_count',
	    title: 'FC Host Count'
	},{
	    field: 'iscsi_host_count',
	    title: 'iSCSI Host Count'
	},{
	    field: 'cifs_nfs_host_count',
	    title: 'CIFS/NFS Host Count'
	},{
	    field: 'lun_thin_count',
	    title: 'LUN thin Count'
	},{
	    field: 'fs_thin_count',
	    title: 'FS thin Count'
	},{
	    field: 'lun_snap_count',
	    title: 'LUN Snap Ccount'
	},{
	    field: 'fs_snap_count',
	    title: 'FS Snap Count'
	},{
	    field: 'lun_ilc_count',
	    title: 'LUN ILC Count'
	},{
	    field: 'fs_ilc_count',
	    title: 'FS ILC Count'
	},{
	    field: 'total_drive_count',
	    title: 'Total Drive Count'
	},{
	    field: 'size_tb',
	    title: 'Size(TB)'
	},{
	    field: 'drive_types',
	    title: 'Drive Types(QTY)',
	    formatter: function (value, row, index) {
	    	var dirveTypes = row.drive_types;
            return dirveTypes.replace(/;/g,'<br/>');
        }
	},{
	    field: 'ilc_ilpd_enable',
	    title: 'ILC/ILPD Enable'
	},{
	    field: 'fastcache_enable',
	    title: 'Fast Cache Enable'
	},{
	    field: 'mapped_raid_enable',
	    title: 'Mapped Raid Enable'
	},{
	    field: 'encryption',
	    title: 'Encryption'
	},{
	    field: 'fsn_enable',
	    title: 'FSN Enable'
	},{
	    field: 'pool1_size_tb',
	    title: 'Pool#1 Size',
	    formatter: function (value, row, index) {
	    	var poolSize = row.pool1_size_tb;
	    	if(poolSize == -1){
	    		return 'NA';
	    	}else{
	            return poolSize;
	    	}
        }
	},{
	    field: 'pool1_drive_types',
	    title: 'Pool#1 Drive Type',
	    formatter: function (value, row, index) {
	    	var dirveTypes = row.pool1_drive_types;
            return dirveTypes.replace(/;/g,'<br/>');
        }
	},{
	    field: 'pool2_size_tb',
	    title: 'Pool#2 Size',
	    formatter: function (value, row, index) {
	    	var poolSize = row.pool2_size_tb;
	    	if(poolSize == -1){
	    		return 'NA';
	    	}else{
	            return poolSize;
	    	}
        }
	},{
	    field: 'pool2_drive_types',
	    title: 'Pool#2 Drive Type',
	    formatter: function (value, row, index) {
	    	var dirveTypes = row.pool2_drive_types;
            return dirveTypes.replace(/;/g,'<br/>');
        }
	},{
	    field: 'pool3_size_tb',
	    title: 'Pool#3 Size',
	    formatter: function (value, row, index) {
	    	var poolSize = row.pool3_size_tb;
	    	if(poolSize == -1){
	    		return 'NA';
	    	}else{
	            return poolSize;
	    	}
        }
	},{
	    field: 'pool3_drive_types',
	    title: 'Pool#3 Drive Type',
	    formatter: function (value, row, index) {
	    	var dirveTypes = row.pool3_drive_types;
            return dirveTypes.replace(/;/g,'<br/>');
        }
	},{
	    field: 'pool4_size_tb',
	    title: 'Pool#4 Size',
	    formatter: function (value, row, index) {
	    	var poolSize = row.pool4_size_tb;
	    	if(poolSize == -1){
	    		return 'NA';
	    	}else{
	            return poolSize;
	    	}
        }
	},{
	    field: 'pool4_drive_types',
	    title: 'Pool#4 Drive Type',
	    formatter: function (value, row, index) {
	    	var dirveTypes = row.pool4_drive_types;
            return dirveTypes.replace(/;/g,'<br/>');
        }
	},{
	    field: 'pool5_size_tb',
	    title: 'Pool#5 Size',
	    formatter: function (value, row, index) {
	    	var poolSize = row.pool5_size_tb;
	    	if(poolSize == -1){
	    		return 'NA';
	    	}else{
	            return poolSize;
	    	}
        }
	},{
	    field: 'pool5_drive_types',
	    title: 'Pool#5 Drive Type',
	    formatter: function (value, row, index) {
	    	var dirveTypes = row.pool5_drive_types;
            return dirveTypes.replace(/;/g,'<br/>');
        }
	}];


function programMapClientPagination() {
    $('#dataTable').bootstrapTable({
        url: dataUrl,
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
        search:false,
        columns: dataColumns,
        rowStyle:function rowStyle(row, index) {
        	  return {
        		    classes: 'text-nowrap another-class',
        		    css: {"font-size": "10px"}
        		  };
        		}
            
    });
}

function timeSelect(){
	
	$('#timeSelect').select2({
		ajax: {
			cache:true,
		    url: '/vtas/arrayConfiguration/getPollTime',
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });
}

//init functions
$(function(){
	timeSelect();
	programMapClientPagination();
	
	$('#configTimeline').datetimepicker(
		{format:'yyyy-mm-dd',
			minView: "month",
			autoclose: true,
			icons: {
			time: "fa fa-clock-o",
			date: "fa fa-calendar",
			up: "fa fa-arrow-up",
			down: "fa fa-arrow-down"
		}}).change(function(){
			var time = $("#configTimeline").val(); 
			var dataUrl = '/vtas/arrayConfiguration/getArrayConfigurationByTime/'+time;
			$('#dataTable').bootstrapTable('refresh',{url:dataUrl}); 
		});
	
	$('#timeSelect').change(function(){
		var timeLine = $('#timeSelect').val();
		var dataUrl = '/vtas/arrayConfiguration/getArrayConfigurationByTime/'+timeLine;
		$('#dataTable').bootstrapTable('refresh',{url:dataUrl}); 
	});
	
})
