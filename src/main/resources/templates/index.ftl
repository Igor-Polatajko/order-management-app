<html>
<head>
    <style type="text/css">
        @import url(https://fonts.googleapis.com/css?family=Lato:700italic,400,400italic,700);
        /* CSS Button */

        html {
            font: 100%/1.5 Lato, Helvetica Neue, Helvetica, Arial, sans-serif;
            -webkit-font-smoothing: antialiased;
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
            min-height: 100%;
            text-align: center;
        }

        body {
            margin-top: 15%
        }

        .butt {
            font-size: 1.825em;
            text-decoration: none;
            font-weight: 700;
            padding: .35em 1em;
            background-color: #eaeef1;
            background-image: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, .1));
            border-radius: 10px;
            color: rgba(0, 0, 0, .6);
            text-shadow: 0 1px 1px rgba(255, 255, 255, .7);
            box-shadow: 0 0 0 1px rgba(0, 0, 0, .2), 0 1px 2px rgba(0, 0, 0, .2), inset 0 1px 2px rgba(255, 255, 255, .7);
        }

        #logoutButton {
            position: absolute;
            top: 5vh;
            right: 5vw;
            border-radius:15px;
            border:2px solid #333029;
            display:inline-block;
            cursor:pointer;
            color:#505739;
            font-size: 1.0em;
            font-weight:bold;
            padding:12px 16px;
            text-decoration:none;
            text-shadow:0px 1px 0px #ffffff;
        }
        #logoutButton:hover {
            background-color:#ccc2a6;
        }

    </style>
</head>
<body>
<form method="POST" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button id="logoutButton">Log out</button>
</form>
<h2>-Management-</h2>
<p>
    <a href="/products/" class=butt>Products</a>
    <a href="/orders/" class="butt">Orders</a>
    <a href="/clients/" class="butt">Clients</a>
</p>
</body>
</html>
