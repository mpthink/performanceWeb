var ackState={0:"Acknowledged", 1:"Unacknowledged"};
var alarmSeverity={1:"Critical",2:"Major",3:"Minor",4:"Warning",5:"Cleared"};
var alarmType = {
	1 : "Communication",
	2 : "Environmental",
	3 : "Equipment",
	4 : "Processing",
	5 : "Quality of service",
	6 : "Physical violation",
	7 : "Security violation",
	8 : "Time domain violation",
	9 : "Integrity violation",
	10 : "Operational violation"
};

var labName;
var zTree, rMenu, treeNodes;
var nasdaObjCMap,alarmStatesMap, objClassMetaMap, interfacesMap, nasdaRefMrMap,nasdaRefAgentMap;
var mrObjsMap={};
var idxOfUpdMr = -1, alarmListType=0;
/**
 * zTree JS
 */
var setting = {
		view: {
	        showLine : true,                  //是否显示节点间的连线  
			showIcon: false,
			showTitle: true
		},

		data: {
			simpleData: {
				enable: true,			//数据是否采用简单 Array 格式，默认false
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
			},
			key: {
				title: "title"
			}
		},
		
		callback: {
			onRightClick: OnRightClick,
			onClick : zTreeOnClick
		}
		
    };

function OnRightClick(event, treeId, treeNode){
	if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			zTree.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			zTree.selectNode(treeNode);
			var nodes = zTree.getSelectedNodes();
			var type="node";
			if (nasdaRefAgentMap[nodes[0].id]!=null && nasdaRefAgentMap[nodes[0].id].AGENT_GID==nodes[0].id) {
				type = 'agent'
			}
			else if (nasdaObjCMap[nodes[0].ocId].OC_NAME=="MR")//MR
			{
				type = "mr";
			}else if (nasdaObjCMap[nodes[0].ocId].OC_NAME=="MRC")//MRC
			{
				type = "mrc";
			}
			else if (nasdaObjCMap[nodes[0].ocId].OC_ADAPTATION == "com.nsn.netact.nasda.interfaces") {
				type = "itf";
			}
			showRMenu(type, event.clientX, event.clientY);
			zTreeOnClick(event, treeId, treeNode);
		}
}

function zTreeOnClick(event, treeId, treeNode) {
	//curNode=treeNode;
	
	$('#alarmstatus').css('display', 'none');
	$('#interfaceProperty').css('display', 'none');
	$('#associatedObjects').css('display', 'none');

	$('#adaptationId').val(nasdaObjCMap[treeNode.ocId].OC_ADAPTATION);
	$('#distinguishedName').val(treeNode.title);
//	$("#adaptationId").html(nasdaObjCMap[treeNode.ocId].OC_ADAPTATION);
//	$('#distinguishedName').html(treeNode.title);
	$('#globalId').val(treeNode.id);
	$('#instance').val(treeNode.objInstance);
	
	var objectClass = nasdaObjCMap[treeNode.ocId].OC_NAME;

	$('#objectClass').val(objectClass);
	if (objClassMetaMap[objectClass] != null) {
		//layer.alert("treeNode.ocId:"+treeNode.ocId+",objectClass="+objectClass+",ADAPTATION_ID="+objClassMetaMap[objectClass].ADAPTATION_ID)
		$('#release').val(objClassMetaMap[objectClass].ADAPTATION_RELEASE);
		$('#adaptationId').val(objClassMetaMap[objectClass].ADAPTATION_ID);
	}
	else {
		$('#release').val(treeNode.version);
	}

	if(nasdaRefAgentMap[treeNode.id]!=null)
	{
		$('#Agent').val(treeNode.title);
		$('#associatedObjects').css('display', 'block');
	}
	
	var alarmState=alarmStatesMap[treeNode.id];
	if(alarmState!=null)
	{
		$('#lastUpdateTimestamp').val(timeStamp2String(alarmState.LAST_UPDATE_TIMESTAMP,"yyyy-MM-dd hh:mm:ss"));
		$('#maxAlarmSeverity').val(alarmSeverity[alarmState.MAX_ALARM_SEVERITY]);
		$('#ackState').val(ackState[alarmState.ACK_STATE]);
		$('#criticalsCnt').val(alarmState.CRITICALS_CNT);
		$('#majorsCnt').val(alarmState.MAJORS_CNT);
		$('#minorsCnt').val(alarmState.MINORS_CNT);
		$('#warningsCnt').val(alarmState.WARNINGS_CNT);
		$('#nonacksCnt').val(alarmState.NONACKS_CNT);
		
		$('#alarmstatus').css('display', 'block');
	}

	loadMrProperty();
	loadInterfaceProperty();
	
	$('#Object_Property').css('display', 'block');
}

