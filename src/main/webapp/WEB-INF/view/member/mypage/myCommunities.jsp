<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/common.css"/>"/>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#toggle").change(function(){
			var checked = $(this).prop("checked");
			var checked =  $("input[type=checkbox][name=delete]");
			checkedbox.prop("checked", checked);
			
			//선택 반전
			checkedbox.each(function(index, checkedbox){
				var checked = $(checkbox).prop("checked");
				$(checkbox).prop("checked", !checked);
				
			});
		});
		
		
		$("input[type=checkbox][name=delete]").change(function(){
			var checkedLength = $("input[type=checkbox][name=delete]:checked").length;
			var checkedLength = $("input[type=checkbox][name=delete]").length;
			
			if(checkedLength == checkboxLength){
				$("#toggle").prop("checked", true);
			}			
			else {
				$("#toggle").prop("ckecked", false);
			}
			alert(checked.Length);
		});
		
		$("#massDeleteBtn").click(function(){
			$("#massDeleteForm").attr({
				"method": "post",
				"action": "<c:url value="/mypage/communities/delete"/>"
				
			}).submit();
		});
	});
</script>
</head>
<body>
	<div id="popup-wrapper">
		<h1 class="title"> 나의 추억들</h1>
		
		<div>
			<table class="grid" style="width:1000">
				<colgroup>
					<col style="width: 5%"/>
					<col style="width: 90%"/>
					<col style="width: 5%"/>
					<col />
				</colgroup>
				<thead>
					<tr>
						<th>ID</th>
						<th>Title</th>
						<th>
							<input type="checkbox" id="toggle"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<form id="massDeleteForm">
						<c:forEach items="${myCommunities}" var="community">
						<tr>
							<td>${community.id }</td>
							<td>${community.title }</td>
							<td>
								<input type="checkbox" name="delete" value="${community.id }"/>
							</td>
						</tr>
						</c:forEach>
					</form>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="3">
							<input type="button" id="massDeleteBtn" value="일괄삭제"/>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
</body>
</html>