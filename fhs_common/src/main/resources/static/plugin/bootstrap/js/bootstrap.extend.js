/**
 * 选中/取消父节点时选中/取消所有子节点
 */
function getChildNodeIdArr(node) {
    var ts = [];
    if (node.nodes) {
        for (x in node.nodes) {
            ts.push(node.nodes[x].nodeId);
            if (node.nodes[x].nodes) {
                var getNodeDieDai = getChildNodeIdArr(node.nodes[x]);
                for (j in getNodeDieDai) {
                    ts.push(getNodeDieDai[j]);
                }
            }
        }
    } else {
        ts.push(node.nodeId);
    }
    return ts;
}

/**
 * 选中所有子节点时选中父节点
 */
function setParentNodeCheck(node) {
    var parentNode = $("#tree").treeview("getNode", node.parentId);
    if (parentNode.nodes) {
        var checkedCount = 0;
        for (x in parentNode.nodes) {
            if (parentNode.nodes[x].state.checked) {
                checkedCount ++;
            } else {
                break;
            }
        }
        if (checkedCount === parentNode.nodes.length) {
            $("#tree").treeview("checkNode", parentNode.nodeId);
            setParentNodeCheck(parentNode);
        }
    }
}

/**
 *
 * @param domId
 * @param treeData
 */
function initBootstrapTreeview(domId, treeData){
    $('#' + domId).treeview(
        {
            data:treeData,
            showCheckbox: true,   //是否显示复选框
            highlightSelected: true,    //是否高亮选中
            multiSelect: false,    //多选
            levels : 2,
            enableLinks : true,//必须在节点属性给出href属性
            color: "#010A0E",
            onNodeChecked : function (event,node) {
                var selectNodes = getChildNodeIdArr(node); //获取所有子节点
                if (selectNodes) { //子节点不为空，则选中所有子节点
                    $('#tree').treeview('checkNode', [selectNodes, { silent: true }]);
                }
            },
            onNodeUnchecked : function(event, node) { //取消选中节点
                var selectNodes = getChildNodeIdArr(node); //获取所有子节点
                if (selectNodes) { //子节点不为空，则取消选中所有子节点
                    $('#tree').treeview('uncheckNode', [selectNodes, { silent: true }]);
                }
            },
            onNodeExpanded : function(event, data) {//节点被打开

            },
            onNodeSelected: function (event, data) {//节点被点击
                if(typeof onClickTree === 'function'){
                    onClickTree(data);
                }
            }
        });
}

/**
 *
 * @param domId
 */
function reloadBootstrapTable(domId, reqPath, columns, paramsExtend, headers, toolBarDomId) {
    $('#' + domId).bootstrapTable('destroy');
    initBootstrapTable(domId, reqPath, columns, paramsExtend, headers, toolBarDomId)
}

/**
 * 初始化bootstrap列表
 * @param domId [require] 需要渲染的元素id
 * @param reqPath [require] 请求数据的地址
 * @param columns [require] 表头信息
 * @param paramsExtend [require] 查询参数
 * @param headers [require]
 * @param toolBarDomId
 */
