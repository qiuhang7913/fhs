/* ############################ js tool script by qh ########################### */
/* ############################ 个人web工作累计所属 ########################### */
/**
 * @describe 替换字符串中文编码
 * @remark 暂时用于解决ajax 提交后台非utf-8中文乱码
 * @since v1.2
 * @param str | 存在中文的字符串
 * @return
 */
function hasCN_changeEncode(str){
    var reg = /([^\u0000-\u00FF])/g,
        rv = str.replace( reg , function($){return encodeURIComponent($);}) ;
    return rv;
}

/**
 * 数字金额大写转换(可以处理整数,小数,负数)
 * @param n
 * @returns {string}
 */
function smalltoBIG(n)
{
    var fraction = ['角', '分'];
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];
    var head = n < 0? '欠': '';
    n = Math.abs(n);

    var s = '';

    for (var i = 0; i < fraction.length; i++)
    {
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
    }
    s = s || '整';
    n = Math.floor(n);

    for (var i = 0; i < unit[0].length && n > 0; i++)
    {
        var p = '';
        for (var j = 0; j < unit[1].length && n > 0; j++)
        {
            p = digit[n % 10] + unit[1][j] + p;
            n = Math.floor(n / 10);
        }
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;
    }
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
}

/**
 *
 * @param listObject
 * @param attr
 * @returns {Array}
 * @constructor
 */
function RvFormlistObjectToListAttr(listObject,attr) {
    var ListAttr = [];
    if (Array.isArray(listObject)){
        $.each(listObject,function (index,object) {
            ListAttr.push(object[attr]);
        });
    }
    return ListAttr;
}

/**
 * @describe 获取随机字符串
 * @param randomFlag-是否任意长度
 * @param min|任意长度最小位[if(randomFlag true) 固定位数]
 * @param max|任意长度最大位
 * @author qh
 * @since v1.2
 * @return 随机字符串
 */
function randomWord(randomFlag, min, max){
    var str = "",
        range = min,
        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

    // 随机产生
    if(randomFlag){
        range = getRandom(min, max);
    }

    for(var i=0; i<range; i++){
        pos = Math.round(Math.random() * (arr.length-1));
        str += arr[pos];
    }
    return str;
}

/**
 * @describe 获取随机数
 * @param min|最小数
 * @param max|最大数
 * @author qh
 * @since v1.1.3
 * @return 返回在传入的最大和最小值之间的随机数
 */
function getRandom(min, max) {
    var range = max - min;
    var random = Math.random();// 结果为0-1间的一个随机数(包括0,不包括1)
    return (min + Math.round(random * range));// 返回整数
}

/**
 * @describe 对比字符串是否以另外一个为结尾
 * @param s|被对比的字符串
 * @example hello.endWith(lo)
 * @since v1.1.2
 * @return true or false
 */
String.prototype.endWith = function(s) {
    if (s == null || s === "" || this.length === 0 || s.length > this.length)
        return false;
    if (this.substring(this.length - s.length) === s)
        return true;
    else
        return false;
    return true;
};

/**
 * @describe 对比字符串是否以另外一个为开头
 * @param s|被对比的字符串
 * @example hello.startWith(he)
 * @since v1.1.2
 * @return true or false
 */
String.prototype.startWith = function(s) {
    if (s == null || s === "" || this.length === 0 || s.length > this.length)
        return false;
    if (this.substr(0, s.length) === s)
        return true;
    else
        return false;
    return true;
};

/**
 * @describe 对比字符串是否和另外一个相等 (不区分大小写)!
 * @param s|被对比的字符串
 * @example hello.equals(hello) data:2016/8/22
 * @since v1.1.2
 * @return true or false
 */

String.prototype.equals = function(s) {
    if (this === "" || s === ""){
        return false;
    }
    var str1 = this.toLowerCase();
    var str2 = s.toLowerCase();
    if (str1 === str2) {
        return true;
    }
    return false;
};

/**
 * 提示弹框
 * @param text
 * @param type
 * @constructor
 */
function Ealert0(text,type) {
    var type = type === 1 ? "red" : "green"
    $.alert({
        type:type,
        title: '系统提示',
        content: text,
        icon:'glyphicon glyphicon-info-sign',
        buttons:{
            confirm:{
                text:"确定"
            }
        }
    });
}

/**
 *
 * @param title
 * @param content
 * @param action
 * @constructor
 */
function Econfirm(title, content, action) {
    $.confirm({
        title: title,
        content: content,
        buttons: {
            confirm: {
                text: '确认',
                action: action
            },
            cancel: {
                text: '取消'
            }
        }
    });
}

/**
 *
 * @param title
 * @param content
 * @param action
 * @constructor
 */
