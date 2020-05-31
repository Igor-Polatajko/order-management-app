<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
        @import url(https://fonts.googleapis.com/css?family=Lato:700italic,400,400italic,700);
        /* CSS Button */

        html {
            font: 100%/1.5 Lato, Helvetica Neue, Helvetica, Arial, sans-serif;
            -webkit-font-smoothing: antialiased;
            min-height: 100%;
        }

        body {
            margin-top: 15%;
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
        }

        .butt {
            font-size: 1.825em;
            text-decoration: none;
            padding: .35em 1em;
            border-radius: 10px;
            text-shadow: 0 1px 1px rgba(255, 255, 255, .7);
            box-shadow: 0 0 0 1px rgba(0, 0, 0, .2), 0 1px 2px rgba(0, 0, 0, .2), inset 0 1px 2px rgba(255, 255, 255, .7);
        }

        #logoutButton {
            position: absolute;
            top: 5vh;
            right: 5vw;
            border-radius: 10px;
            display: inline-block;
        }

    </style>
</head>
<body>
<form method="POST" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button id="logoutButton" class="btn-lg btn-info">Log out</button>
</form>
<div class="container text-center my-4">
    <h1 class="display-3 ">Management</h1>
</div>

<#if userRole?? && userRole.name() = "ROLE_ADMIN">
    <div class="container text-center my-4">
        <a href="/users/" class="butt btn-block btn-warning">Manage users</a>
    </div>
</#if>

<div class="container text-center">
    <a href="/products/" class="butt btn btn-light">Products</a>
    <a href="/orders/" class="butt btn btn-light m-4">Orders</a>
    <a href="/clients/" class=" butt btn btn-light">Clients</a>
</div>
</body>
</html>
