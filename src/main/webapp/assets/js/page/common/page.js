function change_page(name,pageindex){
	$("#"+pageindex).val(1);
	$("#"+name).submit();
}