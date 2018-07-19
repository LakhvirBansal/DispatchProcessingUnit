<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Issue</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<link rel="stylesheet"
	href=" //maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src=" //code.jquery.com/jquery-1.12.4.js"></script>
<script src=" //code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src=" //maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	 <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.min.js"></script>
	 <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css" />
 	<!--  <script type="text/javascript" src="/WEB-INF/bootstrap-typeahead.js">
 	</script>-->
  
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

  height:100px; 
  min-height:100px;  
  max-height:100px;
}
.ui-autocomplete{
z-index: 99999!important;
}
.multiselect-container>li>a>label {
  padding: 4px 30px 3px 20px;
  margin-left:5px;
}

.multiselect-container>li{
margin-left: 10px;
}

#unitNo{
    max-height: 33px;
    max-width: 130px
}
</style>
	<jsp:include page="Include.jsp"></jsp:include>
 <script src="<c:url value="/resources/validations.js" />"></script>
<script type="text/javascript">
function navigate(isSaveAndNew) {
	var flag = $("#addUpdateFlag").val();
	if(!check()){
		 return false;
	 }
	if(flag == 'add') {
		createIssue('saveissue','POST');

		if(isSaveAndNew) {
    		$("#title").val("");
            $("#description").val("");
           	document.getElementById("vmcId").options[0].selected = true;
        	document.getElementById("unitType").options[0].selected = true;
        	document.getElementById("issueCategory").options[0].selected = true;
        	$('#unitNo').multiselect('destroy');
			$("#unitNo").html("<option value='0'>Please Select</option>");
        	document.getElementById("reportedBy").options[0].selected = true;
        	document.getElementById("status").options[0].selected = true;
    	} else {
    		$('#myModal').modal('toggle');
    	}
	} else if(flag == 'update') {
		createIssue('updateissue','PUT');	
		$('#myModal').modal("toggle")
	}
}

