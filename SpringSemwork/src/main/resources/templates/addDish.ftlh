<#import "addItem.ftlh" as base>
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
<@base.values>
    <tr>
        <td class="td-add-left"><label for="country">Страна</label></td>
        <td class="td-add-right"><select name="country" id="add-country-4" class="country">
                <option>Россия</option>
                <option>Китай</option>
            </select></td>
    </tr>

    <input type="hidden" id="itemType" name="itemType" value="dish">
</@base.values>
</body>