<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!--  value 의 /static 은 경로명 아니고 그이후부터가 경로명임 -->
<script src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"
	type="text/javascript"></script>
<script type="text/javascript">
	$().ready(function(){
		<c:if test="${mode == 'modify' && not empty communityVO.displayFilename}">
		$("#file").closest("div").hide();
		</c:if>		
	
	
	// 클릭 됐는지 여부
	$("#displayFilename").change(function(){
		var isChecked = $(this).prop("checked");
		if (isChecked){
				$("label[for=displayFilename]").css({
					"text-decoration-line": "line-through",
					"text-decoration-style": "double",
					"text-decoration-color": "#FF0000"
				});
				$("#file").closest("div").show();
		}
		else {
			$("label[for=displayFilename]").css({
				"text-decoration": "none"
			});
				$("#file").closest("div").hide();
		}
	});
	
/* 
		<c:if test = "${sessionScope.status eq 'emptyTitle'}">
			$("#errorTitle").show();
		</c:if>

		<c:if test= "${sessionScope.status eq 'emptyBody'}">
			$("#errorBody").show();
		</c:if>

		<c:if test= "${sessionScope.status eq 'emptyDate'}">
			$("#errorDate").show();
		</c:if>
		
 */
		$("#writeBtn").click(function() {
		var mode = "${mode}";
			if (mode == 'modify') {
				var url = "<c:url value="/modify/${communityVO.id}"/>";
			} else {
				var url = "<c:url value="/write"/>"
			}

			var writeForm = $("#writeForm");
			writeForm.attr({
				"method" : "post",
				"action" : url

			});

			writeForm.submit();
		});
	});

</script>


</head>
<body>
	<div>
		<form:form modelAttribute="writeForm" enctype="multipart/form-data">

			<div>
				<input type="text" id="title" name="title" placeholder="제목" value="${communityVO.title }"/>
			</div>
			
			<div id="errorTitle" style="display: none;">제목을 입력하세요!</div>
			
			<div><form:errors path="title"/></div>
			
			<div>
				<textarea id="body" name="body" placeholder="내용">${communityVO.body}</textarea>
			</div>

			<div>
				<form:errors path="body"/>
			</div>
			
			<div id="errorBody" style="display: none;">내용을 입력하세요!</div>
			<div>
				<c:if test="${mode == 'modify' &&
							not empty communityVO.displayFilename }">
					<div>
						<input	type="checkbox"
								id="displayFilename"
								name="displayFilename"
								value="${communityVO.displayFilename}"/>
						<label for="displayFilename">
							${communityVO.displayFilename}
						</label>
					</div>			
				</c:if>
			</div>

			
		

			<div>
				<input type="hidden" id="userId" name="userId"
					value="${sessionScope.__USER__.id}" placeholder="아이디" />
			</div>
			
		
			<div>
				<input type="file" id="file" name="file" />
			</div>
			
			<div id="errorDate" style="display: none;">날짜를 입력하세요!</div>
			<div>
				<input type="button" id="writeBtn" value="등록" />
			</div>
			<div>
				<a href="<c:url value="/"/>">뒤로가기</a>
			</div>
		</form:form>


	</div>


</body>
</html>


