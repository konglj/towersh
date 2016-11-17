 window.onload =function() {
	 $("[name='a_ht_img']").each(function(i,n){
		//alert($(this).attr("href"));
		 var file=$(this).attr("href");
		 var point = file.lastIndexOf("."); 
	       
	     var type = file.substr(point);
	     $(this).attr("download",$(this).attr("download")+type);
	});
 }

function tower_city_change(){
	var city=$("#tower_city");
	var area=$("#tower_area");
	city_change(city,area);
	
}


function user_city_change(){
	var city=$("#user_city");
	var area=$("#user_area");
	city_change(city,area);
	
}

function go_page(pageNo){
	$("#pageno").val(pageNo);
	  $("#form_order").submit();
	
}

function jump_page() {
	if($("#jumppage").val()==""||isNaN($("#jumppage").val())){
	return;
	}
	$("#pageno").val($("#jumppage").val());
	$("#form_order").submit();

}

function seach_order(){
	$("#pageno").val(1);
	$("#form_order").submit();
}

function order_sh(id,result,state){
	var info=$("#shinfo").val();
	var  yqcount=0;
	if(result==1&&(state==13||state==20||state==21)){
		yqcount=$("#yqcount").val();
		var yqmincount=$("#yqmincount").val();
		var yqmaxcount=$("#yqmaxcount").val();
		if(yqcount<=0||yqcount<yqmincount||yqcount>yqmaxcount){
			alert("请输入正确的延期天数！");
			return;
		}
			
	}
	if(result==0&&info==""){
		alert("请输入拒绝原因！");
		return;
	}
	
	
	$.ajax({
		url : "order_sh.html",
		type : 'post',
		dataType : "json",
		data : {
			orderid:id,
			result:result,
			shinfo:info,
			yqcount:yqcount
			
		},
		success : function(data) {
			if (data.result == "success") {
			if (confirm("审核成功！")) {
				window.location='order.html';
				}else{
					window.location='order.html';
				}
			} else{
				alert('审核失败！');
			}
				
		}
	});
}


function order_rent_ht(){
	var towerpowerprice=$("#towerpowerprice").val();
	if(towerpowerprice==''){
		alert('请输入电费单价！');
		return;
	}
		
	var towerpowert=$("#towerpowert").val();
	if(towerpowert==''){
		alert('请输入贴电费！');
		return;
	}
	var towerfactrentfee=$("#towerfactrentfee").val();
	if(towerfactrentfee==''){
		alert('请输入实际租金！');
		return;
	}
	var towerfactaddress=$("#towerfactaddress").val();
	if(towerfactaddress==''){
		alert('请输入实际建站地址！');
		return;
	}
	var toweryzname=$("#toweryzname").val();
	if(toweryzname==''){
		alert('请输入业主姓名！');
		return;
	}
	var toweryzphone=$("#toweryzphone").val();
	if(toweryzphone==''){
		alert('请输入业主联系方式！');
		return;
	}
	
	var ischeckht=$("#ischeckht").val();
	if(ischeckht==1){
		 var renthtid=$("#renthtid").val();
		 if(renthtid==undefined||renthtid==''){
			alert('请填写租赁合同编号！');
			return;
		}
		 if(renthtid.length<=1&&renthtid!=''){
			 swal("",'请正确填写租赁合同编号！');
				return;
			}
		
		
	}else if(ischeckht==2){
		 var rentht=$('#demo-files img').attr("src");
		 if(rentht==undefined||rentht==''){
			alert('请上传租赁合同文件！');
			return;
		}
	}
	

	
	$("#form_rent_ht").ajaxSubmit({
		type : "post",
		url : "order_rent_ht.html",
		dataType : "json",
		success : function(data) {

			if (data.result == 'success') {
				if (confirm("提交成功！")) {
					window.location.href = "order.html";
				}else{
					window.location.href = "order.html";
				}
			} else {
				alert("提交失败！");

			}

		}
	});

}

function order_three_ht(){
	
	/*
	var threeht=$("#threeht").val();
	if(threeht==''){
		alert('请上传三方合同！');
		return;
	}
	*/
	
	
	var towerfactfee=$("#towerfactfee").val();
	if(towerfactfee==''||towerfactfee==0){
		alert('请输入实际谈址佣金！');
		return;
	}
		
	
	$("#form_three_ht").ajaxSubmit({
		type : "post",
		url : "order_three_ht.html",
		dataType : "json",
		success : function(data) {

			if (data.result == 'success') {
				if (confirm("提交成功！")) {
					window.location.href = "order.html";
				}else{
					window.location.href = "order.html";
				}
			} else {
				alert("提交失败！");

			}

		}
	});

}


function order_three_sh(id,result){
	var info=$("#shinfo").val();
	if(result==0&&info==""){
		alert("请填写审核信息！");
		return;
	}
	$.ajax({
		url : "order_three_sh.html",
		type : 'post',
		dataType : "json",
		data : {
			shinfo:info,
			orderid:id,
			result:result
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("审核成功！")) {
			    	window.location='order.html';
				}else{
					window.location='order.html';
				}
			} else{
				alert('审核失败');
			}
				
		}
	});

}

function tj_dxfwxy(){
	if($("#dxfwxyfile").val()==""){
		alert("请上传单项服务协议");
		return;
	}
	if($("#towerprojectno").val()==""){
		alert("请填写项目编号");
		return;
	}
	if($("#towerprojectname").val()==""){
		alert("请填写项目名称");
		return;
	}
	
	$("#form_fee").ajaxSubmit({
		type : "post",
		url : "order_fee.html",
		dataType : "json",
		success : function(data) {

			if (data.result == 'success') {
				if (confirm("申请成功！")) {
					window.location.href = "order.html";
				}else{
					window.location.href = "order.html";
				}
			} else {
				alert("申请失败！");

			}

		}
	});
	
	
}

