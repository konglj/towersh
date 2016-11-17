$().ready(function() {
	$("#form_addmessage").validate({
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

$().ready(function() {
	$("#form_editmessage").validate({
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

function addmessage(){
	if (!$("#form_addmessage").validate().form()) {
		return;
	}
	if($("#title").val()==''){
		alert("请输入标题！");
		return;
	}
	$.ajax({
		url : "add_message.html",
		type : 'post',
		dataType : "json",
		data : $("#form_addmessage").serialize(),
		success : function(data) {
			if (data.result == "success") {
				window.location='message.html';
			} else{
				alert("添加失败！");
			}
		}
	});
}

function editmessage(){
	if (!$("#form_editmessage").validate().form()) {
		return;
	}
	if($("#title").val()==''){
		alert("请输入标题！");
		return;
	}
	$.ajax({
		url : "updatemessage.html",
		type : 'post',
		dataType : "json",
		data : $("#form_editmessage").serialize(),
		success : function(data) {
			if (data.result == "success") {
				window.location='message.html';
			} else{
				alert("修改失败！");
			}
		}
	});
}

function del_message(id){
	if (confirm("您确定要删除该留言吗！")) {
	$.ajax({
		url : "del_message.html",
		type : 'post',
		dataType : "json",
		data :{
			id:id
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("删除成功！")) {
					window.location='message.html';
				}else{
					window.location='message.html';
				}
			} else{
				alert("删除失败！");
			}
		}
	});
	}
}
