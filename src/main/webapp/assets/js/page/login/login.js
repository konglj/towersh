
$(function() {

	// session超时，跳出iframe返回登录页面
	if (window.top != null && window.top.document.URL != document.URL) {
		window.top.location = document.URL;
	}

	// 回车登录
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) { // enter 键
			login();
		}
	};
});
//reset
function reset(){
	$('input[type=text]').val('');
}
// 登录操作
function login() {
	if($("#userid").val()==''||$("#userpsd")==''){
		alert("用户名和密码不能为空！");
		return;
	}
		
		$.ajax({
			url : "go_login.html",
			type : 'post',
			dataType : "json",
			data : $("#form_login").serialize(),
			success : function(data) {
				if (data.result == "success") {
					window.location='main.html';
				} else{
					alert(data.msg);
				}
					
			}
		});
	

}
