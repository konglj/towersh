function city_change(select_city,select_area){
	var city=select_city.val();
	if(city==""){
		select_area.empty();
		select_area.append("<option value='0'>全部</option>"); 
	}else{
		$.ajax({
			url : "../area/get_area.html",
			type : 'post',
			dataType : "json",
			data : {cityid:city},
			success : function(data) {
				if (data.result == "success") {
					select_area.empty();
					select_area.append("<option value='0'>全部</option>"); 
					$.each(data.msg, function(i, item){  
						select_area.append("<option value='"+item.id+"'>"+item.areaname+"</option>"); 
						});  
				} else{
				}
					
			}
		});
	
	}
	
}



function city_change_no_all(select_city,select_area){
	var city=select_city.val();
	if(city==""){
		select_area.empty();
	}else{
		$.ajax({
			url : "../area/get_area.html",
			type : 'post',
			dataType : "json",
			data : {cityid:city},
			success : function(data) {
				if (data.result == "success") {
					select_area.empty();
					$.each(data.msg, function(i, item){  
						select_area.append("<option value='"+item.id+"'>"+item.areaname+"</option>"); 
						});  
				} else{
				}
					
			}
		});
	
	}
	
}



function city_change_g(select_city,select_area){
	var city=select_city.val();
	if(city==""){
		select_area.empty();
		select_area.append("<option value='0'>全部</option>"); 
	}else{
		$.ajax({
			url : "area/get_area.html",
			type : 'post',
			dataType : "json",
			data : {cityid:city},
			success : function(data) {
				if (data.result == "success") {
					select_area.empty();
					select_area.append("<option value='0'>全部</option>"); 
					$.each(data.msg, function(i, item){  
						select_area.append("<option value='"+item.id+"'>"+item.areaname+"</option>"); 
						});  
				} else{
				}
					
			}
		});
	
	}
	
}

function city_change_admin_area(select_city,panel_jiedao){
	var city=select_city.val();
	$.ajax({
		url : "../area/get_area.html",
		type : 'post',
		dataType : "json",
		data : {cityid:city},
		success : function(data) {
			if (data.result == "success") {
				panel_jiedao.empty();
				$.each(data.msg, function(i, item){  
					var t=i+1;
					
					 if (t==1){
						 $("#check_area_name").html(item.cityname);
						 panel_jiedao.append("<div class='row' style='margin-left: 10px'>");
					 }	 
					
						 panel_jiedao.append("<div class='checkbox-custom  col-md-3 '><input type='checkbox' id='"+item.areaid+"' name='adminjiedaos'  onclick='set_check_all(this,'areas')' value='"+item.id+"'><label for='checkbox1'>"+item.areaname+"</label>");
						 panel_jiedao.append(" </div>");
						 if(t%4==0){
							 panel_jiedao.append(" </div>");
							 panel_jiedao.append(" <div class='row' style='margin-left:10px'>");
						 }
						 
						 
						
					 
				    
			});
		panel_jiedao.append(" </div>");
				
		}
		}
	});

	
}