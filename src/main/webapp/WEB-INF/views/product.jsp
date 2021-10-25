<%@ page pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<font color=white>
    <c:if test="${product!=null}">
            <table>
                <tr><td>${product.name}</td><td></td></tr>
                <tr><td> <a href="products?id=${product.id}">
                    <img src="static/images/${product.id}.jpg" width="250" height="100%"/>
                </a></td><td>${product.description}</td></tr>
                <td>${product.price}</td><td>

            <form action="cart" method="post">
                <img src='static/images/minus.jpg' width='25' height='25' onclick="sum('quan${product.id}', -1)"/>
                <input type='text' pattern='[0-9]{1,2}' size='2' id='quan${product.id}' name='quantity' value='1'/>
                <img src='static/images/plus.jpg' width='25' height='25' onclick="sum('quan${product.id}', 1)"/>
            <input type="hidden" name="id" id="${product.id}" value="${product.id}"/>
            <input type="button" name="operation" id="oper" value="InCart" onclick='sendParam("oper", "quan${product.id}", "${product.id}")'/>
            </form>
				</td></tr>
            </table></br></br>
    </c:if>
</font>
<%@include file="footer.jsp" %>
