

$().ready(function() {
	$("#form_addnotice").validate({
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
	$("#form_editnotice").validate({
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

function addnotice(){
	if (!$("#form_addnotice").validate().form()) {
		return;
	}
	if($("#title").val()==''){
		alert("请输入标题！");
		return;
	}
	$.ajax({
		url : "add_notice.html",
		type : 'post',
		dataType : "json",
		data : $("#form_addnotice").serialize(),
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
	if (!$("#form_editnotice").validate().form()) {
		return;
	}
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
	if (confirm("您确定要删除该留言吗！")) {
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