function EconfirmPrompt(title, submitText, content, action) {
    $.confirm({
        title: title,
        content: content,
        buttons: {
            formSubmit: {
                text: submitText,
                action: action
            },
            cancel: {
                text: '取消'
            }
        },
        onContentReady: function () {
            // bind to events
            var jc = this;
            this.$content.find('form').on('submit', function (e) {
                // if the user submits the form by pressing enter in the field.
                e.preventDefault();
                jc.$$formSubmit.trigger('click'); // reference the button and click it
            });
        }
    });
}

/**
 * @describe 非ajax post/get提交
 * @date 2017/6/6
 * @example ordinarySub.url = ""; |请求地址(必须)
 *			ajaxSub.postData = {}; | 请求参数
 *			ordinarySub.init("/test/say/hello","get"); | 提交请求
 * @since v1.2.1
 */
var ordinarySub = {
    url : '',
    postData : {},

    init : function(action){
        if(null == url && url.length == 0){
            console.log("未获取请求地址");
        }

        if("get".equals(action)){
            this.get();
        }

        if("post".equals(action)){
            this.post();
        }
    },

    post : function(){
        var ofm = document.createElement("FORM");
        ofm.method = "POST";
        ofm.action = this.url+"?timestamp="+(new Date().getTime());
        ofm.style.display = "none";
        for(var key in this.postData) {
            var tmp = document.createElement("INPUT");
            tmp.type = "hidden";
            tmp.name = key;
            tmp.value = this.postData[key];
            ofm.appendChild(tmp);
        }
        document.body.appendChild(ofm);
        ofm.submit();
    },

    get : function(){
        var finalUrl = this.url;
        for(var key in this.postData) {
            finalUrl = finalUrl + "&key=" + this.postData[key];
        }
        window.location.replace(finalUrl+"&timestamp="+(new Date().getTime()));
    }
};

/**
 * @describe ajax交互数据
 * @data 2016/8/22
 * @updateDate 2017/5/27
 * @example ajaxSub.url = ""; | 请求地址
 * 			ajaxSub.postData = {}; | 请求参数
 * 			ajaxSub.init("get"); | 提交请求
 * 			ajaxSub.rv | 请求结果
 * @since v1.1:支持简单的查询，删除，修改，添加，模糊查询数据
 */
var ajaxSub = {

    postUrl : '',

    getUrl : '',

    delUrl : '',

    rvPostFunc : function (rvData) {

    },

    rvGetFunc : function (rvData) {

    },

    rvDelFunc : function (rvData) {

    },

    reqData : {},

    headers : {},

    // 获取数据
    getData : function() {
        $.ajax({
            url : ajaxSub.getUrl,
            type : 'GET',
            async : true,
            //data : JSON.stringify(ajaxSub.reqData),
            //contentType: "application/json;charset=utf-8",
            dataType : 'json',
            success : function(data) {
                ajaxSub.rvGetFunc(data);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest);
            }
        });
    },
    // 添加数据
    postData : function() {
        if (ajaxSub.postUrl.length === 0){
            console.log("error!");
            return;
        }
        $.ajax({
            url : ajaxSub.postUrl,
            type : 'POST',
            async : true,
            data : JSON.stringify(ajaxSub.reqData),
            contentType: "application/json;charset=utf-8",
            dataType : 'json',
            beforeSend:function(XMLHttpRequest){
                $.each(ajaxSub.headers, function (key, value) {
                    XMLHttpRequest.setRequestHeader(key, value);
                })
            },
            success : function(data) {
                ajaxSub.rvPostFunc(data);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest);
            }
        });
    },

    delData : function() {
        $.ajax({
            url : ajaxSub.delUrl,
            type : 'DELETE',
            data : JSON.stringify(ajaxSub.reqData),
            contentType: "application/json;charset=utf-8",
            async : true,
            dataType : 'json',
            beforeSend:function(XMLHttpRequest){
                $.each(ajaxSub.headers, function (key, value) {
                    XMLHttpRequest.setRequestHeader(key, value);
                })
            },
            success : function(data) {
                ajaxSub.rvDelFunc(data)
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest);
            }
        });
    },

    // 模糊查询
    listData : function() {
        $.ajax({
            url : ajaxSub.url,
            type : 'PATCH',
            async : true,
            data : {
                _method : 'PATCH',
                name : '张三'
            },
            dataType : 'json',
            success : function(data) {
                alert(data);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest);
            }
        });
    }
};

/**
 * @describe 居中打开一个新的窗口
 * @param url | 链接
 * @param name | 窗口名称
 * @param iWidth | 窗口长度
 * @param iHeight | 窗口高低
 * @since v1.2
 */