function loadMrProperty()
 {
	$('#maintenanceRegion').html("");
	var oldMrDn = '';
	var nodeId=$('#globalId').val();
	if (nasdaRefMrMap[nodeId] != null) {
		oldMrDn = mrObjsMap[nasdaRefMrMap[nodeId].MR_GID].name;
	}

	if(nasdaRefAgentMap[nodeId]==null)
	{
		return;
	}
	// agent
	if (nasdaRefAgentMap[nodeId].AGENT_GID == nodeId) {
		$('#maintenanceRegion').append(getMrOption(oldMrDn));
	} else if(oldMrDn!=''){
		$('#maintenanceRegion').append(
				'<option selected="selected" value="' + oldMrDn + '">'
						+ oldMrDn + '</option>');
	}
}

function loadInterfaceProperty()
{
	var nodeId=$('#globalId').val();
	if($("#adaptationId").val()!='com.nsn.netact.nasda.interfaces')
 	{
		return;
	}
	var curPropertyMap=InterfacePropertyMap[$('#objectClass').val()];
	if(curPropertyMap==null)
	{
		return;
	}
	var itfProp = interfacesMap[nodeId];
	var itfhtml = '';
	for ( var key in itfProp) {
//		console.log("itfProp:"+key);
		var node=curPropertyMap[key];
		if (node == null) {
			continue;
		}
		if(node.type=="text")
		{
			
			itfhtml += '<tr><td>' + node.title
			+ '</td><td><input id="'+key+'" name ="'+key+'" type="text" value="' + (itfProp[key]==null?'':itfProp[key])
			+ '" ></td></tr>';
		}
		else if(node.type=="select")
		{
			itfhtml += '<tr><td>' + node.title + '</td><td><select id="'+key+'" name ="'+key+'" >';
			
			for (var optkey in node.option)
			{	
				if(itfProp[key]==optkey)
				{
					itfhtml += '<option selected="selected" value="'+optkey+'">'+node.option[optkey]+'</option>';
				}
				else {
					itfhtml += '<option value="'+optkey+'">'+node.option[optkey]+'</option>';
				}				
			}
			itfhtml +='</select></td></tr>';
		}
	
	}
	$('#interfaceTable').html(itfhtml);
	$('#interfacePropertyName').html($('#objectClass').val());

	$('#interfaceProperty').css('display', 'block');
}

Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k]
					: ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}
function timeStamp2String(timestamp, format) {
	if (timestamp == null) {
		return null;
	}
	return (new Date(timestamp)).format(format);
} 


function showRMenu(type, x, y) {
	$("#rMenu ul").show();
	$("#m_listActiveAlarms").hide();
	$("#m_listWarningAlarms").hide();
	$("#m_listHistoryAlarms").hide();
	$("#m_upload").hide();
	$("#m_update_mrc").hide();
	$("#m_createObject").show();
	$("#m_reload").show();

	if (type == "node" || type == "agent" ) {
		$("#m_listActiveAlarms").show();
		$("#m_listWarningAlarms").show();
		$("#m_listHistoryAlarms").show();
		$("#m_upload").show();
	}
	
	if(type == "agent") {
		$("#m_update_mrc").show();
	}
	else if(type=="mr" || type=="itf")
	{
		$("#m_createObject").hide();
	}

	rMenu.css({
		"top" : y + "px",
		"left" : x + "px",
		"visibility" : "visible"
	});
	$("body").bind("mousedown", onBodyMouseDown);
}

