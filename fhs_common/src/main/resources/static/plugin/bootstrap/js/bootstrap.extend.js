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
    var insertHtml = ''
    if (displayTime === null) {
        displayTime = 3000;
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
