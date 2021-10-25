<%@ page pageEncoding="UTF-8" %>
</div>

<div id="sidebar">
    <table border=1>
        <tr>
            <td width="252" align="left">
                <font color=white>
                    <c:choose>
                        <c:when test="${sessionScope.authorized!=null}">Вы авторизировались как ${sessionScope.authorized}</c:when><c:otherwise>
                        Вы не авторизованы
                    </c:otherwise>
                    </c:choose><br/>
                    В вашей <a href="cart"><font color=white> корзине </font></a> <span
                        id="cartSize">${(sessionScope.productsCartMapSize==null?0:productsCartMapSize)} </span> товаров.
                </font>
            </td>
        </tr>
    </table>
    <h2>Боковое меню</h2>
    <ul>
        <font color=white>
            <li><a href="products?category=1"><font color=white>Myaso</font></a></li>
            <li><a href="products?category=2"><font color=white>Kolbasa</font></a></li>
            <li><a href="products?category=3"><font color=white>Salo</font></a></li>
            <li><a href="registration"><font color=white>Регистрация</font></a></li>
            <li><a href="login"><font color=white>Вход</font></a></li>
            <li><a href="cart"><font color=white>Корзина</font></a></li>
    </ul>
    </font>
</div>
</div>
</div>
</div>
</div>
<div id="footer">
    <p>Copyright (c) by Бондаренко Антон</p>
</div>
<!-- end #footer -->
</body>

<script>

    function sum(id, data) {
        var val1 = document.getElementById(id).value;
        if ((+val1 + +data) > 0) {
            document.getElementById(id).value = +document.getElementById(id).value + +data;
        }
    }

    function sayInfo(qId, product) {
        console.log("Вы купили " + product + " в количестве " + document.getElementById(qId).value + " шт.");
    }

    function sendParam(operId, quanPid, pId) {
        var oper = document.getElementById(operId).value;
        var quan = document.getElementById(quanPid).value;
        var prod = document.getElementById(pId).value;

        //quantity=1&id=1&operation=Buy
        //quantity=1&id=1&operation=Change

        $.ajax({
            url: "cart",         /* Куда пойдет запрос */
            method: "post",             /* Метод передачи (post или get) */
            data: "quantity=" + +quan + "&id=" + +prod + "&operation=" + oper,     /* Параметры передаваемые в запросе. */
            success: function (data) {   /* функция которая будет выполнена после успешного запроса.  */
                var comma = ",";
                var arrayOfStrings = data.split(comma);
                document.getElementById("cartSize").innerHTML = arrayOfStrings[0];
				document.getElementById("totalSum").innerHTML = arrayOfStrings[1];
            }
        });
    }

    function sumCart(id, data) {
        var val1 = document.getElementById(id).value;
        if ((+val1 + +data) > -1) {
            document.getElementById(id).value = +document.getElementById(id).value + +data;
        }
    }

</script>
</html>