function hideRMenu() {
	if (rMenu) rMenu.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}


function onBodyMouseDown(event){
if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
	rMenu.css({"visibility" : "hidden"});
	}
}

//right function buttons
function listHistoryAlarms(){
	hideRMenu();
	alarmListType=1;
	var nodes = zTree.getSelectedNodes();
	var dn = escape(encodeURIComponent(nodes[0].title));
	var url = "/webmonitor/fm/alarm/list/" +labName +"/1/"+dn;
	getAlarmData(url);
}

function listWarningAlarms(){
	hideRMenu();
	alarmListType=2;
	var nodes = zTree.getSelectedNodes();
	var dn = escape(encodeURIComponent(nodes[0].title));
	var url = "/webmonitor/fm/alarm/list/" +labName +"/2/"+dn;
	getAlarmData(url);
}

function listActiveAlarms(){
	hideRMenu();
	alarmListType=3;
	var nodes = zTree.getSelectedNodes();
	var dn = escape(encodeURIComponent(nodes[0].title));
	var url = "/webmonitor/fm/alarm/list/" +labName +"/3/"+dn;
	getAlarmData(url);
}

function alarmUpload() {
	hideRMenu();

	var nodes = zTree.getSelectedNodes();
	var dn = escape(encodeURIComponent(nodes[0].title));
	var url = "/webmonitor/fm/alarm/list/" +labName +"/4/"+dn;
	getAlarmData(url);
}

function createObject() {
	hideRMenu();
	var nodes = zTree.getSelectedNodes();
	var dnType = 'node';
	if (nodes[0] == null) {
		dnType = 'empty';
	} else {
		var ocName=nasdaObjCMap[nodes[0].ocId].OC_NAME;
		if (nasdaRefAgentMap[nodes[0].id]!=null && nasdaRefAgentMap[nodes[0].id].AGENT_GID==nodes[0].id) {
			dnType = 'agent'
		} else if (ocName == "PLMN")//PLMN
		{
			dnType = 'root'
		} else if (ocName == "MRC")//MRC
		{
			dnType = 'mrc'
		} else if (ocName == "MR")//MR
		{
			return;
		} else if (nasdaObjCMap[nodes[0].ocId].OC_ADAPTATION  == "com.nsn.netact.nasda.interfaces") {
			return;
		}
		
	}

	layer.open({
		type : 2,
		title : false,
		shadeClose : true,
		shade : false,
		maxmin : true, // 开启最大化最小化按钮
		area : [ '580px', '400px' ],
		offset: '80px',
		content : ['/webmonitor/nasda/createMO/' + dnType, 'no' ],
		// content:['../../../views/netact/monitor/createobj','no']
		end: function () {
			console.log(" create object end");
			//reloadTree();
        }
	});
}

function getMrOption(oldMrDn)
{
	var mrhtml=''
	if(oldMrDn!=null && oldMrDn!='')
	{
		mrhtml += '<option value=""></option>';
	}
	else{
		oldMrDn='';
	}
	mrhtml += '<option selected="selected"  value="' +oldMrDn + '">'+ oldMrDn + '</option>';

	for ( var key in mrObjsMap) {
		if(!mrObjsMap[key].refed)	
		{
			mrhtml += '<option value="' + mrObjsMap[key].name + '">'+ mrObjsMap[key].name + '</option>';
		}
	}
	return mrhtml;
}

