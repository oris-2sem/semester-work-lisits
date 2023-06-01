<#import "addPage.ftlh" as page>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/myStyle.css">
    <title>Добавить товар</title>
</head>
<body>
<#macro values>
    <#if .template_name == "addHoney.ftlh">
        <#assign typeCounter = 1>
        <#assign itemType = "honey">
    </#if>
    <#if .main_template_name == "addCoffee.ftlh">
        <#assign typeCounter = 2>
        <#assign itemType = "coffee">
    </#if>
    <#if .main_template_name == "addTea.ftlh">
        <#assign typeCounter = 3>
        <#assign itemType = "tea">
    </#if>
    <#if .main_template_name == "addDish.ftlh">
        <#assign typeCounter = 4>
        <#assign itemType = "dish">
    </#if>
<#--    ${itemType}-->
    <form method="post" class="itemDto" id="itemDto-${typeCounter}">
        <table class="addItemTable">
            <tr>
                <td class="td-add-left"><label for="name">Название</label></td>
                <td class="td-add-right" width="200px"><input type="text" id="add-name-${typeCounter}" name="name"></td>
            </tr>
            <tr>
                <td class="td-add-left"><label for="description">Описание</label></td>
                <td class="td-add-right" width="200px"><textarea id="add-description-${typeCounter}" name="description"
                                                                 type="text"></textarea></td>
            </tr>
            <#nested>
            <tr>
                <td class="td-add-left"><label for="weight">Вес</label></td>
                <td class="td-add-right"><input type="number" id="add-weight-${typeCounter}" name="weight" min="1"
                                                max="4000"></td>
            </tr>
            <tr>
                <td class="td-add-left"><label for="price">Цена RUB</label></td>
                <td class="td-add-right"><input type="number" id="add-price-${typeCounter}" name="price" min="1"
                                                max="10000"></td>
            </tr>
        </table>
        <#--        <input  name="${_csrf.parameterName}" value="${_csrf.token}">-->
    </form>

    <form method="post" action="/upload/uploadFile" enctype="multipart/form-data" class="file-form"
          id="file-form-${typeCounter}">
        <input accept="image/*" type="file" id="files-${typeCounter}" name="file"/>
        <img id="image-${typeCounter}" class="uploadImage"/>
        <#--            <button type="submit" class="add__btn">Загрузить файл</button>-->
    </form>

<#--    <input type="hidden" id="typeCounter" name="typeCounter" value="${typeCounter}">-->

    <button type="submit" id="add__btn" class="add__btn" onclick="submitForms()">Сохранить</button>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">

    <script>
        document.getElementById('files-${typeCounter}').onchange = function () {
            var src = URL.createObjectURL(this.files[0]);
            document.getElementById('image-${typeCounter}').src = src;
        };

        function submitForms(event) {
            var formDataItemDto = {
                "name": $("#add-name-${typeCounter}").val(),
                "description": $("#add-description-${typeCounter}").val(),
                "country": $("#add-country-${typeCounter}").val(),
                "itemType": "${itemType?js_string}",
                "weight": $("#add-weight-${typeCounter}").val(),
                "price": $("#add-price-${typeCounter}").val()
            };
            var csrfToken = document.getElementById('token').value;
            console.log(csrfToken)
            console.log(formDataItemDto);
            $.ajax({
                type: 'POST',
                url: '/items/save',
                data: JSON.stringify(formDataItemDto),
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                },
                success: function (response) {
                    console.log('itemDto form submitted successfully');
                    var fileInput = document.getElementById('files-${typeCounter}');
                    var formDataFile = new FormData();
                    if (fileInput.files[0] !== null && fileInput.files[0] !== undefined) {
                        formDataFile.append('file', fileInput.files[0]);
                        var xhr = new XMLHttpRequest();
                        // beforeSend: function(xhr) {
                        // }
                        xhr.open('POST', '/upload/uploadFile', true);
                        xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                        xhr.onload = function () {
                            // Handle the response from the server
                            if (xhr.status === 200) {
                                // Request was successful
                                console.log('File upload and entity ID submission successful');
                                window.location.replace("/items");
                            } else {
                                // An error occurred during the request
                                console.error('Error uploading file and submitting entity ID');
                            }
                        };
                        xhr.send(formDataFile);
                    } else {
                        window.location.replace("/items");
                    }
                },
                error: function (response) {
                    alert("Ошибка добавления");
                }
            });
        }
    </script>
</#macro>

</body>
</html>