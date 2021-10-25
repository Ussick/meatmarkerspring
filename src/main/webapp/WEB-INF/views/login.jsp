<%@ page pageEncoding="UTF-8" %>
<%@include file="header.jsp"%>
<font color=white>
<c:choose>
	<c:when test="${!isBlocked}">

		<c:choose>
			<c:when test="${showAForm}">
				<table>
					<form action="login" method="post">
						<tr><td>Login:</td><td><input type="text" name="login"/></td></tr>
						<tr><td>Password:</td><td><input type="password" name="password"/></td></tr>
						<tr><td> </td><td><input type="submit" value="SEND"/></td></tr>
					</form>
				</table>
			</c:when>

			<c:otherwise>
				<form action="" method="post">
					<tr><td> </td><td><input type="submit" name="logout" value="logout"/></td></tr>
				</form>
			</c:otherwise>
		</c:choose>

		<c:if test="${isAccessDenied}">
			<div style="text-align: center"><font color='red'>${accessDenied}</font></div>
			<h1></h1>
		</c:if>

</c:when>

<c:otherwise>
	<div style="text-align: center"><font color='red'>${block}</font></div>
	<h2></h2>
</c:otherwise>
</c:choose>
</font>
<%@include file="footer.jsp"%>

