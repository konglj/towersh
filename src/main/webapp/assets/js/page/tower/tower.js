function tower_city_change() {
	var city = $("#tower_city");
	var area = $("#tower_area");
	city_change(city, area);

}


function tower_city_change_no_all() {
	var city = $("#tower_city");
	var area = $("#tower_area");
	city_change_no_all(city, area);

}

function addtower_city_change() {
	var city = $("#tower_city");
	var area = $("#tower_area");
	addtower_city_change(city, area);
}

function addtower_city_change_(select_city, select_area) {
	var city = select_city.val();
	if (city == "") {
		select_area.empty();
		select_area.append("<option value='0'>没有</option>");
	} else {
		$.ajax({
			url : "../area/get_area.html",
			type : 'post',
			dataType : "json",
			data : {
				cityid : city
			},
			success : function(data) {
				if (data.result == "success") {
					select_area.empty();
					$.each(data.msg, function(i, item) {
						select_area.append("<option value='" + item.id + "'>"
								+ item.areaname + "</option>");
					});
				} else {
				}
			}
		});
	}
}

function go_page(pageNo) {
	$("#tower_pageindex").val(pageNo);
	$("#form_tower_yes").submit();

}
function jump_page() {
	if($("#jumppage").val()==""||isNaN($("#jumppage").val())){
	return;
	}
	$("#tower_pageindex").val($("#jumppage").val());
	$("#form_tower_yes").submit();

}


function search_tower_yes(){
	$("#tower_pageindex").val(1);
	$("#form_tower_yes").submit();
}



function validateForm() {
	return $("#form_tab1").validate({
		rules : {
			towername : {
				required : true
			},
			tower_city : {
				required : true
			},
			tower_area : {
				required : true
			},
			toweraddress : {
				required : true
			},
			towerlevel : {
				required : true
			},
			
			towerinfo : {
				required : true
			},
			towerfee : {
				required : true,
				number : true
			},
			towerentfee:{
				required : true,
				number : true
			},
			managerphone : {
				required : true,
				minlength : 11,
				maxlength : 11,
				digits : true
			},
			managername : {
				required : true
			},
			towerj : {
				required : true,
				number : true
			},
			towerw : {
				required : true,
				number : true
			},
			towertype : {
				required : true
			},
			towerstore:{
				required : true
			},
		
			towerlarge : {
				required : true,
				number : true
			}
		}
	}).form();
}

function addTower() {
	if (!validateForm())
		return;
	$.ajax({
		type : "POST",
		url : "applyaddtower.html",
		data : $('#form_tab1').serialize(),
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				if (confirm("添加成功")) {
					location.href = "tower_dsh.html?isback=0";
				}else{
					location.href = "tower_dsh.html?isback=0";
				}
			} else {
				alert("添加失败");
			}
		}
	});
}

function all_add() {
	$("#form_all_add").ajaxSubmit({
		type : "post",
		url : "upload_towers.html",
		dataType : "json",
		success : function(data) {

			if (data.result == 'success') {
				location.href = "tower_dsh.html?isback=0";
			} else {
				alert(data.msg);

			}

		}
	});

}
function updateTower() {
	if (!validateForm())
		return;
	var page=$("#page").val();
	var h;
	//page=2;
	if(page==1){
		h="tower_dsh.html";
	}else if(page==0){
		h="tower_no.html";
	}else if(page==2){
		h="tower_yes.html";
	}
	$.ajax({
		type : "POST",
		url : "update_tower.html",
		data : $('#form_tab1').serialize(),
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				if (confirm("修改成功")) {
					location.href =h;
				}else{
					location.href =h;
				}
			} else {
				alert("修改失败");
			}
		}
	});
}

function check_all() {
	$('input[type="checkbox"][name="chk_list"]').each(function() {
		$(this).attr("checked", true);
	});
}

function downTip(del) {
	var selectid = '';
	$('input[type="checkbox"][name="chk_list"]:checked').each(function() {
		selectid += $(this).val() + ",";
	});
	if (selectid == '') {
		alert("请选择站点！");
		return;
	}
	$("#selecttoweids").val(selectid);
	$("#tip").fadeIn(200);

}

function delTip() {
	var selectid = '';
	$('input[type="checkbox"][name="chk_list"]:checked').each(function() {
		selectid += $(this).val() + ",";
	});
	if (selectid == '') {
		alert("请选择站点！");
		return;
	}
	$("#selecttoweids").val(selectid);
	$("#tip_del").fadeIn(200);

}


function shTip() {
	var selectid = '';
	$('input[type="checkbox"][name="chk_list"]:checked').each(function() {
		selectid += $(this).val() + ",";
	});
	if (selectid == '') {
		alert("请选择站点！");
		return;
	}
	$("#selecttoweids").val(selectid);
	$("#tip_sh").fadeIn(200);

}



function tower_visible(visible,page,source) {

	$.ajax({
		type : "POST",
		url : "update_visible.html",
		data : {
			selecttoweids : $("#selecttoweids").val(),
			visible : visible,
			source:source
		},
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				$(".tip").fadeOut(0);
				if (confirm("操作成功")) {
					if (page == 0) {
						location.href = "tower_dsh.html";
					}else if(page==1){
						location.href = "tower_no.html";
					}
					else if(page==2){
						location.href = "tower_yes.html";
					}
				}
			} else {
				alert("操作失败");
			}
		}
	});

}

function tower_del() {

	$.ajax({
		type : "POST",
		url : "tower_del.html",
		data : {
			selecttoweids : $("#selecttoweids").val()
		},
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				$(".tip").fadeOut(0);
				if (confirm("操作成功")) {
					location.href = "tower_dsh.html";
				}else{
					location.href = "tower_dsh.html";
				}
			} else {
				alert("操作失败");
			}
		}
	});

}
function dc_tower_order_info(towerid){
	location.href = "dc_tower_order_info.html?towerid="+towerid;
}
function dc_towers_yes() {
	
	location.href = "dc_towers.html?"+ $('#form_tower_yes').serialize();


}

function dc_towers_no() {
	
	$.ajax({
		type : "POST",
		url : "applyaddtower.html",
		data : $('#form_tab1').serialize(),
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				if (confirm("添加成功")) {
					location.href = "tower_no.html";
				}
			} else {
				alert("添加失败");
			}
		}
	});

}


//公示
function towers_gs(){
	if (confirm("公示时间为从此刻开始到第一个13：00结束，是否确认公示？")) {
	
	$.ajax({
		type : "POST",
		url : "tower_gs.html",
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				alert("公示成功！");
			} else {
				alert("公示失败！");
			}
		}
	});
	}
	
	
}

function tower_edit(towerid){
	$("#towerid").val(towerid);
	$.ajax({
		type : "POST",
		url : "tower_edit.html",
		dataType : 'json',
		data : $('#form_tower_dsh').serialize(),
		async : false,
		error : function(request) {

		},
		success : function(data) {
			
		}
	});
	}
	
	