function createIssue(urlToHit,methodType){
	 
 	var title = $("#title").val();
   	var vmcId = $('#vmcId :selected').val();
   	var unitType = $('#unitType :selected').val();
   	var issueCategory = $('#issueCategory :selected').val();
   	var unitNo = $('#unitNo :selected').val();
   	var reportedBy = $('#reportedBy :selected').val();
   	var status = $('#status :selected').val();
 	var description = $("#description").val();
   	
 	var issueId;
   	if(methodType == 'PUT') {
   		issueId = $('#issueid').val();
   	}
   	
   	blockUI()
	  $.ajax({url: BASE_URL + urlToHit,
	      type:"POST",
	      data:{
	    	title:title,
	    	vmcId:vmcId,
	    	unitTypeId:unitType,
	    	categoryId:issueCategory,
	    	unitNo:unitNo,
	    	reportedById:reportedBy,
	    	statusId:status,
	    	description:description,
	    	issueid:issueId
	      },
	      success: function(result){
        try{
        	
        	var list = result.resultList;
			fillIssueData(list);

	        toastr.success(result.message, 'Success!')
		} catch(e){
			unblockUI();
			toastr.error('Something went wrong', 'Error!')
		}
	  },error:function(result){
		  try{
			  unblockUI();
	        	$("#btnNew").click(function() {
	        		onClickMethodQuestion('0');
	        	});
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

function fillIssueData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var title = "";
    		if(obj.title != null) {
    			title = obj.title;
    		}
    		var vmc = "";
    		if(obj.vmcName != null) {
    			vmc = obj.vmcName;
    		}
    		var unitTypeName = "";
    		if(obj.unitTypeName != null) {
    			unitTypeName = obj.unitTypeName;
    		}
    		var unitNo = "";
    		if(obj.unitNo != null) {
    			unitNo = obj.unitNo;
    		}
    		var reportedByName = "";
    		if(obj.reportedByName != null) {
    			reportedByName = obj.reportedByName;
    		}
    		var statusName = "";
    		if(obj.statusName != null) {
    			statusName = obj.statusName;
    		}
    		
    		tableValue = tableValue + ("<td>"+(title)+"</td>");
    		tableValue = tableValue + ("<td>"+(vmc)+"</td>");
    		tableValue = tableValue + ("<td>"+(unitTypeName)+"</td>");
    		tableValue = tableValue + ("<td>"+(unitNo)+"</td>");
    		tableValue = tableValue + ("<td>"+(reportedByName)+"</td>");
    		tableValue = tableValue + ("<td>"+(statusName)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.id)+"')>Update</a> / <a href='#' onclick=deleteIssue('"+(obj.id)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#issueData").html(tableValue);
	} else {
		$("#issueData").html("No records found.");		
	}
}

function deleteIssue(terminalId){
	  blockUI();
	  $.ajax({url: BASE_URL + "deleteissue/" + terminalId,
		      type:"GET",
		      success: function(result){
	    	  try{	
				  var list = result.resultList;
				  fillIssueData(list);
					
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
		//document.getElementById("frm1").action = "updateissue";
		document.getElementById("btnNew").value = "Update";
		//$("#btnExit").hide();
		$("#modelTitle").html("Edit Issue");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnNew").value = "Save&New";
		//$("#btnExit").show();
		$("#modelTitle").html("Add Issue");
	} else if (field == 'search') {
		/* document.getElementById("frm1").method = "GET";
		document.getElementById("frm1").action = "showques";
		document.getElementById("frm1").submit(); */
	}
}
</script>

<script type="text/javascript">

		function getUnitNo() {
			var unitTypeId = $('#unitType :selected').val();
			var categoryId = $('#issueCategory :selected').val();
			
			if(unitTypeId > 0 && categoryId > 0) {
				$.get("issue/getunitno/category/"+categoryId+"/unittype/"+unitTypeId, function(data) {
	 	           
		            var unitNo = document.getElementById("unitNo");
		            $("#unitNo").empty();

		            if(data.unitNos.length > 0) {
			            for(var i = 0;i < data.unitNos.length;i++) {
			            	unitNo.options[unitNo.options.length] = new Option(data.unitNos[i]);
			            	unitNo.options[i].value = data.unitNos[i];
			            }
		            } else {
						toastr.error('No such UnitNo. exists for selected UnitType and Category', 'Error!')
		            }
				});
			}
		}

        function onClickMethodQuestion(quesId){
        	emptyMessageDiv();
        	clearAll();
        	if(quesId == 0) {
        		blockUI()
        		$("#btnClose").show();
            	$.get("issue/getopenadd", function(data) {
            		unblockUI()
            		
    	            var vmc = document.getElementById("vmcId");
    	            for(var i = 0;i < data.vmcList.length;i++) {
    	            	vmc.options[vmc.options.length] = new Option(data.vmcList[i].name);
    	            	vmc.options[i+1].value = data.vmcList[i].id;
    	            }
            	
    	            var unitType = document.getElementById("unitType");
    	            for(var i = 0;i < data.unitTypeList.length;i++) {
    	            	unitType.options[unitType.options.length] = new Option(data.unitTypeList[i].typeName);
    	            	unitType.options[i+1].value = data.unitTypeList[i].typeId;
    	            }
    	            
    	            var reportedBy = document.getElementById("reportedBy");
    	            for(var i = 0;i < data.reportedByList.length;i++) {
    	            	reportedBy.options[reportedBy.options.length] = new Option(data.reportedByList[i].fullName);
    	            	reportedBy.options[i+1].value = data.reportedByList[i].driverId;
    	            }
    	            
    	           /*  var category = document.getElementById("issueCategory");
    	            for(var i = 0;i < data.categoryList.length;i++) {
    	            	category.options[category.options.length] = new Option(data.categoryList[i].name);
    	            	category.options[i].value = data.categoryList[i].categoryId;
    	            } */
    	            
    	            var status = document.getElementById("status");
    	            for(var i = 0;i < data.statusList.length;i++) {
    	            	status.options[status.options.length] = new Option(data.statusList[i].typeName);
    	            	status.options[i+1].value = data.statusList[i].typeId;
    	            }
    	            
    	            getUnitNo();
    	            
    	        });
        	} else {
        		blockUI()
        		$("#btnClose").hide();
        		 $.get("getissue/issueId",{"issueId" : quesId}, function(data) {
        			unblockUI()
        			document.getElementById("issueid").value = data.id;
                    $("#title").val(data.title);
                    $("#description").val(data.description);                    
                    var vmc = document.getElementById("vmcId");
                    var vmcList = data.vmcList;
                    for(var i = 0;i < vmcList.length;i++) {
                    	vmc.options[vmc.options.length] = new Option(vmcList[i].name);
                    	vmc.options[i+1].value = vmcList[i].id;
                    	if(vmcList[i].id == data.vmcId) {
                    		document.getElementById("vmcId").selectedIndex = i+1;
                    	}
                    }
                    
                    var unitType = document.getElementById("unitType");
                    var unitTypeList = data.unitTypeList;
                    for(var i = 0;i < unitTypeList.length;i++) {
                    	unitType.options[unitType.options.length] = new Option(unitTypeList[i].typeName);
                    	unitType.options[i+1].value = unitTypeList[i].typeId;
                    	if(unitTypeList[i].typeId == data.unitTypeId) {
                    		document.getElementById("unitType").selectedIndex = i+1;
                    	}
                    }
                    
                    var category = document.getElementById("issueCategory");
                    var categoryList = data.categoryList;
                    if(categoryList != null && categoryList.length > 0) {
	                   for(var i = 0;i < categoryList.length;i++) {
	                   	category.options[category.options.length] = new Option(categoryList[i].name);
	                   	category.options[i+1].value = categoryList[i].categoryId;
	                   	if(categoryList[i].categoryId == data.categoryId) {
	                   		document.getElementById("issueCategory").selectedIndex = i+1;
	                   	}
	                   }
                    }
                    
                    var unitNo = document.getElementById("unitNo");
                    var unitNos = data.unitNos;
                    if(unitNos != null && unitNos.length > 0) {
	                    for(var i = 0;i < unitNos.length;i++) {
	                    	unitNo.options[unitNo.options.length] = new Option(unitNos[i]);
	                    	unitNo.options[i+1].value = unitNos[i];
	                    	if(unitNos[i] == data.unitNo) {
	                    		document.getElementById("unitNo").selectedIndex = i+1;
	                    	}
	                    }
                    }
                    
                    var reportedBy = document.getElementById("reportedBy");
                    var reportedByList = data.reportedByList;
                    if(reportedByList != null && reportedByList.length > 0) {
	                    for(var i = 0;i < reportedByList.length;i++) {
	                    	reportedBy.options[reportedBy.options.length] = new Option(reportedByList[i].fullName);
	                    	reportedBy.options[i+1].value = reportedByList[i].driverId;
	                    	if(reportedByList[i].driverId == data.reportedById) {
	                    		document.getElementById("reportedBy").selectedIndex = i+1;
	                    	}
	                    }
                    }
                    
                    var status = document.getElementById("status");
                    var statusList = data.statusList;
                    if(statusList != null && statusList.length > 0) {
	    	            for(var i = 0;i < data.statusList.length;i++) {
	    	            	status.options[status.options.length] = new Option(data.statusList[i].typeName);
	    	            	status.options[i+1].value = data.statusList[i].typeId;
	    	            	if(statusList[i].typeId == data.statusId) {
	                    		document.getElementById("status").selectedIndex = i+1;
	                    	}
	    	            }
                    }
               	}); 
        	}
        }
        
        function clearAll() {
            $("#title").val("");
            $("#description").val("");
           	document.getElementById("vmcId").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("unitType").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("issueCategory").innerHTML = "<option value='0'>Please Select</option>";
       	    $("#unitNo").html("<option value='0'>Please Select</option>");
        	document.getElementById("reportedBy").innerHTML = "<option value='0'>Please Select</option>";
        	document.getElementById("status").innerHTML = "<option value='0'>Please Select</option>";
        }
</script>

<script type="text/javascript">
function check() {
	var title = $("#title").val();
	var description = $("#description").val();
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if(title == "") {
		msg.show();
		msgvalue.text("Title cannot be left blank.");
		$("#title").focus();
		return false;
	} else if(description == "") {
		msg.show();
		msgvalue.text("Description cannot be left blank.");
		$("#description").focus();
		return false;
	}
	return true;
}
function emptyMessageDiv(){
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
}

function getCategories() {
	 var unitTypeName = $('#unitType :selected').text();

	 if(unitTypeName != "Please Select") {
		 
		 blockUI()
		 $.get("getcategories/unittype/"+unitTypeName, function(data) {
	      
			  unblockUI()
		      var category = document.getElementById("issueCategory");
		      $("#issueCategory").empty();
		      category.options[0] = new Option("Please Select");		      
		      category.options[0].value = "0";
		      if(data != null && data.length > 0) {
		    	  for(var i = 0;i < data.length;i++) {
			    	  category.options[category.options.length] = new Option(data[i].name);
			    	  category.options[i+1].value = data[i].categoryId;
			      }
		      } else {
		    	  var category = document.getElementById("issueCategory");
			      $("#issueCategory").empty();
			      category.options[0] = new Option("Please Select");
				  toastr.error("No such Category exist for this Unit Type", 'Error!');
		      }
		 });
	 } else {
		 var category = document.getElementById("issueCategory");
	      $("#issueCategory").empty();
	      category.options[0] = new Option("Please Select");
	 }
}

//To-Do
function getOnlyUnitNos() {
	
	 var unitTypeId = $('#unitType :selected').val();
	 var categoryId = $('#issueCategory :selected').val();
	 
	if(categoryId == "Please Select") {
		categoryId = 0;
	}
	if(unitTypeId != "Please Select") {

		blockUI()
		$.get("<%=request.getContextPath()%>"+"/issue/getunitno/category/" + categoryId + "/unittype/" + unitTypeId, function(data) {
	        
			 unblockUI()
			 var unitNo = document.getElementById("unitNo");
	       	    $("#unitNo").html("<option value='0'>Please Select</option>");
	         if(data.unitNos != null && data.unitNos.length > 0) {
	        	 for(var i = 0;i < data.unitNos.length;i++) {
                   	unitNo.options[unitNo.options.length] = new Option(data.unitNos[i]);
                   	unitNo.options[i+1].value = data.unitNos[i];
                 }
	         } else {
				toastr.error('No such UnitNo. exists for selected UnitType and Category', 'Error!')
	        	 $("#unitNo").html("<option value='0'>Please Select</option>");
	         }
	    });
	} else {
		$("#unitNo").html("<option value='0'>Please Select</option>");
   	}
}

function getOnlyUnitNosOnUnitTypeChange() {
 	
	 var unitTypeId = $('#unitType :selected').val();
	 var categoryId = 0;
	 
	if(unitTypeId != "Please Select") {

		blockUI()
		$.get("<%=request.getContextPath()%>"+"/issue/getunitno/category/" + categoryId + "/unittype/" + unitTypeId, function(data) {
	        
			 unblockUI()
			 var unitNo = document.getElementById("unitNo");
	       	    $("#unitNo").html("<option value='0'>Please Select</option>");
	         if(data.unitNos != null && data.unitNos.length > 0) {
	        	 for(var i = 0;i < data.unitNos.length;i++) {
                   	unitNo.options[unitNo.options.length] = new Option(data.unitNos[i]);
                   	unitNo.options[i+1].value = data.unitNos[i];
                 }
	         } else {
				toastr.error('No such UnitNo. exists for selected UnitType and Category', 'Error!')
				$("#unitNo").html("<option value='0'>Please Select</option>");
	         }
	    });
	} else {
		$("#unitNo").html("<option value='0'>Please Select</option>");
	}
}
</script>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<div class="form-group">
				<div class="row">
					<div class="col-sm-12" align="center">
						<b class = "pageHeading">Issues</b>
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
						<input type="hidden" id = "issueid" name= "issueid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add Issue</p></h4>
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
												<div class="input-group ui-widget"  >
												<span class="input-group-addon">
													 <b>Title</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter Title" id="title" name="title" value="" autofocus />
											</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<!-- <span class="input-group-addon">
														 <b>VMC</b>												
													</span>
													<input type="text" placeHolder="Enter VMC" id="vmcId" name="vmcId" class="form-control"/>
													<input type="hidden" id="vmcIdhidden" /> -->
													<span class="input-group-addon">
														 <b>VMC</b>												
													</span>
													<select class="form-control" name="vmcId" id="vmcId">
														<option value="0">Please Select</option>
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
														 <b>UnitType</b>												
													</span>
													<select id="unitType" class="form-control" name="unitTypeId" onchange="getCategories();getOnlyUnitNosOnUnitTypeChange();">
														<option value = "0">Please Select</option>
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
														 <b>Category</b>												
													</span>
													<select id="issueCategory" class="form-control" name="categoryId" onchange="getOnlyUnitNos();">
														<option value = "0">Please Select</option>
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
														 <b>UnitNo</b>												
													</span>
													<select id="unitNo" class="form-control" name="unitNo">
														<option value = "0">Please select</option>
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
														 <b>ReportedBy</b>												
													</span>
													<select id="reportedBy" class="form-control" name="reportedById">
														<option value = "0">Please Select</option>
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
													<select id="status" class="form-control" name="statusId">
														<option value = "0">Please Select</option>
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
					          <input type="button" class="btn btn-primary" id= "btnNew" value="Save&New" onclick="navigate(true)"/>
					          <input type="button" class="btn btn-primary" id= "btnClose" value="Save&Close" onclick="navigate(false)"/>
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
						<th>Title</th>
						<th>VMC</th>
						<th>Type</th>
						<th>UnitNo</th>
						<th>ReportedBy</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody id="issueData">
					<c:choose>
						<c:when test="${LIST_ISSUE.size() > 0}">
							<c:forEach items="${LIST_ISSUE}" var="obj">
								<tr class="info">
									<td>${obj.title}</td>							
									<td>${obj.vmcName}</td>
									<td>${obj.unitTypeName}</td>
									<td>${obj.unitNo}</td>
									<td>${obj.reportedByName}</td>
									<td>${obj.statusName}</td>
									<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.id}')">Update</a> / <a href="#" onclick="deleteIssue('${obj.id}')">Delete</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td colspan="7">No records found.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>