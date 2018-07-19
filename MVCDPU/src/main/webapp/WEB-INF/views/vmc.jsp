<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VMC</title>
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
		createVMC('savevmc','POST');
	} else if(flag == 'update') {
		createVMC('updatevmc','PUT');			
	}
}
function createVMC(urlToHit,methodType){
	 
	 if(!check()){
		 return false;
	 }
	 
	 	var name = $("#name").val();
	 	var desc = $("#description").val();
	 	
	   	var vmcId;
	   	if(methodType == 'PUT') {
	   		vmcId = $('#vmcid').val();
	   	}
	   	
	   	blockUI()
		  $.ajax({url: BASE_URL + urlToHit,
			      type:"POST",
			      data:{
			    	name:name,
			    	description:desc,
			    	vmcid:vmcId,
			      },
			      success: function(result){
		        try{
		        	$('#myModal').modal('toggle');
		        	var list = result.resultList;
					fillVMCData(list);

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

function fillVMCData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var name = "";
    		if(obj.name != null) {
    			name = obj.name;
    		}
    		var description = "";
    		if(obj.description != null) {
    			description = obj.description;
    		}
    		
    		tableValue = tableValue + ("<td>"+(name)+"</td>");
    		tableValue = tableValue + ("<td>"+(description)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.id)+"')>Update</a> / <a href='#' onclick=deleteVMC('"+(obj.id)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#vmcData").html(tableValue);
	} else {
		$("#vmcData").html("No records found.");		
	}
}

function deleteVMC(terminalId){
	  blockUI();
	  $.ajax({url: BASE_URL + "deletevmc/" + terminalId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillVMCData(list);
					
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
			  $("#frmSearch").attr("action", "showvmc");
			});
			$('#frmSearch').submit();
		});
	});
</script>
<script type="text/javascript">
function checkFlag(field) {
	document.getElementById("addUpdateFlag").value = field;
	if(field == 'update') {
		//document.getElementById("frm1").action = "updatevmc";
		document.getElementById("btnSave").value = "Update";
		$("#modelTitle").html("Edit VMC");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnSave").value = "Save";
		$("#modelTitle").html("Add VMC");
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
        	if(quesId != 0) {
        		blockUI()
        		$.get("getvmc/vmcId",{"vmcId" : quesId}, function(data) {
        			unblockUI()
        			document.getElementById("vmcid").value = data.id;
                    $("#name").val(data.name);
                    $("#description").val(data.description);
               	});
        	}
        }
        
        function clearAll() {
           	$("#name").val("");
           	$("#description").val("");
        }
</script>

 <script type="text/javascript">
function check() {
	var vmcName = $("#name").val();
	var description = $("#description").val();
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if(vmcName == "") {
		msg.show();
		msgvalue.text("VMCName cannot be left blank.");
		$("#name").focus();
		return false;
	}
	if(description == "") {
		msg.show();
		msgvalue.text("Description cannot be left blank.");
		$("#description").focus();
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
						<b class = "pageHeading">Vehicle Maintenance Category (VMC)</b>
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
 						<input type="hidden" id = "vmcid" name= "vmcid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add VMC</p></h4>
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
														<b>VMC</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter VMC Name" id="name" name="name" value="" autofocus />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
												<span class="input-group-addon">
														 <b>Description</b>												
													</span>
													<textarea class="form-control" rows="1" cols="1" placeholder="Enter Description" name="description" id = "description"></textarea>
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
						<th>Name</th>
						<th>Description</th>
						<th>Links</th>
					</tr>
				</thead>
				<tbody id="vmcData">
				<c:choose>
					<c:when test="${LIST_VMC.size() > 0}">
						<c:forEach items="${LIST_VMC}" var="obj">
							<tr class="info">
								<td>${obj.name}</td>							
								<td>${obj.description}</td>
								<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.id}')">Update</a> / <a href="#" onclick="deleteVMC('${obj.id}')">Delete</a></td>
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