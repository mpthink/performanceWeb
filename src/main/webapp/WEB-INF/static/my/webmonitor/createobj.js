
var idxOfCreateObj = parent.layer.getFrameIndex(window.name);

$("#mr_cancel").click(function() {
	parent.layer.close(idxOfCreateObj);
});
$("#plmn_cancel").click(function() {
	parent.layer.close(idxOfCreateObj);
});
$("#ne_cancel").click(function() {
	parent.layer.close(idxOfCreateObj);
});
$("#if_cancel").click(function() {
	parent.layer.close(idxOfCreateObj);
});

$('#form1').on('submit', function() {
	naSubmit("MR", "#form1");
	return false;
});
$('#form2').on('submit', function() {
	naSubmit("PLMN", "#form2");
	return false;
});
$('#form3').on('submit', function() {
	if ($("#ManagedObject").val() == 'invalid') {
		layer.alert("Please select an object");
		return false;
	}
	naSubmit("MO", "#form3");
	return false;
});
$('#form4').on('submit', function() {
	naSubmit("IF", "#form4");
	return false;
});

function naSubmit(type, formid) {
	$.ajax({
		url : "/webmonitor/nasda/doCreatMO/" + type,
		method : "POST",
		data :$.param({'labName':labName,'dn':dn}) + '&' +$(formid).serialize(),
		success : function(data) {
			if (data=='Ok') {
				layer.alert('添加成功', {
					icon : 1,
					title : '信息',
					closeBtn : 0,
					skin : 'layui-layer-molv'
				});
				window.parent.reloadTree();
				parent.layer.close(idxOfCreateObj);
			} else {
				layer.alert(data, {
					icon : 0,
					title : '信息',
					closeBtn : 0,
					skin : 'layui-layer-molv'
				});
			}
		},
		error : function() {
			layer.msg("Create Object failed!", {
				icon : 2,
				time : 3000,
				area : [ '250px', '100px' ]
			});
		}
	});
}


$("#IfObject").change(function(){
	var IfName = $("#IfObject").val();
	var html = '<div class="form-group"> <label for="instanceId">Instance</label> <input type="text" name="instance_id" placeholder="Please input instance " data-rule="instance_id:required;"> </div>';
	var curPropertyMap = InterfacePropertyMap[IfName];
	for ( var key in curPropertyMap) {	
		var node=curPropertyMap[key];
		if (node.type == "select") {
			html+='<div class="form-group"><label>'+node.title+'</label><select name="'+node.name+'" class="form-control select2" style="width: 40%;" required>';
			for (var optkey in node.option)
			{	
				html += '<option value="'+optkey+'">'+node.option[optkey]+'</option>';				
			}
			html +='</select></div>';
		} else if (curPropertyMap[key].type == "text") {
			if(node.required)
			{
				html +='<div class="form-group"><label for="'+node.name+'">'+node.title+'</label> <input type="text" name="'+node.name+'" data-rule="'+node.name+':required;"></div>';
			}
			else{
				html +='<div class="form-group"><label for="'+node.name+'">'+node.title+'</label> <input type="text" name="'+node.name+'"></div>';
			}
		}
	}
	$("#IfAttribute").html(html);
});
var dn, labName;


//init functions
$(function(){
	var html = '<option selected="selected" value="">Select Object</option>';
	for(var objclass in InterfacePropertyMap)
	{
		html += '<option value="'+objclass+'">'+objclass+'</option>';	
	}
	$("#IfObject").html(html);
	
	html = '<option selected="selected" value="">Select Object</option>';
	for(var key in parent.nasdaObjCMap)
	{
		html += '<option value="'+parent.nasdaObjCMap[key].OC_NAME+'">'+parent.nasdaObjCMap[key].OC_NAME+'</option>';	
	}
	$("#ManagedObject").html(html);
	
	labName = parent.$("#labSelect").val();
	var nodes = parent.zTree.getSelectedNodes();
	if(nodes==null||nodes[0]==null)
	{
		dn="empty";
	}
	else{
		dn=nodes[0].title;
	}
	console.log("labname:"+labName+", dn:"+dn);
	
})
