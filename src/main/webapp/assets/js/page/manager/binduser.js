
function go_page(pageNo){
	$("#pageno").val(pageNo);
	$("#form_binduser").submit();
	
}
function jump_page() {
	if($("#jumppage").val()==""||isNaN($("#jumppage").val())){
	return;
	}
	$("#pageno").val($("#jumppage").val());
	$("#form_binduser").submit();

}

function binduser(wxid,adminid){
	$.ajax({
		url : "binduser.html",
		type : 'post',
		dataType : "json",
		data :{
		  adminid:adminid,
		  wxid:wxid
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("绑定成功！")) {
					window.location='manager.html';
					}else{
						window.location='manager.html';
					}
			} else{
				alert("绑定失败！");
			}
		}
	});
}

function unbinduser(adminid){
	$.ajax({
		url : "unbinduser.html",
		type : 'post',
		dataType : "json",
		data :{
		  adminid:adminid
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("解绑成功！")) {
					window.location='manager.html';
					}else{
						window.location='manager.html';
					}
			} else{
				alert("解绑失败！");
			}
		}
	});
}