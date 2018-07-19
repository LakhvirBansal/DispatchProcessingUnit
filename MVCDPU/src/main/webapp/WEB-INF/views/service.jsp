<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Service</title>
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
		createService('saveservice','POST');
	} else if(flag == 'update') {
		createService('updateservice','PUT');			
	}
}
function createService(urlToHit,methodType){
	 
	 if(!check()){
		 return false;
	 }
	 
	   	var textFieldId = $('#textFieldId :selected').val();
	 	var serviceName = $("#serviceName").val();
	   	var associationId = $('#associationId :selected').val();
	   	var statusId = $('#statusId :selected').val();
	   	var serviceId;
	   	if(methodType == 'PUT') {
	   		serviceId = $('#serviceid').val();
	   	}
	   	
	   	blockUI()
		  $.ajax({url: BASE_URL + urlToHit,
			      type:"POST",
			      data:{
			    	serviceName:serviceName,
			    	textFieldId:textFieldId,
			    	statusId:statusId,
			    	associationWithId:associationId,
			    	serviceid:serviceId
			      },
			      success: function(result){
		        try{
		        	$('#myModal').modal('toggle');
		        	var list = result.resultList;
					fillServiceData(list);

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

function fillServiceData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var serviceName = "";
    		if(obj.serviceName != null) {
    			serviceName = obj.serviceName;
    		}
    		var textField = "";
    		if(obj.textField != null) {
    			textField = obj.textField;
    		}
    		var associationWith = "";
    		if(obj.associationWith != null) {
    			associationWith = obj.associationWith;
    		}
    		var status = "";
    		if(obj.status != null) {
    			status = obj.status;
    		}
    		
    		tableValue = tableValue + ("<td>"+(serviceName)+"</td>");
    		tableValue = tableValue + ("<td>"+(textField)+"</td>");
    		tableValue = tableValue + ("<td>"+(associationWith)+"</td>");
    		tableValue = tableValue + ("<td>"+(status)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.serviceId)+"')>Update</a> / <a href='#' onclick=deleteService('"+(obj.serviceId)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#serviceData").html(tableValue);
	} else {
		$("#serviceData").html("No records found.");		
	}
}

function deleteService(serviceId){
	  blockUI();
	  $.ajax({url: BASE_URL + "deleteservice/" + serviceId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillServiceData(list);
					
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
		//document.getElementById("frm1").action = "updateservice";
		document.getElementById("btnSave").value = "Update";
		$("#modelTitle").html("Edit Service");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnSave").value = "Save";
		$("#modelTitle").html("Add Service");
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
        		$.get("service/getopenadd", function(data) {
        			unblockUI()
     	           
    	            var textField = document.getElementById("textFieldId");
    	            for(var i = 0;i < data.textFieldList.length;i++) {
    	            	textField.options[textField.options.length] = new Option(data.textFieldList[i].typeName);
    	            	textField.options[i].value = data.textFieldList[i].typeId;
    	            } 
    	            
    	            var associationWith = document.getElementById("associationId");
    	            for(var i = 0;i < data.associatedWithList.length;i++) {
    	            	associationWith.options[associationWith.options.length] = new Option(data.associatedWithList[i].typeName);
    	            	associationWith.options[i].value = data.associatedWithList[i].typeId;
    	            }
    	            
    	            var status = document.getElementById("statusId");
    	            for(var i = 0;i < data.statusList.length;i++) {
    	            	status.options[status.options.length] = new Option(data.statusList[i].status);
    	            	status.options[i].value = data.statusList[i].id;
    	            }
    	        });        		
        	} else {
        		blockUI()
        		$.get("getservice/serviceId",{"serviceId" : quesId}, function(data) {
        			unblockUI()
        			document.getElementById("serviceid").value = data.serviceId;
                    $("#serviceName").val(data.serviceName);

                    var textField = document.getElementById("textFieldId");
                    var textFieldList = data.textFieldList;
                    for(var i = 0;i < textFieldList.length;i++) {
                    	textField.options[textField.options.length] = new Option(textFieldList[i].typeName);
                    	textField.options[i].value = textFieldList[i].typeId;
                    	if(textFieldList[i].typeId == data.textFieldId) {
                    		document.getElementById("textFieldId").selectedIndex = i;
                    	}
                    }
                    
                    var association = document.getElementById("associationId");
                    var associationList = data.associatedWithList;
                    for(var i = 0;i < associationList.length;i++) {
                    	association.options[association.options.length] = new Option(associationList[i].typeName);
                    	association.options[i].value = associationList[i].typeId;
                    	if(associationList[i].typeId == data.associationWithId) {
                    		document.getElementById("associationId").selectedIndex = i;
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
        function clearAll() {
           	$("#serviceName").val("");
        	document.getElementById("textFieldId").innerHTML = "";
        	document.getElementById("associationId").innerHTML = "";
        	document.getElementById("statusId").innerHTML = "";
        }
</script>

<script type="text/javascript">
function check() {
	var serviceName = $("#serviceName").val();
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if(serviceName == "") {
		msg.show();
		msgvalue.text("ServiceName cannot be left blank.");
		$("#serviceName").focus();
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
						<b class = "pageHeading">Service</b>
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
						<input type="hidden" id = "serviceid" name= "serviceid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add Service</p></h4>
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
													 <b>ServiceName</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter ServiceName" id="serviceName" name="serviceName" value="" autofocus />
											</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>TextField</b>												
													</span>
													<select class="form-control" name="textFieldId" id="textFieldId">
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
														 <b>AssociationWith</b>												
													</span>
													<select id="associationId" class="form-control" name="associationWithId">
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
														 <b>Status</b>												
													</span>
													<select id="statusId" class="form-control" name="statusId">
													</select>
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
						<th>Service</th>
						<th>TextField</th>
						<th>Association With</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody id="serviceData">
				<c:choose>
					<c:when test="${LIST_SERVICE.size() > 0}">
						<c:forEach items="${LIST_SERVICE}" var="obj">
							<tr class="info">
								<td>${obj.serviceName}</td>							
								<td>${obj.textField}</td>
								<td>${obj.associationWith}</td>
								<td>${obj.status}</td>
								<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.serviceId}')">Update</a> / <a href="deleteservice/${obj.serviceId}">Delete</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="4">No records found.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>