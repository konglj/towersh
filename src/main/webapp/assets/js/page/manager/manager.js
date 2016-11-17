function user_city_change(){
	var city=$("#user_city");
	var area=$("#user_area");
	city_change(city,area);
	
}



function modifyadmin(){
	if($("#name").val()==''){
		alert("请输入姓名！");
		return;
	}
	var power=$("#power").val();
	if(power==2||power==4||power==11||power==12||power==13||power==14||power==15){
	var checkedsub = $('input[type="checkbox"][name="adminCitys"]:checked').length;
	if(checkedsub==0)
	{
		alert("请选择管理区域");
		return;
	}
	}else if(power==3){
		var adminjiedaos='';
		var checkedsub = $('input[type="checkbox"]:checked').length;
		if(checkedsub==0)
		{
			alert("请选择管理街道");
			return;
		}
		$('input[type="checkbox"]:checked').each(function() {
			adminjiedaos+=$(this).val()+',';
		});
		if(adminjiedaos.length>0){
		adminjiedaos=adminjiedaos.substring(0,adminjiedaos.length-1);
		$("#adminjiedaos").val(adminjiedaos);
		}
	}
	$.ajax({
		url : "updateadmin.html",
		type : 'post',
		dataType : "json",
		data : $("#form_modifyadmin").serialize(),
		success : function(data) {
			if (data.result == "success") {
				if (confirm("修改成功！")) {
				window.location='manager.html';
				}else{
					window.location='manager.html';
					
				}
			} else{
				alert("修改失败！");
			}
		}
	});
}

function addadmin(){
	if($("#adminid").val()==''){
		alert("请输入账号！");
		return;
	}
	if($("#name").val()==''){
		alert("请输入姓名！");
		return;
	}
	var power=$("#power").val();
	if(power==2||power==4||power==11||power==12||power==13||power==14||power==15){
		var checkedsub = $('input[type="checkbox"][name="adminCitys"]:checked').length;
		if(checkedsub==0)
		{
			alert("请选择管理区域");
			return;
		}
		}else if(power==3){
			var adminjiedaos='';
			var checkedsub = $('input[type="checkbox"]:checked').length;
			if(checkedsub==0)
			{
				alert("请选择管理街道");
				return;
			}
			$('input[type="checkbox"]:checked').each(function() {
				adminjiedaos+=$(this).val()+',';
			});
			if(adminjiedaos.length>0){
			adminjiedaos=adminjiedaos.substring(0,adminjiedaos.length-1);
			$("#adminjiedaos").val(adminjiedaos);
			}
		}
	$.ajax({
		url : "add_admin.html",
		type : 'post',
		dataType : "json",
		data : $("#form_addadmin").serialize(),
		success : function(data) {
			if (data.result == "success") {
				if (confirm("添加成功！")) {
					window.location='manager.html';
					}else{
						window.location='manager.html';
						
					}
			} else{
				alert(data.msg);
			}
		}
	});
}


function resetpwd(id){
	if (confirm("您确定要讲用户密码重置为111111吗！")) {
	$.ajax({
		url : "resetpwd.html",
		type : 'post',
		dataType : "json",
		data :{
			id:id
		},
		success : function(data) {
			if (data.result == "success") {
				alert("重置成功！");
			} else{
				alert("重置失败！");
			}
		}
	 });
	}
	
}

function del_admin(id){
	if (confirm("您确定要删除该管理员吗！")) {
	$.ajax({
		url : "del_admin.html",
		type : 'post',
		dataType : "json",
		data :{
			ids:id
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("删除成功！")) {
				window.location='manager.html';
				}else{
					window.location='manager.html';
				}
			} else{
				alert("删除失败！");
			}
		}
	});
	}
	
}

function del_admin_all(){
	$.ajax({
		url : "del_admin.html",
		type : 'post',
		dataType : "json",
		data :{
			ids:$("#selectadminids").val()
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("删除成功！")){
				window.location='manager.html';
			}else{
				window.location='manager.html';
			}
			} else{
				alert("删除失败！");
			}
		}
	});
	
}


function change_role(){
	var power=$("#power").val();
	if(power==3){
		$("#glcs").hide();
		$("#select_glqy").show();
		$("#glqy").show();
		
	}else if(power!=6&&power!=8){
		var select_area=$("#user_area");
		$("#glcs").show();
		$("#select_glqy").hide();
		$("#glqy").hide();
		
	}else {
		$("#select_glqy").hide();
		$("#glcs").hide();
		$("#glqy").hide();
    	$('input[type="checkbox"][name="adminCitys"]').each(function() {
			$(this).attr("checked", false);
		});
		
	}
}

function check_all_admin() {
	$('input[type="checkbox"][name="chk_list"]').each(function() {
		$(this).attr("checked", true);
	});
}
function downTip() {
	var selectid = '';
	$('input[type="checkbox"][name="chk_list"]:checked').each(function() {
		selectid += $(this).val() + ",";
	});
	if (selectid == '') {
		alert("请选择管理员！");
		return;
	}
	$("#selectadminids").val(selectid);
	$("#tip").fadeIn(200);

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
   }else{
		$("#"+name+"").attr("checked", false); 
   }  
}  



function city_change_admin(){
	
	var city = $("#admin_area");
	var jiedao=$("#panel_jiedao");
	city_change_admin_area(city,jiedao);
	
	
}

function search_manager(){
	
	$("#pageno").val(1);
	$("#form_manager").submit();
}
