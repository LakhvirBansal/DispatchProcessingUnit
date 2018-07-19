<%@page import="com.dpu.model.DivisionReq"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Division</title>
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
		createDivision('savedivision','POST');
	} else if(flag == 'update') {
		createDivision('updatedivision','PUT');			
	}
}
function createDivision(urlToHit,methodType){
	 
	 if(!check()){
		 return false;
	 }
	 
	 	var divisionCode = $("#divisionCode").val();
	   	var divisionName = $("#divisionName").val();
	   	var federal = $("#federal").val();
	   	var provincial = $("#provincial").val();
	   	var scac = $("#scac").val();
	   	var carrierCode = $("#carrierCode").val();
	   	var contractPrefix = $("#contractPrefix").val();
	   	var invoicePrefix = $("#invoicePrefix").val();
	   	var statusId = $('#status :selected').val();
	  	var divisionId;
	   	if(methodType == 'PUT') {
	   		divisionId = $('#divisionid').val();
	   	}
	   
	   	blockUI()
		  $.ajax({url: BASE_URL+ urlToHit,
			      type:"POST",
			      data:{
			    	divisionCode:divisionCode,
			    	divisionName:divisionName,
			    	fedral:federal,
			    	provincial:provincial,
			    	scac:scac,
			    	statusId:statusId,
			    	carrierCode:carrierCode,
			    	contractPrefix:contractPrefix,
			    	invoicePrefix:invoicePrefix,
			    	divisionid:divisionId
			      },
			      success: function(result){
		        try{
		        	$('#myModal').modal('toggle');
		        	var list = result.resultList;
					fillDivisionData(list);

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

function fillDivisionData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var divisionCode = "";
    		if(obj.divisionCode != null) {
    			divisionCode = obj.divisionCode;
    		}
    		var divisionName = "";
    		if(obj.divisionName != null) {
    			divisionName = obj.divisionName;
    		}
    		var federal = "";
    		if(obj.fedral != null) {
    			federal = obj.fedral;
    		}
    		var provincial = "";
    		if(obj.provincial != null) {
    			provincial = obj.provincial;
    		}
    		
    		tableValue = tableValue + ("<td>"+(divisionCode)+"</td>");
    		tableValue = tableValue + ("<td>"+(divisionName)+"</td>");
    		tableValue = tableValue + ("<td>"+(federal)+"</td>");
    		tableValue = tableValue + ("<td>"+(provincial)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.divisionId)+"')>Update</a> / <a href='#' onclick=deleteDivision('"+(obj.divisionId)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#divisionData").html(tableValue);
	} else {
		$("#divisionData").html("No records found.");		
	}
}

function deleteDivision(divisionId){
	 
	blockUI();
	  $.ajax({url: BASE_URL + "deletedivision/" + divisionId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillDivisionData(list);
					
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
		var msg = document.getElementById("used").value;
		if(msg.length > 0) {
			alert($("#used").val());
		}
		$('#btnSave').click(function(){
		//	$('#frm1').submit();
		});
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
		//document.getElementById("frm1").action = "updatedivision";
		document.getElementById("btnSave").value = "Update";
		$("#modelTitle").html("Edit Division");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnSave").value = "Save";
		$("#modelTitle").html("Add Division");
	} else if (field == 'search') {
		/* document.getElementById("frm1").method = "GET";
		document.getElementById("frm1").action = "showques";
		document.getElementById("frm1").submit(); */
	}
}
</script>
<script type="text/javascript">
            function check()
            {
            	
                var divisionCode = $("#divisionCode").val();
                var divisionName = $("#divisionName").val();
                var federal = $("#federal").val();
                var provincial = $("#provincial").val();
                var scac = $("#scac").val();
                var carrierCode = $("#carrierCode").val();
                var contractPrefix = $("#contractPrefix").val();
                var invoicePrefix = $("#invoicePrefix").val();
                
                var msgvalue = $("#msgvalue");
                var msg = $("#msg");
                msg.hide();
            	msgvalue.text("");
                if(divisionCode=="")
                {
                	msg.show();
                	msgvalue.text("DivisionCode cannot be left blank");
                    document.getElementById("divisionCode").focus();
                    return false;
                }
                if(divisionName=="")
                {
                	msg.show();
                	msgvalue.text("DivisionName cannot be left blank");
                    document.getElementById("divisionName").focus();
                    return false;
                }
                if(federal=="")
                {
                	msg.show();
                	msgvalue.text("Federal cannot be left blank");
                    document.getElementById("federal").focus();
                    return false;
                }
                if(provincial=="")
                {
                	msg.show();
                	msgvalue.text("Provincial cannot be left blank");
                    document.getElementById("provincial").focus();
                    return false;
                }
                if(scac=="")
                {
                	msg.show();
                	msgvalue.text("Scac cannot be left blank");
                    document.getElementById("scac").focus();
                    return false;
                }
                if(carrierCode=="")
                {
                	msg.show();
                	msgvalue.text("CarrierCode cannot be left blank");
                    document.getElementById("carrierCode").focus();
                    return false;
                }
                if(contractPrefix=="")
                {
                	msg.show();
                	msgvalue.text("ContractPrefix cannot be left blank");
                    document.getElementById("contractPrefix").focus();
                    return false;
                }
                if(invoicePrefix=="")
                {
                	msg.show();
                	msgvalue.text("InvoicePrefix cannot be left blank");
                    document.getElementById("invoicePrefix").focus();
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

<script type="text/javascript">
        function onClickMethodQuestion(quesId){
        	emptyMessageDiv();
        	clearAll();
        	if(quesId == 0) {
        		document.getElementById("status").innerHTML = "";
            	$.get("getStatus", function(data) {
    	           
    	            var status = document.getElementById("status");
    	            for(var i = 0;i < data.length;i++) {
    	            	//alert(data[0].status);
    	            	status.options[status.options.length] = new Option(data[i].status);
    	            	status.options[i].value = data[i].id;
    	            } 
    	        });
        	} else {
        		blockUI()
        		$.get("getdivision/divisionId",{"divisionId" : quesId}, function(data) {
        			unblockUI()
        			document.getElementById("divisionid").value = data.divisionId;
                    $("#divisionCode").val(data.divisionCode);
                   	$("#divisionName").val(data.divisionName);
                   	$("#federal").val(data.fedral);
                   	$("#provincial").val(data.provincial);
                   	$("#scac").val(data.scac);
                   	$("#carrierCode").val(data.carrierCode);
                   	$("#contractPrefix").val(data.contractPrefix);
                   	$("#invoicePrefix").val(data.invoicePrefix);
                   	
                    var status = document.getElementById("status");
                    var statusList = data.statusList;
                    for(var i = 0;i < statusList.length;i++) {
                    	status.options[status.options.length] = new Option(statusList[i].status);
                    	status.options[i].value = statusList[i].id;
                    	if(statusList[i].id == data.statusId) {
                    		document.getElementById("status").selectedIndex = i;
                    	}
                    }
               	});
        	}
        }
        
        function clearAll() {
        	$("#divisionCode").val("");
           	$("#divisionName").val("");
           	$("#federal").val("");
           	$("#provincial").val("");
           	$("#scac").val("");
           	$("#carrierCode").val("");
           	$("#contractPrefix").val("");
           	$("#invoicePrefix").val("");
           	document.getElementById("status").innerHTML = "";
        }
</script>
<script src="//cdn.ckeditor.com/4.5.11/basic/ckeditor.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="form-group">
				<div class="row">
					<div class="col-sm-12" align="center">
						<b class = "pageHeading">Divisions</b>
					</div>
				</div>
			</div>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="checkFlag('add'); onClickMethodQuestion('0'); emptyMessageDiv();" >Add New</button>
		<div class="form-group">
			<div class="row">
				<div class="col-sm-12">
				<div class="modal fade" id="myModal" role="dialog">
				    <div class="modal-dialog">

				      <!-- Modal content-->
				      	<form id="frm1">
						<input type="hidden" id = "addUpdateFlag" value = "" />
						<input type="hidden" id = "divisionid" name = "divisionid" value = "" />
						<% 
							if(request.getParameter("msg") != null) {
								
						%>
							<input type="hidden" id = "used" name = "used" value = "<%=request.getParameter("msg")%>" />								
						<%
							} else {
						%>	
							<input type="hidden" id = "used" name = "used" value ="" />																
						<%
							}
						%>      
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal">&times;</button>
				          <h4 class="modal-title"><p id ="modelTitle">Add Division</p></h4>
								<div class="alert alert-danger fade in" id="msg" style="display: none;">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong id="msgvalue"></strong>
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
													 <b>DivisionCode</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter DivisionCode" id="divisionCode" name="divisionCode" value="" autofocus />
											</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>DivisionName</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter DivisionName" id="divisionName" name="divisionName" value="" autofocus />
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
													<select class="form-control" name="statusId" id="status">
													</select>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Federal</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Federal" id="federal" name="fedral" value="" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="form-group text-left">
													<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
													Include in Management Reporting
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12">
												<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
												Include in Accounting Transfers
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Provincial</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter Provincial" id="provincial" name="provincial" value="" />
												</div>
											</div>	
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>SCAC</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter SCAC" id="scac" name="scac" value="" />
												</div>
											</div>	
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>CarrierCode</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter CarrierCode" id="carrierCode" name="carrierCode" value="" />
												</div>
											</div>	
											<div class="col-sm-6">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>ContractPrefix</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter ContractPrefix" id="contractPrefix" name="contractPrefix" value="" />
												</div>
											</div>	
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>InvoicePrefix</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter InvoicePrefix" id="invoicePrefix" name="invoicePrefix" value="" />
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
	<%
	//	List<DivisionReq> list = (List<DivisionReq>)request.getParameter("DIVISION_LIST");
		
	%>
	<div class="container">
		<div class="table-responsive">
			<table class="table table-striped table-hover table-condensed">
				<thead>
					<tr>
						<th>Code</th>
						<th>Name</th>
						<th>Federal Account</th>
						<th>Provincial</th>
						<th>Links</th>
					</tr>
				</thead>
				<tbody id="divisionData">
						<c:choose>
						<c:when test="${LIST_DIVISION.size() > 0}">
							<c:forEach items="${LIST_DIVISION}" var="obj">
								<tr class="info">
									<td>${obj.divisionCode}</td>							
									<td>${obj.divisionName}</td>
									<td>${obj.fedral}</td>
									<td>${obj.provincial}</td>
									<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.divisionId}')">Update</a> / <a href="#" onclick="deleteDivision('${obj.divisionId}')">Delete</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td colspan="5">No records found.</td></tr>
						</c:otherwise>
						</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>