<#import "spring.ftl" as spring/>
<#import "mainPage.ftlh" as main>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="/css/myStyle.css">
    <link rel="stylesheet" type="text/css" href="/css/items.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/items.js"></script>
    <#if itemType??>
        <title>${itemType}</title>
    <#else>
        <title>Items</title>
    </#if>
    <#--    отображение кнопок-->
    <script type="text/javascript">
        function showModalWin() {
            var darkLayer = document.createElement('div');
            darkLayer.id = 'shadow';
            document.body.appendChild(darkLayer);

            var modalWin = document.getElementById('popupWin');
            modalWin.style.display = 'block';

            darkLayer.onclick = function () {
                darkLayer.parentNode.removeChild(darkLayer);
                modalWin.style.display = 'none';
                return false;
            };
        }
    </script>

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
            var currencyInfoElements = document.getElementsByClassName("price-title");

            for (var i = 0; i < currencyInfoElements.length; i++) {
                var currencyInfoElement = currencyInfoElements[i];
                var currencyNameElement = currencyInfoElement.getElementsByClassName("currencyName")[0];
                var counter = 0;
                <#list itemsList as item>
                if(counter === i) {
                    var price = '${item.price}'.replace(/\s/g, '');
                }
                counter++;
                </#list>
                var currencyValueElement = currencyInfoElement.getElementsByClassName("price")[0];
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

<#if itemsList?size == 0>
        <h1>Здесь пока ничего нет, но обязательно будет!</h1>
    <#else>
        <div class="card-container">
            <#list itemsList as item>
                <div class="card">
                    <#assign isImage = 0>
                    <#list images as image>
                        <#if (image.item.id = item.id)>
                            <a href="/items/${item.itemType.toString()}/${item.id}"><img
                                        src="/storage/${image.storageFileName}"
                                        alt=${item.name} height="200px"
                                        width="200px"
                                        class="card-img-top"></a>
                        <#else>
                            <#assign isImage = isImage + 1>
                        </#if>
                    </#list>
                    <#if images?size == isImage>
                        <a href="/items/${item.itemType.toString()}/${item.id}"><img
                                    src="/images/defaultItem.jpg" alt=${item.name} height="200px"
                                    width="200px"
                                    class="card-img-top"></a>
                    </#if>
                    <a href="/items/${item.itemType.toString()}/${item.id}" class="name">${item.name}</a>
                    <p class="card-text" id="description-${item.id}">${item.description}</p>

                    <div class="box">
                        <div class="price-title">
                            <#if item.price??>
                                <span class="price" id="currencyValue">${item.price}</span>
                            </#if>
                            <span id="currencyName" class="currencyName">RUB</span>
                            <div class="id-form">
                                <input name="item-id" value="${item.id}" type="hidden">
                            </div>
                            <div class="price-form">
                                <input name="item-price" value="${item.price}" type="hidden" class="itemPrice">
                            </div>
                        </div>
                        <div class="quantity-form">
                            <button class="minus">-</button>
                            <input type="text" value="1" name="quantity">
                            <button class="plus">+</button>
                        </div>

                    </div>

                    <button class="add-to-card-btn">
                        <span>В корзину</span>
                        <img src="/images/bucket.png" class="bucket-image">
                    </button>
                </div>
            <#--                обрезание лишнего текста в описании-->
                <script>
                    var descriptionElement = document.getElementById("description-${item.id}");
                    var maxLength = 80; // Максимальная длина описания

                    if (descriptionElement.textContent.length > maxLength) {
                        var trimmedText = descriptionElement.textContent.slice(0, maxLength) + "...";
                        descriptionElement.textContent = trimmedText;
                    }
                </script>
            </#list>
        </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">
    <#--        script for adding to cart-->
        <script>
            // Получаем родительский элемент, содержащий все кнопки add-to-card-btn
            var container = document.querySelector('.card-container');
            var csrfToken = document.getElementById('token').value;
            // Назначаем обработчик события клика на родительский элемент
            container.addEventListener('click', function (event) {
                // Проверяем, что цель клика имеет класс add-to-card-btn
                if (event.target.classList.contains('add-to-card-btn')) {
                    // // Получаем необходимые данные, например, айди товара и количество
                    // var itemId = event.target.dataset.itemId;
                    var quantity = event.target.parentNode.querySelector('.quantity-form input').value
                    var itemId = event.target.parentNode.querySelector('.id-form input').value;
                    var price = event.target.parentNode.querySelector('.price-form input').value;

                    // Создаем объект с данными для отправки
                    var data = {
                        itemCount: quantity,
                        itemCost: price.replace(/\s/g, ''),
                        itemId: itemId,
                        orderId: null
                    };
                    console.log(data)

                    $.ajax({
                        type: 'POST',
                        url: '/order/addToCart',
                        data: JSON.stringify(data),
                        contentType: 'application/json',
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                        },
                        success: function (response) {
                            console.log('item added to cart successfully');
                            alert("Товар успешно добавлен в заказ!")
                        },
                        error: function (response) {
                            console.log('error adding to cart');
                            alert("При добавлении товара в заказ возникла ошибка :(");
                        }
                    });
                }
            });
        </script>
    </#if>

</@main.header>

<@main.footer></@main.footer>
</body>
</html>