function update_mrc_layer() {
	hideRMenu();

	var dn, mrhtml = '', oldMrDn = '';
	var nodes = zTree.getSelectedNodes();

	if (nodes[0] == null) {
		return;
	} else {
		dn = nodes[0].title;
	}

	if(nasdaRefMrMap[nodes[0].id]!=null)
	{
		oldMrDn = mrObjsMap[nasdaRefMrMap[nodes[0].id].MR_GID].name;
	}

	var mr_context = '<div class="form-group"> '
			+ '<label>Distinguished Name</label> <select id="MrObject"'
			+ 'name="MrObjectSelect" class="form-control select2" '
			+ 'style="width: 80%;" required>' + getMrOption(oldMrDn) + '</select> </div>'

	idxOfUpdMr=layer.open({
		type : 1,
		title : '<span style="color:#333;font-size:14px;font-weight:bold">Maintenance region Associate</span>',
		shadeClose : true,
		shade : false,
		maxmin : false, // 开启最大化最小化按钮
		area : [ '368px', '180px' ],
		move : true,
		content : mr_context,
		btn : [ 'OK', 'Cancel' ],
		yes : function(index, layero) {
			var newMrDn = $('#MrObject').val();
			update_mrc(nodes[0].id, dn, oldMrDn, newMrDn);
		},
		cancel : function(index) {
			//layer.alert('cancel...', {icon : 1});
		},
	});
	
}

function reloadTree() {
	hideRMenu();
	displayTopology(labName);
	loadObjectData(labName);
}

function loadObjectData(labName) {
	$.ajax({
		url : "/webmonitor/nasda/objectdata/" + labName,
		success : function(data) {
			if (data) {
				var retmap = JSON.parse(data);
				nasdaObjCMap=retmap.nasdaObjectClassMap;
				objClassMetaMap=retmap.objClassMetaMap;
				interfacesMap=retmap.interfaceMap;
				alarmStatesMap=retmap.alarmStatesMap;
				nasdaRefAgentMap=retmap.nasdaRefAgentMap;
				nasdaRefMrMap=retmap.nasdaRefMrMap;
		
				//get Mr
				var mrUsed = new Array();
				
				for(var i =0;i<treeNodes.length;i++){ 
					node = treeNodes[i] ;
			
					if(nasdaObjCMap[node.ocId].OC_NAME=="MR")
					{
						mrObjsMap[node.id]={"name":node.title,"refed":false};
						//console.log("mrObjsMap[node.id] "+mrObjsMap[node.id].name);
					}
				}
				
				for (var key in nasdaRefMrMap)
				{
					mrObjsMap[nasdaRefMrMap[key].MR_GID].refed=true;
				}
			}
		},
		error : function() {
			layer.msg("Get object data failed for lab " + labName, {
				icon : 2,
				time : 3000,
				area : [ '250px', '100px' ]
			});
		}
	});
}


function displayTopology(labName) {
	$("#objectTree").empty();
	$.ajax({
		url: "/webmonitor/nasda/motree/"+labName,
		success:function(data){
			if(data){
				treeNodes = JSON.parse(data);
				
				zTree = $.fn.zTree.init($("#objectTree"), setting, treeNodes);
				rMenu = $("#rMenu")
			}
		},
		error:function(){
			layer.msg("Get data failed for lab "+labName, {icon: 2,time:3000,area:['250px','100px']});
		}
	});
}

/**
 * select lab 选择后加载topology
 */

function loadTopology(){
	$("#labSelect").change(function(){
		layer.msg('Delay about 4 seconds', {icon: 1,time:1500,area:['250px','100px']});
		labName = $("#labSelect").val();
		//console.log(labName);
		displayTopology(labName);
		loadObjectData(labName);
	});
}


