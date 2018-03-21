<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	  type="text/css" href="<c:url value="/static/css/button.css"/>"/>
<link rel="stylesheet"
	  type="text/css" href="<c:url value="/static/css/input.css"/>"/>
<link rel="stylesheet"
	  type="text/css" href="<c:url value="/static/css/link.css"/>"/>

<script type="text/javascript" 
		 src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
		 
<script type="text/javascript">
	$().ready(function(){
		
		$("#email").keyup(function(){
			var value = $(this).val();
			if( value == "") {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});
		
		$("#nickname").keyup(function(){
			var value = $(this).val();
			if( value == "") {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});
		
		$("#password").keyup(function(){
			var value = $(this).val();
			if( value == "") {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});
		
		$("#password-confirm").keyup(function(){
			var value = $(this).val();
			var password = $("#password").val();
			
			if(value != password){
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password").removeClass("valid");
				$("#password").addClass("invalid");
			}
			else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password").removeClass("invalid");
				$("#password").addClass("valid");				
			}
			
			var password = $("#password-confirm").val();
			
			if(value != password){
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password-confirm").removeClass("valid");
				$("#password-confirm").addClass("invalid");
			}
			else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password-confirm").removeClass("invalid");
				$("#password-confirm").addClass("valid");				
			}
			
		});
	
		
		$("#registBtn").click(function() {
			if ($("#email").val() == ""){
				alert("이메익 입력해");
				$("#email").focus();
				$("#email").addClass("invalid");
				return false;
			}
			
			if($("#nickname").val() == "") {
				alert("Nickname을 입력하세여");
				$("#nickname").focus();
				$("#nickname").addClass("invalid");
				return false;
				
			}
			
			if($("#password").val() == "") {
				alert("password 를 입력하세여");
				$("#password").focus();
				$("#password").addClass("invalid");
				return false;
				
			}
			
			$("#registForm").attr({
								"method": "post",
								"action": "<c:url value="/regist"/>"
							})
							.submit();  
			
		});
	});
</script>
</head>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp"/>
		<form:form modelAttribute="registForm">
		
			<div>
				<!-- TODO Email 중복검사 하기 (ajax) -->
				<input type="email" id="email" name="email" placeholder="Email"
						value="${registForm.eamil }"/>
				<div>
					<form:errors path="email"/>
				</div>
			</div>
			
			<div>
				<!-- TODO Nickname 중복검사 하기 (ajax) -->
				<input type="text" id="nickname" name="nickname" placeholder="Nickname"	 
						value="${registForm.nickname}"/>
							<form:errors path="nickname"/>
			</div>
			
			<div>
				<input type="password" id="password" name="password" placeholder="Password"	/>
						<form:errors path="password"/>
			</div>
			
			<div>
				<input type="password" id="password-confirm"  placeholder="Password"/>
			</div>
			<div style="text-align:center;">
				<div id="registBtn" class="button">회원가입</div>
			</div>
		</form:form>
	</div>

	
</body>
</html>