function fee_sh(id,result){
	var info=$("#shinfo").val();
	if(result==0&&info==""){
		alert("请填写审核信息！");
		return;
	}
	$.ajax({
		url : "order_fee_sh.html",
		type : 'post',
		dataType : "json",
		data : {
			shinfo:info,
			orderid:id,
			result:result
		},
		success : function(data) {
			if (data.result == "success") {
				if (confirm("审核成功！")) {
			    	window.location='fee_sh.html';
				}else{
					window.location='fee_sh.html';
				}
			} else{
				alert('审核失败');
			}
				
		}
	});
}

//交单拒接、 合同拒绝
function  order_shjj(id){
	var info=$("#shinfo").val();
	if(info==""){
		alert("请填写审核信息！");
		return;
	}
	 $.ajax({
			url : "order_shjj.html",
			type : 'post',
			dataType : "json",
			data : {
				orderid:id,
				shinfo : info
			},
			success : function(data) {
				if (data.result == "success") {
					if (confirm("审核成功！")) {
						window.location.href = "order.html";
					}else{
						window.location.href = "order.html";
					}
				} else{
					alert("审核失败！");
					
				}
					
			}
		});

}

//评价订单

function order_pj(orderid){
	var info=$("#admincontent").val();
	if(info==""){
		alert("请填写评价信息！");
		return;
	}
	 $.ajax({
			url : "order_evaluate.html",
			type : 'post',
			dataType : "json",
			data : {
				orderid:orderid,
				admincontent : info
			},
			success : function(data) {
				if (data.result == "success") {
					if (confirm("评价成功！")) {
						window.location.href = "order.html";
					}else{
						window.location.href = "order.html";
					}
				} else{
					alert("评价失败！");
					
				}
					
			}
		});

}





/*
function tj_first_fee(){
	var htno=$("#htno").val();
	if(htno==""){
		alert("请输入合同编号！");
		return;
	}
	if($("#firstfile").val()==""){
		alert("请输入选择合同扫描件！");
		return;
	}
	//firstfile
	var endfee=$("#endfee").val();
	if(Number(endfee)<=0||$("#firstfee").val()==""){
		alert("请输入正确的首付金额！");
		return;
	}
	$("#form_first_fee").ajaxSubmit({
		type : "post",
		url : "order_first_fee.html",
		dataType : "json",
		success : function(data) {

			if (data.result == 'success') {
				if (confirm("申请成功！")) {
					window.location.href = "order.html";
				}else{
					window.location.href = "order.html";
				}
			} else {
				alert("申请失败！");

			}

		}
	});

}

function tj_end_fee(){
	if($("#endfile").val()==""){
		alert("请输入选择验收报告！");
		return;
	}
	$("#form_end_fee").ajaxSubmit({
		type : "post",
		url : "order_end_fee.html",
		dataType : "json",
		success : function(data) {

			if (data.result == 'success') {
				if (confirm("申请成功！")) {
					window.location.href = "order.html";
				}else{
					window.location.href = "order.html";
				}
			} else {
				alert("申请失败！");

			}

		}
	});

}
*/



function yq(id){
	$.ajax({
		url : "order_yq.html",
		type : 'post',
		dataType : "json",
		data : {
			orderid:id
		},
		success : function(data) {
			if (data.result == "success") {
				window.location='order.html';
			} else{
				alert('审核失败');
			}
				
		}
	});
}


function  shjj_show(id){
	 $("#tip").fadeIn(200);
	 $("#select_order_id").val(id);
}

function  shjj(){
	var id= $("#select_order_id").val();
	 $.ajax({
			url : "order_shjj.html",
			type : 'post',
			dataType : "json",
			data : {
				orderid:id
			},
			success : function(data) {
				if (data.result == "success") {
					window.location='order.html';
				} else{
					 $("#tip").fadeOut(200);
					alert("拒绝失败！");
					
				}
					
			}
		});

}



function dc_orders(){
	location.href = "dc_orders.html?"+ $('#form_order').serialize();
	
}


function rent_ht_display(){
	
	if ($("#ischeckht").val()==0) { 
		$("#renthtid").val("");
		//$("#rentht").val("");
		//$("#renthtid").attr("readonly","readonly");
	//	$("#file").attr("disabled","disabled");
		$("#tr_htid").attr("hidden","hidden");
		$("#tr_htimg").attr("hidden","hidden");
		 $('#demo-files').empty();
	} else if($("#ischeckht").val()==1){
		//$("#file").attr("disabled","disabled");
		 $('#demo-files').empty();
		 //$("#renthtid").removeAttr("readonly","");
		 $("#tr_htid").removeAttr("hidden");
		$("#tr_htimg").attr("hidden","hidden");
	} else{
		$("#renthtid").val("");
		// $("#renthtid").attr("readonly","readonly");
		//$("#file").removeAttr("disabled","");
		 $("#tr_htimg").removeAttr("hidden");
			$("#tr_htid").attr("hidden","hidden");
	}
}


function del_old_img(type){
	if(type==0){
		 $('#demo-files').empty();
		 $('#demo-files').html('<span class="demo-note"></span>');
	}
	if($("#fileurl").val()=='')
		return;
	  $.ajax({
		 	url : "del_old_img.html",
			type : 'post',
			dataType : "json",
			data : {
			oldimg : $("#fileurl").val()

		},
		error : function(request) {

		},
		success : function(data) {
		

		}
	});
	
}
