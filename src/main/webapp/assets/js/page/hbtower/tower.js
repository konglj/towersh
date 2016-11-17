function tower_city_change() {
	var city = $("#tower_city");
	var area = $("#tower_area");
	city_change(city, area);

}

function addtower_city_change() {
	var city = $("#tower_city");
	var area = $("#tower_area");
	addtower_city_change(city, area);
}

function addtower_city_change(select_city, select_area) {
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
					location.href = "tower_no.html";
				}else{
					location.href = "tower_no.html";
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
				window.location.href = "tower_no.html";
			} else {
				alert(data.msg);

			}

		}
	});

}
function updateTower() {
	if (!validateForm())
		return;
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
					location.href = "tower_no.html";
				}else{
					location.href = "tower_no.html";
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

function tower_visible(visible) {

	$.ajax({
		type : "POST",
		url : "update_visible.html",
		data : {
			selecttoweids : $("#selecttoweids").val(),
			visible : visible
		},
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				$(".tip").fadeOut(0);
				if (confirm("操作成功")) {
					if (visible == 0) {
						location.href = "tower_yes.html";
					} else {
						location.href = "tower_no.html";

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
					location.href = "tower_no.html";
				}else{
					location.href = "tower_no.html";
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
