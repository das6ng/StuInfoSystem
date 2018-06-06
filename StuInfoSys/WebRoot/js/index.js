/**
 * 
 */
function page_switch(element){
	//alert(element.id);
	var x = document.getElementsByClassName("pannel");
	//alert(x.length);
	for(var i=0; i<x.length; i++){
		x[i].style.display="none";
	}
	element.style.display="block";
}

function get_base_info(){
	//alert("baseinfo:  ");
	var gender = {"F":"女", "M":"男", "X":"其他"};
	$.ajax({
		type : "POST",
		url : "baseinfo",
		data :{},
		cache : false,
		dataType:"json",
		success : function(data) {
			if (data["status"] == "OK"){
				$("#info_id").text(data["id"]);
				$("#info_name").text(data["name"]);
				$("#info_birthday").text(data["birthday"]);
				$("#info_gender").text(gender[data["gender"]]);
				$("#info_dept").text(data["dept"]);
			}else if(data["ststus"] == "LOGIN"){
				window.location.href="login.html";
			}
			else{
				alert("获取信息失败！");
			}
		},
		error : function(xhr) {
			alert("error: " + xhr.responseText);
		}
	})
}

function showScores(data){
	var nature = {"compulsory":"必修", "elective":"选修", "X":"其他"};
	$("#score-table tr:gt(0)").remove();
	//$("#table1 tr:eq(0):not(:eq(0))").remove();
	//alert("scores:"+data);
	$.each(data,function(n,v) {
		var newRow = "<tr><td>"+v["id"]+
		             "</td><td>"+v["name"]+
		             "</td><td>"+v["weight"]+
		             "</td><td>"+nature[v["nature"]]+
		             "</td><td>"+v["semester"]+
		             "</td><td>"+v["grades"]+
		             "</td></tr>";
		$("#score-table tr:last").after(newRow);
	});
}

function get_score_info(){
	//alert("scoreinfo:  ");
	$.ajax({
		type : "POST",
		url : "scoreinfo",
		data :{},
		cache : false,
		dataType:"json",
		success : function(data) {
			if (data["status"] == "OK"){
				showScores(data["scores"]);
			}else if(data["ststus"] == "LOGIN"){
				window.location.href="login.html";
			}
			else{
				alert("获取成绩失败！");
			}
		},
		error : function(xhr) {
			alert("error: " + xhr.responseText);
		}
	})
}