function initBootstrapTable(domId, reqPath, columns, paramsExtend, headers, toolBarDomId){
    $('#'+ domId).bootstrapTable({
        url: reqPath,////请求后台的URL（*）
        // data : data,
        method: 'post', //请求方式（*）
        toolbar: (typeof (toolBarDomId) !== 'undefined') ? '#' + toolBarDomId : '#toolbar',//工具按钮用哪个容器
        striped: true,                    //是否显示行间隔色
        cache: false,                     //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                 //是否显示分页（*）
        sortable: true,                   //是否启用排序
        sort: 'createTime',
        sortOrder: "asc",                 //排序方式
        sidePagination: "server",         //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                    //初始化加载第一页，默认第一页,并记录
        pageSize: 15,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],      //可供选择的每页的行数（*）
        search: false,                    //是否显示表格搜索
        showColumns: true,                //是否显示所有的列（选择显示的列）
        showRefresh: true,                //是否显示刷新按钮
        minimumCountColumns: 3,           //最少允许的列数
        clickToSelect: true,              //是否启用点击选中行
        height: 800,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                   //每一行的唯一标识，一般为主键列
        showToggle: true,                 //是否显示详细视图和列表视图的切换按钮
        cardView: false,                  //是否显示详细视图
        detailView: false,                //是否显示父子表
        columns: columns,                 //列
        ajaxOptions:{
            headers: headers
        },
        queryParams : function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            var temp = {
                rows: params.limit,                         //页面大小
                page: (params.offset / params.limit) + 1,   //页码
                sort: params.sort,      //排序列名
                sortOrder: params.order, //排位命令（desc，asc）
            };
            $.each(paramsExtend,function(key,value){
                temp[key] = value;
            });
            return temp;
        },

        onLoadSuccess: function (data) {
            if(data.code !== 200){
                alert(data.describe);
            }
        },

        onLoadError: function () {

        },

        onDblClickRow: function (row, $element) {

        }
    });
}
/**
 *  from 校验
 */
function isVlidator(domId) {
    $("#" + domId).bootstrapValidator('validate');//提交验证
    return $("#" + domId).data('bootstrapValidator').isValid();
}

/**
 * 初始化话from校验
 */
function initVlidator(domId, fields) {
    $("#" + domId).bootstrapValidator({
        live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
        feedbackIcons: {//根据验证结果显示的各种图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: fields
    });
}

/**
 * @des 警示框
 * @param1 domId:
 * @param2 type(1:success|2:warning)
 * @param3 msg:提示内容
 * @param4 langType:语言种类
 * @param5 displayTime:显示时间
 */
function Ealert(domId, type, msg, langType, displayTime) {
    var insertHtml = '';

    if (typeof(langType) === "undefined" || langType === null) {
        langType = 1;
    }

    if (typeof(displayTime) === "undefined" || displayTime === null) {
        displayTime = 10000;
    }
    if (type === 1) {
        insertHtml = insertHtml + '<div class="alert alert-success">'
    } else {
        insertHtml = insertHtml + '<div class="alert alert-warning">'
    }
    insertHtml = insertHtml + '<a href="#" class="close" data-dismiss="alert" aria-hidden="true">'
            + '&times;'
        + '</a>';
    if (type === 1) {
        var successText = "SUCCESS!&nbsp;&nbsp;&nbsp;";
        if (langType === 1) {
            successText = "成功!&nbsp;&nbsp;&nbsp;"
        }
        insertHtml = insertHtml + '<strong>'+successText+'</strong><span>' + msg + '</span></div>';
    } else {
        var warningText = "WARNING!&nbsp;&nbsp;&nbsp;";
        if (langType === 1) {
            successText = "警告!&nbsp;&nbsp;&nbsp;"
        }
        insertHtml = insertHtml + '<strong>'+warningText+'</strong><span>' + msg + '</span></div>'
    }
    $("#" + domId).empty();
    $("#" + domId).append(insertHtml);
    setTimeout(function () {
        EalertClose(domId);
    }, displayTime);
}

/**
 * @des 关闭警示框
 * @param1 domId:
 * @param2 type(1:success|2:warning)
 */
function EalertClose(domId) {
    $('#' + domId).empty();
}

/**
 * @des 按钮提示框
 * @param1 domId:
 * @param2 type(1:success|2:warning)
 */
function btnPopup(domId, content) {
    $("#" + domId).attr("data-container", "body");
    $("#" + domId).attr("data-toggle", "popover");
    $("#" + domId).attr("data-placement", "right");
    $("#" + domId).attr("data-content", content);
    $("#" + domId).mouseout(function () {
        $("#" + domId).popover('hide')
    });
    $("#" + domId).mouseover(function () {
        $("#" + domId).popover('show')
    });
}

/**
 * @des 销毁按钮提示框
 * @param1 domId:
 */
function btnPopupDsetory(domId) {
    $("#" + domId).unbind("mouseout")
    $("#" + domId).unbind("mouseover")
}
