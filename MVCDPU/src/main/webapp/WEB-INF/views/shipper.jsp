<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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

  height:90x; 
  min-height:90px;  
  max-height:90px;
}
</style>

	<jsp:include page="Include.jsp"></jsp:include>
 <script src="<c:url value="/resources/validations.js" />"></script>
<script type="text/javascript">
function navigate() {
	var flag = $("#addUpdateFlag").val();
	if(flag == 'add') {
		createShipper('saveshipper','POST');
	} else if(flag == 'update') {
		createShipper('updateshipper','PUT');			
	}
}
function createShipper(urlToHit,methodType){
	 
	 if(!check()){
		 return false;
	 }
	var location = $("#location").val();
	var importer = $("#importer").val();
   	var countryId = $('#countryId :selected').val();
   	var contact = $("#contact").val();
   	var position = $("#position").val();
   	var address = $("#address").val();
   	var phone = $("#phone").val();
   	var ext = $("#ext").val();
   	var unitNo = $("#unitNo").val();
   	var fax = $("#fax").val();
   	var prefix = $("#prefix").val();
   	var city = $("#city").val();
   	var tollfree = $("#tollfree").val();
   	var plant = $("#plant").val();
	var stateId = $('#stateId :selected').val();
   	var zip = $("#zip").val();
   	var zone = $("#zone").val();
   	var email = $("#email").val();
	var statusId = $('#statusId :selected').val();
   	var leadTime = $("#leadTime").val();
   	var timeZone = $("#timeZone").val();
   	var internalNotes = $("#internalNotes").val();
   	var standardNotes = $("#standardNotes").val();

   	var shipperId;
	if(methodType == 'PUT') {
		shipperId = $("#shipperid").val();
	}
	
	blockUI()
	  $.ajax({url: BASE_URL + urlToHit,
		      type:"POST",
		      data:{
		    	locationName:location,
		    	importer:importer,
		    	countryId:countryId,
		    	contact:contact,
		    	position:position,
		    	address:address,
		    	phone:phone,
		    	ext:ext,
		    	unit:unitNo,
		    	fax:fax,
		    	prefix:prefix,
		    	city:city,
		    	tollFree:tollfree,
		    	plant:plant,
		    	stateId:stateId,
		    	postalZip:zip,
		    	zone:zone,
		    	email:email,
		    	statusId:statusId,
		    	leadTime:leadTime,
		    	timeZone:timeZone,
		    	internalNotes:internalNotes,
		    	standardNotes:standardNotes,
		    	shipperid:shipperId
		      },
		      success: function(result){
	        try{
	        	
	        	$('#myModal').modal('toggle');
	        	var list = result.resultList;
				fillShipperData(list);

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

function fillShipperData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
			var locationName = "";
    		if(obj.locationName != null) {
    			locationName = obj.locationName;
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
    		var phone = "";
    		if(obj.phone != null) {
    			phone = obj.phone;
    		}
    		var prefix = "";
    		if(obj.prefix != null) {
    			prefix = obj.prefix;
    		}
    		var tollFree = "";
    		if(obj.tollFree != null) {
    			tollFree = obj.tollFree;
    		}
    		var plant = "";
    		if(obj.plant != null) {
    			plant = obj.plant;
    		}
    		var email = "";
    		if(obj.email != null) {
    			email = obj.email;
    		}
    		var importer = "";
    		if(obj.importer != null) {
    			importer = obj.importer;
    		}
    		
    		tableValue = tableValue + ("<td>"+(locationName)+"</td>");
    		tableValue = tableValue + ("<td>"+(address)+"</td>");
    		tableValue = tableValue + ("<td>"+(unit)+"</td>");
    		tableValue = tableValue + ("<td>"+(city)+"</td>");
    		tableValue = tableValue + ("<td>"+(stateName)+"</td>");
    		tableValue = tableValue + ("<td>"+(phone)+"</td>");
    		tableValue = tableValue + ("<td>"+(prefix)+"</td>");
    		tableValue = tableValue + ("<td>"+(tollFree)+"</td>");
    		tableValue = tableValue + ("<td>"+(plant)+"</td>");
    		tableValue = tableValue + ("<td>"+(email)+"</td>");
    		tableValue = tableValue + ("<td>"+(importer)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.shipperId)+"')>Update</a> / <a href='#' onclick=deleteShipper('"+(obj.shipperId)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#shipperData").html(tableValue);
	} else {
		$("#shipperData").html("No records found.");		
	}
}

function deleteShipper(shipperId){
	 
	  blockUI();
	  $.ajax({url: BASE_URL + "deleteshipper/" + shipperId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillShipperData(list);
					
	    		  toastr.success(result.message, 'Success!')
			  }catch(e){
				  unblockUI();
				toastr.error('Something went wrong', 'Error!')
			  }
	  },error:function(result){
		  try{
			  unblockUI();
			  	var obj = JSON.parse(result.responseText);
			  	toastr.error(obj.message, 'Error!')
			  }catch(e){
				  unblockUI();
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
</script>
<script type="text/javascript">
	function checkFlag(field) {
		document.getElementById("addUpdateFlag").value = field;
		if(field == 'update') {
			//document.getElementById("frm1").action = "updateshipper";
			document.getElementById("btnSave").value = "Update";
			$("#modelTitle").html("Edit Location");
		}
		else if(field == 'add') {
			//$("#cke_1_contents").html('');
			$(":text").val("");
	   		//document.getElementById('categoryId').selectedIndex = 0;
			document.getElementById("btnSave").value = "Save";
			$("#modelTitle").html("Add Location");
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
	     	$.get("shipper/getopenadd", function(data) {
	     	  unblockUI()
	          var status = document.getElementById("statusId");
	          for(var i = 0;i < data.statusList.length;i++) {
	          	status.options[status.options.length] = new Option(data.statusList[i].status);
	          	status.options[i].value = data.statusList[i].id;
	          }
	          
		        var country = document.getElementById("countryId");
	            for(var i = 0;i < data.countryList.length;i++) {
	            	country.options[country.options.length] = new Option(data.countryList[i].countryName);
	            	country.options[i].value = data.countryList[i].countryId;
	            }
	            
	            getStates();
				changeStateLabel();
	      });
    	} else {
    		blockUI()
    		$.get("getshipper/shipperId",{"shipperId" : quesId}, function(data) {
    			unblockUI()
    			document.getElementById("shipperid").value = data.shipperId;
            	$("#location").val(data.locationName);
            	$("#importer").val(data.importer);
            	$("#contact").val(data.contact);
            	$("#position").val(data.position);
            	$("#address").val(data.address);
            	$("#phone").val(data.phone);
            	$("#ext").val(data.ext);
            	$("#unitNo").val(data.unit);
            	$("#fax").val(data.fax);
            	$("#prefix").val(data.prefix);
            	$("#city").val(data.city);
            	$("#tollfree").val(data.tollFree);
            	$("#plant").val(data.plant);
            	$("#zip").val(data.zip);
            	$("#zone").val(data.zone);
            	$("#email").val(data.email);
            	$("#leadTime").val(data.leadTime);
            	$("#timeZone").val(data.timeZone);
            	$("#internalNotes").val(data.internalNotes);
            	$("#standardNotes").val(data.standardNotes);
            	$("#zip").val(data.postalZip);

            	var country = document.getElementById("countryId");
	            var countryList = data.countryList;
	            for(var i = 0;i < data.countryList.length;i++) {
	            	country.options[country.options.length] = new Option(data.countryList[i].countryName);
	            	country.options[i].value = data.countryList[i].countryId;
	            	if(countryList[i].countryId == data.countryId) {
	            		document.getElementById("countryId").selectedIndex = i;		            		
	            	}
	            }
	            
                //getStates();
				changeStateLabel();

				var driverState = document.getElementById("stateId");
	            var stateList = data.stateList;
	            for(var i = 0;i < data.stateList.length;i++) {
	            	driverState.options[driverState.options.length] = new Option(data.stateList[i].stateCode);
	            	driverState.options[i].value = data.stateList[i].stateId;
	            	if(stateList[i].stateId == data.stateId) {
	            		document.getElementById("stateId").selectedIndex = i;		            		
	            	}
	            }
            	
                var status = document.getElementById("statusId");
                var statusList = data.statusList;
                for(var i = 0;i < statusList.length;i++) {
                	status.options[status.options.length] = new Option(statusList[i].status);
                	status.options[i].value = statusList[i].id;
                	if(statusList[i].id == data.statusId) {
                		document.getElementById("statusId").selectedIndex = i;
                	}
                }
                
           	});
    	}
    }
    
    function getStates() {
    	
    	var countryId = $('#countryId :selected').val();
    	
    	blockUI()
    	document.getElementById("stateId").innerHTML = "";
    	$.get("states/" + countryId, function(response) {
             
    		unblockUI();
            if(response.length > 0) {
                var state = document.getElementById("stateId");
            	for(var i = 0;i < response.length;i++) {
            		state.options[state.options.length] = new Option(response[i].stateCode);
            		state.options[i].value = response[i].stateId;
                }
            }
    	});
    }
    
    function changeStateLabel() {
    	
    	var country = $('#countryId :selected').text();
    	if(country == 'USA') {
    		$("#zipLabel").text("Zip");
    		$("#zip").attr("placeholder","Enter Zip");
    		$("#stateLabel").text("State");
    	} else if(country == 'Canada'){
    		$("#zipLabel").text("PostalCode");
    		$("#zip").attr("placeholder","Enter PostalCode");
    		$("#stateLabel").text("Province");
    	}
    }
    
    function clearAll() {
    	$("#location").val("");
    	$("#contact").val("");
    	$("#address").val("");
    	$("#position").val("");
    	$("#unitNo").val("");
    	$("#phone").val("");
    	$("#ext").val("");
    	$("#city").val("");
    	$("#fax").val("");
    	$("#prefix").val("");
    	$("#tollfree").val("");
    	$("#plant").val("");
    	$("#zone").val("");
    	$("#email").val("");
    	$("#leadTime").val("");
    	$("#timeZone").val("");
    	$("#importer").val("");
    	$("#internalNotes").val("");
    	$("#standardNotes").val("");
    	$("#zip").val("");
       	document.getElementById("statusId").innerHTML = "";
    	document.getElementById("countryId").innerHTML = ""; 
    	document.getElementById("stateId").innerHTML = ""; 
    }
</script>

<script type="text/javascript">
function check() {
	var shipperLocation = $("#location").val();
	var contact = $("#contact").val();
	var address = $("#address").val();
	var position = $("#position").val();
	var unitNo = $("#unitNo").val();
	var phone = $("#phone").val();
	var ext = $("#ext").val();
	var city = $("#city").val();
	var fax = $("#fax").val();
	var prefix = $("#prefix").val();
	var province = $("#province").val();
	var zip = $("#zip").val();
	var tollFree = $("#tollfree").val();
	var plant = $("#plant").val();
	var zone = $("#zone").val();
	var email = $("#email").val();
	var leadTime = $("#leadTime").val();
	var timeZone = $("#timeZone").val();
	var importer = $("#importer").val();
 	var internalNotes = $("#internalNotes").val();
 	var standardNotes = $("#standardNotes").val();
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if(shipperLocation == "") {
		msg.show();
		msgvalue.text("Location cannot be left blank.");
		$("#location").focus();
		return false;
	}
	if(contact == "") {
		msg.show();
		msgvalue.text("Contact cannot be left blank.");
		$("#contact").focus();
		return false;
	}
	if(!isNumeric(contact)) {
		msg.show();
		msgvalue.text("Only numerics allowed in contact");
		$("#contact").focus();
		return false;
	}
	if(contact.length != 10) {
		msg.show();
		msgvalue.text("Length 10 allowed in contact");
		$("#contact").focus();
		return false;
	}
	if(address == "") {
		msg.show();
		msgvalue.text("Address cannot be left blank.");
		$("#address").focus();
		return false;
	}
	if(position == "") {
		msg.show();
		msgvalue.text("Position cannot be left blank.");
		$("#position").focus();
		return false;
	}
	if(unitNo == "") {
		msg.show();
		msgvalue.text("UnitNo cannot be left blank.");
		$("#unitNo").focus();
		return false;
	}
	if(phone == "") {
		msg.show();
		msgvalue.text("Phone cannot be left blank.");
		$("#phone").focus();
		return false;
	}
	if(!isNumeric(phone)) {
		msg.show();
		msgvalue.text("Only numerics allowed in phone");
		$("#phone").focus();
		return false;
	}
	if(phone.length != 10) {
		msg.show();
		msgvalue.text("Length 10 allowed in phone");
		$("#phone").focus();
		return false;
	}
	if(ext == "") {
		msg.show();
		msgvalue.text("Ext cannot be left blank.");
		$("#ext").focus();
		return false;
	}
	if(!isNumeric(ext)) {
		msg.show();
		msgvalue.text("Only numerics allowed in ext");
		$("#ext").focus();
		return false;
	}
	if(ext.length != 10) {
		msg.show();
		msgvalue.text("Length 10 allowed in ext");
		$("#ext").focus();
		return false;
	}
	if(city == "") {
		msg.show();
		msgvalue.text("City cannot be left blank.");
		$("#city").focus();
		return false;
	}
	if(fax == "") {
		msg.show();
		msgvalue.text("Fax cannot be left blank.");
		$("#fax").focus();
		return false;
	}
	if(!isNumeric(fax)) {
		msg.show();
		msgvalue.text("Only numerics allowed in fax");
		$("#fax").focus();
		return false;
	}
	if(fax.length != 10) {
		msg.show();
		msgvalue.text("Length 10 allowed in fax");
		$("#fax").focus();
		return false;
	}
	if(prefix == "") {
		msg.show();
		msgvalue.text("Prefix cannot be left blank.");
		$("#prefix").focus();
		return false;
	}
	if(province == "") {
		msg.show();
		msgvalue.text("Province cannot be left blank.");
		$("#province").focus();
		return false;
	}
	var country = $('#countryId :selected').text();
	if(country == 'USA') {
		if(zip == "") {
			msg.show();
			msgvalue.text("Zip cannot be left blank.");
			$("#zip").focus();
			return false;
		}
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
	var country = $('#countryId :selected').text();
	if(country == 'Canada') {
		if(zip == "") {
			msg.show();
			msgvalue.text("PostalCode cannot be left blank.");
			$("#zip").focus();
			return false;
		}
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
	if(tollFree == "") {
		msg.show();
		msgvalue.text("TollFree cannot be left blank.");
		$("#tollfree").focus();
		return false;
	}
	if(!isNumeric(tollFree)) {
		msg.show();
		msgvalue.text("Only numerics allowed in tollFree");
		$("#tollfree").focus();
		return false;
	}
	if(tollFree.length != 10) {
		msg.show();
		msgvalue.text("Length 10 allowed in tollFree");
		$("#tollfree").focus();
		return false;
	}
	if(plant == "") {
		msg.show();
		msgvalue.text("Plant cannot be left blank.");
		$("#plant").focus();
		return false;
	}
	if(tollFree == "") {
		msg.show();
		msgvalue.text("TollFree cannot be left blank.");
		$("#tollfree").focus();
		return false;
	}
	if(zone == "") {
		msg.show();
		msgvalue.text("Zone cannot be left blank.");
		$("#zone").focus();
		return false;
	}
	if(email == "") {
		msg.show();
		msgvalue.text("Email cannot be left blank.");
		$("#email").focus();
		return false;
	}
	if(!isEmail(email)) {
		msg.show();
		msgvalue.text("Email should contain dot, @, anydomainname");
		$("#email").focus();
		return false;
	}
	if(leadTime == "") {
		msg.show();
		msgvalue.text("LeadTime cannot be left blank.");
		$("#leadTime").focus();
		return false;
	}
	if(timeZone == "") {
		msg.show();
		msgvalue.text("TimeZone cannot be left blank.");
		$("#timeZone").focus();
		return false;
	}
	if(importer == "") {
		msg.show();
		msgvalue.text("Importer cannot be left blank.");
		$("#importer").focus();
		return false;
	}
	if(internalNotes == "") {
		msg.show();
		msgvalue.text("InternalNotes cannot be left blank.");
		$("#internalNotes").focus();
		return false;
	}
	if(standardNotes == "") {
		msg.show();
		msgvalue.text("StandardNotes cannot be left blank.");
		$("#standardNotes").focus();
		return false;
	}
	$('#modal').modal('toggle');
	return true;
}

function emptyMessageDiv(){
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");	
}
</script>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<div class="form-group">
				<div class="row">
					<div class="col-sm-12" align="center">
						<b class = "pageHeading">Locations</b>
					</div>
				</div>
			</div>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="checkFlag('add'); onClickMethodQuestion('0'); emptyMessageDiv(); emptyMessageDiv();" >Add New</button>
		<div class="form-group">
		<div class="row">
			<div class="col-sm-8">
					<div class="modal fade" id="myModal" role="dialog">
					    <div class="modal-dialog">
						<form id="frm1">
						<input type="hidden" id = "shipperid" name= "shipperid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add Location</p></h4>
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
													 <b>LocationName</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter Location" id="location" name="locationName" value="" autofocus />
											</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Importer</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Importer" id="importer" name="importer" value="" />
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
													</select>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
														 		<b>Contact</b>												
															</span>
															<input type="text" class="form-control" placeHolder="Enter Contact" id="contact" name="contact" value="" />
														</div>
													</div>
												<div class="col-sm-6">
													<div class="input-group">
														<span class="input-group-addon">
															 <b>Position</b>												
														</span>
														<input type="text" class="form-control" placeHolder="Enter Position" id="position" name="position" value="" />
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
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
													<span class="input-group-addon">
														 <b>Phone</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Phone" id="phone" name="phone" value="" />
													</div>
													</div>
													<div class="col-sm-6">
													<div class="input-group">
													<span class="input-group-addon">
														 <b>Ext</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Ext" id="ext" name="ext" value="" />
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
														 <b>UnitNo</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter UnitNo" id="unitNo" name="unit" value="" />
												</div>
											</div>	
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
													<span class="input-group-addon">
														 <b>Fax</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Fax" id="fax" name="fax" value="" />
													</div>
													</div>
													<div class="col-sm-6">
													<div class="input-group">
													<span class="input-group-addon">
														 <b>Prefix</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Prefix" id="prefix" name="prefix" value="" />
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
														 <b>TollFree</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter TollFree" id="tollfree" name="tollFree" value="" />
													</div>
													</div>
													<div class="col-sm-6">
													<div class="input-group">
													<span class="input-group-addon">
														 <b>Plant</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Plant" id="plant" name="plant" value="" />
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
															</select>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
																 <b id="zipLabel">Zip</b>												
															</span>
															<input type="text" class="form-control" placeHolder="Enter Zip" id="zip" name="zip" value="" />
														</div>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
																 <b>Zone</b>												
															</span>
															<input type="text" class="form-control" placeHolder="Enter Zone" id="zone" name="zone" value="" />
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
										</div>
									</div>
									
									<div class="form-group">
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
												<div class="row">
													<div class="col-sm-6">
														<div class="input-group">
															<div class="form-group text-left">
																<input type="checkbox" tabindex="3" class="" name="remember" id="remember">ETA To PickUp Alert
																<input type="checkbox" tabindex="3" class="" name="remember" id="remember">ETA To Deliver Alert
															</div>
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
																 <b>LeadTime</b>												
															</span>
															<input type="text" class="form-control" placeHolder="Enter LeadTime" id="leadTime" name="leadTime" value="" />
														</div>
													</div>
													<div class="col-sm-6">
														<div class="input-group">
															<span class="input-group-addon">
																 <b>TimeZone</b>												
															</span>
															<input type="text" class="form-control" placeHolder="Enter TimeZone" id="timeZone" name="timeZone" value="" />
														</div>
													</div>												
												</div>
											</div>
											<div class="col-sm-6">
												<div class="row">
													<div class="col-sm-12">
														<div class="input-group">
															<div class="form-group text-left">
																<input type="checkbox" tabindex="1" class="" name="remember" id="remember">Registered C.S.A Facility
																<input type="checkbox" tabindex="1" class="" name="remember" id="remember">Registered C-TPAT Facility
																<input type="checkbox" tabindex="1" class="" name="remember" id="remember">Warehouse or Cross-Dock Facility
															</div>
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
														 <b>InternalNotes</b>												
													</span>
													<textarea class="form-control" rows="1" cols="1" placeholder="Internal Notes" name="internalNotes" id = "internalNotes"></textarea>
												</div>
											</div>	
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>StandardNotes</b>												
													</span>
													<textarea class="form-control" rows="1" cols="1" placeholder="Standard Notes" name="standardNotes" id="standardNotes"></textarea>
												</div>
											</div>	
										</div>
									</div>
									
								</div>
				        	</div>
					        </div>
					         <div class="modal-footer">
						          <input type="button" class="btn btn-default" data-dismiss="modal" value="Directions"/>
								  <button type="button" class="btn btn-default" data-dismiss="modal">Hours</button>
								  <input type="button" class="btn btn-default" data-dismiss="modal"  value="Notes" />
								  <button type="button" class="btn btn-default" data-dismiss="modal">Reports</button>
								  <input type="button" class="btn btn-default" data-dismiss="modal" value="AC" />
						          <input type="button" class="btn btn-primary" id= "btnSave" value="Save" onclick="navigate()"/>
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
						<th>Company</th>
						<th>address</th>
						<th>Unit</th>
						<th>City</th>
						<th>Province</th>
						<th>Phone</th>
						<th>Prefix</th>
						<th>Tollfree</th>
						<th>Plant</th>
						<th>Email</th>
						<th>Importer</th>
						<th>Links</th>
					</tr>
				</thead>
				<tbody id="shipperData">
					<c:choose>
						<c:when test="${LIST_LOCATION.size() > 0}">
							<c:forEach items="${LIST_LOCATION}" var="obj">
								<tr class="info">
									<td>${obj.locationName}</td>
									<td>${obj.address}</td>
									<td>${obj.unit}</td>
									<td>${obj.city}</td>
									<td>${obj.stateName}</td>
									<td>${obj.phone}</td>
									<td>${obj.prefix}</td>
									<td>${obj.tollFree}</td>
									<td>${obj.plant}</td>
									<td>${obj.email}</td>
									<td>${obj.importer}</td>
									<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update'); onClickMethodQuestion('${obj.shipperId}');">Update</a> / <a href="#" onclick="deleteShipper('${obj.shipperId}')">Delete</a></td>
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