//alarm table JS
var dataUrl;
var dataColumns = [{  
	    field: 'Number',  
	    title: 'No',  
	    formatter: function (value, row, index) {  
	    return index+1;  
	    }  
	}, {
	    field: 'SEVERITY',
	    title: 'Severity',
	
	    formatter : function(value, row, index){
	    	return alarmSeverity[value];
	    },
	    cellStyle : function(value, row, index) {
		var bgcolor = "";
		var severity=alarmSeverity[value];
		if (severity == "Critical") {
			bgcolor = "#ff0000"
		} else if (severity == "Major") {
			bgcolor = "#F85717";
		}  else if (severity == "Minor") {
			bgcolor = "#fece00";
		}  else if (severity == "Warning") {
			bgcolor = "#1263EFB3";
		} 
		return {
			css : {
				"background-color" : bgcolor
			}
		};
	}
	},{
	    field: 'ALARM_NUMBER',
	    title: 'Alarm Number'
	}, {
	    field: 'ALARM_TYPE',
	    title: 'Alarm Type',
	    formatter : function(value, row, index){
	    	return alarmType[value];
	    }
	},{
	    field: 'ALARM_TIME',
	    title: 'Alarm Time',
	    formatter : function(value, row, index){
	    	return timeStamp2String(value,"yyyy-MM-dd hh:mm:ss");
	    }
	},{
	    field: 'EVENT_TIME',
	    title: 'Event Time',
	    formatter : function(value, row, index){
	    	return timeStamp2String(value,"yyyy-MM-dd hh:mm:ss");
	    }
	},{
	    field: 'DN',
	    title: 'Distinguished Name'
	}, {
	    field: 'TEXT',
	    title: 'Alarm Text'
	}, {
	    field: 'ACK_STATUS',
	    title: 'Ack State',
	    formatter : function(value, row, index){
	    	return ackState[value];
	    }
	}, {
	    field: 'ACKED_BY',
	    title: 'Ack User'
	},{
	    field: 'CANCEL_TIME',
	    title: 'Cancel Time',
	    visible: false,
	    formatter : function(value, row, index){
	    	return timeStamp2String(value,"yyyy-MM-dd hh:mm:ss");
	    }
	},{
	    field: 'CANCELLED_BY',
	    title: 'Cancel User',
	    visible: false
	},{
	    field: 'SUPPLEMENTARY_INFO',
	    title: 'Supplementary Information',
	    visible: false
	},{
	    field: 'ALARM_COUNT',
	    title: 'Instance Counter',
	    visible: false
	},{
	    field: 'NOTIFICATION_ID',
	    title: 'Notification ID',
	    visible: false
	},{
	    field: 'PROBABLE_CAUSE',
	    title: 'Probable Cause',
	    visible: false
	},{
	    field: 'ADDITIONAL_TEXT5',
	    title: 'Additional Information 1',
	    visible: false
	},{
	    field: 'ADDITIONAL_TEXT6',
	    title: 'Additional Information 2',
	    visible: false
	},{
	    field: 'ADDITIONAL_TEXT7',
	    title: 'Additional Information 3',
	    visible: false
	}];

function getAlarmData(url){
	dataUrl = url;
	$('#dataTable').bootstrapTable('refresh',{url:dataUrl});
}

//list labs JS
function listLabs(){
	$.ajax({
		url:"/system/lab/getmylist",
		type:"get",
		timeout:3000,
		//async:true,  default:true
		//data:{username:$("#xxx").val()},
		dataType:"json",
		success:function(data){
			var optionList = "";
			$.each(data, function(i, item) {
				optionList += '<option value="' + item.labName + '">' + item.labName + '</option>';
	       	 });
			/**
			 * 另外一种遍历方式
			for(var i in data){
				optionList += '<option value="' + data[i].labName + '">' + data[i].labName + '</option>';
			}
			 */
			$("#labSelect").append(optionList);
		}
	})
	
}


$("#property_cancel").click(function() {
	loadMrProperty();
	loadInterfaceProperty();
});

