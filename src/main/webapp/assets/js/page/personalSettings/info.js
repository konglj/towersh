
function validateform() {
	return $("#form_info").validate({
	    	rules:{
	    		name :{required : true,maxlength:20 },
	    		phone :{required : true, minlength:11, maxlength:11, digits:true}
	    	}
	}).form();
}

function validateform_pwd() {
	return $("#form_pwd").validate({
	    	rules:{
	    		
	    		oldpsd :{required : true, minlength:6, maxlength:15},
	    		newpsd :{required : true, minlength:6, maxlength:15},
	    		againpsd :{required : true, minlength:6, maxlength:15,equalTo:newpsd},
	    	}
	}).form();
}




function test() {
	alert("check erro");
//	$.ajax({
//		type : "POST",
//		url : "info.html",
//		data : $('#form_info').serialize(),
//		dataType : 'json',
//		error : function(request) {
//		
//		},
//		success : function(data) {
//			if (data.result == 'success') {
//				if(confirm("修改成功")){location.href = "main.html";}
//			} else {
//				alert("修改失败");
//			}
//		}
//	});
}

function updateinfo(){
	if (!validateform())
		return;
	$.ajax({
		type : "POST",
		url : "updateinfo.html",
		data : $('#form_info').serialize(),
		dataType : 'json',
		error : function(request) {
		
		},
		success : function(data) {
			if (data.result == 'success') {
				alert("修改成功！");
			} else {
				alert("修改失败！");
			}
		}
	});
}


function updatePwd(){
	if (!validateform_pwd())
		return;
	$.ajax({
		type : "POST",
		url : "updatePwd.html",
		data : $('#form_pwd').serialize(),
		dataType : 'json',
		error : function(request) {
		
		},
		success : function(data) {
			if (data.result == 'success') {
				alert("修改成功！");
			} else {
				alert(data.msg);
			}
		}
	});
}



