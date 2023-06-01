<#import "mainPage.ftlh" as main>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/myStyle.css">
    <title>Добавить товары</title>
    <style>
        *,
        *::before,
        *::after {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            /*font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";*/
            font-size: 16px;
            font-weight: 400;
            line-height: 1.5;
            color: #212529;
            text-align: left;
            background-color: #fff;
        }

        .tabs {
            max-width: 350px;
            margin-left: auto;
            margin-right: auto;
        }

        .tabs > input[type="radio"] {
            display: none;
        }

        .tabs > input[type="radio"]:checked + label {
            background-color: #bdbdbd;
        }

        .tabs > div {
            /* ������ ������� �� ��������� */
            display: none;
            border: 1px solid #eee;
            padding: 10px 15px;
            border-radius: 4px;
        }

        #tab-btn-1:checked ~ #content-1,
        #tab-btn-2:checked ~ #content-2,
        #tab-btn-3:checked ~ #content-3,
        #tab-btn-4:checked ~ #content-4 {
            display: block;
        }

        .tabs > label {
            display: inline-block;
            text-align: center;
            vertical-align: middle;
            user-select: none;
            background-color: #eee;
            border: 1px solid transparent;
            padding: 2px 8px;
            font-size: 16px;
            line-height: 1.5;
            border-radius: 4px;
            transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out;
            margin-left: 6px;
            cursor: pointer;
            margin-bottom: 10px;
        }

        .tabs > label:first-of-type {
            margin-left: 0;
        }
    </style>

</head>

<body>
<#macro itemsAdder>

<div class="tabs">
    <input type="radio" name="tab-btn-1" id="tab-btn-1" value="">
    <label for="tab-btn-1">Honey</label>
    <input type="radio" name="tab-btn-2" id="tab-btn-2" value="">
    <label for="tab-btn-2">Coffee</label>
    <input type="radio" name="tab-btn-3" id="tab-btn-3" value="">
    <label for="tab-btn-3">Tea</label>
    <input type="radio" name="tab-btn-4" id="tab-btn-4" value="">
    <label for="tab-btn-4">Dish</label>

    <div id="content-1">
<#--        Контент 1...-->
        <div id="itemContainer1">
            <input type="hidden" id="itemType-1" name="itemType" value="honey">
        </div>
    </div>
    <div id="content-2">
<#--        Контент 2...-->
        <div id="itemContainer2">
            <input type="hidden" id="itemType-2" name="itemType" value="coffee">
        </div>
    </div>
    <div id="content-3">
<#--        Контент 3...-->
        <div id="itemContainer3">
            <input type="hidden" id="itemType-3" name="itemType" value="tea">
        </div>
    </div>
    <div id="content-4">
<#--        Контент 4...-->
        <div id="itemContainer4">
            <input type="hidden" id="itemType-4" name="itemType" value="dish">
        </div>
    </div>
</div>
    <script>

    $(document).ready(function () {
        $('input[name="tab-btn-1"]').change(function () {
            var selectedValue = $(this).next('label').text().toLowerCase();
            $('#itemType').val(selectedValue);
        $('input[name="tab-btn-2"], input[name="tab-btn-3"], input[name="tab-btn-4"]').not(this.name).prop('checked', false);
            $(this).prop('checked', true)
            $.ajax({
            type: 'GET',
            url: '/items/add/' + selectedValue,
            success: function (response) {
                $('#itemContainer1').html(response);
            }
        });
        });
    });

    $(document).ready(function () {
        $('input[name="tab-btn-2"]').change(function () {
            var selectedValue = $(this).next('label').text().toLowerCase();
            $('#itemType').val(selectedValue);
            $('input[name="tab-btn-1"], input[name="tab-btn-3"], input[name="tab-btn-4"]').not(this.name).prop('checked', false);
            $(this).prop('checked', true)
            $.ajax({
                type: 'GET',
                url: '/items/add/' + selectedValue,
                success: function (response) {
                    $('#itemContainer2').html(response);
                }
            });
        });
    });

    $(document).ready(function () {
        $('input[name="tab-btn-3"]').change(function () {
            var selectedValue = $(this).next('label').text().toLowerCase();
            $('#itemType').val(selectedValue);
            $('input[name="tab-btn-1"], input[name="tab-btn-2"], input[name="tab-btn-4"]').not(this.name).prop('checked', false);
            $(this).prop('checked', true)
            $.ajax({
                type: 'GET',
                url: '/items/add/' + selectedValue,
                success: function (response) {
                    $('#itemContainer3').html(response);
                }
            });
        });
    });

    $(document).ready(function () {
        $('input[name="tab-btn-4"]').change(function () {
            var selectedValue = $(this).next('label').text().toLowerCase();
            $('#itemType').val(selectedValue);
            $('input[name="tab-btn-1"], input[name="tab-btn-2"], input[name="tab-btn-3"]').not(this.name).prop('checked', false);
            $(this).prop('checked', true)
            $.ajax({
                type: 'GET',
                url: '/items/add/' + selectedValue,
                success: function (response) {
                    $('#itemContainer4').html(response);
                }
            });
        });
    });

</script>
</#macro>
<@main.footer></@main.footer>
</body>

</html>