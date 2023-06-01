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
    <link rel="stylesheet" type="text/css" href="/css/signIn.css">
    <title>Войти</title>
</head>
<body>

<#macro loginForm>
    <div id="loginBlock" class="login-block">
        <h3 style="text-align: center">Войти в аккаунт</h3>
        <form action="/signIn" method="post" class="loginForm" id="loginForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <#--                <label for="email">Email: </label>-->
            <div class="email-block">
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
        </form>
    </div>
</#macro>

<@main.header>

    <@loginForm></@loginForm>

</@main.header>
<@main.footer></@main.footer>
</body>
</html>