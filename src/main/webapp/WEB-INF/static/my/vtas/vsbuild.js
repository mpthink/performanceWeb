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
		    url: '/vtas/niceNameMap/getNiceNameByProgramAndArray/'+program+'/'+arrayName,
		    processResults: function (data) {
		      return {
		        results: data
		      };
		    }
		  }
    });	
}



