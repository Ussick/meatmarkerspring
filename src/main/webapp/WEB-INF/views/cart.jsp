<%@ page pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<font color=white>
    <c:choose>
        <c:when test="${(sessionScope.productsCartMapSize!=0)}">
            <c:forEach var="product" items="${sessionScope.productsCartMap}">
                <table>
                    <tr><td>${product.key.name}</td><td></td></tr>
                    <tr><td> <a href="products?id=${product.key.id}"> <img src="static/images/${product.key.id}.jpg" width="150" height="100%"/></a></td><td>${product.key.description}</td></tr>
                    <td>${product.key.price}</td></tr>
                    <tr><td></td><td>
                    <form action="cart" method="post">
                        <img src='static/images/minus.jpg' width='25' height='25' onclick="sumCart('quan${product.key.id}', -1)"/>
                        <input type='text' pattern='[0-9]{1,2}' size='2' id='quan${product.key.id}' name='quantity' value='${product.value}'/>
                        <img src='static/images/plus.jpg' width='25' height='25' onclick="sumCart('quan${product.key.id}', 1)"/>
                        <input type="hidden" name="id" id="${product.key.id}" value="${product.key.id}"/>
                        <input type="button" name="operation" id="oper" value="Change" onclick='sendParam("oper", "quan${product.key.id}", "${product.key.id}")'/>
                    </form>
                </table>
            </c:forEach>
            </br></br>
            <h1> Готовьтесь расстаться с вашими деньгами на сумму <span id="totalSum">${sessionScope.total} </span> грн.</h1>
        </c:when>
        <c:otherwise>
            <h1> Ваша корзина пуста! </h1>
        </c:otherwise>
    </c:choose>
</font>
<%@include file="footer.jsp" %>
