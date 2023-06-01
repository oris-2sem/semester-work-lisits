<#import "mainPage.ftlh" as main>
<!doctype html>
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
    <title>${item.name}</title>
    <style>
        .item-image {
            border: 2px #597824;
            margin-right: 20px;
            /*width: 400px;*/
            /*!*height: auto;*!*/
        }

        .top {
            display: flex;
        }

        .top .order-container {
            position: absolute;
            right: 300px;
            top: 350px;
        }

        .order-container .box {
            display: inline-flex;
            position: relative;
            /*bottom: 0px;*/
            width: 200px;
            align-items: center;
            justify-content: space-between;
        }

        .order-container .box .price-title {
            font-size: 1.5rem;
            margin-right: 10px;
            width: 200px;
            display: inline-flex;
        }

        .order-container .box .price-title .price {
            font-weight: 600;
        }

        .order-container .box .quantity-form {
            height: 30px;
            width: 100px;
            right: -110px;
            /*margin-bottom: -30px;*/
            /*margin-right: -10px;*/
        }

        .order-container .add-to-card-btn {
            margin-top: 0;
            margin-left: 0;
            position: inherit;
            bottom: 0;
            left: 0;
        }

        .change-header {
            color: #597824;
        }

        .update-btn {
            display: inline-block;
            font-weight: 400;
            font-family: teaFont, sans-serif;
            line-height: 1.5;
            width: 300px;
            height: 40px;
            color: #597824;
            text-align: center;
            vertical-align: middle;
            cursor: pointer;
            user-select: none;
            background-color: #eee;
            border: 1px solid #bdbdbd;
            padding-left: 30px;
            padding-right: 30px;
            margin-bottom: 10px;
            font-size: 1rem;
            transition: background-color .15s ease-in-out;
        }

        input[type="number"] {
            font-family: inherit; /* 1 */
            font-size: inherit; /* 1 */
            line-height: inherit; /* 1 */
            margin-top: 10px; /* 2 */
        }

        input[type="file"] {
            font-family: inherit; /* 1 */
            font-size: inherit; /* 1 */
            line-height: inherit; /* 1 */
            /*margin-top: 10px; !* 2 *!*/
        }

        textarea {
            font-family: inherit; /* 1 */
            font-size: inherit; /* 1 */
            line-height: inherit; /* 1 */
            margin-bottom: -8px;
            margin-top: 11px;
            width: 198px;
        }

        .description {
            width: 400px;
        }

        .file-form {
            position: sticky;
            width: 500px;
            /*margin-left: -300px;*/
            /*margin-top: -30px;*/
        }
    </style>
</head>
<body>
<@main.header>
    <h2>${item.name}</h2>
    <script>
        function updateCurrencyInfo() {
            var selectedCurrency = document.getElementById("currencySelect").value;
            var currencyMap = <#noautoesc>${currencies}</#noautoesc>;

            var currencyNameElement = document.getElementById("currencyName");
            var currencyValueElement = document.getElementById("currencyValue");

            if (selectedCurrency in currencyMap) {
                var currencyValue = currencyMap[selectedCurrency];
                currencyNameElement.textContent = selectedCurrency;
                currencyValueElement.textContent = (${item.price} / currencyValue).toFixed(1);
            }
        }
    </script>
    <select id="currencySelect" class="currency" onchange="updateCurrencyInfo()">
        <#list currencies?eval as key, value>
            <option value="${key}">${key}</option>
        </#list>
    </select>

    <div class="top">
        <div class="item-image">
            <#if images?size gt 0>
                <#list images as image>
                    <img src="/storage/${image.storageFileName}" alt="itemImage" height="auto" width="300px">
                </#list>
            <#else>
                <img src="/images/defaultItem.jpg" alt="defaultItemImage" height="auto" width="300px"
                     class="item-image">
            </#if>
        </div>
        <div class="information">
            <h3>Артикул: ${item.id}</h3>
            <div class="id-form">
                <input name="item-id" value="${item.id}" type="hidden">
            </div>
            <div class="price-form">
                <input name="item-price" value="${item.price}" type="hidden">
            </div>
            <h3 class="description">${item.description}</h3>
        </div>
            <div class="order-container">
                <#if item.state != 'DELETED'>
                <div class="box">
                    <div class="price-title">
                        <div>
                            <#if item.price??>
                                <span class="price" id="currencyValue">${item.price}</span>
                            </#if>
                            <span id="currencyName">RUB</span>
                        </div>
                    </div>
                    <div class="quantity-form">
                        <button class="minus">-</button>
                        <input type="text" value="1" name="quantity">
                        <button class="plus">+</button>
                    </div>
                    <div class="id-form">
                        <input name="item-id" value="${item.id}" type="hidden">
                    </div>
                    <div class="price-form">
                        <input name="item-price" value="${item.price}" type="hidden">
                    </div>
                </div>
                <button class="add-to-card-btn" onclick="addToCart()">
                    <span>В корзину</span>
                    <img src="/images/bucket.png" class="bucket-image">
                </button>
                <#else>
                    <h4>Товар недоступен для заказа</h4>
                </#if>
            </div>
    </div>

<#if user?has_content>

