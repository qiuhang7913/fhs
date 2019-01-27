/* ############################ js tool script by qh ########################### */
/* ############################ ����web�����ۼ����� ########################### */

/**
 * @describe �滻�ַ������ı���
 * @remark ��ʱ���ڽ��ajax �ύ��̨��utf-8��������
 * @since v1.2
 * @param str | �������ĵ��ַ���
 * @return 
 */
function hasCN_changeEncode(str){
	var reg = /([^\u0000-\u00FF])/g,	
	rv = str.replace( reg , function($){return encodeURIComponent($);}) ;	
	return rv;
}


/**
 * @describe ��ȡ����ַ���
 * @param randomFlag-�Ƿ����ⳤ�� 
 * @param min|���ⳤ����Сλ[if(randomFlag true) �̶�λ��] 
 * @param max|���ⳤ�����λ
 * @author qh
 * @since v1.2
 * @return ����ַ���
 */
function randomWord(randomFlag, min, max){
    var str = "",
    range = min,
    arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
 
    // �������
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
 * @describe ��ȡ�����
 * @param min|��С��
 * @param max|�����
 * @author qh
 * @since v1.1.3 
 * @return �����ڴ����������Сֵ֮��������
 */
function getRandom(min, max) {
	var range = max - min;
	var random = Math.random();// ���Ϊ0-1���һ�������(����0,������1)
	return (min + Math.round(random * range));// ��������
}

/**
 * @describe �Ա��ַ����Ƿ�������һ��Ϊ��β 
 * @param s|���Աȵ��ַ��� 
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
 * @describe �Ա��ַ����Ƿ�������һ��Ϊ��ͷ 
 * @param s|���Աȵ��ַ��� 
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
 * @describe �Ա��ַ����Ƿ������һ����� (�����ִ�Сд)!
 * @param s|���Աȵ��ַ��� 
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
 * @describe ��ajax post/get�ύ
 * @date 2017/6/6
 * @example ordinarySub.url = ""; |�����ַ(����)
 *			ajaxSub.postData = {}; | �������
 *			ordinarySub.init("/test/say/hello","get"); | �ύ����
 * @since v1.2.1
 */
var ordinarySub = {
		url : '',
		postData : {},
		
		init : function(action){
			if(null == url && url.length == 0){
				console.log("δ��ȡ�����ַ");
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
 * @describe ajax�������� 
 * @data 2016/8/22
 * @updateDate 2017/5/27 
 * @example ajaxSub.url = ""; | �����ַ
 * 			ajaxSub.postData = {}; | �������
 * 			ajaxSub.init("get"); | �ύ����
 * 			ajaxSub.rv | ������
 * @since v1.1:֧�ּ򵥵Ĳ�ѯ��ɾ�����޸ģ���ӣ�ģ����ѯ���� 
 */
var ajaxSub = {

	url : '',
	rvFunc : function (rvData) {
		
	},

	postData : {},

	headers : {},

	// ��ȡ����
	getData : function() {
		$.ajax({
			url : ajaxSub.url,
			type : 'GET',
			async : true,
			data : ajaxSub.postData,
			contentType: "application/json;charset=utf-8",
			dataType : 'json',
			success : function(data) {
				this.rvFunc(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
			}
		});
	},
	// �������
	addOrUpdateData : function() {
		$.ajax({
			url : ajaxSub.url,
			type : 'post',
			async : true,
			data : JSON.stringify(ajaxSub.postData),
			contentType: "application/json;charset=utf-8",
			dataType : 'json',
			beforeSend:function(XMLHttpRequest){
				$.each(ajaxSub.headers, function (key, value) {
					XMLHttpRequest.setRequestHeader(key, value);
				})
			},
			success : function(data) {
				ajaxSub.rvFunc(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				alert(XMLHttpRequest.responseJSON);
			}
		});
	},

	delData : function() {
		alert(ajaxSub.url);
		$.ajax({
			url : ajaxSub.url,
			type : 'delete',
			async : false,
			dataType : 'json',
			beforeSend:function(XMLHttpRequest){
				$.each(ajaxSub.headers, function (key, value) {
					XMLHttpRequest.setRequestHeader(key, value);
				})
			},
			success : function(data) {
				ajaxSub.rv = data;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				alert(XMLHttpRequest.responseJSON);
			}
		});
	},

	// ģ����ѯ
	listData : function() {
		$.ajax({
			url : ajaxSub.url,
			type : 'PATCH',
			async : true,
			data : {
				_method : 'PATCH',
				name : '����'
			},
			dataType : 'json',
			success : function(data) {
				alert(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("ERROR");
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
	}
};

/**
 * @describe ���д�һ���µĴ���
 * @param url | ����
 * @param name | ��������
 * @param iWidth | ���ڳ���
 * @param iHeight | ���ڸߵ�
 * @since v1.2
 */
function openNewWindow(url, name, iWidth, iHeight) {
	var url; // ת����ҳ�ĵ�ַ;
	var name; // ��ҳ���ƣ���Ϊ��;
	var iWidth; // �������ڵĿ��;
	var iHeight; // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // ��ô��ڵĴ�ֱλ��;
	var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // ��ô��ڵ�ˮƽλ��;
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
 * @describe ��һ���µ��������ǩҳ
 * @param src | ����
 * @since v1.2
 */
function openNewWindowTab(src){
	$('body').append('<a href="" id="gotoNewTab" target="_blank"></a>');

	$('#gotoNewTab').attr('href', src);
	$('#gotoNewTab').get(0).click();
}

/**
 * @describe ��֤���ʱ�� 
 * @param obj|��ʱ���� 
 * @param number|��ʱʱ�� 
 * @param timer|��ʱ��������󣬳�ʼ����nullֵ���� 
 * @example settime(&("#btnId"), 90, null)
 * @since: v1.1.3 
 *
 */
function settime(obj, number, timer) {
	if (number == 0) {
		obj.removeAttribute("disabled");
		obj.text = "";
		obj.innerHTML = "��ȡ��֤��";
		number = 90;
		return;
	} else {
		obj.setAttribute("disabled", true);
		obj.innerHTML = "���Ժ�...(" + number + "��)";
		number--;
	}
	timer = setTimeout(function() {
		settime(obj, number, timer);
	}, 1000);
}

/**********************************�й�ͼƬ����ϴ�start(��δ����,���޸�)**************************************/
/* ######################## sine v1.2 ############################# */
/* ######################## alert v1.2.1 [1.�޸��޷�ɾ���ļ�bug] ############################# */

/*	ʹ�÷���example��
	[dom]:
    <div id="add_img">
    	��Ż���ͼƬ
	</div>
	<div style="display: none">
		<form id="uploadForm" method="post" enctype="multipart/form-data">
			<!-- �ļ��ϴ�input -->
			<input id="file1" type="file" onchange="preview(this,'60', false, 'add_img', 'count')" name="uploadImg" multiple="multiple" />
			<span id="fAliase"></span>
		</form>
		<span id="count" val="0" ></span> <!-- �����ϴ��ļ���ǰ���ͼƬ����������� -->
	</div>
	<div id="recordDelFileName" style="display: none">
		������ʱ��¼��ɾ�����ļ�
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
 * ��鵱ǰ�����ļ���Ϣ������ɸѡ����Ҫ�ϴ����ļ���Ϣ
 * @param formDomId ��Ҫ�ϴ���form����domId
 * @param delFileDomId ɾ�����ļ���ʱ��¼domId
 */
function checkFileGetNeedFile(formDomId, delFileDomId){
	var files = $("#"+formDomId).find("input[type='file']");
	var recordDelFileNames = $("#"+delFileDomId).text();
	
	var newFiles = [];
	var needFiles = [];
	
	//ɸѡ�����ļ�
	$.each(files,function(index,file){
		if(file.files.length > 0){//���ļ�
			$.each(file.files, function(index,file2){
				newFiles.push(file2);
			});
		}
	});
	
	console.log(newFiles);
	//ɸѡ����ɾ�����ļ�
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
 * �ϴ�ͼƬѡ��ͼƬ�����
 * @param file
 * @param imgSize ���ƻ���ͼƬ���
 * @param flag  �����Ƿ�׷��ͼƬ����
 * @param backDisplayImgDomId ͼƬ���Ե��Ǹ�dom��domId
 * @param countDomId ͳ��domId
 * @param delFileLogDomId ��ɾ���ļ�����ʱ��¼domId
 */
function preview(file, imgSize, flag, backDisplayImgDomId, countDomId, delFileLogDomId) {
	//console.log("count:" + count);
	//���ݰ�ȫ��ȡУ��
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
 * ���ͼƬ����ͼƬ
 * @param current_id ��ǰͼƬid
 * @param current_file ��ǰ�ļ���Ϣ
 * @param imgSize ����ͼƬ��С
 * @param flag �����Ƿ���ʾ��������
 * @param backDisplayImgDomId ͼƬ���Ե��Ǹ�dom��domId
 */
function addImg_callback_display(current_id, current_file, imgSize, flag, backDisplayImgDomId, delFileLogDomId){
	//console.log(backDisplayImgDomId);
	var fileName =  current_file.name;//�ļ�����
	var fileSize =  current_file.size;//�ļ���С
	var fileLastModifiedData = current_file.lastModifiedDate;//����޸�ʱ��
	
	var reader = new FileReader();
	reader.onload = function(evt) {
		//console.log(evt);
		//console.log(id);
		$("#"+ backDisplayImgDomId).append(
				function(){
						var html = 	'<div id="img_' + current_id + '" class="pull-left margin-left4 image-mask-box" >'
						+ '<img src="' + evt.target.result + '" height="'+imgSize+'" width="'+imgSize+'"  title = '+ fileName +' ' 
						+ 'onmouseover="imgMouseOver(\'span_delete_'+ current_id +'\')" />';
						//img ��ǩ����
						if(flag){
							html = html + '<input type="text" id="span_des_'+ current_id +'" class="image-mask-compile" value="�c��ݔ����Ϣ" '
							+ 'onfocus="imgAddAliase_focus(this)"'
							+ 'onchange="imgAddAliase_change(this)" />';
						}
						//�����ļ�������Ϣ��ǩ����
						html = html + '<span id="span_delete_'+ current_id +'" hidden="true" class="image-mask-delete" '
						+ 'onmouseout="imgMouseOut(this)" '
						+ 'onclick="imgDelete(this, \'' +fileName+ '\', \'' + delFileLogDomId + '\')">ɾ��</span>' 
						//ɾ�������ǩ����
						+ '</div>';
						return html;
					}
				);
	};
	reader.readAsDataURL(current_file);
}

/**
 * �������
 * @param which
 */
function imgMouseOver(which){
	//console.log(which);
	if($("#" + which).attr("hidden")){
		$("#" + which).attr("hidden", false);
	}
}

/**
 * ����Ƴ�
 * @param which
 */
function imgMouseOut(which){
	if(!$(which).attr("hidden")){
		$(which).attr("hidden", true);
	}
}

/**
 * ͼƬɾ��
 * @param which
 */
function imgDelete(which, fileName, delFileLogDomId){
	//���img
	var delete_id = $(which).attr("id").replace("span_delete_","img_");
	//console.log(delete_id);
	$("#" + delete_id).remove();
	
	//����ļ�����
	var input_id = $(which).attr("id").replace("span_delete_","fAliases");
	//console.log(input_id);
	if(!(typeof($("#" + input_id).val())  == 'undefined')){
		$("#" + input_id).remove();
	}
	
	//��¼ɾ��
	$("#"+delFileLogDomId).append(fileName);
}

/**
 * �޸��ļ�����
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
 * �޸��ļ������۽�
 * @param which
 */
function imgAddAliase_focus(which){
	$(which).val("");
}

/**********************************�й�ͼƬ�ϴ�end**************************************/

/**
 * �����������ʾ������,����ʹ��!
 */
function par_obj(auth_rdn,res,ptype,pip,acc_name){ //��������
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