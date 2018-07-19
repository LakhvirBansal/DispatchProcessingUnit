<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Driver</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.css" >
	<script  src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js" ></script>
	
	
	<jsp:include page="Include.jsp"></jsp:include>
 <script src="<c:url value="/resources/validations.js" />"></script>
<style type="text/css">
.modal-dialog {
  width: 98%;
  height: 100%;
  padding: 0;
}

.modal-content {
  height: auto;
  min-height: 100%;
  border-radius: 5;
}
textarea{ 
  width: 100%; 
  min-width:100%; 
  max-width:100%; 

  height:360px; 
  min-height:360px;  
  max-height:360px;
}
</style>
<script type="text/javascript">

function navigate() {
	var flag = $("#addUpdateFlag").val();
	if(flag == 'add') {
		createDriver('savedriver','POST');
	} else if(flag == 'update') {
		createDriver('updatedriver','PUT');			
	}
}
function createDriver(urlToHit, methodType){
	 
	if(!check()){
		 return false;
	 }
	var driverCode = $("#driverCode").val();
 	var email = $("#email").val();
 	var firstName = $("#firstName").val();
 	var home = $("#home").val();
 	var fax = $("#fax").val();
 	var lastName = $("#lastName").val();
 	var cellular = $("#cellular").val();
 	var pager = $("#pager").val();
 	var address = $("#address").val();
 	var city = $("#city").val();
 	var zip = $("#zip").val();
 	var unit = $("#unit").val();
 	var zip = $("#zip").val();
 	var province = $("#province").val();
	var divisionId = $('#divisionId :selected').val();
	var terminalId = $('#terminalId :selected').val();
	var countryId = $('#countryId :selected').val();
	var stateId = $('#stateId :selected').val();
	var categoryId = $('#categoryId :selected').val();
	var roleId = $('#roleId :selected').val();
	var statusId = $('#statusId :selected').val();
	var driverClassId = $('#classId :selected').val();
	var driverId;
	if(methodType == 'PUT') {
		driverId = $("#driverid").val();
	}
	blockUI()
	  $.ajax({url: BASE_URL+ urlToHit,
		      type:"POST",
		      data:{
		    	driverCode:driverCode,
		    	email:email,
		    	firstName:firstName,
		    	home:home,
		    	faxNo:fax,
		    	lastName:lastName,
		    	cellular:cellular,
		    	pager:pager,
		    	address:address,
		    	divisionId:divisionId,
		    	unit:unit,
		    	terminalId:terminalId,
		    	countryId:countryId,
		    	stateId:stateId,
		    	categoryId:categoryId,
		    	roleId:roleId,
		    	postalCode:zip,
		    	city:city,
		    	statusId:statusId,
		    	driverClassId:driverClassId,
		    	driverid:driverId
		      },
		      success: function(result){
	        try{
	        	$('#myModal').modal('toggle');
	        	var list = result.resultList;
				fillDriverData(list);

		        toastr.success(result.message, 'Success!')
			} catch(e){
				unblockUI()
				toastr.error('Something went wrong', 'Error!')
			}
	  },error:function(result){
		  try{
			    unblockUI()
			  	var obj = JSON.parse(result.responseText);
			  	toastr.error(obj.message, 'Error!')
			  }catch(e){
				  unblockUI()
				  toastr.error('Something went wrong', 'Error!')
			  }
	  }}).done(function(){
		  unblockUI();
	  });
	  return true;
}