<#list user.roles as role>
<#if role == "ADMIN">
    <h2 class="change-header">Изменить данные товара</h2>
    <form method="post" action="/upload/uploadFile" enctype="multipart/form-data" class="file-form"
          id="file-form">
        <input accept="image/*" type="file" id="files" name="file"/>
        <img id="image" class="uploadImage"/>
    </form>
    <div class="text-field">
        <form class="changeItemForm" method="put" action="/items/${item.id}/update">
            <table class="changeItemTable">
                <tr>
                    <td class="td-add-left"><label for="name">Название</label></td>
                    <td class="td-add-right" width="200px"><input type="text" id="name" name="name"></td>
                </tr>
                <tr>
                    <td class="td-add-left"><label for="description">Описание</label></td>
                    <td class="td-add-right" width="200px"><textarea id="description" name="description"
                                                                     type="text"></textarea></td>
                </tr>
                <tr>
                    <td class="td-add-left"><label for="weight">Вес</label></td>
                    <td class="td-add-right"><input type="number" id="weight" name="weight" min="1" max="4000"></td>
                </tr>
                <tr>
                    <td class="td-add-left"><label for="price">Цена</label></td>
                    <td class="td-add-right"><input type="number" id="price" name="price" min="1" max="10000"></td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">
        </form>

        <button type="submit" id="update-item-btn" class="update-btn" onclick="submitForms()">Изменить данные товара
        </button>

        <#if item.state != 'DELETED'>
            <form>
                <#--            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">-->
                <input type="submit" class="update-btn" id="delete-btn" value="Удалить товар" onclick="deleteItem()">
            </form>
            <#else>
                <form>
                    <input type="submit" class="update-btn" id="back-btn" value="Восстановить товар" onclick="backItem()">
                </form>
        </#if>

    </div>

<#--    update item-->
    <script>
        document.getElementById('files').onchange = function () {
            var src = URL.createObjectURL(this.files[0]);
            document.getElementById('image').src = src;
        };

        function submitForms(event) {
            var formDataItemDto = {
                "id": ${item.id},
                "name": $("#name").val(),
                "description": $("#description").val(),
                // "country": $("#country").val(),
                <#--"itemType": "${itemType?js_string}",-->
                "weight": $("#weight").val(),
                "price": $("#price").val()
            };
            var csrfToken = document.getElementById('token').value;
            console.log(formDataItemDto);
            <#--if (${images?size} !== 0) {-->
            <#--    var path = '/upload/updateFile/${item.id}'-->
            <#--} else {-->
            <#--    var path = '/upload/uploadFile'-->
            <#--}-->
            $.ajax({
                type: 'PUT',
                url: '/items/${item.id}/update',
                data: JSON.stringify(formDataItemDto),
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                },
                success: function (response) {
                    console.log('item updated successfully');
                    var fileInput = document.getElementById('files');
                    var formDataFile = new FormData();
                    formDataFile.append('file', fileInput.files[0]);
                    var xhr = new XMLHttpRequest();
                    xhr.open('POST', '/upload/updateFile/${item.id}', true);
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                    xhr.onload = function () {
                        // Handle the response from the server
                        if (xhr.status === 200) {
                            // Request was successful
                            console.log('File upload');
                            window.location.replace("/items/${item.itemType}/${item.id}");
                        } else {
                            // An error occurred during the request
                            console.error('Error uploading file');
                        }
                    };
                    xhr.send(formDataFile);
                },
                error: function (response) {
                    alert("Updating error");
                }
            });
        }
    </script>

</#if>
</#list>
</#if>
<#--    add to cart -->
    <script>
        function addToCart() {
            // Получаем значение quantity и айди товара
            var quantity = parseInt(document.querySelector('input[name="quantity"]').value);
            var itemId = parseInt(document.querySelector('input[name="item-id"]').value);
            var price = document.querySelector('input[name="item-price"]').value;

            // Создаем объект с данными для отправки

            var data = {
                itemCount: quantity,
                itemCost: price.replace(/\s/g, ''),
                itemId: itemId,
                orderId: null
            };

            var csrfToken = document.getElementById('token').value;

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
    </script>

<#--    delete item-->
    <script>
        function deleteItem() {
            var itemId = parseInt(${item.id});

            var data = {
                itemId: itemId,
            };
            var csrfToken = document.getElementById('token').value;
            $.ajax({
                type: 'DELETE',
                url: '/items/${item.id}/delete',
                data: JSON.stringify(data),
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                },
                success: function (response) {
                    window.location.replace("/items");
                },
                error: function (response) {
                    alert("Ошибка удаления товара");
                }
            });
        }
    </script>

<#--    restore item-->
    <script>
        function backItem() {
            var itemId = parseInt(${item.id});

            var data = {
                itemId: itemId,
            };
            var csrfToken = document.getElementById('token').value;
            console.log(csrfToken);
            $.ajax({
                type: 'PUT',
                url: '/items/${item.id}/back',
                data: JSON.stringify(data),
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                },
                success: function (response) {
                    window.location.replace("/items");
                },
                error: function (response) {
                    alert("Ошибка оживления товара");
                }
            });
        }
    </script>


</@main.header>
<@main.footer></@main.footer>
</body>
</html>