<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="/css/myStyle.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Tea Shop</title>
</head>
<body>
<#macro header>
    <header class="sticky-header">
        <nav>
            <ul>
                <li><a href="/items">Каталог</a></li>
                <li><span class="divider"></span></li>
                <li><a href="/info">О компании</a></li>
                <li><span class="divider"></span></li>
                <li><a href="/delivery">Доставка</a></li>
                <li><span class="divider"></span></li>
                <#if user?has_content>
                    <li><a href="/profile">Мой кабинет</a></li>
                    <li><span class="divider"></span></li>
                    <li><a href="/logout">Выход</a></li>
                <#else>
                    <li><a href="/signIn">Войти</a></li>
                    <li><span class="divider"></span></li>
                    <li><a href="/signUp">Регистрация</a></li>
                </#if>
            </ul>
        </nav>
    </header>

    <header class="underheader">
        <div class="container">
            <a href="/" class="logo"><img src="/images/logo.png"></a>

            <div class="text-field">
                <div class="text-field__group">
<#--                    <form action="/items/search" id="search-form" class="search-form" method="get">-->
                        <input class="text-field__input" type="search" id="searchInput" name="search"
                               placeholder="Поиск по названию и описанию">
                        <button class="text-field__btn" type="button">Найти</button>
                    <ul id="searchResults" class="searchResults"></ul>
                    <#--                    </form>-->
                </div>
            </div>
            <a href="/bucket" class="bucket"><img src="/images/bucket.png"></a>
        </div>

    </header>

    <div class="content">
        <#nested>
    </div>

<#--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">-->

    <script>
        document.querySelector('.text-field__btn').addEventListener('click', function() {
            var searchValue = document.getElementById('searchInput').value;
            window.location.href = '/items/search?query=' + encodeURIComponent(searchValue);
        });

        $(document).ready(function() {
            var searchInput = $('#searchInput');
            var searchResults = $('#searchResults');

            searchInput.on('input', function() {
                var query = searchInput.val();

                var xhr = new XMLHttpRequest();
                xhr.open('GET', '/items/contextSearch?query='+query, true);

                xhr.onreadystatechange = function() {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            var response = xhr.response;
                            renderResults(response);
                        } else {
                            console.error('Ошибка запроса: ' + xhr.status);
                        }
                    }
                };
                xhr.send();
            });

            function renderResults(results) {
                var objects = JSON.parse(results);
                searchResults.empty();
                document.addEventListener('click', function(event) {
                    var targetElement = event.target;

                    if (!targetElement.closest('.text-field__input')) {
                        searchResults.empty()
                        console.log("out")
                    }
                });

                if (objects.length === 0) {
                    searchResults.append('<li class="search-li">No results found</li>');
                } else {
                    for (var i = 0; i < Math.min(objects.length, 3); i++) {
                        searchResults.append('<li class="search-li"><a class="search-a" href="/items/' +objects[i].itemType + '/' + objects[i].id + '">' + objects[i].name + '</a></li>');
                    }

                    if (objects.length > 3) {
                        var remainingCount = objects.length - 3;
                        searchResults.append('<li class="search-li">and ' + remainingCount + ' more items</li>');
                    }
                }
            }
        });

    </script>
</#macro>

<@header>
    <div class="button-wrapper">
        <a href="/items/tea" class="button" data-itemType="tea">
            <span>Чай</span>
        </a>
        <a href="/items/coffee" class="button" data-itemType="coffee">
            <span>Кофе</span>
        </a>
        <a href="/items/honey" class="button" data-itemType="honey">
            <span>Мёд</span>
        </a>
        <a href="/items/dish" class="button" data-itemType="dish">
            <span>Посуда</span>
        </a>
    </div>
</@header>

<#macro footer>
    <footer class="footer-bottom">
        <nav>
            <ul>
                <li class="footer_li">2023 TeaShop - Все права защищены</li>
                <li class="footer_li">г.Казань</li>
                <li class="footer_li">
                    <div class="social-box">
                        <a href="https://vk.com/id94489060">
                            <img src="/images/vk.png" width="25px" height="25px">
                        </a>
                        <a href="https://t.me/NiveusAurum">
                            <img src="/images/tg.png" width="25px" height="25px">
                        </a>
                    </div>
                </li>
            </ul>
        </nav>
    </footer>
</#macro>
<@footer></@footer>

<script>
    $(document).ready(function () {
        $(".button-wrapper .button").click(function () {
            var itemType = $(this).attr("href").split("/").pop();
            window.location.href = "/items/" + itemType;
        });
    });
</script>

</body>
</html>