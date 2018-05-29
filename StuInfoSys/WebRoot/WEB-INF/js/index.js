/**
 * 
 */
function login(){
	//{"username":"admin", "passwd":"123456"}
	$.ajax({
		type:"POST",
		url:"login",
		data:"qvPxZLwk8PrDh9KD8LO7X/lE6z7ay1hFcpPeDZ01Tq2tRRRn/A8sWsHE/72Bxyqe",
        contentType:"plain/text",
		cache:false,
		success:function(data){
			$("#result").html("success: <br/>"+data);
		},
		error:function(xhr){
			$("#result").html("error: "+xhr.responseText);
		}
	})
}