function openNewWindow(url, name, iWidth, iHeight) {
    var url; // 转向网页的地址;
    var name; // 网页名称，可为空;
    var iWidth; // 弹出窗口的宽度;
    var iHeight; // 弹出窗口的高度;
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
    window.open(
        url,
        name,
        'height='
        + iHeight
        + ',,innerHeight='
        + iHeight
        + ',width='
        + iWidth
        + ',innerWidth='
        + iWidth
        + ',top='
        + iTop
        + ',left='
        + iLeft
        + ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no'
    );

}

/**
 * @describe 打开一个新的浏览器标签页
 * @param src | 链接
 * @since v1.2
 */
function openNewWindowTab(src){
    $('body').append('<a href="" id="gotoNewTab" target="_blank"></a>');

    $('#gotoNewTab').attr('href', src);
    $('#gotoNewTab').get(0).click();
}

/**
 * @describe 验证码计时器
 * @param obj|计时对象
 * @param number|计时时间
 * @param timer|计时器本身对象，初始传如null值即可
 * @example settime(&("#btnId"), 90, null)
 * @since: v1.1.3
 *
 */
function settime(obj, number, timer) {
    if (number == 0) {
        obj.removeAttribute("disabled");
        obj.text = "";
        obj.innerHTML = "获取验证码";
        number = 90;
        return;
    } else {
        obj.setAttribute("disabled", true);
        obj.innerHTML = "请稍后...(" + number + "秒)";
        number--;
    }
    timer = setTimeout(function() {
        settime(obj, number, timer);
    }, 1000);
}

/**********************************有关图片插件上传start(并未完善,待修改)**************************************/
/* ######################## sine v1.2 ############################# */
/* ######################## alert v1.2.1 [1.修改无法删除文件bug] ############################# */

/*	使用方法example：
	[dom]:
    <div id="add_img">
    	存放回显图片
	</div>
	<div style="display: none">
		<form id="uploadForm" method="post" enctype="multipart/form-data">
			<!-- 文件上传input -->
			<input id="file1" type="file" onchange="preview(this,'60', false, 'add_img', 'count')" name="uploadImg" multiple="multiple" />
			<span id="fAliase"></span>
		</form>
		<span id="count" val="0" ></span> <!-- 用于上传文件当前添加图片点击次数计数 -->
	</div>
	<div id="recordDelFileName" style="display: none">
		用于临时记录被删除的文件
	</div>

	[js]:
	var clickSum = 0;
	function upload_file(){
		$("#uploadForm").append('<input id="file' +clickSum+ '" type="file" onchange="preview(this,\'90\', true, \'add_img\', \'count\',\'recordDelFileName\')" name="uploadImg"'
					+ 'multiple="multiple" />');
		$("#file"+clickSum).click();
		clickSum++;
	}
	var form = checkFileGetNeedFile('#uploadForm','recordDelFileName\')
*/


/**
 * 检查当前所有文件信息，检索筛选出需要上传的文件信息
 * @param formDomId 需要上传的form表单的domId
 * @param delFileDomId 删除的文件临时记录domId
 */
function checkFileGetNeedFile(formDomId, delFileDomId){
    var files = $("#"+formDomId).find("input[type='file']");
    var recordDelFileNames = $("#"+delFileDomId).text();

    var newFiles = [];
    var needFiles = [];

    //筛选出空文件
    $.each(files,function(index,file){
        if(file.files.length > 0){//有文件
            $.each(file.files, function(index,file2){
                newFiles.push(file2);
            });
        }
    });

    console.log(newFiles);
    //筛选出被删除的文件
    $.each(newFiles,function(index, newFile){
        var fileName = newFile.name;
        if(recordDelFileNames.indexOf(fileName) == -1){
            needFiles.push(newFile);
        }
    });

    $.each(files,function(index,file){
        file.remove();
    });
    var form = new FormData($('#'+formDomId)[0]);
    $.each(needFiles,function(index,needFile){
        form.append(needFile.name, needFile);
    });
    console.log(needFiles);
    return form;
}

/**
 * 上传图片选择图片后回显
 * @param file
 * @param imgSize 控制回显图片宽高
 * @param flag  控制是否追加图片别名
 * @param backDisplayImgDomId 图片回显到那个dom的domId
 * @param countDomId 统计domId
 * @param delFileLogDomId 被删除文件的临时记录domId
 */
