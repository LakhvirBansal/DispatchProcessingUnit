<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PO</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
	 <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 	 <script src="//code.jquery.com/jquery-1.12.4.js"></script>
	 <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	 <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	 <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.min.js"></script>
	 <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css" />
<link rel = "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/css/bootstrap-datepicker.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.js"></script>
<style type="text/css">
.ui-front {
    z-index: 9999;
}
.multiselect-container>li>a>label {
	padding: 4px 30px 3px 20px;
	margin-left:5px;
}
.multiselect-container>li {
	margin-left: 10px;
}
.multiselect-container {
	overflow: auto;
	height: 200px;
}
#unitNoIssue {
    max-height: 33px;
    max-width: 130px
}
#unitNo {
    max-height: 33px;
    max-width: 130px
}
</style>
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
textarea { 
	width: 100%; 
	min-width:100%; 
	max-width:100%; 
	height:100px; 
	min-height:100px;  
	max-height:100px;
}
</style>
<script type="text/javascript">
function isDecimal(field) {
    var rxExp = /^[0-9]\d*(\.\d+)?$/;
    var rx = new RegExp(rxExp);

    var matches = rx.exec(field);
    return (matches != null && field == matches[0]);
}
</script>
	<jsp:include page="Include.jsp"></jsp:include>
 <script src="<c:url value="/resources/validations.js" />"></script>