function fillDriverData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
			var driverCode = "";
    		if(obj.driverCode != null) {
    			driverCode = obj.driverCode;
    		}
    		var firstName = "";
    		if(obj.firstName != null) {
    			firstName = obj.firstName;
    		}
    		var lastName = "";
    		if(obj.lastName != null) {
    			lastName = obj.lastName;
    		}
    		var address = "";
    		if(obj.address != null) {
    			address = obj.address;
    		}
    		var unit = "";
    		if(obj.unit != null) {
    			unit = obj.unit;
    		}
    		var city = "";
    		if(obj.city != null) {
    			city = obj.city;
    		}
    		var stateName = "";
    		if(obj.stateName != null) {
    			stateName = obj.stateName;
    		}
    		var faxNo = "";
    		if(obj.faxNo != null) {
    			faxNo = obj.faxNo;
    		}
    		var cellular = "";
    		if(obj.cellular != null) {
    			cellular = obj.cellular;
    		}
    		var pager = "";
    		if(obj.pager != null) {
    			pager = obj.pager;
    		}
    		var email = "";
    		if(obj.email != null) {
    			email = obj.email;
    		}
    		
    		tableValue = tableValue + ("<td>"+(driverCode)+"</td>");
    		
    		tableValue = tableValue + ("<td>"+(firstName)+"</td>");
    		tableValue = tableValue + ("<td>"+(lastName)+"</td>");
    		tableValue = tableValue + ("<td>"+(address)+"</td>");
    		tableValue = tableValue + ("<td>"+(unit)+"</td>");
    		tableValue = tableValue + ("<td>"+(city)+"</td>");
    		tableValue = tableValue + ("<td>"+ (stateName)+"</td>");
    		tableValue = tableValue + ("<td>"+(faxNo)+"</td>");
    		tableValue = tableValue + ("<td>"+(cellular)+"</td>");
    		tableValue = tableValue + ("<td>"+(pager)+"</td>");
    		tableValue = tableValue + ("<td>"+(email)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.driverId)+"')>Update</a> / <a href='#' onclick=deleteDriver('"+(obj.driverId)+"')>Delete</a></td>";
    		 tableValue = tableValue + ("</tr>");
		}
		$("#driverData").html(tableValue);
	} else {
		$("#driverData").html("No records found.");		
	}
}
	function deleteDriver(driverId){
		 
		blockUI()
		  $.ajax({url: BASE_URL + "deletedriver/" + driverId,
			      type:"GET",
			      success: function(result){
		    	  try{	
						var list = result.resultList;
						fillDriverData(list);
						
		    		  toastr.success(result.message, 'Success!')
				  }catch(e){
					  unblockUI()
					toastr.error('Something went wrong', 'Error!')
				  }
		  },error:function(result){
			  try{
					  unblockUI();
				  	var obj = JSON.parse(result.responseText);
				  	toastr.error(obj.message, 'Error!')
				  }catch(e){
					  toastr.error('Something went wrong', 'Error!')
				  }
		  }}).done(function(){
			  unblockUI();
		  });
		  return true;
	}
	
	$(document).ready(function(){
		/* $('#btnSave').click(function(){
			$('#frm1').submit();
		}); */
		$('#btnSearch').click(function(){
			$("#frmSearch").change(function() {
			  $("#frmSearch").attr("action", "showques");
			});
			$('#frmSearch').submit();
		});
	});
	
	function getStates() {
		
		var countryId = $('#countryId :selected').val();
		
		blockUI()
		$.get("states/" + countryId, function(response) {
	        
			unblockUI()
	    	document.getElementById("stateId").innerHTML = "<option value='0'>Please Select</option>";
            if(response.length > 0) {
	            var state = document.getElementById("stateId");
            	for(var i = 0;i < response.length;i++) {
            		state.options[state.options.length] = new Option(response[i].stateCode);
            		state.options[i+1].value = response[i].stateId;
	            }
            } else {
            	document.getElementById("stateId").innerHTML = "<option value='0'>Please Select</option>";
            }
		});
	}
	
	function changeStateLabel() {
		
		var country = $('#countryId :selected').text();
		if(country == 'United States') {
			$("#zipLabel").text("Zip");
			$("#zip").attr("placeholder","Enter Zip");
			$("#stateLabel").text("State");
		} else if(country == 'Canada'){
			$("#zipLabel").text("PostalCode");
			$("#zip").attr("placeholder","Enter PostalCode");
			$("#stateLabel").text("Province");
		}
	}
</script>
<script type="text/javascript">
	function checkFlag(field) {
		document.getElementById("addUpdateFlag").value = field;
		if(field == 'update') {
			//document.getElementById("frm1").action = "updatedriver";
			document.getElementById("btnSave").value = "Update";
			$("#modelTitle").html("Edit Driver");
		}
		else if(field == 'add') {
			//$("#cke_1_contents").html('');
			//$(":text").val("");
       		//document.getElementById('categoryId').selectedIndex = 0;
			document.getElementById("btnSave").value = "Save";
			$("#modelTitle").html("Add Driver");
		} else if (field == 'search') {
			/* document.getElementById("frm1").method = "GET";
			document.getElementById("frm1").action = "showques";
			document.getElementById("frm1").submit(); */
		}
	}
