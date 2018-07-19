<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trailer</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
 <jsp:include page="Include.jsp"></jsp:include>
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
		createTrailer('savetrailer','POST');
	} else if(flag == 'update') {
		createTrailer('updatetrailer','PUT');			
	}
}
function createTrailer(urlToHit,methodType){
	 
	 if(!check()){
		 return false;
	 }
	 var unitNo = $("#unitNo").val();
	   	var usage = $("#usage").val();
	   	var owner = $("#owner").val();
	   	var oOName = $("#oOName").val();
	   	var finance = $("#finance").val();
	   	var statusId = $('#statusId :selected').val();
	   	var divisionId = $('#divisionId :selected').val();
	   	var terminalId = $('#terminalId :selected').val();
	   	var categoryId = $('#categoryId :selected').val();
	   	var trailerTypeId = $('#trailerTypeId :selected').val();
	  	var trailerId;
	   	if(methodType == 'PUT') {
	   		trailerId = $('#trailerid').val();
	   	}
	   	
	   	blockUI()
		  $.ajax({url: BASE_URL+ urlToHit,
			      type:"POST",
			      data:{
			    	unitNo:unitNo,
			    	usage:usage,
			    	owner:owner,
			    	oOName:oOName,
			    	finance:finance,
			    	statusId:statusId,
			    	divisionId:divisionId,
			    	terminalId:terminalId,
			    	categoryId:categoryId,
			    	trailerTypeId:trailerTypeId,
			    	trailerid:trailerId
			      },
			      success: function(result){
		        try{
		        	
		        	$('#myModal').modal('toggle');
		        	var list = result.resultList;
					fillTrailerData(list);

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

function fillTrailerData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var unitNo = "";
    		if(obj.unitNo != null) {
    			unitNo = obj.unitNo;
    		}
    		var owner = "";
    		if(obj.owner != null) {
    			owner = obj.owner;
    		}
    		var oOName = "";
    		if(obj.oOName != null) {
    			oOName = obj.oOName;
    		}
    		var category = "";
    		if(obj.catogory != null) {
    			category = obj.catogory;
    		}
    		var status = "";
    		if(obj.status != null) {
    			status = obj.status;
    		}
    		var usage = "";
    		if(obj.usage != null) {
    			usage = obj.usage;
    		}
    		var divisionName = "";
    		if(obj.division != null) {
    			divisionName = obj.division;
    		}
    		var terminal = "";
    		if(obj.terminal != null) {
    			terminal = obj.terminal;
    		}
    		var trailerType = "";
    		if(obj.trailerType != null) {
    			trailerType = obj.trailerType;
    		}
    		var finance = "";
    		if(obj.finance != null) {
    			finance = obj.finance;
    		}
    		
    		tableValue = tableValue + ("<td>"+(unitNo)+"</td>");
    		tableValue = tableValue + ("<td>"+(owner)+"</td>");
    		tableValue = tableValue + ("<td>"+(oOName)+"</td>");
    		tableValue = tableValue + ("<td>"+(category)+"</td>");
    		tableValue = tableValue + ("<td>"+(status)+"</td>");
    		tableValue = tableValue + ("<td>"+(usage)+"</td>");
    		tableValue = tableValue + ("<td>"+(divisionName)+"</td>");
    		tableValue = tableValue + ("<td>"+(terminal)+"</td>");
    		tableValue = tableValue + ("<td>"+(trailerType)+"</td>");
    		tableValue = tableValue + ("<td>"+(finance)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.trailerId)+"')>Update</a> / <a href='#' onclick=deleteTrailer('"+(obj.trailerId)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#trailerData").html(tableValue);
	} else {
		$("#trailerData").html("No records found.");		
	}
}

function deleteTrailer(trailerId){
	 
	  blockUI()
	  $.ajax({url: BASE_URL + "deletetrailer/" + trailerId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillTrailerData(list);
					
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
</script>
<script type="text/javascript">
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
		document.getElementById("frm1").action = "updatetrailer";
		document.getElementById("btnSave").value = "Update";
		$("#modelTitle").html("Edit Trailer");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnSave").value = "Save";
		$("#modelTitle").html("Add Trailer");
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
        		$.get("trailer/getopenadd", function(data) {
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
    	            
    	            var trailerType = document.getElementById("trailerTypeId");
    	            for(var i = 0;i < data.trailerTypeList.length;i++) {
    	            	trailerType.options[trailerType.options.length] = new Option(data.trailerTypeList[i].typeName);
    	            	trailerType.options[i+1].value = data.trailerTypeList[i].typeId;
    	            }
    	        });
        	} else {
        		blockUI()

        		$.get("gettrailer/trailerId",{"trailerId" : quesId}, function(data) {
            		unblockUI()

        			document.getElementById("trailerid").value = data.trailerId;
                   	$("#unitNo").val(data.unitNo);
                   	$("#usage").val(data.usage);
                   	$("#owner").val(data.owner);
                   	$("#oOName").val(data.oOName);
                   	$("#finance").val(data.finance);
                   	
                   	var division = document.getElementById("divisionId");
                    var divisionList = data.divisionList;
                    for(var i = 0;i < divisionList.length;i++) {
                    	division.options[division.options.length] = new Option(divisionList[i].divisionName);
                    	division.options[i+1].value = divisionList[i].divisionId;
                    	if(divisionList[i].divisionId == data.divisionId) {
                    		document.getElementById("divisionId").selectedIndex = i+1;
                    	}
                    }
                    
                    var terminal = document.getElementById("terminalId");
                    var terminalList = data.terminalList;
                    for(var i = 0;i < terminalList.length;i++) {
                    	terminal.options[terminal.options.length] = new Option(terminalList[i].terminalName);
                    	terminal.options[i+1].value = terminalList[i].terminalId;
                    	if(terminalList[i].terminalId == data.terminalId) {
                    		document.getElementById("terminalId").selectedIndex = i+1;
                    	}
                    }
                    
                    var trailerType = document.getElementById("trailerTypeId");
                    var trailerTypeList = data.trailerTypeList;
                    for(var i = 0;i < trailerTypeList.length;i++) {
                    	trailerType.options[trailerType.options.length] = new Option(trailerTypeList[i].typeName);
                    	trailerType.options[i+1].value = trailerTypeList[i].typeId;
                    	if(trailerTypeList[i].typeId == data.trailerTypeId) {
                    		document.getElementById("trailerTypeId").selectedIndex = i+1;
                    	}
                    }
                    
                    var category = document.getElementById("categoryId");
                    var categoryList = data.categoryList;
                    for(var i = 0;i < categoryList.length;i++) {
                    	category.options[category.options.length] = new Option(categoryList[i].name);
                    	category.options[i+1].value = categoryList[i].categoryId;
                    	if(categoryList[i].categoryId == data.categoryId) {
                    		document.getElementById("categoryId").selectedIndex = i+1;
                    	}
                    }
                    
                    var status = document.getElementById("statusId");
                    var statusList = data.statusList;
                    for(var i = 0;i < statusList.length;i++) {
                    	status.options[status.options.length] = new Option(statusList[i].status);
                    	status.options[i+1].value = statusList[i].id;
                    	if(statusList[i].id == data.statusId) {
                    		document.getElementById("statusId").selectedIndex = i+1;
                    	}
                    }
               	});
        	}
        }
        
        function clearAll() {
        	$("#unitNo").val("");
           	$("#usage").val("");
           	$("#owner").val("");
           	$("#oOName").val("");
           	$("#finance").val("");
           	document.getElementById("statusId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("divisionId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("terminalId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("categoryId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("trailerTypeId").innerHTML = "<option value='0'>Please Select</option>";
        }
</script>

<script type="text/javascript">
function check() {
	var unitNo = $("#unitNo").val();
	var usage = $("#usage").val();
	var owner = $("#owner").val();
	var oOName = $("#oOName").val();
	var finance = $("#finance").val();
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if(unitNo == "") {
		msg.show();
		msgvalue.text("UnitNo cannot be left blank.");
		$("#unitNo").focus();
		return false;
	}
	/* if(!isNumeric(unitNo)) {
		msg.show();
		msgvalue.text("Only numerics allowed in UnitNo");
		$("#unitNo").focus();
		return false;
	} */
	/* if(usage == "") {
		msg.show();
		msgvalue.text("Usage cannot be left blank.");
		$("#usage").focus();
		return false;
	}
	if(owner == "") {
		msg.show();
		msgvalue.text("Owner cannot be left blank.");
		$("#owner").focus();
		return false;
	}
	if(oOName == "") {
		msg.show();
		msgvalue.text("OOName cannot be left blank.");
		$("#oOName").focus();
		return false;
	}
	if(finance == "") {
		msg.show();
		msgvalue.text("Finance cannot be left blank.");
		$("#finance").focus();
		return false;
	} */
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
						<b class = "pageHeading">Trailers</b>
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
						<input type="hidden" id = "trailerid" name= "trailerid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add Trailer</p></h4>
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
													 <b>UnitNo</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter UnitNo" id="unitNo" name="unitNo" value="" autofocus />
											</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Usage</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Usage" id="usage" name="usage" value="" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Owner</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Owner" id="owner" name="owner" value="" />
												</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														<b>Division</b>											
													</span>
													<select class="form-control" name="divisionId" id="divisionId">
														<option value="0">Please Select</option>
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
														 <b>OOName</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter oOName" id="oOName" name="oOName" value="" />
												</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														<b>Terminal</b>												
													</span>
													<select class="form-control" name="terminalId" id="terminalId">
														<option value="0">Please Select</option>
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
														<b>Category</b>												
													</span>
													<select class="form-control" name="categoryId" id="categoryId">
														<option value="0">Please Select</option>
													</select>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														<b>TrailerType</b>												
													</span>
													<select class="form-control" name="trailerTypeId" id="trailerTypeId">
														<option value="0">Please Select</option>
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
														<b>Status</b>												
													</span>
													<select class="form-control" name="statusId" id="statusId">
														<option value="0">Please Select</option>
													</select>
												</div>
											</div>	
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Finance</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Finance" id="finance" name="finance" value="" />
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
						<th>Unit No</th>
						<th>Owner</th>
						<th>O/O's Name</th>
						<th>Category</th>
						<th>Status</th>
						<th>Usage</th>
						<th>Division</th>
						<th>Terminal</th>
						<th>Trailer Type</th>
						<th>Finance</th>
						<th>Links</th>
					</tr>
				</thead>
				<tbody id="trailerData">
				<c:choose>
					<c:when test="${LIST_TRAILER.size() > 0}">
						<c:forEach items="${LIST_TRAILER}" var="obj">
							<tr class="info">
								<td>${obj.unitNo}</td>							
								<td>${obj.owner}</td>
								<td>${obj.oOName}</td>
								<td>${obj.category}</td>
								<td>${obj.status}</td>
								<td>${obj.usage}</td>
								<td>${obj.division}</td>
								<td>${obj.terminal}</td>
								<td>${obj.trailerType}</td>
								<td>${obj.finance}</td>
								<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.trailerId}')">Update</a> / <a href="#" onclick="deleteTrailer('${obj.trailerId}')">Delete</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="11">No records found.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>