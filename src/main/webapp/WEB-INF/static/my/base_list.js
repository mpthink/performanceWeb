
//更加详细的配置参考：http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
/**
 * server 模式，使用selectPage,返回page
 * 提供如下参数：
 * - dataUrl
 * - dataColumns
 */

function serverPagination() {
    $('#dataTable').bootstrapTable({
        url: dataUrl,     //服务器数据的加载地址,请求后台的URL（*）
        dataField: "records",   //返回的数据列表的key值，Key in incoming json containing rows data list.default:rows
        method: 'get',           // 服务器数据的请求方式 'get' or 'post'
        toolbar: '#toolbar',        //一个jQuery 选择器，指明自定义的toolbar 例如: #toolbar, .toolbar.
        striped: true,           //是否显示行间隔色
        cache: false,            //设置为 false 禁用 AJAX 数据缓存,default is true
        pagination: true,          //设置为 true 会在表格底部显示分页条,default is false
        sortable: false,           //设置为false 将禁止所有列的排序, default is true
        sortOrder: "asc",          //定义排序方式 'asc' 或者 'desc',default is "asc"
        queryParamsType:'limit',     //设置为 'limit' 则会发送符合 RESTFul 格式的参数.default:limit
        queryParams: function(params) {
        	return {
        			_size: params.limit,  //页面大小
                	_index: params.offset, //页码，用于server端分页构造sql
                	};
              	},							//请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 
        									//如果 queryParamsType = 'limit' ,返回参数必须包含limit, offset, search, sort, order 
        									//否则, 需要包含: pageSize, pageNumber, searchText, sortName, sortOrder. 
        									//返回false将会终止请求
        sidePagination: "server",       //设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置 服务器数据地址（url）或者重写ajax方法
        								//default is 'client'
        pageNumber:1,            //如果设置了分页，首页页码. default is 1
        pageSize: 5,            //如果设置了分页，页面数据条数, default is 10
        pageList: [10, 25, 50, 100],    //如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。default: [10, 25, 50, 100, All]
        strictSearch: false,      //设置为 true启用 全匹配搜索，否则为模糊搜索, default: false
        clickToSelect: true,        //设置true 将在点击行时，自动选择rediobox 和 checkbox,default: false
                 //height: 定义表格的高度。，如果没有设置height属性，表格自动根据记录条数调整表格高度
        //uniqueId: "id",           //Indicate an unique identifier for each row. 每一行的唯一标识，一般为主键列,default: undefined.
        cardView: false,          //设置为 true将显示card视图，适用于移动设备。否则为table视图，适用于pc.  default: false
        detailView: false,          //设置为 true 可以显示详细页面模式。 default: false
        showRefresh:true,           //是否显示 刷新按钮. default: false, 
        search:true,                //是否启用搜索框,default: false
        searchAlign:'right',          //指定 搜索框 水平方向的位置。'left' or 'right',default: right
        detailView:false,           //设置为 true 可以显示详细页面模式。default:false
        detailFormatter:function(index, row) {return '';},  //格式化详细页面模式的视图，需要重写这个方法
        //rowStyle:function(row,index) {return class;}, 自定义行样式 参数为：        row: 行数据          index: 行下标         返回值可以为class或者css
        showColumns:true,           //是否显示 内容列下拉框,  default: false
        columns: dataColumns,   //列配置项,详情请查看 列参数 表格.
        contextMenu: '#alarm-rmenu-ul',
		beforeContextMenuRow : function(e, row, buttonElement) {
			if (alarmListType != 3) {
				return false;
			}
			if (ackState[row.ACK_STATUS] == "Acknowledged")
			{
				$('#ack_text').html("UnAck");
			} else// if (ackState[row.ACK_STATUS] == "Unacknowledged")
			{
				$('#ack_text').html("Ack");
			}
		},
		onContextMenuItem: function(row, $el){
			if($el.data("item")=='Ack')
				{
				if(ackState[row.ACK_STATUS] == "Acknowledged")
					{
					  alarmDelegat("UnAck",row);
					}
				else
					{
					  alarmDelegat("Ack",row);
					}
				}
			else
			{
				alarmDelegat($el.data("item"),row);
			}
		  }
    });

}

/**
 * client 模式，使用selectList，返回list即可
 * 提供如下变量：
 * - dataUrl
 * - dataColumns
 */

function clientPagination() {
    $('#dataTable').bootstrapTable({
        url: dataUrl,     //服务器数据的加载地址,请求后台的URL（*）
        //dataField: "records",   //返回的数据列表的key值，Key in incoming json containing rows data list.default:rows
        method: 'get',           // 服务器数据的请求方式 'get' or 'post'
        toolbar: '#toolbar',        //一个jQuery 选择器，指明自定义的toolbar 例如: #toolbar, .toolbar.
        striped: true,           //是否显示行间隔色
        cache: true,            //设置为 false 禁用 AJAX 数据缓存,default is true
        pagination: true,          //设置为 true 会在表格底部显示分页条,default is false
        //sortable: false,           //设置为false 将禁止所有列的排序, default is true
        sortOrder: "asc",          //定义排序方式 'asc' 或者 'desc',default is "asc"
        //queryParamsType:limit,     //设置为 'limit' 则会发送符合 RESTFul 格式的参数.default:limit
        //queryParams: function(params) {
        //	return {_size: params.limit,  //页面大小
        //       	_index: params.offset, //页码，用于server端分页构造sql
        //        	};
        //      	},							//请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 
        									//如果 queryParamsType = 'limit' ,返回参数必须包含limit, offset, search, sort, order 
        									//否则, 需要包含: pageSize, pageNumber, searchText, sortName, sortOrder. 
        									//返回false将会终止请求
        sidePagination: "client",       //设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置 服务器数据地址（url）或者重写ajax方法
        								//default is 'client'
        pageNumber:1,            //如果设置了分页，首页页码. default is 1
        pageSize: 20,            //如果设置了分页，页面数据条数, default is 10
        pageList: [20, 50, 100],    //如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。default: [10, 25, 50, 100, All]
        strictSearch: false,      //设置为 true启用 全匹配搜索，否则为模糊搜索, default: false
        clickToSelect: true,        //设置true 将在点击行时，自动选择rediobox 和 checkbox,default: false
                 //height: 定义表格的高度。，如果没有设置height属性，表格自动根据记录条数调整表格高度
        //uniqueId: "id",           //Indicate an unique identifier for each row. 每一行的唯一标识，一般为主键列,default: undefined.
        cardView: false,          //设置为 true将显示card视图，适用于移动设备。否则为table视图，适用于pc.  default: false
        detailView: false,          //设置为 true 可以显示详细页面模式。 default: false
        showRefresh:true,           //是否显示 刷新按钮. default: false, 
        search:true,                //是否启用搜索框,default: false
        searchAlign:'right',          //指定 搜索框 水平方向的位置。'left' or 'right',default: right
        //detailView:false,           //设置为 true 可以显示详细页面模式。default:false
        //detailFormatter:function(index, row) {return '';},  //格式化详细页面模式的视图，需要重写这个方法
        //rowStyle:function(row,index) {return class;}, 自定义行样式 参数为：        row: 行数据          index: 行下标         返回值可以为class或者css
        showColumns:true,           //是否显示 内容列下拉框,  default: false
        columns: dataColumns   //列配置项,详情请查看 列参数 表格.
    });
}