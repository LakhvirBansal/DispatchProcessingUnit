<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Category</title>
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
		createCategory('savecategory','POST');
	} else if(flag == 'update') {
		createCategory('updatecategory','PUT');			
	}
}
function createCategory(urlToHit,methodType){
	 
	 if(!check()){
		 return false;
	 }
	 
	   	var typeId = $('#type :selected').val();
	 	var category = $("#category").val();
	   	var status = $('#status :selected').val();
	   	var highlight = $('#highlight :selected').val();
	   	var categoryId;
	   	if(methodType == 'PUT') {
	   		categoryId = $('#categoryid').val();
	   	}
	   	
	   	blockUI()
		  $.ajax({url: BASE_URL + urlToHit,
			      type:"POST",
			      data:{
			    	typeId:typeId,
			    	name:category,
			    	statusId:status,
			    	highlightId:highlight,
			    	categoryid:categoryId
			      },
			      success: function(result){
		        try{
		        	$('#myModal').modal('toggle');
		        	var list = result.resultList;
					fillCategoryData(list);

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

function fillCategoryData(list) {
	var tableValue = "";
	if(list.length > 0) {
		 for(var i=0;i<list.length;i++) {
			var obj = list[i];
			tableValue = tableValue + ("<tr class='info'>");
    		var typeName = "";
    		if(obj.typeName != null) {
    			typeName = obj.typeName;
    		}
    		var name = "";
    		if(obj.name != null) {
    			name = obj.name;
    		}
    		
    		tableValue = tableValue + ("<td>"+(typeName)+"</td>");
    		tableValue = tableValue + ("<td>"+(name)+"</td>");
    		tableValue = tableValue + "<td><a href = '#' data-toggle='modal' data-target='#myModal'  onclick=checkFlag('update');onClickMethodQuestion('"+(obj.categoryId)+"')>Update</a> / <a href='#' onclick=deleteCategory('"+(obj.categoryId)+"')>Delete</a></td>";
    		tableValue = tableValue + ("</tr>");
		}
		$("#categoryData").html(tableValue);
	} else {
		$("#categoryData").html("No records found.");		
	}
}

function deleteCategory(categoryId){
	  blockUI();
	  $.ajax({url: BASE_URL + "deletecategory/" + categoryId,
		      type:"GET",
		      success: function(result){
	    	  try{	
					var list = result.resultList;
					fillCategoryData(list);
					
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
            function check()
            {
            	
                var category = $("#category").val();
                var msgvalue = $("#msgvalue");
                var msg = $("#msg");
                msg.hide();
            	msgvalue.text("");
                if(category=="")
                {
                	msg.show();
                	msgvalue.text("Category cannot be left blank");
                    document.getElementById("category").focus();
                    return false;
                }
                $('#modal').modal('toggle');
                return true;
            }
        </script>

<script type="text/javascript">
function checkFlag(field) {
	document.getElementById("addUpdateFlag").value = field;
	if(field == 'update') {
		//document.getElementById("frm1").action = "updatecategory";
		document.getElementById("btnSave").value = "Update";
		$("#modelTitle").html("Edit Category");
	}
	else if(field == 'add') {
		//$("#cke_1_contents").html('');
		$(":text").val("");
   		//document.getElementById('categoryId').selectedIndex = 0;
		document.getElementById("btnSave").value = "Save";
		$("#modelTitle").html("Add Category");
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
            	$.get("getopenadd", function(data) {
            		unblockUI()
    	            var status = document.getElementById("status");
    	            for(var i = 0;i < data.statusList.length;i++) {
    	            	status.options[status.options.length] = new Option(data.statusList[i].status);
    	            	status.options[i].value = data.statusList[i].id;
    	            } 
    	            
    	            var type = document.getElementById("type");
    	            for(var i = 0;i < data.typeList.length;i++) {
    	            	type.options[type.options.length] = new Option(data.typeList[i].typeName);
    	            	type.options[i].value = data.typeList[i].typeId;
    	            }
    	            
    	            var highlight = document.getElementById("highlight");
    	            for(var i = 0;i < data.highlightList.length;i++) {
    	            	highlight.options[highlight.options.length] = new Option(data.highlightList[i].typeName);
    	            	highlight.options[i].value = data.highlightList[i].typeId;
    	            }
    	        });        		
        	} else {
        		blockUI()
        		$.get("getcategory/categoryId",{"categoryId" : quesId}, function(data) {
        			unblockUI()
        			document.getElementById("categoryid").value = data.categoryId;
                    $("#category").val(data.name);

                    var categoryType = document.getElementById("type");
                    var categoryList = data.typeList;
                    for(var i = 0;i < categoryList.length;i++) {
                    	categoryType.options[categoryType.options.length] = new Option(categoryList[i].typeName);
                    	categoryType.options[i].value = categoryList[i].typeId;
                    	if(categoryList[i].typeId == data.typeId) {
                    		document.getElementById("type").selectedIndex = i;
                    	}
                    }
                    
                    var highlight = document.getElementById("highlight");
                    var highlightList = data.highlightList;
                    for(var i = 0;i < highlightList.length;i++) {
                    	highlight.options[highlight.options.length] = new Option(highlightList[i].typeName);
                    	highlight.options[i].value = highlightList[i].typeId;
                    	if(highlightList[i].typeId == data.highlightId) {
                    		document.getElementById("highlight").selectedIndex = i;
                    	}
                    }
                    
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
           	$("#category").val("");
           	document.getElementById("status").innerHTML = "";
        	document.getElementById("type").innerHTML = "";
        	document.getElementById("highlight").innerHTML = "";
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
						<b class = "pageHeading">Category</b>
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
 						<input type="hidden" id = "categoryid" name= "categoryid" value = "" />					
						<input type="hidden" id = "addUpdateFlag" value = "" />					
	
					      <!-- Modal content-->
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title"><p id ="modelTitle">Add Category</p></h4>
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
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon">
														 <b>Type</b>												
													</span>
													<select class="form-control" name="typeId" id="type">
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
													 <b>CategoryName</b>												
												</span>
												<input type="text" class="form-control" placeHolder="Enter CategoryName" id="category" name="name" value="" autofocus />
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
													<select class="form-control" name="statusId" id="status">
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
														 <b>Highlight</b>												
													</span>
													<select class="form-control" name="highlightId" id="highlight">
													</select>
												</div>
											</div>
										</div>
									</div>
								</div>
								</div>
				        	</div>
				        	 <div class="modal-footer">
					          <input type="button" class="btn btn-primary"  id= "btnSave" value="Save" onclick="navigate()"/>
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
						<th>Type</th>
						<th>Category</th>
						<th>Links</th>
					</tr>
				</thead>
				<tbody id="categoryData">
				<c:choose>
					<c:when test="${LIST_CATEGORY.size() > 0}">
						<c:forEach items="${LIST_CATEGORY}" var="obj">
							<tr class="info">
								<td>${obj.typeName}</td>							
								<td>${obj.name}</td>
								<td><a href = "#" data-toggle="modal" data-target="#myModal" onclick="checkFlag('update');onClickMethodQuestion('${obj.categoryId}')">Update</a> / <a href="#" onclick="deleteCategory('${obj.categoryId}')">Delete</a></td>
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