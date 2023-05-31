<#import "mainPage.ftlh" as main>
<#import "signIn.ftlh" as login>
<#import "addPage.ftlh" as adder>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="/css/myStyle.css">
    <link rel="stylesheet" type="text/css" href="/css/profile.css">
    <link rel="stylesheet" type="text/css" href="/css/signIn.css">
    <link rel="stylesheet" type="text/css" href="/css/addItem.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Профиль</title>
</head>
<body>

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">

<#macro loginForm>
    <div id="loginBlock" class="login-block">
        <form action="/signIn" method="post" class="loginForm" id="loginForm">
            <div class="email-block">
                <#--                <label for="email">Email: </label>-->
                <input type="email" name="email" placeholder="Email">
            </div>
            <div class="password-block">
                <#--                <label for="email">Пароль: </label>-->
                <input type="password" name="password" placeholder="Пароль">
            </div>
            <div class="checkbox-block">
                <input type="checkbox" name="rememberMe">Запомнить меня
            </div>
            <input type="submit" class="login-btn" value="Войти">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </form>
    </div>
</#macro>

<@main.header>
    <H1>Hi, ${user.name}</H1>

    <div class="sidebar">
        <button class="menu-btn active" data-target="profile">Профиль</button>
        <button class="menu-btn" data-target="orders">История заказов</button>
        <button class="menu-btn" data-target="discount">Накопленная скидка</button>
        <#list user.roles as role>
            <#if role == "ADMIN">
                <button class="menu-btn" data-target="itemsAdmin">Удалённые товары</button>
                <button class="menu-btn" data-target="addNewItem">Добавить товар</button>
            </#if>
        </#list>
    </div>

    <div class="form-content" id="profile">
        <h2>Регистрационные данные</h2>
        <#if user?has_content>
            <form action="/changeData" method="put" class="loginForm" id="changeForm">
                <table>
                    <tr>
                        <td>Email</td>
                        <td>Имя</td>
                        <td>Пароль</td>
                    </tr>
                    <tr>
                        <td><input type="email" name="new-email" placeholder="${user.email}"></td>
                        <td><input type="text" name="new-name" placeholder="${user.name}"></td>
                        <td rowspan="2"><input type="password" name="new-password" placeholder="Новый пароль"> <br>
                            <input type="password" name="new-password-2" placeholder="Подтвердите пароль"></td>
                    </tr>
                </table>
                <input type="submit" class="update-btn" value="Изменить данные">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </form>
        <#else>
            <h3>Для просмотра личной информации войдите в систему</h3>
            <@loginForm></@loginForm>
        </#if>
    </div>

    <div class="form-content" id="orders" style="display:none;">
        <h2>История заказов</h2>

        <#if user?has_content>
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
                        <#list orders as order>
                        if (counter === i) {
                            var price = '${order.totalPrice}'.replace(/\s/g, '');
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

            <div id="ordersStory" class="ordersStory">
                <table>
                    <thead>
                    <tr>
                        <th>Номер заказа</th>
                        <th>Дата оформления</th>
                        <th>Статус</th>
                        <th>Товары</th>
                        <th>Стоимость</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list orders as order>
                        <tr>
                            <td>${order.id}</td>
                            <#if order.state != 'CREATING'>
                                <td class="date">${order.date}</td>
                            <#else>
                                <td></td>
                            </#if>
                            <td>${order.state}</td>
                            <td>
                                <ul>
                                    <#list items as item>
                                        <#list order.itemsWithCount as items>
                                            <#if items.itemId = item.id>
                                                <li>Артикул: ${item.id}</li>
                                                <li><a href="/items/${item.itemType}/${item.id}">${item.name}</a></li>
                                                <li>${items.itemCount} шт.</li>
                                                <br>
                                            </#if>
                                        </#list>
                                    </#list>
                                </ul>
                            </td>
                            <td class="center-td">
                                <div class="prices">
                                    <div class="currencyPrice">
                                        ${order.totalPrice}
                                    </div>
                                    <div class="currencyName">
                                        RUB
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        <#else>
            <h3>Для просмотра истории заказов войдите в систему</h3>
            <@loginForm></@loginForm>
        </#if>
    </div>

    <div class="form-content" id="discount" style="display:none;">
        <h2>Накопленная скидка: ${user.personalDiscount}%</h2>
        <h3>Сумма всех ваших заказов ${totalCount} RUB</h3>
        <#assign nextDiscount = 10000>
        <#if (totalCount > 10000)>
            <#assign nextDiscount = 15000>
        <#elseif (totalCount >15000)>
            <#assign nextDiscount = 25000>
        <#elseif (totalCount >25000)>
            <#assign nextDiscount = 50000>
        <#elseif (totalCount >50000)>
            <#assign nextDiscount = 100000>
        <#elseif (totalCount >100000)>
            <#assign nextDiscount = 0>
        </#if>
        <#if nextDiscount = 0>
            <h3>Поздравляем, у вас максимальная скидка!</h3>
        <#else>
            <h3>До следующей скидки вам осталось заказать на ${nextDiscount - totalCount}</h3>
        </#if>

        <div class="discount-block-lk-values">
            <div class="discount-block-lk-header">
                <div class="discount-block-lk-left">
                    Если сумма всех Ваших покупок составляет
                </div>
                <div class="discount-block-lk-right">
                    Ваша скидка
                </div>
            </div>
            <div class="discount-block-lk-line">
                <div class="discount-block-lk-left">не менее <span>10000</span> руб.</div>
                <div class="discount-block-lk-right">3 %</div>
                <div class="clearfloat"></div>
            </div>
            <div class="discount-block-lk-line">
                <div class="discount-block-lk-left">не менее <span>15000</span> руб.</div>
                <div class="discount-block-lk-right">5 %</div>
                <div class="clearfloat"></div>
            </div>
            <div class="discount-block-lk-line">
                <div class="discount-block-lk-left">не менее <span>25000</span> руб.</div>
                <div class="discount-block-lk-right">10 %</div>
                <div class="clearfloat"></div>
            </div>
            <div class="discount-block-lk-line">
                <div class="discount-block-lk-left">не менее <span>50000</span> руб.</div>
                <div class="discount-block-lk-right">15 %</div>
                <div class="clearfloat"></div>
            </div>
            <div class="discount-block-lk-line">
                <div class="discount-block-lk-left">не менее <span>75000</span> руб.</div>
                <div class="discount-block-lk-right">20 %</div>
                <div class="clearfloat"></div>
            </div>
            <div class="discount-block-lk-line">
                <div class="discount-block-lk-left">не менее <span>100000</span> руб.</div>
                <div class="discount-block-lk-right">25 %</div>
                <div class="clearfloat"></div>
            </div>
        </div>

    </div>

    <div class="form-content" id="itemsAdmin" style="display:none;">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Название</th>
            </tr>
            </thead>
            <tbody>
            <#list deletedItems as item>
                <tr>
                    <td>${item.id}</td>
                    <td><a href="/items/${item.itemType}/${item.id}">${item.name}</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="form-content" id="addNewItem" style="display:none;">
        <@adder.itemsAdder></@adder.itemsAdder>
    </div>

<#--    form menu buttons-->
    <script>
        const menuButtons = document.querySelectorAll('.menu-btn');
        const contents = document.querySelectorAll('.form-content');

        menuButtons.forEach(button => {
            button.addEventListener('click', () => {
                const target = button.dataset.target;
                contents.forEach(content => {
                    if (content.id === target) {
                        content.style.display = 'block';
                    } else {
                        content.style.display = 'none';
                    }
                });
                menuButtons.forEach(btn => {
                    if (btn === button) {
                        btn.classList.add('active');
                    } else {
                        btn.classList.remove('active');
                    }
                });
            });
        });
    </script>

<#--    Change user's data-->
    <script>
        $('#changeForm').submit(function (event) {
            event.preventDefault();
            var formData = {
                "name": document.querySelector('input[name="new-name"]').value,
                "email": document.querySelector('input[name="new-email"]').value,
                "password": document.querySelector('input[name="new-password"]').value
            };
            console.log(formData)
            var csrfToken = document.getElementById('token').value;
            if (document.querySelector('input[name="new-password"]').value != document.querySelector('input[name="new-password-2"]').value) {
                alert('Введённые пароли не совпадают!')
            } else {
                $.ajax({
                    type: 'PUT',
                    url: '/changeData',
                    data: JSON.stringify(formData),
                    contentType: 'application/json',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                    },
                    success: function (response) {
                        alert("Данные успешно обновлены");
                        window.location.replace("/profile");
                    },
                    error: function (response) {
                        alert("Ошибка изменения данных");
                    }
                });
            }
        });
    </script>

</@main.header>

<@main.footer></@main.footer>
</body>
</html>