function preview(file, imgSize, flag, backDisplayImgDomId, countDomId, delFileLogDomId) {
    //console.log("count:" + count);
    //数据安全获取校验
    if(!file.files && file.files.length == 0){
        return
    }

    var total = 0;

    var count = parseInt($("#"+countDomId).attr("val"));

    for ( var i = 0; i < file.files.length; i++) {
        //console.log("i:" + i);
        //console.log("i+count:" + (i+count));
        if(!file.files[i]){
            break;
        }

        var current_file = file.files[i];

        total = ((i+1)+count);

        addImg_callback_display(total,current_file, imgSize, flag, backDisplayImgDomId, delFileLogDomId);

    }

    $("#"+countDomId).attr("val",total);
    count++;
}

/**
 * 添加图片回显图片
 * @param current_id 当前图片id
 * @param current_file 当前文件信息
 * @param imgSize 控制图片大小
 * @param flag 控制是否显示别名浮层
 * @param backDisplayImgDomId 图片回显到那个dom的domId
 */
function addImg_callback_display(current_id, current_file, imgSize, flag, backDisplayImgDomId, delFileLogDomId){
    //console.log(backDisplayImgDomId);
    var fileName =  current_file.name;//文件名称
    var fileSize =  current_file.size;//文件大小
    var fileLastModifiedData = current_file.lastModifiedDate;//最后修改时间

    var reader = new FileReader();
    reader.onload = function(evt) {
        //console.log(evt);
        //console.log(id);
        $("#"+ backDisplayImgDomId).append(
            function(){
                var html = 	'<div id="img_' + current_id + '" class="pull-left margin-left4 image-mask-box" >'
                    + '<img src="' + evt.target.result + '" height="'+imgSize+'" width="'+imgSize+'"  title = '+ fileName +' '
                    + 'onmouseover="imgMouseOver(\'span_delete_'+ current_id +'\')" />';
                //img 标签结束
                if(flag){
                    html = html + '<input type="text" id="span_des_'+ current_id +'" class="image-mask-compile" value="點擊輸入信息" '
                        + 'onfocus="imgAddAliase_focus(this)"'
                        + 'onchange="imgAddAliase_change(this)" />';
                }
                //输入文件名称信息标签结束
                html = html + '<span id="span_delete_'+ current_id +'" hidden="true" class="image-mask-delete" '
                    + 'onmouseout="imgMouseOut(this)" '
                    + 'onclick="imgDelete(this, \'' +fileName+ '\', \'' + delFileLogDomId + '\')">删除</span>'
                    //删除浮层标签结束
                    + '</div>';
                return html;
            }
        );
    };
    reader.readAsDataURL(current_file);
}

/**
 * 鼠标移入
 * @param which
 */
function imgMouseOver(which){
    //console.log(which);
    if($("#" + which).attr("hidden")){
        $("#" + which).attr("hidden", false);
    }
}

/**
 * 鼠标移出
 * @param which
 */
function imgMouseOut(which){
    if(!$(which).attr("hidden")){
        $(which).attr("hidden", true);
    }
}

/**
 * 图片删除
 * @param which
 */
function imgDelete(which, fileName, delFileLogDomId){
    //清除img
    var delete_id = $(which).attr("id").replace("span_delete_","img_");
    //console.log(delete_id);
    $("#" + delete_id).remove();

    //清除文件别名
    var input_id = $(which).attr("id").replace("span_delete_","fAliases");
    //console.log(input_id);
    if(!(typeof($("#" + input_id).val())  == 'undefined')){
        $("#" + input_id).remove();
    }

    //记录删除
    $("#"+delFileLogDomId).append(fileName);
}

/**
 * 修改文件别名
 * @param which
 */
function imgAddAliase_change(which){
    var fAliaseName = $(which).val();
    var input_id = $(which).attr("id").replace("span_des_","fAliases");
    //console.log(input_id);
    if($("#" + input_id).val() != $(which).val()){
        $("#" + input_id).remove();
        $("#fAliase").append('<input id="' +input_id+ '" type="text" style="text-align: center;" name="fAliase" value="' + fAliaseName + '" /> ');
    }
}

/**
 * 修改文件别名聚焦
 * @param which
 */
function imgAddAliase_focus(which){
    $(which).val("");
}

/**********************************有关图片上传end**************************************/

/**
 * 定义参数对象示例方法,参照使用!
 */
function par_obj(auth_rdn,res,ptype,pip,acc_name){ //参数对象
    this.auth_rdn=auth_rdn;
    this.res=res;
    this.ptype=ptype;
    this.pip = pip;
    this.acc_name = acc_name;
    this.show = function show(){
        alert("[subPar:]" + " [auth_rdn:" + this.auth_rdn + "] [res:" +this.res + "] [ptype:" + this.ptype + "] [pip:" +this.pip + "] [acc_name:" +this.acc_name+ "]");
    };
    return this;
}