</script>
<script type="text/javascript">
        function onClickMethodQuestion(quesId){
        	emptyMessageDiv();
        	clearAll();
        	if(quesId == 0) {
        		blockUI()
	        	$.get("driver/getopenadd", function(data) {
	        		unblockUI()
		            var status = document.getElementById("statusId");
		            for(var i = 0;i < data.statusList.length;i++) {
		            	status.options[status.options.length] = new Option(data.statusList[i].status);
		            	status.options[i+1].value = data.statusList[i].id;
		            } 
		            
		            var division = document.getElementById("divisionId");
		            for(var i = 0;i < data.divisionList.length;i++) {
		            	division.options[division.options.length] = new Option(data.divisionList[i].divisionName);
		            	division.options[i+1].value = data.divisionList[i].divisionId;
		            }
		            
		            var terminal = document.getElementById("terminalId");
		            for(var i = 0;i < data.terminalList.length;i++) {
		            	terminal.options[terminal.options.length] = new Option(data.terminalList[i].terminalName);
		            	terminal.options[i+1].value = data.terminalList[i].terminalId;
		            }
		            
		            var category = document.getElementById("categoryId");
		            for(var i = 0;i < data.categoryList.length;i++) {
		            	category.options[category.options.length] = new Option(data.categoryList[i].name);
		            	category.options[i+1].value = data.categoryList[i].categoryId;
		            }
		            
		            var role = document.getElementById("roleId");
		            for(var i = 0;i < data.roleList.length;i++) {
		            	role.options[role.options.length] = new Option(data.roleList[i].typeName);
		            	role.options[i+1].value = data.roleList[i].typeId;
		            }
		            
		            var driverClass = document.getElementById("classId");
		            for(var i = 0;i < data.driverClassList.length;i++) {
		            	driverClass.options[driverClass.options.length] = new Option(data.driverClassList[i].typeName);
		            	driverClass.options[i+1].value = data.driverClassList[i].typeId;
		            }
		            
		            var country = document.getElementById("countryId");
		            for(var i = 0;i < data.countryList.length-1;i++) {
		            	country.options[country.options.length] = new Option(data.countryList[i].countryName);
		            	country.options[i+1].value = data.countryList[i].countryId;
		            }
		            
		            changeStateLabel();
		            getStates();
		        });
        	} else {
        		blockUI()

        		$.get("getdriver/driverId",{"driverId" : quesId}, function(data) {
        			unblockUI()

		            document.getElementById("driverid").value = data.driverId;
		            $("#driverCode").val(data.driverCode);
	            	$("#email").val(data.email);
	            	$("#firstName").val(data.firstName);
	            	$("#home").val(data.home);
	            	$("#fax").val(data.faxNo);
	            	$("#lastName").val(data.lastName);
	            	$("#cellular").val(data.cellular);
	            	$("#pager").val(data.pager);
	            	$("#address").val(data.address);
	            	$("#city").val(data.city);
	            	$("#zip").val(data.zip);
	            	$("#province").val(data.province);
	            	$("#unit").val(data.unit);
	            	$("#zip").val(data.postalCode);
	            	$("#province").val(data.pvs);
	            	
		            var division = document.getElementById("divisionId");
		            var divisionList = data.divisionList;
		            if(divisionList != null && divisionList.length > 0) {
			            for(var i = 0;i < divisionList.length;i++) {
			            	division.options[division.options.length] = new Option(divisionList[i].divisionName);
			            	division.options[i+1].value = divisionList[i].divisionId;
			            	if(divisionList[i].divisionId == data.divisionId) {
			            		document.getElementById("divisionId").selectedIndex = i+1;
			            	}
			            }
		            }
		            
		            var terminal = document.getElementById("terminalId");
		            var terminalList = data.terminalList;

		            if(terminalList != null && terminalList.length > 0) {
			            for(var i = 0;i < terminalList.length;i++) {
			            	terminal.options[terminal.options.length] = new Option(terminalList[i].terminalName);
			            	terminal.options[i+1].value = terminalList[i].terminalId;
			            	if(terminalList[i].terminalId == data.terminalId) {
			            		document.getElementById("terminalId").selectedIndex = i+1;
			            	}
			            }
		            }
		            
		            var category = document.getElementById("categoryId");
		            var categoryList = data.categoryList;
		            
		            if(categoryList != null && categoryList.length > 0) {
			            for(var i = 0;i < categoryList.length;i++) {
			            	category.options[category.options.length] = new Option(categoryList[i].name);
			            	category.options[i+1].value = categoryList[i].categoryId;
			            	if(categoryList[i].categoryId == data.categoryId) {
			            		document.getElementById("categoryId").selectedIndex = i+1;
			            	}
			            }
		            }
		            
		            var role = document.getElementById("roleId");
		            var roleList = data.roleList;
		            
		            if(roleList != null && roleList.length > 0) {
			            for(var i = 0;i < roleList.length;i++) {
			            	role.options[role.options.length] = new Option(roleList[i].typeName);
			            	role.options[i+1].value = roleList[i].typeId;
			            	if(roleList[i].typeId == data.roleId) {
			            		document.getElementById("roleId").selectedIndex = i+1;
			            	}
			            }
		            }
		            
		            var status = document.getElementById("statusId");
		            var statusList = data.statusList;
		            
		            if(statusList != null && statusList.length > 0) {
			            for(var i = 0;i < statusList.length;i++) {
			            	status.options[status.options.length] = new Option(statusList[i].status);
			            	status.options[i+1].value = statusList[i].id;
			            	if(statusList[i].id == data.statusId) {
			            		document.getElementById("statusId").selectedIndex = i+1;
			            	}
			            }
		            }
		            
		            var driverClass = document.getElementById("classId");
		            var driverClassList = data.driverClassList;
		            
		            if(driverClassList != null && driverClassList.length > 0) {
			            for(var i = 0;i < driverClassList.length;i++) {
			            	driverClass.options[driverClass.options.length] = new Option(driverClassList[i].typeName);
			            	driverClass.options[i+1].value = driverClassList[i].typeId;
			            	if(driverClassList[i].typeId == data.driverClassId) {
			            		document.getElementById("classId").selectedIndex = i+1;
			            	}
			            }
		            }
		            
		            var country = document.getElementById("countryId");
		            var countryList = data.countryList;
		            
		            if(countryList != null && countryList.length > 0) {
			            for(var i = 0;i < data.countryList.length-1;i++) {
			            	country.options[country.options.length] = new Option(data.countryList[i].countryName);
			            	country.options[i+1].value = data.countryList[i].countryId;
			            	if(countryList[i].countryId == data.countryId) {
			            		document.getElementById("countryId").selectedIndex = i+1;		            		
			            	}
			            }
		            }
		            
		            var driverState = document.getElementById("stateId");
		            var stateList = data.stateList;
		            
		            if(stateList != null && stateList.length > 0) {
			            for(var i = 0;i < data.stateList.length;i++) {
			            	driverState.options[driverState.options.length] = new Option(data.stateList[i].stateCode);
			            	driverState.options[i+1].value = data.stateList[i].stateId;
			            	if(stateList[i].stateId == data.stateId) {
			            		document.getElementById("stateId").selectedIndex = i+1;		            		
			            	}
			            }
		            }
		            
		            changeStateLabel();
		            //getStates();
            	});
        	}
        }
        function clearAll() {
        	$("#driverCode").val("");
        	$("#email").val("");
        	$("#firstName").val("");
        	$("#home").val("");
        	$("#fax").val("");
        	$("#lastName").val("");
        	$("#cellular").val("");
        	$("#pager").val("");
        	$("#address").val("");
        	$("#city").val("");
        	$("#zip").val("");
        	$("#province").val("");
        	$("#unit").val("");
        	$("#zip").val("");
        	document.getElementById("statusId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("divisionId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("terminalId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("categoryId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("roleId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("classId").innerHTML = "<option value='0'>Please Select</option>"; 
        	document.getElementById("countryId").innerHTML = "<option value='0'>Please Select</option>"; 
        	document.getElementById("stateId").innerHTML = "<option value='0'>Please Select</option>"; 
        }
        function emptyMessageDiv(){
        	var msg = $("#msg");
        	var msgvalue = $("#msgvalue");
        	msg.hide();
        	msgvalue.val("");	
        }

</script>
<script type="text/javascript">
function check() {
	var driverCode = $("#driverCode").val();
	var email = $("#email").val();
	var firstName =	$("#firstName").val();
	var home = $("#home").val();
	var fax = $("#fax").val();
	var lastName = $("#lastName").val();
	var cellular = $("#cellular").val();
	var pager =	$("#pager").val();
	var address = $("#address").val();
	var city = $("#city").val();
	var zip = $("#zip").val();
	var unit = $("#unit").val();
	var province = $("#province").val();
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if(driverCode == "") {
		msg.show();
		msgvalue.text("DriverCode cannot be left blank.");
		$("#driverCode").focus();
		return false;
	}
	/*if(email == "") {
		msg.show();
		msgvalue.text("Email cannot be left blank.");
		$("#email").focus();
		return false;
	} */
	if(email != "") {
		if(!isEmail(email)) {
			msg.show();
			msgvalue.text("Email should contain dot, @, anydomainname");
			$("#email").focus();
			return false;
		}
	}
	if(firstName == "") {
		msg.show();
		msgvalue.text("FirstName cannot be left blank.");
		$("#firstName").focus();
		return false;
	}
	/* if(home == "") {
		msg.show();
		msgvalue.text("Home cannot be left blank.");
		$("#home").focus();
		return false;
	} */
	if(home != "") {
		if(!isNumeric(home)) {
			msg.show();
			msgvalue.text("Only numerics allowed in Home");
			$("#home").focus();
			return false;
		}
		if(home.length != 10) {
			msg.show();
			msgvalue.text("Length 10 allowed in Home");
			$("#home").focus();
			return false;
		}
	}
/* 	if(fax == "") {
		msg.show();
		msgvalue.text("Fax cannot be left blank.");
		$("#fax").focus();
		return false;
	} */
	if(fax != "") {
		if(!isNumeric(fax)) {
			msg.show();
			msgvalue.text("Only numerics allowed in fax");
			$("#home").focus();
			return false;
		}
		if(fax.length != 10) {
			msg.show();
			msgvalue.text("Length 10 allowed in fax");
			$("#fax").focus();
			return false;
		}
	}
	if(lastName == "") {
		msg.show();
		msgvalue.text("LastName cannot be left blank.");
		$("#lastName").focus();
		return false;
	}
	/* if(cellular == "") {
		msg.show();
		msgvalue.text("Cellular cannot be left blank.");
		$("#cellular").focus();
		return false;
	} */
	if(cellular != "") {
		if(!isNumeric(cellular)) {
			msg.show();
			msgvalue.text("Only numerics allowed in cellular");
			$("#cellular").focus();
			return false;
		}
		if(cellular.length != 10) {
			msg.show();
			msgvalue.text("Length 10 allowed in cellular");
			$("#cellular").focus();
			return false;
		}
	}
	/* if(pager == "") {
		msg.show();
		msgvalue.text("Pager cannot be left blank.");
		$("#pager").focus();
		return false;
	} */
	if(pager != "") {
		if(!isNumeric(pager)) {
			msg.show();
			msgvalue.text("Only numerics allowed in pager");
			$("#pager").focus();
			return false;
		}
		if(pager.length != 10) {
			msg.show();
			msgvalue.text("Length 10 allowed in pager");
			$("#pager").focus();
			return false;
		}
	}
	/* if(address == "") {
		msg.show();
		msgvalue.text("Address cannot be left blank.");
		$("#address").focus();
		return false;
	}
	if(city == "") {
		msg.show();
		msgvalue.text("City cannot be left blank.");
		$("#city").focus();
		return false;
	} */
	var country = $('#countryId :selected').text();
	if(country == 'United States') {
		/* if(zip == "") {
			msg.show();
			msgvalue.text("Zip cannot be left blank.");
			$("#zip").focus();
			return false;
		} */
		if(zip != "") {
			if(!isNumeric(zip)) {
				msg.show();
				msgvalue.text("Only numerics allowed in Zip");
				$("#zip").focus();
				return false;
			}
			if(zip.length != 5) {
				msg.show();
				msgvalue.text("Length 5 allowed in Zip");
				$("#zip").focus();
				return false;
			}
		}
		if($('#stateId :selected').val() == 0) {
			msg.show();
			msgvalue.text("State cannot be left blank.");
			$("#stateId").focus();
			return false;			
		}
	}
	var country = $('#countryId :selected').text();
	if(country == 'Canada') {
		/* if(zip == "") {
			msg.show();
			msgvalue.text("PostalCode cannot be left blank.");
			$("#zip").focus();
			return false;
		} */
		if(zip != "") {
			if(!isAlphaNumeric(zip)) {
				msg.show();
				msgvalue.text("Only alphanumerics allowed in PostalCode");
				$("#zip").focus();
				return false;
			}
			if(zip.length != 6) {
				msg.show();
				msgvalue.text("Length 6 allowed in PostalCode");
				$("#zip").focus();
				return false;
			}
			if((!isNameWithoutSpace(zip[0])) || (!isNumeric(zip[1])) || (!isNameWithoutSpace(zip[2])) || (!isNumeric(zip[3])) || (!isNameWithoutSpace(zip[4])) || (!isNumeric(zip[5]))) {
				msg.show();
				msgvalue.text("Invalid pattern PostalCode");
				$("#zip").focus();
				return false;
			}
		}
		if($('#stateId :selected').val() == 0) {
			msg.show();
			msgvalue.text("State cannot be left blank.");
			$("#stateId").focus();
			return false;			
		}
	}
	var division = $('#divisionId :selected').val();
	//var terminal = $('#terminalId :selected').val();
	
	if(division == 0) {
		msg.show();
		msgvalue.text("Division cannot be left blank.");
		$("#divisionId").focus();
		return false;
	}
	
	/* if(terminal == 0) {
		msg.show();
		msgvalue.text("Terminal cannot be left blank.");
		$("#terminalId").focus();
		return false;
	} */
	
	/* if(unit == "") {
		msg.show();
		msgvalue.text("Unit cannot be left blank.");
		$("#unit").focus();
		return false;
	}
	if(province == "") {
		msg.show();
		msgvalue.text("Province cannot be left blank.");
		$("#province").focus();
		return false;
	} */
	return true;
}
</script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<div class="form-group">
				<div class="row">
					<div class="col-sm-12" align="center">
						<b class = "pageHeading">Drivers</b>
					</div>
				</div>
			</div>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="checkFlag('add'); onClickMethodQuestion('0'); emptyMessageDiv()" >Add New</button>
		<div class="form-group">
		<div class="row">
			<div class="col-sm-8">
					<div class="modal fade" id="myModal" role="dialog">
					    <div class="modal-dialog">
						<!-- <form action="savedriver" method="POST" name="driver" id="frm1" onsubmit="return check()"> -->
						<form id="frm1">
						<input type="hidden" id = "driverid" name= "driverid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add Driver</p></h4>
					          <div class="alert alert-danger fade in" id="msg" style="display: none;">
									<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
									<strong id = "msgvalue"></strong>
							  </div>
					        </div>
					        <div class="modal-body">
								<div class = "row">
								<div class="col-sm-12">
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="input-group">
												<span class="input-group-addon">
													 <b>Code</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter DriverCode" id="driverCode" name="driverCode" value="" autofocus />
											</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Email</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Email" id="email" name="email" value="" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
																 <b>FirstName</b>												
															</span>
															<input type="text" class="form-control" placeHolder="Enter FirstName" id="firstName" name="firstName" value="" />
														</div>
													</div>
													<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>LastName</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter LastName" id="lastName" name="lastName" value="" />
												</div>
											</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
																 <b>Home</b>												
															</span>
															<input type="text" class="form-control" placeHolder="Enter Home" id="home" name="home" value="" />
														</div>
													</div>
													<div class="col-sm-6">
														<div class="input-group">
													<span class="input-group-addon">
														 <b>Fax</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Fax" id="fax" name="faxNo" value="" />
													</div>
													</div>												
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<div class="row">
											
											<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
																 <b>Country</b>												
															</span>
															<select class="form-control" name="countryId" id="countryId" onchange="getStates();changeStateLabel()">
																<option value="0">Please Select</option>
															</select>
														</div>
													</div>
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
													<span class="input-group-addon">
														 <b>Cellular</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Cellular" id="cellular" name="cellular" value="" />
													</div>
													</div>
													<div class="col-sm-6">
													<div class="input-group">
													<span class="input-group-addon">
														 <b>RoamingPhone</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter RoamingPhone" id="pager" name="pager" value="" />
													</div>
													</div>												
												</div>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Address</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Address" id="address" name="address" value="" />
												</div>
											</div>	
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Division</b>												
													</span>
													<select class="form-control" name="divisionId" id="divisionId">
													</select>
												</div>
											</div>	
										</div>
									</div>
									
									<div class="form-group">
										<div class="row">
										<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>UnitNo</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter UnitNo" id="unit" name="unit" value="" />
												</div>
											</div>
											
												<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Terminal</b>												
													</span>
													<select class="form-control" name="terminalId" id="terminalId">
													</select>
												</div>
											</div>	
										</div>
									</div>
									
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>City</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter City" id="city" name="city" value="" />
												</div>
											</div>	
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
													<div class="input-group">
														<span class="input-group-addon">
															 <b>Category</b>												
														</span>
														<select class="form-control" name="categoryId" id="categoryId">
														</select>
													</div>
													</div>
													<div class="col-sm-6">
													<div class="input-group">
														<span class="input-group-addon">
															 <b>Role</b>												
														</span>
														<select class="form-control" name="roleId" id="roleId">
														</select>
													</div>
													</div>								
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="row">
													
													<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
																 <b id="stateLabel">Province</b>												
															</span>
															<select class="form-control" name="stateId" id="stateId">
																<option value="0">Please Select</option>
															</select>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="input-group">
													<span class="input-group-addon">
														 <b id="zipLabel">Zip</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Zip" id="zip" name="postalCode" value="" />
													</div>
													</div>
																									
												</div>
											</div>
											
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
													<div class="input-group">
														<span class="input-group-addon">
															 <b>Status</b>												
														</span>
														<select class="form-control" name="statusId" id="statusId">
														</select>
													</div>
													</div>
													<div class="col-sm-6">
													<div class="input-group">
														<span class="input-group-addon">
															 <b>Class</b>												
														</span>
														<select class="form-control" name="driverClassId" id="classId">
														</select>
													</div>
													</div>													
												</div>
											</div>
										</div>
									</div>
								</div>
				        	</div>
					        </div>
					         <div class="modal-footer">
					          <input type="button" class="btn btn-primary" id= "btnSave" value="Save" onclick="navigate()" />
							  <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					        </div>
					      </div>
					      </form>
					    </div>
					  </div>
				</div>
				<div class="col-sm-4">
				</div>
		</div>
		</div>	
		<form action="showques" method="GET" name="ques" id="frmSearch">
		<%-- <div class="row">
			<div class="col-sm-4">
				<input type="text" name="question" placeholder="Write Question to Search..." class="form-control" />
			</div>
			<div class="col-sm-4">
				<input type="text" name="answer" placeholder="Write Answer to Search..." class="form-control" />
			</div>
			<div class="col-sm-2">
				<select class="form-control" name="categoryId">
					<option value="0">Select</option>
					<c:forEach items="${LIST_CAT}" var="obj">
						<option value="${obj.categoryId}">${obj.title}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2">
				<button type="button" id = "btnSearch" class="btn btn-info"><span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;&nbsp;Search</button>
			</div>
		</div> --%>
		</form>
	</div>
	<div class="container">
		<div class="table-responsive">
			<table class="table table-striped table-hover table-condensed">
				<thead>
					<tr>
						<th>DriverCode</th>
						<th>FirstName</th>
						<th>LastName</th>
						<th>Address</th>
						<th>Unit</th>
						<th>City</th>
						<th>P/S</th>
						<!-- <th>ZipCode</th>
						<th>Home</th> -->
						<th>Fax</th>
						<th>Cellular</th>
						<th>Pager</th>
						<th>Email</th>
						<th>Links</th>
					</tr>
				</thead>
				<tbody id="driverData">
				<c:choose>
					<c:when test="${LIST_DRIVER.size() > 0}">
						<c:forEach items="${LIST_DRIVER}" var="obj">
							<tr class="info">
								<c:if test = "${obj.firstName.length() <= 20}">
									<c:set var = "firstName" value="${obj.firstName}"/>
								</c:if>
								<c:if test = "${obj.lastName.length() > 20}">
									<c:set var = "firstName" value="${fn:substring(obj.firstName, 0, 19)}..."/>
								</c:if>
	
								<td>${obj.driverCode}</td>							
								<td>${firstName}</td>
								
								<c:if test = "${obj.lastName.length() <= 20}">
									<c:set var = "lastName" value="${obj.lastName}"/>
								</c:if>
								<c:if test = "${obj.lastName.length() > 20}">
									<c:set var = "lastName" value="${fn:substring(obj.lastName, 0, 19)}..."/>
								</c:if>
								<td>${lastName}</td>
								<td>${obj.address}</td>
								<td>${obj.unit}</td>
								<td>${obj.city}</td>
								<td>${obj.stateName}</td>
								
								<td>${obj.faxNo}</td>
								<td>${obj.cellular}</td>
								<td>${obj.pager}</td>
								<td>${obj.email}</td>
								<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.driverId}')">Update</a> / <a href="#" onclick="deleteDriver('${obj.driverId}')">Delete</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="12">No records found.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>