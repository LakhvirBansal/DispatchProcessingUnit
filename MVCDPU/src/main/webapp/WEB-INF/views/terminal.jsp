<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Terminal</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.min.js"></script>
	 <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css" />
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
	<jsp:include page="Include.jsp"></jsp:include>
 <script src="<c:url value="/resources/validations.js" />"></script>
<script type="text/javascript">
function navigate() {
	var flag = $("#addUpdateFlag").val();
	if(flag == 'add') {
		createTerminal('saveterminal','POST');
	} else if(flag == 'update') {
		createTerminal('updateterminal','PUT');			
	}
}
function createTerminal(urlToHit,methodType){
	 
	 if(!check()){
		 return false;
	 }
	 
	 	var terminalName = $("#terminalName").val();
	   	var locationId = $('#locationId :selected').val();
	   	var serviceIds = $('#serviceIds').val();

	   	var terminalId;
	   	if(methodType == 'PUT') {
	   		terminalId = $('#terminalid').val();
	   	}
	   	
	   	var loc = parseInt(locationId);
	   	
	   	blockUI()
		  $.ajax({url: BASE_URL + urlToHit,
			      type:"POST",
			      data:{
			    	terminalName:terminalName,
			    	shipperId:loc,
			    	stringServiceIds:serviceIds.toString(),
			    	terminalid:terminalId
			      },
			      success: function(result){
		        try{
		        	$('#myModal').modal('toggle');
		        	var list = result.resultList;
					fillTerminalData(list);

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

function fillTerminalData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var terminal = "";
    		if(obj.terminalName != null) {
    			terminal = obj.terminalName;
    		}
    		var shipperName = "";
    		if(obj.shipperName != null) {
    			shipperName = obj.shipperName;
    		}
    		
    		tableValue = tableValue + ("<td>"+(terminal)+"</td>");
    		tableValue = tableValue + ("<td>"+(shipperName)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.terminalId)+"')>Update</a> / <a href='#' onclick=deleteTerminal('"+(obj.terminalId)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#terminalData").html(tableValue);
	} else {
		$("#terminalData").html("No records found.");		
	}
}

function deleteTerminal(terminalId){
	  blockUI()
	  $.ajax({url: BASE_URL + "deleteterminal/" + terminalId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillTerminalData(list);
					
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
		//document.getElementById("frm1").action = "updateterminal";
		document.getElementById("btnSave").value = "Update";
		$("#modelTitle").html("Edit Terminal");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnSave").value = "Save";
		$("#modelTitle").html("Add Terminal");
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
        		document.getElementById("locationId").innerHTML = "";
            	document.getElementById("serviceIds").innerHTML = "";
            	$.get("terminal/getopenadd", function(data) {
            		unblockUI()
    	           
    	            var shipperLocation = document.getElementById("locationId");
    	            for(var i = 0;i < data.shipperList.length;i++) {
    	            	shipperLocation.options[shipperLocation.options.length] = new Option(data.shipperList[i].locationName);
    	            	shipperLocation.options[i].value = data.shipperList[i].shipperId;
    	            } 
    	            
    	            var opt = "";
    	            for(var i = 0;i < data.serviceList.length;i++) {
 			         	opt += "<option value='"+data.serviceList[i].serviceId+"' id='chkService"+data.serviceList[i].serviceId+"'>"+data.serviceList[i].serviceName+"</option>"
 			         }
    	            
    	            $('#serviceIds').multiselect('destroy');
    		         $("#serviceIds").html(opt);
    		         $('#serviceIds').multiselect({
    			 		  	includeSelectAllOption: true
    				 });
    	        });
        	} else {
        		blockUI()
        		$.get("getterminal/terminalId",{"terminalId" : quesId}, function(data) {
        			unblockUI()
        			document.getElementById("terminalid").value = data.terminalId;
                    $("#terminalName").val(data.terminalName);
                    
                    var shipperLocation = document.getElementById("locationId");
                    var shipperList = data.shipperList;
                    for(var i = 0;i < shipperList.length;i++) {
                    	shipperLocation.options[shipperLocation.options.length] = new Option(shipperList[i].locationName);
                    	shipperLocation.options[i].value = shipperList[i].shipperId;
                    	if(shipperList[i].shipperId == data.shipperId) {
                    		document.getElementById("locationId").selectedIndex = i;
                    	}
                    }
                    
                    var serviceIds = document.getElementById("serviceIds");
                    var serviceList = data.serviceList;
                    var opt = "";
                    for(var i = 0;i < serviceList.length;i++) {
 			         	opt += "<option value='" + serviceList[i].serviceId + "' id='chkService" + serviceList[i].serviceId+"'>" + serviceList[i].serviceName+"</option>"
                    }
                    
                    var selectedServiceIds = data.serviceIds;

                    $("#serviceIds").html(opt);
   		      		$("#serviceIds").val(selectedServiceIds);
   		      		setTimeout(function(){
			         	$('#serviceIds').multiselect({
				 			includeSelectAllOption: true
					 	});
   		      		})

               	});
        	}
        }
        
        function clearAll() {
            $("#terminalName").val("");
        	document.getElementById("locationId").innerHTML = "";
        	document.getElementById("serviceIds").innerHTML = "";
        	$('#serviceIds').multiselect('destroy');
        }
</script>

<script type="text/javascript">
function check() {
	var terminalName = $("#terminalName").val();
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if(terminalName == "") {
		msg.show();
		msgvalue.text("TerminalName cannot be left blank.");
		$("#terminalName").focus();
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
						<b class = "pageHeading">Terminals</b>
					</div>
				</div>
			</div>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="checkFlag('add'); onClickMethodQuestion('0'); emptyMessageDiv();" >Add New</button>
		<div class="form-group">
		<div class="row">
			<div class="col-sm-8">
					<div class="modal fade" id="myModal" role="dialog">
					    <div class="modal-dialog">
						<form id="frm1">
						<input type="hidden" id = "terminalid" name= "terminalid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add Terminal</p></h4>
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
											<div class="col-sm-12">
												<div class="input-group">
												<span class="input-group-addon">
													 <b>TerminalName</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter TerminalName" id="terminalName" name="terminalName" value="" autofocus />
											</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Location</b>												
													</span>
													<select class="form-control" name="shipperId" id="locationId">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Services</b>												
													</span>
													<select id="serviceIds" class="form-control" multiple="multiple" name="serviceIds">
													</select>
												</div>
											</div>
										</div>
									</div>
								</div>
				        	</div>
					        </div>
					        <div class="modal-footer">
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
						<th>Terminal</th>
						<th>Location</th>
						<th>Links</th>
					</tr>
				</thead>
				<tbody id="terminalData">
				<c:choose>
					<c:when test="${LIST_TERMINAL.size() > 0}">
						<c:forEach items="${LIST_TERMINAL}" var="obj">
							<tr class="info">
								<td>${obj.terminalName}</td>							
								<td>${obj.shipperName}</td>
								<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.terminalId}')">Update</a> / <a href="#" onclick="deleteTerminal('${obj.terminalId}')">Delete</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="3">No records found.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>