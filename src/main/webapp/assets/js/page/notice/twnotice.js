$().ready(function() {
	$("#form_save_twnotice").validate({
		rules : {

			title : {
				required : true,
				rangelength:[2,50]

			},
			content : {
				required : true

			}
		}
	});
});
function go_page(pageNo) {
	$("#pageno").val(pageNo);
	$("#form_order").submit();

}

function savenotice(){
	if (!$("#form_save_twnotice").validate().form()) {
		return;
	}
	if($("#title").val()==''){
		alert("请输入标题！");
		return;
	}
	if(editor.text()==''){
		alert("请输入内容！");
		return;
	}

	$.ajax({
		url : "save_notice.html",
		type : 'post',
		dataType : "json",
		data : {
			id:$("#id").val(),
			title:$("#title").val(),
			content:editor.html()
		},
		success : function(data) {
			if (data.result == "success") {
				window.location='notice.html';
			} else{
				alert("添加失败！");
			}
		}
	});
	
}

function editnotice(){
	if($("#title").val()==''){
		alert("请输入标题！");
		return;
	}
	$.ajax({
		url : "updatenotice.html",
		type : 'post',
		dataType : "json",
		data : $("#form_editnotice").serialize(),
		success : function(data) {
			if (data.result == "success") {
				window.location='notice.html';
			} else{
				alert("修改失败！");
			}
		}
	});
}

function del_notice(id){
	if (confirm("您确定要删除该公告吗！")) {
	$.ajax({
		url : "del_notice.html",
		type : 'post',
		dataType : "json",
		data :{
			id:id
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("删除成功！")) {
					window.location='notice.html';
				}else{
					window.location='notice.html';
				}
			} else{
				alert("删除失败！");
			}
		}
	});
	}
}

function tip_show(id){
	
	$(".tip").fadeIn(200);
}

function check_all(ob,name) {
	 if ($(ob).attr("checked")) {  
			$('input[type="checkbox"][name="'+name+'"]').each(function() {
				$(this).attr("checked", true);
			});
	    } else {  
	    	$('input[type="checkbox"][name="'+name+'"]').each(function() {
				$(this).attr("checked", false);
			});
	    }  
}

//子复选框的事件  
function set_check_all(ob,name){  
	
    //当没有选中某个子复选框时，SelectAll取消选中  
    if (!$(ob).attr("checked")) {  
    	$("#"+name+'').attr("checked", false);
    	return;
    }  
    var chsub = $('input[type="checkbox"][name="'+$(ob).attr("name")+'"]').length; //获取subcheck的个数  
    var checkedsub = $('input[type="checkbox"][name="'+$(ob).attr("name")+'"]:checked').length; //获取选中的subcheck的个数  
    if (checkedsub == chsub) {  
    	$("#"+name+"").attr("checked", true);
    }  
}  



function send(){
	$.ajax({
		url : "send.html",
		type : 'post',
		dataType : "json",
		data :$("#send_notice_form").serialize(),
		success : function(data) {
			if (data.result == "success") {
				if (confirm("发送成功！")) {
					window.location='notice.html';
				}else{
					window.location='notice.html';
				}
			} else{
				alert(data.msg);
			}
		}
	});
	}
