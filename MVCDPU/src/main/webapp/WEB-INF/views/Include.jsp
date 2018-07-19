<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.css" >
	<script  src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js" ></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
 	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.blockUI/2.70/jquery.blockUI.min.js"></script>
</head>
<body>
<script type="text/javascript">
		var BASE_URL = "http://35.166.16.104:8080/MVCDPU/"
		//var BASE_URL = "http://localhost:8084/MVCDPU/"
		
		function unblockUI(){
			$.unblockUI();
		}
		function blockUI(){
			 $.blockUI({ css: {background: 'none', left:'45%', width: '120px',height: '120px',animation: 'spin 2s linear infinite',border: '16px solid #f3f3f3','border-top': '16px solid #3498db','border-radius': '50%'},baseZ: 999999 }); 
		}
</script>
</body>
<style type="text/css">
.input-group-addon {
    min-width:150px;
    text-align:left;
}
.pageHeading {
	font-size: 20px;
	font-style : italic;
	font-family: cursive;
	
}
@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
.blockUI > h1{
display: none;
}
</style>
</html>