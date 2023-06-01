<#import "mainPage.ftlh" as main>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/myStyle.css">
    <title>Зарегистрироваться</title>
    <style>
        .signUpForm {
            padding: 50px;
            background: #BFD4AD;
            position: fixed;
            border-radius: .25rem;
            text-align: justify-all;
            top: 50%;
            left: 50%;
            -webkit-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
        }

        .login-btn {
            display: inline-block;
            position: relative;
            /*bottom: 0;*/
            font-weight: 400;
            font-family: teaFont, sans-serif;
            line-height: 1.5;
            width: 350px;
            color: #ffffff;
            text-align: center;
            vertical-align: middle;
            cursor: pointer;
            /*-webkit-user-select: none;*/
            /*-moz-user-select: none;*/
            user-select: none;
            background-color: #597824;
            border: 1px solid #bdbdbd;
            /*padding: .375rem .75rem;*/
            /*margin-left: -17px;*/
            /*margin-bottom: -2px;*/
            padding-left: 30px;
            padding-right: 30px;
            font-size: 1.5rem;
            /*border-radius: .25rem;*/
            transition: background-color .15s ease-in-out;
        }

        input[type="email"] {
            font-family: inherit; /* 1 */
            font-size: inherit; /* 1 */
            line-height: inherit; /* 1 */
            position: absolute;
            right: 50px;
            margin: 0; /* 2 */
            width: 250px;
            height: 30px;
        }

        input[type="password"] {
            font-family: inherit; /* 1 */
            font-size: inherit; /* 1 */
            line-height: inherit; /* 1 */
            margin: 0; /* 2 */
            position: absolute;
            right: 50px;
            width: 250px;
            height: 30px;
        }

        input[type="text"] {
            font-family: inherit; /* 1 */
            font-size: inherit; /* 1 */
            line-height: inherit; /* 1 */
            margin: 0; /* 2 */
            position: absolute;
            right: 50px;
            width: 250px;
            height: 30px;
        }

        .name-block {
            padding-top: 30px;
            padding-bottom: 30px;
        }

        .password-block {
            padding-top: 30px;
            padding-bottom: 30px;
        }

        .little-label {
            position: relative;
            font-size: 1.5rem;
        }

        .big-label {
            position: relative;
            font-size: 1.25rem;
        }

        h3 {
            margin: 0;
        }
    </style>
</head>
<body>
<@main.header></@main.header>
<#--<div id="signUpBlock" class="sign-up-block">-->
    <form id="signUpForm" class="signUpForm" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <h3 style="text-align: center">Зарегистрироваться</h3>
        <div class="name-block">
            <label for="name" class="little-label">Name:</label>
            <input type="text" id="name" name="name" />
        </div>

        <div class="email-block">
            <label for="email" class="little-label">Email:</label>
            <input type="email" id="email" name="email" />
        </div>

        <div class="password-block">
            <label for="password" class="big-label">Password:</label>
            <input type="password" id="password" name="password" />
        </div>

        <button type="submit" class="login-btn">Sign Up</button>
    </form>
<#--</div>-->
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token">

<script>
    $('#signUpForm').submit(function(event) {
        event.preventDefault();
        var formData = {
            "name": $("#name").val(),
            "email": $("#email").val(),
            "password": $("#password").val()
        };
        console.log(formData)
        var csrfToken = document.getElementById('token').value;
        $.ajax({
            type: 'POST',
            url: '/signUp',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
            },
            success: function(response) {
                window.location.replace("/signIn");
            },
            error: function(response) {
                alert("Ошибка регистрации");
            }
        });
    });
</script>
<@main.footer></@main.footer>
</body>
</html>