<#import "mainPage.ftlh" as main>
<#--<#import "signIn.ftlh" as signIn>-->
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="/css/myStyle.css">
    <link rel="stylesheet" type="text/css" href="/css/signIn.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Корзина</title>
    <style>
        .confirmOrder {
            /*display: inline-block;*/
            /*position: absolute;*/
            /*bottom: 0;*/
            font-weight: 400;
            font-family: teaFont, sans-serif;
            line-height: 1.5;
            width: 300px;
            color: #212529;
            text-align: center;
            vertical-align: middle;
            cursor: pointer;
            /*-webkit-user-select: none;*/
            /*-moz-user-select: none;*/
            user-select: none;
            background-color: #eee;
            border: 1px solid #bdbdbd;
            /*padding: .375rem .75rem;*/
            margin-left: -17px;
            margin-bottom: -2px;
            padding-left: 30px;
            padding-right: 30px;
            font-size: 1rem;
            /*border-radius: .25rem;*/
            transition: background-color .15s ease-in-out;
        }

        .login-block {
            position: relative;
            margin-top: 100px;
            width: 450px;
            margin-left: -270px;
        }

        table {
            width: 600px;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        th {
            border: 1px solid #bdbdbd;
            background: #597824;
            color: #eeeeee;
        }

        td {
            border: 1px solid #BFD4AD;
        }

        .center-td {
            border: 1px solid #BFD4AD;
            text-align: center;
        }

        a {
            color: #597824;
        }
    </style>
</head>
<body>
<@main.header>
    <select id="currencySelect" class="currency" onchange="updateCurrencyInfo()">
        <#list currencies?eval as key, value>
            <option value="${key}">${key}</option>
        </#list>
    </select>

    <script>
        function updateCurrencyInfo() {
            var selectedCurrency = document.getElementById("currencySelect").value;
            var currencyMap = <#noautoesc>${currencies}</#noautoesc>;
            var currencyInfoElements = document.getElementsByClassName("prices");

            for (var i = 0; i < currencyInfoElements.length; i++) {
                var currencyInfoElement = currencyInfoElements[i];
                var currencyNameElement = currencyInfoElement.getElementsByClassName("currencyName")[0];
                var counter = 0;
                <#list order.itemsWithCount as item>
                if(counter === i) {
                    var price = '${item.itemCost}'.replace(/\s/g, '');
                }
                counter++;
                </#list>
                var currencyValueElement = currencyInfoElement.getElementsByClassName("currencyPrice")[0];
                if (selectedCurrency in currencyMap) {
                    var currencyValue = currencyMap[selectedCurrency];
                    currencyNameElement.textContent = selectedCurrency;
                    if (selectedCurrency !== 'RUB') {
                        currencyValueElement.textContent = (price / currencyValue).toFixed(1);
                    } else {
                        currencyValueElement.textContent = (price / currencyValue).toFixed(0);
                    }
                }
            }
        }
    </script>

    <div class="items-container">
        <h1>ID заказа: ${order.id}</h1>
        <table>
            <tr>
                <th>Название</th>
                <th>Цена</th>
                <th>Артикул</th>
                <th>Количество</th>
            </tr>

            <#list order.itemsWithCount as item>
                <tr>
                    <#list items as itemNames>
                        <#if item.itemId = itemNames.id>
                            <td>${itemNames.name}</td>
                            <#break>
                        </#if>
                    </#list>
                    <td class="center-td">
                        <div class="prices">
                        <div class="currencyPrice">
                            ${item.itemCost}
                        </div>
                        <div class="currencyName">
                            RUB
                        </div>
                        </div>
                    </td>
                    <td class="center-td">${item.itemId}</td>
                    <td class="center-td">${item.itemCount}</td>
                    <td class="center-td"><a href="" onclick="deleteItem(${item.itemId})">Удалить</a></td>
                </tr>
            </#list>
        </table>
    </div>

    <script>
        function deleteItem(itemId) {
            var csrfToken = document.getElementById('token').value;

            var data = {
                "itemId": parseInt(itemId)
            };

            $.ajax({
                type: 'DELETE',
                url: '/order/deleteItem/'+itemId,
                data: JSON.stringify(data),
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                },
                success: function (response) {
                    // console.log('order successfully formed');
                    alert("Товар успешно удалён!");
                    window.location.replace("/bucket");
                },
                error: function (response) {
                    alert("Не удалось удалить товар");
                }
            });
        }
    </script>

    <#if user?has_content>
    <div class="user-id">
        <input name="user-id" value="${user.id}" type="hidden">
    </div>
        <button class="confirmOrder" type="submit" onclick="addToCart()">Оформить заказ</button>
    <#else>
    <br>
    <h2>Войдите в систему, чтобы оформить заказ</h2>
    <br>
    <div id="loginBlock" class="login-block">
        <form action="/signIn" method="post" class="loginForm" id="loginForm">
            <div class="email-block">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="email" name="email" placeholder="Email">
            </div>
            <div class="password-block">
                <input type="password" name="password" placeholder="Пароль">
            </div>
            <div class="checkbox-block">
                <input type="checkbox" name="rememberMe">Запомнить меня
            </div>
            <input type="submit" class="login-btn" value="Войти">
        </form>
    </div>
    </#if>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">

    <script>
        function addToCart() {

            if(!${order.itemsWithCount?size} == 0) {

            console.log("try to save")
            var id = document.querySelector('input[name="user-id"]').value;
            var data = {
                "userId": id
            };
            var csrfToken = document.getElementById('token').value;

            $.ajax({
                type: 'PUT',
                url: '/order/formOrder',
                data: JSON.stringify(data),
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                },
                success: function (response) {
                    console.log('order successfully formed');
                    window.location.replace("/profile");
                },
                error: function (response) {
                    console.log('error forming order');
                    // alert("Updating error");
                }
            });
            } else {
                alert("В корзине ещё ничего нет!")
            }
        }
    </script>

</@main.header>

<@main.footer></@main.footer>
</body>
</html>