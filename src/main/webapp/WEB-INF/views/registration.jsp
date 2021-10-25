<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<font color=white>
<c:if test="${showform}">
    <table>
        <form action="registration" method="post">
            <tr>
                <td>Login(Your Email):</td>
                <td><input type="email" name="login" value=${user.login}></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input type="text" name="name" value=${user.name}></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" value=${user.password}></td>
            </tr>
            <tr>
                <td>Repeat password:</td>
                <td><input type="password" name="passwordRepeat" value=${user.passwordRepeat}></td>
            </tr>
            <tr>
                <td>Gender:</td>
                <td>M<input type="radio" name="gender"
                            value="M" <c:if test="${user.gender.equals('M')}"> checked="true"</c:if>>
                    F<input
                        type="radio" name="gender"
                        value="F" <c:if test="${user.gender.equals('F')}"> checked="true"</c:if>  /></td>
            </tr>
            <tr>
            <tr>
                <td>Region:</td>
                <td><select name="region">
                    <option value="DNR"
                    <c:if test="${user.region.equals('DNR')}">
                        selected='true'
                    </c:if>
                    >ДНР
                    </option>
                    <option value="LNR"
                            <c:if test="${user.region.equals('LNR')}">
                                selected='true'
                            </c:if>
                    >ЛНР
                    <option value="Crimea"
                            <c:if test="${user.region.equals('Crimea')}">
                                selected='true'
                            </c:if>
                    >Крым
                    </option>
                </select></td>
            </tr>
            <tr>
                <td>Comment:</td>
                <td><textarea rows=10 cols=20
                              name="comment">${user.comment}</textarea>
                </td>
            </tr>
            <tr>
                <td>I agree to install an Amigo Browser:</td>
                <td><input type="checkbox" name="browser" checked="true"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="SEND"/></td>
            </tr>
        </form>
    </table>

    <c:if test="${isError}">
        <h1 style="float: left; margin-left: 400px; margin-top: -250px; font-size:11px; font-weight: normal">
                ${result}
        </h1>
    </c:if>
</c:if>

<c:if test="${!isError}">
    <h1 style='font-size:25px' align='center'>
            ${result}
    </h1>

</c:if>
</font>
<%@include file="footer.jsp"%>