<script type="text/javascript">
function navigate() {
	var flag = $("#addUpdateFlag").val();
	if(flag == 'add') {
		createPO('savepo','POST');
	} else if(flag == 'update') {
		createPO('updatepo','PUT');			
	}
	/* $.confirm({
	    title: 'Confirm!',
	    content: 'Do you want to make this PO as complete ?',
	    buttons: {
	        cancel: {
	            text: 'Not Now',
	        },
	        confirm: {
	            text: 'Yes',
	            btnClass: 'btn-blue',
	            keys: ['enter', 'shift'],
	            action: function(){
	                $.alert('Something else?');
	            }
	        }
	    }
	}); */
}
function navigateUpdateIssue() {
	if(!checkUpdateIssue()){
		 return false;
	 }
	updateIssue('updateissue','PUT');	
	$('#issueModal').modal("toggle")
}
function updateIssue(urlToHit,methodType){
	 
 	var title = $("#title").val();
   	var vmcId = $('#vmcId :selected').val();
   	var unitType = $('#unitType :selected').val();
   	var issueCategory = $('#issueCategory :selected').val();
   	var unitNo = $('#unitNoIssue :selected').val();
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
        	
        	//var list = result.resultList;
			//fillIssueData(list);

	        toastr.success(result.message, 'Success!')
		} catch(e){
			unblockUI();
			toastr.error('Something went wrong' + e, 'Error!')
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
function checkUpdateIssue() {
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
function createPO(urlToHit,methodType){
	 
		 if(!check()){
			 return false;
		 }

	 	var vendorId = $('#vendorId :selected').val();
	 	if(vendorId == "Please Select") {
	 		vendorId = 0;
	 	}
	   	var unitTypeId = $('#unitTypeId :selected').val();
	   	if(unitTypeId == "Please Select") {
	   		unitTypeId = 0;
	   	}
	   	var categoryId = $('#categoryId :selected').val();
	   	if(categoryId == "Please Select") {
	   		categoryId = 0;
	   	}
	   	var message = $("#message").val();
	   	var poIssues = $('.poIssueIds:checkbox:checked');
	   	var unitNo = $('#unitNo').val();
	    var issueIds = new Array();
	    var issueStatusIds = new Array();
	    var currentStatusVal = $("#statusFlag").val();
	    
	    if(unitNo == null) {
	    	unitNo = "";
	    }

	    for(var i=0;i<poIssues.length;i++) {
	    	var valIssue = poIssues[i].value;
		   	var issueStatusId = $("#issueStatusId" + valIssue + " :selected").val();
		   	issueIds.push(valIssue);
		   	issueStatusIds.push(issueStatusId);
	    }
		
	   	var poId;
	   	if(methodType == 'PUT') {
	   		poId = $('#poid').val();
	   	}
	   	
	   	blockUI()
	   	$.ajax({url: BASE_URL + urlToHit,
			      type:"POST",
			      data:{
			        vendorId:vendorId,
			    	unitTypeId:unitTypeId,
			    	categoryId:categoryId,
			    	message:message,
			    	issueIds:issueIds.toString(),
			    	issueStatusIds:issueStatusIds.toString(),
			    	selectedUnitNos:unitNo.toString(),
			    	poid:poId,
			    	currentStatusVal:currentStatusVal
			      },
			      success: function(result){
		        try{
		        	$('#myModal').modal('toggle');
		        	var list = result.resultList;
					fillPOData(list);

			        toastr.success(result.message, 'Success!')
				} catch(e){
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

function deleteInvoice(poId){
	 
	blockUI()
	  $.ajax({url: BASE_URL + "deleteinvoice/" + poId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillPOData(list);
					
	    		  toastr.success(result.message, 'Success!');
			  }catch(e){
				  unblockUI();
				toastr.error('Something went wrong', 'Error!');
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

function fillPOData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var poNo = "";
    		if(obj.PoNo != null) {
    			poNo = obj.PoNo;
    		}
    		var message = "";
    		if(obj.message != null) {
    			message = obj.message;
    		}
    		var categoryName = "";
    		if(obj.categoryName != null) {
    			categoryName = obj.categoryName;
    		}
    		var statusName = "";
    		if(obj.statusName != null) {
    			statusName = obj.statusName;
    		}
    		var unitTypeName = "";
    		if(obj.unitTypeName != null) {
    			unitTypeName = obj.unitTypeName;
    		}
    		var vendorName = "";
    		if(obj.vendorName != null) {
    			vendorName = obj.vendorName;
    		}
    		
    		tableValue = tableValue + ("<td>"+(poNo)+"</td>");
    		tableValue = tableValue + ("<td>"+(message)+"</td>");
    		tableValue = tableValue + ("<td>"+(categoryName)+"</td>");
    		tableValue = tableValue + ("<td>"+(statusName)+"</td>");
    		tableValue = tableValue + ("<td>"+(unitTypeName)+"</td>");
    		tableValue = tableValue + ("<td>"+(vendorName)+"</td>");
    		tableValue = tableValue + "<td>";
    		if($("#statusFlag").val() == 'Active') {
	    		tableValue = tableValue + "<a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.id)+"')>Update</a>";
    		}
    		if($("#statusFlag").val() == 'Complete') {
	    		tableValue = tableValue + "<a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.id)+"')>Update</a> / <a href='#' data-toggle='modal' data-target='#invoiceModal' onclick=pastePoNo('"+(poNo)+"');pastePoIdAndStatusId('"+(obj.id)+"','"+(obj.invoiceStatusId)+"');toggleInvoice('add')>Change to Invoice</a>";	    			
    		}
    		if($("#statusFlag").val() == 'Invoiced') {
	    		tableValue = tableValue + "<a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.id)+"')>Update</a> / <a href = '#' data-toggle='modal' data-target='#invoiceModal'  onclick=pastePoIdAndStatusId('"+(obj.id)+"','"+(obj.invoiceStatusId)+"');getInvoice('" + (obj.id) + "','" + (poNo) + "');toggleInvoice('update')>Edit Invoice</a> / <a href = '#' onclick=deleteInvoice('" + (obj.id) + "')>Delete Invoice</a> / <a href = '#' onclick=popUpForInvoiceToComplete('" + (obj.id) + "')>Change to Complete</a>";	    			
    		}
    		if(obj.isComplete == true) {
    			tableValue = tableValue + " / <a href='#' onclick=changeStatusToComplete('"+ (obj.id) + "','" + (obj.completeStatusId) + "') id='changeStatus'>Change to Complete</a>";
    		}
    		tableValue = tableValue + ("</td></tr>");
		}
		$("#poData").html(tableValue);
	} else {
		$("#poData").html("No records found.");
	}
}

function toggleInvoice(invoiceFlag) {
	document.getElementById("invoiceFlag").value = invoiceFlag;

	if(invoiceFlag == 'add') {
		$("#invoiceTitle").html("Create Invoice");
		document.getElementById("btnChangeToInvoice").value = "Create";
	} else if(invoiceFlag == 'update') {
		$("#invoiceTitle").html("Edit Invoice");
		document.getElementById("btnChangeToInvoice").value = "Update";
	}
}

function getInvoice(poId, poNo) {
	
	pastePoNo(poNo);
	blockUI()
	$.get("getinvoice/poId",{"poId" : poId}, function(data) {
		
		unblockUI()
		$("#invoiceNo").val(data.invoiceNo);
		$("#invoiceAmount").val(data.amount);
		var invoiceDate = data.invoiceDate;
       	if(invoiceDate != null) {
           	var invoiceDateArr = invoiceDate.split("-");
           	$("#invoiceDate").val(invoiceDateArr[1]+"/"+invoiceDateArr[2]+"/"+invoiceDateArr[0]);
       	}
   	}); 
}

function deletePO(terminalId){
	  blockUI();
	  $.ajax({url: BASE_URL + "deletepo/" + terminalId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillPOData(list);
					
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
		$("#btnActive").css('background','pink');
		$("#btnInvoiced").css('background','white');
		$("#btnComplete").css('background','white');
		$("#btnActive").click(function () {
			showActivePOs();
			$("#btnActive").css('background','pink');
			$("#btnInvoiced").css('background','white');
			$("#btnComplete").css('background','white');
			$("#statusFlag").val("Active");
		});
		$('#message').keyup(function() {
		    if($('#message').val().length > 1000) {
				toastr.error('Only 1000 words allowed', 'Error!')
				return false;
		    }
		});
		if($("#statusFlag").val() == 'Active') {
			$("#btnActive").focus();
			/* $("#btnComplete").addClass("btn btn-default"); 
			$("#btnInvoiced").addClass("btn btn-default"); */
		}
		$("#btnComplete").click(function () {
			$("#btnActive").css('background','white');
			$("#btnInvoiced").css('background','white');
			$("#btnComplete").css('background','pink');
			showCompletePOs();
			$("#statusFlag").val("Complete");
		});
		
		$("#btnInvoiced").click(function () {
			$("#btnActive").css('background','white');
			$("#btnInvoiced").css('background','pink');
			$("#btnComplete").css('background','white');
			showInvoicedPOs();
			$("#statusFlag").val("Invoiced");
		});
		if($("#statusFlag").val() == 'Complete') {
			$("#btnComplete").focus();
		}
		var url = window.location.href;
		if(url.indexOf("Active") > 0) {
			$("#btnActive").removeClass("btn btn-default").addClass("btn btn-info");
			$("#btnComplete").addClass("btn btn-default"); 
			$("#btnInvoiced").addClass("btn btn-default");
		}
		
		if(url.indexOf("Complete") > 0) {
			$("#btnActive").addClass("btn btn-default"); 
			$("#btnComplete").removeClass("btn btn-default").addClass("btn btn-info"); 
			$("#btnInvoiced").addClass("btn btn-default"); 
		}
		
		$('#btnSearch').click(function(){
			$("#frmSearch").change(function() {
			  $("#frmSearch").attr("action", "showques");
			});
			$('#frmSearch').submit();
		});
	});
function checkFlag(field) {
	document.getElementById("addUpdateFlag").value = field;
	$("#btnNew").show();
	if(field == 'update') {
		document.getElementById("btnNew").value = "Update";
		//$("#btnExit").hide();
		$("#modelTitle").html("Edit PO");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
		document.getElementById("frm1").action = '<%=request.getContextPath()%>'+"/savepo";
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnNew").value = "Save";
		//$("#btnExit").show();
		$("#modelTitle").html("Add PO");
	} else if (field == 'search') {
		/* document.getElementById("frm1").method = "GET";
		document.getElementById("frm1").action = "showques";
		document.getElementById("frm1").submit(); */
	} else if(field == 'view') {
		document.getElementById("btnNew").value = "Update";

		$("#modelTitle").html("Edit PO");
		//$("#btnNew").hide();
	}
}

function showIssueDetail(quesId) {
	$("#title").val("");
    $("#description").val("");
    document.getElementById("vmcId").innerHTML = "<option value='0'>Please Select</option>";
	document.getElementById("unitType").innerHTML = "<option value='0'>Please Select</option>";
	document.getElementById("issueCategory").innerHTML = "<option value='0'>Please Select</option>";
    $("#unitNoIssue").html("<option value='0'>Please Select</option>");
	document.getElementById("reportedBy").innerHTML = "<option value='0'>Please Select</option>";
	document.getElementById("status").innerHTML = "<option value='0'>Please Select</option>";
	
	blockUI()
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
        for(var i = 0;i < categoryList.length;i++) {
        	category.options[category.options.length] = new Option(categoryList[i].name);
        	category.options[i+1].value = categoryList[i].categoryId;
        	if(categoryList[i].categoryId == data.categoryId) {
        		document.getElementById("issueCategory").selectedIndex = i+1;
        	}
        }
        
        var unitNo = document.getElementById("unitNoIssue");
        var unitNos = data.unitNos;
        for(var i = 0;i < unitNos.length;i++) {
        	unitNo.options[unitNo.options.length] = new Option(unitNos[i]);
        	unitNo.options[i+1].value = unitNos[i];
        	if(unitNos[i] == data.unitNo) {
        		document.getElementById("unitNoIssue").selectedIndex = i+1;
        	}
        }
        
        var reportedBy = document.getElementById("reportedBy");
        var reportedByList = data.reportedByList;
        for(var i = 0;i < reportedByList.length;i++) {
        	reportedBy.options[reportedBy.options.length] = new Option(reportedByList[i].fullName);
        	reportedBy.options[i+1].value = reportedByList[i].driverId;
        	if(reportedByList[i].driverId == data.reportedById) {
        		document.getElementById("reportedBy").selectedIndex = i+1;
        	}
        }
        
        var status = document.getElementById("status");
        var statusList = data.statusList;
        for(var i = 0;i < data.statusList.length;i++) {
        	status.options[status.options.length] = new Option(data.statusList[i].typeName);
        	status.options[i+1].value = data.statusList[i].typeId;
        	if(statusList[i].typeId == data.statusId) {
        		document.getElementById("status").selectedIndex = i+1;
        	}
        }
   	}); 
}
		var editInitialValue = "";
		
		var issuesFroDropDown;
		<%-- function getUnitNo() {
			var unitTypeId = $('#unitTypeId :selected').val();
			var categoryId = $('#categoryId :selected').val();
			if(unitTypeId > 0 && categoryId > 0 ) {
				
				$.get("<%=request.getContextPath()%>" + "/po/getissues/category/"+ categoryId + "/unittype/" + unitTypeId, function(issuesResponse) {
	 	           
					issuesFroDropDown = issuesResponse;
		            if(issuesResponse.length > 0) {
		            	var tableValue = "";
						$("#mainDiv").show();
		            	for(var i=0;i<issuesResponse.length;i++) {
		            		var obj = issuesResponse[i];
		            		if($("#addUpdateFlag").val() == 'update') {
			            		if(!editIssueIds.includes(obj.id)) {
				            		tableValue = tableValue + ("<tr class='info'>");
				            		tableValue = tableValue + ("<td><div style='margin-top: -11px;'><input type='checkbox' class='form-control poIssueIds' value='"+(obj.id)+"' id='issueId" + (obj.id) + "' name='issueIds' /></div></td>");
				            		tableValue = tableValue + ("<td><a href='#' onclick=showIssueDetail('"+(obj.id) + "') data-toggle='modal' data-target='#issueModal'>" + (obj.title)+"</a></td>");
				            		tableValue = tableValue + ("<td>"+(obj.vmcName)+"</td>");
				            		tableValue = tableValue + ("<td>"+(obj.categoryName)+"</td>");
				            		tableValue = tableValue + ("<td>"+(obj.unitTypeName)+"</td>");
				            		tableValue = tableValue + ("<td>"+(obj.unitNo)+"</td>");
				            		tableValue = tableValue + ("<td>"+(obj.reportedByName)+"</td>");
				            		tableValue = tableValue + ("<td><select class='form-control issueStatusClass' id='issueStatusId" + (obj.id)+"'><option value='-1'>Please Select</option><option value='104'>Complete</option><option value='105'>Incomplete</option><option value='106'>Assigned</option></select></td>");
				            		tableValue = tableValue + ("</tr>");
			            		}
		            		}
		            		else {
			            		tableValue = tableValue + ("<tr class='info'>");
			            		tableValue = tableValue + ("<td><div style='margin-top: -11px;'><input type='checkbox' class='form-control poIssueIds' value='"+(obj.id) + "' id='issueId" + (obj.id) + "' name='issueIds' /></div></td>");
			            		tableValue = tableValue + ("<td><a href='#' onclick=showIssueDetail('"+(obj.id) + "') data-toggle='modal' data-target='#issueModal'>"+(obj.title)+"</a></td>");
			            		tableValue = tableValue + ("<td>"+(obj.vmcName)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.categoryName)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.unitTypeName)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.unitNo)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.reportedByName)+"</td>");
			            		tableValue = tableValue + ("<td><select class='form-control issueStatusClass' id='issueStatusId" + (obj.id)+"'><option value='-1'>Please Select</option><option value='104'>Complete</option><option value='105'>Incomplete</option><option value='106'>Assigned</option></select></td>");
			            		tableValue = tableValue + ("</tr>");
		            		}
		            	}
		            	if($("#addUpdateFlag").val() == 'update') {
			            	$("#issuesTable").html($("#issuesTable").html() + tableValue);
		            	} else {
							$("#mainDiv").show();
			            	$("#issuesTable").html(""+tableValue);	            		
		            	}
		            	
		            } else {
		            	//$("#mainDiv").hide();
		            	toastr.error("No Open/ Deferred/ Incomplete Issue related to unittype and category exists.", 'Message!');
						$("#issuesTable").html(editInitialValue);
		            }
		            
					for(var k=0;k<issuesFroDropDown.length;k++) {
						var obj = issuesFroDropDown[k];
						$("#issueId" + obj.id).click(function (){
						    if ($(this).is(':checked') )
						   		document.getElementById("issueStatusId" + $(this).val()).selectedIndex = 3;
						});
					}
				});
			}
		} --%>
		
		function getOnlyUnitNosOnUnitTypeChangeUpdateIssue() {
		 	
			 var unitTypeId = $('#unitType :selected').val();
			 var categoryId = 0;
			 
			if(unitTypeId != "Please Select") {

				blockUI()
				$.get("<%=request.getContextPath()%>"+"/issue/getunitno/category/" + categoryId + "/unittype/" + unitTypeId, function(data) {
			        
					unblockUI()
					 var unitNo = document.getElementById("unitNoIssue");
			       	    $("#unitNoIssue").html("<option value='0'>Please Select</option>");
			         if(data.unitNos != null && data.unitNos.length > 0) {
			        	 for(var i = 0;i < data.unitNos.length;i++) {
		                   	unitNo.options[unitNo.options.length] = new Option(data.unitNos[i]);
		                   	unitNo.options[i+1].value = data.unitNos[i];
		                 }
			         } else {
						toastr.error('No such UnitNo. exists for selected UnitType and Category', 'Error!')
						$("#unitNoIssue").html("<option value='0'>Please Select</option>");
			         }
			    });
			} else {
				$("#unitNoIssue").html("<option value='0'>Please Select</option>");
			}
		}
		
		function getCategoriesUpdateIssue() {
			 var unitTypeName = $('#unitType :selected').text();

			 if(unitTypeName != "Please Select") {
				 blockUI()
				 $.get("getcategories/unittype/"+unitTypeName, function(data) {
			      
					 unblockUI()
				      var category = document.getElementById("issueCategory");
				      $("#issueCategory").empty();
				      category.options[0] = new Option("Please Select");		      
				      
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
		
		var editIssueIds = new Array();
		var editIssueHtml = "";
        function onClickMethodQuestion(quesId){
        	emptyMessageDiv();
        	clearAll();
        	if(quesId == 0) {
        		
        		blockUI()
        		$("#mainDiv").hide();
       			$("#issueIds").html("");
       			$("#invoceNoDiv").hide();
            	$("#invoiceNo").val("");
            	$.get("<%=request.getContextPath()%>"+"/po/getopenadd", function(data) {
            		unblockUI()
    	           
    	            var vendor = document.getElementById("vendorId");
    	            for(var i = 0;i < data.vendorList.length;i++) {
    	            	vendor.options[vendor.options.length] = new Option(data.vendorList[i].name);
    	            	vendor.options[i+1].value = data.vendorList[i].vendorId;
    	            }

    	           /*  var category = document.getElementById("categoryId");
    	            for(var i = 0;i < data.categoryList.length;i++) {
    	            	category.options[category.options.length] = new Option(data.categoryList[i].name);
    	            	category.options[i].value = data.categoryList[i].categoryId;
    	            } */

    	            var unitType = document.getElementById("unitTypeId");
    	            for(var i = 0;i < data.unitTypeList.length;i++) {
    	            	unitType.options[unitType.options.length] = new Option(data.unitTypeList[i].typeName);
    	            	unitType.options[i+1].value = data.unitTypeList[i].typeId;
    	            }
    	            
    	            //getUnitNo();
    	             
    	        });
        	} else {
        		$("#mainDiv").hide();
       			$("#issueIds").html("");
       			$("#invoceNoDiv").hide();
            	$("#invoiceNo").val("");
        		blockUI()
        		$.get('<%=request.getContextPath()%>'+"/getpo/poId",{"poId" : quesId}, function(data) {
        			unblockUI()
        			document.getElementById("poid").value = data.id;
                    
        			var vendor = document.getElementById("vendorId");
                    var vendorList = data.vendorList;
                  
                    if(vendorList != null) {
	    	            for(var i = 0;i < vendorList.length;i++) {
	    	            	vendor.options[vendor.options.length] = new Option(vendorList[i].name);
	    	            	vendor.options[i+1].value = vendorList[i].vendorId;
	                    	if(vendorList[i].vendorId == data.vendorId) {
	                    		document.getElementById("vendorId").selectedIndex = i+1;
	                    	}
	    	            }
                    }
                    
                    var unitType = document.getElementById("unitTypeId");
                    var unitTypeList = data.unitTypeList;
                    
                    if(unitTypeList != null) {
	                    for(var i = 0;i < unitTypeList.length;i++) {
	                    	unitType.options[unitType.options.length] = new Option(unitTypeList[i].typeName);
	                    	unitType.options[i+1].value = unitTypeList[i].typeId;
	                    	if(unitTypeList[i].typeId == data.unitTypeId) {
	                    		document.getElementById("unitTypeId").selectedIndex = i+1;
	                    	}
	                    }
                    }
                    
                    
                    var category = document.getElementById("categoryId");
                    var categoryList = data.categoryList;
                    
                    if(categoryList != null) {
	                    for(var i = 0;i < categoryList.length;i++) {
	                    	category.options[category.options.length] = new Option(categoryList[i].name);
	                    	category.options[i+1].value = categoryList[i].categoryId;
	                    	if(categoryList[i].categoryId == data.categoryId) {
	                    		document.getElementById("categoryId").selectedIndex = i+1;
	                    	}
	                    }
                    }
                    
                    
                    var unitNo = document.getElementById("unitNo");
                    var unitNos = data.allUnitNos;
                    var opt = "";

                    if(unitNos != null) {
	                    for(var i = 0;i < unitNos.length;i++) {
	 			         	opt += "<option value='"+unitNos[i]+"' id='chk" + unitNos[i]+"'>" + unitNos[i]+"</option>"
	                    }
                    }
                    

                    var selectedUnitNos = data.selectedUnitNos;

                    $("#unitNo").html(opt);
   		      		$("#unitNo").val(selectedUnitNos);
   		      		setTimeout(function(){
			         	$('#unitNo').multiselect({
				 			includeSelectAllOption: true
					 	});
   		      		})
                    var issueList = data.issueList;
	            	var tableValue = "";
					$("#issuesTable").html("");
					
					if(issueList != null) {
						if(issueList.length > 0) {
							$("#mainDiv").show();
		    	            for(var i = 0;i < issueList.length;i++) {
		    	            	var obj = issueList[i];
		    	            	editIssueIds.push(obj.id);
			            		tableValue = tableValue + ("<tr class='info " + unitNo + "'>");
			            		var assigned,complete,incomplete;
			            		if(obj.statusName == "Assigned") {
			            			assigned = "selected";
			            			complete = "";
			            			incomplete = "";
			            		}
			            		else if(obj.statusName == "Complete") {
			            			assigned = "";
			            			complete = "selected";
			            			incomplete = "";
			            		}
			            		else if(obj.statusName == "Incomplete") {
			            			assigned = "";
			            			complete = "";
			            			incomplete = "selected";
			            		} else {
			            			assigned = "";
			            			complete = "";
			            			incomplete = "";
			            		}
			            		if((obj.statusName == "Assigned") || (obj.statusName == "Complete") || (obj.statusName == "Incomplete")) {
				            		tableValue = tableValue + ("<td><div style='margin-top: -11px;'><input type='checkbox' on class='form-control poIssueIds' value='"+(obj.id) + "' id='issueId" + (obj.id) + "' onclick=selectUnSelectDropDown('" + (obj.id) + "') name='issueIds' checked /></div></td>");
			            		} else {
				            		tableValue = tableValue + ("<td><div style='margin-top: -11px;'><input type='checkbox' class='form-control poIssueIds' value='"+(obj.id) + "' id='issueId" + (obj.id) + "' name='issueIds' onclick=selectUnSelectDropDown('" + (obj.id) + "') /></div></td>");		            			
			            		}
	     						tableValue = tableValue + ("<td><a href='#' onclick=showIssueDetail('"+(obj.id) + "') data-toggle='modal' data-target='#issueModal'>" + (obj.title)+"</a></td>");
			            		tableValue = tableValue + ("<td>"+(obj.vmcName)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.categoryName)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.unitTypeName)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.unitNo)+"</td>");
			            		tableValue = tableValue + ("<td>"+(obj.reportedByName)+"</td>");
			            		tableValue = tableValue + ("<td><select class='form-control issueStatusClass' id='issueStatusId" + (obj.id)+"'><option value='-1'>Please Select</option><option value='104' " + complete + ">Complete</option><option value='105' " + incomplete +">Incomplete</option><option value='106' " + assigned +">Assigned</option></select></td>");
			            		tableValue = tableValue + ("</tr>");
			            		
		    	            }
		    	            editIssueHtml = tableValue;
						} else {
							toastr.error("No Open/ Deferred/ Incomplete Issue related to unittype and category exists.", 'Message!');
						}
					}

					editInitialValue = tableValue;
	            	$("#issuesTable").html(tableValue);
	            	
                    if(data.invoiceNo != null) {
                    	$("#invoceNoDiv").show();
                    	$("#invoiceNo").val(data.invoiceNo);
                    } else {
                    	$("#invoceNoDiv").hide();
                    }

                    $("#message").val(data.message);
                    
                    /* if(issueList != null) {
	                    for(var i=0;i<issueList.length;i++) {
			            	var obj = issueList[i];
			     			$("#issueId" + obj.id).on('click',function (){
			     				if ($(this).is(':checked')) {
			     					document.getElementById("issueStatusId" + obj.id).selectedIndex = 3;
			     				} else {
			     					document.getElementById("issueStatusId" + obj.id).selectedIndex = 0;		     					
			     				}
			     			});
		            	}
                    } */
               	}); 
        	}
        	
        	$("#btnGo").click(function() {
        		var msg = $("#msg");
        		msg.hide();
        		var msgvalue = $("#msgvalue");
        		msgvalue.hide();
        		msgvalue.text("");        		
        	});
        }
        
        function clearAll() {
        	document.getElementById("vendorId").innerHTML = "<option>Please Select</option>";
        	document.getElementById("unitTypeId").innerHTML = "<option>Please Select</option>";        	
        	document.getElementById("categoryId").innerHTML = "<option>Please Select</option>";
        	$('#unitNo').multiselect('destroy');
       	    $("#unitNo").html("<option>Please Select</option>");
            $("#invoiceNo").val("");
            $("#message").val("");
        }
        
function check() {
	var msg = $("#msg");
	var msgvalue = $("#msgvalue");
	msg.hide();
	msgvalue.val("");
	if($('#message').val() == "") {
		msg.show();
		$("#message").focus();
		msgvalue.text("Message cannot be left blank.");
		return false;

    }
	if($('#message').val().length > 1000) {
		msg.show();
		$("#message").focus();
		msgvalue.text("Only 1000 words allowed in message");
		return false;

    }
	return true;
}

function checkInvoice() {
	var invoiceAmount = $("#invoiceAmount").val();
	var invoiceNo = $("#invoiceNo").val();
	var invoiceDate = $("#invoiceDate").val();
	var msg = $("#msgInvoice");
	var msgvalue = $("#msgvalueInvoice");
	msg.hide();
	msgvalue.val("");
	if(invoiceAmount == "") {
		msg.show();
		$("#invoiceAmount").focus();
		msgvalue.text("Invoice Amount cannot be left blank.");
		return false;
    }
	if(!isDecimal(invoiceAmount)) {
		msg.show();
		msgvalue.text("Only numerics & dot allowed in Invoice Amount");
		$("#invoiceAmount").focus();
		return false;
	}
	if(invoiceNo == "") {
		msg.show();
		$("#invoiceNo").focus();
		msgvalue.text("Invoice No cannot be left blank.");
		return false;
    }
	if(!isNumeric(invoiceNo)) {
		msg.show();
		msgvalue.text("Only numerics allowed in Invoice No");
		$("#invoiceNo").focus();
		return false;
	}
	if(invoiceDate == "") {
		msg.show();
		$("#invoiceDate").focus();
		msgvalue.text("Invoice Date cannot be left blank.");
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
 function changeStatusToComplete(poId, statusId) {
	
	 blockUI()
	 $.ajax({url: BASE_URL + poId + "/complete/"+statusId,
	      type:"GET",
	      success: function(result){
   	  try{	
				var list = result.resultList;
				fillPOData(list);
				
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
 
 function pastePoNo(poNo) {
	 $("#invoicePoNo").val(poNo);
	 $("#invoiceAmount").val("");
	 $("#invoiceNo").val("");
	 $("#invoiceDate").val("");
 }
 
 var invoicePoId;
 var invoiceStatusId;
 function pastePoIdAndStatusId(poId,statusId) {
	 invoicePoId=poId;
	 invoiceStatusId = statusId;
	 $("#invoicePoId").val(poId);
	 $("#invoiceStatusId").val(statusId);	 
 }
 
 function popUpForInvoiceToComplete(poId) {
	 
	$.confirm({
	    title: 'Do you want to make this PO as complete ?',
	    content: 'You may lose its invoice data.',
	    buttons: {
	        cancel: {
	            text: 'Not Now',
	        },
	        confirm: {
	            text: 'Yes',
	            btnClass: 'btn-blue',
	            keys: ['enter', 'shift'],
	            action: function(){
	                deleteInvoice(poId);
	            }
	        }
	    }
	});
 }
 
 function changeStatusToInvoice() {
	 
	 if(!checkInvoice()) {
		 return false;
	 }
	 
	 var poId = invoicePoId;
	 var statusId = invoiceStatusId;
 	 var amount = $("#invoiceAmount").val();
 	 var invoiceNo = $("#invoiceNo").val();
     var invoiceDate = $("#invoiceDate").val();
	 var url = "";
	 var methodType = "GET";
	 if($("#invoiceFlag").val() == 'add') {
		 url = BASE_URL + poId + "/complete/" + statusId + "/invoiced";
	 } else {
		 methodType = "POST";
		 url = BASE_URL + "updateinvoice";		 
	 }
	 
	 blockUI()
	 $.ajax({url: url,
	      type:methodType,
	      data:{
		    	invoiceDate:invoiceDate,
		    	invoiceNo:invoiceNo,
		    	amount:amount,
		    	poid:poId,
		    	invoiceAmount:amount
		      },
	      success: function(result){
   	  try{	
      	$('#invoiceModal').modal('toggle');

				var list = result.resultList;
				fillPOData(list);
				
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

  function showCompletePOs() {
	  blockUI();
	 $.ajax({url: BASE_URL + "showpo/status/Complete",
	      type:"GET",
	      success: function(result){
  	  try{	
				var list = result;
				fillPOData(list);
				
  		  //toastr.success(result.message, 'Success!')
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
  
  function showInvoicedPOs() {
	     blockUI()
		 $.ajax({url: BASE_URL + "showpo/status/Invoiced",
		      type:"GET",
		      success: function(result){
	  	  try{	
					var list = result;
					fillPOData(list);
					
	  		  //toastr.success(result.message, 'Success!')
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
  
  function showActivePOs() {
	     blockUI()
		 $.ajax({url: BASE_URL + "showpo/status/Active",
		      type:"GET",
		      success: function(result){
	  	  try{	
	  			$("#poData").html("");
					var list = result;
					fillPOData(list);
					
	  		  //toastr.success(result.message, 'Success!')
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
 function getUnitNos() {
		var unitTypeId = $('#unitType :selected').val();
		var categoryId = $('#issueCategory :selected').val();
		
		blockUI()
		$.get("issue/getunitno/category/"+categoryId+"/unittype/"+unitTypeId, function(data) {
			
		 unblockUI();
         var unitNo = document.getElementById("unitNo");
         $("#unitNo").empty();
         for(var i = 0;i < data.unitNos.length;i++) {
         	unitNo.options[unitNo.options.length] = new Option(data.unitNos[i]);
         	unitNo.options[i].value = data.unitNos[i];
         }
		});
	}
 
 function getCategories() {
	 var unitTypeName = $('#unitTypeId :selected').text();

	 if(unitTypeName != "Please Select") {
		 
		 blockUI()
		 $.get("getcategories/unittype/"+unitTypeName, function(data) {
	      
			 unblockUI();
		      var category = document.getElementById("categoryId");
		      $("#categoryId").empty();
		      category.options[0] = new Option("Please Select");		      
		      
		      if(data != null && data.length > 0) {
		    	  for(var i = 0;i < data.length;i++) {
			    	  category.options[category.options.length] = new Option(data[i].name);
			    	  category.options[i+1].value = data[i].categoryId;
			      }
		      } else {
		    	  var category = document.getElementById("categoryId");
			      $("#categoryId").empty();
			      category.options[0] = new Option("Please Select");
				  toastr.error("No such Category exist for this Unit Type", 'Error!');
		      }
		 });
	 } else {
		 var category = document.getElementById("categoryId");
	      $("#categoryId").empty();
	      category.options[0] = new Option("Please Select");
	 }
 }
 
 function getCategoriesUpdateIssue() {
	 var unitTypeName = $('#unitType :selected').text();

	 if(unitTypeName != "Please Select") {
		 
		 blockUI()
		 $.get("getcategories/unittype/"+unitTypeName, function(data) {
	      
			  unblockUI();
		      var category = document.getElementById("issueCategory");
		      $("#issueCategory").empty();
		      category.options[0] = new Option("Please Select");		      
		      
		      if(data != null && data.length > 0) {
		    	  for(var i = 0;i < data.length;i++) {
			    	  category.options[category.options.length] = new Option(data[i].name);
			    	  category.options[i+1].value = data[i].categoryId;
			      }
		      } else {
		    	  var category = document.getElementById("categoryId");
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
function getOnlyUnitNosOnUnitTypeChange() {
 	
 	 var unitTypeId = $('#unitTypeId :selected').val();
 	 var categoryId = 0;
 	 
 	if(unitTypeId != "Please Select") {

 		blockUI()
 		$.get("<%=request.getContextPath()%>"+"/issue/getunitno/category/" + categoryId + "/unittype/" + unitTypeId, function(data) {
 	        
 			 unblockUI();
 			 var unitNo = document.getElementById("unitNo");
 	         $("#unitNo").empty();
 	         var opt = "";
 	         if(data.unitNos != null && data.unitNos.length > 0) {
 		         if(data.unitNos != null && data.unitNos.length > 0) {
 			         for(var i = 0;i < data.unitNos.length;i++) {
 			         	opt += "<option value='"+data.unitNos[i]+"' id='chk"+data.unitNos[i]+"'>"+data.unitNos[i]+"</option>"
 			         }
 		         } else {
 					toastr.error('No such UnitNo. exists for selected UnitType and Category', 'Error!')
 		         }
 		         
		 		 $('#unitNo').multiselect('destroy');
 		         $("#unitNo").html(opt);
 		         $('#unitNo').multiselect({
 			 		  	includeSelectAllOption: true
 				 });
 		         
 		         var issuesFroDropDown;
 		         var selectedUnitNos = [];
 		         
 		         var allUnitNos = data.unitNos;
 		        
 	         } else {
 	        	$('#unitNo').multiselect('destroy');
 				$("#unitNo").html("<option value ='0'>Please Select</option>");
 	         }
 	    });
 	} else {
 		$('#unitNo').multiselect('destroy');
		$("#unitNo").html("<option value='0'>Please Select</option>");
 	}
 }

 function getOnlyUnitNos() {
 	
 	 var unitTypeId = $('#unitTypeId :selected').val();
 	 var categoryId = $('#categoryId :selected').val();
 	 
 	if(categoryId == "Please Select") {
 		categoryId = 0;
 	}
 	if(unitTypeId != "Please Select") {

 		blockUI()
 		$.get("<%=request.getContextPath()%>"+"/issue/getunitno/category/" + categoryId + "/unittype/" + unitTypeId, function(data) {
 	        
 			 unblockUI();
 			 var unitNo = document.getElementById("unitNo");
 	         $("#unitNo").empty();
 	         var opt = "";
 	         if(data.unitNos != null && data.unitNos.length > 0) {
 		         if(data.unitNos != null && data.unitNos.length > 0) {
 			         for(var i = 0;i < data.unitNos.length;i++) {
 			         	opt += "<option value='"+data.unitNos[i]+"' id='chk"+data.unitNos[i]+"'>"+data.unitNos[i]+"</option>"
 			         }
 		         } else {
 					toastr.error('No such UnitNo. exists for selected UnitType and Category', 'Error!')
 		         }
 		         
		 		 $('#unitNo').multiselect('destroy');
 		         $("#unitNo").html(opt);
 		         $('#unitNo').multiselect({
 			 		  	includeSelectAllOption: true
 				 });
 		         
 		         var issuesFroDropDown;
 		         var selectedUnitNos = [];
 		         
 		         var allUnitNos = data.unitNos;
 		        
 	         } else {
 	        	$('#unitNo').multiselect('destroy');
 				$("#unitNo").html("<option value='0'>Please Select</option>");
 	         }
 	    });
 	} else {
 		$('#unitNo').multiselect('destroy');
		$("#unitNo").html("<option value='0'>Please Select</option>");
 	}
 }
 
 function getOnlyUnitNosUpdateIssue() {
	 	
 	 var unitTypeId = $('#unitType :selected').val();
 	 var categoryId = $('#issueCategory :selected').val();
 	 
 	if(categoryId == "Please Select") {
 		categoryId = 0;
 	}
 	if(unitTypeId != "Please Select") {

		blockUI()
		$.get("<%=request.getContextPath()%>"+"/issue/getunitno/category/" + categoryId + "/unittype/" + unitTypeId, function(data) {
	        
			 unblockUI()
			 var unitNo = document.getElementById("unitNoIssue");
	       	    $("#unitNoIssue").html("<option>Please Select</option>");
	         if(data.unitNos != null && data.unitNos.length > 0) {
	        	 for(var i = 0;i < data.unitNos.length;i++) {
                   	unitNo.options[unitNo.options.length] = new Option(data.unitNos[i]);
                   	unitNo.options[i+1].value = data.unitNos[i];
                 }
	         } else {
				toastr.error('No such UnitNo. exists for selected UnitType and Category', 'Error!')
				$("#unitNoIssue").html("<option value='0'>Please Select</option>");
	         }
	    });
	} else {
		$("#unitNoIssue").html("<option value='0'>Please Select</option>");
	}
 }
function functionToBeCalledOnGo() {
	
	 var unitTypeId = $('#unitTypeId :selected').val();
	 var unitNo1 = $('#unitNo').val();
	 if(unitNo1 != null) {
		 
      var issuesFroDropDown;
      
      blockUI()
      $.ajax({url: BASE_URL + "/getissuesbasedonunitnoandunittype/unittypeid/" + unitTypeId,
		      async:false,
		      type:"POST",
		      data:{
		    	  selectedUnitNos:unitNo1.toString(),
		      },
		      success: function(issuesResponse){
	        try{

	        	issuesFroDropDown = issuesResponse;
	     		if(issuesResponse.length > 0) {
	     			var tableValue = "";
	     			$("#mainDiv").show();
	     			for(var i=0;i<issuesResponse.length;i++) {
	     				var obj = issuesResponse[i];
	     				if($("#addUpdateFlag").val() == 'update') {
	     					if(!editIssueIds.includes(obj.id)) {
	     						tableValue = tableValue + ("<tr class='info " + unitNo + "'>");
	     						tableValue = tableValue + ("<td><div style='margin-top: -11px;'><input type='checkbox' class='form-control poIssueIds' value='"+(obj.id)+"' id='issueId" + (obj.id) + "' name='issueIds' onclick=selectUnSelectDropDown('" + (obj.id) + "') /></div></td>");
	     						tableValue = tableValue + ("<td><a href='#' onclick=showIssueDetail('"+(obj.id) + "') data-toggle='modal' data-target='#issueModal'>" + (obj.title)+"</a></td>");
	     						if(obj.vmcName != null) {
		     						tableValue = tableValue + ("<td>"+(obj.vmcName)+"</td>");
	     						} else {
		     						tableValue = tableValue + ("<td></td>");	     							
	     						}
	     						if(obj.categoryName != null) {
		     						tableValue = tableValue + ("<td>"+(obj.categoryName)+"</td>");
	     						} else {
		     						tableValue = tableValue + ("<td></td>");	     							
	     						}
	     						if(obj.unitTypeName != null) {
			     					tableValue = tableValue + ("<td>"+(obj.unitTypeName)+"</td>");
	     						} else {
		     						tableValue = tableValue + ("<td></td>");	     							
	     						}
	     						if(obj.unitNo != null) {
		     						tableValue = tableValue + ("<td>"+(obj.unitNo)+"</td>");
	     						} else {
		     						tableValue = tableValue + ("<td></td>");	     							
	     						}
	     						if(obj.reportedByName != null) {
		     						tableValue = tableValue + ("<td>"+(obj.reportedByName)+"</td>");
	     						} else {
		     						tableValue = tableValue + ("<td></td>");	     							
	     						}
	     						tableValue = tableValue + ("<td><select class='form-control issueStatusClass' id='issueStatusId" + (obj.id)+"'><option value='-1'>Please Select</option><option value='104'>Complete</option><option value='105'>Incomplete</option><option value='106'>Assigned</option></select></td>");
	     						tableValue = tableValue + ("</tr>");
	     					}
	     				}
	     				else {
	     					tableValue = tableValue + ("<tr class='info " + unitNo + "'>");
	     					tableValue = tableValue + ("<td><div style='margin-top: -11px;'><input type='checkbox' class='form-control poIssueIds' value='"+(obj.id) + "' id='issueId" + (obj.id) + "' name='issueIds' onclick=selectUnSelectDropDown('" + (obj.id) + "') /></div></td>");
	     					tableValue = tableValue + ("<td><a href='#' onclick=showIssueDetail('"+(obj.id) + "') data-toggle='modal' data-target='#issueModal'>"+(obj.title)+"</a></td>");
	     					if(obj.vmcName != null) {
	     						tableValue = tableValue + ("<td>"+(obj.vmcName)+"</td>");
     						} else {
	     						tableValue = tableValue + ("<td></td>");	     							
     						}
	     					if(obj.categoryName != null) {
	     						tableValue = tableValue + ("<td>"+(obj.categoryName)+"</td>");
     						} else {
	     						tableValue = tableValue + ("<td></td>");	     							
     						}
	     					if(obj.unitTypeName != null) {
		     					tableValue = tableValue + ("<td>"+(obj.unitTypeName)+"</td>");
     						} else {
	     						tableValue = tableValue + ("<td></td>");	     							
     						}
	     					if(obj.unitNo != null) {
	     						tableValue = tableValue + ("<td>"+(obj.unitNo)+"</td>");
     						} else {
	     						tableValue = tableValue + ("<td></td>");	     							
     						}
     						if(obj.reportedByName != null) {
	     						tableValue = tableValue + ("<td>"+(obj.reportedByName)+"</td>");
     						} else {
	     						tableValue = tableValue + ("<td></td>");	     							
     						}
	     					tableValue = tableValue + ("<td><select class='form-control issueStatusClass' id='issueStatusId" + (obj.id)+"'><option value='-1'>Please Select</option><option value='104'>Complete</option><option value='105'>Incomplete</option><option value='106'>Assigned</option></select></td>");
	     					tableValue = tableValue + ("</tr>");
	     				}
	     			}
	     			if($("#addUpdateFlag").val() == 'update') {
	     				$("#issuesTable").html(editIssueHtml + tableValue);
	     			} else {
	     				$("#mainDiv").show();
	     				$("#issuesTable").html(""+tableValue);	            		
	     			}
	     			
	     		} else {
	     			toastr.error("No Open/ Deferred/ Incomplete Issue related to unittype and category exists.", 'Message!');
	     			$("#issuesTable").html(editInitialValue);
	     		}
	     		
	     		/* for(var k=0;k<issuesFroDropDown.length;k++) {
	     			var obj = issuesFroDropDown[k];
	     			$("#issueId" + obj.id).click(function (){
	     				if ($(this).is(':checked')) {
	     					document.getElementById("issueStatusId" + $(this).val()).selectedIndex = 3;
	     				} else {
	     					document.getElementById("issueStatusId" + $(this).val()).selectedIndex = 0;	     					
	     				}
	     			});
	     		} */
	        	
			} catch(e){
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
	 }
}

function selectUnSelectDropDown(id) {
	if ($("#issueId" + id).is(':checked')) {
		document.getElementById("issueStatusId" + id).selectedIndex = 3;
	} else {
		document.getElementById("issueStatusId" + id).selectedIndex = 0;	     					
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
						<b class = "pageHeading">Purchase Order (PO)</b>
					</div>
				</div>
			</div>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="checkFlag('add'); onClickMethodQuestion('0'); emptyMessageDiv();" >Add New</button>
		<div class="form-group">
		<div class="row">
			<div class="col-sm-12" align= "center">
			<input type="hidden" id="statusFlag" value="Active"/>
	          <input type="button" class="btn btn-default" id= "btnActive" value="Active" />
	          <input type="button" class="btn btn-default" id= "btnComplete" value="Complete" />
	          <input type="button" class="btn btn-default" id= "btnInvoiced" value="Invoiced" />
			</div>			
		</div>
		<div class="row">
			<div class="col-sm-8">
					<div class="modal fade" id="myModal" role="dialog">
					    <div class="modal-dialog">
						<form id="frm1">
						<input type="hidden" id = "poid" name= "poid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
						<input type="hidden" id = "issueid" name= "issueid" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add PO</p></h4>
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
													<b>Vendor</b>												
												</span>
												<select class="form-control" name="vendorId" id="vendorId">
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
													<select id="unitTypeId" class="form-control" name="unitTypeId" onchange="getCategories();getOnlyUnitNosOnUnitTypeChange();">
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
														 <b>Category</b>												
													</span>
													<select class="form-control" name="categoryId" id="categoryId" onchange="getOnlyUnitNos();">
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
														 <b>Unit No.</b>												
													</span>
													<!-- <input type="text" id="searchedUnitNos" name="searchedUnitNos" />
													<input type="hidden" id="searchedUnitNoshidden" /> -->
													<select id="unitNo" class="form-control" name="unitNo" multiple="multiple">
														<option value="0">Please select</option>
													</select>
													<button type="button" class="btn btn-danger" id = "btnGo" onclick="functionToBeCalledOnGo()">Fetch</button>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group" style="display: none;" id="mainDiv">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<div class="container">
														<div class="table-responsive">
															<table class="table table-striped table-hover table-condensed">
																<thead>
																	<tr>
																		<th>&nbsp;</th>
																		<th>Title</th>
																		<th>VMC</th>
																		<th>Category</th>
																		<th>Unit Type</th>
																		<th>UnitNo</th>
																		<th>ReportedBy</th>
																		<th>Status</th>
																	</tr>
																</thead>
																<tbody id = "issuesTable">
																
																</tbody>
															</table>
														</div>
													</div>								
												</div>
											</div>
										</div>
									</div>
									<!-- <div class="form-group" id="statusDiv">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Status</b>												
													</span>
													<select id="statusId" class="form-control" name="statusId" onchange="showInvoiceNo()">
													</select>
												</div>
											</div>
										</div>
									</div> -->
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Message</b>												
													</span>
													<textarea class="form-control" rows="1" cols="1" placeholder="Enter Message" name="message" id = "message"></textarea>
												</div>
											</div>
										</div>
									</div>
								</div>
				        	</div>
					        </div>
					        <div class="modal-footer">
					          <input type="button" class="btn btn-primary" id= "btnNew" value="Save" onclick="navigate()" />
					    	  <!-- <input type="button" class="btn btn-primary" id= "btnExit" value="Save&Exit" /> -->
					    	  <input type="reset" class="btn btn-primary" id= "btnReset" value="Reset" />
							  <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					        </div>
					      </div>
					      </form>
					    </div>
					  </div>
					  <div class="modal fade" id="invoiceModal" role="dialog">
					    <div class="modal-dialog">
						<form method="POST" name="po" id="frm2">
						<input type="hidden" id = "invoicePoId" value = "" />					
						<input type="hidden" id = "invoiceStatusId" value = "" />
						<input type="hidden" id = "invoiceFlag" value = "" />					
										
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="invoiceTitle">Create Invoice</p></h4>
					          <div class="alert alert-danger fade in" id="msgInvoice" style="display: none;">
									<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
									<strong id = "msgvalueInvoice"></strong>
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
													<b>PO No</b>												
												</span>
												<input type="text" class="form-control" disabled="disabled" id="invoicePoNo" name="PoNo" value="" />
											</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Invoice Amount</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter InvoiceAmount" id="invoiceAmount" name="amount" value="" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Invoice No</b>												
													</span>
													<input type="text" class="form-control" placeHolder="Enter InvoiceNo" id="invoiceNo" name="invoiceNo" value="" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<div class="input-group date" data-provide="datepicker">
													<span class="input-group-addon">
														 <b>Invoice Date</b>												
													</span>
													<input type="text" class="form-control datepicker" placeHolder="Enter InvoiceDate" id="invoiceDate" name="invoiceDate" value="" />
												</div>
											</div>
										</div>
									</div>
								</div>
				        	</div>
					        </div>
					        <div class="modal-footer">
					          <input type="button" class="btn btn-primary" id= "btnChangeToInvoice" value="Create" onclick="changeStatusToInvoice()" />
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
		<div class="row">
			<div class="col-sm-8">
					<div class="modal fade" id="issueModal" role="dialog">
					    <div class="modal-dialog">
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Edit Issue</p></h4>
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
													<select id="unitType" class="form-control" name="unitTypeId" onchange="getCategoriesUpdateIssue();getOnlyUnitNosOnUnitTypeChangeUpdateIssue();">
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
													<select id="issueCategory" class="form-control" name="categoryId" onchange="getOnlyUnitNosUpdateIssue();">
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
													<select id="unitNoIssue" class="form-control" name="unitNo">
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
					          <input type="button" class="btn btn-primary" id= "btnUpdateIssue" value="Update" onclick="navigateUpdateIssue()"/>
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
						<th>PO No.</th>
						<th>Title</th>
						<th>Category</th>
						<th>Status</th>
						<th>UnitType</th>
						<th>Vendor</th>
					</tr>
				</thead>
				<tbody id="poData">
					<c:choose>
						<c:when test="${LIST_PO.size() > 0}" >
							<c:forEach items="${LIST_PO}" var="obj">
								<tr class="info">
									<td>${obj.poNo}</td>							
									<td>${obj.message}</td>
									<td>${obj.categoryName}</td>
									<td>${obj.statusName}</td>
									<td>${obj.unitTypeName}</td>
									<td>${obj.vendorName}</td>
									
									<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.id}')">Update</a> / <a href="#" onclick="deletePO('${obj.id}')">Delete</a> 
									<c:if test="${obj.isComplete == true}">
										/ <a href="#" onclick="changeStatusToComplete('${obj.id}','${obj.completeStatusId}')" id="changeStatus">Change to Complete</a>
									</c:if>
									</td>
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