$('#form_property').on('submit', function() {
	
	var mrDn= $("#maintenanceRegion").val();
	var nodeId= $("#globalId").val();
	var dn= $("#distinguishedName").val();
	var oldMrDn="";
	if(nasdaRefMrMap[nodeId]!=null)
	{
		oldMrDn = mrObjsMap[nasdaRefMrMap[nodeId].MR_GID].name;
	}
	update_mrc(nodeId, dn, oldMrDn, mrDn);

	update_InterfaceProperty(dn,nodeId);
	
	return false;
});

function update_InterfaceProperty(dn,nodeId)
{
	var param={};
	if($("#adaptationId").val()!='com.nsn.netact.nasda.interfaces')
 	{
		return;
	}
	var curPropertyMap=InterfacePropertyMap[$('#objectClass').val()];
	if(curPropertyMap==null)
	{
		return;
	}
	var isChanged=false;
	var itfProp = interfacesMap[nodeId];
	var itfhtml = '';
	for ( var key in itfProp) {
		if (curPropertyMap[key] != null) {
			nVal=$("#"+key).val();
			var oVal=itfProp[key];
			if (oVal==null)
			{
				oVal="";
			}
			if (nVal==null)
			{
				nVal="";
			}
			if(nVal!=itfProp[key])
			{
				//console.log(key+"/"+curPropertyMap[key].name+':nVal=='+nVal+",oVal=="+itfProp[key]);	
				isChanged=true;
			}
			param[curPropertyMap[key].name]=nVal;
		}
	}
	
	if(!isChanged)
	{
		console.log('update_InterfaceProperty : no any changed');
		return;
	}
	param["dn"]=dn;
	param["objectClass"]=$('#objectClass').val();
	console.log("url=/webmonitor/nasda/updateMO/" + labName);
	$.ajax({
		method : "POST",
		data :param,
		url : "/webmonitor/nasda/updateMO/" + labName,
		success : function(data) {
			if (data=='Ok') {
				layer.msg("Update Object Property success", {icon : 1});
				loadObjectData(labName);
			}else {
				layer.alert(data, {icon : 1});
			}
		},
		error : function() {
			layer.msg("Update Object Property failed for lab " + labName, {icon : 1});
		}
	});
}


function update_mrc(nodeId, dn, oldMrDn, newMrDn)
{
	if (nasdaRefAgentMap[nodeId]==null || nasdaRefAgentMap[nodeId].AGENT_GID!=nodeId)
	{
		return false;
	}
	
	if (oldMrDn == newMrDn) {
		layer.alert('same, no need update.', {icon : 1});
		return false;
	}

	console.log('node.dn'+dn+', newMrDn=' + newMrDn + ', oldMrDn=' + oldMrDn);
	$.ajax({
		method : "POST",
		data :{"dn":dn,"oldMrDn":oldMrDn,"mrDn":newMrDn},
		url : "/webmonitor/nasda/updateMr/" + labName,
		success : function(data) {
			if (data=='Ok') {
				layer.msg("Maintenance region Associate success", {icon : 1});
				loadObjectData(labName);
				layer.close(idxOfUpdMr);
				idxOfUpdMr=-1;
				return true;
			}else {
				layer.alert(data, {icon : 1});
			}
		},
		error : function() {
			layer.msg("Maintenance region Associate failed for lab " + labName, {icon : 1});
		}
	});
	return false;
}

function alarmDelegat(opt, alarm)
{
	 console.log(opt+"/"+opt);
	 $.ajax({
			method : "POST",
			data :{"labName": labName,"opt":opt,"alarm":alarm,"alarmKey":alarm.CONSEC_NBR},
			url : "/webmonitor/fm/alarm/delegat",
			success : function(data) {
				if (data=='Ok') {
					layer.msg("success", {icon : 1});
					listActiveAlarms();
					return true;
				}else {
					layer.alert(data, {icon : 1});
				}
			},
			error : function() {
				layer.msg("Maintenance region Associate failed for lab " + labName, {icon : 1});
			}
		});
}

//init functions
$(function(){
	listLabs();
	loadTopology();
	serverPagination();
})
