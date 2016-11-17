
function go_page(pageNo) {
	$("#pageno").val(pageNo);
	$("#form_tx").submit();

}
function jump_page() {
	if($("#jumppage").val()==""||isNaN($("#jumppage").val())){
	return;
	}
	$("#pageno").val($("#jumppage").val());
	$("#form_tx").submit();

}