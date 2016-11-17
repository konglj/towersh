function user_city_change() {
	var city = $("#user_city");
	var area = $("#user_area");
	city_change(city, area);

}

function go_page(pageNo) {
	$("#pageno").val(pageNo);
	$("#form_txmanage").submit();

}

function jump_page() {
	if($("#jumppage").val()==""||isNaN($("#jumppage").val())){
	return;
	}
	$("#pageno").val($("#jumppage").val());
	$("#form_txmanage").submit();

}

function tx_sh(id, result, fee) {
	var info = $("#shinfo").val();
	if (result == 2 && info == '') {
		alert("请填写拒绝理由！");
		return;
	}
	$.ajax({
		url : "tx_sh.html",
		type : 'post',
		dataType : "json",
		data : {
			txid : id,
			result : result,
			shinfo : info,
			fee : fee
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("处理成功！")) {
				window.location = 'tx.html';
				}else{
				window.location = 'tx.html';
				}
			} else {
				alert("处理失败！");
			}

		}
	});
}

function tx_dk(id, result, fee) {
	var info = $("#dkinfo").val();
	if(info==''){
		alert("请输入打款信息");
		return;
	}

	$.ajax({
		url : "tx_dk.html",
		type : 'post',
		dataType : "json",
		data : {
			txid : id,
			result : result,
			shinfo : info,
			fee : fee
		},
		success : function(data) {
			if (data.result == "success") {
				window.location = 'txmanage.html';
			} else {
				alert(data.msg);
			}
		}
	});
}

function dc_txs() {
	location.href = "dc_moneys.html?" + $('#form_tx').serialize();

}

function dc_txmanager() {
	location.href = "dc_txmanagers.html?" + $('#form_txmanage').serialize();

}


function pldr(){
	
		$("#form_all_add").ajaxSubmit({
			type : "post",
			url : "tx_dr.html.html",
			dataType : "json",
			success : function(data) {

				if (data.result == 'success') {
					if (confirm("批量导入成功！")) {
					window.location.href = "txmanage.html";
					}else{
						window.location.href = "txmanage.html";
					}
				} else {
					alert("批量添加失败！");

				}

			}
		});

	
}

//全部选中
function check_tx_all() {
	$('input[type="checkbox"][name="chk_list"]').each(function() {
		$(this).attr("checked", true);
	});
}
//未选择是提示
function downTip(del) {
	var selectid = '';
	$('input[type="checkbox"][name="chk_list"]:checked').each(function() {
		selectid += $(this).val() + ",";
	});
	if (selectid == '') {
		alert("请选择提现申请！");
		return;
	}
	$("#selecttxids").val(selectid);
	$("#tip").fadeIn(200);

}


function pl_tx_sh() {
	var selectid=$("#selecttxids").val();
	if (selectid == '') {
		alert("请选择提现申请！");
		return;
	}

	$.ajax({
		type : "POST",
		url : "pl_tx_sh.html",
		data : {
			selecttxids : $("#selecttxids").val()
			
		},
		dataType : 'json',
		error : function(request) {

		},
		success : function(data) {
			if (data.result == 'success') {
				$(".tip").fadeOut(0);
				if (confirm("操作成功")) {
					
						location.href = "tx.html";
					
				}
			} else {
				$(".tip").fadeOut(0);
				alert("操作失败");
			}
		}
	});

}






